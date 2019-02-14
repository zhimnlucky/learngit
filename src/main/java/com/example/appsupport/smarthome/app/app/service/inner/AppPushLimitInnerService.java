package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.AppPushLimitDto;
import com.auxgroup.bridge.app.inner.vo.AppPushLimitVo;
import com.auxgroup.smarthome.app.service.AppPushLimitInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INNER-SERVICE", fallback = AppPushLimitInnerServiceFallback.class)
public interface AppPushLimitInnerService {

    @RequestMapping(value = "/inner/appPushLimit/setSilenceTime", method = RequestMethod.POST)
    ApiResponse<AppPushLimitVo> setSilenceTime(@RequestBody AppPushLimitDto appPushLimitDto);

    @RequestMapping(value = "/inner/appPushLimit/detail", method = RequestMethod.GET)
    ApiResponse<AppPushLimitVo> getInfo(@RequestParam("uid") String uid);
}
