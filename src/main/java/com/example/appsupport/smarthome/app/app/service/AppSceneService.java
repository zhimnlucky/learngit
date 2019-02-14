package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.dto.AppSceneDto;
import com.auxgroup.smarthome.app.service.inner.AppSceneInnerService;
import com.auxgroup.smarthome.app.vo.AppSceneDetailVo;
import com.auxgroup.smarthome.app.vo.AppScenePartVo;
import com.auxgroup.smarthome.app.vo.AppSceneVo;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSceneService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppSceneInnerService appSceneInnerService;


    /**
     * 创建场景
     */
    public ApiResponse<AppSceneVo> sceneAdd(AppSceneDto appSceneDto) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        appSceneDto.setUid(uid);
        return appSceneInnerService.sceneAdd(appSceneDto);
    }


    /**
     * 编辑场景
     */
    public ApiResponse<AppSceneVo> sceneUpdate(String sceneId, AppSceneDto appSceneDto) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        appSceneDto.setUid(uid);
        return appSceneInnerService.sceneUpdate(sceneId, appSceneDto);
    }


    /**
     * 删除场景
     */
    public ApiResponse sceneDelete(String sceneId) {
        return appSceneInnerService.sceneDelete(sceneId);
    }


    /**
     * 开关场景
     */
    public ApiResponse<AppSceneVo> onOff(String sceneId, int state) {
        return appSceneInnerService.onOff(sceneId, state);
    }


    /**
     * 修改场景名称
     */
    public ApiResponse<AppSceneVo> rename(String sceneId, String sceneName) {
        return appSceneInnerService.rename(sceneId, sceneName);
    }


    /**
     * 查询场景详情
     */
    public ApiResponse<AppSceneDetailVo> sceneDetail(String sceneId) {
        return appSceneInnerService.sceneDetail(sceneId);
    }


    /**
     * 用户场景搜索
     */
    public ApiResponse<List<AppSceneVo>> owner(String key) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appSceneInnerService.owner(key, uid);
    }


    /**
     * 用户场景搜索（区分手动自动）
     */
    public ApiResponse<AppScenePartVo> ownerPart(String key) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appSceneInnerService.ownerPart(key, uid);
    }


    /**
     * 用户首页场景搜索
     */
    public ApiResponse<List<AppSceneVo>> homeSceneQuery() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appSceneInnerService.homeSceneQuery(uid);
    }


    /**
     * 用户预制场景搜索
     */
    public ApiResponse<List<AppSceneVo>> precutSceneQuery() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appSceneInnerService.precutSceneQuery(uid);
    }

    /**
     * 获取开启的位置场景列表
     *
     * @return
     */
    public ApiResponse<List<AppSceneVo>> openPlaceQuery() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return appSceneInnerService.openPlaceQuery(uid);
    }

    /**
     * 执行手动场景
     */
    public ApiResponse sceneHandle(String sceneId) {
        return appSceneInnerService.sceneHandle(sceneId);
    }

    /**
     * 执行位置场景
     */
    public ApiResponse scenePlace(String sceneId) {
        return appSceneInnerService.scenePlace(sceneId);
    }


    /**
     * init
     */
    public ApiResponse initScene() {
        return appSceneInnerService.initScene();
    }

}
