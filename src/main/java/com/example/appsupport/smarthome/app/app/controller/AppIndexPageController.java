package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.business.inner.vo.AppIndexPageVo;
import com.auxgroup.smarthome.app.service.inner.AppIndexPageInnerService;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niuGuangzhe on 2017/8/3.
 */
@Api(description = "app首页",tags = "app首页相关")
@RestController
public class AppIndexPageController {

    @Autowired
    private AppIndexPageInnerService appIndexPageInnerService;

    @ApiOperation(value = "获取最新首页发布版本", notes = "App首页-获取最新发布")
    @RequestMapping(value = "/newest_index_page", method = RequestMethod.GET)
    @PermissionFilter(filter = true)
    public ApiResponse<AppIndexPageVo> getNewestIndexPage(){
        return appIndexPageInnerService.getNewestIndexPage();
    }
}
