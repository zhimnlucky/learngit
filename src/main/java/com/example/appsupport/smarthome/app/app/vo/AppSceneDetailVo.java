package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "场景详情返回实体")
public class AppSceneDetailVo {

    @ApiModelProperty(value = "场景Id")
    private String sceneId;

    @ApiModelProperty(value = "场景名称")
    private String sceneName;

    @ApiModelProperty(value = "设备动作")
    private List<DeviceActionVo> deviceActionVoList;

    @ApiModelProperty(value = "场景类型1:位置2:时间3:手动")
    private Integer sceneType;

    @ApiModelProperty(value = "用户id")
    private String uid;

    @ApiModelProperty(value = "是否首页标记")
    private Boolean homePageFlag;

    @ApiModelProperty(value = "开启状态 1开启 2关闭")
    private Integer state;

    @ApiModelProperty(value = "循环规则，周一到周日之间7天可以选择多天执行，多天用英文逗号隔开")
    private String repeatRule;

    //定时字段
    @ApiModelProperty(value = "定时字段(执行时间,00:00)")
    private String actionTime;

    //位置字段
    @ApiModelProperty(value = "位置字段(执行类型 1离开 2进入)")
    private Integer actionType;

////    private Integer timeDelay;

    //位置字段
    @ApiModelProperty(value = "位置字段(执行距离)")
    private Integer distance;

    //位置字段
    @ApiModelProperty(value = "位置字段(目标方位 纬度,经度 例:28.116,112.43)")
    private String location;

    @ApiModelProperty(value = "位置字段(详细地址)")
    private String address;

    //位置字段
    @ApiModelProperty(value = "位置字段(有效时间,00:00-00:00)")
    private String effectiveTime;

    @ApiModelProperty(value = "场景执行描述")
    private String actionDescription;

    @ApiModelProperty(value = "初始化标志   0手动生成   1初始化生成  2初始化生成已被设置")
    private Integer initFlag;

    @ApiModelProperty(value = "是否存在有效设备动作标志 true存在 false不存在")
    private Boolean existdevice = true;

    @ApiModelProperty(value = "上次执行的时间戳(秒)")
    private Long lastRuntime;

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    public Boolean getHomePageFlag() {
        return homePageFlag;
    }

    public void setHomePageFlag(Boolean homePageFlag) {
        this.homePageFlag = homePageFlag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRepeatRule() {
        return repeatRule;
    }

    public void setRepeatRule(String repeatRule) {
        this.repeatRule = repeatRule;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Integer getInitFlag() {
        return initFlag;
    }

    public void setInitFlag(Integer initFlag) {
        this.initFlag = initFlag;
    }


    public List<DeviceActionVo> getDeviceActionVoList() {
        return deviceActionVoList;
    }

    public void setDeviceActionVoList(List<DeviceActionVo> deviceActionVoList) {
        this.deviceActionVoList = deviceActionVoList;
    }

    public Boolean getExistdevice() {
        return existdevice;
    }

    public void setExistdevice(Boolean existdevice) {
        this.existdevice = existdevice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLastRuntime() {
        return lastRuntime;
    }

    public void setLastRuntime(Long lastRuntime) {
        this.lastRuntime = lastRuntime;
    }
}
