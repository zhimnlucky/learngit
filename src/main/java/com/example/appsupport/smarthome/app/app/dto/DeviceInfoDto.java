package com.example.appsupport.smarthome.app.app.dto;

import com.auxgroup.smarthome.validation.AllowedValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by fju on 2017/7/25.
 * 设备信息传输对象 带token，userId，appId
 */
@ApiModel(value = "设备信息传输对象")
public class DeviceInfoDto implements Serializable {

    @NotNull(message = "source 不能为空")
    @AllowedValues(value ={"0","1"}, message = "source只能为0或1")
    @ApiModelProperty(name = "source", value = "来源:0 古北 ,1 机智云", dataType = "int", required = true)
    private Integer source;
    @ApiModelProperty(name = "alias", value = "别名", dataType = "string")
    private String alias;
    @ApiModelProperty(name = "city", value = "城市", dataType = "string")
    private String city;
    @ApiModelProperty(name = "cityCode", value = "城市编码", dataType = "string")
    private String cityCode;
    @ApiModelProperty(name = "dataOne", value = "dataOne", dataType = "string")
    private String dataOne;
    @ApiModelProperty(name = "dataTwo", value = "dataTwo", dataType = "string")
    private String dataTwo;
    @ApiModelProperty(name = "dataThree", value = "dataThree", dataType = "string")
    private String dataThree;
    @ApiModelProperty(name = "deviceKey", value = "设备key", dataType = "string")
    private String deviceKey;
    @ApiModelProperty(name = "deviceLock", value = "设备锁", dataType = "string")
    private String deviceLock;
    @ApiModelProperty(name = "did", value = "设备Did", dataType = "string")
    private String did;
    @NotNull(message = "isOnline不能为空")
    @ApiModelProperty(name = "isOnline", value = "是否在线", dataType = "boolean",required = true)
    private Boolean isOnline;
    @ApiModelProperty(name = "longitude", value = "经度", dataType = "string")
    private String longitude;
    @ApiModelProperty(name = "latitude", value = "纬度", dataType = "string")
    private String latitude;
    @NotEmpty(message = "mac不能为空")
    @ApiModelProperty(name = "mac", value = "设备Mac", dataType = "string",required = true)
    private String mac;
    @NotEmpty(message = "modelId不能为空")
    @ApiModelProperty(name = "modelId", value = "型号Id", dataType = "string",required = true)
    private String modelId;
    @ApiModelProperty(name = "password", value = "密码", dataType = "string")
    private String password;
    @ApiModelProperty(name = "productKey", value = "产品productKey", dataType = "string")
    private String productKey;
    @ApiModelProperty(name = "sn", value = "设备SN", dataType = "string")
    private String sn;
    @ApiModelProperty(name = "subDevice", value = "子设备", dataType = "integer")
    private Integer subDevice;
    @ApiModelProperty(name = "terminalId", value = "终端Id", dataType = "integer")
    private Integer terminalId;
    @ApiModelProperty(name = "type", value = "类型", dataType = "string")
    private String type;
    @ApiModelProperty(name="passCode",value="passCode",dataType = "string")
    private String passCode;

    public DeviceInfoDto() {}

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDataOne() {
        return dataOne;
    }

    public void setDataOne(String dataOne) {
        this.dataOne = dataOne;
    }

    public String getDataTwo() {
        return dataTwo;
    }

    public void setDataTwo(String dataTwo) {
        this.dataTwo = dataTwo;
    }

    public String getDataThree() {
        return dataThree;
    }

    public void setDataThree(String dataThree) {
        this.dataThree = dataThree;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceLock() {
        return deviceLock;
    }

    public void setDeviceLock(String deviceLock) {
        this.deviceLock = deviceLock;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSubDevice() {
        return subDevice;
    }

    public void setSubDevice(Integer subDevice) {
        this.subDevice = subDevice;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }
}

