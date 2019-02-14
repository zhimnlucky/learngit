package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.bridge.app.inner.enums.AdTypeCode;
import com.auxgroup.bridge.app.inner.vo.AppAdvertisementVo;
import com.auxgroup.smarthome.app.service.inner.AppAdPageService;
import com.auxgroup.smarthome.filter.annotation.PermissionFilter;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * APP广告页
 * </p>
 *
 * @author fudongwei
 * @package com.auxgroup.smarthome.app.controller
 * @date 2019-01-15 08:48
 * @copyright: Copyright (c) 2018
 * @version: V1.0.0
 * @modified: fudongwei
 */
@Api(description = "APP广告", tags = "APP广告")
@RestController
public class AppAdPageController {


    @Autowired
    private AppAdPageService appAdPageService;

    @ApiOperation(value = "获取APP欢迎页", notes = "获取APP欢迎页")
    @RequestMapping(value = "/welcomeAds", method = RequestMethod.GET)
    @PermissionFilter(filter = true)
    public ApiResponse<AppAdvertisementVo> welcomePage() {
        ApiResponse<List<AppAdvertisementVo>> apiResponse = appAdPageService.getAdvertisement(AdTypeCode.WELCOME.code);

        List<AppAdvertisementVo> data = apiResponse.getData();
        if (CollectionUtils.isEmpty(data)) {
            return new ApiResponse<>(apiResponse.getCode(), apiResponse.getMessage());
        }

        return new ApiResponse(apiResponse.getCode(), apiResponse.getMessage(), data.get(0));
    }


    @ApiOperation(value = "获取APP设备列表广告", notes = "获取APP设备列表广告")
    @RequestMapping(value = "/deviceListAds", method = RequestMethod.GET)
    @PermissionFilter(filter = true)
    public ApiResponse<List<AppAdvertisementVo>> deviceListPage() {
        return appAdPageService.getAdvertisement(AdTypeCode.DEVICELIST.code);
    }
}
