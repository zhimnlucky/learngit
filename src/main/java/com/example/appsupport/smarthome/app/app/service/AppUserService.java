package com.example.appsupport.smarthome.app.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auxgroup.bridge.app.inner.dto.AppUserDto;
import com.auxgroup.bridge.app.inner.vo.AppDeviceInfoListVo;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.bridge.app.inner.vo.ShareResultVo;
import com.auxgroup.jpush.JpushService;
import com.auxgroup.jpush.entity.JpushEntity;
import com.auxgroup.smarthome.BeanUtils;
import com.auxgroup.smarthome.app.filter.PermissionAppService;
import com.auxgroup.smarthome.app.service.inner.AppSceneInnerService;
import com.auxgroup.smarthome.app.service.inner.AppUserInnerService;
import com.auxgroup.smarthome.app.service.inner.DeviceInfoInnerService;
import com.auxgroup.smarthome.app.util.WechatUtil;
import com.auxgroup.smarthome.app.vo.*;
import com.auxgroup.smarthome.appconst.AppConstant;
import com.auxgroup.smarthome.constant.cache.CachedConsant;
import com.auxgroup.smarthome.jwt.JwtConstant;
import com.auxgroup.smarthome.jwt.JwtUtils;
import com.auxgroup.smarthome.openapi.OpenApi;
import com.auxgroup.smarthome.openapi.responsebody.RequestTokenResult;
import com.auxgroup.smarthome.openapi.responsebody.UserTokenMsg;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.regex.RegexUtils;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.OpenApiErrorCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.utils.common.LOG;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.auxgroup.smarthome.syscode.AppCode.*;

/**
 * Created by kevinchen on 2017/3/23.
 */
@Service
public class AppUserService {
    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    private AppUserInnerService appUserInnerService;
    @Autowired
    private PermissionAppService permissionAppService;
    @Autowired
    private DeviceShareService deviceShareService;
    @Autowired
    private DeviceInfoInnerService deviceInfoInnerService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectRedis objectRedis;
    @Autowired
    private AppSceneInnerService appSceneInnerService;
    @Autowired
    private AppUserService appUserService;

    /**
     * 用户登录默认过期时间为30天
     */
    private final long USER_LOGIN_EXPIRED_TIME = 60L * 24 * 30;

    // 后面看需要再修改，保留
    private final String APP_USER_REDIS_PREFIX = CachedConsant.APP_USER_REDIS_PREFIX;

    /**
     * 获取app终端访问SaaS后台的accessToken
     *
     * @param clientId
     * @param appid
     * @return
     */
    public ApiResponse<AccessTokenVo> getAccessToken(String clientId, String appid) {
        if (StringUtils.isBlank(clientId) || StringUtils.isBlank(appid)) {
            return ApiResponse.prompt(AppCode.FAIL);
        }
        String accessToken = "";
        long nowMillis = System.currentTimeMillis();
        try {
            accessToken = JwtUtils.createJWT(JwtConstant.APP_JWT_SECRET, nowMillis, clientId, appid, JwtConstant.APP_JWT_TTL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.ok(new AccessTokenVo(accessToken, nowMillis + JwtConstant.APP_JWT_TTL));
    }

    /**
     * 通过手机注册用户
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    public ApiResponse<LoginUserVo> registered(String phone, String password, String code) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        if (!RegexUtils.checkPasswordComplexity(password)) {
            return ApiResponse.prompt(AppCode.PASSWORD_COMPLEXITY_CHECK);
        }
        String clientId = permissionAppService.getClientId();
        String appId = permissionAppService.getAppId();
        String ip = permissionAppService.getIp();
        UserTokenMsg userTokenMsg = OpenApi.registerPhone(appId, phone, password, code);
        if (StringUtils.isNotBlank(userTokenMsg.getError_code())) {
            LOG.error(this, "用户注册失败信息" + userTokenMsg.getError_code() + " ," + userTokenMsg.getError_message());
            return OpenApiErrorCode.prompErrorCode(userTokenMsg.getError_code());
        }
        ApiResponse<AppUserVo> appUser = appUserInnerService.getAppUser(userTokenMsg.getUid());
        if (appUser.getData() != null) {
            return ApiResponse.prompt(PHONE_REGISTERED);
        }
        ApiResponse<AppUserVo> response = appUserInnerService.createAppUser(userTokenMsg.getUid(), phone);
        if (response.getData() == null) {
            return ApiResponse.prompt(SYSTEM_EXCEPTION);
        }
        //begin 注册记录登录信息 add by lixiaoxiao 20171019
        AppUserVo user = response.getData();
        appUserInnerService.recordLoginInfo(user.getUid(), clientId, ip);
        //end 注册记录登录信息 add by lixiaoxiao 20171019
        objectRedis.add(clientId, USER_LOGIN_EXPIRED_TIME, userTokenMsg);
        AppUserVo appUserVo = new AppUserVo(userTokenMsg.getUid(), phone);
        objectRedis.add(userTokenMsg.getUid(), USER_LOGIN_EXPIRED_TIME, appUserVo);
        LoginUserVo loginUserInfo = new LoginUserVo(appUserVo, new TokenVo(userTokenMsg.getToken(), userTokenMsg.getExpireAt()));
        loginRecordClientId(user.getUid(), clientId);
        //初始化个人预制场景
        if (!isExistPreScene(user.getUid()))
            appSceneInnerService.createUserInitScene(userTokenMsg.getUid());
        return ApiResponse.ok(loginUserInfo);
    }

    /**
     * app 用户登录
     *
     * @param phone
     * @param password
     * @return
     */
    public ApiResponse<LoginUserVo> login(String phone, String password) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        String appId = permissionAppService.getAppId();
        String clientId = permissionAppService.getClientId();
        String ip = permissionAppService.getIp();
        UserTokenMsg userTokenMsg = OpenApi.loginPhone(appId, phone, password);
        if (StringUtils.isNotBlank(userTokenMsg.getError_code())) {
            return OpenApiErrorCode.prompErrorCode(userTokenMsg.getError_code());
        }
        //同步用户信息
        String uid = userTokenMsg.getUid();
        String oldClientId = getOldDeviceClientId(uid);
        ApiResponse<AppUserVo> response = appUserInnerService.getAppUser(uid);
        if (response.getData() == null) {
            response = appUserInnerService.createAppUser(userTokenMsg.getUid(), phone);
            if (response.getData() == null) {
                return ApiResponse.prompt(CREATE_USER_FAIL);
            }
        }
        //不处理返回信息，即使记录失败，也要保证能登录
        appUserInnerService.recordLoginInfo(uid, clientId, ip);
        objectRedis.add(clientId, USER_LOGIN_EXPIRED_TIME, userTokenMsg);
        objectRedis.add(uid, USER_LOGIN_EXPIRED_TIME, response.getData());
        LoginUserVo loginUserInfo = new LoginUserVo(response.getData(), new TokenVo(userTokenMsg.getToken(), userTokenMsg.getExpireAt()));
        // 单机登录推送
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 是否同一设备登录
                    boolean flag = isDifferentDeviceLogin(oldClientId, clientId);
                    // 清除单台设备的其他用户登录
                    clearOtherUserRecordClientId(uid, clientId);
                    if (flag) {
                        logout(oldClientId);
                        if (!clearOtherUserRecordClientId(uid, oldClientId)) {
                            pushOldClientLogout(oldClientId);
                        }
                    }
                    loginRecordClientId(uid,clientId);
                } catch (Exception e) {
                    LOG.error(this, "单点推送登录", e);
                }
            }
        }).start();*/
        //初始化个人预制场景
        if (!isExistPreScene(uid))
            appSceneInnerService.createUserInitScene(uid);
        return ApiResponse.ok(loginUserInfo);
    }

    /**
     * 业务缓存标识  标记前缀带有此标记的表示此缓存只用于 ** 业务使用
     */
    public static final String LOGIN_RECORD_CLIENTID = "login_record_clientid_";

    /**
     * 用户登录时缓存 app用户使用的是哪一台设备
     *
     * @param uid      app用户
     * @param clientId 即表示用户具体使用的哪一台设备
     */
    private void loginRecordClientId(String uid, String clientId) {
        redisTemplate.opsForValue().set(LOGIN_RECORD_CLIENTID + uid, clientId, USER_LOGIN_EXPIRED_TIME, TimeUnit.MINUTES);
    }

    /**
     * 获取用户上一次登录 的ClientId
     *
     * @param uid
     * @return
     */
    private String getOldDeviceClientId(String uid) {
        return redisTemplate.opsForValue().get(LOGIN_RECORD_CLIENTID + uid);
    }

    /**
     * 是否同一设备登录
     *
     * @param oldClientId
     * @param currClientId
     * @return true: 不同设备  false:同一台设备
     */
    private boolean isDifferentDeviceLogin(String oldClientId, String currClientId) {
        return oldClientId == null ? false : currClientId.compareTo(oldClientId) != 0;
    }

    /**
     * 单台设备同时只能允许一个用户登录，针对用户登录后卸载的行为，嗯，好吧，没考虑全面，退出这台设备登录的其他用户
     * 存在一种情况，用户登录后直接卸载App，再安装app,这时候登录另一个账号，原有账号还是登录在此设备下的，原因账号在其他设备端登录
     * 会把现有账号挤下线
     *
     * @param uid
     * @param clientId
     */
    private boolean clearOtherUserRecordClientId(String uid, String clientId) {
        Set<String> allKeys = objectRedis.getAllKeys(LOGIN_RECORD_CLIENTID + "*");
        allKeys.remove(LOGIN_RECORD_CLIENTID + uid);
        boolean flag = false;
        for (String key : allKeys) {
            String loginRecord = redisTemplate.opsForValue().get(key);
            if (Objects.equals(loginRecord, clientId)) {
                objectRedis.delete(key);
                flag = true;
                logger.info("删除客户端登录用户key:{}", key);
            }
        }
        return flag;
    }

    @Autowired
    private JpushService jpushService;

    /**
     * 原设备app应用强制退出。
     *
     * @param clientId
     */
    private void pushOldClientLogout(String clientId) {
        JpushEntity jpushEntity = new JpushEntity("退出系统", "你在另一台设备登录使用该账号，本机账号强制退出。", null);
        jpushEntity.setCategory("9");
        jpushService.pushByTag(jpushEntity, clientId);
        logger.info("客户端Id:{}强制退出应用", clientId);
    }


    /**
     * 更新用户信息
     *
     * @param appUser
     * @return
     */
    public ApiResponse<AppUserVo> updateAppUser(AppUserDto appUser) {
        return appUserInnerService.updateAppUser(getUid(), appUser);
    }

    public ApiResponse resetPassword(String phone, String newPwd, String code) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        if (!RegexUtils.checkPasswordComplexity(newPwd)) {
            return ApiResponse.prompt(AppCode.PASSWORD_COMPLEXITY_CHECK);
        }
        String appId = permissionAppService.getAppId();
        JSONObject res = OpenApi.resetPassword(appId, phone, newPwd, code);
        if (res == null) {
            return ApiResponse.prompt(RESET_PASSWORD_FAIL);
        }
        if (res.getString("error_code") != null) {
            return OpenApiErrorCode.prompErrorCode(res.getString("error_code"));
        }
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public ApiResponse modifyPassword(String oldPwd, String newPwd) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        if (!RegexUtils.checkPasswordComplexity(newPwd)) {
            return ApiResponse.prompt(AppCode.PASSWORD_COMPLEXITY_CHECK);
        }
        String appId = permissionAppService.getAppId();
        String token = getUserToken().getToken();

        JSONObject res = OpenApi.modifyPassword(appId, token, oldPwd, newPwd);
        if (res == null) {
            return ApiResponse.prompt(MODIFY_PASSWORD_FAIL);
        }

        if (res.getString("error_code") != null) {
            return ApiResponse.prompt(AppCode.OLD_PASSWORD_ERROR);
        }
        return ApiResponse.prompt(SC_OK);
    }

    /**
     * 登出
     *
     * @return
     */
    public ApiResponse logout() {
        String clientId = permissionAppService.getClientId();
        logout(clientId);
        return ApiResponse.prompt(AppCode.SC_OK);
    }

    public ApiResponse logout(String clientId) {
        UserTokenMsg userTokenMsg = objectRedis.get(clientId, UserTokenMsg.class);
        String uid = userTokenMsg.getUid();
        objectRedis.delete(clientId);
        objectRedis.delete(LOGIN_RECORD_CLIENTID + uid);
        return ApiResponse.prompt(AppCode.SC_OK);
    }


    /**
     * 获取短信验证码： 测试用
     *
     * @param phone
     * @return
     */
    public ApiResponse getSmsCode(String phone, String code) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        String appId = permissionAppService.getAppId();
        String secret = AppConstant.APP_ID_SECRET_MAP.get(appId);
        RequestTokenResult requestTokenResult = OpenApi.requestToken(appId, secret);
        if (requestTokenResult.getToken() == null) {
            return ApiResponse.prompt(requestTokenResult.getError_code(), requestTokenResult.getError_message());
        }
        String res = OpenApi.requestSmsCode(appId, requestTokenResult.getToken(), phone, code);
        LOG.info(this, "手机号" + phone + "调用paas短信接口返回数据：" + res);
        if (!"{}".equals(res)) {
            LOG.info(this, "短信发送失败：" + res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String detailMessage = jsonObject.getString("detail_message");
            String errorCode = jsonObject.getString("error_code");
            return ApiResponse.prompt(errorCode, detailMessage);
        }

        return ApiResponse.ok(AppCode.SC_OK);
    }

    /**
     * 从缓存中获取用户信息
     *
     * @return
     */
    public ApiResponse<AppUserVo> getAppUser() {
        UserTokenMsg userToken = getUserToken();
        if (userToken == null) {
            return ApiResponse.prompt(AppCode.FAIL);
        }
        String userJson = redisTemplate.opsForValue().get(userToken.getUid());
        if (StringUtils.isBlank(userJson)) {
            return ApiResponse.prompt(AppCode.FAIL);
        }
        AppUserVo appUserVo = JSON.parseObject(userJson, AppUserVo.class);
        return ApiResponse.ok(appUserVo);
    }

    /**
     * 从redis缓存里面拿到openApi的访问token及用户uid。
     *
     * @return
     */
    public UserTokenMsg getUserToken() {
        String clientId = permissionAppService.getClientId();
        String tokenStr = redisTemplate.opsForValue().get(clientId);
        return JSON.parseObject(tokenStr, UserTokenMsg.class);
    }

    public String getUid() {
        UserTokenMsg userToken = getUserToken();
        return userToken != null ? userToken.getUid() : null;
    }

    /**
     * 微信登录
     * 20180410
     *
     * @param code
     * @param os_type
     * @return
     */
    public ApiResponse<LoginUserVo> wechatLogin(String code, String os_type, String src) {
        //1.通过token获取微信信息
        WechatUserInfo wechatUserInfo = WechatUtil.getWechatUserInfo(code, os_type);
        if (wechatUserInfo.getErrcode() != 0) {
            return new ApiResponse(wechatUserInfo.getErrcode(), wechatUserInfo.getErrmsg());
        }
        //2.查询微信号是否存在,存在则直接登录,不存在返回提示是否绑定手机号
        ApiResponse<List<AppUserVo>> listApiResponse = appUserInnerService.findOtherUser(wechatUserInfo.getOpenid(), "");
        if (listApiResponse.getData() != null && listApiResponse.getData().size() > 0) {
            //第三方登录调用用户创建接口登录
            return registerFlow(src, wechatUserInfo.getOpenid(), wechatUserInfo.getUnionid(), wechatUserInfo.getToken(),
                    wechatUserInfo.getNickname(), wechatUserInfo.getHeadimgurl(), listApiResponse.getData().get(0).getPhone());
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", wechatUserInfo.getToken());
            jsonObject.put("openid", wechatUserInfo.getOpenid());
            return new ApiResponse(Syscode.SC_OK.getCode(), "是否绑定手机号", jsonObject);
        }
    }

    /**
     * qq登录
     * 20180416
     *
     * @param token
     * @param openid
     * @param os_type
     * @return
     */
    public ApiResponse<LoginUserVo> qqLogin(String src, String token, String openid, String os_type) {
        QQUserInfo qqUserInfo = WechatUtil.getQqUserInfo(openid, token, os_type);
        if (qqUserInfo.getRet() != 0) {
            return new ApiResponse(qqUserInfo.getRet(), qqUserInfo.getMsg());
        }
        //查询qq号是否存在,存在则直接登录,不存在返回提示是否绑定手机号
        ApiResponse<List<AppUserVo>> listApiResponse = appUserInnerService.findOtherUser("", openid);
        if (listApiResponse.getData() != null && listApiResponse.getData().size() > 0) {
            //第三方登录调用用户创建接口登录
            return registerFlow(src, openid, "", token, qqUserInfo.getNickname(), qqUserInfo.getFigureurl_qq_2(), listApiResponse.getData().get(0).getPhone());
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("true", "是否绑定手机号");
            return new ApiResponse(Syscode.SC_OK.getCode(), "是否绑定手机号", jsonObject);
        }
    }

    /**
     * 微信登录(适配3.0.3 支持手机号跳过)
     * 20180609
     *
     * @param code
     * @param os_type
     * @param src
     * @return
     */
    public ApiResponse<LoginUserVo> wechatLoginSkipPhone(String code, String os_type, String src) {
        //1.通过token获取微信信息
        WechatUserInfo wechatUserInfo = WechatUtil.getWechatUserInfo(code, os_type);
        if (wechatUserInfo.getErrcode() != 0) {
            return new ApiResponse(wechatUserInfo.getErrcode(), wechatUserInfo.getErrmsg());
        }
        ApiResponse<List<AppUserVo>> listApiResponse = appUserInnerService.findOtherUser(wechatUserInfo.getOpenid(), "");
        //第三方登录调用用户创建接口登录
        ApiResponse<LoginUserVo> loginUserVo = registerFlow(src, wechatUserInfo.getOpenid(), wechatUserInfo.getUnionid(), wechatUserInfo.getToken(),
                wechatUserInfo.getNickname(), wechatUserInfo.getHeadimgurl(), listApiResponse.getData().size() > 0 ? listApiResponse.getData().get(0).getPhone() : "");
        boolean isJustRegister = false;
        if (listApiResponse.getData().size() > 0) {
            isJustRegister = true;
        }
        loginUserVo.getData().getAppUser().setJustRegister(isJustRegister);
        return loginUserVo;
    }

    /**
     * qq登录(适配3.0.3 支持手机号跳过)
     * 20180609
     *
     * @param token
     * @param openid
     * @param os_type
     * @return
     */
    public ApiResponse<LoginUserVo> qqLoginSkipPhone(String src, String token, String openid, String os_type) {
        QQUserInfo qqUserInfo = WechatUtil.getQqUserInfo(openid, token, os_type);
        if (qqUserInfo.getRet() != 0) {
            return new ApiResponse(qqUserInfo.getRet(), qqUserInfo.getMsg());
        }
        ApiResponse<List<AppUserVo>> listApiResponse = appUserInnerService.findOtherUser("", openid);
        //第三方登录调用用户创建接口登录
        ApiResponse<LoginUserVo> loginUserVo = registerFlow(src, openid, "", token, qqUserInfo.getNickname(),
                qqUserInfo.getFigureurl_qq_2(), listApiResponse.getData().size() > 0 ? listApiResponse.getData().get(0).getPhone() : "");
        boolean isJustRegister = false;
        if (listApiResponse.getData().size() > 0) {
            isJustRegister = true;
        }
        loginUserVo.getData().getAppUser().setJustRegister(isJustRegister);
        return loginUserVo;
    }

    /**
     * 第三方绑定手机号
     * 20180410
     *
     * @param src
     * @param nickName
     * @param headImg
     * @param openid
     * @param phone
     * @param smsCode
     * @return
     */
    public ApiResponse<LoginUserVo> bindOther(String src, String nickName, String headImg,
                                              String token, String openid, String unionid, String phone, String smsCode) {
        if (permissionAppService.isAccessTokenInvaild()) {
            return ApiResponse.prompt(AppCode.JWT_TOKEN_INVALID);
        }
        if (StringUtils.isEmpty(phone)) {
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        }
        //如果手机号和验证码不为空,验证手机号,验证码正确时,把该手机下的设备分享到第三方帐号下
        ApiResponse apiResponse = getSmsCode(phone, smsCode);
        if (apiResponse.getCode() != AppCode.SC_OK.getCode()) {
            return ApiResponse.prompt(AppCode.INVALID_CODE);
        }
        ApiResponse<LoginUserVo> loginUserVoApiResponse = registerFlow(src, openid, unionid, token, nickName, headImg, phone);
        if (loginUserVoApiResponse.getCode() != AppCode.SC_OK.getCode()) {
            return loginUserVoApiResponse;
        }
        ApiResponse<AppUserVo> appUserVoApiResponse = appUserInnerService.getAppUserByPhone(phone);
        if (null != appUserVoApiResponse.getData()) {
            //查找用户下所有设备并以家人方式分享给第三方帐号
            shareDevice(appUserVoApiResponse.getData());
        }
        LoginUserVo loginUserVo = loginUserVoApiResponse.getData();
        if (loginUserVo != null) {
            if ("qq".equals(src)) {
                appUserInnerService.bindOtherAccount(
                        loginUserVo.getAppUser().getUid(), loginUserVo.getAppUser().getOpenid(), openid, phone);
            } else if ("wechat".equals(src)) {
                appUserInnerService.bindOtherAccount(
                        loginUserVo.getAppUser().getUid(), openid, loginUserVo.getAppUser().getQqid(), phone);
            }
        }
        return loginUserVoApiResponse;
    }

    private ApiResponse<LoginUserVo> registerFlow(String src, String openid, String unionid, String token, String nickName, String headImg, String phone) {
        String clientId = permissionAppService.getClientId();
        String appId = permissionAppService.getAppId();
        String ip = permissionAppService.getIp();
        UserTokenMsg userTokenMsg = OpenApi.registerBySrc(appId, src, openid, token);
        if (StringUtils.isNotBlank(userTokenMsg.getError_code())) {
            LOG.error(this, "用户注册失败信息" + userTokenMsg.getError_code() + " ," + userTokenMsg.getError_message());
            return OpenApiErrorCode.prompErrorCode(userTokenMsg.getError_code());
        }
        ApiResponse<AppUserVo> appUser = appUserInnerService.getAppUser(userTokenMsg.getUid());
        AppUserVo user = appUser.getData();
        if (user == null) {
            ApiResponse<AppUserVo> response = appUserInnerService.createOtherUser(
                    userTokenMsg.getUid(), openid, unionid, nickName, headImg, src);
            if (response.getData() == null) {
                return ApiResponse.prompt(SYSTEM_EXCEPTION);
            }
            user = response.getData();
        } else {
            if (StringUtils.isEmpty(user.getUnionid()) && "wechat".equals(src)) {
                user.setUnionid(unionid);
                appUserInnerService.updateAppUser(appUser.getData().getUid(), BeanUtils.copyAttrs(new AppUserDto(), user));
            }
        }
        //begin 注册记录登录信息 add by lixiaoxiao 20171019
        user.setPhone(phone);
        appUserInnerService.recordLoginInfo(user.getUid(), clientId, ip);
        //end 注册记录登录信息 add by lixiaoxiao 20171019
        objectRedis.add(clientId, USER_LOGIN_EXPIRED_TIME, userTokenMsg);
        objectRedis.add(userTokenMsg.getUid(), USER_LOGIN_EXPIRED_TIME, user);
        LoginUserVo loginUserInfo = new LoginUserVo(user, new TokenVo(userTokenMsg.getToken(), userTokenMsg.getExpireAt()));
        loginRecordClientId(user.getUid(), clientId);

        if (!isExistPreScene(user.getUid()))
            appSceneInnerService.createUserInitScene(user.getUid());

        return ApiResponse.ok(loginUserInfo);
    }

    private boolean isExistPreScene(String uid) {
        ApiResponse<Boolean> response = appSceneInnerService.isExistPreScene(uid);
        return response.getData();
    }

    /**
     * 第三方绑定手机号(适配3.0.3 支持手机号跳过)
     * 20180609
     *
     * @param phone
     * @param smsCode
     * @return
     */
    public ApiResponse bindOtherSkipPhone(String phone, String smsCode) {
        UserTokenMsg userTokenMsg = objectRedis.get(permissionAppService.getClientId(), UserTokenMsg.class);
        if (null == userTokenMsg) {
            return ApiResponse.prompt(AppCode.UID_IS_NULL);
        }
        if (StringUtils.isEmpty(phone)) {
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        }
        //如果手机号和验证码不为空,验证手机号,验证码正确时,把该手机下的设备分享到第三方帐号下
        ApiResponse apiResponse = getSmsCode(phone, smsCode);
        if (apiResponse.getCode() != AppCode.SC_OK.getCode()) {
            return ApiResponse.prompt(AppCode.INVALID_CODE);
        }
        ApiResponse<AppUserVo> appUserVoApiResponse = appUserInnerService.getAppUser(userTokenMsg.getUid());
        AppUserVo user = appUserVoApiResponse.getData();
        if (null != user) {
            shareDevice(appUserVoApiResponse.getData());
            appUserInnerService.bindOtherAccount(userTokenMsg.getUid(),
                    user.getOpenid(), user.getQqid(), phone);
            user.setPhone(phone);
            objectRedis.add(userTokenMsg.getUid(), USER_LOGIN_EXPIRED_TIME, user);
        }
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * 分享设备给第三方帐号
     *
     * @param appUserVo
     */
    private void shareDevice(AppUserVo appUserVo) {
        //查找用户下所有设备并以家人方式分享给第三方帐号
        StringBuilder stringBuilder = new StringBuilder();
        ApiResponse<List<AppDeviceInfoListVo>> listApiResponse = deviceInfoInnerService.
                getAppDeviceInfoList(appUserVo.getUid());
        if (listApiResponse.getData() != null && listApiResponse.getData().size() > 0) {
            listApiResponse.getData().stream().forEach(appDeviceInfoListVo -> {
                stringBuilder.append(appDeviceInfoListVo.getDeviceId() + ",");
            });
        }
        if (stringBuilder.length() > 0) {
            ApiResponse<ShareResultVo> shareResultVoApiResponse = deviceShareService.
                    createSharingOtherLogin(appUserVo.getUid(),
                            stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1), 1);
            deviceShareService.scanSharing(shareResultVoApiResponse.getData().getQrContent());
        }
    }

    public ApiResponse getPwdSmsCode(String phone) {
        if (StringUtils.isBlank(phone))
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        //验证手机号是否注册
        ApiResponse<Boolean> response = appUserInnerService.isExistPhone(phone);
        if (!response.isNotErrorCode())
            return ApiResponse.prompt(response.getCode(), response.getMessage());
        if (!response.getData())
            return ApiResponse.prompt(Syscode.PHONE_NOT_REGISTRY);
        //发送验证码
        ApiResponse apiResponse = getSmsCode(phone, null);
        if (apiResponse.getCode() != AppCode.SC_OK.getCode())
            return ApiResponse.prompt(apiResponse.getCode(), apiResponse.getMessage());

        return ApiResponse.ok();
    }

    public ApiResponse getRegistrySmsCode(String phone) {
        if (StringUtils.isBlank(phone))
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        //验证手机号是否注册
        ApiResponse<Boolean> response = appUserInnerService.isExistPhone(phone);
        if (!response.isNotErrorCode())
            return ApiResponse.prompt(response.getCode(), response.getMessage());
        if (response.getData())
            return ApiResponse.prompt(AppCode.PHONE_REGISTERED);
        //发送验证码
        ApiResponse apiResponse = getSmsCode(phone, null);
        if (apiResponse.getCode() != AppCode.SC_OK.getCode())
            return ApiResponse.prompt(apiResponse.getCode(), apiResponse.getMessage());

        return ApiResponse.ok();
    }

    public ApiResponse getBindSmsCode(String phone) {
        String uid = appUserService.getUid();
        if (StringUtils.isBlank(uid))
            return ApiResponse.prompt(Syscode.UID_ERXCEPTION.getCode(), Syscode.UID_ERXCEPTION.getMsg());
        if (StringUtils.isBlank(phone))
            return ApiResponse.prompt(AppCode.PHONE_IS_NULL);
        //验证账号是否绑定手机
        ApiResponse<Boolean> response = appUserInnerService.isBindPhone(uid);
        if (!response.isNotErrorCode())
            return ApiResponse.prompt(response.getCode(), response.getMessage());
        if (response.getData())
            return ApiResponse.prompt(AppCode.OTHER_IS_BIND_PHONE);
        //发送验证码
        ApiResponse apiResponse = getSmsCode(phone, null);
        if (apiResponse.getCode() != AppCode.SC_OK.getCode())
            return ApiResponse.prompt(apiResponse.getCode(), apiResponse.getMessage());

        return ApiResponse.ok();
    }
}
