package cn.anthony.boot.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import cn.anthony.util.StringTools;

public class RefactorUtil {

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
}
