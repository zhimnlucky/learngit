package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.AppPushLimitDto;
import com.auxgroup.bridge.app.inner.vo.AppPushLimitVo;
import com.auxgroup.smarthome.app.service.inner.AppPushLimitInnerService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;

public class AppPushLimitInnerServiceFallback implements AppPushLimitInnerService {
    @Override
    public ApiResponse<AppPushLimitVo> setSilenceTime(AppPushLimitDto appPushLimitDto) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<AppPushLimitVo> getInfo(String uid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }
}
