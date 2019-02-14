package com.example.appsupport.smarthome.app.app.vo;

/**
 * Created by root on 17-9-15.
 */
public class AppHomeDistanceRedisVo {
    private double distance;

    private Long  createdAt;

    public AppHomeDistanceRedisVo() {
    }

    public AppHomeDistanceRedisVo(double distance, Long createdAt) {
        this.distance = distance;
        this.createdAt = createdAt;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
