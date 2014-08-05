package com.zyf.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Sort {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantbizid", "" + System.currentTimeMillis());
		map.put("tradetype", "1");
		map.put("tradeexplan", "����");
		map.put("todepositacct", "6226200103289030");
		map.put("todepositacctname", "��Ʒ�");
		map.put("tosubbankname", "��������");
		map.put("bankno", "MSYH");
		map.put("applymoney", "0.01");
		map.put("paychannel", "b2e_chinapay");
		map.put("merchantid", "jrj_ylb");
		String merchantid = "jrj_ylb";
		map.put("password", 123);
		map.put("merchantid", merchantid);
		map.put("chkvalue", "chkvalue");

		System.out.println(getSortString(map, "chkvalue"));
	}

	public static String getSortString(Map<String, Object> map, String filterStr) {
		Set<String> s = map.keySet();
		Iterator<String> itor = s.iterator();
		StringBuffer strs = new StringBuffer();
		while (itor.hasNext()) {
			String key = itor.next();
			if (key.equals(filterStr)) {
				continue;
			}
			strs.append(key);
			strs.append("=");
			strs.append(map.get(key) + "&");
		}

		if (strs.length() > 0) {
			return strs.substring(0, strs.length() - 1);
		}
		return strs.toString();
	}

	/**
	 * ȡ��bean��,���������
	 * 
	 * @param bean
	 * @param filterStr
	 *            ����Ҫ���в������ַ�
	 * @return
	 */
	public static String getSortForJson(String json, String filterStr) {
		return filterStr;
	}

	/**
	 * ȡ��bean��,���������
	 * 
	 * @param bean
	 * @param filterStr
	 *            ����Ҫ���в������ַ�
	 * @return
	 */
	public static String getSortString(Object bean, String filterStr) {
		if (bean == null) {
			return "";
		}
		return getSortString(getByObj(bean), filterStr);
	}

	/**
	 * ȡ�ö����л�������ͣ�����map
	 * 
	 * @param bean
	 * @return
	 */
	private static Map<String, Object> getByObj(Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		Map<String, Object> infoMap = new HashMap<String, Object>();
		for (int i = 0; i < fields.length; i++) {
			Class classType = fields[i].getType();
			if (classType.isPrimitive() || classType == String.class
					|| classType == Integer.class || classType == Long.class
					|| classType == Double.class || classType == Float.class) {
				infoMap.put(fields[i].getName(),
						getFieldValueByName(fields[i].getName(), bean));
			}
		}
		return infoMap;
	}

	/**
	 * ����������ȡ����ֵ
	 * */
	private static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
