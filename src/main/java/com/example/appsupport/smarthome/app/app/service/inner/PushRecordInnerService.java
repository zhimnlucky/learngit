package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.bridge.app.inner.vo.PushRecordVo;
import com.auxgroup.smarthome.app.service.PushRecordInnerServiceFallback;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by lixiaoxiao on 17-10-16.
 */
@FeignClient(name = "INNER-SERVICE", fallback = PushRecordInnerServiceFallback.class)
public interface PushRecordInnerService {
    @RequestMapping(value = "/inner/pushRecord/list/{uid}", method = RequestMethod.GET)
    ApiResponse<List<PushRecordVo>> findAllByUid(@PathVariable("uid") String uid, @RequestParam("time") String time);

    @RequestMapping(value = "/inner/pushRecord/savePushRecord/{uid}", method = RequestMethod.POST)
    ApiResponse savePushRecord(@PathVariable("uid") String uid, @RequestParam("body") String body, @RequestParam("title") String title, @RequestParam("sourceType") String sourceType, @RequestParam("imageUrl") String imageUrl, @RequestParam("linkedUrl") String linkedUrl, @RequestParam("sourceValue") String sourceValue, @RequestParam("type") String type, @RequestParam("platform") String platform);

    @RequestMapping(value = "/inner/pushRecord/notReadCount", method = RequestMethod.GET)
    ApiResponse<Integer> findNotReadCountByUid(@RequestParam("uid") String uid);

    @RequestMapping(value = "/inner/pushRecord/updateReadState", method = RequestMethod.PUT)
    ApiResponse updateReadState(List<String> recordIdList);

    @RequestMapping(value = "/inner/pushRecord/updateAllReadState", method = RequestMethod.PUT)
    ApiResponse<Integer> updateAllReadState(@RequestParam("uid") String uid);
}
