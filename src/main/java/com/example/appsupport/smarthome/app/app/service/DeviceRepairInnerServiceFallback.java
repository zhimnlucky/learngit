package com.example.appsupport.smarthome.app.app.service;


import com.auxgroup.bridge.app.inner.dto.DeviceRepairDto;
import com.auxgroup.smarthome.app.service.inner.DeviceRepairInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

/**
 * Created by lixiaoxiao on 2017/8/10.
 */
@Component
public class DeviceRepairInnerServiceFallback implements DeviceRepairInnerService {

    @Override
    public ApiResponse addDeviceFault(DeviceRepairDto deviceRepairDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}