package com.youzi.resource.server.service;

import com.youzi.common.server.result.JsonResult;
import com.youzi.resource.server.vo.LoginUserReqVo;
import com.youzi.resource.server.vo.RegistUserReqVo;

import java.util.Map;

public interface LoginService {

    JsonResult<Map<String,Object>> login(LoginUserReqVo loginUserReqVo);

    JsonResult<String> regist(RegistUserReqVo registUserReqVo);
}
