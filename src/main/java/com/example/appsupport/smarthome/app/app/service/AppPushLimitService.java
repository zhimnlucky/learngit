package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.AppPushLimitDto;
import com.auxgroup.bridge.app.inner.vo.AppPushLimitVo;
import com.auxgroup.smarthome.app.service.inner.AppPushLimitInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppPushLimitService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppPushLimitInnerService appPushLimitInnerService;

    public ApiResponse<AppPushLimitVo> setSilenceTime(AppPushLimitDto appPushLimitDto) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        appPushLimitDto.setUid(uid);
        return appPushLimitInnerService.setSilenceTime(appPushLimitDto);
    }

    public ApiResponse<AppPushLimitVo> getInfo() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appPushLimitInnerService.getInfo(uid);
    }
}
