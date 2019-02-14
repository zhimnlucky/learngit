package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class AppScenePartVo {

    @ApiModelProperty(value = "手动场景列表")
    private List<AppSceneVo> manualList;

    @ApiModelProperty(value = "自动场景列表")
    private List<AppSceneVo> autoList;

    public List<AppSceneVo> getManualList() {
        return manualList;
    }

    public void setManualList(List<AppSceneVo> manualList) {
        this.manualList = manualList;
    }

    public List<AppSceneVo> getAutoList() {
        return autoList;
    }

    public void setAutoList(List<AppSceneVo> autoList) {
        this.autoList = autoList;
    }

    public AppScenePartVo() {
        manualList = new ArrayList<>();
        autoList = new ArrayList<>();
    }
}
