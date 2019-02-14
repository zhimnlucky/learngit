package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.dto.AppPushLimitDto;
import com.auxgroup.bridge.app.inner.vo.AppPushLimitVo;
import com.auxgroup.smarthome.app.service.AppPushLimitService;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "推送限制管理", description = "推送限制管理")
@RestController
public class AppPushLimitController {


    @Autowired
    private AppPushLimitService appPushLimitService;

    @ApiOperation(value = "添加/更新推送限制")
    @RequestMapping(value = "/appPushLimit/setSilenceTime", method = RequestMethod.POST)
    public ApiResponse<AppPushLimitVo> setSilenceTime(@RequestBody AppPushLimitDto appPushLimitDto) {
        return appPushLimitService.setSilenceTime(appPushLimitDto);
    }


    @ApiOperation(value = "查询推送限制详情")
    @RequestMapping(value = "/appPushLimit/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
    })
    public ApiResponse<AppPushLimitVo> getInfo() {
        return appPushLimitService.getInfo();
    }

}
