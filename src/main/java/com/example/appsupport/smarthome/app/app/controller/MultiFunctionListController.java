package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.business.inner.vo.MultiFunctionListVo;
import com.auxgroup.smarthome.app.service.inner.MultiFunctionListInnerService;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/10/17 14:06.
 */
@Api(tags = "设备型号", description = "多联机功能列表")
@RestController
@RequestMapping("/multi_function_list")
public class MultiFunctionListController {
    @Autowired
    private MultiFunctionListInnerService multiFunctionListInnerService;

    @ApiOperation(value = "获取多联机功能列表")
    @GetMapping("/list")
    public ApiResponse<List<MultiFunctionListVo>> getMultiFunctionList() {
        return multiFunctionListInnerService.getMultiFunctionList();
    }

}
