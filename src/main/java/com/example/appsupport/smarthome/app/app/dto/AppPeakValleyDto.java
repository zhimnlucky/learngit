package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by niuGuangzhe on 2017/8/1.
 */
@ApiModel(value = "app设置波峰波谷信息")
public class AppPeakValleyDto {

    @NotNull
    @ApiModelProperty(value = "设备主键")
    private String deviceId;

    @NotNull
    @Min(value = 0,message = "波峰开始小时传入有误，最小为0")
    @Max(value = 23,message = "波峰开始小时传入有误，最大为23")
    @ApiModelProperty(value = "波峰开始小时")
    private Integer peakStartHour;

    @NotNull
    @Min(value = 0,message = "波峰开始分钟传入有误，最小为0")
    @Max(value = 59,message = "波峰开始分钟传入有误，最大为59")
    @ApiModelProperty(value = "波峰开始分钟")
    private Integer peakStartMinute;

    @NotNull
    @Min(value = 0,message = "波峰结束小时传入有误，最小为0")
    @Max(value = 23,message = "波峰结束小时传入有误，最小为23")
    @ApiModelProperty(value = "波峰结束小时")
    private Integer peakEndHour;

    @NotNull
    @Min(value = 0,message = "波峰结束分钟传入有误，最小为0")
    @Max(value = 59,message = "波峰结束分钟传入有误，最大为59")
    @ApiModelProperty(value = "波峰结束分钟")
    private Integer peakEndMinute;

    @NotNull
    @Min(value = 0,message = "波谷开始小时传入有误，最小为0")
    @Max(value = 23,message = "波谷开始小时传入有误，最大为23")
    @ApiModelProperty(value = "波谷开始小时")
    private Integer valleyStartHour;

    @NotNull
    @Min(value = 0,message = "波谷开始分钟传入有误，最小为0")
    @Max(value = 59,message = "波谷开始分钟传入有误，最大为59")
    @ApiModelProperty(value = "波谷开始分钟")
    private Integer valleyStartMinute;

    @NotNull
    @Min(value = 0,message = "波谷结束小时传入有误，最小为0")
    @Max(value = 23,message = "波谷结束小时传入有误，最大为23")
    @ApiModelProperty(value = "波谷结束小时")
    private Integer valleyEndHour;

    @NotNull
    @Min(value = 0,message = "波谷结束分钟传入有误，最小为0")
    @Max(value = 59,message = "波谷结束分钟传入有误，最大为59")
    @ApiModelProperty(value = "波谷结束分钟")
    private Integer valleyEndMinute;

    @ApiModelProperty(value = "是否开启设备波峰波谷统计")
    @NotNull
    private Boolean on;


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
