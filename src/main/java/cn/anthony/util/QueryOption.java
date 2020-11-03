package cn.anthony.util;

import cn.anthony.boot.util.Constant;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class QueryOption {
	String inputType, key, value, andOr, option;

	public QueryOption(String key, String value, String andOr, String option) {
		super();
		this.key = key;
		this.value = value;
		this.andOr = andOr;
		this.option = option;
	}

	public String getKeyDesc() {
		if (!ObjectUtils.isEmpty(Constant.ALL_KEY_MAP.get(key)))
			return Constant.ALL_KEY_MAP.get(key).getLabel();
		else
			return key;
	}

	public String toPlainString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constant.getAndOrString(andOr) + "{");
		if (Constant.ALL_KEY_MAP.containsKey(key) && StringTools.checkNull(Constant.ALL_KEY_MAP.get(key)) != null)
			sb.append(Constant.ALL_KEY_MAP.get(key).getLabel());
		else
			sb.append(key);
		sb.append(" " + Constant.getOptionString(option) + " ");
		sb.append(value);
		sb.append("}");
		return sb.toString();
	}
}
