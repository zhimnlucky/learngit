package com.example.appsupport.smarthome.app.app.dto;

import java.io.Serializable;

/**
 * Created by fju on 2017/8/3.
 * 智能用电 计算结果 不参与Swagger信息
 *
 */
public class SmartElecMidData implements Serializable{

    private Double availableElec;//期望 - 已经用掉的
    private Integer times;//要计算的次数
    private Integer power;//额定功率
    private Integer x;//放大倍数

    public SmartElecMidData() {
    }

    public SmartElecMidData(Double availableElec, Integer times, Integer power, Integer x) {
        this.availableElec = availableElec;
        this.times = times;
        this.power = power;
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Double getAvailableElec() {
        return availableElec;
    }

    public void setAvailableElec(Double availableElec) {
        this.availableElec = availableElec;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public boolean isLast(){
        return times == 1;
    }
}
