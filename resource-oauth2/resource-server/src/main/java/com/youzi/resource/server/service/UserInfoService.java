package com.youzi.resource.server.service;

import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.vo.UserDetailInfoVo;

import java.util.Map;

public interface UserInfoService {

    JsonResult<Map<String,Object>> getLoginUserInfo(Map<String,Object> map);
}
