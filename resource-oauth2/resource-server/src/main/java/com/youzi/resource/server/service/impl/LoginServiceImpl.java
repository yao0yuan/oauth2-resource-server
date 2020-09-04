package com.youzi.resource.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.youzi.common.server.config.RedisBaseDao;
import com.youzi.common.server.enums.JsonResultEnum;
import com.youzi.common.server.feign.OauthServiceFeign;
import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.util.CacheUtil;
import com.youzi.common.server.util.JsonResultUtil;
import com.youzi.resource.server.controller.UserInfoController;
import com.youzi.resource.server.mapper.LoginMapper;
import com.youzi.resource.server.service.LoginService;
import com.youzi.resource.server.service.UserInfoService;
import com.youzi.resource.server.vo.LoginUserReqVo;
import com.youzi.resource.server.vo.RegistUserReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.youzi.common.server.result.JsonResult.SUCCESS;


@Service
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private OauthServiceFeign oauthServiceFeign;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public JsonResult<Map<String, Object>> login(LoginUserReqVo loginUserReqVo) {
        logger.info("登录，入参封装："+JSON.toJSONString(loginUserReqVo));
        JsonResult<Map<String,Object>> jsonResult = new JsonResult<>();
        try {
            final Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("grant_type", "password");
            parameters.put("client_id", "user-client");
            parameters.put("client_secret", "user-secret-8888");
            parameters.put("username", loginUserReqVo.getLoginName());
            parameters.put("password", loginUserReqVo.getPassword());
            final Map<String, Object> tokenInfo = oauthServiceFeign.postAccessToken(parameters);
            //获取token
            String accessToken = tokenInfo.get("access_token").toString();
            String refreshToken = tokenInfo.get("refresh_token").toString();
            //根据userName查询用户信息
            Map<String,Object> map = new HashMap<>();
            map.put("loginName",loginUserReqVo.getLoginName());
            Map<String,Object> userInfoMap = userInfoService.getLoginUserInfo(map).getInfo();
            //存入缓存
            CacheUtil.setString("String:Session:"+accessToken,JSON.toJSONString(userInfoMap));
            CacheUtil.expire("String:Session:"+accessToken,60*60);
            Map<String,Object> tokenMap = new HashMap<>();
            tokenMap.put("accessToken", accessToken);
            tokenMap.put("refreshToken", refreshToken);
            JsonResultUtil.setJsonResult(jsonResult, SUCCESS, JsonResultEnum.SUCCESS,tokenMap);
            logger.info("用户："+loginUserReqVo.getLoginName()+"登录成功！");
        }catch (Exception e){
            logger.error("用户登录失败，",e);
            JsonResultUtil.setJsonResult(jsonResult, JsonResult.FAIL, JsonResultEnum.INTERNAL_SERVER_ERROR);
        }
        return jsonResult;
    }

    @Override
    public JsonResult<String> regist(RegistUserReqVo registUserReqVo) {
        JsonResult<String> jsonResult = new JsonResult<>();
        try {
            registUserReqVo.setPassword(new BCryptPasswordEncoder(10).encode(registUserReqVo.getPassword()));
            loginMapper.saveUser(registUserReqVo);
            JsonResultUtil.setJsonResult(jsonResult, SUCCESS, JsonResultEnum.SUCCESS);
        }catch (Exception e){
            JsonResultUtil.setJsonResult(jsonResult, JsonResult.FAIL, JsonResultEnum.SAVE_ERROR);
        }
        return jsonResult;
    }


}
