package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.vo.*;
import com.auxgroup.smarthome.app.service.DeviceShareService;
import com.auxgroup.smarthome.app.service.inner.DeviceShareInnerService;
import com.auxgroup.smarthome.app.vo.ClipShareDataVo;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/7/27 15:13.
 */
@Api(description = "设备分享", tags = "设备管理")
@RestController
@RequestMapping("/devices")
@Validated
public class ConsumerDeviceShareController {
    @Autowired
    private DeviceShareService deviceShareService;
    @Autowired
    private DeviceShareInnerService deviceShareInnerService;

    @ApiOperation(value = "获取分享者列表", notes = "设备管理-设备分享列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "query", dataType = "string"),
    })
    @GetMapping(value = "/sharing/list")
    public ApiResponse<List<ShareUserListVo>> getShareuserList(String deviceId) {
        return deviceShareService.getShareUserList(deviceId);
    }

    @ApiOperation(value = "获取分享者子用户列表")
    @GetMapping(value = "/sharing/sub_user")
    public ApiResponse<List<SubShareUserVo>> getSubShareUserList() {
        return deviceShareService.getSubShareUserList();
    }

    @ApiOperation(value = "创建二维码分享")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceIds", value = "设备Id,多个设备用英文,隔开", paramType = "form", dataType = "string", required = true),
            @ApiImplicitParam(name = "userTag", value = "控制类型：1：家人永久控制权, 2：好友8小时控制权", paramType = "form", dataType = "int", required = true),
    })
    @PostMapping(value = "/sharing")
    public ApiResponse<ShareResultVo> createSharing(@NotBlank(message = "设备id不能为空") String deviceIds, @Range(min = 1, max = 2, message = "userTag值只能为1或2") Integer userTag) {
        return deviceShareService.createSharing(deviceIds, userTag);
    }

    @ApiOperation(value = "解除分享", notes = "设备管理-解除分享")
    @DeleteMapping(value = "/sharing/{shareId}")
    public ApiResponse relieveSharing(@PathVariable("shareId") String shareId) {
        return deviceShareService.relieveSharing(shareId);
    }

    @ApiOperation(value = "扫描二维码分享")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qrContent", value = "二维码内容", paramType = "form", dataType = "string", required = true),
    })
    @PutMapping(value = "/sharing/qrcode")
    public ApiResponse scanSharing(@NotBlank(message = "二维码内容不能为空") String qrContent) {
        return deviceShareService.scanSharing(qrContent);
    }

    @ApiOperation(value = "获取家庭中心-家人列表")
    @GetMapping(value = "/sharing/family_center/family")
    public ApiResponse<List<FamilyShareListVo>> getFamilySharing() {
        return deviceShareService.getFamilySharing();
    }

    @ApiOperation(value = "获取家庭中心-好友列表")
    @GetMapping(value = "/sharing/family_center/friends")
    public ApiResponse<List<FamilyShareListVo>> getFriendSharing() {
        return deviceShareService.getFriendSharing();
    }

    @ApiOperation(value = "获取家庭中心-用户分享-设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "uid", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "userTag", value = "用户标签：1：家人，2：好友", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "batchNo", value = "批次号", paramType = "query", dataType = "string")
    })
    @GetMapping("/sharing/family_center/devices")
    ApiResponse<List<FamilyCenterDeviceVo>> getUserShareDeviceList(@NotBlank(message = "uid不能为空") String uid, @NotNull(message = "userTag不能为空") Integer userTag, String batchNo) {
        return deviceShareService.getUserShareDeviceList(uid, userTag, batchNo);
    }

    @ApiOperation(value = "解除家庭中心-设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "uid", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "userTag", value = "用户标签：1：家人，2：好友", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "batchNo", value = "批次号, userTag为好友时必填", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "deviceId", value = "设备id", paramType = "query", dataType = "string")
    })
    @DeleteMapping("/sharing/family_center/devices/relieve")
    ApiResponse relieveUserSharingByOwner(@NotBlank(message = "uid不能为空") String uid, @NotNull(message = "userTag不能为空") Integer userTag, String batchNo, String deviceId) {
        return deviceShareService.relieveUserSharingByOwner(uid, userTag, batchNo, deviceId);
    }

    @ApiOperation(value = "生成剪切板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceName", value = "deviceName", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "name", value = "name", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(name = "qrContent", value = "qrContent", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "ownerUid", value = "ownerUid", paramType = "query", dataType = "string", required = true)

    })
    @PostMapping(value = "/sharing/createClipbordShare")
    public ApiResponse<ShareResultVo> createClipbordShare(String deviceName, String name, @NotBlank(message = "qrContent不能为空") String qrContent, @NotBlank(message = "ownerUid不能为空") String ownerUid) {
        return deviceShareService.createClipbordShare(deviceName, name, qrContent,ownerUid);
    }

    @ApiOperation(value = "根据剪切板信息分享设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clipbordShareData", value = "剪切板信息", paramType = "query", dataType = "string", required = true),
    })
    @PostMapping(value = "/sharing/decodeClipbordShare")
    public ApiResponse<ShareResultVo> decodeClipbordShare(@NotBlank(message = "clipbordShareData不能为空") String clipbordShareData) {
        return deviceShareService.decodeClipbordShare(clipbordShareData);
    }

    @ApiOperation(value = "获取剪切板信息分享设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clipbordShareData", value = "剪切板信息", paramType = "query", dataType = "string", required = true),
    })
    @PostMapping(value = "/sharing/getClipbordShare")
    public ApiResponse<ClipShareDataVo> getClipbordShare(@NotBlank(message = "clipbordShareData不能为空") String clipbordShareData) {
        return deviceShareService.getClipbordShare(clipbordShareData);
    }
}
