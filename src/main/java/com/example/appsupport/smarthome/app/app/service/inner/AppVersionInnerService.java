package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.smarthome.app.service.AppVersionInnerServiceFallback;
import com.auxgroup.smarthome.app.vo.AppVersionVo;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "INNER-SERVICE", fallback = AppVersionInnerServiceFallback.class)
public interface AppVersionInnerService {

//    @GetMapping(value = "/inner/app_version")
    @RequestMapping(value="/inner/app_version",method= RequestMethod.GET)
    ApiResponse<AppVersionVo> getCurrentAppVersion();
}
