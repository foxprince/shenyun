package cn.anthony.boot.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class FileAsset extends GenericNoSQLEntity {
	private static final long serialVersionUID = -8566135736622133604L;
	String fileName,filePath,vistiPath,nr;
	Boolean isDir;

	public String getVisitUrl() {
		if(StringUtils.isNotBlank(filePath))
			return "http://172.21.17.9"+filePath.replaceAll(":","");
		else
			return "#";
	}
}
