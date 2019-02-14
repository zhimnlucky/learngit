package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 离家回家模式Dto
 * Created by lixiaoxiao  on 17-8-1.
 */
public class AppHomeDistanceDto {

    @ApiModelProperty(value = "距离主键")
    private String distanceId;

    @NotNull
    @ApiModelProperty(value = "设备主键")
    private String deviceId;

    @ApiModelProperty(value = "模式触发 0:未触发1:离家2:回家")
    private Integer modeLaunch;

    @NotNull
    @ApiModelProperty(value = "设置距离")
    private Integer distance;

    @NotNull
    @ApiModelProperty(value = "开启&关闭")
    private Boolean on;

    public String getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(String distanceId) {
        this.distanceId = distanceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getModeLaunch() {
        return modeLaunch;
    }

    public void setModeLaunch(Integer modeLaunch) {
        this.modeLaunch = modeLaunch;
    }
}
