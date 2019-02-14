package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.dto.AppPeakValleyDto;
import com.auxgroup.smarthome.app.entity.AppPeakValleyEntity;
import com.auxgroup.smarthome.app.entity.AppPeakValleyRedisEntity;
import com.auxgroup.smarthome.app.entity.AppSmartElectricity;
import com.auxgroup.smarthome.app.repo.AppPeakValleyRepo;
import com.auxgroup.smarthome.app.repo.AppSmartElectricityRepo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.util.StrTime;
import com.auxgroup.smarthome.app.vo.AppPeakValleyVo;
import com.auxgroup.smarthome.app.vo.PowerCurveVo;
import com.auxgroup.smarthome.basebean.DateType;
import com.auxgroup.smarthome.constant.SmartElectricityConsant;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.enterprise.service.DeviceManageService;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.regex.ValidatorUtils;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 峰谷节电相关service
 * Created by niuGuangzhe on 2017/8/1.
 * 峰谷节电  - 旧设备在saas平台不存储数据 saas平台不参与控制         	商用空调没有
 *
 */
@Service
public class AppPeakValleyService {

    @Autowired
    private AppPeakValleyRepo appPeakValleyRepo;

    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;

    @Autowired
    private ObjectRedis objectRedis;

    @Autowired
    private AppSmartElectricityRepo smartElectricityRepo;

    @Autowired
    private AppElectricityStatisticsService electricityStatisticsService;

    @Autowired
    private DeviceManageService deviceManageService;

    /**
     * 根据设备id查找峰谷表信息
     * @param deviceId
     * @return
     */
    public ApiResponse<AppPeakValleyVo> findPeakValleyByDeviceId(String deviceId) {
        AppPeakValleyEntity entity = appPeakValleyRepo.findByDeviceId(deviceId);
        return entity == null ? ApiResponse.ok(new AppPeakValleyVo()) : ApiResponse.ok(BeanUtils.copyAttrs(new AppPeakValleyVo(), entity));
    }

    public ApiResponse<AppPeakValleyVo> findPeakValleyByDeviceIdPrivate(String deviceId) {
        AppPeakValleyEntity entity = appPeakValleyRepo.findByDeviceId(deviceId);
        return entity == null ? ApiResponse.ok(null) : ApiResponse.ok(BeanUtils.copyAttrs(new AppPeakValleyVo(), entity));
    }

    /**
     * 需要校验智能用电是否开启
     * 智能用电功能与峰谷节电功能互斥
     * 新增峰谷设置
     * kevin 2017-9-13 峰谷节电  - 旧设备在saas平台不存储数据 saas平台不参与控制、商用空调没有
     * @param dto
     * @return
     */
    public ApiResponse<AppPeakValleyVo> addPeakValley(AppPeakValleyDto dto) {
        //TODO 校验智能用电是否开启 如果开启就不能编辑
        String deviceId = dto.getDeviceId();
        AppSmartElectricity appSmartElectricity = smartElectricityRepo.findByDeviceId(deviceId);
        if (appSmartElectricity!=null && appSmartElectricity.getOn()){
            return ApiResponse.prompt(AppCode.PEAKVALLEY_CANT_ALLOW);
        }
        AppPeakValleyEntity peakValleyEntity = appPeakValleyRepo.findByDeviceId(dto.getDeviceId());
        if (peakValleyEntity != null) {
            return ApiResponse.prompt(AppCode.PEAKVALLEY_ALEARY_EXIST);
        }
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse = deviceInfoInnerService.getDeviceNewInfoByDeviceId(dto.getDeviceId());
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()){
            return ApiResponse.prompt(deviceInfoNewVoApiResponse.getCode(),deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if (deviceInfoNewVo==null || !deviceInfoNewVo.isNewDevice()){
            return ApiResponse.prompt(AppCode.PEAKVALLEY_ALLOW_DEVICE);
        }
        if (isCoincide(dto.getPeakStartHour(),dto.getPeakEndHour(),
                dto.getValleyStartHour(),dto.getValleyEndHour())) {
            return ApiResponse.prompt(AppCode.PEAKVALLEY_TIME_AREA_CONFLICT);
        }
        AppPeakValleyEntity entity = BeanUtils.copyAttrs(new AppPeakValleyEntity(dto.getDeviceId(),
                deviceInfoNewVo.getProductKey(),deviceInfoNewVo.getDid()), dto);
        entity = appPeakValleyRepo.save(entity);
        peakVallyCacheMgt(entity);
        AppPeakValleyVo vo = BeanUtils.copyAttrs(new AppPeakValleyVo(), entity);
        return ApiResponse.ok(vo);
    }

    /**
     * 修改&开启&关闭峰谷设置
     * @param dto
     * @return
     */
    public ApiResponse updatePeakValley(String peakValleyId, AppPeakValleyDto dto) {
        if (dto.getOn()){
            String deviceId = dto.getDeviceId();
            AppSmartElectricity appSmartElectricity = smartElectricityRepo.findByDeviceId(deviceId);
            if (appSmartElectricity!=null && appSmartElectricity.getOn()){
                return ApiResponse.prompt(AppCode.PEAKVALLEY_CANT_ALLOW);
            }
        }
        if (isCoincide(dto.getPeakStartHour(),dto.getPeakEndHour(),
                dto.getValleyStartHour(),dto.getValleyEndHour())) {
            return ApiResponse.prompt(AppCode.PEAKVALLEY_TIME_AREA_CONFLICT);
        }
        AppPeakValleyEntity entity = appPeakValleyRepo.findByPeakValleyId(peakValleyId);
        if (entity == null) {
            return ApiResponse.prompt(AppCode.PEAKVALLEY_NOT_EXIST);
        }
        entity = BeanUtils.copyAttrs(entity, dto);
        appPeakValleyRepo.save(entity);
        peakVallyCacheMgt(entity);
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    private static final String PEAK_VALLEY_REDIS_KEY = CachedConsant.PEAK_VALLEY_REDIS_KEY_PREFIX;

    /**
     *  管理峰谷时间配置缓存
     *  开启 新增缓存
     *  关闭 移除缓存
     * @param entity
     */
    private void peakVallyCacheMgt(AppPeakValleyEntity entity){
        if (entity.getOn()){
            AppPeakValleyRedisEntity peakValleyRedisEntity =
                    BeanUtils.copyAttrs(new AppPeakValleyRedisEntity(), entity);
            objectRedis.add(PEAK_VALLEY_REDIS_KEY+entity.getDid(),peakValleyRedisEntity);
        }else{
            // 关闭峰谷节电，用电曲线还需要计算，默认时间段为波平
            AppPeakValleyRedisEntity peakValleyRedisEntity = new AppPeakValleyRedisEntity(entity.getPk(), entity.getDid());
            objectRedis.add(PEAK_VALLEY_REDIS_KEY+entity.getDid(), peakValleyRedisEntity);
            deviceManageService.controlPeakValleyAndSmartElectricity(entity.getPk(),
                    entity.getDid(),0,0,SmartElectricityConsant.defaultElectricityValue);
        }
    }

    /**
     * true:重合  false:不重合
     * @param startHour1
     * @param endHour1
     * @param startHour2
     * @param endHour2
     * @return
     */
    private boolean isCoincide(int startHour1,int endHour1,int startHour2,int endHour2){
        StrTime strTime1 = new StrTime(startHour1, 0,endHour1,0);
        StrTime strTime2 = new StrTime(startHour2, 0,endHour2,0);
        return strTime1.isCoincide(strTime2) == 0;
    }

    /**
     * 获取 日、月、年用电曲线
     * @param did
     * @param timeStr 时间格式 DAY:yyyyMMdd、 MONTH:yyyyMM、 YEAR:yyyy  (需要注意时间格式，在调用平台的接口，都是统一用的yyyyMMddHH)
     * @param dateType DAY,MONTH,YEAR
     * @return
     */
    public ApiResponse<PowerCurveVo> getAllPowerCurve(String did,String timeStr,DateType dateType){
        if (dateType==null){
            ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
        }
        ApiResponse<DeviceInfoNewVo> deviceInfoNewVoApiResponse = deviceInfoInnerService.getDeviceNewInfoByDid(did);
        if (!deviceInfoNewVoApiResponse.isNotErrorCode()){
            return ApiResponse.prompt(deviceInfoNewVoApiResponse.getCode(),deviceInfoNewVoApiResponse.getMessage());
        }
        DeviceInfoNewVo deviceInfoNewVo = deviceInfoNewVoApiResponse.getData();
        if (deviceInfoNewVo==null || !deviceInfoNewVo.isNewDevice()){
            return ApiResponse.prompt(AppCode.PEAKVALLEY_ALLOW_DEVICE);
        }
        String productKey = deviceInfoNewVo.getProductKey();
        switch (dateType){
            case DAY:
                if(!ValidatorUtils.isDayStr(timeStr)){
                    ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
                }
                return electricityStatisticsService.getDayPowerCurve(productKey,did, timeStr);
            case MONTH:
                if(!ValidatorUtils.isMonthStr(timeStr)){
                    ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
                }
                return electricityStatisticsService.getMonthPowerCurve(productKey,did,timeStr);
            case YEAR:
                if(!ValidatorUtils.isYearStr(timeStr)){
                    ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
                }
                return electricityStatisticsService.getYearPowerCurve(productKey,did,timeStr);
        }
        return ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
    }



    /**
     * 关闭峰谷节电功能，同时发送电控协议 关闭峰谷节电
     * @param did
     * @return
     */
    public ApiResponse closePeakValley(String did){
        AppPeakValleyRedisEntity appPeakValleyRedisEntity = objectRedis.get(PEAK_VALLEY_REDIS_KEY + did, AppPeakValleyRedisEntity.class);
        if (appPeakValleyRedisEntity==null){
            return ApiResponse.prompt(Syscode.NOT_CONTENT);
        }
        objectRedis.delete(PEAK_VALLEY_REDIS_KEY + did);
        AppPeakValleyEntity peakValleyEntity = appPeakValleyRepo.findByDid(did);
        if (peakValleyEntity == null) {
            return ApiResponse.prompt(Syscode.NOT_CONTENT);
        }
        peakValleyEntity.setOn(false);
        appPeakValleyRepo.save(peakValleyEntity);
        deviceManageService.controlPeakValleyAndSmartElectricity(
        appPeakValleyRedisEntity.getPk(),
        did,
        0,
        0,
        SmartElectricityConsant.defaultElectricityValue);
        return ApiResponse.prompt(Syscode.SC_OK);
    }




}
