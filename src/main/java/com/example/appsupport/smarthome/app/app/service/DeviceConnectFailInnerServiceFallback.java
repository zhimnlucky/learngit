package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.DeviceConnectFailDto;
import com.auxgroup.smarthome.app.service.inner.DeviceConnectFailInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

/**
 * Created by lixiaoxiao on 17-9-11.
 */
@Component
public class DeviceConnectFailInnerServiceFallback implements DeviceConnectFailInnerService{
    @Override
    public ApiResponse saveConnectFail(DeviceConnectFailDto deviceConnectFailDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
