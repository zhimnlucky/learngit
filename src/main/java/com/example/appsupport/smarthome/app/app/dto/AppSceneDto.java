package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "场景请求实体")
public class AppSceneDto {

//    @NotBlank(message = "场景名称不能为空")
    @ApiModelProperty(value = "场景名称", required = true)
    private String sceneName;

    @ApiModelProperty(value = "设备动作", required = true)
    private List<DeviceActionDto> deviceActionDtoList;

//    @NotNull(message = "场景类型不能为空")
//    @Range(min = 1, max = 3, message = "场景类型1:位置2:时间3:手动 请输入正确的场景类型")
    @ApiModelProperty(value = "场景类型1:位置2:时间3:手动", required = true)
    private Integer sceneType;

    //    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private String uid;

//    @NotNull(message = "首页标记不能为空")
    @ApiModelProperty(value = "首页标记", required = true)
    private Boolean homePageFlag;

//    @NotNull(message = "开启状态不能为空")
//    @Range(min = 0, max = 1, message = "开启状态 0关闭 1开启,请输入正确的开启状态")
    @ApiModelProperty(value = "开启状态 0关闭 1开启", required = true)
    private Integer state;

    @ApiModelProperty(value = "循环规则，周一到周日之间7天可以选择多天执行，多天用英文逗号隔开,不重复则为空")
    private String repeatRule;

    //定时字段
    @ApiModelProperty(value = "定时字段(执行时间,00:00)")
    private String actionTime;

    //位置字段
//    @Range(min = 1,max = 2,message = "位置字段(执行类型 1离开 2进入) 请输入正确的执行类型")
    @ApiModelProperty(value = "位置字段(执行类型 1离开 2进入)")
    private Integer actionType;

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

    private String actionDescription;

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public List<DeviceActionDto> getDeviceActionDtoList() {
        return deviceActionDtoList;
    }

    public void setDeviceActionDtoList(List<DeviceActionDto> deviceActionDtoList) {
        this.deviceActionDtoList = deviceActionDtoList;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
