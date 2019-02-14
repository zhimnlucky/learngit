package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.dto.AppUserDto;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.smarthome.app.service.inner.AppUserInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by kevinchen on 2017/7/24.
 */
@Component
public class AppUserInnerServiceFallback implements AppUserInnerService {

    @Override
    public ApiResponse<List<AppUserVo>> findOtherUser(String openid, String qqid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppUserVo> getAppUserByPhone(String phone) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppUserVo>> getAllUserList(String keywords) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<Boolean> isExistPhone(String phone) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<Boolean> isBindPhone(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppUserVo> createAppUser(@RequestParam("uid") String uid, @RequestParam("phone") String phone) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppUserVo> createOtherUser(String uid, String openid, String unionid, String nickName, String headImg, String src) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse updateAppUser(@RequestParam("uid") String uid, @RequestBody AppUserDto appUserDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppUserVo> getAppUser(@RequestParam("uid") String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse recordLoginInfo(@RequestParam("uid") String uid, @RequestParam("clientId") String clientId, @RequestParam("ip") String ip) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse bindOtherAccount(String uid, String openid, String qqid, String phone) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
