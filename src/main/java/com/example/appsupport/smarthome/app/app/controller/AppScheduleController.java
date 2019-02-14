package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppScheduleDto;
import com.auxgroup.smarthome.app.dto.AppUpdateScheduleDto;
import com.auxgroup.smarthome.app.service.AppScheduleService;
import com.auxgroup.smarthome.app.vo.AppScheduleIdVo;
import com.auxgroup.smarthome.app.vo.AppScheduleVo;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by fju on 2017/8/14.
 */
@Api(tags = "定时任务", description = "定时控制设备")
@RestController
@Validated
public class AppScheduleController {

    @Autowired
    private AppScheduleService appScheduleService;

    @ApiOperation(value = "创建定时任务")
    @RequestMapping(value = "/schedule",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "query", dataType = "string",required = true),
            @ApiImplicitParam(name = "hourSetting", value = "设置小时时间, 取值范围：0-23",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "minuteSetting", value = "设置分钟时间,取值范围：0-59",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "deviceOperate", value = "操作,0:关机 1:开机",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "mode", value = "模式,0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "temperatureSetting", value = "温度设置,范围16～32",  paramType = "query", dataType = "float",required = true),
            @ApiImplicitParam(name = "dst", value = "设备地址： 单元机默认为1，多联机取值：1-64",  paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "windSpeed", value = "风速,0:低风 1:中风 2:高风 3:静音 4:自动 5:强力",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "repeatRule", value = "循环规则 周一到周日之间7天可以选择多天执行，多天用英文逗号隔开",  paramType = "query", dataType = "string",required = true),
            @ApiImplicitParam(name = "on", value = "是否开启", defaultValue ="false", paramType = "query", dataType = "boolean",required = true)
    })
    public ApiResponse<AppScheduleIdVo> createSchedule(@ApiIgnore @Valid AppScheduleDto appScheduleDto){
        return appScheduleService.createSchedule(appScheduleDto);
    }

    @ApiOperation(value = "修改定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定时控制任务id",  paramType = "path", dataType = "string",required = true),
            @ApiImplicitParam(name = "hourSetting", value = "设置小时时间, 取值范围：0-23",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "minuteSetting", value = "设置分钟时间,取值范围：0-59",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "deviceOperate", value = "操作,0:关机 1:开机",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "mode", value = "模式,0:自动模式 1:制冷模式 2:制热模式 3:除湿模式 4:送风模式",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "temperatureSetting", value = "温度设置,范围16～32",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "dst", value = "设备地址： 单元机默认为1，多联机取值：1-64",  paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "windSpeed", value = "风速,0:低风 1:中风 2:高风 3:静音 4:自动 5:强力",  paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "repeatRule", value = "循环规则 周一到周日之间7天可以选择多天执行，多天用英文逗号隔开",  paramType = "query", dataType = "string",required = true),
            @ApiImplicitParam(name = "on", value = "是否开启", defaultValue ="false", paramType = "query", dataType = "boolean",required = true)
    })
    @PutMapping(value = "/schedules/{id}")
    public ApiResponse<AppScheduleIdVo> updateSchedule(@PathVariable("id") @NotBlank String id, @ApiIgnore @Valid AppUpdateScheduleDto appScheduleDto){
        return appScheduleService.updateSchedule(id, appScheduleDto);
    }

    @ApiOperation(value = "开启或者关闭定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定时任务id",  paramType = "query", dataType = "string",required = true),
            @ApiImplicitParam(name = "on", value = "是否开启,true:开启 false:关闭", paramType = "query", dataType = "boolean",required = true)
    })
    @RequestMapping(value = "/schedule",method = RequestMethod.PUT)
    public ApiResponse switchSchedule(@NotBlank(message = "定时任务id不能为空") String id , @NotNull(message = "是否开启不能为空") Boolean on){
        return appScheduleService.switchSchedule(id, on);
    }

    @ApiOperation(value = "获取定时任务列表")
    @RequestMapping(value = "/schedule",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "query", dataType = "string",required = true),
            @ApiImplicitParam(name = "dst", value = "多联机子设备地址，取值：[1,64], 不传表示查询单元机定时任务",  paramType = "query", dataType = "int")
    })
    public ApiResponse<List<AppScheduleVo>> getScheduleList(@NotBlank(message = "设备id不能为空") String deviceId, Integer dst){
        return appScheduleService.getScheduleList(deviceId, dst);
    }

    @ApiOperation(value = "删除定时任务")
    @RequestMapping(value = "/schedule",method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定时任务id",  paramType = "query", dataType = "string",required = true)
    })
    public ApiResponse delSchedule(@NotBlank(message = "设备id不能为空") String id){
        return appScheduleService.delSchedule(id);
    }

}