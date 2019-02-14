package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.DeviceRepairDto;
import com.auxgroup.smarthome.app.service.DeviceRepairInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 设备故障调用inner接口
 * Created by lixiaoxiao on 17-8-9.
 */
@FeignClient(name="INNER-SERVICE", fallback = DeviceRepairInnerServiceFallback.class)
public interface DeviceRepairInnerService {
    @RequestMapping(value = "/inner/device_repair", method = RequestMethod.POST)
    ApiResponse addDeviceFault(DeviceRepairDto deviceRepairDto);
}
