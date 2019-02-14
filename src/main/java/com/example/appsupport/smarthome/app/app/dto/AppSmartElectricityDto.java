package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by fju on 2017/8/1.
 */
@ApiModel(value = "智能用电规则")
public class AppSmartElectricityDto {

    @NotBlank(message = "设备id不能为空")
    @Size(max = 36,message = "deviceId max length 36")
    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "mac地址")
    private String mac;

    @Range(min = 0, max = 23, message = "开始小时取值范围为0-23")
    @ApiModelProperty(value = "开始小时，取值0-23")
    private int startHour;

    @Range(min = 0, max = 59, message = "开始分钟取值范围为0-59")
    @ApiModelProperty(value = "开始分钟，取值0-59")
    private int startMinute;

    @Range(min = 0, max = 23, message = "结束小时取值范围为0-23")
    @ApiModelProperty(value = "结束小时，取值0-23")
    private int endHour;

    @Range(min = 0, max = 59, message = "结束分钟取值范围为0-59")
    @ApiModelProperty(value = "结束分钟，取值0-59")
    private int endMinute;

    @Min(value = 1,message = "期望用电最小值为1")
    @ApiModelProperty(value = "期望用电")
    private int totalElec;

    @Range(min = 0, max = 2, message = "设备模式0：极速模式1：均衡模式 2：标准模式" )
    @ApiModelProperty(value = "设备模式 0：极速模式1：均衡模式 2：标准模式")
    private int mode;//设备模式

    @Min(value = 0,message = "执行周期 0：每天执行 1：执行一次")
    @Max(value = 1,message = "执行周期 0：每天执行 1：执行一次")
    @ApiModelProperty(value = "执行周期 0：每天执行 1：执行一次")
    private Integer executeCycle;//执行周期

    public AppSmartElectricityDto() {
    }

    public AppSmartElectricityDto(String deviceId, String mac, int startHour, int startMinute, int endHour, int endMinute, Integer totalElec, Integer mode, Integer executeCycle) {
        this.deviceId = deviceId;
        this.mac = mac;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.totalElec = totalElec;
        this.mode = mode;
        this.executeCycle = executeCycle;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setTotalElec(int totalElec) {
        this.totalElec = totalElec;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public Integer getTotalElec() {
        return totalElec;
    }

    public void setTotalElec(Integer totalElec) {
        this.totalElec = totalElec;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getExecuteCycle() {
        return executeCycle;
    }

    public void setExecuteCycle(Integer executeCycle) {
        this.executeCycle = executeCycle;
    }
}
