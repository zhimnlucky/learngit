package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fju on 2017/8/14.
 */
@ApiModel(value = "定时任务Id信息")
public class AppScheduleIdVo {
    @ApiModelProperty(value = "定时任务Id")
    private String id;

    public AppScheduleIdVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
