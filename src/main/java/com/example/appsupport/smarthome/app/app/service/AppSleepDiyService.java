package com.example.appsupport.smarthome.app.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoVo;
import com.auxgroup.smarthome.BeanListUtils;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.SleepDiyDatapointControlEnum;
import com.auxgroup.smarthome.app.dto.AppSleepDiyDto;
import com.auxgroup.smarthome.app.entity.AppSleepDiyEntity;
import com.auxgroup.smarthome.app.repo.AppSleepDiyRepo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.util.StrTime;
import com.auxgroup.smarthome.app.vo.AppSleepDiyVo;
import com.auxgroup.smarthome.enterprise.bean.AppSleepDiyCacheInfo;
import com.auxgroup.smarthome.enterprise.service.DeviceManageService;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.utils.common.LOG;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by niuGuangzhe on 2017/7/26.
 */
@Service
public class AppSleepDiyService {
    @Autowired
    private AppSleepDiyRepo appSleepDiyRepo;

    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;

    @Autowired
    private DeviceManageService deviceManageService;

    @Autowired
    private ObjectRedis objectRedis;

    static String PREFIX_RULE = "app_sleep_diy/";


    /**
     * 查询睡眠DIY
     * 2017年 10月12日 新增 互斥关系 仅制冷，制热模式有效的提示
     * 即只有在制冷制热两模式下才能使用睡眠DIY功能 也能打开睡眠diy的功能面板。
     *
     * @param deviceId 设备ID
     * @return
     */
    public ApiResponse<List<AppSleepDiyVo>> getAppSleepDiy(String deviceId) {
        ApiResponse<DeviceInfoNewVo> apiResponse = deviceInfoInnerService.getDeviceNewInfoByDeviceId(deviceId);
        if (!apiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(apiResponse.getCode(), apiResponse.getMessage());
        }
        if (apiResponse.getData() == null) {
            throw new IllegalArgumentException();
        }
        DeviceInfoNewVo deviceInfoNewVo = apiResponse.getData();
        // 转由 移动端限制
//        String productKey = deviceInfoNewVo.getProductKey();
//        String did = deviceInfoNewVo.getDid();
//        // 设备来源
//        int source = deviceInfoNewVo.getSource();
//        if(source==1 && !deviceManageService.isHotAndColdMode(productKey,did)){
//            return ApiResponse.prompt(AppCode.SLEEPDIY_NOT_SETTING);
//        }
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        List<AppSleepDiyEntity> appSleepDiyEntities = appSleepDiyRepo.findByDeviceIdAndEnableTrue(deviceId, sort);
        List<AppSleepDiyVo> sleepDiyVos = new BeanListUtils<>(AppSleepDiyVo.class, appSleepDiyEntities).getTargetList();
        return ApiResponse.ok(sleepDiyVos);
    }

    /**
     * 新增睡眠DIY
     * 再三确认 这里的新增没有检查时间重合机制、
     * 注意:此处的新增睡眠DIY、默认是关闭的。
     *
     * @param appSleepDiyDto
     * @return 新增是否成功
     */
    public ApiResponse<AppSleepDiyVo> addAppSleepDiy(AppSleepDiyDto appSleepDiyDto) {
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse =
                deviceInfoInnerService.getDeviceNewInfoByDeviceId(appSleepDiyDto.getDeviceId());
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(
                    deviceInfoNewVoApiResponse.getCode(), deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
//        //单冷机无法设置制热定时任务
//        if(appSleepDiyDto.getMode()==2&&!deviceManageService.isSupportHeat(deviceInfoNewVo.getProductKey(),deviceInfoNewVo.getDid()))
//            return ApiResponse.prompt(AppCode.SLEEPDIY_NO_HEAT_TASK);
        appSleepDiyDto.assignEndHour();
        AppSleepDiyEntity appSleepDiyEntity = BeanUtils.copyAttrs(new AppSleepDiyEntity(), appSleepDiyDto);
        appSleepDiyEntity = appSleepDiyRepo.save(appSleepDiyEntity);
        AppSleepDiyVo appSleepDiyVo = BeanUtils.copyAttrs(new AppSleepDiyVo(), appSleepDiyEntity);
        return ApiResponse.ok(appSleepDiyVo);
    }

    /**
     * 修改睡眠DIY
     * 再次确认开启状态下的睡眠diy无法编辑
     *
     * @param appSleepDiyDto
     * @return 更细成功的标志
     */
    public ApiResponse updateAppSleepDiy(String sleepDiyId, AppSleepDiyDto appSleepDiyDto) {
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse =
                deviceInfoInnerService.getDeviceNewInfoByDeviceId(appSleepDiyDto.getDeviceId());
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(
                    deviceInfoNewVoApiResponse.getCode(), deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
//        //单冷机无法设置制热定时任务
//        if(appSleepDiyDto.getMode()==2&&!deviceManageService.isSupportHeat(deviceInfoNewVo.getProductKey(),deviceInfoNewVo.getDid()))
//            return ApiResponse.prompt(AppCode.SLEEPDIY_NO_HEAT_TASK);
        appSleepDiyDto.assignEndHour();
        AppSleepDiyEntity sleepDiyEntity = appSleepDiyRepo.findBySleepDiyIdAndEnableTrue(sleepDiyId);
        //增加睡眠diy空指针判断 add by lixiaoxiao 20171207
        if (null == sleepDiyEntity) {
            return ApiResponse.prompt(AppCode.SLEEPDIY_NOT_FIND);
        }
        if (sleepDiyEntity.getOn()) {
            return ApiResponse.prompt(AppCode.SLEEPDIY_NOT_EDIT);
        }
        AppSleepDiyEntity appSleepDiyEntity = BeanUtils.copyAttrs(sleepDiyEntity, appSleepDiyDto);
        appSleepDiyRepo.save(appSleepDiyEntity);
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    /**
     * 删除睡眠DIY
     *
     * @param sleepDiyId 睡眠表主键
     * @return
     */
    public ApiResponse deleteAppSleepDiy(String sleepDiyId) {
        int row = appSleepDiyRepo.logicalDeleted(sleepDiyId, false);
        if (row > 0) {
            deleteCache(sleepDiyId);
            return ApiResponse.prompt(AppCode.SC_OK);
        }
        return ApiResponse.prompt(AppCode.FAIL);
    }

    /**
     * 关闭设备所有的睡眠DIY
     *
     * @param deviceId
     * @return
     */
    public ApiResponse closeAllSleepDiyByDeviceId(String deviceId) {
        List<AppSleepDiyEntity> appSleepDiyEntityList = appSleepDiyRepo.findByDeviceIdAndEnableTrueAndOnTrue(deviceId);
        for (Iterator<AppSleepDiyEntity> iterator = appSleepDiyEntityList.iterator(); iterator.hasNext(); ) {
            AppSleepDiyEntity sleepDiyEntity = iterator.next();
            deleteCache(sleepDiyEntity.getSleepDiyId());
        }
        appSleepDiyRepo.closeAllSleepDiyByDeviceId(deviceId);
        return ApiResponse.ok();
    }

    /**
     * 需求确认：
     * 古北设备 同时只有一个开启
     * 机智云设备 只要时间不重合就可以开启多个
     *
     * @param sleepDiyId
     * @param isOn
     * @return
     */
    public ApiResponse switchAppSleepDiyColumn(String sleepDiyId, Boolean isOn) {
        AppSleepDiyEntity entity = appSleepDiyRepo.findBySleepDiyIdAndEnableTrue(sleepDiyId);
        if (entity == null) {
            return ApiResponse.prompt(AppCode.SLEEPDIY_NOT_FIND);
        }
        //关闭 ：只需要移除缓存操作即可
        if (!isOn) {
            entity.setOn(false);
            appSleepDiyRepo.save(entity);
            deleteCache(entity.getSleepDiyId());
            // add by lixiaoxiao 关闭时下发关闭睡眠diy指令
            closeSleepDiy(entity);
            return ApiResponse.prompt(AppCode.SC_OK);
        }
        //古北设备同时只有一个开启
        if (entity.getDeviceManufacturer() == 0) {
            return handleGuBeiDeviceSleepDiy(entity);
        }
        //机智云只要时间不重合 就可以开启，可以同时存在多个开启状态
        if (entity.getDeviceManufacturer() == 1) {
            return handleGizwitsDeviceSleepDiy(entity);
        }
        return ApiResponse.prompt(AppCode.FAIL);
    }

    /**
     * 此方法仅用于处理古北设备的睡眠diy开启逻辑
     *
     * @param entity
     * @return
     */
    private ApiResponse handleGuBeiDeviceSleepDiy(AppSleepDiyEntity entity) {
        List<AppSleepDiyEntity> appSleepDiyEntities = appSleepDiyRepo.findByDeviceIdAndEnableTrue(entity.getDeviceId());
        for (Iterator<AppSleepDiyEntity> iterator = appSleepDiyEntities.iterator(); iterator.hasNext(); ) {
            AppSleepDiyEntity sleepDiyEntity = iterator.next();
            if (sleepDiyEntity.getOn()) {
                return ApiResponse.prompt(AppCode.SLEEPDIY_GUBEI_ONLY_ONE);
            }
        }
        entity.setOn(true);
        appSleepDiyRepo.save(entity);
        syncCache(entity);
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    /**
     * 此方法仅用于处理机智云设备的睡眠diy开启逻辑
     *
     * @param entity
     * @return
     */
    private ApiResponse handleGizwitsDeviceSleepDiy(AppSleepDiyEntity entity) {
        List<AppSleepDiyEntity> appSleepDiyEntities = appSleepDiyRepo.findByDeviceIdAndEnableTrue(entity.getDeviceId());
        for (Iterator<AppSleepDiyEntity> iterator = appSleepDiyEntities.iterator(); iterator.hasNext(); ) {
            AppSleepDiyEntity sleepDiyEntity = iterator.next();
            if (sleepDiyEntity.getOn() && isCoincide(sleepDiyEntity.getStartHour(),
                    sleepDiyEntity.getEndHour(), entity.getStartHour(), entity.getEndHour())) {
                return ApiResponse.prompt(AppCode.SLEEPDIY_TIME_CONFLICT);
            }
        }
        entity.setOn(true);
        appSleepDiyRepo.save(entity);
        syncCache(entity);
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    /**
     * true:重合  false:不重合
     *
     * @param startHour1
     * @param endHour1
     * @param startHour2
     * @param endHour2
     * @return
     */
    private boolean isCoincide(int startHour1, int endHour1, int startHour2, int endHour2) {
        StrTime strTime1 = new StrTime(startHour1, 0, endHour1, 0);
        StrTime strTime2 = new StrTime(startHour2, 0, endHour2, 0);
        return strTime1.isCoincide(strTime2) == 0;
    }

    private static boolean isCoincide2(int startHour1, int endHour1, int startHour2, int endHour2) {
        StrTime strTime1 = new StrTime(startHour1, 0, endHour1, 0);
        StrTime strTime2 = new StrTime(startHour2, 0, endHour2, 0);
        return strTime1.isCoincide(strTime2) == 0;
    }

    /**
     * 缓存睡眠diy指令
     *
     * @param appSleepDiyEntity
     */
    private void syncCache(AppSleepDiyEntity appSleepDiyEntity) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String strElectricControl = appSleepDiyEntity.getElectricControl();
        JSONArray jsonArray = JSON.parseArray(strElectricControl);
        Iterator iterator = jsonArray.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            for (int k = 1; k < 7; k++) {
                map.put(SleepDiyDatapointControlEnum.SLEEP_DIY_WIND_SPEED.getName() + "_" + (k + i * 6),
                        convertSpeed(jsonObject.getString("windSpeed")));
                map.put(SleepDiyDatapointControlEnum.SLEEP_DIY_TEMPERATURE.getName() + "_" + (k + i * 6),
                        jsonObject.getString("temperature"));
            }
            i++;
        }
        DeviceInfoVo deviceInfoVo = deviceInfoInnerService.getDeviceInfo(
                appSleepDiyEntity.getDeviceId()).getData();
        AppSleepDiyCacheInfo appSleepDiyCacheInfo = new AppSleepDiyCacheInfo(
                appSleepDiyEntity.getStartHour(), appSleepDiyEntity.getStartMinute(),
                deviceInfoVo.getProductKey(), deviceInfoVo.getDid(),
                map, appSleepDiyEntity.getMode(), appSleepDiyEntity.getSleepDiyId());
        objectRedis.add(PREFIX_RULE + appSleepDiyEntity.getSleepDiyId(), appSleepDiyCacheInfo);
    }

    /**
     * 清空缓存
     *
     * @param sleepDiyId
     */
    private void deleteCache(String sleepDiyId) {
        objectRedis.delete(PREFIX_RULE + sleepDiyId);
    }

    public ApiResponse closeAllSleepDiy(String pk, String did) {
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse = deviceInfoInnerService.getDeviceNewInfoByDid(did);
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(deviceInfoNewVoApiResponse.getCode(), deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if (deviceInfoNewVo == null || !deviceInfoNewVo.isNewDevice()) {
            return ApiResponse.prompt(AppCode.NOT_CONTENT);
        }
        String deviceId = deviceInfoNewVo.getDeviceId();
        closeAllSleepDiyByDeviceId(deviceId);
        deviceManageService.closeSleepDiyRemoteControl(pk, did);
        LOG.dubug(this, "did =" + did + ",切换非制冷制热模式的时候，会关闭睡眠DIY功能");
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * add by lixiaoxiao 关闭时下发关闭睡眠diy指令
     *
     * @param entity
     */

    private void closeSleepDiy(AppSleepDiyEntity entity) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        int hour = localDateTime.getHour();
        if (between(hour, entity.getStartHour(), entity.getEndHour())) {
            DeviceInfoVo deviceInfoVo = deviceInfoInnerService.getDeviceInfo(
                    entity.getDeviceId()).getData();
            if (null != deviceInfoVo
                    && StringUtils.isNotBlank(deviceInfoVo.getProductKey())
                    && StringUtils.isNotBlank(deviceInfoVo.getDid())) {
                deviceManageService.closeSleepDiyRemoteControl(deviceInfoVo.getProductKey(), deviceInfoVo.getDid());
            }
        }
    }

    public String convertSpeed(String speed) {
        switch (speed) {
            //低风
            case "0":
                return "1";
            //中风
            case "1":
                return "2";
            //高风
            case "2":
                return "3";
            //静音
            case "3":
                return "0";
            //其他
            default:
                return "4";
        }
    }

    public static boolean between(int curentTime, int startTime, int endTime) {
        if (startTime > endTime) {
            if (curentTime <= endTime) {
                return true;
            } else {
                if (curentTime >= startTime) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return curentTime >= startTime && curentTime <= endTime;
        }
    }

    public ApiResponse closeRunSleepDiy(String pk, String did, String deviceId) {
        List<AppSleepDiyEntity> appSleepDiyEntityList = appSleepDiyRepo.findByDeviceIdAndEnableTrueAndOnTrue(deviceId);
        if (CollectionUtils.isEmpty(appSleepDiyEntityList))
            return ApiResponse.ok();
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int nowMinutes = hour * 60 + minute;
        for (int i = 0, size = appSleepDiyEntityList.size(); i < size; i++) {
            AppSleepDiyEntity appSleepDiyEntity = appSleepDiyEntityList.get(i);
            int startHour = appSleepDiyEntity.getStartHour();
            int startMinute = appSleepDiyEntity.getStartMinute();
            int startMinutes = startHour * 60 + startMinute;
            int endHour = appSleepDiyEntity.getEndHour();
            int endMinute = appSleepDiyEntity.getEndMinute();
            int endMinutes = endHour * 60 + endMinute;
            boolean b = isBetween(nowMinutes, startMinutes, endMinutes);
            if (!b)
                continue;
            Long createdAt = appSleepDiyEntity.getCreatedAt();
            long startSecond = epochSecond(startHour, startMinute);
            //创建时间大于开始时间表示睡眠diy未运行
            if (createdAt > startSecond)
                continue;
            //关闭正在运行的睡眠diy
            closeRunDiy(appSleepDiyEntity, pk, did);
            break;
        }
        return ApiResponse.ok();

    }

    public ApiResponse closeRunDiy(AppSleepDiyEntity appSleepDiyEntity, String pk, String did) {
        appSleepDiyEntity.setOn(false);
        appSleepDiyRepo.save(appSleepDiyEntity);
        deleteCache(appSleepDiyEntity.getSleepDiyId());
        // 关闭时下发关闭睡眠diy指令
        deviceManageService.closeSleepDiyRemoteControl(pk, did);
        return ApiResponse.ok();
    }


    public boolean isBetween(int nowMinutes, int startMinutes, int endMinutes) {
        if (startMinutes == endMinutes)
            return true;
        else if (startMinutes < endMinutes) {
            if (nowMinutes >= startMinutes && nowMinutes <= endMinutes)
                return true;
        } else if (startMinutes > endMinutes) {
            if (nowMinutes >= startMinutes || nowMinutes <= endMinutes)
                return true;
        }
        return false;
    }

    public long epochSecond(int hour, int minute) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(hour, minute);
        LocalDateTime now = LocalDateTime.of(localDate, localTime);
        Long second = now.toEpochSecond(ZoneOffset.of("+8"));
        return second;
    }

}
