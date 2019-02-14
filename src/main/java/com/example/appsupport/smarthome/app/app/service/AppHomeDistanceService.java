package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.dto.AppHomeDistanceDto;
import com.auxgroup.smarthome.app.entity.AppHomeDistanceEntity;
import com.auxgroup.smarthome.app.repo.AppHomeDistanceRepo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.vo.AppHomeDistanceVo;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 离家回家服务
 * Created by lixiaoxiao on 17-8-1.
 */
@Service
public class AppHomeDistanceService {

    @Autowired
    private AppHomeDistanceRepo appHomeDistanceRepo;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ObjectRedis objectRedis;

    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;

    public List<AppHomeDistanceVo> findHomeDistance(String deviceId) {
        List<AppHomeDistanceEntity> result = new ArrayList<>();
        String uid = appUserService.getUid();
        if (StringUtils.isNotEmpty(deviceId)) {
            //根据用户id和设备id获取该用户开启离家回家的数据（先去redis查，如果没有再去数据库查） 修改人：吴建龙 修改时间：2018-05-17
            AppHomeDistanceEntity appHomeDistanceEntity = getUserDeviceByUIdAndDeviceId(uid, deviceId);
            if (appHomeDistanceEntity != null)
                result.add(appHomeDistanceEntity);
        } else {
            //根据用户id获取该用户开启离家回家的设备列表（先去redis查，如果没有再去数据库查） 修改人：吴建龙 修改时间：2018-05-17
            result = getUserDeviceListByUId(uid);
        }
        //筛选存在的设备 修改人：吴建龙 修改时间：2018-05-17
        List<AppHomeDistanceEntity> results = rmNotexistDevice(result);

        List<AppHomeDistanceVo> appHomeDistanceVoList = results.stream().map(x -> BeanUtils.copyAttrs(new AppHomeDistanceVo(), x)).collect(Collectors.toList());
        return appHomeDistanceVoList;
    }

    /**
     * 筛选存在的设备，并把不存在的设备从数据库和缓存中清除
     *
     * @param result
     * @return
     */
    public List<AppHomeDistanceEntity> rmNotexistDevice(List<AppHomeDistanceEntity> result) {
        List<AppHomeDistanceEntity> results = new ArrayList<>();
        if (null != result && result.size() > 0) {
            for (AppHomeDistanceEntity appHomeDistanceEntity : result) {
                String resultDeviceId = appHomeDistanceEntity.getDeviceId();
                String resultUid = appHomeDistanceEntity.getuId();
                ApiResponse<Boolean> apiResponse = deviceInfoInnerService.exist(resultDeviceId);
                if (null == apiResponse.getData() || apiResponse.getData() == false) {
                    appHomeDistanceRepo.deleteByUIdAndDeviceId(resultUid, resultDeviceId);
                    objectRedis.delete(CachedConsant.HOME_DISTANCE_CACHED_PREFIX + resultUid + "/" + resultDeviceId);
                    objectRedis.delete(CachedConsant.HOME_DISTANCE_CACHED_PREFIX + resultDeviceId + "/" + resultUid);
                    objectRedis.delete(CachedConsant.HOME_DISTANCE_CACHED_PREFIX + resultDeviceId);
                    continue;
                }
                results.add(appHomeDistanceEntity);
            }
        }
        return results;
    }

    /**
     * 根据设备id,uid获取离家回家信息
     *
     * @param deviceId
     * @return
     */
    public AppHomeDistanceVo findByDeviceId(String deviceId) {
        String uid = appUserService.getUid();
        AppHomeDistanceEntity appHomeDistanceEntity = appHomeDistanceRepo.findByDeviceIdAndUId(deviceId, uid);
        return BeanUtils.copyAttrs(new AppHomeDistanceVo(), appHomeDistanceEntity);
    }

    /**
     * 新增离家回家模式
     *
     * @param appHomeDistanceDto
     * @return
     */
    public boolean addHomeDistance(AppHomeDistanceDto appHomeDistanceDto) {
        String uid = appUserService.getUid();
        AppHomeDistanceEntity appHomeDistanceEntity = appHomeDistanceRepo.findByDeviceIdAndUId(
                appHomeDistanceDto.getDeviceId(), uid);
        if (appHomeDistanceEntity == null) {
            appHomeDistanceEntity = new AppHomeDistanceEntity();
            BeanUtils.copyAttrs(appHomeDistanceEntity, appHomeDistanceDto);
            //设置触发模式默认false
            appHomeDistanceEntity.setModeLaunch(0);
            appHomeDistanceEntity.setuId(appUserService.getUid());
        } else {
            appHomeDistanceEntity.setDistance(appHomeDistanceDto.getDistance());
            appHomeDistanceEntity.setOn(appHomeDistanceDto.getOn());
        }
        AppHomeDistanceEntity appHomeDistanceEntity1 = appHomeDistanceRepo.save(appHomeDistanceEntity);
        //设置用户-设备的离家回家缓存 修改人：吴建龙 修改时间：2018-05-16
        saveUserDeviceHomeDistanceCache(appHomeDistanceEntity1);
        return true;
    }

    /**
     * 保存离家回家模式
     *
     * @param appHomeDistanceDto
     * @return
     */
    public boolean saveHomeDistance(AppHomeDistanceDto appHomeDistanceDto) {
        AppHomeDistanceEntity appHomeDistanceEntity = new AppHomeDistanceEntity();
        BeanUtils.copyAttrs(appHomeDistanceEntity, appHomeDistanceDto);
        //设置触发模式默认false
        appHomeDistanceEntity.setModeLaunch(0);
        appHomeDistanceEntity.setuId(appUserService.getUid());
        AppHomeDistanceEntity appHomeDistanceEntity1 = appHomeDistanceRepo.save(appHomeDistanceEntity);
        //设置用户-设备的离家回家缓存 修改人：吴建龙 修改时间：2018-05-16
        saveUserDeviceHomeDistanceCache(appHomeDistanceEntity1);
        return true;
    }

    /**
     * 开启/关闭离家回家
     *
     * @param appHomeDistanceDto
     * @return
     */
    public boolean updateHomeDistance(AppHomeDistanceDto appHomeDistanceDto) {
        AppHomeDistanceEntity appHomeDistanceEntity = appHomeDistanceRepo.findByDistanceId(appHomeDistanceDto.getDistanceId());
        appHomeDistanceEntity.setOn(appHomeDistanceDto.getOn());
        AppHomeDistanceEntity appHomeDistanceEntity1 = appHomeDistanceRepo.save(appHomeDistanceEntity);
        //设置用户-设备的离家回家缓存 修改人：吴建龙 修改时间：2018-05-16
        saveUserDeviceHomeDistanceCache(appHomeDistanceEntity1);
        return true;
    }


    /**
     * 设置用户-设备的离家回家缓存
     *
     * @param appHomeDistanceEntity 离家回家实体类
     */
    public void saveUserDeviceHomeDistanceCache(AppHomeDistanceEntity appHomeDistanceEntity) {
        String redisKey = CachedConsant.HOME_DISTANCE_CACHED_PREFIX + appHomeDistanceEntity.getuId() + "/" + appHomeDistanceEntity.getDeviceId();
        if (appHomeDistanceEntity.getOn())
            objectRedis.add(redisKey, appHomeDistanceEntity);
        else
            objectRedis.delete(redisKey);
    }

    /**
     * 根据用户id获取该用户开启离家回家的设备列表
     *
     * @param uid 用户id
     * @return
     */
    public List<AppHomeDistanceEntity> getUserDeviceListByUId(String uid) {
        String redisKeyPatter = CachedConsant.HOME_DISTANCE_CACHED_PREFIX + uid + "/*";
        List<AppHomeDistanceEntity> appHomeDistanceEntityList = objectRedis.getAllObjects(redisKeyPatter, AppHomeDistanceEntity.class);
        if (null == appHomeDistanceEntityList) {
            appHomeDistanceEntityList = appHomeDistanceRepo.findByUId(uid);
            for (AppHomeDistanceEntity appHomeDistanceEntity : appHomeDistanceEntityList) {
                String redisKey = CachedConsant.HOME_DISTANCE_CACHED_PREFIX + uid + "/" + appHomeDistanceEntity.getDeviceId();
                objectRedis.add(redisKey, appHomeDistanceEntity);
            }
        }
        return appHomeDistanceEntityList;
    }

    /**
     * 根据用户id和设备id获取该用户开启离家回家的数据
     * @param uid
     * @param deviceId
     * @return
     */
    public AppHomeDistanceEntity getUserDeviceByUIdAndDeviceId(String uid, String deviceId) {
        String redisKey = CachedConsant.HOME_DISTANCE_CACHED_PREFIX + uid + "/" + deviceId;
        AppHomeDistanceEntity appHomeDistanceEntity = objectRedis.get(redisKey, AppHomeDistanceEntity.class);
        if (null == appHomeDistanceEntity) {
            appHomeDistanceEntity = appHomeDistanceRepo.findByDeviceIdAndUIdAndOnIsTrue(deviceId, uid);
            if (appHomeDistanceEntity != null)
                objectRedis.add(redisKey, appHomeDistanceEntity);
        }
        return appHomeDistanceEntity;
    }

}
