package cn.anthony.boot.domain;

import cn.anthony.util.StringTools;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Remark extends GenericNoSQLEntity {
	private String label, content, doctor, operator;
	private List<String> filenames;

	public List<String> getThumbNames() {
		List<String> l = new ArrayList<>();
		if(filenames!=null)
			for(String s : filenames)
				l.add(StringTools.getThumbName(s));
		return l;
	}
	public Remark() {
		super();
	}

	public Remark(String remarkId) {
		this.id = remarkId;
	}

	public boolean equals(Remark that) {
		System.out.println("check equal:" + this.getId() + "," + that.getId());
		return this.getId().equalsIgnoreCase((that).getId());
	}
}
