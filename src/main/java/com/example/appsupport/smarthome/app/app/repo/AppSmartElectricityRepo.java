package com.example.appsupport.smarthome.app.app.repo;

import com.auxgroup.smarthome.app.entity.AppSmartElectricity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppSmartElectricityRepo extends JpaRepository<AppSmartElectricity,String> {

    /**
     * 根据deviceId查询
     * @param deviceId
     * @return
     */
    AppSmartElectricity findByDeviceId(String deviceId);

    AppSmartElectricity findByMac(String mac);
}
