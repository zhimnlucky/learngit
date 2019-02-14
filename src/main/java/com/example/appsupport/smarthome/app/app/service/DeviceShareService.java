package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.FamilyCenterDeviceVo;
import com.auxgroup.bridge.app.inner.vo.ShareResultVo;
import com.auxgroup.bridge.app.inner.vo.ShareUserListVo;
import com.auxgroup.bridge.app.inner.vo.SubShareUserVo;
import com.auxgroup.smarthome.app.filter.PermissionAppService;
import com.auxgroup.smarthome.app.service.inner.DeviceShareInnerService;
import com.auxgroup.smarthome.app.vo.ClipShareDataVo;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.openapi.responsebody.UserTokenMsg;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/7/29 9:22.
 */
@Service
public final class DeviceShareService {
    @Autowired
    private DeviceShareInnerService deviceShareInnerService;
    @Autowired
    private PermissionAppService permissionAppService;
    @Autowired
    private ObjectRedis objectRedis;

    public ApiResponse<List<ShareUserListVo>> getShareUserList(String deviceId) {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.getSharing(userTokenMsg.getUid(), deviceId);
    }

    public ApiResponse<List<SubShareUserVo>> getSubShareUserList() {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.getSubShareUserList(userTokenMsg.getUid());
    }

    public ApiResponse<ShareResultVo> createSharing(String deviceIds, Integer userTag) {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);

        return deviceShareInnerService.createSharing(userTokenMsg.getUid(), deviceIds, userTag);
    }

    public ApiResponse relieveSharing(String shareId) {
        String appId = permissionAppService.getAppId();
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.relieveSharing(appId, userTokenMsg.getToken(), shareId);
    }

    public ApiResponse scanSharing(String qrContent) {
        String appId = permissionAppService.getAppId();
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.scanSharing(appId, userTokenMsg.getToken(), userTokenMsg.getUid(), qrContent);
    }

    public ApiResponse getFamilySharing() {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.getFamilySharing(userTokenMsg.getUid());
    }

    public ApiResponse getFriendSharing() {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.getFriendSharing(userTokenMsg.getUid());
    }

    public ApiResponse<List<FamilyCenterDeviceVo>> getUserShareDeviceList(String uid, Integer userTag, String batchNo) {
        String clientId = permissionAppService.getClientId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.getUserShareDeviceList(userTokenMsg.getUid(), uid, userTag, batchNo);
    }

    public ApiResponse relieveUserSharingByOwner(String uid, Integer userTag, String batchNo, String deviceId) {
        String clientId = permissionAppService.getClientId();
        String appId = permissionAppService.getAppId();
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        return deviceShareInnerService.relieveUserSharingByOwner(appId, userTokenMsg.getToken(), userTokenMsg.getUid(), uid, userTag, batchNo, deviceId);
    }

    /**
     * 第三方登录分享
     *
     * @param uid
     * @param deviceIds
     * @param userTag
     * @return
     */
    public ApiResponse<ShareResultVo> createSharingOtherLogin(String uid, String deviceIds, Integer userTag) {
        return deviceShareInnerService.createSharing(uid, deviceIds, userTag);
    }


    public ApiResponse<ShareResultVo> createClipbordShare(String deviceName, String name, String qrContent, String ownerUid) {
        if (StringUtils.isEmpty(deviceName)) {
            deviceName = "奥克斯空调";
        }
        String uuid = UUID.randomUUID().toString();
        String redisKey = CachedConsant.DEVICE_SHARE_CLIPBORD_PREFIX + uuid;

        ClipShareDataVo clipShareDataVo = new ClipShareDataVo();
        clipShareDataVo.setDeviceName(deviceName);
        clipShareDataVo.setName(name);
        clipShareDataVo.setQrContent(qrContent);
        clipShareDataVo.setOwnerUid(ownerUid);
        objectRedis.add(redisKey, 15l, clipShareDataVo);
        String s = "【" + "奥克斯空调" + "】，长按复制这段文本描述@aux" + uuid + "@后打开奥克斯A+ App，即可绑定设备";
        return ApiResponse.ok(new ShareResultVo(s));
    }

    public ApiResponse<ShareResultVo> decodeClipbordShare(String clipbordShareData) {
//        String s = "【" + deviceName + "】，復·制这段描述@aux:" + uuid + "@后到奥克斯A+ 打开 ♂♀";
        int indexOf = clipbordShareData.indexOf("@aux");
        int indexOf2 = clipbordShareData.indexOf("@", indexOf + 1);
        if (indexOf > -1) {
            String uuid = clipbordShareData.substring(indexOf + 4, indexOf2);
            String redisKey = CachedConsant.DEVICE_SHARE_CLIPBORD_PREFIX + uuid;
            ClipShareDataVo clipShareDataVo = objectRedis.get(redisKey, ClipShareDataVo.class);
            if (clipShareDataVo == null || StringUtil.isBlank(clipShareDataVo.getQrContent()))
                return ApiResponse.prompt(Syscode.DEVICE_SHARE_QRCONTENT_EXPIRAT);

            return scanSharing(clipShareDataVo.getQrContent());
        }

        return ApiResponse.prompt(Syscode.DEVICE_SHARE_QRCONTENT_INCORRECT);

    }

    public ApiResponse<ClipShareDataVo> getClipbordShare(String clipbordShareData) {
        int indexOf = clipbordShareData.indexOf("@aux");
        int indexOf2 = clipbordShareData.indexOf("@", indexOf + 1);
        if (indexOf > -1) {
            String uuid = clipbordShareData.substring(indexOf + 4, indexOf2);
            String redisKey = CachedConsant.DEVICE_SHARE_CLIPBORD_PREFIX + uuid;
            ClipShareDataVo clipShareDataVo = objectRedis.get(redisKey, ClipShareDataVo.class);
            if (clipShareDataVo == null || StringUtil.isBlank(clipShareDataVo.getQrContent()))
                return ApiResponse.prompt(Syscode.DEVICE_SHARE_QRCONTENT_EXPIRAT);

            if (StringUtils.isEmpty(clipShareDataVo.getDeviceName())) {
                clipShareDataVo.setDeviceName("奥克斯空调");
            }
            clipShareDataVo.setDescription(clipShareDataVo.getName() + "向您共享设备\n" + clipShareDataVo.getDeviceName());
            return ApiResponse.ok(clipShareDataVo);
        }
        return ApiResponse.prompt(Syscode.DEVICE_SHARE_QRCONTENT_INCORRECT);
    }
}
