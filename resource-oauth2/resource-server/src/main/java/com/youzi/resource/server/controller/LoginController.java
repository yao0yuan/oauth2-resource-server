package com.youzi.resource.server.controller;

import com.youzi.common.server.result.JsonResult;
import com.youzi.resource.server.service.LoginService;
import com.youzi.resource.server.vo.LoginUserReqVo;
import com.youzi.resource.server.vo.RegistUserReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/LoginController")
public class LoginController {


    @Autowired
    private LoginService loginService;

    /**
     * @Description: 登录
     * @author: yaoyuan
     * @date:  2020/8/12 10:41
     * @version V1.0
     */
    @PostMapping("/login")
    public JsonResult<Map<String,Object>> login(@Validated @RequestBody LoginUserReqVo loginUserReqVo, HttpServletRequest req){
        return loginService.login(loginUserReqVo);
    }

    /**
     * @Description: 注册
     * @author: yaoyuan
     * @date:  2020/8/14 9:13
     * @version V1.0
     */
    @PostMapping("/regist")
    public JsonResult<String> regist(@Validated @RequestBody RegistUserReqVo registUserReqVo){
        return loginService.regist(registUserReqVo);
    }

}
