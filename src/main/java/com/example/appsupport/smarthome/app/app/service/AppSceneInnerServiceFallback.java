package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.dto.AppSceneDto;
import com.auxgroup.smarthome.app.service.inner.AppSceneInnerService;
import com.auxgroup.smarthome.app.vo.AppSceneDetailVo;
import com.auxgroup.smarthome.app.vo.AppScenePartVo;
import com.auxgroup.smarthome.app.vo.AppSceneVo;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;

import java.util.List;

public class AppSceneInnerServiceFallback implements AppSceneInnerService {
    @Override
    public ApiResponse<AppSceneVo> sceneAdd(AppSceneDto appSceneDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppSceneVo> sceneUpdate(String sceneId, AppSceneDto appSceneDto) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppSceneVo> sceneDelete(String sceneId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppSceneVo> onOff(String sceneId, int state) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppSceneVo> rename(String sceneId, String sceneName) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppSceneDetailVo> sceneDetail(String sceneId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppSceneVo>> owner(String key, String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<AppScenePartVo> ownerPart(String key, String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppSceneVo>> homeSceneQuery(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppSceneVo>> precutSceneQuery(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<AppSceneVo>> openPlaceQuery(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse sceneHandle(String sceneId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse scenePlace(String sceneId) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse isExistPreScene(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse initScene() {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse createUserInitScene(String uid) {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
