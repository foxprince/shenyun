package cn.anthony.boot.web;

public class PatientSearch extends PageRequest {
    String name;

    public PatientSearch() {
	super();
    }

    public PatientSearch(int page, int size, String name) {
	this.page = page;
	this.size = size;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }


}
