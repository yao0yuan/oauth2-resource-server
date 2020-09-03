package com.youzi.common.server.feign;

import com.youzi.common.server.config.FeignConfig;
import com.youzi.common.server.result.JsonResult;
import com.youzi.common.server.vo.UserDetailInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "resource-server",configuration = FeignConfig.class)
public interface ResourceServiceFeign {


    @PostMapping("/UserInfoController/getLoginUserInfo")
    JsonResult<Map<String,Object>> getLoginUserInfo(@RequestBody Map<String,Object> map);
}
