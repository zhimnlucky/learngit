package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "场景设备动作返回实体")
public class DeviceActionVo {

    @ApiModelProperty(value = "设备动作id")
    private String actionId;

    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备did")
    private String did;

    @ApiModelProperty(value = "设备productKey")
    private String productKey;

    @ApiModelProperty(value = "空调类型 0：家用 1：商用")
    private Integer suitType;

    @ApiModelProperty(value = "'多联机标识")
    private Integer dst;

    @ApiModelProperty(value = "开关机 0：关机 1：开机")
    private Integer onOff;

    @ApiModelProperty(value = "模式  0:自动模式  1:制冷模式  2:除湿模式 4:制热模式  6:送风模式")
    private String mode;

    @ApiModelProperty(value = "温度(16-32度)")
    private String temperature;

    @ApiModelProperty(value = "显示状态  0：不显示  1：显示")
    private Integer display = 1;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Integer getSuitType() {
        return suitType;
    }

    public void setSuitType(Integer suitType) {
        this.suitType = suitType;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }



    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
