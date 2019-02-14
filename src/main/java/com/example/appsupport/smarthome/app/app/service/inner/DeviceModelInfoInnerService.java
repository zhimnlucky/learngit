package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.vo.DeviceModelInfoVo;
import com.auxgroup.bridge.app.inner.vo.DeviceModelListVo;
import com.auxgroup.smarthome.app.service.DeviceModelInfoInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 客户端对外暴露设备型号接口
 * Created by niuGuangzhe on 2017/7/28.
 */
@FeignClient(name = "INNER-SERVICE", fallback = DeviceModelInfoInnerServiceFallback.class)
public interface DeviceModelInfoInnerService {

    @RequestMapping(value = "/inner/model", method = RequestMethod.GET, consumes = "application/json")
    ApiResponse<DeviceModelInfoVo> getDeviceInfo(@RequestParam("model") String model);

    @RequestMapping(value = "/inner/device_model", method = RequestMethod.GET, consumes = "application/json")
    ApiResponse<DeviceModelInfoVo> getDeviceInfoBySn(@RequestParam("sn") String sn);

    /**
     * 获取所有的型号信息
     * @return
     */
    @GetMapping(value = "/inner/all_model_app")
    ApiResponse<List<DeviceModelListVo>> getAllDeviceModelInfo();

    @GetMapping(value = "/inner/count_sn_by_model_id")
     long countSnByModelId(@RequestParam("modelId") String modelId, @RequestParam("sn") String sn);

}
