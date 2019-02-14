package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by fju on 2017/8/30.
 * 用电曲线 数据结构
 * kevin chen 在不改变大部分属性字段的情况下删除冗余的
 *
 */
@ApiModel
public class PowerCurveVo {


    @ApiModelProperty(value = "用电曲线数据")
    private List<PowerCurveData> powerCurveDatas;

    @ApiModelProperty(value = "波平总耗电量")
    private Float sumWaveFlatElectricity;

    @ApiModelProperty(value = "波谷总耗电电")
    private Float sumValleyElectricity;

    @ApiModelProperty(value = "波峰总耗电量")
    private Float sumPeakElectricity;

    @ApiModelProperty(value = "全部耗电量")
    private Float total;

    @ApiModelProperty(value = "波峰波谷时间段，格式：num1,num2-num3,num4|num5,num6 或者 num1,num2|num3,num4-num5,num6 或者 日用电曲线需用到")
    private String timeBucket;

    public PowerCurveVo() {
    }

    public PowerCurveVo(Float total, Float sumValleyElectricity, Float sumPeakElectricity) {
        this.sumValleyElectricity = sumValleyElectricity == null ? 0f : sumValleyElectricity;
        this.sumPeakElectricity = sumPeakElectricity == null ? 0f : sumPeakElectricity;
        this.total = total == null ? 0f : total;
        this.sumWaveFlatElectricity = this.total - this.sumValleyElectricity - this.sumPeakElectricity;
    }

    public PowerCurveVo(Float total, Float sumValleyElectricity, Float sumPeakElectricity,String timeBucket) {
        this.sumValleyElectricity = sumValleyElectricity == null ? 0f : sumValleyElectricity;
        this.sumPeakElectricity = sumPeakElectricity == null ? 0f : sumPeakElectricity;
        this.total = total == null ? 0f : total;
        this.sumWaveFlatElectricity = this.total - this.sumValleyElectricity - this.sumPeakElectricity;
        this.timeBucket = timeBucket;
    }

    /**
     * 无波峰波谷的构造参数
     * @param total
     */
    public PowerCurveVo(Float total) {
        this.total = total == null ? 0f : total;
        this.timeBucket = "";
        this.sumWaveFlatElectricity = this.total;
        this.sumValleyElectricity = 0f;
        this.sumPeakElectricity = 0f;
    }

    /**
     * 构建前端用电曲线所需的数据
     * @param powerCurveDatas
     */
    public void createCurveData(List<PowerCurveData> powerCurveDatas){
        this.powerCurveDatas = powerCurveDatas;
    }

    public List<PowerCurveData> getPowerCurveDatas() {
        return powerCurveDatas;
    }

    public void setPowerCurveDatas(List<PowerCurveData> powerCurveDatas) {
        this.powerCurveDatas = powerCurveDatas;
    }

    public Float getSumWaveFlatElectricity() {
        return sumWaveFlatElectricity;
    }

    public void setSumWaveFlatElectricity(Float sumWaveFlatElectricity) {
        this.sumWaveFlatElectricity = sumWaveFlatElectricity;
    }

    public Float getSumValleyElectricity() {
        return sumValleyElectricity;
    }

    public void setSumValleyElectricity(Float sumValleyElectricity) {
        this.sumValleyElectricity = sumValleyElectricity;
    }

    public Float getSumPeakElectricity() {
        return sumPeakElectricity;
    }

    public void setSumPeakElectricity(Float sumPeakElectricity) {
        this.sumPeakElectricity = sumPeakElectricity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getTimeBucket() {
        return timeBucket;
    }

    public void setTimeBucket(String timeBucket) {
        this.timeBucket = timeBucket;
    }
}
