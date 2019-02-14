package com.example.appsupport.smarthome.app.app.entity;

import com.auxgroup.smarthome.entity.AbstractGenericEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by fju on 2017/8/14.
 */
@Entity
@Table(name = "app_schedule")
public class AppScheduleEntity extends AbstractGenericEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36,nullable = false)
    private String id;

    @Column(columnDefinition = "varchar(50) NOT NULL COMMENT '兼容新旧设备的deviceId'")
    private String deviceId;
    /**
     * 设备地址：
     *
     * 用于多联机控制
     * 0：广播
     * 1：1 – 255 (兼容485通信，一个地址代表一台内机)
     */
    private Integer dst = 1;

    @Column(columnDefinition = "smallint(3) NOT NULL COMMENT '小时设定：0-24'")
    private Integer hourSetting;

    @Column(columnDefinition = "smallint(3) NOT NULL COMMENT '分钟设定，取值范围：0-59'")
    private Integer minuteSetting;

    @Column(columnDefinition = "tinyint(1) NOT NULL COMMENT '开关机 0:关机 1:开机'")
    private Integer deviceOperate;

    @Column(columnDefinition = "tinyint(1) NOT NULL COMMENT '模式、0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式'")
    private Integer mode;

    @Column(columnDefinition = "int(11) NOT NULL COMMENT '温度，范围16～32'")
    private Integer temperatureSetting;

    @Column(columnDefinition = "tinyint(1) NOT NULL COMMENT '风速-0：低风1：中风2：高风3：静音4：自动5：强力'")
    private Integer windSpeed;

    @Column(columnDefinition = "varchar(50) NOT NULL COMMENT '循环规则，周一到周日之间7天可以选择多天执行，多天用英文逗号隔开'")
    private String repeatRule;

    @Column(name="is_on", columnDefinition = "bit(1) NOT NULL COMMENT '该条定时任务是否开启，false：关闭，true：开启'")
    private Boolean on;

    @Column(name = "is_trash",columnDefinition = " bit(1) NOT NULL COMMENT '该条定时任务是否有效，false：有效，true：无效，丢弃，放弃'")
    private Boolean trash = false ;

    public AppScheduleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Boolean getTrash() {
        return trash;
    }

    public void setTrash(Boolean trash) {
        this.trash = trash;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

}
