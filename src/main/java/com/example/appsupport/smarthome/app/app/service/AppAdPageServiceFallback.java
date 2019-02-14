package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.AppAdvertisementVo;
import com.auxgroup.smarthome.app.service.inner.AppAdPageService;
import com.auxgroup.smarthome.web.ApiResponse;

import java.util.List;

/**
 * <p>
 * APP广告页回调处理
 * </p>
 *
 * @author fudongwei
 * @package com.auxgroup.smarthome.app.service
 * @date 2019-01-15 11:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0.0
 * @modified: fudongwei
 */
public class AppAdPageServiceFallback implements AppAdPageService {
    @Override
    public ApiResponse<List<AppAdvertisementVo>> getAdvertisement(String adType) {
        return ApiResponse.fail();
    }
}
