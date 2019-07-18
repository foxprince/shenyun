package cn.anthony.boot.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class FileAsset extends GenericNoSQLEntity {
	private static final long serialVersionUID = -8566135736622133604L;
	String fileName,filePath,nr;
	Boolean isDir;
}
