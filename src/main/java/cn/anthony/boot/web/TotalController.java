package cn.anthony.boot.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import cn.anthony.boot.service.KeyGroup;
import cn.anthony.boot.service.TotalService;
import cn.anthony.boot.util.Constant;
import lombok.Data;

@Controller
@RequestMapping(value = "/total")
public class TotalController {
    @Resource
    TotalService service;

    @ModelAttribute("pageRequest")
    PatientSearch initPageRquest() {
	return new PatientSearch();
    }

    @RequestMapping(value = { "/fullTotal" })
    public String fullTotal(String[] fields, Model m) throws Exception {
	List<KeyGroup> l = service.agg(10000, fields);
	m.addAttribute("keyCount", l);
	return "/patient/total";
    }

    @RequestMapping(value = { "/initTotal" })
    public String total(String[] fields, Model m) throws Exception {
	List<KeyGroup> l = service.agg(10000, fields);
	Map<Object, Integer> map = new LinkedHashMap<Object, Integer>();
	if (fields.length == 1 && fields[0].equals("frontRecords.age")) {
	    map = sortedAgeGroup(l);
	} else {
	    map = toMap(service.agg(9, fields));
	}
	//饼图的标签
	m.addAttribute("lables", getLables(map.keySet()));
	//每个标签对应的值
	m.addAttribute("valueGroup", map.values().toString());
	//列表的表头
	if (fields.length == 1)
	    m.addAttribute("columns", new ArrayList(){{add(Constant.getKeyDesc(fields[0]));}});
	else
	    m.addAttribute("columns", getColumns(map.keySet().iterator().next()));
	//饼图的数据
	m.addAttribute("pieData", toPieData(map));
	//列表数据
	m.addAttribute("keyCount", l);
	// 仅对单子段统计有用
	m.addAttribute("keyDesc", Constant.getKeyDesc(fields[0]));
	m.addAttribute("key", fields[0]);
	return "/patient/total";
    }

    private List<String> getLables(Set<Object> keySet) {
	List<String> l = new ArrayList<String>();
	for(Object o : keySet)
	    l.add("\""+(o.toString().length()>10?o.toString().substring(0,10):o.toString())+"\"");
	return l;
    }

    private List<String> getColumns(Object o) {
	List<String> l = new ArrayList<String>();
	if (o instanceof Map) {
	    Map m = (Map) o;
	    for (Object item : m.keySet())
		l.add(Constant.getKeyDesc(item.toString()));
	} else
	    l.add(o.toString());
	return l;
    }

    @RequestMapping(value = { "/initFullTotal" })
    public String initFullTotal(Model m) {
	m.addAttribute("totalOptions", Constant.totalKeyMap.values());
	return "/patient/fullTotal";
    }

    private Map<Object, Integer> toMap(List<KeyGroup> l) {
	Map<Object, Integer> m = new LinkedHashMap<Object, Integer>();
	for (KeyGroup k : l)
	    if (k.getKey() instanceof Map) {
		m.put(k.getKey(), k.getCount());
	    } else
		m.put(k.getKey().toString(), k.getCount());
	return m;
    }

    private Map<Object, Integer> sortedAgeGroup(List<KeyGroup> l) {
	Map<Object, Integer> m = new LinkedHashMap<Object, Integer>();
	for (KeyGroup kg : l) {
	    int d = ((Integer) kg.getKey()).intValue() % 10;
	    String key = "";
	    switch (d) {
	    case 0:
		key = "0-10";
		break;
	    case 1:
		key = "10-20";
		break;
	    case 2:
		key = "20-30";
		break;
	    case 3:
		key = "30-40";
		break;
	    case 4:
		key = "40-50";
		break;
	    case 5:
		key = "50-60";
		break;
	    case 6:
		key = "60-70";
		break;
	    case 7:
		key = "70-80";
		break;
	    default:
		key = "80+";
		break;
	    }
	    mapPut(m, key, kg.getCount());
	}
	TreeMap<Object, Integer> sortedMap = new TreeMap<Object, Integer>(new Comparator<Object>() {
	    @Override
	    public int compare(Object o1, Object o2) {
		return o1.toString().compareTo(o2.toString());
	    }
	});
	sortedMap.putAll(m);
	// Map<Object, Integer> sortedMap =
	// m.entrySet().stream().sorted(Map.Entry.comparingByKey())
	// .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	// (e1, e2) -> e1, LinkedHashMap::new));
	return sortedMap;
    }

    private void mapPut(Map<Object, Integer> m, String key, Integer count) {
	if (m.containsKey(key))
	    m.put(key, count + m.get(key));
	else
	    m.put(key, count);
    }

    private String toPieData(Map<Object, Integer> map) {
	List<PieData> l = new ArrayList<PieData>();
	int i = 0;
	for (Map.Entry<Object, Integer> entry : map.entrySet()) {
	    l.add(new PieData(entry.getValue(), Constant.pieColors[i], Constant.pieHighlights[i],
		    entry.getKey().toString()));
	    i++;
	}
	return JSON.toJSONString(l);
    }

    @Data
    public class PieData {
	Integer value;
	String color, highlight, label;

	public PieData(Integer value, String color, String highlight, String label) {
	    super();
	    this.value = value;
	    this.color = color;
	    this.highlight = highlight;
	    this.label = label;
	}

    }

}