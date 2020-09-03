package com.youzi.resource.server.mapper;


import com.youzi.common.server.vo.UserDetailInfoVo;
import com.youzi.common.server.vo.UserRoleInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {

    Map<String,Object> getLoginUserInfo(String loginName);

    List<UserRoleInfoVo> queryUserRoleByLoginName(String userId);
}
