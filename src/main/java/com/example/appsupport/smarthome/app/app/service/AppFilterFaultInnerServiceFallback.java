package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.AppFilterFaultDto;
import com.auxgroup.bridge.app.inner.vo.AppFilterFaultVo;
import com.auxgroup.smarthome.app.service.inner.AppFilterFaultInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;

/**
 * Created by lixiaoxiao on 17-9-22.
 */
public class AppFilterFaultInnerServiceFallback implements AppFilterFaultInnerService {
    @Override
    public ApiResponse confirmFilterWashed(AppFilterFaultDto appFilterFaultDto,String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppFilterFaultVo> getFilterFault(String mac) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
