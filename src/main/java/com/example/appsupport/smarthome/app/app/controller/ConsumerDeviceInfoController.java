package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.vo.AppDeviceInfoListVo;
import com.auxgroup.bridge.app.inner.vo.DeviceSimpleInfoVo;
import com.auxgroup.smarthome.app.dto.DeviceInfoDto;
import com.auxgroup.smarthome.app.service.DeviceInfoService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by fju on 2017/7/27.
 */
@Api(tags = "绑定与解绑", description = "绑定与解绑")
@RestController
@Validated
public class ConsumerDeviceInfoController {

    @Autowired
    private DeviceInfoService deviceInfoService;

    @ApiOperation(value = "设备绑定")
    @RequestMapping(value = "/device_bindings", method = RequestMethod.POST)
    public ApiResponse<DeviceSimpleInfoVo> bindDevice(@Valid @RequestBody DeviceInfoDto deviceInfoDto) {
        return deviceInfoService.bindDevice(deviceInfoDto);
//        return ApiResponse.prompt(AppCode.GIZWITS_DEVICE_PRODUCT_KEY_NOT_NULL);
    }

    @ApiOperation(value = "设备绑定new(验证passcode)")
    @RequestMapping(value = "/device_bindings_new", method = RequestMethod.POST)
    public ApiResponse<DeviceSimpleInfoVo> bindDeviceNew(@Valid @RequestBody DeviceInfoDto deviceInfoDto) {
        return deviceInfoService.bindDeviceNew(deviceInfoDto);
//        return ApiResponse.prompt(AppCode.GIZWITS_DEVICE_PRODUCT_KEY_NOT_NULL);
    }

    @ApiOperation(value = "设备解绑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "query", dataType = "string",required = true),
    })
    @RequestMapping(value = "/device_bindings", method = RequestMethod.DELETE)
    public ApiResponse unBindDevice(@NotEmpty(message = "设备id不能为空") String deviceId) {
        return deviceInfoService.unBindDevice(deviceId);
    }

    @ApiOperation(value = "获取app设备列表")
    @GetMapping(value = "/device_bindings")
    public ApiResponse<List<AppDeviceInfoListVo>> getAppDeviceInfoList() {
        return deviceInfoService.getAppDeviceInfoList();
    }

    @ApiOperation(value = "修改设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac",value = "mac地址",paramType = "path",dataType = "string",required = true),
            @ApiImplicitParam(name = "sn",value = "SN",paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "alias",value = "设备别名",paramType = "query",dataType = "string"),
    })
    @RequestMapping(value = "/device/{mac}",method = RequestMethod.PUT)
    public ApiResponse updateDeviceInfo(@PathVariable("mac") String mac, String sn, String alias){
        if(StringUtils.isBlank(sn) && StringUtils.isBlank(alias)){
            return ApiResponse.prompt(AppCode.SN_ALIAS_EMPTY);
        }
        return deviceInfoService.updateDeviceInfo(mac,sn,alias);
    }
}
