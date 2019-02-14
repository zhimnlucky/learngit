package com.example.appsupport.smarthome.app.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.auxgroup.bridge.app.inner.dto.DeviceConnectFailDto;
import com.auxgroup.bridge.app.inner.vo.AppUserVo;
import com.auxgroup.smarthome.app.service.AppUserService;
import com.auxgroup.smarthome.app.service.inner.DeviceConnectFailInnerService;
import com.auxgroup.smarthome.app.util.HttpUtil;
import com.auxgroup.smarthome.syscode.Syscode;
import com.auxgroup.smarthome.utils.FileUtils;
import com.auxgroup.smarthome.utils.HttpClientUtil;
import com.auxgroup.smarthome.utils.UuidCreateor;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixiaoxiao on 17-9-11.
 */
@Api(description = "配网失败上传功能", tags = "配网失败上传功能")
@RestController
public class ConsumerDeviceConnectFailController {

    @Autowired
    private DeviceConnectFailInnerService deviceConnectFailInnerService;

    @Autowired
    private AppUserService appUserService;

    /**
     * 通过mac查询厂商接口地址
     */
    private static String MAC_SEARCH_URI = "http://7n4.cn/api/mac2vendor";

    @ApiOperation(value = "上报配网失败日志", notes = "上报配网失败日志")
    @RequestMapping(value = "/device_connect_fail", method = RequestMethod.POST)
    public ApiResponse addDeviceRepairList(@RequestBody DeviceConnectFailDto deviceConnectFailDto) {
        String uid = appUserService.getUid();
        deviceConnectFailDto.setUid(uid);
        deviceConnectFailInnerService.saveConnectFail(deviceConnectFailDto);
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * APP配网日志上报(json) add by lixiaoxiao 20181030
     * @param json_log
     * {
     * "message_id":"消息id(用来标识唯一性 uid+,+时间戳),string,",
     * "config_type":"配网方式,int",
     * "config_type_value":"配网方式对应值,string",
     * "wifi_ssid":"wifi的id,string",
     * "bssid":"路由器mac,string",
     * "frequency":"频率,string",
     * "hidden_ssid":"隐藏ssid,boolean",
     * "ip":"ip地址,string",
     * "net_id":"网络id,string",
     * "os_version":"手机系统版本,string",
     * "phone_model":"手机型号,string",
     * "rssi":"信号强度,string",
     * "speed":"网速,string",
     * "connect_code":"成功失败码,int -1配网失败 1绑定成功 其他"
     * "connect_tag":"配网连接成功/失败标记,string"
     * }
     * @return
     */
    @ApiOperation(value = "配网日志上报(json)", notes = "配网日志上报(json)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json_log", value = "日志", paramType = "body", dataType = "string", required = true)
    })
    @RequestMapping(value = "/device_connect_json", method = RequestMethod.POST)
    public ApiResponse deviceConnectLog(@RequestBody(required = true) String json_log, HttpServletRequest request) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(json_log);
            jsonObject.put("uid",appUserService.getUid());
            AppUserVo  appUserVo = appUserService.getAppUser().getData();
            jsonObject.put("userName",StringUtils.isBlank(appUserVo.getPhone())?appUserVo.getNickName():appUserVo.getPhone());
            jsonObject.put("ip", HttpUtil.getClientIp(request));
            String wifi_mac = jsonObject.getString("bssid");
            if(StringUtils.isNotEmpty(wifi_mac)){
                String result = HttpClientUtil.httpRequest(MAC_SEARCH_URI+"?mac=" + wifi_mac, null, "", HttpClientUtil.MethodType.METHOD_GET);
                JSONObject json = JSONObject.parseObject(result);
                String comment = json.getString("comment");
                jsonObject.put("router_model",comment);
            }
            FileUtils.generateLog("device-connect",jsonObject.toJSONString()+"\r\n");
            //--begin add by lixiaoxiao 20190118 将配网日志写入数据库
            int connect_code = jsonObject.getInteger("connect_code");
            String isSuccess = "";
            if(connect_code == -1){
                isSuccess = "2";
            }else if(connect_code == 1){
                isSuccess = "1";
            }else{
                isSuccess = String.valueOf(connect_code);
            }
            DeviceConnectFailDto deviceConnectFailDto = new DeviceConnectFailDto(HttpUtil.getClientIp(request),
                    "", jsonObject.getString("wifi_ssid"),jsonObject.getString("hidden_ssid"),
                    wifi_mac,jsonObject.getString("rssi"),jsonObject.getString("speed"),
                    jsonObject.getString("frequency"), jsonObject.getString("net_id"),
                    jsonObject.getString("os_version"),appUserService.getUid(),wifi_mac,isSuccess);
            deviceConnectFailInnerService.saveConnectFail(deviceConnectFailDto);
            //--end add by lixiaoxiao 20190118 将配网日志写入数据库
            return ApiResponse.prompt(Syscode.SC_OK);
        }catch(Exception e){
            return ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
        }
    }

    /**
     * APP配网日志上报(json) add by lixiaoxiao 20181030
     * @param json_log
     * @return
     */
    @ApiOperation(value = "模组配网日志上报(json)", notes = "模组配网日志上报(json)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json_log", value = "日志", paramType = "body", dataType = "string", required = true)
    })
    @RequestMapping(value = "/module_connect_json", method = RequestMethod.POST)
    public ApiResponse moduleConnectLog(@RequestBody(required = true) String json_log) {
        String uuid = UuidCreateor.getUUID();
        try{
            JSONObject jsonObject = JSONObject.parseObject(json_log);
            jsonObject.put("messageId",uuid);
            FileUtils.generateLog("module-connect",jsonObject.toJSONString()+"\r\n");
            return ApiResponse.prompt(Syscode.SC_OK);
        }catch(Exception e){
            return ApiResponse.prompt(Syscode.DTO_PARAMS_ERROR);
        }
    }
}
