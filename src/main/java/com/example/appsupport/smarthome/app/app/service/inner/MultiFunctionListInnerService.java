package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.business.inner.vo.MultiFunctionListVo;
import com.auxgroup.smarthome.app.service.MultiFunctionListInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: laiqiuhua.
 * @Date: 2017/10/17 14:01.
 */
@FeignClient(name="INNER-SERVICE", fallback = MultiFunctionListInnerServiceFallback.class)
public interface MultiFunctionListInnerService {
    @ApiOperation(value = "获取多联机功能列表")
    @GetMapping("/inner/multi_function_list/list")
    ApiResponse<List<MultiFunctionListVo>> getMultiFunctionList() ;

}
