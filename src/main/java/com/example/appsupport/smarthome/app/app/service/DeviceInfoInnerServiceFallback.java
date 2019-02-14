package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.DeviceInfoWithTokenDto;
import com.auxgroup.bridge.app.inner.vo.AppDeviceInfoListVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoVo;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by fju on 2017/7/26.
 */
@Component
public class DeviceInfoInnerServiceFallback implements DeviceInfoInnerService {
    @Override
    public ApiResponse<DeviceInfoNewVo> bindDevice(@RequestBody DeviceInfoWithTokenDto deviceInfoWithTokenDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<DeviceInfoNewVo> bindDeviceNew(DeviceInfoWithTokenDto deviceInfoWithTokenDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse unBindDevice(@RequestParam("userId") String userId, @RequestParam("appId") String appId, @RequestParam("token") String token, @RequestParam("deviceId") String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppDeviceInfoListVo>> getAppDeviceInfoList(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<Boolean> exist(@RequestParam String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<String> getMappedDeviceId(@RequestParam("did") String did) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<String> getMappedDid(@RequestParam("deviceId") String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<Integer> getDeviceManufacturers(String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse updateDeviceInfo(@PathVariable("mac") String mac, @RequestParam(name = "sn", required = false) String sn, @RequestParam(name = "alias", required = false) String alias, @RequestParam("uid") String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<DeviceInfoVo> getDeviceInfo(String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<DeviceInfoNewVo> getDeviceNewInfoByDeviceId(String deviceId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<DeviceInfoNewVo> getDeviceNewInfoByDid(String did) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
