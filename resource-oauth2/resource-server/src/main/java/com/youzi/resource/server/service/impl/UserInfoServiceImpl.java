package com.youzi.resource.server.service.impl;

import com.youzi.common.server.enums.JsonResultEnum;
import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.util.JsonResultUtil;
import com.youzi.common.server.vo.UserDetailInfoVo;
import com.youzi.common.server.vo.UserRoleInfoVo;
import com.youzi.resource.server.mapper.UserInfoMapper;
import com.youzi.resource.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.youzi.common.server.result.JsonResult.SUCCESS;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public JsonResult<Map<String,Object>> getLoginUserInfo(Map<String,Object> map) {
        JsonResult<Map<String,Object>> jsonResult = new JsonResult<>();
        try {
            if (StringUtils.isEmpty(map.get("loginName"))){
                JsonResultUtil.setJsonResult(jsonResult, JsonResult.FAIL, JsonResultEnum.PARAM_ISNULL);
                return jsonResult;
            }
            Map<String,Object> userDetailMap = new HashMap<>();
            //查询用户信息
            userDetailMap = userInfoMapper.getLoginUserInfo(map.get("loginName").toString());
            //查询角色信息
            List<UserRoleInfoVo> userRoleInfoVoList = userInfoMapper.queryUserRoleByLoginName(userDetailMap.get("userId").toString());
            if (userRoleInfoVoList != null){
                //userDetailInfoVo.setUserRoleInfoVoList(userRoleInfoVoList);
                userDetailMap.put("userRoleInfoVoList",userRoleInfoVoList);
            }
            JsonResultUtil.setJsonResult(jsonResult, SUCCESS, JsonResultEnum.SUCCESS,userDetailMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }
}
