package cn.anthony.boot.domain;

public class PatientPO {
	String id;
	String name;
	int fs;
	int is;
	int os;
	int xs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getFs() {
		return fs;
	}

	public int getIs() {
		return is;
	}

	public int getOs() {
		return os;
	}

	public int getXs() {
		return xs;
	}

	public PatientPO(String id, String name, int fs, int is, int os, int xs) {
		super();
		this.id = id;
		this.name = name;
		this.fs = fs;
		this.is = is;
		this.os = os;
		this.xs = xs;
	}
}
