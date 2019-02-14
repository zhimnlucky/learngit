package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.*;
import com.auxgroup.smarthome.app.service.inner.DeviceShareInnerService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/7/29 9:09.
 */
@Component
public class DeviceShareServiceInnerFallback implements DeviceShareInnerService {


    @Override
    public ApiResponse<ShareResultVo> createSharing(String uid, String deviceIds, Integer userTag) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse relieveSharing(String appid, String token, String shareId) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<List<ShareUserListVo>> getSharing(String shareUid, String deviceId) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<List<SubShareUserVo>> getSubShareUserList(String shareUid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse scanSharing(String appid, String token, String uid, String qrContent) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<List<FamilyShareListVo>> getFamilySharing(String uid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<List<FamilyShareListVo>> getFriendSharing(String uid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse<List<FamilyCenterDeviceVo>> getUserShareDeviceList(String pUid, String uid, Integer userTag, String batchNo) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse relieveUserSharingByOwner(String appid, String token, String pUid, String uid, Integer userTag, String batchNo, String deviceId) {
        return ApiResponse.prompt(AppCode.FAIL);
    }
}
