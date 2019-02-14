package com.example.appsupport.smarthome.app.app.entity;

import com.auxgroup.bridge.app.inner.vo.DeviceInfoNewVo;
import com.auxgroup.smarthome.BeanUtils;

/**
 * Created by fju on 2017/8/14.
 * 用于缓存定时设备扩展数据实体
 */
public class AppScheduleExtraEntity {

    private String id;

    private String deviceId;

    private Integer hourSetting;

    private Integer minuteSetting;

    private Integer deviceOperate;

    private Integer mode;

    private Integer temperatureSetting;

    private Integer windSpeed;

    private Integer dst;

    private String repeatRule;

    private Boolean on;

    private Boolean trash = false;

    //来源 0 古北（旧） ,1 机智云（新）
    private Integer source;

    private String productKey;

    private String mac;

    //机智云did
    private String did;

    //适用类型 0：家用 1：商用
    private Integer suitType;

    //挂机/柜机标识 0：挂机 1：柜机
    private Integer useType;

    public AppScheduleExtraEntity() {
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
        trash = trash;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Integer getSuitType() {
        return suitType;
    }

    public void setSuitType(Integer suitType) {
        this.suitType = suitType;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

    public AppScheduleExtraEntity createAppScheduleExtraEntity(AppScheduleEntity appScheduleEntity, DeviceInfoNewVo deviceInfoNewVo){
        AppScheduleExtraEntity appScheduleExtraEntity = BeanUtils.copyAttrs(this, appScheduleEntity);
        BeanUtils.copyAttrs(appScheduleExtraEntity,deviceInfoNewVo);
        return this;
    }
}
