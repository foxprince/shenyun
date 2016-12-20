package cn.anthony.boot.domain;

import java.util.List;

import lombok.Data;

@Data public class Remark extends GenericNoSQLEntity  {
	private String label,content,doctor,operator;
	private List<String> filenames;
	public Remark() {
		super();
	}
	public Remark(String remarkId) {
		this.id = remarkId;
	} 
	
	public boolean equals(Remark that) {
		System.out.println("check equal:"+this.getId()+","+that.getId());
		return this.getId().equalsIgnoreCase((that).getId());
	}
}
