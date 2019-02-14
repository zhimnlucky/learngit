package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.service.inner.MultiFunctionListInnerService;
import com.auxgroup.smarthome.syscode.BusinessCode;
import com.auxgroup.smarthome.web.ApiResponse;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/10/17 14:04.
 */
public class MultiFunctionListInnerServiceFallback implements MultiFunctionListInnerService {

    @Override
    public ApiResponse getMultiFunctionList() {
        return ApiResponse.prompt(BusinessCode.REMOTE_CALL_FAIL);
    }
}
