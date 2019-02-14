package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.vo.PushRecordVo;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.ConsumerPushRecordService;
import com.auxgroup.smarthome.app.service.inner.PushRecordInnerService;
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

import java.util.List;

/**
 * Created by root on 17-10-16.
 */
@Api(description = "推送记录", tags = "推送记录")
@RestController
@RequestMapping("/pushRecord")
public class ConsumerPushRecordController {

    @Autowired
    private PushRecordInnerService pushRecordInnerService;

    @Autowired
    private ConsumerPushRecordService consumerPushRecordService;

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "获取推送记录", notes = "获取推送记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "uid", paramType = "path", dataType = "string", required = true),
            @ApiImplicitParam(name = "time", value = "time 格式YYYY-mm-dd HH24:mi:ss", paramType = "query", dataType = "string", required = true),
    })
    @RequestMapping(value = "/list/{uid}", method = RequestMethod.GET)
    public ApiResponse<List<PushRecordVo>> findAllByUid(@PathVariable String uid, @RequestParam(value = "time") String time) {
        return pushRecordInnerService.findAllByUid(uid, time);
    }


    //获取推送记录返回json格式修改 修改人：吴建龙 修改日期：2018-05-12
    @ApiOperation(value = "获取推送记录new", notes = "获取推送记录new")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "uid", paramType = "path", dataType = "string", required = true),
            @ApiImplicitParam(name = "time", value = "time 格式YYYY-mm-dd HH24:mi:ss", paramType = "query", dataType = "string", required = true),
    })
    @RequestMapping(value = "/listNew/{uid}", method = RequestMethod.GET)
    public ApiResponse findAllByUidNew(@PathVariable String uid, @RequestParam(value = "time") String time) {
        return consumerPushRecordService.findConsumerPushRecord(uid, time);
    }

    @ApiOperation(value = "获取未阅读的推送消息记录数", notes = "获取未阅读的推送消息记录数")
    @RequestMapping(value = "/notReadCount", method = RequestMethod.GET)
    public ApiResponse<Integer> findNotReadCountByUid() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return pushRecordInnerService.findNotReadCountByUid(uid);
    }

    @ApiIgnore
    @ApiOperation(value = "更新推送消息阅读状态为已阅读", notes = "更新推送消息阅读状态为已阅读")
    @RequestMapping(value = "/updateReadState", method = RequestMethod.PUT)
    public ApiResponse updateReadState(@RequestBody List<String> recordIdList) {
        return pushRecordInnerService.updateReadState(recordIdList);
    }

    @ApiOperation(value = "更新所有未读消息为已读状态", notes = "更新所有未读消息为已读状态")
    @RequestMapping(value = "/updateAllReadState", method = RequestMethod.PUT)
    public ApiResponse<Integer> updateAllReadState() {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        return pushRecordInnerService.updateAllReadState(uid);
    }

    @ApiIgnore
    @ApiOperation(value = "保存推送记录", notes = "保存推送记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "uid", paramType = "path", dataType = "string", required = true),
            @ApiImplicitParam(name = "body", value = "body", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "title", value = "title", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "sourceType", value = "sourceType", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "imageUrl", value = "imageUrl", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "linkedUrl", value = "linkedUrl", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "sourceValue", value = "sourceValue", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "type", value = "type", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "platform", value = "platform", paramType = "query", dataType = "string", required = false),
    })
    @RequestMapping(value = "/savePushRecord/{uid}", method = RequestMethod.POST)
    public ApiResponse savePushRecord(@PathVariable String uid, String body, String title, String sourceType, String imageUrl, String linkedUrl, String sourceValue, String type, String platform) {
        return pushRecordInnerService.savePushRecord(uid, body, title, sourceType, imageUrl, linkedUrl, sourceValue, type, platform);
    }

}
