package cn.anthony.boot.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.data.domain.Page;

import com.mysema.query.annotations.QueryEntity;

import cn.anthony.boot.util.Constant;
import cn.anthony.boot.web.PatientSearch;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.StringTools;
import lombok.Data;

@QueryEntity
@Entity
@Data
public class SearchModel extends GenericEntity {
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
	    m.put(sf.getSkey(), sf.getSvalue());
	return m;
    }
    public String operator;
    public Long hits;
    public String orderBy,sort;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "searchModel")
    public List<SearchField> fields;
    public String getFieldsDesc() {
	StringBuilder sb = new StringBuilder();
	for(SearchField sf : fields)
	    if(Constant.keyNameMap.containsKey(sf.getSkey()))
		sb.append(Constant.keyNameMap.get(sf.getSkey())+":"+sf.getSvalue()+",");
	return sb.substring(0, sb.length()-1);
    }
    public void addField(String k, String v) {
	this.fields.add(new SearchField(k,v));
    }
}
