package cc.com.jsoft.utils;

import java.util.Date;
import java.util.List;

/**
 * 数据类型的判断
 * @author Administrator
 *
 */
public class ObjectUtils {
	
	/** 
	 *  判断String是否为空 
	 * @param str
	 * @return
	 */
	public boolean isEmpty (Object str) {
		if (null == str || "".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	
	/** 是否是String类型的
	 * @param obj
	 * @return
	 */
	public boolean isString (Object obj) {
		if (obj.getClass().equals(java.lang.String.class)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public boolean isList (@SuppressWarnings("rawtypes") List list) {
		if (list != null && !list.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断日期是否为空
	 * @param date
	 * @return
	 */
	public boolean isDate (Date date) {
		if (date != null) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	

}
