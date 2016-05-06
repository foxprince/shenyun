package cn.anthony.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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

    public static void setObjectValue(Object o, Map<String, String> m) {
	Field[] fields = o.getClass().getDeclaredFields();
	for (Field field : fields) {
	    try {
		field.setAccessible(true); // 设置些属性是可以访问的
		String key = field.getName();
		Class<?> c = field.getType();
		if (!key.startsWith("this$")) // 过滤掉内部类对父类的引用
		    if (m.containsKey(key)) {
			String value = StringTools.printString(m.get(field.getName()));
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


}
