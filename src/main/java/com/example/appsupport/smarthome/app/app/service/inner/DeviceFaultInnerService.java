package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.vo.DeviceFaultVo;
import com.auxgroup.smarthome.app.service.DeviceFaultInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 设备故障调用inner接口
 * Created by lixiaoxiao on 17-8-9.
 */
@FeignClient(name="INNER-SERVICE", fallback = DeviceFaultInnerServiceFallback.class)
public interface DeviceFaultInnerService {

    @RequestMapping(value = "/inner/devices/faults", method = RequestMethod.GET)
    ApiResponse<List<DeviceFaultVo>> getAppDeviceFaultList(@RequestParam("mac") String mac);

    @RequestMapping(value = "/inner/devices/faultsHis", method = RequestMethod.GET)
    ApiResponse<List<DeviceFaultVo>> findAppDeviceHisFaultList(@RequestParam("uid") String uid);
}
