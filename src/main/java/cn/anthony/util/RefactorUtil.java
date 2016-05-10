package cn.anthony.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.MultiValueMap;

public class RefactorUtil {
    public static Field getFieldByName(Object o, String name) {
	Field[] fields = o.getClass().getDeclaredFields();
	for (Field field : fields) {
	    field.setAccessible(true); // 设置些属性是可以访问的
	    if (!field.getName().startsWith("this$") && field.getName().equals(name)) // 过滤掉内部类对父类的引用
		return field;
	}
	return null;
    }

    /**
     * 设置对象的字段值
     */
    public static void setFieldValue(Object o, String key, String value) {
	Field[] fields = o.getClass().getDeclaredFields();
	for (Field field : fields) {
	    field.setAccessible(true); // 设置些属性是可以访问的
	    if (!field.getName().startsWith("this$") && field.getName().equals(key))
		try {
		    if (field.getType().getCanonicalName().equals("java.lang.Boolean"))
			field.setBoolean(o, Boolean.getBoolean(value));
		    else
			field.set(o, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
		    e.printStackTrace();
		}
	}
    }

    public static Map<String, String> getObjectParaMap(Object o) {
	Field[] fields = o.getClass().getDeclaredFields();
	Map<String, String> m = new HashMap<String, String>();
	for (Field field : fields) {
	    try {
		field.setAccessible(true); // 设置些属性是可以访问的
		Object val = field.get(o);// 得到此属性的值
		if (!field.getName().startsWith("this$")) // 过滤掉内部类对父类的引用
		    m.put(field.getName(), StringTools.printString(val));
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		e.printStackTrace();
	    }
	}
	return m;
    }

    /**
     * 根据map的键值对设置对象字段值
     * 
     * @param o
     * @param m
     */
    public static void setObjectValue(Object o, Map<String, String> m) {
	Field[] fields = o.getClass().getDeclaredFields();
	for (Field field : fields) {
	    try {
		field.setAccessible(true); // 设置些属性是可以访问的
		String key = field.getName();
		Class<?> c = field.getType();
		if (!key.startsWith("this$")) // 过滤掉内部类对父类的引用
		    if (m.containsKey(key)) {
			String value = StringTools.printString(m.get(key));
			if (c.getCanonicalName().equals("java.lang.Integer") && StringTools.checkNull(m.get(field.getName())) != null)
			    try {
				field.set(o, Integer.parseInt(StringTools.pe(value, "(\\d+).*")));
			    } catch (NumberFormatException e) {
				// System.out.println("wrong format : " + key +
				// ":" + value);
			    }
			else if (c.getCanonicalName().equals("java.util.Date"))
			    try {
				if (key.equals("dateOfBirthday"))
				    field.set(o, new SimpleDateFormat("yyyy年MM月dd日").parse(value));
				else
				    field.set(o, new SimpleDateFormat("yyyy年MM月dd日HH时").parse(value));
			    } catch (ParseException e) {
			    }
			else if (c.getCanonicalName().equals("java.lang.Boolean"))
			    field.setBoolean(o, Boolean.getBoolean(value));
			else if (c.getCanonicalName().equals("java.lang.String"))
			    field.set(o, value);
		    }
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * 获得对象的非空字段的键值对
     * 
     * @param o
     * @param paramMap
     * @return
     */
    public static List<QueryOption> getNotNullValueMap(Object o, String requestPre, MultiValueMap<String, String> paramMap) {
	System.out.println(paramMap);
	List<QueryOption> l = new ArrayList<QueryOption>();
	Field[] selfFields = o.getClass().getDeclaredFields();
	// 父类属性
	Field[] parentFields = o.getClass().getSuperclass().getDeclaredFields();
	Field[] fields = ArrayUtils.addAll(selfFields, parentFields);
	for (Field field : fields) {
	    try {
		field.setAccessible(true); // 设置些属性是可以访问的
		String key = field.getName();
		String reqKey = requestPre + key;
		if (!key.startsWith("this$")) // 过滤掉内部类对父类的引用
		    if (paramMap.containsKey(reqKey) && (StringTools.checkNull(paramMap.get(reqKey)) != null)) {
			for (int i = 0; i < paramMap.get(reqKey).size(); i++) {
				String value = StringTools.printString(paramMap.get(reqKey).get(i));
			    String andOr = "and";// 缺省比较方法是与
			    try {
				andOr = paramMap.get(reqKey + "_andOr").get(i);
			    } catch (NullPointerException e) {
			    }
			    String option = "contains";// 缺省比较方式是包含
			    try {
				option = paramMap.get(reqKey + "_option").get(i);
			    } catch (NullPointerException e) {
			    }
				l.add(new QueryOption(key, value, andOr, option));
			    }
		    }
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    }
	}
	System.out.println(l);
	return l;
    }
}
