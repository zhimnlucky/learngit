package com.example.appsupport.smarthome.app.app.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by fju on 2017/8/25.
 */
public class AppElectricalCurveDto implements Serializable{

    @NotNull(message = "productKey不能为Null")
    @Size(max=64,message = "productKey长度超过64")
    private String productKey;

    @NotNull(message = "deviceId不能为Null")
    @Size(max=64,message = "mac长度超过64")
    private String deviceId;

    @NotNull(message = "did不能为Null")
    @Size(max=64,message = "did长度超过64")
    private String did;

    @NotNull(message = "attr不能为Null")
    @Size(max=200,message = "attr长度超过200")
    private String attr;

    @NotNull(message = "year不能为Null")
    private Integer year;

    @NotNull(message = "month不能为Null")
    private Integer month;

    @NotNull(message = "day不能为Null")
    private Integer day;

    @NotNull(message = "waveFlatElectricity不能为Null")
    private Float  waveFlatElectricity;

    @NotNull(message = "valleyElectricity不能为Null")
    private Float valleyElectricity;

    @NotNull(message = "peakElectricity不能为Null")
    private Float peakElectricity;

    @NotNull(message = "totalElectricity不能为Null")
    private Float totalElectricity;

    @NotNull(message = "peakStartHour不能为Null")
    private Integer peakStartHour;

    @NotNull(message = "peakEndHour不能为Null")
    private Integer peakEndHour;

    @NotNull(message = "valleyStartHour不能为Null")
    private Integer valleyStartHour;

    @NotNull(message = "valleyEndHour不能为Null")
    private Integer valleyEndHour;

    @NotNull(message = "dateType不能为Null且可选DAY,MONTH,YEAR")
    private String dateType;//DAY,MONTH,YEAR

    public AppElectricalCurveDto() {
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Float getWaveFlatElectricity() {
        return waveFlatElectricity;
    }

    public void setWaveFlatElectricity(Float waveFlatElectricity) {
        this.waveFlatElectricity = waveFlatElectricity;
    }

    public Float getValleyElectricity() {
        return valleyElectricity;
    }

    public void setValleyElectricity(Float valleyElectricity) {
        this.valleyElectricity = valleyElectricity;
    }

    public Float getPeakElectricity() {
        return peakElectricity;
    }

    public void setPeakElectricity(Float peakElectricity) {
        this.peakElectricity = peakElectricity;
    }

    public Float getTotalElectricity() {
        return totalElectricity;
    }

    public void setTotalElectricity(Float totalElectricity) {
        this.totalElectricity = totalElectricity;
    }

    public Integer getPeakStartHour() {
        return peakStartHour;
    }

    public void setPeakStartHour(Integer peakStartHour) {
        this.peakStartHour = peakStartHour;
    }

    public Integer getPeakEndHour() {
        return peakEndHour;
    }

    public void setPeakEndHour(Integer peakEndHour) {
        this.peakEndHour = peakEndHour;
    }

    public Integer getValleyStartHour() {
        return valleyStartHour;
    }

    public void setValleyStartHour(Integer valleyStartHour) {
        this.valleyStartHour = valleyStartHour;
    }

    public Integer getValleyEndHour() {
        return valleyEndHour;
    }

    public void setValleyEndHour(Integer valleyEndHour) {
        this.valleyEndHour = valleyEndHour;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
