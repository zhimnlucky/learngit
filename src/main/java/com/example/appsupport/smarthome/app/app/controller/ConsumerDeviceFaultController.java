package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.vo.AppFilterFaultVo;
import com.auxgroup.bridge.app.inner.vo.DeviceFaultVo;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.inner.AppFilterFaultInnerService;
import com.auxgroup.smarthome.app.service.inner.DeviceFaultInnerService;
import com.auxgroup.smarthome.utils.NewDateUtils;
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

import java.util.List;

/**
 * 设备故障
 * Created by lixiaoxiao on 17-8-9.
 */
@Api(description = "设备故障", tags = "设备故障")
@RestController
public class ConsumerDeviceFaultController {

    @Autowired
    private DeviceFaultInnerService deviceFaultInnerService;

    @Autowired
    private AppFilterFaultInnerService appFilterFaultService;

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "设备故障列表", notes = "设备故障--获取故障列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mac", value = "mac地址", paramType = "query", dataType = "string", required = true)
    })
    @RequestMapping(value = "/devices/faults", method = RequestMethod.GET)
    public ApiResponse<List<DeviceFaultVo>> getDeviceFaultList(@RequestParam(required = true) String mac) {
        //返回时需增加滤网故障
        ApiResponse<List<DeviceFaultVo>> apiResponse = deviceFaultInnerService.getAppDeviceFaultList(mac);
        List<DeviceFaultVo> deviceFaultVos = apiResponse.getData();
        //查询滤网故障，如大于500小时，则返回滤网故障
        AppFilterFaultVo appFilterFaultVo = appFilterFaultService.getFilterFault(mac).getData();
        if(appFilterFaultVo != null && appFilterFaultVo.getBootTime() > 500*60){
            DeviceFaultVo deviceFaultVo = BeanUtils.copyAttrs(new DeviceFaultVo(),appFilterFaultVo);
            deviceFaultVo.setFaultId("-1");
            deviceFaultVo.setFaultName("滤网信息");
            deviceFaultVo.setFaultReason("需要清洗滤网");
            deviceFaultVo.setStatus(1);
            Long updateAt = appFilterFaultVo.getUpdateAt();
            deviceFaultVo.setOccurrenceTime(NewDateUtils.formatLongToDateTime(updateAt,"yyyy-MM-dd HH:mm:ss"));
            deviceFaultVos.add(deviceFaultVo);
        }
        return apiResponse;
    }

    @ApiOperation(value = "app设备历史故障", notes = "设备故障--app获取历史故障")
    @RequestMapping(value = "/devices/faultsHis", method = RequestMethod.GET)
    public ApiResponse<List<DeviceFaultVo>> getAppDeviceHisFaultList() {
        String uid = appUserService.getUid();
        return deviceFaultInnerService.findAppDeviceHisFaultList(uid);
    }
}
