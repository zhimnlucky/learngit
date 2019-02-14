package com.example.appsupport.smarthome.app.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by niuGuangzhe on 2017/7/26.
 */
@ApiModel(value = "app睡眠表请求实体信息")
public class AppSleepDiyDto {

    @ApiModelProperty(value = "设备主键")
    @NotBlank(message = "设备ID不能为空")
    private String deviceId;

    @ApiModelProperty(value = "睡眠表名称")
    @NotBlank(message = "睡眠表名称不能为空")
    @Size(min = 1, max = 50, message = "睡眠表名称长度应该在0-50之间")
    private String name;

    @ApiModelProperty(value = "制冷还是制热模式")
    @NotNull(message = "制冷制热模式必须填写")//制冷制热
    @Range(min = 1,max = 2,message = "模式(1:制冷 2:制热)")
    private Integer mode;

    //兼容古北云设备
    @Min(value = 1,message = "开始小时传入有误，最小为1")
    @Max(value = 12,message = "开始小时传入有误，最大为12")
    @ApiModelProperty(value = "兼容古北云的定时时间表示，单位：小时")
    private Integer definiteTime;

    @Min(value = 0,message = "开始小时传入有误，最小为0")
    @Max(value = 23,message = "开始小时传入有误，最大为23")
    @ApiModelProperty(value = "开始小时")
    private Integer startHour;

    @Min(value = 0,message = "开始分钟传入有误，最小为0")
    @Max(value = 59,message = "开始分钟传入有误，最大为59")
    @ApiModelProperty(value = "开始分钟")
    private Integer startMinute = 0;

    @Min(value = 0,message = "结束小时传入有误，最小为0")
    @Max(value = 23,message = "结束小时传入有误，最大为23")
    @ApiModelProperty(value = "结束小时")
    private Integer endHour = 0;

    @Min(value = 0,message = "结束分钟传入有误，最小为0")
    @Max(value = 59,message = "结束分钟传入有误，最大为59")
    @ApiModelProperty(value = "结束分钟")
    private Integer endMinute = 0;

    @ApiModelProperty(value = "风速标识")
    @NotNull(message = "风速需要填写")
    @Range(min = 0,max = 6,message = "风速(0:低风 1:中风 2:高风 3:静音 4:自动 5:强力 6:自定义)")
    private Integer windSpeed;

    @ApiModelProperty(value = "是否智能模式")
    @NotNull(message = "是否开启智能模式需要填写")
    private Boolean smart;

    @ApiModelProperty(value = "电控指令")
    @NotBlank(message = "电控指令必须填写")
    private String electricControl;

    // 新添加 是否开启睡眠表，用来合并开启关闭接口控制
    @ApiModelProperty(value = "是否开启睡眠表功能")
    @NotNull(message = "是否开启睡眠表必须填写")
    private Boolean on = false;

    @ApiModelProperty(value = "厂商标识 1：机智云 0：古北")
    @Min(value = 0,message = "结束分钟传入有误，最小为0")
    @Max(value = 1,message = "结束分钟传入有误，最大为1")
    private Integer deviceManufacturer;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
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

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getElectricControl() {
        return electricControl;
    }

    public void setElectricControl(String electricControl) {
        this.electricControl = electricControl;
    }

    public Boolean getSmart() {
        return smart;
    }

    public void setSmart(Boolean smart) {
        this.smart = smart;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getDefiniteTime() {
        return definiteTime;
    }

    public void setDefiniteTime(Integer definiteTime) {
        this.definiteTime = definiteTime;
    }

    public void assignEndHour(){
        this.endHour = this.startHour + definiteTime < 24 ? this.startHour + definiteTime
                : definiteTime - (24 - this.startHour);
    }

    public Integer getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(Integer deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }
}
