package cn.anthony.boot.domain;

import java.util.List;

import lombok.Data;

@Data public class Remark extends GenericNoSQLEntity {
	private String label,content,doctor,operator;
	private List<String> filenames;
	public Remark() {
		super();
	} 
	
//	@Override
//	public boolean equals(Object that) {
//		return this.getId().equalsIgnoreCase(((Remark)that).getId());
//	}
}
