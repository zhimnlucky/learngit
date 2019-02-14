package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Qiuhua Lai
 */
@FeignClient(name="inner-service", fallback = FeignTestFallback.class)
public interface FeignTestService {

    @GetMapping(value = "/inner/test-feign")
    ApiResponse testFeign(@RequestParam("send") String send);
}

@Component
class FeignTestFallback implements FeignTestService {

    @Override
    public ApiResponse testFeign(String send) {
        return ApiResponse.fail();
    }
}
