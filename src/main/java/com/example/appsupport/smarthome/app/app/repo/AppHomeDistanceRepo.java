package com.example.appsupport.smarthome.app.app.repo;

import com.auxgroup.smarthome.app.entity.AppHomeDistanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by root on 17-7-25.
 */
public interface AppHomeDistanceRepo extends JpaRepository<AppHomeDistanceEntity, String> {
    AppHomeDistanceEntity findByDeviceIdAndUId(String deviceId, String uid);

    AppHomeDistanceEntity findByDeviceIdAndUIdAndOnIsTrue(String deviceId, String uid);

    @Query(value = "SELECT * FROM app_home_distance where uid = ? and is_on = true ", nativeQuery = true)
    List<AppHomeDistanceEntity> findByUId(String uid);

    AppHomeDistanceEntity findByDistanceId(String distanceId);

    List<AppHomeDistanceEntity> findByDeviceId(String deviceId);

    @Transactional
    int deleteByUIdAndDeviceId(String uid, String deviceId);

}
