package com.youzi.common.server.enums;


public enum JsonResultEnum {

    SUCCESS(200, "成功"),

    INTERNAL_SERVER_ERROR(500, "服务器错误(允许进行重试)"),

    PARAM_ISNULL(400,"请求参数有为空"),

    SAVE_ERROR(701,"保存失败");


    /**
     * @description 字段功能描述
     * @value value:errCode
     */
    private Integer errCode;
    /**
     * @description 字段功能描述
     * @value value:errMsg
     */
    private String errMsg;

    /**
     *@constructor 构造方法
     *@param errCode 错误码
     *@param errMsg 错误描述
     */
    JsonResultEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     *@name   返回errCode
     *@return errCode
     */
    public Integer getErrCode() {
        return errCode;
    }

    /**
     *@name  赋值errCode
     *@param errCode 错误码
     */
    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    /**
     *@name   返回errMsg
     *@return errMsg 错误描述
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     *@name  赋值errMsg
     *@param errMsg 错误描述
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
