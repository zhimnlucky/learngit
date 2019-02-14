package com.example.appsupport.smarthome.app.app.util;

import com.alibaba.fastjson.JSON;
import com.auxgroup.smarthome.app.vo.QQUserInfo;
import com.auxgroup.smarthome.app.vo.WechatToken;
import com.auxgroup.smarthome.app.vo.WechatUserInfo;
import com.auxgroup.smarthome.utils.HttpClientUtil;
import com.auxgroup.smarthome.utils.MapJsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-4-12.
 */
public class WechatUtil {
    /**
     *微信获取token接口地址
     */
    private final static String WECHAT_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     *微信refresh_token接口地址
     */
    private final static String WECHAT_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     *微信获取用户信息接口地址
     */
    private final static String WECHAT_USER_URL = "https://api.weixin.qq.com/sns/userinfo";

    private final static String ANDROID_APP_ID = "wx56da5ea2e7839f40";

    private final static String ANDROID_APP_SECRET = "704744e6a69f2f6a20a5b834c4a323f8";

    private final static String IOS_APP_ID = "wx56da5ea2e7839f40";

    private final static String IOS_APP_SECRET = "704744e6a69f2f6a20a5b834c4a323f8";

    /**
     *qq获取用户信息接口地址
     */
    private final static String QQ_USER_URL = "https://graph.qq.com/user/get_user_info";

    public static Map<String,String> QQ_APP_ID_MAP =new HashMap<String,String>();
    static{
        QQ_APP_ID_MAP.put("IOS","1106827098");
        QQ_APP_ID_MAP.put("ANDROID","1103188588");
    }

    /**
     * 获取token
     * @param code
     * @return
     */
    public static WechatToken getAccessToken(String code,String os_type){
        String result = HttpClientUtil.httpRequest(WECHAT_TOKEN_URL,
                null,
                MapJsonUtils.put(new String[]{"code", code},
                        new String[]{"appid", os_type=="IOS"?IOS_APP_ID:ANDROID_APP_ID},
                        new String[]{"secret", os_type=="ANDROID"?IOS_APP_SECRET:ANDROID_APP_SECRET},
                        new String[]{"grant_type", "authorization_code"}),
                HttpClientUtil.MethodType.METHOD_GET);
        WechatToken wechatToken = JSON.parseObject(result, WechatToken.class);

        return wechatToken;
    }

    /**
     * 获取refresh_token
     * @param
     * @return
     */
    public static WechatToken getRefreshToken(String refresh_token,String os_type){
        String result = HttpClientUtil.httpRequest(WECHAT_TOKEN_URL,
                null,
                MapJsonUtils.put(new String[]{"refresh_token", refresh_token},
                        new String[]{"appid", os_type=="IOS"?IOS_APP_ID:ANDROID_APP_ID},
                        new String[]{"grant_type", "refresh_token"}),
                HttpClientUtil.MethodType.METHOD_GET);
        WechatToken wechatToken = JSON.parseObject(result, WechatToken.class);
        return wechatToken;
    }

    /**
     * 获取微信信息
     * @param code
     * @return
     */
    public static WechatUserInfo getWechatUserInfo(String code,String os_type){
        WechatToken wechatToken = getAccessToken(code,os_type);
        if(null!=wechatToken.getErrcode() && wechatToken.getErrcode() == 42001){
            wechatToken = getRefreshToken(wechatToken.getRefresh_token(),os_type);
            if(wechatToken.getErrcode() != 0){
                return new WechatUserInfo(wechatToken.getErrcode(),wechatToken.getErrmsg());
            }
        }else if(null!=wechatToken.getErrcode() && wechatToken.getErrcode() != 0){
            return new WechatUserInfo(wechatToken.getErrcode(),wechatToken.getErrmsg());
        }
        String result = HttpClientUtil.httpRequest(WECHAT_USER_URL,
                null,
                MapJsonUtils.put(new String[]{"access_token", wechatToken.getAccess_token()},
                        new String[]{"openid", wechatToken.getOpenid()}),
                HttpClientUtil.MethodType.METHOD_GET);
        WechatUserInfo wechatUserInfo = JSON.parseObject(result, WechatUserInfo.class);
        wechatUserInfo.setToken(wechatToken.getAccess_token());
        wechatUserInfo.setErrcode(0);
        return wechatUserInfo;
    }

    public static WechatUserInfo getWechatUserInfoByToken(String token,String openid){
        String result = HttpClientUtil.httpRequest(WECHAT_USER_URL,
                null,
                MapJsonUtils.put(new String[]{"access_token", token},
                        new String[]{"openid", openid}),
                HttpClientUtil.MethodType.METHOD_GET);
        WechatUserInfo wechatUserInfo = JSON.parseObject(result, WechatUserInfo.class);
        wechatUserInfo.setErrcode(0);
        return wechatUserInfo;
    }

    public static QQUserInfo getQqUserInfo(String openid,String token,String os_type){
        String result = HttpClientUtil.httpRequest(QQ_USER_URL,
                null,
                MapJsonUtils.put(new String[]{"openid", openid},
                        new String[]{"access_token", token},new String[]{"oauth_consumer_key",
                                QQ_APP_ID_MAP.get(os_type)}),
                HttpClientUtil.MethodType.METHOD_GET);
        QQUserInfo qqUserInfo = JSON.parseObject(result, QQUserInfo.class);
        return qqUserInfo;
    }

}
