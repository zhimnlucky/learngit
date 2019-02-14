package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.DeviceInfoWithTokenDto;
import com.auxgroup.bridge.app.inner.vo.AppDeviceInfoListVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.bridge.app.inner.vo.DeviceSimpleInfoVo;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.dto.DeviceInfoDto;
import com.auxgroup.smarthome.app.filter.PermissionAppService;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.service.inner.DeviceModelInfoInnerService;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fju on 2017/7/27.
 */
@Service
public class DeviceInfoService {

    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;
    @Autowired
    private PermissionAppService permissionAppService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private DeviceModelInfoInnerService deviceModelInfoInnerService;
    @Autowired
    private AppSmartElectricityService appSmartElectricityService;
    @Autowired
    private ObjectRedis objectRedis;

    public ApiResponse<DeviceSimpleInfoVo> bindDevice(DeviceInfoDto deviceInfoDto) {
        DeviceSimpleInfoVo deviceSimpleInfoVo = new DeviceSimpleInfoVo();
        //--begin modify by lixiaoxiao 因部分型号未录,直接选择通用型号,故不需要此判断
        /*if(StringUtils.isNotEmpty(deviceInfoDto.getSn())) {
            long count = deviceModelInfoInnerService.countSnByModelId(deviceInfoDto.getModelId(), deviceInfoDto.getSn());
            if(count == 0) {
                return ApiResponse.prompt(AppCode.MODEL_ID_AND_SN_NOT_MATCH);
            }
        }*/
        //--end modify by lixiaoxiao 因部分型号未录,直接选择通用型号,故不需要此判断
        if(deviceInfoDto.getSource() == 1 && StringUtils.isBlank(deviceInfoDto.getProductKey())) {
            return ApiResponse.prompt(AppCode.GIZWITS_DEVICE_PRODUCT_KEY_NOT_NULL);
        }
        DeviceInfoWithTokenDto deviceInfoWithTokenDto = BeanUtils.copyAttrs(new DeviceInfoWithTokenDto(),deviceInfoDto);
        deviceInfoWithTokenDto.setToken(appUserService.getUserToken().getToken());
        deviceInfoWithTokenDto.setUserId(appUserService.getUserToken().getUid());
        deviceInfoWithTokenDto.setAppId(permissionAppService.getAppId());
        String nickName = appUserService.getAppUser().getData().getNickName();
        if(nickName == null){
            nickName = appUserService.getAppUser().getData().getPhone();
        }
        deviceInfoWithTokenDto.setUsername(nickName);
        deviceInfoWithTokenDto.setPhone(appUserService.getAppUser().getData().getPhone());
        ApiResponse<DeviceInfoNewVo> apiResponse = deviceInfoInnerService.bindDevice(deviceInfoWithTokenDto);
        if(apiResponse.isNotErrorCode()) {
            DeviceInfoNewVo deviceInfoNewVo = apiResponse.getData();
            deviceSimpleInfoVo.setDeviceId(deviceInfoNewVo.getDeviceId());
            //关闭新设备的智能用电 --modify by wujianlong 2019-01-17
            if(deviceInfoNewVo.getSource()==1)
                appSmartElectricityService.clearSmartElectricityRedis(deviceInfoNewVo.getMac());

            return ApiResponse.ok(deviceSimpleInfoVo);
        }
        return (ApiResponse)apiResponse;
    }

    public ApiResponse<DeviceSimpleInfoVo> bindDeviceNew(DeviceInfoDto deviceInfoDto) {
        DeviceSimpleInfoVo deviceSimpleInfoVo = new DeviceSimpleInfoVo();
        //--begin modify by lixiaoxiao 因部分型号未录,直接选择通用型号,故不需要此判断
        /*if(StringUtils.isNotEmpty(deviceInfoDto.getSn())) {
            long count = deviceModelInfoInnerService.countSnByModelId(deviceInfoDto.getModelId(), deviceInfoDto.getSn());
            if(count == 0) {
                return ApiResponse.prompt(AppCode.MODEL_ID_AND_SN_NOT_MATCH);
            }
        }*/
        //--end modify by lixiaoxiao 因部分型号未录,直接选择通用型号,故不需要此判断
        if(deviceInfoDto.getSource() == 1 && StringUtils.isBlank(deviceInfoDto.getProductKey())) {
            return ApiResponse.prompt(AppCode.GIZWITS_DEVICE_PRODUCT_KEY_NOT_NULL);
        }
        DeviceInfoWithTokenDto deviceInfoWithTokenDto = BeanUtils.copyAttrs(new DeviceInfoWithTokenDto(),deviceInfoDto);
        deviceInfoWithTokenDto.setToken(appUserService.getUserToken().getToken());
        deviceInfoWithTokenDto.setUserId(appUserService.getUserToken().getUid());
        deviceInfoWithTokenDto.setAppId(permissionAppService.getAppId());
        String nickName = appUserService.getAppUser().getData().getNickName();
        if(nickName == null){
            nickName = appUserService.getAppUser().getData().getPhone();
        }
        deviceInfoWithTokenDto.setUsername(nickName);
        deviceInfoWithTokenDto.setPhone(appUserService.getAppUser().getData().getPhone());
        ApiResponse<DeviceInfoNewVo> apiResponse = deviceInfoInnerService.bindDeviceNew(deviceInfoWithTokenDto);
        if(apiResponse.isNotErrorCode()) {
            DeviceInfoNewVo deviceInfoNewVo = apiResponse.getData();
            deviceSimpleInfoVo.setDeviceId(deviceInfoNewVo.getDeviceId());
            //关闭新设备的智能用电 --modify by wujianlong 2019-01-17
            if(deviceInfoNewVo.getSource()==1)
                appSmartElectricityService.clearSmartElectricityRedis(deviceInfoNewVo.getMac());
            return ApiResponse.ok(deviceSimpleInfoVo);
        }
        return (ApiResponse)apiResponse;
    }

    public ApiResponse unBindDevice(String deviceId) {
        ApiResponse apiResponse = deviceInfoInnerService.unBindDevice(appUserService.getUserToken().getUid(),permissionAppService.getAppId(),appUserService.getUserToken().getToken(),deviceId);
        return apiResponse;
    }



    public ApiResponse<List<AppDeviceInfoListVo>> getAppDeviceInfoList() {
        return deviceInfoInnerService.getAppDeviceInfoList(appUserService.getUid());
    }


    public ApiResponse updateDeviceInfo(String mac,String sn,String alias){
        return deviceInfoInnerService.updateDeviceInfo(mac,sn,alias,appUserService.getUserToken().getUid());
    }


}
