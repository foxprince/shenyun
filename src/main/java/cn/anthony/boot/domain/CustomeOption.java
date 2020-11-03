package cn.anthony.boot.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@QueryEntity
@Document
@Data
public class CustomeOption extends GenericNoSQLEntity {
	public String name;
	public String[] fields;

	public CustomeOption() {
	}

	public String getArrayString() {
		StringBuilder sb = new StringBuilder();
		for (String o : fields)
			sb.append(o + "-");
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
