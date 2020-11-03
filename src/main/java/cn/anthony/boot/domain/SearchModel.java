package cn.anthony.boot.domain;

import cn.anthony.boot.util.Constant;
import cn.anthony.util.QueryOption;
import cn.anthony.util.StringTools;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QueryEntity
@Document
@Data
public class SearchModel extends GenericNoSQLEntity {
	public SearchModel() {
		fields = new ArrayList<QueryOption>();
	}

	public SearchModel(String type, Map<String, List<String>> paramMap, Page<Patient> page) {
		this.type = type;
		hits = page.getTotalElements();
		fields = new ArrayList<QueryOption>();
		for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			if (Constant.ALL_KEY_MAP.containsKey(key) && !(key.endsWith("_andOr") || key.endsWith("_option"))) {
				for (int i = 0; i < entry.getValue().size(); i++) {
					if (StringTools.checkNull(entry.getValue().get(i)) != null) {
						String value = StringTools.printString(entry.getValue().get(i));
						String andOr = "and";// 缺省比较方法是与
						try {
							andOr = paramMap.get(key + "_andOr").get(i);
						} catch (NullPointerException e) {
						}
						String option = "contains";// 缺省比较方式是包含
						try {
							option = paramMap.get(key + "_option").get(i);
						} catch (NullPointerException e) {
						}
						addField(key, value, andOr, option);
					}
				}
			}
		}
	}

	public Map<String, String> getKeyValueMap() {
		Map<String, String> m = new HashMap<String, String>();
		for (QueryOption sf : getFields())
			m.put(sf.getKey(), sf.getValue());
		return m;
	}

	public String operator;
	public String type;// 1:标准，2：高级
	public Long hits;
	public String orderBy, sort;
	public List<QueryOption> fields;

	public String getFieldsDesc() {
		StringBuilder sb = new StringBuilder();
		for (QueryOption sf : fields)
			sb.append(sf.toPlainString() + ",");
		if(sb.length()>0)
			return sb.substring(0, sb.length() - 1);
		else
			return sb.toString();
	}

	public void addField(String k, String v, String andOr, String option) {
		String inputType = Constant.ALL_KEY_MAP.get(k).getInputType();
		QueryOption q = new QueryOption(k, v, andOr, option);
		q.setInputType(inputType);
		this.fields.add(q);
	}
}
