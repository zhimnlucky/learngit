package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.service.inner.AppVersionInnerService;
import com.auxgroup.smarthome.app.vo.AppVersionVo;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * app版本接口
 * Created by lixiaoxiao on 18-3-15.
 */
@Api(description = "app版本", tags = "app版本")
@RestController
public class AppVersionController {

    @Autowired
    AppVersionInnerService appVersionInnerService;

    @ApiOperation(value = "获取app版本信息")
//    @GetMapping(value = "/app_version")
    @RequestMapping(value = "/app_version",method= RequestMethod.GET)
    public ApiResponse<AppVersionVo> getCurrentAppVersion() {
        return appVersionInnerService.getCurrentAppVersion();
    }
}
