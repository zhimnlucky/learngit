package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppPeakValleyDto;
import com.auxgroup.smarthome.app.service.AppPeakValleyService;
import com.auxgroup.smarthome.app.vo.AppPeakValleyVo;
import com.auxgroup.smarthome.app.vo.PowerCurveVo;
import com.auxgroup.smarthome.basebean.DateType;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 峰谷节电接口
 * Created by niuGuangzhe on 2017/8/1.
 */
@Api(description = "峰谷节电接口", tags = "峰谷节电")
@RestController
public class AppPeakValleyController {

    @Autowired
    private AppPeakValleyService appPeakValleyService;

    @ApiOperation(value = "查询峰谷设置", notes = "峰谷节电--查询峰谷设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/peak_valley", method = RequestMethod.GET)
    public ApiResponse<AppPeakValleyVo> getPeakValleyByDevice(String deviceId) {
        return appPeakValleyService.findPeakValleyByDeviceId(deviceId);
    }

    @ApiIgnore
    @ApiOperation(value = "查询峰谷设置[内部调用]", notes = "峰谷节电--查询峰谷设置--用于内部查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/private/peak_valley", method = RequestMethod.GET)
    public ApiResponse<AppPeakValleyVo> getPeakValleyByDevicePrivate(String deviceId) {
        return appPeakValleyService.findPeakValleyByDeviceIdPrivate(deviceId);
    }

    @ApiOperation(value = "新增峰谷设置", notes = "峰谷节电--新增峰谷设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "peakStartHour", value = "谷峰开始小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakStartMinute", defaultValue = "0", value = "谷峰开始分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakEndHour", value = "谷峰结束小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakEndMinute",  defaultValue = "0", value = "谷峰结束分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyStartHour", value = "波谷开始小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyStartMinute",  defaultValue = "0", value = "波谷开始分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyEndHour", value = "波谷结束小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyEndMinute", defaultValue = "0", value = "波谷结束分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "on", value = "是否开启(是：1 否：0)", paramType = "form", dataType = "boolean", required = true),
    })
    @RequestMapping(value = "/peak_valley", method = RequestMethod.POST)
    public ApiResponse<AppPeakValleyVo> addPeakValley(@ApiIgnore @Valid AppPeakValleyDto appPeakValleyDto) {
        return appPeakValleyService.addPeakValley(appPeakValleyDto);
    }

    @ApiOperation(value = "修改峰谷设置", notes = "峰谷节电--更新峰谷设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "peakValleyId", value = "峰谷主键", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "deviceId", value = "设备主键", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "peakStartHour", value = "谷峰开始小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakStartMinute", value = "谷峰开始分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakEndHour", value = "谷峰结束小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "peakEndMinute", value = "谷峰结束分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyStartHour", value = "波谷开始小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyStartMinute", value = "波谷开始分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyEndHour", value = "波谷结束小时", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "valleyEndMinute", value = "波谷结束分钟", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "on", value = "状态(true:开启 false:关闭)", paramType = "form", dataType = "boolean", required = true),
    })
    @RequestMapping(value = "/peak_valley", method = RequestMethod.PUT)
    public ApiResponse updatePeakValley(String peakValleyId,
                                        @ApiIgnore @Valid AppPeakValleyDto appPeakValleyDto) {
        return appPeakValleyService.updatePeakValley(peakValleyId, appPeakValleyDto);
    }


    @ApiOperation(value = "获取用电曲线(日、月、年)", notes = "峰谷节电--用电曲线<br>如果传入的日期为非法,那么会直接使用当前的日期作为默认值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="did", value = "设备did", dataType = "string",paramType = "query", required = true) ,
            @ApiImplicitParam(name="time", value = "时间 yyyyMMddHH格式;example,day:20170830 month:201708 year:2017", dataType = "string",paramType = "query", required = true) ,
            @ApiImplicitParam(name="dateType",value = "DAY,MONTH,YEAR",dataType = "string",paramType = "query", required = true)
    })
    @RequestMapping(value = "/peak_valley_curve", method = RequestMethod.GET)
    public ApiResponse<PowerCurveVo> getPeakValleyCurve(@RequestParam("did") String did, @RequestParam("time") String time, @RequestParam("dateType") DateType dateType) {
        return appPeakValleyService.getAllPowerCurve(did,time,dateType);
    }

    @ApiIgnore
    @ApiOperation(value = "获取峰谷节电的状态")
    @GetMapping(value = "/private/peak_valley_did")
    @PermissionFilter(filter = true)
    public ApiResponse getPeakValleyByDid(@RequestParam("did") String did ){
        return appPeakValleyService.closePeakValley(did);
    }

    @ApiIgnore
    @ApiOperation(value = "关闭峰谷节电功能", notes = "关闭峰谷节电功能，并且发送电控指令，关闭峰谷节电")
    @RequestMapping(value = "/private/peak_valley", method = RequestMethod.DELETE)
    @PermissionFilter(filter = true)
    public ApiResponse closePeakValleyCurve(@RequestParam("did") String did ){
        return appPeakValleyService.closePeakValley(did);
    }


}
