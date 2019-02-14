package com.example.appsupport.smarthome.app.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kevinchen on 2017/7/21.
 *  调用openApi需要用到的token
 */
@ApiModel(description = "openApi的token信息")
public class TokenVo {

    @ApiModelProperty(value = "paas平台的token")
    private String token;
    @ApiModelProperty(value = "token到期时间")
    private long expireAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }

    public TokenVo() {
    }

    public TokenVo(String token, long expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }
}
