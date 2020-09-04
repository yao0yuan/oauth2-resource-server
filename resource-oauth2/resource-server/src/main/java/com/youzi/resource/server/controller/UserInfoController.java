package com.youzi.resource.server.controller;

import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.vo.UserDetailInfoVo;
import com.youzi.resource.server.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("UserInfoController")
public class UserInfoController {

    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;
    
    /**
     * @Description: 获取用户角色信息
     * @author: yaoyuan
     * @date:  2020/8/31 15:09
     * @version V1.0
     */
    @PostMapping("getLoginUserInfo")
    public JsonResult<Map<String,Object>> getLoginUserInfo(@RequestBody Map<String,Object> map){
        return userInfoService.getLoginUserInfo(map);
    }

    @PostMapping("hhh")
    public String hhh(){
        logger.info("测试日志输出");
        return "guaguagua";
    }
}
