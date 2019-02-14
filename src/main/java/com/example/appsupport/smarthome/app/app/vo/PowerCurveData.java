package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fju on 2017/8/30.
 * kevin chen 在不改变大部分属性字段的情况下删除冗余的
 */
@ApiModel(value = "用于前端生成曲线的vo")
public class PowerCurveData {
    @ApiModelProperty(value = "时间轴，选择日用电曲线为时间轴单位为小时，选择月用电曲线为时间轴单位为天，选择月用电曲线为时间轴单位为月份")
    private int timeShaft;

    @ApiModelProperty(value = "波平总耗电量")
    private float waveFlatElectricity;

    @ApiModelProperty(value = "波谷总耗电电")
    private float valleyElectricity;

    @ApiModelProperty(value = "波峰总耗电量")
    private float peakElectricity;

    public PowerCurveData() {
    }

    public PowerCurveData(int timeShaft) {
        this.timeShaft = timeShaft;
    }

    public PowerCurveData(int timeShaft, float waveFlatElectricity, float valleyElectricity, float peakElectricity) {
        this.timeShaft = timeShaft;
        this.waveFlatElectricity = waveFlatElectricity;
        this.valleyElectricity = valleyElectricity;
        this.peakElectricity = peakElectricity;
    }

    public void createPowerCurveData(float waveFlatElectricity, float valleyElectricity, float peakElectricity) {
        this.waveFlatElectricity = waveFlatElectricity;
        this.valleyElectricity = valleyElectricity;
        this.peakElectricity = peakElectricity;
    }

    public void createPowerPeakValley(float valleyElectricity, float peakElectricity){
        this.valleyElectricity = valleyElectricity;
        this.peakElectricity = peakElectricity;
    }

    /**
     * 用电曲线存在波峰波谷的时候  纠正波平的值
     * 当然无波峰波谷则波平即为用电总量，不做处理
      * @param valleyElectricity
     * @param peakElectricity
     */
    public void changeWaveFlatElectricityValue(float valleyElectricity, float peakElectricity){
        this.waveFlatElectricity = this.waveFlatElectricity - valleyElectricity - peakElectricity;
    }

    public int getTimeShaft() {
        return timeShaft;
    }

    public void setTimeShaft(int timeShaft) {
        this.timeShaft = timeShaft;
    }

    public float getWaveFlatElectricity() {
        return waveFlatElectricity;
    }

    public void setWaveFlatElectricity(float waveFlatElectricity) {
        this.waveFlatElectricity = waveFlatElectricity;
    }

    public float getValleyElectricity() {
        return valleyElectricity;
    }

    public void setValleyElectricity(float valleyElectricity) {
        this.valleyElectricity = valleyElectricity;
    }

    public float getPeakElectricity() {
        return peakElectricity;
    }

    public void setPeakElectricity(float peakElectricity) {
        this.peakElectricity = peakElectricity;
    }
}
