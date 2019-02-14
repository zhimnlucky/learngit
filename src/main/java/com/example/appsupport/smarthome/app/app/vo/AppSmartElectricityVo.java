package com.example.appsupport.smarthome.app.app.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fju on 2017/8/1.
 */
@ApiModel(value = "智能用电规则信息")
public class AppSmartElectricityVo {

    @ApiModelProperty(value = "智能用电规则Id")
    private String smartElecId;

    @ApiModelProperty(value = "设备Id")
    private String deviceId;

    @ApiModelProperty(value = "开始小时，取值0-23")
    private Integer startHour;

    @ApiModelProperty(value = "开始分钟，取值0-59")
    private Integer startMinute;

    @ApiModelProperty(value = "结束小时，取值0-23")
    private Integer endHour;

    @ApiModelProperty(value = "结束分钟，取值0-59")
    private Integer endMinute;

    @ApiModelProperty(value = "期望用电")
    private Integer totalElec;

    @ApiModelProperty(value = "设备模式 0：极速模式1：均衡模式 2：标准模式")
    private Integer mode;//设备模式

    @ApiModelProperty(value = "执行周期 0：每天执行 1：执行一次")
    private Integer executeCycle;//执行周期

    @ApiModelProperty(value = "是否启用,0:关闭 1：启用")
    private Boolean on;//是否已经开启执行

    public AppSmartElectricityVo() {
    }

    public String getSmartElecId() {
        return smartElecId;
    }

    public void setSmartElecId(String smartElecId) {
        this.smartElecId = smartElecId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
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

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }
}
