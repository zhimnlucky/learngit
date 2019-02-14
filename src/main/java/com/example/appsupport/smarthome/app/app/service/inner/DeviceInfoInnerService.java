package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.DeviceInfoWithTokenDto;
import com.auxgroup.bridge.app.inner.vo.AppDeviceInfoListVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.bridge.app.inner.vo.DeviceInfoVo;
import com.auxgroup.smarthome.app.service.DeviceInfoInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fju on 2017/7/26.
 */
@FeignClient(name="INNER-SERVICE", fallback = DeviceInfoInnerServiceFallback.class)
public interface DeviceInfoInnerService {

    @RequestMapping(value = "/inner/device_bindings", method = RequestMethod.POST)
    ApiResponse<DeviceInfoNewVo> bindDevice(@RequestBody DeviceInfoWithTokenDto deviceInfoWithTokenDto);

    @RequestMapping(value = "/inner/device_bindings_new", method = RequestMethod.POST)
    ApiResponse<DeviceInfoNewVo> bindDeviceNew(@RequestBody DeviceInfoWithTokenDto deviceInfoWithTokenDto);

    @RequestMapping(value = "/inner/device_bindings", method = RequestMethod.DELETE)
    ApiResponse unBindDevice(@RequestParam("userId") String userId, @RequestParam("appId") String appId, @RequestParam("token") String token, @RequestParam("deviceId") String deviceId);

    @GetMapping(value = "/inner/device_bindings/{uid}")
    ApiResponse<List<AppDeviceInfoListVo>> getAppDeviceInfoList(@PathVariable("uid") String uid);

    @RequestMapping(value = "/inner/device_exist", method = RequestMethod.GET)
    ApiResponse<Boolean> exist(@RequestParam("deviceId") String deviceId);

    @RequestMapping(value = "/inner/device_id", method = RequestMethod.GET)
    ApiResponse<String> getMappedDeviceId(@RequestParam("did") String did);

    @RequestMapping(value = "/inner/device_did", method = RequestMethod.GET)
    ApiResponse<String> getMappedDid(@RequestParam("deviceId") String deviceId);

    @RequestMapping(value = "/inner/device_manufacturer",method = RequestMethod.GET)
    ApiResponse<Integer> getDeviceManufacturers(@RequestParam("deviceId") String deviceId);

    @RequestMapping(value = "/inner/device/{mac}",method = RequestMethod.PUT)
    ApiResponse updateDeviceInfo(@PathVariable("mac") String mac, @RequestParam(name = "sn", required = false) String sn, @RequestParam(name = "alias", required = false) String alias, @RequestParam("uid") String uid);

    @RequestMapping(value = "/inner/device_info/{deviceId}", method = RequestMethod.GET)
    ApiResponse<DeviceInfoVo> getDeviceInfo(@PathVariable("deviceId") String deviceId);

    @RequestMapping(value = "/inner/device_new_info/{deviceId}",method = RequestMethod.GET)
    ApiResponse<DeviceInfoNewVo> getDeviceNewInfoByDeviceId(@PathVariable("deviceId") String deviceId);

    @RequestMapping(value = "/inner/device_new_did_info/{did}",method = RequestMethod.GET)
    ApiResponse<DeviceInfoNewVo> getDeviceNewInfoByDid(@PathVariable("did") String did);
}
