package com.example.appsupport.smarthome.app.app.repo;

import com.auxgroup.smarthome.app.entity.AppScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时控制任务数据访问接口
 * kevin chen
 */
public interface AppScheduleRepo extends JpaRepository<AppScheduleEntity, String> {
    /**
     * 通过设备id 查询设备相关的定时任务列表
     * @param deviceId
     * @param trash false：有效，true：无效（被丢弃）
     * @return
     */
    List<AppScheduleEntity> findByDeviceIdAndTrash(String deviceId, Boolean trash);

    /**
     * 通过设备id 查询设备相关的定时任务列表
     * @param deviceId
     * @return
     */
    List<AppScheduleEntity> findByDeviceIdAndTrashFalse(String deviceId);

    /**
     * 查找多联机子设备定时列表
     * @param deviceId 设备id
     * @param dst 子设备地址位
     * @return
     */
    List<AppScheduleEntity> findByDeviceIdAndDstAndTrashFalse(String deviceId, Integer dst);

    /**
     * 开启或者关闭 定时控制任务
     * @param id
     * @param on true 开启  false:关闭
     */
    @Modifying
    @Query("update AppScheduleEntity a set a.on = ?2 where a.id = ?1 ")
    @Transactional(readOnly = false)
    void openOrcloseSchudleTask(String id, boolean on);

    /**
     * 放弃，丢弃、定时控制任务
     * @param id
     * @param trash false：有效， true：无效，丢弃，放弃'
     */
    @Modifying
    @Query("update AppScheduleEntity a set a.trash = ?2 where a.id =?1 ")
    @Transactional(readOnly = false)
    void trashSchudleTask(String id, boolean trash);

}
