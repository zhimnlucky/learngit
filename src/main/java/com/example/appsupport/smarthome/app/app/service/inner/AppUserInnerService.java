package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.AppUserDto;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.smarthome.app.service.AppUserInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by kevinchen on 2017/7/24.
 */
@FeignClient(name = "INNER-SERVICE", fallback = AppUserInnerServiceFallback.class)
public interface AppUserInnerService {

    @RequestMapping(value = "/inner/user", method = RequestMethod.POST)
    ApiResponse<AppUserVo> createAppUser(@RequestParam("uid") String uid, @RequestParam("phone") String phone);

    @RequestMapping(value = "/inner/otherUser", method = RequestMethod.POST)
    ApiResponse<AppUserVo> createOtherUser(@RequestParam("uid") String uid, @RequestParam("openid") String openid,
                                           @RequestParam("unionid") String unionid, @RequestParam("nickName") String nickName,
                                           @RequestParam("headImg") String headImg, @RequestParam("src") String src);

    @RequestMapping(value = "/inner/user", method = RequestMethod.PUT)
    ApiResponse<AppUserVo> updateAppUser(@RequestParam("uid") String uid, @RequestBody AppUserDto appUserDto);


    @RequestMapping(value = "/inner/user", method = RequestMethod.GET)
    ApiResponse<AppUserVo> getAppUser(@RequestParam("uid") String uid);

    @RequestMapping(value = "/inner/loginInfo", method = RequestMethod.POST)
    ApiResponse recordLoginInfo(@RequestParam("uid") String uid, @RequestParam("clientId") String clientId, @RequestParam("ip") String ip);

    @RequestMapping(value = "/inner/bindOtherAccount", method = RequestMethod.PUT)
    ApiResponse bindOtherAccount(@RequestParam("uid") String uid,
                                 @RequestParam("openid") String openid,
                                 @RequestParam("qqid") String qqid,
                                 @RequestParam("phone") String phone);

    @RequestMapping(value = "/inner/findOtherUser", method = RequestMethod.GET)
    ApiResponse<List<AppUserVo>> findOtherUser(@RequestParam("openid") String openid, @RequestParam("qqid") String qqid);

    @RequestMapping(value = "/inner/userByPhone", method = RequestMethod.GET)
    ApiResponse<AppUserVo> getAppUserByPhone(@RequestParam("phone") String phone);

    @RequestMapping(value = "/inner/app/users/all", method = RequestMethod.GET)
    ApiResponse<List<AppUserVo>> getAllUserList(@RequestParam(value = "keywords", required = false) String keywords);

    @RequestMapping(value="/inner/isExistPhone",method= RequestMethod.GET)
    ApiResponse<Boolean> isExistPhone(@RequestParam("phone") String phone);

    @RequestMapping(value="/inner/isBindPhone",method= RequestMethod.GET)
    ApiResponse<Boolean> isBindPhone(@RequestParam("uid") String uid);
}
