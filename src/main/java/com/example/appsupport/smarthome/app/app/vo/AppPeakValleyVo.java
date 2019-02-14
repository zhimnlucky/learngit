package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by niuGuangzhe on 2017/8/1.
 */
@ApiModel(value = "设备波峰波谷表")
public class AppPeakValleyVo {

    @ApiModelProperty("波峰波谷表主键")
    private String peakValleyId;

    @ApiModelProperty("设备ID主键")
    private String deviceId;

    @ApiModelProperty(value = "波峰开始小时")
    private Integer peakStartHour;

    @ApiModelProperty(value = "波峰开始分钟")
    private Integer peakStartMinute;

    @ApiModelProperty(value = "波峰结束小时")
    private Integer peakEndHour;

    @ApiModelProperty(value = "波峰结束分钟")
    private Integer peakEndMinute;

    @ApiModelProperty(value = "波谷开始小时")
    private Integer valleyStartHour;

    @ApiModelProperty(value = "波谷开始分钟")
    private Integer valleyStartMinute;

    @ApiModelProperty(value = "波谷结束小时")
    private Integer valleyEndHour;

    @ApiModelProperty(value = "波谷结束分钟")
    private Integer valleyEndMinute;

    @ApiModelProperty("app是否开启波峰波谷功能 0:未开启 1：开启")
    private Boolean on;

    public String getPeakValleyId() {
        return peakValleyId;
    }

    public void setPeakValleyId(String peakValleyId) {
        this.peakValleyId = peakValleyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPeakStartHour() {
        return peakStartHour;
    }

    public void setPeakStartHour(Integer peakStartHour) {
        this.peakStartHour = peakStartHour;
    }

    public Integer getPeakStartMinute() {
        return peakStartMinute;
    }

    public void setPeakStartMinute(Integer peakStartMinute) {
        this.peakStartMinute = peakStartMinute;
    }

    public Integer getPeakEndHour() {
        return peakEndHour;
    }

    public void setPeakEndHour(Integer peakEndHour) {
        this.peakEndHour = peakEndHour;
    }

    public Integer getPeakEndMinute() {
        return peakEndMinute;
    }

    public void setPeakEndMinute(Integer peakEndMinute) {
        this.peakEndMinute = peakEndMinute;
    }

    public Integer getValleyStartHour() {
        return valleyStartHour;
    }

    public void setValleyStartHour(Integer valleyStartHour) {
        this.valleyStartHour = valleyStartHour;
    }

    public Integer getValleyStartMinute() {
        return valleyStartMinute;
    }

    public void setValleyStartMinute(Integer valleyStartMinute) {
        this.valleyStartMinute = valleyStartMinute;
    }

    public Integer getValleyEndHour() {
        return valleyEndHour;
    }

    public void setValleyEndHour(Integer valleyEndHour) {
        this.valleyEndHour = valleyEndHour;
    }

    public Integer getValleyEndMinute() {
        return valleyEndMinute;
    }

    public void setValleyEndMinute(Integer valleyEndMinute) {
        this.valleyEndMinute = valleyEndMinute;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }
}
