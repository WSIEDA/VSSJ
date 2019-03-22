package cc.com.jsoft.pojo;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

public class ResponseManager {

	private int code;
	private boolean state;
	private T result;
	private String message;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static Map<String, Object> success(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "OK");
		map.put("result", data);
		return map;
	}
	
	public static Map<String, Object> error(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 400);
		map.put("message", "error");
		map.put("result", data);
		return map;
	}
}
