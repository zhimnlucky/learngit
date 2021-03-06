package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * Created by fju on 2017/8/14.
 */
@ApiModel
public class AppScheduleDto {

    @NotBlank(message = "设备Id不能为空")
    @Size(max=36)
    @ApiModelProperty(value = "设备Id")
    private String deviceId;

    @NotNull(message = "小时不能为空")
    @Range(min=0, max = 23, message = "小时时间设置范围只能0-23")
    @ApiModelProperty(value = "小时设置范围0-23")
    private int hourSetting;

    @NotNull(message = "分钟不能为空")
    @Range(min=0, max = 59, message = "分钟时间设置范围只能0-59")
    @ApiModelProperty(value = "分钟设置范围0-59")
    private int minuteSetting;

    @NotNull(message = "设备操作不能为空")
    @Range(min = 0,max = 1,message = "操作,0:关机 1:开机")
    @ApiModelProperty(value = "操作,0:关机 1:开机")
    private Integer deviceOperate;//0：关机 1：开机

    @NotNull
    @Range(min = 0,max = 4,message = "模式,0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式")
    @ApiModelProperty(value = "模式,0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式")
    private Integer mode;//0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式

    @NotNull
    @DecimalMax(value = "32" ,message = "温度最大设定32")
    @DecimalMin(value = "16" ,message = "温度最小设定16")
    @ApiModelProperty(value = "温度设置,范围16～32")
    private Float temperatureSetting;//范围16～32

    @ApiModelProperty("设备地址：取值1-64")
    private Integer dst;

    @NotNull
    @Range(min = 0,max = 5,message = "风速,0:低风 1:中风 2:高风 3:静音 4:自动 5:强力")
    @ApiModelProperty(value = "风速,0:低风 1:中风 2:高风 3:静音 4:自动 5:强力")
    private Integer windSpeed;//0：低风1：中风2：高风3：静音4：自动5：强力

    @NotBlank(message = "重复规则不能为空")
    @Pattern(regexp = "^(?!.*(^|,)([^,]*),.*\\2)[1-7]+(,[1-7]+)+$|[1-7]",message = "重复规则不合法")
    @ApiModelProperty(value = "重复规则")
    private String repeatRule;//循环规则 周一到周日之间7天可以选择多天执行，多天用英文逗号隔开

    @ApiModelProperty(value = "是否开启")
    private boolean on;

    public AppScheduleDto() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getHourSetting() {
        return hourSetting;
    }

    public void setHourSetting(int hourSetting) {
        this.hourSetting = hourSetting;
    }

    public int getMinuteSetting() {
        return minuteSetting;
    }

    public void setMinuteSetting(int minuteSetting) {
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

    public Float getTemperatureSetting() {
        return temperatureSetting;
    }

    public void setTemperatureSetting(Float temperatureSetting) {
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

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
