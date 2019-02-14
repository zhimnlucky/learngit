package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.DeviceConnectFailDto;
import com.auxgroup.smarthome.app.service.DeviceConnectFailInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lixiaoxiao on 17-9-11.
 */
@FeignClient(name="INNER-SERVICE", fallback = DeviceConnectFailInnerServiceFallback.class)
public interface DeviceConnectFailInnerService {

    @RequestMapping(value = "/inner/device_connect_fail", method = RequestMethod.POST)
    ApiResponse saveConnectFail(@RequestBody DeviceConnectFailDto deviceConnectFailDto);
}
