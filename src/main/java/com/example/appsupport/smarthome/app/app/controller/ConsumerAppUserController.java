package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.dto.AppUserDto;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.util.WechatUtil;
import com.auxgroup.smarthome.app.vo.AccessTokenVo;
import com.auxgroup.smarthome.app.vo.LoginUserVo;
import com.auxgroup.smarthome.app.vo.QQUserInfo;
import com.auxgroup.smarthome.app.vo.WechatUserInfo;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by kevinchen on 2017/7/21.
 */
@Api(description = "获取token", tags = "app用户相关")
@RestController
@Validated
public class ConsumerAppUserController {

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "获取token", notes = "accessToken有效期默认也是7天")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "终端唯一标识码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "appid", value = "appid", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<AccessTokenVo> getAccessToken(@ApiIgnore @NotBlank(message = "clientId不能为空") String clientId, @ApiIgnore @NotBlank(message="appid不能为空") String appid) {
        return appUserService.getAccessToken(clientId, appid);
    }

    @ApiOperation(value = "用户注册",notes = "返回用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<LoginUserVo> registered(@ApiIgnore String phone, @ApiIgnore String password, @ApiIgnore String code) {
        return appUserService.registered(phone,password,code);
    }

    @ApiOperation(value = "获取短信验证码",notes = "只传phone的时候发送验证码，传入phone和code验证手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/sms_code", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse getSmsCode(String phone, String code) {
        return appUserService.getSmsCode(phone, code);
    }

    @ApiOperation(value = "忘记密码获取短信验证码",notes = "忘记密码获取短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/pwd_sms_code", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse getPwdSmsCode(String phone) {
        return appUserService.getPwdSmsCode(phone);
    }


    @ApiOperation(value = "手机号注册获取短信验证码",notes = "手机号注册获取短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value="/registry_sms_code",method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse getRegistrySmsCode(String phone){
        return appUserService.getRegistrySmsCode(phone);
    }

    @ApiOperation(value = "第三方账号绑定手机号获取短信验证码",notes = "第三方账号绑定手机号获取短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value="/bind_sms_code",method = RequestMethod.POST)
    public ApiResponse getBindSmsCode(String phone){return appUserService.getBindSmsCode(phone);}

    @ApiOperation(value = "用户登录",notes = "返回用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册手机号码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<LoginUserVo> login(@ApiIgnore String phone, @ApiIgnore String password) {
        return appUserService.login(phone,password);
    }

    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "realName", value = "姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "headImg", value = "头像地址", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "gender", value = "性别，M、F、N", required = true,defaultValue = "M",paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "country", value = "国家", defaultValue = "中国", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "region", value = "省份", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "city", value = "城市", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ApiResponse<AppUserVo> updateCurrentUser(@ApiIgnore @Valid AppUserDto appUser) {
        return appUserService.updateAppUser(appUser);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ApiResponse<AppUserVo> getCurrentUser() {
        return appUserService.getAppUser();
    }

    @ApiOperation(value = "注销用户")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResponse logout(){
        return appUserService.logout();
    }


    @ApiOperation(value = "重置密码",notes = "通过手机重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "手机验证码", required = true, paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/reset_password", method = RequestMethod.PUT)
    @PermissionFilter(filter = true)
    public ApiResponse resetPassword(@RequestParam("phone")String phone, @RequestParam("newPassword")String newPassword, @RequestParam("code")String code) {
        return appUserService.resetPassword(phone,newPassword,code);
    }

    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/modify_password", method = RequestMethod.PUT)
    public ApiResponse modifyPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        return appUserService.modifyPassword(oldPassword,newPassword);
    }

    /**
     * 第三方登录
     * 20180414
     * @param access_token
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "第三方登录", notes = "第三方登录,目前仅支持qq/wechat")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "access_token", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "openid", value = "openid", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "code", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "os_type", value = "系统类型(IOS/ANDROID)", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "src", value = "第三方帐号登录(wechat/qq)", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/otherLogin", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<LoginUserVo> otherLogin(String access_token,String openid,String code,String os_type,String src) {
        if("qq".equals(src)){
            return appUserService.qqLogin(src,access_token, openid, os_type);
        }else{
            return appUserService.wechatLogin(code,os_type,src);
        }

    }

    @ApiOperation(value = "第三方帐号绑定手机号", notes = "第三方帐号绑定手机号,目前仅支持qq/wechat")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "access_token", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "openid", value = "openid", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "src", value = "第三方帐号绑定(wechat/qq)", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "phone", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "smsCode", value = "验证码", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "os_type", value = "系统类型(IOS/ANDROID)", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/bindOther", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<LoginUserVo> bindOther(@ApiIgnore String access_token,String openid,
                                              String src,String phone,String smsCode,String os_type) {
        if("qq".equals(src)){
            QQUserInfo qqUserInfo = WechatUtil.getQqUserInfo(openid,access_token,os_type);
            if(qqUserInfo.getRet() != 0){
                return new ApiResponse(qqUserInfo.getRet(),qqUserInfo.getMsg());
            }
            return appUserService.bindOther(src,qqUserInfo.getNickname(),
                    qqUserInfo.getFigureurl_qq_2(),access_token,openid,"",phone,smsCode);
        }else /*if("wechat".equals(src))*/{
            WechatUserInfo wechatUserInfo = WechatUtil.getWechatUserInfoByToken(access_token,openid);
            if(wechatUserInfo.getErrcode() != 0){
                return new ApiResponse(wechatUserInfo.getErrcode(),wechatUserInfo.getErrmsg());
            }
            return appUserService.bindOther(src,wechatUserInfo.getNickname(),
                    wechatUserInfo.getHeadimgurl(),access_token,openid,wechatUserInfo.getUnionid(),phone,smsCode);
        }
    }

    /**
     * 第三方登录(适配3.0.3 支持手机号跳过)
     * 20180609
     * @param access_token
     * @param openid
     * @param code
     * @param os_type
     * @param src
     * @return
     */
    @ApiOperation(value = "第三方登录(适配3.0.3 支持手机号跳过)", notes = "第三方登录,目前仅支持qq/wechat(适配3.0.3 支持手机号跳过)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "access_token", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "openid", value = "openid", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "code", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "os_type", value = "系统类型(IOS/ANDROID)", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "src", value = "第三方帐号登录(wechat/qq)", required = true, paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/otherLoginSkipPhone", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse<LoginUserVo> otherLoginSkipPhone(String access_token,String openid,String code,String os_type,String src) {
        if("qq".equals(src)){
            return appUserService.qqLoginSkipPhone(src,access_token, openid, os_type);
        }else{
            return appUserService.wechatLoginSkipPhone(code,os_type,src);
        }

    }

    @ApiOperation(value = "第三方帐号绑定手机号(适配3.0.3 支持手机号跳过)", notes = "第三方帐号绑定手机号,目前仅支持qq/wechat(适配3.0.3 支持手机号跳过)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "phone", required = false, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "smsCode", value = "验证码", required = false, paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/bindOtherSkipPhone", method = RequestMethod.POST)
    @PermissionFilter(filter = true)
    public ApiResponse bindOtherSkipPhone(String phone,String smsCode) {
        return appUserService.bindOtherSkipPhone(phone,smsCode);
    }
}
