package cc.com.jsoft.utils;

import java.util.Date;
import java.util.List;

/**
 * �������͵��ж�
 * @author Administrator
 *
 */
public class ObjectUtils {
	
	/** 
	 *  �ж�String�Ƿ�Ϊ�� 
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
	
	/** �Ƿ���String���͵�
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
	 * �ж�list�Ƿ�Ϊ��
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
	 * �ж������Ƿ�Ϊ��
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
