package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppSleepDiyDto;
import com.auxgroup.smarthome.app.service.AppSleepDiyService;
import com.auxgroup.smarthome.app.vo.AppSleepDiyVo;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by niuGuangzhe on 2017/7/26.
 */
@Api(description = "睡眠diy相关", tags = "睡眠diy相关")
@RestController
@Validated
public class AppSleepDiyController {

    @Autowired
    private AppSleepDiyService appSleepDiyService;

    @ApiOperation(value = "查询睡眠DIY列表", notes = "睡眠DIY--查询睡眠DIY列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/sleep", method = RequestMethod.GET)
    public ApiResponse<List<AppSleepDiyVo>> getAppSleepDiy(@NotBlank(message = "设备id不能为空") String deviceId) {
        return appSleepDiyService.getAppSleepDiy(deviceId);
    }

    @ApiOperation(value = "新增睡眠DIY", notes = "睡眠DIY--新增睡眠DIY列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "mode", value = "模式(1:制冷 2:制热)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "startHour", value = "开始小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "definiteTime", value = "定时时间 必填 单位：小时(取值范围 1-12)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "windSpeed", value = "风速(0:低风 1:中风 2:高风 3:静音 4:自动 5:强力 6:自定义)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "smart", value = "是否智能模式(是:1,否：0)", paramType = "form", dataType = "boolean", required = true),
            @ApiImplicitParam(name = "electricControl", value = "电控指令（json)", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "deviceManufacturer", value = "厂商标识 1：机智云 0：古北", paramType = "form", dataType = "int", required = true)
    })
    @RequestMapping(value = "/sleep", method = RequestMethod.POST)
    public ApiResponse<AppSleepDiyVo> addAppSleepDiy(@ApiIgnore @Valid AppSleepDiyDto appSleepDiyDto) {
        return appSleepDiyService.addAppSleepDiy(appSleepDiyDto);
    }

    @ApiOperation(value = "修改睡眠DIY", notes = "睡眠DIY--修改睡眠DIY列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sleepDiyId", value = "睡眠表主键", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "deviceId", value = "设备Id", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "mode", value = "模式(1:制冷 2:制热)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "startHour", value = "开始小时", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "definiteTime", value = "定时时间 必填 单位：小时(取值范围 1-12)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "windSpeed", value = "风速(0:低风 1:中风 2:高风 3:静音 4:自动 5:强力 6:自定义)", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "smart", value = "是否智能模式(是:true 否:false)", paramType = "form", dataType = "boolean", required = true),
            @ApiImplicitParam(name = "electricControl", value = "(电控指令json)", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "deviceManufacturer", value = "厂商标识 1：机智云 0：古北", paramType = "form", dataType = "int", required = true)
    })
    @RequestMapping(value = "/sleep", method = RequestMethod.PUT)
    public ApiResponse updateAppSleepDiy(String sleepDiyId,
                                         @ApiIgnore @Valid AppSleepDiyDto appSleepDiyDto) {
        if (StringUtils.isBlank(sleepDiyId)) {
            return ApiResponse.prompt(AppCode.SLEEPDIY_INPUT_ILLEGAL);
        }
        return appSleepDiyService.updateAppSleepDiy(sleepDiyId, appSleepDiyDto);
    }

    @ApiOperation(value = "删除睡眠DIY", notes = "睡眠DIY--删除睡眠DIY列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sleepDiyId", value = "睡眠表主键ID", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/sleep", method = RequestMethod.DELETE)
    public ApiResponse deleteAppSleepDiy(@NotBlank(message = "睡眠diy id不能为空") String sleepDiyId) {
        if (StringUtils.isBlank(sleepDiyId)) {
            return ApiResponse.prompt(AppCode.SLEEPDIY_INPUT_ILLEGAL);
        }
        return appSleepDiyService.deleteAppSleepDiy(sleepDiyId);
    }

    @ApiOperation(value = "睡眠DIY开启&关闭", notes = "睡眠DIY--开启&关闭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "on", value = "开关 是否开启 true:开启、false:关闭", paramType = "form", dataType = "boolean", required = true)
    })
    @PutMapping(value = "/sleep/switch")
    public ApiResponse switchSleepDiyId(@RequestParam("sleepDiyId") String sleepDiyId, @NotNull(message = "开关不能为空") Boolean on) {
        return appSleepDiyService.switchAppSleepDiyColumn(sleepDiyId, on);
    }

    @ApiOperation(value = "关闭设备的所有的睡眠diy")
    @DeleteMapping(value = "/sleep/switch/close_all")
    public ApiResponse switchCloseAllSleepDiy(@RequestParam("deviceId") String deviceId) {
        return appSleepDiyService.closeAllSleepDiyByDeviceId(deviceId);
    }

    @ApiIgnore
    @ApiOperation(value = "关闭睡眠DIY功能", notes = "关闭睡眠DIY功能，同时下发电控指令，取消所有正在进行中的睡眠DIY")
    @RequestMapping(value = "/private/all/sleep", method = RequestMethod.DELETE)
    @PermissionFilter(filter = true)
    public ApiResponse closeAllSleepDiy(@RequestParam("pk") String pk, @RequestParam("did") String did) {
        return appSleepDiyService.closeAllSleepDiy(pk, did);
    }

    @ApiIgnore
    @ApiOperation(value = "关闭正在运行的睡眠DIY", notes = "关闭正在运行的睡眠DIY，同时下发电控指令,取消正在进行中的睡眠DIY")
    @PermissionFilter(filter = true)
    @RequestMapping(value = "/sleep/closeRun", method = RequestMethod.DELETE)
    public ApiResponse closeRunSleepDiy(@RequestParam("pk") String pk, @RequestParam("did") String did, @RequestParam("deviceId") String deviceId) {
        return appSleepDiyService.closeRunSleepDiy(pk, did, deviceId);
    }

}
