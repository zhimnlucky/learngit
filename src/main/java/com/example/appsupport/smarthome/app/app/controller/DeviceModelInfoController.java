package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.vo.DeviceModelInfoVo;
import com.auxgroup.bridge.app.inner.vo.DeviceModelListVo;
import com.auxgroup.bridge.business.inner.vo.BaseFeatureVo;
import com.auxgroup.smarthome.app.service.inner.BaseFeatureInnerService;
import com.auxgroup.smarthome.app.service.inner.DeviceModelInfoInnerService;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备型号接口相关
 * Created by niuGuangzhe on 2017/7/31.
 */
@Api(tags = "设备型号", description = "app设备型号相关")
@RestController
public class DeviceModelInfoController {

    @Autowired
    private DeviceModelInfoInnerService deviceModelInfoInnerService;
    @Autowired
    private BaseFeatureInnerService baseFeatureInnerService;

    @ApiOperation(value = "查找设备型号", notes = "设备列表-查找设备型号信息")
    @RequestMapping(value = "/device/model/one", method = RequestMethod.GET)
    public ApiResponse<DeviceModelInfoVo> getDeviceModelInfoVosByOne(@RequestParam("model") String model) {
        //前端必须输入型号才可以查询
        if (StringUtils.isBlank(model)) {
            ApiResponse.prompt(AppCode.DEVICE_MODEL_INFO_ILLEGAL);
        }

        return deviceModelInfoInnerService.getDeviceInfo(model);
    }

    @ApiOperation(value = "查找设备型号", notes = "设备列表-查找设备型号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "型号名称", paramType = "path", dataType = "string", required = true)
    })
    @RequestMapping(value = "/device/model/{model}", method = RequestMethod.GET)
    public ApiResponse<DeviceModelInfoVo> getDeviceModelInfoVos(@PathVariable("model") String model) {
        //前端必须输入型号才可以查询
        if (StringUtils.isBlank(model)) {
            ApiResponse.prompt(AppCode.DEVICE_MODEL_INFO_ILLEGAL);
        }

        return deviceModelInfoInnerService.getDeviceInfo(model);
    }

    @ApiOperation(value = "扫描sn获取型号", notes = "扫描sn码得到型号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "sn码", paramType = "path", dataType = "string", required = true)
    })
    @RequestMapping(value = "/device/sn/{sn}", method = RequestMethod.GET)
    public ApiResponse<DeviceModelInfoVo> getModelBySn(@PathVariable("sn") String sn) {
        if (StringUtils.isBlank(sn)) {
            ApiResponse.ok(AppCode.DEVICE_MODEL_INFO_ILLEGAL);
        }
        ApiResponse<DeviceModelInfoVo> modelInfoVo = deviceModelInfoInnerService.getDeviceInfoBySn(sn);
        return modelInfoVo;
    }

    @ApiOperation(value ="返回所有的型号信息")
    @GetMapping(value = "/products/all_models")
    public ApiResponse<List<DeviceModelListVo>> getAllModelInfo(){
        return deviceModelInfoInnerService.getAllDeviceModelInfo();
    }

    @ApiOperation(value = "获取基础功能", notes = "")
    @RequestMapping(value = "/baseFeature", method = RequestMethod.GET)
    public ApiResponse<List<BaseFeatureVo>> findAllFeatures() {
        return baseFeatureInnerService.findAllFeatures();
    }


}
