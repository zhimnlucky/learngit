package com.example.appsupport.smarthome.app.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.inner.AppOtherFeatureInnerService;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 18-11-13.
 * 附加功能controller类
 */
@Api(description = "附加功能", tags = "附加功能")
@RestController
public class OtherFeatureController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppOtherFeatureInnerService appOtherFeatureInnerService;
    /**
     * @param json_log
     * {
     * "messageId": "用来标识唯一性 uid+,+时间戳",
     * "starLevel": "星级 可为null",
     * "typeLabel": "反馈类型 中文",
     * "typeValue": "反馈类型 数字",
     * "content": "内容",
     * "phone": "手机号 必填",
     * "img": "图片地址 用,分隔"
     * }
     * @return
     */
    @ApiOperation(value = "留言功能上报(json)", notes = "留言功能上报(json)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json_log", value = "留言日志", paramType = "body", dataType = "string", required = true)
    })
    @RequestMapping(value = "/feedback_log", method = RequestMethod.POST)
    public ApiResponse feedbackLog(@RequestBody(required = true) String json_log) {
        JSONObject jsonObject = JSONObject.parseObject(json_log);
        /*if(StringUtils.isEmpty(jsonObject.getString("phone"))){
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        }*/
        jsonObject.put("uid",appUserService.getUid());

        return appOtherFeatureInnerService.feedbackLog(jsonObject.toString());
    }
}
