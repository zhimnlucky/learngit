package com.example.appsupport.smarthome.app.app.service.inner;

import com.auxgroup.smarthome.app.dto.AppSceneDto;
import com.auxgroup.smarthome.app.service.AppSceneInnerServiceFallback;
import com.auxgroup.smarthome.app.vo.AppSceneDetailVo;
import com.auxgroup.smarthome.app.vo.AppScenePartVo;
import com.auxgroup.smarthome.app.vo.AppSceneVo;
import com.auxgroup.smarthome.web.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INNER-SERVICE", fallback = AppSceneInnerServiceFallback.class)
public interface AppSceneInnerService {


    /**
     * 创建场景
     *
     * @param appSceneDto
     * @return
     */
//    @PostMapping("/inner/scenes")
    @RequestMapping(value = "/inner/scenes", method = RequestMethod.POST)
    ApiResponse<AppSceneVo> sceneAdd(@RequestBody AppSceneDto appSceneDto);


    /**
     * 编辑场景
     */
//    @PutMapping("/inner/scenes/{sceneId}")
    @RequestMapping(value = "/inner/scenes/{sceneId}", method = RequestMethod.PUT)
    ApiResponse<AppSceneVo> sceneUpdate(@PathVariable("sceneId") String sceneId, @RequestBody AppSceneDto appSceneDto);


    /**
     * 删除场景
     */
//    @DeleteMapping("/inner/scenes/{sceneId}")
    @RequestMapping(value = "/inner/scenes/{sceneId}", method = RequestMethod.DELETE)
    ApiResponse sceneDelete(@PathVariable("sceneId") String sceneId);


    /**
     * 开关场景
     */
//    @PutMapping("/inner/scenes/{sceneId}/onOff")
    @RequestMapping(value = "/inner/scenes/{sceneId}/onOff", method = RequestMethod.PUT)
    ApiResponse<AppSceneVo> onOff(@PathVariable("sceneId") String sceneId, @RequestParam("state") int state);


    /**
     * 修改场景名称
     */
//    @PutMapping("/inner/scenes/{sceneId}/rename")
    @RequestMapping(value = "/inner/scenes/{sceneId}/rename", method = RequestMethod.PUT)
    ApiResponse<AppSceneVo> rename(@PathVariable("sceneId") String sceneId, @RequestParam("sceneName") String sceneName);


    /**
     * 查询场景详情
     */
//    @GetMapping("/inner/scenes/{sceneId}")
    @RequestMapping(value = "/inner/scenes/{sceneId}", method = RequestMethod.GET)
    ApiResponse<AppSceneDetailVo> sceneDetail(@PathVariable("sceneId") String sceneId);


    /**
     * 用户场景搜索
     *
     * @param key
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/owner")
    @RequestMapping(value = "/inner/scenes/owner", method = RequestMethod.GET)
    ApiResponse<List<AppSceneVo>> owner(@RequestParam("key") String key, @RequestParam("uid") String uid);

    /**
     * 用户场景搜索（区分手动自动）
     *
     * @param key
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/ownerPart")
    @RequestMapping(value = "/inner/scenes/ownerPart", method = RequestMethod.GET)
    ApiResponse<AppScenePartVo> ownerPart(@RequestParam("key") String key, @RequestParam("uid") String uid);


    /**
     * 用户首页场景搜索
     *
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/ownerHomepage")
    @RequestMapping(value = "/inner/scenes/ownerHomepage", method = RequestMethod.GET)
    ApiResponse<List<AppSceneVo>> homeSceneQuery(@RequestParam("uid") String uid);

    /**
     * 用户预制场景搜索
     *
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/ownerPrecut")
    @RequestMapping(value = "/inner/scenes/ownerPrecut", method = RequestMethod.GET)
    ApiResponse<List<AppSceneVo>> precutSceneQuery(@RequestParam("uid") String uid);

    /**
     * 获取开启的位置场景列表
     *
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/openPlace")
    @RequestMapping(value = "/inner/scenes/openPlace", method = RequestMethod.GET)
    ApiResponse<List<AppSceneVo>> openPlaceQuery(@RequestParam("uid") String uid);

    /**
     * 执行手动场景
     *
     * @param sceneId
     * @return
     */
//    @PostMapping("/inner/scenes/Manual/{sceneId}")
    @RequestMapping(value = "/inner/scenes/Manual/{sceneId}", method = RequestMethod.POST)
    ApiResponse sceneHandle(@PathVariable("sceneId") String sceneId);


    /**
     * 执行位置场景
     *
     * @param sceneId
     * @return
     */
//    @PostMapping("/inner/scenes/place/{sceneId}")
    @RequestMapping(value = "/inner/scenes/place/{sceneId}", method = RequestMethod.POST)
    ApiResponse scenePlace(@PathVariable("sceneId") String sceneId);


    /**
     * 查询是否拥有预制场景
     *
     * @param uid
     * @return
     */
//    @GetMapping("/inner/scenes/isExistPreScene")
    @RequestMapping(value = "/inner/scenes/isExistPreScene", method = RequestMethod.GET)
    ApiResponse<Boolean> isExistPreScene(@RequestParam("uid") String uid);

    /**
     * 初始化预制场景
     *
     * @return
     */
//    @PostMapping("/inner/scenes/init")
    @RequestMapping(value = "/inner/scenes/init", method = RequestMethod.POST)
    ApiResponse initScene();


    /**
     * 初始化个人预制场景
     *
     * @param uid
     * @return
     */
//    @PostMapping("/inner/scenes/createUserInitScene")
    @RequestMapping(value = "/inner/scenes/createUserInitScene", method = RequestMethod.POST)
    ApiResponse createUserInitScene(@RequestParam("uid") String uid);


}
