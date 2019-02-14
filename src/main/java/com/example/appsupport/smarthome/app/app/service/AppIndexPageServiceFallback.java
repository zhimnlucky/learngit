package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.business.inner.vo.AppIndexPageVo;
import com.auxgroup.smarthome.app.service.inner.AppIndexPageInnerService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

/**
 * Created by niuGuangzhe on 2017/8/3.
 */
@Component
public class AppIndexPageServiceFallback implements AppIndexPageInnerService{
    @Override
    public ApiResponse<AppIndexPageVo> getNewestIndexPage() {
        return ApiResponse.prompt(AppCode.FAIL);
    }
}
