package com.example.appsupport.smarthome.app.app.entity;

import com.auxgroup.smarthome.entity.AbstractGenericEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 设备睡眠表DIY实体类
 * Created by niuGuangzhe on 2017/7/26.
 */
@Entity
@Table(name = "app_sleep_diy")
public class AppSleepDiyEntity extends AbstractGenericEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36) NOT NULL COMMENT '睡眠表主键'")
    private String sleepDiyId;

    @Column(columnDefinition = "VARCHAR(36) NOT NULL COMMENT '设备ID'")
    private String deviceId;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL COMMENT '睡眠表名称'")
    private String name;

    @Column(columnDefinition = "INTEGER NOT NULL COMMENT '制冷制热 0：制热 1：制冷'")
    private Integer mode;

    @Column(columnDefinition = "INTEGER NOT NULL COMMENT '定时时间设定，单位：小时'")
    private Integer definiteTime;

    @Column(columnDefinition = "INTEGER COMMENT '开始小时'")
    private Integer startHour;

    @Column(columnDefinition = "INTEGER COMMENT '开始分钟'")
    private Integer startMinute;

    @Column(columnDefinition = "INTEGER COMMENT '结束小时'")
    private Integer endHour;

    @Column(columnDefinition = "INTEGER COMMENT '结束分钟'")
    private Integer endMinute;

    @Column(columnDefinition = "INTEGER NOT NULL COMMENT '风速标识 0：低风 1：中风 2：高风 3：静音 4：自动'")
    private Integer windSpeed;

    @Column(name = "is_smart", columnDefinition = "BIT NOT NULL COMMENT '是否智能模式'")
    private Boolean smart;

    @Lob
    @Column(columnDefinition = "TEXT NOT NULL COMMENT '下发电控指令'")
    private String electricControl;

    @Column(columnDefinition = "INTEGER NOT NULL DEFAULT 1 COMMENT '厂商标识 1：机智云 0：古北'")
    private Integer deviceManufacturer;

    @Column(name = "is_on", columnDefinition = "BIT NOT NULL DEFAULT 0 COMMENT '睡眠表开关,0关闭 1开启'")
    private Boolean on;

    @Column(name = "is_enable", columnDefinition = "BIT NOT NULL DEFAULT 1 COMMENT '当前是否可用 0：不可用，1：可用'")
    private Boolean enable = true ;


    public String getSleepDiyId() {
        return sleepDiyId;
    }

    public void setSleepDiyId(String sleepDiyId) {
        this.sleepDiyId = sleepDiyId;
    }

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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getElectricControl() {
        return electricControl;
    }

    public void setElectricControl(String electricControl) {
        this.electricControl = electricControl;
    }

    public Integer getDefiniteTime() {
        return definiteTime;
    }

    public void setDefiniteTime(Integer definiteTime) {
        this.definiteTime = definiteTime;
    }

    public Integer getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(Integer deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

}
