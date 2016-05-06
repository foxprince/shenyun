package cn.anthony.boot.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mysema.query.annotations.QueryEntity;

import cn.anthony.boot.util.Constant;
import cn.anthony.boot.web.PatientSearch;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.StringTools;
import lombok.Data;

@QueryEntity
@Document
@Data
public class SearchModel extends GenericNoSQLEntity {
    public SearchModel(PatientSearch ps, Page<Patient> page) {
	hits = page.getTotalElements();
	fields = new ArrayList<SearchField>();
	for(Map.Entry<String, String> entry : RefactorUtil.getObjectParaMap(ps).entrySet()) {
	    if(StringTools.checkNull(entry.getValue())!=null)
		fields.add(new SearchField(entry.getKey(),entry.getValue()));
	}
    }
    public SearchModel() {
	fields = new ArrayList<SearchField>();
    }
    public Map<String,String> getKeyValueMap() {
	Map<String,String> m = new HashMap<String,String>();
	for(SearchField sf : getFields())
	    m.put(sf.getKey(), sf.getValue());
	return m;
    }
    public String operator;
    public Long hits;
    public String orderBy,sort;
    public List<SearchField> fields;
    public String getFieldsDesc() {
	StringBuilder sb = new StringBuilder();
	for(SearchField sf : fields)
	    if(Constant.keyNameMap.containsKey(sf.getKey()))
		sb.append(Constant.keyNameMap.get(sf.getKey())+":"+sf.getValue()+",");
	return sb.substring(0, sb.length()-1);
    }
    public @Data class SearchField {
	public String key,value;

	public SearchField(String key, String value) {
	    super();
	    this.key = key;
	    this.value = value;
	}
	
    }
    public void addField(String k, String v) {
	this.fields.add(new SearchField(k,v));
    }
}
