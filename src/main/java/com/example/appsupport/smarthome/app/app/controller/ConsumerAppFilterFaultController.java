package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.dto.AppFilterFaultDto;
import com.auxgroup.bridge.app.inner.vo.AppFilterFaultVo;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.inner.AppFilterFaultInnerService;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 滤网故障类
 * Created by lixiaoxiao on 17-8-14.
 */
@Api(description = "滤网清洗", tags = "滤网故障")
@RestController
public class ConsumerAppFilterFaultController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppFilterFaultInnerService appFilterFaultInnerService;

    @ApiOperation(value = "滤网清洗", notes = "滤网故障--滤网清洗")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac", value = "设备mac", paramType = "form", dataType = "string", required = true)
    })
    @RequestMapping(value = "/confirm_filter_washed", method = RequestMethod.POST)
    public ApiResponse confirmFilterWashed(AppFilterFaultDto appFilterFaultDto) {
        String uid = appUserService.getUid();
        return appFilterFaultInnerService.confirmFilterWashed(appFilterFaultDto,uid);
    }

    /**
     * 滤网故障
     * @param mac
     * @return
     */
    @ApiOperation(value = "滤网故障")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac", value = "设备mac", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/filter_fault", method = RequestMethod.GET)
    public ApiResponse<AppFilterFaultVo> getFilterFault(@RequestParam(required = true) String mac) {
        return appFilterFaultInnerService.getFilterFault(mac);
    }
}
