package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.dto.AppSmartElectricityDto;
import com.auxgroup.smarthome.app.entity.AppSmartElectricity;
import com.auxgroup.smarthome.app.entity.SmartElectricityRedis;
import com.auxgroup.smarthome.app.repo.AppSmartElectricityRepo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.vo.AppSmartElectricityVo;
import com.auxgroup.smarthome.constant.SmartElectricityConsant;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.enterprise.service.DeviceManageService;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.utils.common.LOG;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fju on 2017/8/1.
 * 智能用电
 */
@Service
public class AppSmartElectricityService {

    @Autowired
    private AppSmartElectricityRepo appSmartElectricityRepo;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;

    @Autowired
    private ObjectRedis objectRedis;

    @Autowired
    private DeviceManageService deviceManageService;

    /**
     * 关闭智能用电 -测试用
     * @param deviceId
     * @return
     */
    @Transactional
    public ApiResponse<String> disableSmartElectricity(String deviceId){
        //检查是否开启了智能用电
        AppSmartElectricity appSmartElectricity = appSmartElectricityRepo.findByDeviceId(deviceId);
        if(appSmartElectricity == null || !appSmartElectricity.getOn()) {
            return ApiResponse.prompt(AppCode.SMART_ELECTRICITY_NOT_ENABLE);
        }
        //关闭智能用电
        appSmartElectricity.close();

        // 清除智能用电缓存
        appSmartElectricity = appSmartElectricityRepo.save(appSmartElectricity);
        String redisKey = CachedConsant.DEVICE_SMART_ELECTRICITY_PREFIX+appSmartElectricity.getMac();
        objectRedis.delete(redisKey);
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse = deviceInfoInnerService.getDeviceNewInfoByDeviceId(deviceId);
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()){
            return ApiResponse.prompt(deviceInfoNewVoApiResponse.getCode(),deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if(null != deviceInfoNewVo) {
            deviceManageService.controlPeakValleyAndSmartElectricity(
                    deviceInfoNewVo.getProductKey(), deviceInfoNewVo.getDid(), 0, 0, SmartElectricityConsant.defaultElectricityValue);
        }
        LOG.info(this, appSmartElectricity.getMac()+"设备关闭智能用电");
        return ApiResponse.ok(appSmartElectricity.getSmartElecId());
    }

    /**
     * 获取某个设备的智能用电规则
     * @param deviceId
     * @return
     */
    public ApiResponse<AppSmartElectricityVo> getSmartElectricity(String deviceId){
        AppSmartElectricity appSmartElectricity = appSmartElectricityRepo.findByDeviceId(deviceId);
        if(appSmartElectricity == null){
           return ApiResponse.ok(new AppSmartElectricityVo());
        }
        //处理为Vo
        AppSmartElectricityVo appSmartElectricityVo = BeanUtils.copyAttrs(new AppSmartElectricityVo(),appSmartElectricity);
        return ApiResponse.ok(appSmartElectricityVo);
    }

    /**
     * 通过mac获取某个设备的智能用电规则
     * @param mac
     * @return
     */
    public ApiResponse<AppSmartElectricityVo> getSmartElectricityByMac(String mac){
        AppSmartElectricity appSmartElectricity = appSmartElectricityRepo.findByMac(mac);
        if(appSmartElectricity == null){
            return ApiResponse.ok(new AppSmartElectricityVo());
        }
        //处理为Vo
        AppSmartElectricityVo appSmartElectricityVo = BeanUtils.copyAttrs(new AppSmartElectricityVo(),appSmartElectricity);
        return ApiResponse.ok(appSmartElectricityVo);
    }

    /**
     * 启用智能用电
     * @param appSmartElectricityDto
     * @return
     */
    @Transactional
    public ApiResponse enableSmartElectricity(AppSmartElectricityDto appSmartElectricityDto){
        //检查设备是否存在
        if(!deviceInfoInnerService.exist(appSmartElectricityDto.getDeviceId()).getData()){
            return ApiResponse.prompt(AppCode.DEVICE_NOT_EXIST);
        }
        //检查规则是否存在
        AppSmartElectricity appSmartElectricity = appSmartElectricityRepo.findByDeviceId(appSmartElectricityDto.getDeviceId());
        if(appSmartElectricity == null){
            //创建新规则
            appSmartElectricity = BeanUtils.copyAttrs(new AppSmartElectricity(appUserService.getUserToken().getUid()), appSmartElectricityDto);
        }else {
            //已经开启
            if(appSmartElectricity.getOn()){
                return ApiResponse.prompt(AppCode.SMART_ELECTRICITY_ENABLED);
            }
            //更新规则
            appSmartElectricity = BeanUtils.copyAttrs(appSmartElectricity,appSmartElectricityDto);
        }
        //开启智能用电
        appSmartElectricity.open(appUserService.getUserToken().getUid());
        appSmartElectricity = appSmartElectricityRepo.save(appSmartElectricity);

        /**
         *  开启智能用电缓存，计算下发功率
         */
        String redisKey = CachedConsant.DEVICE_SMART_ELECTRICITY_PREFIX+appSmartElectricity.getMac();
        SmartElectricityRedis smartElectricityRedis = new SmartElectricityRedis();
        smartElectricityRedis.of(appSmartElectricity.getDeviceId(), appSmartElectricity.getMac(), appSmartElectricity.getStartHour(), appSmartElectricity.getStartMinute(), appSmartElectricity.getEndHour(), appSmartElectricity.getEndMinute(), appSmartElectricity.getTotalElec(), appSmartElectricity.getMode(), appSmartElectricity.getExecuteCycle(), appSmartElectricity.getOn());
        // 初始化智能用电缓存
        smartElectricityRedis.firstInit();
        objectRedis.add(redisKey, smartElectricityRedis);
        LOG.info(this, appSmartElectricity.getMac()+"设备开启智能用电");
        return ApiResponse.ok(appSmartElectricity.getSmartElecId());
    }

    /**
     * 关闭智能用电功能功能 以及下发关闭的电控指令
     * @param mac
     * @param did
     * @return
     */
    public ApiResponse closeSmartElectricity(String pk,String mac,String did){
        String redisKey = CachedConsant.DEVICE_SMART_ELECTRICITY_PREFIX + mac;
        SmartElectricityRedis smartElectricityRedis = objectRedis.get(redisKey, SmartElectricityRedis.class);
        if (smartElectricityRedis==null){
            return ApiResponse.prompt(Syscode.NOT_CONTENT);
        }
        objectRedis.delete(redisKey);
        AppSmartElectricity appSmartElectricity = appSmartElectricityRepo.findByDeviceId(
                smartElectricityRedis.getDeviceId());
        appSmartElectricity.close();
        appSmartElectricityRepo.save(appSmartElectricity);
        deviceManageService.controlPeakValleyAndSmartElectricity(
        pk, did, 0, 0, SmartElectricityConsant.defaultElectricityValue);
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * 删除智能用电缓存
     * @param mac
     */
    public void clearSmartElectricityRedis(String mac){
        String redisKey = CachedConsant.DEVICE_SMART_ELECTRICITY_PREFIX + mac;
        SmartElectricityRedis smartElectricityRedis = objectRedis.get(redisKey, SmartElectricityRedis.class);
        if (smartElectricityRedis != null){
            objectRedis.delete(redisKey);
        }
    }
}
