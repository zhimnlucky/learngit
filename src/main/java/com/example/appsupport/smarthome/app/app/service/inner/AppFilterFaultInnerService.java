package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.dto.AppFilterFaultDto;
import com.auxgroup.bridge.app.inner.vo.AppFilterFaultVo;
import com.auxgroup.smarthome.app.service.AppFilterFaultInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lixiaoxiao on 17-9-22.
 */
@FeignClient(name = "INNER-SERVICE",fallback = AppFilterFaultInnerServiceFallback.class)
public interface AppFilterFaultInnerService {

    @RequestMapping(value = "/inner/confirm_filter_washed", method = RequestMethod.POST)
    ApiResponse confirmFilterWashed(@RequestBody AppFilterFaultDto appFilterFaultDto,
                                    @RequestParam(value = "uid") String uid);

    @RequestMapping(value = "/inner/filter_fault", method = RequestMethod.GET)
    ApiResponse<AppFilterFaultVo> getFilterFault(@RequestParam("mac") String mac);
}
