package com.example.appsupport.smarthome.app.app.controller;

import com.auxgroup.smarthome.app.dto.AppSceneDto;
import com.auxgroup.smarthome.app.service.AppSceneService;
import com.auxgroup.smarthome.app.vo.AppSceneDetailVo;
import com.auxgroup.smarthome.app.vo.AppScenePartVo;
import com.auxgroup.smarthome.app.vo.AppSceneVo;
import com.auxgroup.smarthome.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "场景管理", description = "场景管理")
@RestController
@Validated
public class AppSceneController {

    @Autowired
    private AppSceneService appSceneService;

    @ApiOperation(value = "创建场景")
//    @PostMapping("/scenes")
    @RequestMapping(value = "/scenes", method = RequestMethod.POST)
    public ApiResponse<AppSceneVo> sceneAdd(@Valid @RequestBody AppSceneDto appSceneDto) {
        return appSceneService.sceneAdd(appSceneDto);
    }


    @ApiOperation(value = "编辑场景")
//    @PutMapping("/scenes/{sceneId}")
    @RequestMapping(value = "/scenes/{sceneId}", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true)
    })
    public ApiResponse<AppSceneVo> sceneUpdate(@PathVariable("sceneId") String sceneId, @Valid @RequestBody AppSceneDto appSceneDto) {
        return appSceneService.sceneUpdate(sceneId, appSceneDto);
    }

    @ApiOperation(value = "删除场景")
//    @DeleteMapping("/scenes/{sceneId}")
    @RequestMapping(value = "/scenes/{sceneId}", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true)})
    public ApiResponse sceneDelete(@PathVariable("sceneId") String sceneId) {
        return appSceneService.sceneDelete(sceneId);
    }

    @ApiOperation(value = "开关场景")
//    @PutMapping("/scenes/{sceneId}/onOff")
    @RequestMapping(value = "/scenes/{sceneId}/onOff", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true),
            @ApiImplicitParam(name = "state", value = "开启状态 0关闭 1开启", paramType = "form", dataType = "int", required = true)
    })
    public ApiResponse<AppSceneVo> onOff(@PathVariable("sceneId") String sceneId, int state) {
        return appSceneService.onOff(sceneId, state);
    }


    @ApiOperation(value = "修改场景名称")
//    @PutMapping("/scenes/{sceneId}/rename")
    @RequestMapping(value = "/scenes/{sceneId}/rename", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true),
            @ApiImplicitParam(name = "sceneName", value = "场景名称", paramType = "form", dataType = "string", required = true)
    })
    public ApiResponse<AppSceneVo> rename(@PathVariable("sceneId") String sceneId, String sceneName) {
        return appSceneService.rename(sceneId, sceneName);
    }

    @ApiOperation(value = "查询场景详情")
//    @GetMapping("/scenes/{sceneId}")
    @RequestMapping(value = "/scenes/{sceneId}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true)
    })
    public ApiResponse<AppSceneDetailVo> sceneDetail(@PathVariable("sceneId") String sceneId) {
        return appSceneService.sceneDetail(sceneId);
    }


    @ApiOperation(value = "用户场景搜索")
//    @GetMapping("/scenes/owner")
    @RequestMapping(value = "/scenes/owner", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", paramType = "query", dataType = "string", required = false)
    })
    public ApiResponse<List<AppSceneVo>> owner(String key) {
        return appSceneService.owner(key);
    }


    @ApiOperation(value = "用户场景搜索（区分手动自动）")
//    @GetMapping("/scenes/ownerPart")
    @RequestMapping(value = "/scenes/ownerPart", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", paramType = "query", dataType = "string", required = false)
    })
    public ApiResponse<AppScenePartVo> ownerPart(String key) {
        return appSceneService.ownerPart(key);
    }

    @ApiIgnore
    @ApiOperation(value = "用户首页场景搜索")
//    @GetMapping("/scenes/ownerHomepage")
    @RequestMapping(value = "/scenes/ownerHomepage", method = RequestMethod.GET)
    public ApiResponse<List<AppSceneVo>> homeSceneQuery() {
        return appSceneService.homeSceneQuery();
    }

    @ApiIgnore
    @ApiOperation(value = "用户预制场景搜索")
//    @GetMapping("/scenes/ownerPrecut")
    @RequestMapping(value = "/scenes/ownerPrecut", method = RequestMethod.GET)
    public ApiResponse<List<AppSceneVo>> precutSceneQuery() {
        return appSceneService.precutSceneQuery();
    }

    @ApiOperation(value = "获取今日未执行开启的位置场景列表")
//    @GetMapping("/scenes/openPlace")
    @RequestMapping(value = "/scenes/openPlace", method = RequestMethod.GET)
    public ApiResponse<List<AppSceneVo>> openPlaceQuery() {
        return appSceneService.openPlaceQuery();
    }


    @ApiOperation(value = "执行手动场景")
//    @PostMapping("/scenes/Manual/{sceneId}")
    @RequestMapping(value = "/scenes/Manual/{sceneId}", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true)
    })
    public ApiResponse sceneHandle(@PathVariable("sceneId") String sceneId) {
        return appSceneService.sceneHandle(sceneId);
    }

    @ApiOperation(value = "执行位置场景")
//    @PostMapping("/scenes/place/{sceneId}")
    @RequestMapping(value = "/scenes/place/{sceneId}", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场景id", paramType = "path", dataType = "string", required = true)
    })
    public ApiResponse scenePlace(@PathVariable("sceneId") String sceneId) {
        return appSceneService.scenePlace(sceneId);
    }


    @ApiIgnore
    @ApiOperation(value = "初始化预制场景")
//    @PostMapping("/scenes/init")
    @RequestMapping(value = "/scenes/init", method = RequestMethod.POST)
    public ApiResponse initScene() {
        return appSceneService.initScene();
    }

}
