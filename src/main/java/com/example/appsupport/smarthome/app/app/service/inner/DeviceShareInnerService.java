package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.vo.*;
import com.auxgroup.smarthome.app.service.DeviceShareServiceInnerFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/7/29 9:04.
 */
@FeignClient(name="INNER-SERVICE", fallback = DeviceShareServiceInnerFallback.class)
public interface DeviceShareInnerService {

    /**
     * 分享设备
     * @return
     */
    @PostMapping("/inner/devices/sharing")
    ApiResponse<ShareResultVo> createSharing(@RequestParam("uid") String uid, @RequestParam("deviceIds") String deviceIds, @RequestParam("userTag") Integer userTag);

    /**
     * 解除分享
     * @return
     */
    @DeleteMapping("/inner/devices/sharing")
    ApiResponse relieveSharing(@RequestParam("appid") String appid, @RequestParam("token") String token, @RequestParam("shareId") String shareId);

    /**
     * 获取设备分享用户列表
     * @param shareUid
     * @return
     */
    @GetMapping("/inner/devices/sharing/{shareUid}")
    ApiResponse<List<ShareUserListVo>> getSharing(@PathVariable("shareUid") String shareUid, @RequestParam(value = "deviceId", required = false) String deviceId);

    @GetMapping("/inner/devices/sharing/{shareUid}/sub_user")
    ApiResponse<List<SubShareUserVo>> getSubShareUserList(@PathVariable("shareUid") String shareUid);

    /**
     *  扫描二维码分享事件
     * @param appid
     * @param token
     * @param uid
     * @param qrContent
     * @return
     */
    @PutMapping("/inner/devices/sharing/qrcode")
    ApiResponse scanSharing(@RequestParam("appid") String appid, @RequestParam("token") String token, @RequestParam("uid") String uid, @RequestParam("qrContent") String qrContent);

    @ApiOperation(value = "获取家庭中心-家人列表")
    @GetMapping("/inner/devices/sharing/family_center/{uid}/family")
    ApiResponse<List<FamilyShareListVo>> getFamilySharing(@PathVariable("uid") String uid);

    @ApiOperation(value = "获取家庭中心-家人列表")
    @GetMapping("/inner/devices/sharing/family_center/{uid}/friends")
    ApiResponse<List<FamilyShareListVo>> getFriendSharing(@PathVariable("uid") String uid);

    @GetMapping("/inner/devices/sharing/family_center/devices")
    ApiResponse<List<FamilyCenterDeviceVo>> getUserShareDeviceList(@RequestParam("pUid") String pUid, @RequestParam("uid") String uid, @RequestParam("userTag") Integer userTag, @RequestParam(value = "batchNo", required = false) String batchNo);

    @DeleteMapping("/inner/devices/sharing/family_center/devices/relieve")
    ApiResponse relieveUserSharingByOwner(@RequestParam("appid") String appid, @RequestParam("token") String token, @RequestParam("pUid") String pUid, @RequestParam("uid") String uid, @RequestParam("userTag") Integer userTag, @RequestParam(value = "batchNo", required = false) String batchNo, @RequestParam(value = "deviceId", required = false) String deviceId);

}
