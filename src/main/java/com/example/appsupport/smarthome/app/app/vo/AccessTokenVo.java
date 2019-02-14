package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kevinchen on 2017/7/21.
 */
@ApiModel(value = "app访问SAAS后台所需要的token")
public class AccessTokenVo {
    @ApiModelProperty(value = "SAAS平台的accesstoken")
    private String accessToken;
    @ApiModelProperty(value = "到期时间")
    private long expireSaasAt;

    public AccessTokenVo(String accessToken, long expireSaasAt) {
        this.accessToken = accessToken;
        this.expireSaasAt = expireSaasAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireSaasAt() {
        return expireSaasAt;
    }

    public void setExpireSaasAt(long expireSaasAt) {
        this.expireSaasAt = expireSaasAt;
    }
}
