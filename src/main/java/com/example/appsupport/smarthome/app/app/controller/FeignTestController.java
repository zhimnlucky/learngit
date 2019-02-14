package com.example.appsupport.smarthome.app.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.auxgroup.smarthome.app.service.inner.FeignTestService;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiuhua Lai
 */
@RestController
public class FeignTestController {
    @Autowired
    private FeignTestService feignTestService;

    @GetMapping(value = "/test-feign")
    @PermissionFilter(filter = true)
    public ApiResponse test() {
        String send = "I'm app, 我正在请求inner, 收到请回复!";
        System.out.printf("app发送内容："+send);
        ApiResponse apiResponse = feignTestService.testFeign(send);
        System.out.printf(JSONObject.toJSONString(apiResponse));
        return apiResponse;
    }


}
