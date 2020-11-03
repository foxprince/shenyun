package cn.anthony.boot.domain;

import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Value
@Document
public class DepartmentConfig extends GenericNoSQLEntity {
	String name;
	String type;
	List<String> sourceNames;
	List<String> doctors;
}
