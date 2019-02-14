package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.PushRecordVo;
import com.auxgroup.smarthome.app.service.inner.PushRecordInnerService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;

import java.util.List;

/**
 * Created by lixiaoxiao on 17-10-16.
 */
public class PushRecordInnerServiceFallback implements PushRecordInnerService {

    @Override
    public ApiResponse<List<PushRecordVo>> findAllByUid(String uid, String time) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse savePushRecord(String uid, String body, String title, String sourceType, String imageUrl, String linkedUrl, String sourceValue, String type, String platform) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse findNotReadCountByUid(String uid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse updateReadState(List<String> recordIdList) {
        return ApiResponse.prompt(AppCode.FAIL);
    }

    @Override
    public ApiResponse updateAllReadState(String uid) {
        return ApiResponse.prompt(AppCode.FAIL);
    }
}
