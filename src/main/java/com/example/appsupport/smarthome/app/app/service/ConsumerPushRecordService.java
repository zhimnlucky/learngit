package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.bridge.app.inner.vo.PushRecordNewVo;
import com.auxgroup.bridge.app.inner.vo.PushRecordVo;
import com.auxgroup.smarthome.app.service.inner.PushRecordInnerService;
import com.auxgroup.smarthome.app.util.FinalDateLocalUtil;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴建龙 on 18-05-12.
 */
@Service
public class ConsumerPushRecordService {

    @Autowired
    private PushRecordInnerService pushRecordInnerService;


    /**
     * 测试数据
     * data.get(0).setPushTime("2018-05-12 16:40:57.974");
     * data.get(1).setPushTime("2018-05-12 12:40:57.974");
     * data.get(2).setPushTime("2018-05-12 01:40:57.974");
     * data.get(3).setPushTime("2018-05-11 16:40:57.974");
     * data.get(4).setPushTime("2018-05-11 13:40:57.974");
     * data.get(5).setPushTime("2018-05-10 16:40:57.974");
     * data.get(6).setPushTime("2018-05-09 13:40:57.974");
     * data.get(7).setPushTime("2018-05-08 16:40:57.974");
     * data.get(8).setPushTime("2018-05-08 13:40:57.974");
     * data.get(9).setPushTime("2018-05-07 16:40:57.974");
     * data.get(10).setPushTime("2018-05-07 13:40:57.974");
     * data.get(11).setPushTime("2018-05-06 16:40:57.974");
     * data.get(12).setPushTime("2018-05-06 13:40:57.974");
     * data.get(13).setPushTime("2018-05-05 16:40:57.974");
     * data.get(14).setPushTime("2018-05-05 13:40:57.974");
     */
    public ApiResponse findConsumerPushRecord(@PathVariable String uid, @RequestParam(value = "time") String time) {
        ApiResponse<List<PushRecordVo>> allByUid = pushRecordInnerService.findAllByUid(uid, time);
        List<PushRecordVo> data = allByUid.getData();
        if (allByUid.getCode() != AppCode.SC_OK.getCode() || data == null || data.size() == 0)
            return allByUid;

        List<PushRecordNewVo> pushRecordNewVoList = exchangData(data);
        return ApiResponse.ok(pushRecordNewVoList);
    }

    /**
     * 将PushRecordVo类型数据转化成PushRecordNewVo类型数据
     *
     * @param data
     * @return
     */
    public List<PushRecordNewVo> exchangData(List<PushRecordVo> data) {
        Map<String, List<PushRecordVo>> map = new LinkedHashMap<>();
        LocalDate now = LocalDate.now();
        for (PushRecordVo datum : data) {
            String pushTime = datum.getPushTime();
            String day = FinalDateLocalUtil.computeDay(now, pushTime);

            if (map.containsKey(day)) {
                map.get(day).add(datum);
            } else {
                List<PushRecordVo> pushRecordVos = new ArrayList<>();
                pushRecordVos.add(datum);
                map.put(day, pushRecordVos);
            }
        }

        List<PushRecordNewVo> pushRecordNewVoList = new ArrayList<>();
        for (Map.Entry<String, List<PushRecordVo>> entry : map.entrySet()) {
            PushRecordNewVo pushRecordNewVo = new PushRecordNewVo();
            pushRecordNewVo.setDateInfo(entry.getKey());
            pushRecordNewVo.setList(entry.getValue());
            pushRecordNewVoList.add(pushRecordNewVo);
        }
        return pushRecordNewVoList;
    }


}
