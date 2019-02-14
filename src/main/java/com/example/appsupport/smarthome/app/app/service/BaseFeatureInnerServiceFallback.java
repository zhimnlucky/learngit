package com.example.appsupport.smarthome.app.app.service;


import com.auxgroup.bridge.business.inner.vo.BaseFeatureVo;
import com.auxgroup.smarthome.app.service.inner.BaseFeatureInnerService;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoxiao on 2017/8/21.
 */
@Component
public class BaseFeatureInnerServiceFallback implements BaseFeatureInnerService {

    @Override
    public ApiResponse<List<BaseFeatureVo>> findAllFeatures() {
        return ApiResponse.prompt(Syscode.FAIL);
    }
}
