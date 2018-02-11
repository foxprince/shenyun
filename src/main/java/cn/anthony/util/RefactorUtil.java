package cn.anthony.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ListPath;

import cn.anthony.boot.domain.ExtendObject;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient_Diag;
import cn.anthony.boot.domain.QPatient_OperationDetail;
import cn.anthony.boot.domain.QPatient_OutDiag;
import cn.anthony.boot.domain.QPatient_SevereDetail;
import cn.anthony.boot.domain.QPatient_动眼神经;
import cn.anthony.boot.domain.QPatient_反射;
import cn.anthony.boot.domain.QPatient_听力;
import cn.anthony.boot.domain.QPatient_头部反射;
import cn.anthony.boot.domain.QPatient_痛触觉;
import cn.anthony.boot.domain.QPatient_眼底;
import cn.anthony.boot.domain.QPatient_视力;
import cn.anthony.boot.domain.QPatient_颅神经;
import cn.anthony.boot.domain.QPatient_高级皮层功能;
import cn.anthony.boot.domain.QSomatoscopy_SpecialExamination;
import cn.anthony.boot.domain.Somatoscopy;
import cn.anthony.boot.domain.Somatoscopy.SpecialExamination;

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

	public static Object initPathClass(Path p) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// System.out.println(p.getType().toString().substring(6));
		return Class.forName(p.getType().toString().substring(6)).newInstance();
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

	public static void main(String[] args) {
		System.out.println(getObjectParaMap(new ExtendObject.出血组()));
	}

	public static Map<String, String> getObjectParaMap(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Field field : fields) {
			try {
				field.setAccessible(true); // 设置些属性是可以访问的
				Object val = field.get(o);// 得到此属性的值
				if (!field.getName().startsWith("this$")) { // 过滤掉内部类对父类的引用
					m.put(field.getName(), StringTools.printString(val));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public static Map<String, String> getKeyValueMap(Object o, List<String> keys, String mappre) {
		Field[] fields = o.getClass().getDeclaredFields();
		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Field field : fields) {
			try {
				field.setAccessible(true); // 设置些属性是可以访问的
				Object val = field.get(o);// 得到此属性的值
				if (val instanceof List) {
					if (((List) val).size() > 0)
						m.putAll(getKeyValueMap(((List) val).get(0), keys, mappre));
				} else if (val instanceof Somatoscopy || val instanceof SpecialExamination || val instanceof Patient.高级皮层功能
						|| val instanceof Patient.颅神经 || val instanceof Patient.反射 || val instanceof Patient.视力
						|| val instanceof Patient.眼底 || val instanceof Patient.动眼神经 || val instanceof Patient.痛触觉
						|| val instanceof Patient.头部反射 || val instanceof Patient.听力 || val instanceof Patient.Diag
						|| val instanceof Patient.SevereDetail || val instanceof Patient.OperationDetail
						|| val instanceof Patient.OutDiag) {
					m.putAll(getKeyValueMap(val, keys, mappre));
				} else {
					if (!field.getName().startsWith("this$") && keys.contains(mappre + field.getName())) {
						Class<?> c = field.getType();
						String value = StringTools.printString(val);
						if (c.getCanonicalName().equals("java.util.Date"))
							value = DateUtil.format(((Date) val), DateUtil.TIME_FORMAT);
						m.put(mappre + field.getName(), value);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public static List<String> getQueraPaths(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		List<String> m = new ArrayList<String>();
		for (Field field : fields) {
			try {
				field.setAccessible(true); // 设置些属性是可以访问的
				Object val = field.get(o);// 得到此属性的值
				if (!field.getName().startsWith("this$")) { // 过滤掉内部类对父类的引用
					m.add(StringTools.printString(val));
					System.out.println(val.getClass().getName());
					if (!val.getClass().getName().equals(o.getClass().getName())) {
						if (val instanceof ListPath) {
							m.addAll(getQueraPaths(((ListPath) val).any()));
						} else if ((val instanceof QPatient_OutDiag || val instanceof QPatient_Diag
								|| val instanceof QPatient_OperationDetail || val instanceof QPatient_SevereDetail
								|| val instanceof QPatient_动眼神经 || val instanceof QPatient_反射 || val instanceof QPatient_听力
								|| val instanceof QPatient_头部反射 || val instanceof QPatient_痛触觉 || val instanceof QPatient_眼底
								|| val instanceof QPatient_视力 || val instanceof QPatient_颅神经 || val instanceof QPatient_高级皮层功能
								|| val instanceof QSomatoscopy_SpecialExamination)) {
							m.addAll(getQueraPaths(val));
						}
					}
				}
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

	public static Map<String, List<String>> filterEmpty(MultiValueMap<String, String> map) {
		Map<String, List<String>> m = new LinkedHashMap<String, List<String>>(map.size());
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			if (!ObjectUtils.isEmpty(entry.getValue()) && !isEmptyCollection(entry.getValue()))
				m.put(entry.getKey(), entry.getValue());
		}
		return m;
	}

	public static boolean isEmptyCollection(List<String> l) {
		for (String s : l)
			if (StringTools.checkNull(s) != null)
				return false;
		return true;
	}

	/**
	 * 获得对象的非空字段的键值对
	 * 
	 * @param o
	 * @param paramMap
	 * @return
	 */
	public static List<QueryOption> getNotNullValueMap(Object o, String requestPre, Map<String, List<String>> paramMap) {
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
					if (paramMap.containsKey(reqKey) && (!ObjectUtils.isEmpty(paramMap.get(reqKey)))) {
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
		return l;
	}
}
