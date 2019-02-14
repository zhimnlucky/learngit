package com.example.appsupport.smarthome.app.app.filter;

import com.auxgroup.smarthome.filter.CommonPermissionSecurityUserServiceImpl;
import com.auxgroup.smarthome.filter.SuitTypeEnum;
import com.auxgroup.smarthome.jwt.JwtConstant;
import com.auxgroup.smarthome.openapi.responsebody.UserTokenMsg;
import com.auxgroup.smarthome.redis.config.ObjectRedis;
import com.auxgroup.smarthome.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by kevinchen on 2017/3/26.
 */
@Service
public class PermissionAppService extends CommonPermissionSecurityUserServiceImpl {
    @Autowired
    private ObjectRedis objectRedis;

    @Override
    protected String getJwtSecret() {
        return JwtConstant.APP_JWT_SECRET;
    }

    @Override
    public void addNoAuthUri() {
        noAuthUri = Arrays.asList("/private/peak_valley","/private/electrical_data","/private/electrical_data/sum",
                "/private/smart_electricity","/private/all/sleep","/app_version");
    }

    @Override
    public SessionUser getUserEntity() {
        UserTokenMsg userTokenMsg = objectRedis.get(getClientId(), UserTokenMsg.class);
        if(null == userTokenMsg) return null;
        return new SessionUser();
    }

    @Override
    public boolean isTimeout() {
        return false;
    }

    @Override
    public boolean allowQueryRequestUri(String uri){
        return true;
    }

    public String getClientId(){
        return getJwtId();
    }

    public String getAppId(){
        return getSubjectFromAccessToken();
    }

    public SuitTypeEnum getSuitType() {
        return SuitTypeEnum.MOBILE;
    }

}
