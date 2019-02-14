package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.service.inner.AppOtherFeatureInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;

/**
 * Created by lixiaoxiao on 17-9-22.
 */
public class AppOtherFeatureInnerServiceFallback implements AppOtherFeatureInnerService {

    @Override
    public ApiResponse feedbackLog(String json_log) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
