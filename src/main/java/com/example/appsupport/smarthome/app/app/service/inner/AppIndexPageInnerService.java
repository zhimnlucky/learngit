package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.business.inner.vo.AppIndexPageVo;
import com.auxgroup.smarthome.app.service.AppIndexPageServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by niuGuangzhe on 2017/8/3.
 */
@FeignClient(name = "INNER-SERVICE",fallback = AppIndexPageServiceFallback.class)
public interface AppIndexPageInnerService {
    /**
     * 获取最新的发布版本
     */
    @RequestMapping(value = "/inner/index_page/current_version", method = RequestMethod.GET)
    ApiResponse<AppIndexPageVo> getNewestIndexPage();
}
