package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.service.inner.AppVersionInnerService;
import com.auxgroup.smarthome.app.vo.AppVersionVo;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class AppVersionInnerServiceFallback implements AppVersionInnerService {
    @Override
    public ApiResponse<AppVersionVo> getCurrentAppVersion() {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
