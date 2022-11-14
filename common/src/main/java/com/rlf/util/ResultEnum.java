package com.rlf.util;

/**
 * @author yingyongzhi
 */
public enum ResultEnum {
	/** 正确 **/
	SUCCESS_CODE(200,"正确"),
	/** 默认业务错误 **/
	DEFAULT_ERROR_CODE(-1,"业务错误"),
	/** 参数错误 **/
	PARAM_ERROR_CODE(400,"参数错误"),
	/** token 过期 **/
	TOKEN_TIMEOUT_CODE(401,"token 过期"),
	/** 禁止访问 **/
	NO_AUTH_CODE(402,"禁止访问"),
	/** 资源没找到 **/
	NOT_FOUND(404,"资源没找到"),
	/** 服务器错误 **/
	SERVER_ERROR_CODE(500,"服务器错误"),

	NETWORK_DESERTED_PLEASE_TRY_AGAIN_LATER(600,"网络开小差了，请稍后再试。"),
	/** 与其他系统交互异常 **/
	SYSTEM_NOTICE_EXCEPTION(501,"与其他系统交互异常"),
	/** 用户中心调用失败 **/
	USER_ERROR_CODE(501,"用户中心调用失败"),
	/** 订单中心调用失败 **/
	ORDER_ERROR_CODE(502,"订单中心调用失败"),
	/** 营销中心调用失败 **/
	MARKET_ERROR_CODE(503,"营销中心调用失败"),
	/** 文件中心调用失败 **/
	FILE_ERROR_CODE(504,"文件中心调用失败"),
	/** 短信中心调用失败 **/
	SMS_ERROR_CODE(505,"短信中心调用失败"),
	/** 数据中心调用失败 **/
	DATA_ERROR_CODE(506,"数据中心调用失败"),
	/** 商品中心调用失败 **/
	GOODS_ERROR_CODE(507,"商品中心调用失败"),
	/** 支付中心调用失败 **/
	PAY_ERROR_CODE(508,"支付中心调用失败"),
	/** 系统中心调用失败 **/
	SYSTEM_ERROR_CODE(509,"系统中心调用失败"),
	/** 门店中心调用失败 **/
	STORE_ERROR_CODE(510,"门店中心调用失败");

	private int code;
	private String msg;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private ResultEnum(int code) {
		this.code = code;
	}

	private ResultEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResultEnum{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';
	}
}
