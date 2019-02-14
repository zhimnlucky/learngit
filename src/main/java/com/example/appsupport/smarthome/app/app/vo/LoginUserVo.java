package com.example.appsupport.smarthome.app.app.vo;

import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kevinchen on 2017/7/31.
 * app用户登录的时候把用户信息 与平台openApi token传给app
 */
@ApiModel(value = "用户登录时返回的信息")
public class LoginUserVo {

    @ApiModelProperty(value = "app用户实体信息")
    private AppUserVo appUser;
    @ApiModelProperty(value = "调用openApi所需的token信息")
    private TokenVo openApiToken;

    public AppUserVo getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserVo appUser) {
        this.appUser = appUser;
    }

    public TokenVo getOpenApiToken() {
        return openApiToken;
    }

    public void setOpenApiToken(TokenVo openApiToken) {
        this.openApiToken = openApiToken;
    }

    public LoginUserVo() {
    }

    public LoginUserVo(AppUserVo appUser, TokenVo openApiToken) {
        this.appUser = appUser;
        this.openApiToken = openApiToken;
    }
}
