package cn.anthony.boot.web;

import lombok.Data;

@Data
public class JsonResponse {
	Integer code;
	String msg;
	Object data;
	
	public static JsonResponse success() {
		return new JsonResponse(0,"success",null);
	}
	
	public static JsonResponse success(Object data) {
		return new JsonResponse(0,"success",data);
	}
	
	public static JsonResponse fail(Integer code,String msg) {
		return new JsonResponse(code,msg,null);
	}

	public JsonResponse(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public JsonResponse(Object data) {
		setCode(0);
		setMsg("success");
		setData(data);
	}
}
