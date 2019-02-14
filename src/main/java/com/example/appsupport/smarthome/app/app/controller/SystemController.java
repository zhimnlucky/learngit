package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/8/12 14:07.
 */
@Api(tags = "系统状态")
@RestController
public class SystemController {

    @ApiOperation(value = "所有错误码")
    @GetMapping(value = "/errorcode")
    @PermissionFilter(filter = true)
    public ApiResponse getAllCode(){
        List<Map> errors = new ArrayList();

        for (AppCode code : AppCode.values()) {
            Map<String, Object> error = new HashMap<String, Object>();
            error.put("code", code.getCode());
            error.put("msg", code.getMsg());
            errors.add(error);
        }
        return ApiResponse.ok(errors);
    }

}
