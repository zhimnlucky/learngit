package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.dto.AppScheduleDto;
import com.auxgroup.smarthome.app.dto.AppUpdateScheduleDto;
import com.auxgroup.smarthome.app.entity.AppScheduleEntity;
import com.auxgroup.smarthome.app.entity.AppScheduleExtraEntity;
import com.auxgroup.smarthome.app.repo.AppScheduleRepo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.vo.AppScheduleIdVo;
import com.auxgroup.smarthome.app.vo.AppScheduleVo;
import com.auxgroup.smarthome.enterprise.service.DeviceManageService;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by fju on 2017/8/14. 定时控制 - 旧设备在saas平台不存储数据 saas平台不参与控制 商用空调有
 */
@Service
public class AppScheduleService {

    @Autowired
    private AppScheduleRepo appScheduleRepo;
    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;
    @Autowired
    private ObjectRedis objectRedis;
    @Autowired
    private DeviceManageService deviceManageService;

    private static final String schedulePrefix = "app_schedule/";

    /**
     * 同步定时任务数据到缓存
     *
     * @param appScheduleEntity
     */
    private ApiResponse addScheduleAtCache(AppScheduleEntity appScheduleEntity) {
        if (appScheduleEntity.getOn()) {
            ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse =
                    deviceInfoInnerService.getDeviceNewInfoByDeviceId(
                            appScheduleEntity.getDeviceId());
            if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
                return ApiResponse.prompt(
                        deviceInfoNewVoApiResponse.getCode(),
                        deviceInfoNewVoApiResponse.getMessage());
            }
            DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
            if (deviceInfoNewVo.isNewDevice()) {
                AppScheduleExtraEntity appScheduleExtraEntity =
                        new AppScheduleExtraEntity()
                                .createAppScheduleExtraEntity(appScheduleEntity, deviceInfoNewVo);
                objectRedis.add(
                        schedulePrefix + appScheduleExtraEntity.getId(), appScheduleExtraEntity);
            }
        } else {
            removeScheduleAtCache(appScheduleEntity.getId());
        }
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * 移除缓存中的定时任务
     *
     * @param schedule_id
     */
    private void removeScheduleAtCache(String schedule_id) {
        objectRedis.delete(schedulePrefix + schedule_id);
    }

    /**
     * 创建定时任务 默认该任务为关闭状态 kevin 2017-09-13 新增旧设备定时任务数据不能存储在saas平台的校验
     *
     * @param appScheduleDto
     * @return
     */
    public ApiResponse<AppScheduleIdVo> createSchedule(AppScheduleDto appScheduleDto) {
        if (!deviceInfoInnerService.exist(appScheduleDto.getDeviceId()).getData()) {
            return ApiResponse.prompt(AppCode.DEVICE_NOT_EXIST);
        }
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse =
                deviceInfoInnerService.getDeviceNewInfoByDeviceId(appScheduleDto.getDeviceId());
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(
                    deviceInfoNewVoApiResponse.getCode(), deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if (deviceInfoNewVo == null || !deviceInfoNewVo.isNewDevice()) {
            return ApiResponse.prompt(AppCode.SCHEDULE_ALLOW_DEVICE);
        }
//        //单冷机无法设置制热定时任务
//        if(appScheduleDto.getMode()==2&&!deviceManageService.isSupportHeat(deviceInfoNewVo.getProductKey(),deviceInfoNewVo.getDid()))
//            return ApiResponse.prompt(AppCode.SCHEDULE_NO_HEAT_TASK);
        // 空调类型，0：单元机，1：多联机
        int suitType = deviceInfoNewVo.getSuitType();
        if (suitType == 1 && appScheduleDto.getDst() == null) {
            return ApiResponse.prompt(AppCode.SCHEDULE_MULTI_SPLIT_DST_NOT_NULL);
        }
//        appScheduleDto.setOn(false);
        List<AppScheduleEntity> appScheduleEntities =
                appScheduleRepo.findByDeviceIdAndTrashFalse(appScheduleDto.getDeviceId());
        boolean isRepeat = isRepeatTimeSetting(appScheduleEntities, appScheduleDto.getHourSetting(), appScheduleDto.getMinuteSetting(), appScheduleDto.getRepeatRule());
        if (isRepeat) {
            return ApiResponse.prompt(AppCode.SCHEDULE_TIME_SETTING_REPEATED);
        }
        AppScheduleEntity appScheduleEntity =
                BeanUtils.copyAttrs(new AppScheduleEntity(), appScheduleDto);
        appScheduleEntity = appScheduleRepo.save(appScheduleEntity);
        if (appScheduleEntity == null) {
            return ApiResponse.prompt(AppCode.SCHEDULE_CREATE_FAIL);
        }
        addScheduleAtCache(appScheduleEntity);
        AppScheduleIdVo appScheduleIdVo = new AppScheduleIdVo();
        appScheduleIdVo.setId(appScheduleEntity.getId());
        return ApiResponse.ok(appScheduleIdVo);
    }

    /**
     * Determine if there is a repeat time setting. If the time setting is repeated, return true and
     * false on other cases
     * e.g. monday was been setting 10:42, after that the group of monday and Tuesday wasn't
     *
     * @return
     */
    private boolean isRepeatTimeSetting(List<AppScheduleEntity> appScheduleEntities, int hour, int minute, String repeatRule) {
        if (null != appScheduleEntities) {
            long count =
                    appScheduleEntities
                            .stream()
                            .filter(
                                    appSchedule -> {
                                        Collection collection = CollectionUtils.intersection(Arrays.asList(appSchedule.getRepeatRule().split(",")), Arrays.asList(repeatRule.split(",")));
                                        return collection.size()>0 && appSchedule.getHourSetting() == hour
                                            && appSchedule.getMinuteSetting() == minute;
                                    })
                            .count();
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改定时控制任务
     *
     * @param id
     * @param appScheduleDto
     * @return
     */
    public ApiResponse<AppScheduleIdVo> updateSchedule(
            String id, AppUpdateScheduleDto appScheduleDto) {
        AppScheduleEntity appScheduleEntity = appScheduleRepo.findOne(id);
        if (appScheduleEntity == null) {
            return ApiResponse.prompt(AppCode.SCHEDULE_NOT_FOUND);
        }
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse =
                deviceInfoInnerService.getDeviceNewInfoByDeviceId(appScheduleEntity.getDeviceId());
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(
                    deviceInfoNewVoApiResponse.getCode(), deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if (deviceInfoNewVo == null || !deviceInfoNewVo.isNewDevice()) {
            return ApiResponse.prompt(AppCode.SCHEDULE_ALLOW_DEVICE);
        }
//        //单冷机无法设置制热定时任务
//        if(appScheduleDto.getMode()==2&&!deviceManageService.isSupportHeat(deviceInfoNewVo.getProductKey(),deviceInfoNewVo.getDid()))
//            return ApiResponse.prompt(AppCode.SCHEDULE_NO_HEAT_TASK);
        // 空调类型，0：单元机，1：多联机
        int suitType = deviceInfoNewVo.getSuitType();
        if (suitType == 1 && appScheduleDto.getDst() == null) {
            return ApiResponse.prompt(AppCode.SCHEDULE_MULTI_SPLIT_DST_NOT_NULL);
        }
//        appScheduleDto.setOn(false);
        List<AppScheduleEntity> appScheduleEntities =
                appScheduleRepo.findByDeviceIdAndTrashFalse(appScheduleEntity.getDeviceId());
        appScheduleEntities.remove(appScheduleEntity);
        boolean isRepeat = isRepeatTimeSetting(appScheduleEntities, appScheduleDto.getHourSetting(), appScheduleDto.getMinuteSetting(), appScheduleDto.getRepeatRule());
        if (isRepeat) {
            return ApiResponse.prompt(AppCode.SCHEDULE_TIME_SETTING_REPEATED);
        }
        appScheduleEntity = BeanUtils.copyAttrs(appScheduleEntity, appScheduleDto);
        appScheduleEntity = appScheduleRepo.save(appScheduleEntity);
        if (appScheduleEntity == null) {
            return ApiResponse.prompt(AppCode.SCHEDULE_CREATE_FAIL);
        }
        AppScheduleIdVo appScheduleIdVo = new AppScheduleIdVo();
        appScheduleIdVo.setId(appScheduleEntity.getId());
        //BUG-025 搭配新设备编辑定时成功后未执行   修改人：吴建龙 修改日期：2018-05-08
        addScheduleAtCache(appScheduleEntity);
        return ApiResponse.ok(appScheduleIdVo);
    }

    /**
     * 开启或者关闭定时控制任务
     *
     * @param id   定时控制任务
     * @param isOn true:开启任务 false:关闭任务
     * @return
     */
    public ApiResponse switchSchedule(String id, Boolean isOn) {
        AppScheduleEntity appScheduleEntity = appScheduleRepo.findOne(id);
        if (appScheduleEntity == null || appScheduleEntity.getTrash()) {
            return ApiResponse.prompt(AppCode.SCHEDULE_NOT_FOUND);
        }
        appScheduleEntity.setOn(isOn);
        appScheduleRepo.save(appScheduleEntity);
        if (!isOn) {
            removeScheduleAtCache(appScheduleEntity.getId());
        } else {
            addScheduleAtCache(appScheduleEntity);
        }
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    /**
     * 获取设备的定时任务列表
     *
     * @param deviceId
     * @return
     */
    public ApiResponse<List<AppScheduleVo>> getScheduleList(String deviceId, Integer dst) {
        ApiResponse<Boolean> apiResponse = deviceInfoInnerService.exist(deviceId);
        if (!apiResponse.isNotErrorCode()) {
            return ApiResponse.prompt(apiResponse.getCode() + "", apiResponse.getMessage());
        }
        if (!apiResponse.getData()) {
            return ApiResponse.prompt(AppCode.DEVICE_NOT_EXIST);
        }
        List<AppScheduleEntity> appScheduleEntityList = new ArrayList<>();
        if (null != dst) {
            appScheduleEntityList =
                    appScheduleRepo.findByDeviceIdAndDstAndTrashFalse(deviceId, dst);
        } else {
            appScheduleEntityList = appScheduleRepo.findByDeviceIdAndTrash(deviceId, false);
        }
        List<AppScheduleVo> appScheduleVoList = new ArrayList<>();
        appScheduleEntityList
                .stream()
                .forEach(
                        appScheduleEntity -> {
                            AppScheduleVo appScheduleVo =
                                    BeanUtils.copyAttrs(new AppScheduleVo(), appScheduleEntity);
                            appScheduleVoList.add(appScheduleVo);
                        });
        return ApiResponse.ok(appScheduleVoList);
    }

    /**
     * 删除(丢弃、放弃)定时任务
     *
     * @param id
     * @return
     */
    public ApiResponse delSchedule(String id) {
        AppScheduleEntity appScheduleEntity = appScheduleRepo.findOne(id);
        if (appScheduleEntity == null || appScheduleEntity.getTrash()) {
            return ApiResponse.prompt(AppCode.SCHEDULE_NOT_FOUND);
        }
        appScheduleEntity.setTrash(true);
        appScheduleRepo.save(appScheduleEntity);
        removeScheduleAtCache(id);
        return ApiResponse.prompt(AppCode.SC_OK);
    }
}
