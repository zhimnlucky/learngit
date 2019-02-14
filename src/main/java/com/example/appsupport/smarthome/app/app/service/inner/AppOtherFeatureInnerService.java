package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.smarthome.app.service.AppOtherFeatureInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lixiaoxiao on 17-9-22.
 */
@FeignClient(name = "INNER-SERVICE",fallback = AppOtherFeatureInnerServiceFallback.class)
public interface AppOtherFeatureInnerService {

    @RequestMapping(value = "/inner/feedback_log", method = RequestMethod.POST)
    ApiResponse feedbackLog(@RequestBody String json_log);

}
