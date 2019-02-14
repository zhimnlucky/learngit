package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fju on 2017/8/14.
 */
@ApiModel(value = "定时任务信息")
public class AppScheduleVo {

    @ApiModelProperty(value = "设备Id")
    private String id;
    @ApiModelProperty(value = "设置小时，取值：0-23")
    private Integer hourSetting;
    @ApiModelProperty(value = "设置分钟，取值：0-59")
    private Integer minuteSetting;
    @ApiModelProperty(value = "操作,0:关机 1:开机")
    private Integer deviceOperate;//0：关机 1：开机
    @ApiModelProperty(value = "模式,0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式")
    private Integer mode;//0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式
    @ApiModelProperty(value = "温度设置,范围16～32")
    private Integer temperatureSetting;//范围16～32
    @ApiModelProperty(value = "风速,0:低风 1:中风 2:高风 3:静音 4:自动 5:强力")
    private Integer windSpeed;//0：低风1：中风2：高风3：静音4：自动5：强力
    @ApiModelProperty(value = "循环规则 周一到周日之间7天可以选择多天执行，多天用英文逗号隔开")
    private String repeatRule;//循环规则 周一到周日之间7天可以选择多天执行，多天用英文逗号隔开
    @ApiModelProperty(value = "是否开启")
    private Boolean on;
    @ApiModelProperty(value = "厂商标识 1：机智云 0：古北")
    private Integer deviceManufacturer;
    @ApiModelProperty(value = "多联机，子设备地址，取值：[1,64]")
    private Integer dst;

    public AppScheduleVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHourSetting() {
        return hourSetting;
    }

    public void setHourSetting(Integer hourSetting) {
        this.hourSetting = hourSetting;
    }

    public Integer getMinuteSetting() {
        return minuteSetting;
    }

    public void setMinuteSetting(Integer minuteSetting) {
        this.minuteSetting = minuteSetting;
    }

    public Integer getDeviceOperate() {
        return deviceOperate;
    }

    public void setDeviceOperate(Integer deviceOperate) {
        this.deviceOperate = deviceOperate;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getTemperatureSetting() {
        return temperatureSetting;
    }

    public void setTemperatureSetting(Integer temperatureSetting) {
        this.temperatureSetting = temperatureSetting;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getRepeatRule() {
        return repeatRule;
    }

    public void setRepeatRule(String repeatRule) {
        this.repeatRule = repeatRule;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(Integer deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }
}
