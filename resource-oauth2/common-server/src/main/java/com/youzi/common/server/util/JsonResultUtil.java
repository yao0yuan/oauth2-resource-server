package com.youzi.common.server.util;


import com.youzi.common.server.enums.JsonResultEnum;
import com.youzi.common.server.result.JsonResult;

public class JsonResultUtil {

    public static void setJsonResult(JsonResult<? extends Object> jsonResult, Integer result, JsonResultEnum resultEnum) {
        jsonResult.setResult(result);
        jsonResult.setMsg(resultEnum.getErrMsg());
        jsonResult.setResCode(resultEnum.getErrCode());
    }

    public static <T> void setJsonResult(JsonResult<T> jsonResult, Integer result, JsonResultEnum resultEnum, T info) {
        jsonResult.setResult(result);
        jsonResult.setMsg(resultEnum.getErrMsg());
        jsonResult.setResCode(resultEnum.getErrCode());
        jsonResult.setInfo(info);
    }

    public static <T> void setJsonResultERROR(JsonResult<T> jsonResult) {
        jsonResult.setResult(JsonResult.FAIL);
        jsonResult.setMsg(JsonResultEnum.INTERNAL_SERVER_ERROR.getErrMsg());
        jsonResult.setResCode(JsonResultEnum.INTERNAL_SERVER_ERROR.getErrCode());
    }

}
