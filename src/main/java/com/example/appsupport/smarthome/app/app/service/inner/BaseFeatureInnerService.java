package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.business.inner.vo.BaseFeatureVo;
import com.auxgroup.smarthome.app.service.BaseFeatureInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 设备故障调用inner接口
 * Created by lixiaoxiao on 17-8-9.
 */
@FeignClient(name="INNER-SERVICE", fallback = BaseFeatureInnerServiceFallback.class)
public interface BaseFeatureInnerService {
    @RequestMapping(value = "/inner/baseFeature", method = RequestMethod.GET)
    ApiResponse<List<BaseFeatureVo>> findAllFeatures();

}
