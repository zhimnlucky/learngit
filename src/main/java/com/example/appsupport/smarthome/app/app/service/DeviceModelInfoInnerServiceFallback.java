package com.example.appsupport.smarthome.app.app.service;


import com.auxgroup.bridge.app.inner.vo.DeviceModelInfoVo;
import com.auxgroup.bridge.app.inner.vo.DeviceModelListVo;
import com.auxgroup.smarthome.app.service.inner.DeviceModelInfoInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by niuGuangzhe on 2017/7/28.
 */
@Component
public class DeviceModelInfoInnerServiceFallback implements DeviceModelInfoInnerService {

    @Override
    public ApiResponse getDeviceInfo(String model) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<DeviceModelInfoVo> getDeviceInfoBySn(String sn) {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public ApiResponse<List<DeviceModelListVo>> getAllDeviceModelInfo() {
        return ApiResponse.prompt(Syscode.FAIL);
    }

    @Override
    public long countSnByModelId(String modelId, String sn) {
        return 0;
    }

}
