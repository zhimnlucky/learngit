package com.example.appsupport.smarthome.app.app.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by root on 18-11-20.
 */
public class HttpUtil {

    /**
     * 获取真实ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip.trim().contains(",")){//为什么会有这一步，因为经过多层代理后会有多个代理，取第一个ip地址就可以了
            String [] ips=ip.split(",");
            ip=ips[0];
        }
        return ip;
    }
}
