package com.youzi.resource.server.mapper;


import com.youzi.resource.server.vo.RegistUserReqVo;
import org.apache.ibatis.annotations.Param;


public interface LoginMapper {

    void saveUser(@Param("registUserReqVo") RegistUserReqVo registUserReqVo);

}
