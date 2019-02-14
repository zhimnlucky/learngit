package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel(value = "设备动作请求实体")
public class DeviceActionDto {

    @ApiModelProperty(value = "设备动作id")
    private String actionId;

    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id", required = true)
    private String deviceId;

    @ApiModelProperty(value = "多联机标识")
    private Integer dst;

    @NotNull(message = "开关机不能为空")
    @ApiModelProperty(value = "开关机 0：关机 1：开机", required = true)
    private Integer onOff;

    @ApiModelProperty(value = "模式  0:自动模式  1:制冷模式  2:除湿模式 4:制热模式  6:送风模式")
    private String mode;

    @ApiModelProperty(value = "温度(16-32度)")
    private String temperature;

//    @ApiModelProperty(value = "设备名称")
//    private String deviceName;
//
//    @ApiModelProperty(value="设备did")
//    private String did;
//
//    @ApiModelProperty(value="设备pk")
//    private String productKey;
//
//    @ApiModelProperty(value="家用商用标识")
//    private Integer suitType;

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

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }


    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
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

//    public void setDeviceName(String deviceName) {
//        this.deviceName = deviceName;
//    }
//
//    public String getDeviceName() {
//        return deviceName;
//    }
//
//    public void setDid(String did) {
//        this.did = did;
//    }
//
//    public String getDid() {
//        return did;
//    }
//
//    public void setProductKey(String productKey) {
//        this.productKey = productKey;
//    }
//
//    public String getProductKey() {
//        return productKey;
//    }
//
//    public void setSuitType(Integer suitType) {
//        this.suitType = suitType;
//    }
//
//    public Integer getSuitType() {
//        return suitType;
//    }
}
