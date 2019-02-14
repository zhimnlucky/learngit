package com.example.appsupport.smarthome.app.app.vo;

/**
 * Created by root on 18-4-16.
 */
public class QQUserInfo {
    private String openid;

    private String figureurl_qq_2;

    private String nickname;

    private Integer ret;

    private String msg;

    public QQUserInfo() {
    }

    public QQUserInfo(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
