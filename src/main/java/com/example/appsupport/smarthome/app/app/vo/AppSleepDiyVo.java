package com.example.appsupport.smarthome.app.app.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by niuGuangzhe on 2017/7/26.
 */
@ApiModel(value = "睡眠表返回实体")
public class AppSleepDiyVo {

    @ApiModelProperty("睡眠表主键")
    private String sleepDiyId;

    @ApiModelProperty("睡眠表名称")
    private String name;

    @ApiModelProperty("制冷模式制热模式标识0：制冷 1：制热")
    private Integer mode;

    @ApiModelProperty(value = "开始小时")
    private Integer startHour;

    @ApiModelProperty(value = "开始分钟")
    private Integer startMinute;

    @ApiModelProperty(value = "结束小时")
    private Integer endHour;

    @ApiModelProperty(value = "结束分钟")
    private Integer endMinute;


    @ApiModelProperty(value = "兼容古北云的定时时间表示，单位：小时")
    private Integer definiteTime;

    @ApiModelProperty(value = "厂商标识 0:古北 1:机智云 ")
    private Integer deviceManufacturer;

    @ApiModelProperty("风速标识 0:低风 1：中风 2：高风 3：静音 4：自动")
    private Integer windSpeed;


    @ApiModelProperty("是否智能模式标识 1：是 0：否")
    private Boolean smart;

    @ApiModelProperty("要下发的电控指令(json）格式")
    private String electricControl;

    @ApiModelProperty("睡眠表是否开启1：开启 0：关闭")
    private Boolean on;

    @ApiModelProperty("创建时间，10位时间戳")
    private Long createdAt;


    public String getSleepDiyId() {
        return sleepDiyId;
    }

    public void setSleepDiyId(String sleepDiyId) {
        this.sleepDiyId = sleepDiyId;
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

    public Integer getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(Integer deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
