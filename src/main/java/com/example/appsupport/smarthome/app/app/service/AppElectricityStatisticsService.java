package com.example.appsupport.smarthome.app.app.service;

import com.auxgroup.smarthome.app.entity.AppElectricityStatisticsEntity;
import com.auxgroup.smarthome.app.repo.AppElectricityStatisticsRepo;
import com.auxgroup.smarthome.app.vo.PowerCurveData;
import com.auxgroup.smarthome.app.vo.PowerCurveVo;
import com.auxgroup.smarthome.enterprise.service.AuxAnalysisService;
import com.auxgroup.smarthome.enterpriseapi.requestbody.auxrequest.AuxDeviceAggDataBody;
import com.auxgroup.smarthome.enterpriseapi.responsebody.auxresult.AuxAggDataResult;
import com.auxgroup.smarthome.syscode.AppCode;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.utils.NewDateUtils;
import com.auxgroup.smarthome.web.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kevinchen on 2017/9/13.
 * (需要注意时间格式，在调用平台的接口，都是统一用的yyyyMMddHH)
 */
@Service
public class AppElectricityStatisticsService {

    @Autowired
    private AppElectricityStatisticsRepo electricityStatisticsRepo;
    @Autowired
    private AuxAnalysisService auxAnalysisService;

    /**
     * -------日用电曲线---------
     * 获取某日的波峰波谷波平总耗电量
     * 以及某日每小时的耗电量
     * 以及某日设置的波峰波谷波平的时间段
     * @param did
     * @param dayTime 格式："yyyyMMdd"
     * @return
     */
    public ApiResponse<PowerCurveVo> getDayPowerCurve(String productKey,String did,String dayTime){
        AppElectricityStatisticsEntity dayElectricityStatisticsEntity = electricityStatisticsRepo.findByDidAndTimeTypeAndTimeStr(did, 0, dayTime);
        //没有数据，即表示该设备没有设置过波峰波谷，则直接显示该设备当日的用电总量即可。
        if (dayElectricityStatisticsEntity==null){
            return commonElectricityCurve(productKey,did,dayTime,"HOURS");
        }
        AuxAggDataResult dataResult = auxAnalysisService.getElectricityAggDataInfo(productKey,did,dayTime,"HOURS");
        if (dataResult == null)
            return ApiResponse.prompt(Syscode.FAIL);
        PowerCurveVo powerCurveVo = new PowerCurveVo(dataResult.getTotalElectricity(), dayElectricityStatisticsEntity.getValleyTotal(),
                dayElectricityStatisticsEntity.getPeakTotal(),dayElectricityStatisticsEntity.getTimeBucket());
        String timeBucket = dayElectricityStatisticsEntity.getTimeBucket();
        List<PowerCurveData> powerCurveDataList = new ArrayList<>();
        //需求确认 开了峰谷节电就一定有波峰波谷时间段
        if (StringUtils.isBlank(timeBucket)){
            throw new IllegalArgumentException("：日用电曲线中参与计算的波峰波谷没有时间段...");
        }
        ApiResponse<List<PowerCurveData>> apiResponse = getPowerCurveDataListByTimeBucket(dayElectricityStatisticsEntity.getPk(), did, dayTime, dayElectricityStatisticsEntity);
        if (!apiResponse.isNotErrorCode()){
            return ApiResponse.prompt(apiResponse.getCode(),apiResponse.getMessage());
        }
        powerCurveDataList= apiResponse.getData();
        powerCurveVo.createCurveData(powerCurveDataList);
        return ApiResponse.ok(powerCurveVo);
    }



    /**
     *  获取某月的用电曲线
     * @param did
     * @param timeStr 格式："yyyyMM"
     * @return
     */
    public ApiResponse<PowerCurveVo> getMonthPowerCurve(String productKey,String did,String timeStr){
        return getMonthOrYearPowerCurve(productKey,did,timeStr,1);//type=1 表示月曲线
    }

    /**
     * 获取年用电曲线
     * @param did
     * @param timeStr 格式："yyyy"
     * @return
     */
    public ApiResponse<PowerCurveVo> getYearPowerCurve(String productKey,String did,String timeStr){
        return getMonthOrYearPowerCurve(productKey,did,timeStr,2); //type=2 表示年曲线
    }

    /**
     * 仅服务于日用电曲线
     * @param pk
     * @param did
     * @param dayTime 该方法格式："yyyyMMdd"
     * @param dayElectricityStatisticsEntity 当天每小时的用电总量
     * @return
     */
    private ApiResponse<List<PowerCurveData>> getPowerCurveDataListByTimeBucket(String pk,String did,String dayTime,AppElectricityStatisticsEntity dayElectricityStatisticsEntity){
        List<PowerCurveData> powerCurveDataList = new ArrayList<>();
        AuxDeviceAggDataBody body = new AuxDeviceAggDataBody(dayTime+"00", NewDateUtils.getNextDayStr(dayTime)+"00","electricity","sum","HOURS");
        ApiResponse<AuxAggDataResult> apiResponse = auxAnalysisService.wifiDeviceAggData(pk, did, body);
        if (!apiResponse.isNotErrorCode())
            return ApiResponse.prompt(apiResponse.getCode(),apiResponse.getMessage());
        AuxAggDataResult data = apiResponse.getData();
        if (data == null)
            return ApiResponse.prompt(AppCode.PEAKVALLEY_DATA_NULL);
        Map<Integer, Float> timeElectricityMap = data.getTimeElectricityMap();
        if (timeElectricityMap == null)
            return ApiResponse.prompt(AppCode.PEAKVALLEY_DATA_NULL);
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> peakBucket = dayElectricityStatisticsEntity.getParsePeakVallyBucket(0);
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> vallyBucket = dayElectricityStatisticsEntity.getParsePeakVallyBucket(1);
        if ( peakBucket == null || vallyBucket == null){
            throw new IllegalArgumentException("：日用电曲线中参与计算的波峰波谷没有时间段...");
        }
        Pair<Integer, Integer> peakBucket1 = peakBucket.getLeft();//波峰时间段1
        Pair<Integer, Integer> peakBucket2 = peakBucket.getRight();//波峰时间段2 有可能为null
        Pair<Integer, Integer> vallyBucket1 = vallyBucket.getLeft();//波峰时间段1
        Pair<Integer, Integer> vallyBucket2 = vallyBucket.getRight();//波峰时间段2 有可能为null

        for (int i = 0; i < 24 ; i++) {
            // modify by qiuhua  此处有种情况，如果有些时间点没有统计用电量会返回空，计算会报空指针异常
            Float electricity = timeElectricityMap.get(i) == null ? 0f : timeElectricityMap.get(i);
            PowerCurveData powerCurveData = new PowerCurveData(i);
            boolean flag = false;
            if((peakBucket1.getLeft() <=i && i<peakBucket1.getRight())||
                    (peakBucket2 != null && peakBucket2.getLeft() <=i && i<peakBucket2.getRight())){
                powerCurveData.createPowerCurveData(0,0,electricity);
                flag = true;
            }
            if((vallyBucket1.getLeft() <=i && i<vallyBucket1.getRight())||
                    (vallyBucket2 != null && vallyBucket2.getLeft() <=i && i<vallyBucket2.getRight())){
                powerCurveData.createPowerCurveData(0,electricity,0);
                flag = true;
            }
            if (!flag){
                powerCurveData.createPowerCurveData(electricity,0,0);
            }
            powerCurveDataList.add(powerCurveData);
        }

        return ApiResponse.ok(powerCurveDataList);
    }

    /**
     * 获取月用电曲线或者年用电曲线 共用方法
     * @param did
     * @param timeStr 当为月曲线时格式为 "yyyyMM"、当为年曲线时格式为"yyyy"
     * @param type type=1 表示月曲线  type=2 表示年曲线
     * @return
     */
    private ApiResponse<PowerCurveVo> getMonthOrYearPowerCurve(String productKey,String did,String timeStr,int type){
        AppElectricityStatisticsEntity monthOrYearElectricityStatisticsEntity = electricityStatisticsRepo.findByDidAndTimeTypeAndTimeStr(did, type, timeStr);
        if(monthOrYearElectricityStatisticsEntity == null && type==1)
            return commonElectricityCurve(productKey,did,timeStr,"DAYS");
        if(monthOrYearElectricityStatisticsEntity == null && type==2)
            return commonElectricityCurve(productKey,did,timeStr,"MONTHS");
        AuxAggDataResult dataResult = null;
        if (type==1)
            dataResult = auxAnalysisService.getElectricityAggDataInfo(productKey, did, timeStr, "DAYS");
        if (type==2)
            dataResult = auxAnalysisService.getElectricityAggDataInfo(productKey, did, timeStr, "MONTHS");
        PowerCurveVo powerCurveVo = new PowerCurveVo(dataResult.getTotalElectricity(),
                monthOrYearElectricityStatisticsEntity.getValleyTotal(),monthOrYearElectricityStatisticsEntity.getPeakTotal());
        List<PowerCurveData> powerCurveDataList = new ArrayList<>();
        Map<Integer, Float> timeElectricityMap = dataResult.getTimeElectricityMap();
        if (timeElectricityMap==null || timeElectricityMap.size()==0){
            throw new IllegalArgumentException("获取用电曲线异常！");
        }
        List<AppElectricityStatisticsEntity> monthOrYearElectricityStatisticsEntities = electricityStatisticsRepo.findByDidAndTimeTypeAndTimeStrStartingWith(did, type-1, timeStr);
        if (monthOrYearElectricityStatisticsEntities==null || monthOrYearElectricityStatisticsEntities.size()==0){
            for(Iterator<Map.Entry<Integer, Float>> iterator = timeElectricityMap.entrySet().iterator();iterator.hasNext();){
                Map.Entry<Integer, Float> next = iterator.next();
                Integer dayOrMonthNo = next.getKey();
                float value = next.getValue();
                powerCurveDataList.add(new PowerCurveData(dayOrMonthNo,value,0,0));
            }
            powerCurveVo.createCurveData(powerCurveDataList);
            return ApiResponse.ok(powerCurveVo);
        }
        Map<Integer,PowerCurveData> powerCurveDataMap = new HashMap<Integer,PowerCurveData>();
        for(Iterator<Map.Entry<Integer, Float>> iterator = timeElectricityMap.entrySet().iterator();iterator.hasNext();){
            Map.Entry<Integer, Float> next = iterator.next();
            Integer dayOrMonthNo = next.getKey();
            float value = next.getValue();
            powerCurveDataMap.put(dayOrMonthNo,new PowerCurveData(dayOrMonthNo,value,0,0));
        }
        for (Iterator<AppElectricityStatisticsEntity> iterator = monthOrYearElectricityStatisticsEntities.iterator(); iterator.hasNext();) {
            AppElectricityStatisticsEntity next =  iterator.next();
            String timeStr1 = next.getTimeStr();
            float peakTotal = next.getPeakTotal();
            float valleyTotal = next.getValleyTotal();
            String subTime = timeStr1.substring(timeStr1.length() - 2, timeStr1.length());
            int dayOrMonthNo = Integer.parseInt(subTime);
            PowerCurveData powerCurveData = powerCurveDataMap.get(dayOrMonthNo);
            if (powerCurveData==null){
                powerCurveData = new PowerCurveData(dayOrMonthNo);
            }
            powerCurveData.createPowerPeakValley(valleyTotal,peakTotal);
            powerCurveData.changeWaveFlatElectricityValue(valleyTotal,peakTotal);
        }
        powerCurveVo.createCurveData(new ArrayList<>(powerCurveDataMap.values()));
        return ApiResponse.ok(powerCurveVo);
    }

    /**
     * 新增2017-10-31
     * 没有设置波峰波谷的设备的用电曲线
     * （直接查平台数据即可）
     * @param pk
     * @param did
     * @param timeStr
     * @param unit  HOURS  DAYS MONTHS
     * @return
     */
    private ApiResponse<PowerCurveVo> commonElectricityCurve(String pk,String did,String timeStr,String unit){
        AuxAggDataResult dataResult = auxAnalysisService.getElectricityAggDataInfo(pk, did, timeStr, unit);
        if (dataResult == null)
            return ApiResponse.prompt(Syscode.FAIL);
        Map<Integer, Float> timeElectricityMap = dataResult.getTimeElectricityMap();
        PowerCurveVo powerCurveVo = new PowerCurveVo(dataResult.getTotalElectricity());
        List<PowerCurveData> powerCurveDataList = new ArrayList<>();
        if (timeElectricityMap!=null && timeElectricityMap.size()> 0 ){
            for (Iterator<Map.Entry<Integer, Float>> iterator = timeElectricityMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, Float> next = iterator.next();
                PowerCurveData powerCurveData = new PowerCurveData(next.getKey());
                powerCurveData.createPowerCurveData(next.getValue(),0,0);
                powerCurveDataList.add(powerCurveData);
            }
        }
        powerCurveVo.createCurveData(powerCurveDataList);
        return ApiResponse.ok(powerCurveVo);
    }

}
