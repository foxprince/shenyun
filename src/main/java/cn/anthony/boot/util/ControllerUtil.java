package cn.anthony.boot.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class ControllerUtil {
    private static Map<String, String> provinceMap = new TreeMap<String, String>() {
	private static final long serialVersionUID = -3030414655735281228L;

	{
	    put("广东", "广东");
	    put("吉林", "吉林");
	    put("江苏", "江苏");
	    put("上海", "上海");
	    put("江西", "江西");
	    put("天津", "天津");
	    put("宁夏", "宁夏");
	    put("山东", "山东");
	    put("新疆", "新疆");
	    put("北京", "北京");
	    put("河南", "河南");
	    put("湖北", "湖北");
	    put("甘肃", "甘肃");
	    put("陕西", "陕西");
	    put("重庆", "重庆");
	    put("贵州", "贵州");
	    put("湖南", "湖南");
	    put("山西", "山西");
	    put("河北", "河北");
	    put("广西", "广西");
	    put("浙江", "浙江");
	    put("海南", "海南");
	    put("安徽", "安徽");
	    put("四川", "四川");
	    put("福建", "福建");
	    put("西藏", "西藏");
	    put("青海", "青海");
	    put("黑龙江", "黑龙江");
	    put("辽宁", "辽宁");
	    put("云南", "云南");
	    put("内蒙", "内蒙");
	}
    };

    public static <T> void setPageVariables(Model mav, Page<T> page) {
	mav.addAttribute("itemList", page.getContent());
	int current = page.getNumber() + 1;
	int begin = Math.max(1, current - 5);
	int end = Math.min(begin + 10, page.getTotalPages());
	mav.addAttribute("beginIndex", begin);
	mav.addAttribute("endIndex", end);
	mav.addAttribute("currentIndex", current);
	mav.addAttribute("pageSize", page.getSize());
	mav.addAttribute("total", page.getTotalElements());
	mav.addAttribute("totalPages", page.getTotalPages());
	mav.addAttribute("isFirst", page.isFirst());
	mav.addAttribute("hasPrevious", page.hasPrevious());
	mav.addAttribute("hasNext", page.hasNext());
	mav.addAttribute("isLast", page.isLast());
    }

    public static Map<Boolean, String> getActiveMap() {
	Map<Boolean, String> m = new LinkedHashMap<Boolean, String>();
	m.put(Boolean.TRUE, "开");
	m.put(Boolean.FALSE, "关");
	return m;
    }

    public static Map<String, String> getProvinceMap() {
	return provinceMap;
    }

}
