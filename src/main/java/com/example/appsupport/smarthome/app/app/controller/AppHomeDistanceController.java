package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppHomeDistanceDto;
import com.auxgroup.smarthome.app.service.AppHomeDistanceService;
import com.auxgroup.smarthome.app.vo.AppHomeDistanceVo;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 离家回家模式接口
 * Created by lixiaoxiao on 17-8-1.
 */
@Api(description = "离家回家", tags = "离家回家")
@RestController
public class AppHomeDistanceController {

    @Autowired
    private AppHomeDistanceService appHomeDistanceService;

    @ApiOperation(value = "获取操作过离家回家的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "query", dataType = "string"),
    })
    @GetMapping(value = "/home_distance/devices")
    public ApiResponse getAllDeviceHomeDistance(String deviceId) {
        return ApiResponse.ok(appHomeDistanceService.findHomeDistance(deviceId));
    }


    @ApiOperation(value = "获取离家回家表", notes = "")
    //@ApiImplicitParam(name = "deviceId", value = "设备id", required = true, paramType = "form", dataType = "string")
    @RequestMapping(value = "/home_distance/{deviceId}", method = RequestMethod.GET)
    public ApiResponse<AppHomeDistanceVo> findByDeviceId(@PathVariable("deviceId") String deviceId) {
        return new ApiResponse<AppHomeDistanceVo>(
                Syscode.SC_OK.getCode(),Syscode.SC_OK.getMsg(),appHomeDistanceService.findByDeviceId(deviceId));
    }
    @ApiOperation(value = "新增离家回家", notes = "离家回家--新增离家回家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "distance", value = "距离", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "on", value = "开启/关闭 true/false", paramType = "form", dataType = "bool", required = true)
    })
    @RequestMapping(value = "/home_distance", method = RequestMethod.POST)
    public ApiResponse addHomeDistance(@ApiIgnore @Valid AppHomeDistanceDto appHomeDistanceDto) {
        if(StringUtils.isBlank(appHomeDistanceDto.getDeviceId()) || null == appHomeDistanceDto.getDistance()){
            return ApiResponse.prompt(AppCode.HOMEDISTANCE_UNKNOWN_DEVICE);
        }
        appHomeDistanceService.addHomeDistance(appHomeDistanceDto);
        return ApiResponse.prompt(String.valueOf(Syscode.SC_OK.getCode()), Syscode.SC_OK.getMsg());
    }

    @ApiOperation(value = "修改离家回家", notes = "离家回家--修改离家回家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distanceId", value = "主键", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "distance", value = "距离", paramType = "form", dataType = "int", required = true),
            @ApiImplicitParam(name = "on", value = "开启/关闭 true/false", paramType = "form", dataType = "bool", required = true)
    })
    @RequestMapping(value = "/home_distance", method = RequestMethod.PUT)
    public ApiResponse saveHomeDistance(@ApiIgnore @Valid AppHomeDistanceDto appHomeDistanceDto) {
        if(StringUtils.isBlank(appHomeDistanceDto.getDistanceId())
                || StringUtils.isBlank(appHomeDistanceDto.getDeviceId())
                || null == appHomeDistanceDto.getDistance()){
            return ApiResponse.prompt(AppCode.HOMEDISTANCE_UNKNOWN_DEVICE);
        }
        Boolean isOk = appHomeDistanceService.saveHomeDistance(appHomeDistanceDto);
        return ApiResponse.prompt(String.valueOf(Syscode.SC_OK.getCode()), Syscode.SC_OK.getMsg());
    }

    @ApiOperation(value = "开启/关闭离家回家", notes = "离家回家--开启/关闭离家回家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distanceId", value = "主键", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "on", value = "开启/关闭", paramType = "form", dataType = "bool", required = true)
    })
    @RequestMapping(value = "/home_distance/{distanceId}", method = RequestMethod.PUT)
    public ApiResponse updateHomeDistance(@ApiIgnore AppHomeDistanceDto appHomeDistanceDto) {
        Boolean isOk = appHomeDistanceService.updateHomeDistance(appHomeDistanceDto);
        return ApiResponse.prompt(String.valueOf(Syscode.SC_OK.getCode()), Syscode.SC_OK.getMsg());
    }

}
