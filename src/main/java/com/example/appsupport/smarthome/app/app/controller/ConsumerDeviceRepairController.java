package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.dto.DeviceRepairDto;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.inner.DeviceRepairInnerService;
import com.auxgroup.smarthome.appconst.BusinessConstant;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 一键报修相关API
 * Created by lixiaoxiao on 17-8-7.
 */
@Api(description = "一键报修", tags = "一键报修")
@RestController
public class ConsumerDeviceRepairController {

    @Autowired
    private DeviceRepairInnerService deviceRepairInnerService;

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "上报一键报修",notes = "一键报修--上报一键报修")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac", value = "mac",   paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "deviceSn", value = "设备sn",   paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "faultType", value = "故障类型",   paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/device_fault", method = RequestMethod.POST)
    public ApiResponse addDeviceRepairList(@ApiIgnore @Valid DeviceRepairDto deviceRepairDto)
    {
        AppUserVo appUserVo = appUserService.getAppUser().getData();
        if(appUserVo != null){
            deviceRepairDto.setUserName(appUserVo.getNickName());
            deviceRepairDto.setUserPhone(appUserVo.getPhone());
        }
        deviceRepairDto.setFaultStatus(BusinessConstant.FAULT_NOT_RESOLVED.getValue());
        return deviceRepairInnerService.addDeviceFault(deviceRepairDto);
    }

}