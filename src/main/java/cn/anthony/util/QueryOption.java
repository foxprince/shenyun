package cn.anthony.util;

import lombok.Data;

@Data
public class QueryOption {
    String key,value,andOr,option;

    public QueryOption(String key, String value, String andOr, String option) {
	super();
	this.key = key;
	this.value = value;
	this.andOr = andOr;
	this.option = option;
    }
    
}
