package cn.anthony.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class KeyGroup<T> {
	private T key;
	private Integer count;
	public KeyGroup(T key, Integer count) {
	    super();
	    this.key = key;
	    this.count = count;
	}
	
	public List<Object> getColumnKey() {
	    List<Object> l = new ArrayList<Object>();
	    if(key instanceof Map)
		l = new ArrayList(((Map)key).values());
	    else
		l.add(key);
	    return l;
	}
}
