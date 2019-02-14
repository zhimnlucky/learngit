package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppSmartElectricityDto;
import com.auxgroup.smarthome.app.entity.SmartElectricityRedis;
import com.auxgroup.smarthome.app.service.AppSmartElectricityService;
import com.auxgroup.smarthome.app.service.SmartElectricityExecuteService;
import com.auxgroup.smarthome.app.vo.AppSmartElectricityVo;
import com.auxgroup.smarthome.appconst.AppConstant;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.enterprise.service.DeviceManageService;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.utils.common.LOG;
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

/**
 * Created by fju on 2017/8/1.
 */
@Api(description = "智能用电", tags = "智能用电")
@RestController
@Validated
public class AppSmartElectricityController {
    @Autowired
    private ObjectRedis objectRedis;

    @Autowired
    private AppSmartElectricityService appSmartElectricityService;

    @Autowired
    private SmartElectricityExecuteService smartElectricityExecuteService;

    @Autowired
    private DeviceManageService deviceManageService;

    @ApiOperation(value = "查询智能用电规则",notes = "智能用电规则-查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "query", dataType = "string",required = true),
    })
    @RequestMapping(value = "/smart_electricity", method = RequestMethod.GET)
    public ApiResponse<AppSmartElectricityVo> getSmartElectricity(@NotBlank(message = "设备Id不能为空")  @RequestParam String deviceId){
        return appSmartElectricityService.getSmartElectricity(deviceId);
    }

    @ApiOperation(value = "创建智能用电")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "mac", value = "mac地址",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "startHour", value = "开始小时0-23",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "startMinute", value = "结束分钟0-59",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "endHour", value = "结束小时0-23",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "endMinute", value = "结束分钟0-59",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "totalElec", value = "总共耗电",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "mode", value = "模式 0:极速,1：均衡,2：标准",  paramType = "form", dataType = "int",required = true),
            @ApiImplicitParam(name = "executeCycle", value = "执行周期 0:每天,1:一次",  paramType = "form", dataType = "int",required = true),
    })
    @PostMapping(value = "/smart_electricity")
    public ApiResponse<String> enableSmartElectricity(@ApiIgnore @Valid AppSmartElectricityDto appSmartElectricityDto){
        return appSmartElectricityService.enableSmartElectricity(appSmartElectricityDto);
    }

    @ApiOperation(value = "关闭智能用电",notes = "智能用电规则-关闭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id",  paramType = "query", dataType = "string",required = true),
    })
    @RequestMapping(value = "/smart_electricity", method = RequestMethod.DELETE)
    public ApiResponse<String> disableSmartElectricity(@NotBlank(message = "设备Id不能为空") @RequestParam("deviceId") String deviceId){
        return appSmartElectricityService.disableSmartElectricity(deviceId);
    }

    @ApiIgnore
    @Deprecated
    @ApiOperation(value = "测试智能用电下发", notes="测试使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac", value = "mac地址",  paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "did", value = "did",  paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "electricity", value = "10分钟上报电量",  paramType = "form", dataType = "float",required = true),
            @ApiImplicitParam(name = "ratedPower", value = "额定功率",  paramType = "form", dataType = "int",required = true),
    })
    @PostMapping(value = "/test/smart_electricity_exe")
    public ApiResponse testExecuteSmartElectricity(@RequestParam("mac") String mac, @RequestParam("did") String did, @RequestParam("electricity") float electricity, @RequestParam("ratedPower") int ratedPower){
        String redisKey = CachedConsant.DEVICE_SMART_ELECTRICITY_PREFIX+mac;
        SmartElectricityRedis smartElectricityRedis = objectRedis.get(redisKey, SmartElectricityRedis.class);
        if (null == smartElectricityRedis || !smartElectricityRedis.isOn()) {
            return ApiResponse.prompt("-1", "尚未开启智能用电");
        }
        if(!smartElectricityRedis.isExecuteTime()) {
            return ApiResponse.prompt("-1", "未到执行智能用电时间");
        }

        String productKey = AppConstant.HOME_WIFI_PRODUCT_KEY;
        // 默认功率100
        int defaultPower = 100;

        int power = 0;

        if (smartElectricityRedis.isOn()) {
            if (smartElectricityRedis.isExecuteTime()) {
                power = smartElectricityExecuteService.executeElectricityPower(mac, electricity, ratedPower);
                smartElecControl(1, productKey, did, power);
                // 智能用电执行一次，执行完一个周期，移除缓存
                if(smartElectricityRedis.getExecuteCycle() == 1 && smartElectricityRedis.getN() == (smartElectricityRedis.getTotal()-1)) {
                    smartElecControl(0, productKey, did, defaultPower);
                    objectRedis.delete(redisKey);
                }
            } else {
                smartElecControl(0, productKey, did, defaultPower);
            }
        }
        return ApiResponse.ok(power);
    }

    private void smartElecControl(int smartElectricityStatus, String productKey, String did, int power) {
        LOG.info(this, "开始执行控制指令==========智能用电-》did:" + did);
        deviceManageService.controlPeakValleyAndSmartElectricity(productKey, did, 3, smartElectricityStatus, power);
        LOG.info(this, "执行控制指令结束==========智能用电-》did:" + did);
    }

    @ApiIgnore
    @ApiOperation(value = "查询智能用电规则")
    @RequestMapping(value = "/private/smart_electricity_mac", method = RequestMethod.GET)
    public ApiResponse<AppSmartElectricityVo> getSmartElectricityByMac(@RequestParam("mac") String mac){
        return appSmartElectricityService.getSmartElectricityByMac(mac);
    }

    @ApiIgnore
    @ApiOperation(value = "关闭智能用电功能",notes = "关闭智能用电功能，并下发电控指令")
    @RequestMapping(value = "/private/smart_electricity", method = RequestMethod.DELETE)
    @PermissionFilter(filter = true)
    public ApiResponse closeSmartElectricity(@RequestParam("pk")String pk, @RequestParam("mac")String mac, @RequestParam("did")String did ){
        return appSmartElectricityService.closeSmartElectricity(pk,mac,did);
    }


}
