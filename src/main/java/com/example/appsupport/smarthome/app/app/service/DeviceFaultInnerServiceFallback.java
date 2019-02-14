package com.example.appsupport.smarthome.app.app.service;


import com.auxgroup.bridge.app.inner.vo.DeviceFaultVo;
import com.auxgroup.smarthome.app.service.inner.DeviceFaultInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Created by lixiaoxiao on 2017/8/10.
 */
@Component
public class DeviceFaultInnerServiceFallback implements DeviceFaultInnerService {

    @Override
    public ApiResponse getAppDeviceFaultList(String mac) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<DeviceFaultVo>> findAppDeviceHisFaultList(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
