package com.youzi.common.server.result;

import com.alibaba.fastjson.JSON;

/**
 * @author lenovo
 *
 * @param <T>
 */
public class JsonResult<T> {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	private int result;
	private int resCode;
	private String msg;
	private T info;
	//private long total;

	public JsonResult() {
	}

	public JsonResult(int result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public JsonResult(int result, int resCode, String msg, T info) {
		this.result = result;
		this.msg = msg;
		this.info = info;
		this.resCode = resCode;
	}

	public JsonResult(int result, String msg, T info, long total) {
		this.result = result;
		this.msg = msg;
		this.info = info;
		//this.total = total;
	}

	public JsonResult(int result, String msg, T info) {
		this.result = result;
		this.msg = msg;
		this.info = info;
	}

	public JsonResult(int result, T info) {
		this.result = result;
		this.info = info;
	}

	@SuppressWarnings("rawtypes")
    public static JsonResult success(String msg) {
		final JsonResult result = new JsonResult(SUCCESS, msg);
		return result;
	}

	@SuppressWarnings("rawtypes")
    public static JsonResult fail(String msg) {
		final JsonResult result = new JsonResult(FAIL, msg);
		return result;
	}

	@SuppressWarnings("rawtypes")
    public JsonResult put(T t) {
		info = t;
		return this;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

//	public long getTotal() {
//		return total;
//	}
//
//	public void setTotal(long total) {
//		this.total = total;
//	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	@Override
	public String toString() {
		return "JsonResult{"
				+ "result=" + result
				+ ", resCode=" + resCode
				+ ", msg='" + msg + '\''
				+ ", info=" + info
				+ '}';
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	public JsonResult(int result, int resCode, String msg) {
		super();
		this.result = result;
		this.resCode = resCode;
		this.msg = msg;
	}
	
	
}
