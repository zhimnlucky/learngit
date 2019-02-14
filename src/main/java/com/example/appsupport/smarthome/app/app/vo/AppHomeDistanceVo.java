package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModelProperty;

/**离家回家Vo
 * Created by lixiaoxiao on 17-8-1.
 */
public class AppHomeDistanceVo {

    private String distanceId;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设置距离")
    private Integer distance;

    @ApiModelProperty(value = "触发模式 0:未触发1:离家2:回家")
    private Integer modeLaunch;

    @ApiModelProperty(value = "用户id")
    private String uId;

    @ApiModelProperty(value = "开启&关闭")
    private Boolean on;

    private Long createdAt;

    public String getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(String distanceId) {
        this.distanceId = distanceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getModeLaunch() {
        return modeLaunch;
    }

    public void setModeLaunch(Integer modeLaunch) {
        this.modeLaunch = modeLaunch;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public AppHomeDistanceVo() {
    }

    public AppHomeDistanceVo(Integer distance, Long createdAt) {
        this.distance = distance;
        this.createdAt = createdAt;
    }
}
