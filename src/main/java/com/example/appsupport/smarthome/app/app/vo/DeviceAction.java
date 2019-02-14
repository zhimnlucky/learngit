package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "场景设备动作实体")
public class DeviceAction {

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    private String deviceName;

    private String did;

    private String productKey;

    //空调类型 0：家用 1：商用
    private Integer suitType;

    @ApiModelProperty(value = "多联机标识")
    private Integer dst;

    // 开关机  0：关机 1：开机
    @ApiModelProperty(value = "开关机 0：关机 1：开机")
    private String onOff;

    //模式  0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式
    @ApiModelProperty(value = "模式  0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式")
    private String mode;

    //温度
    @ApiModelProperty(value = "温度(16-32度)")
    private String temperature;

    // 风速  0：低风1：中风2：高风3：静音4：自动5：强力
    private String windSpeed;

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

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
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

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

    public Integer getSuitType() {
        return suitType;
    }

    public void setSuitType(Integer suitType) {
        this.suitType = suitType;
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
}
