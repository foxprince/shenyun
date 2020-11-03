package cn.anthony.boot.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@QueryEntity
@Document
@Data
public class ImportModel extends GenericNoSQLEntity {
	// 分类
	public String type;
	public String srcName;
	public String dstName;
	public Integer total;
	public Integer success;
	public Integer insertTotal;
	public List<String> insertList;
	public Integer updateTotal;
	public List<String> updateList;
	public Integer operator;

	public ImportModel() {
	}

	public ImportModel(String type, String srcName, String dstName, Integer total, Integer success, Integer insertTotal,
			List<String> insertList, Integer updateTotal, List<String> updateList, Integer operator) {
		super();
		this.type = type;
		this.srcName = srcName;
		this.dstName = dstName;
		this.total = total;
		this.success = success;
		this.insertTotal = insertTotal;
		this.insertList = insertList;
		this.updateList = updateList;
		this.updateTotal = updateTotal;
		this.operator = operator;
	}
}
