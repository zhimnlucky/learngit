package com.example.appsupport.smarthome.app.app.entity;

import com.auxgroup.smarthome.entity.AbstractGenericEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by fju on 2017/8/1.
 * 智能用电 app_smart_electricity
 */
@Entity
@Table(name = "app_smart_electricity")
public class AppSmartElectricity extends AbstractGenericEntity{

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36,nullable = false)
    private String smartElecId;

    @Column(length = 36,nullable = false)
    private String deviceId;

    @Column
    private String mac;

    @Column
    private Integer startHour; // 开始小时，取值：0-23

    @Column
    private Integer startMinute; // 开始分钟, 取值：0-59

    @Column
    private Integer endHour; // 结束小时， 取值：0-23

    @Column
    private Integer endMinute; // 结束分钟, 取值：0-59

    @Column(nullable = false)
    private Integer totalElec;//总用电量

    @Column(length = 1,nullable = false)
    private Integer mode;//设备模式 0:极速，1：均衡，2：标准

    @Column(length = 1,nullable = false)
    private Integer executeCycle;//执行周期 0:每天，1:一次

    @Column(name="is_on", nullable = false)
    private Boolean on = false;//是否已经开启执行

    public AppSmartElectricity() {
    }

    public AppSmartElectricity(String createdBy) {
        this.createdBy = createdBy;
        this.on = false;
    }

    /**
     * 开启智能用电
     */
    public void open(String updateBy){
        this.updateBy = updateBy;
        this.on = true;
    }

    /**
     * 关闭智能用电
     */
    public void close(){
        this.on = false;
    }

    public AppSmartElectricity(String deviceId, String createdBy, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, Integer totalElec, Integer mode, Integer executeCycle) {
        this.deviceId = deviceId;
        this.createdBy = createdBy;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.totalElec = totalElec;
        this.mode = mode;
        this.executeCycle = executeCycle;
        this.on = false;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getExecuteCount() {
        LocalTime startTime = LocalTime.now(ZoneId.of("Asia/Shanghai")).of(this.startHour, this.startMinute);
        LocalTime endTime = LocalTime.now(ZoneId.of("Asia/Shanghai")).of(this.endHour, this.endMinute);
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Shanghai"));
        Duration startIntervalEndTime = Duration.between(startTime, endTime);
        /**
         *  存在以下几种情况：
         *  1、设置的开始时间还没到，这时候计算次数为开始时间和结束时间之差
         *  2、设置的时间已经过了，这时候以现在的时间为准计算执行次数
         *  3、结束时间已经过了
         *  其他情况均为0， 不考虑什么时候结束，因为可能由于各种原因。。。提前结束，正常结束
         */
        if(endTime.isBefore(now)) {
            return 0;
        } else if (startTime.isBefore(now) && endTime.isAfter(now)) {
            Duration nowIntervalEndTime = Duration.between(now, endTime);
            System.out.println(nowIntervalEndTime.toMinutes());
            return Long.valueOf(nowIntervalEndTime.toMinutes()/10).intValue();
        } else if (startTime.isAfter(now)  && endTime.isAfter(now) ) {
            return Long.valueOf(startIntervalEndTime.toMinutes()/10).intValue();
        }
        return 0;
    }

}
