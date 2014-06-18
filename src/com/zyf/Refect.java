package com.zyf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Refect {
	/**
	 * 根据属性名获取属性值
	 * */
	private Object getFieldValueByName(String fieldName, Object o) {
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

	/**
	 * 获取属性名数组
	 * */
	private String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getType());
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * */
	public Map getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		Map infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap();

			Class classType = fields[i].getType();

			System.out.println(fields[i].getName());
			if (classType == String.class || classType == Integer.class
					|| classType == Long.class || classType.isPrimitive()
					|| classType == Double.class || classType == Float.class) {
				System.out.println("is put :" + true);
			}

			System.out.println(getFieldValueByName(fields[i].getName(), o));
			System.out.println("==============================");
			infoMap.put("type", fields[i].getType().toString());

			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
		}
		return infoMap;
	}

	public static void main(String[] args) {
		new Refect().getFiledsInfo(new Merchant());
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 * */
	public Object[] getFiledValues(Object o) {
		String[] fieldNames = this.getFiledName(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			value[i] = this.getFieldValueByName(fieldNames[i], o);
		}
		return value;
	}

}
