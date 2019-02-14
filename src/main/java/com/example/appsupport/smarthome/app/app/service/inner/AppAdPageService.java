package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.vo.AppAdvertisementVo;
import com.auxgroup.smarthome.app.service.AppAdPageServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 获取广告列表
 * </p>
 *
 * @author fudongwei
 * @package com.auxgroup.smarthome.app.service.inner
 * @date 2019-01-15 11:12
 * @copyright: Copyright (c) 2018
 * @version: V1.0.0
 * @modified: fudongwei
 */
@FeignClient(name = "INNER-SERVICE", fallback = AppAdPageServiceFallback.class)
public interface AppAdPageService {

    /**
     * 根据广告类型获取可用的广告列表
     *
     * @param adType
     * @return
     */
    @RequestMapping(value = "/inner/ads/getByType", method = RequestMethod.GET)
    ApiResponse<List<AppAdvertisementVo>> getAdvertisement(@RequestParam("adType") String adType);
}
