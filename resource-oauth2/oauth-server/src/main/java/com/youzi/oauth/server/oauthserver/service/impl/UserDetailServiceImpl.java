package com.youzi.oauth.server.oauthserver.service.impl;


import com.alibaba.fastjson.JSON;
import com.youzi.common.server.feign.ResourceServiceFeign;
import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.vo.UserDetailInfoVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component(value = "userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private ResourceServiceFeign resourceServiceFeign;


    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        JsonResult<Map<String,Object>> jsonResult = new JsonResult<>();
        UserDetailInfoVo userDetailInfoVo = new UserDetailInfoVo();
        try {

            Map<String,Object> userDetailMap = new HashMap<>();
            Map<String,Object> map = new HashMap<>();
            map.put("loginName",loginName);
            jsonResult = resourceServiceFeign.getLoginUserInfo(map);
            userDetailMap = jsonResult.getInfo();
            userDetailInfoVo = JSON.parseObject(JSON.toJSONString(userDetailMap), UserDetailInfoVo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userDetailInfoVo;
    }

}
