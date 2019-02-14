package com.example.appsupport.smarthome.app.app.repo;


import com.auxgroup.smarthome.app.entity.AppSleepDiyEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by niuGuangzhe on 2017/7/26.
 */
public interface AppSleepDiyRepo extends JpaRepository<AppSleepDiyEntity, String> {

    /**
     * 通过设备id获取有效的睡眠diy数据
     * @param deviceId
     * @return
     */
    List<AppSleepDiyEntity> findByDeviceIdAndEnableTrue(String deviceId);

    List<AppSleepDiyEntity> findByDeviceIdAndEnableTrue(String deviceId, Sort sort);

    /**
     * 通过睡眠DIY的id 获取有效的睡眠diy数据
     * @param sleepDiyId
     * @return
     */
    AppSleepDiyEntity findBySleepDiyIdAndEnableTrue(String sleepDiyId);

    /**
     * 关闭设备下所有的睡眠diy
     * @param deviceId
     * @return
     */
    @Modifying
    @Query("update AppSleepDiyEntity o set o.on = 0 where o.enable = 1 and o.deviceId=?1")
    @Transactional
    int closeAllSleepDiyByDeviceId(String deviceId);


    /**
     * 逻辑删除 睡眠diy
     * @param sleepDiyId
     * @param enable
     * @return
     */
    @Modifying
    @Query("update AppSleepDiyEntity o set o.enable=?2 where o.sleepDiyId=?1")
    @Transactional
    int logicalDeleted(String sleepDiyId, Boolean enable);

    /**
     * 通过设备id获取有效的睡眠diy数据
     * @param deviceId
     * @return
     */
    @Query(value ="select * from app_sleep_diy where is_enable=true and is_on=true and device_id=?1",nativeQuery = true)
    List<AppSleepDiyEntity> findByDeviceIdAndEnableTrueAndOnTrue(String deviceId);

}
