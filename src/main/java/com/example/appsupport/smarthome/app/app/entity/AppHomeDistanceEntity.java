package com.example.appsupport.smarthome.app.app.entity;

import com.auxgroup.smarthome.entity.AbstractGenericEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 离家回家实体类
 * Created by lixiaoxiao on 2017/8/1.
 */
@Entity
@Table(name = "app_home_distance")
public class AppHomeDistanceEntity extends AbstractGenericEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="distance_id",length = 36)
    private String distanceId;

    @Column(name="device_id",columnDefinition = "varchar(36) NOT NULL COMMENT '设备主键'")
    private String deviceId;

    @Column(name="distance",columnDefinition = "int NOT NULL COMMENT '设置距离'")
    private Integer distance;

    @Column(name="mode_launch",columnDefinition = "int NOT NULL COMMENT '模式触发 0:未触发1:离家2:回家'")
    private Integer modeLaunch;

    @Column(name="uid",columnDefinition = "varchar(36) NOT NULL COMMENT '用户id'")
    private String uId;

    //开启&关闭
    @Column(name="is_on",columnDefinition = "bool NOT NULL COMMENT '是否开启'")
    private Boolean on;

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
}
