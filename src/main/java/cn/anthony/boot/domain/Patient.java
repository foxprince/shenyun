package cn.anthony.boot.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "patient")
public class Patient extends GenericNoSQLEntity {

    private static final long serialVersionUID = -9199964027188332358L;

    private String name;

    private String description;

    private boolean active = true;
    @JsonIgnore
    transient private String activeDesc;
    @JsonIgnore
    transient private Map<Long, String> codeMap = new HashMap<Long, String>();

    public String getActiveDesc() {
	if (active)
	    return "开";
	else
	    return "关";
    }

    public Patient(String title, String description) {
	super();
	this.name = title;
	this.description = description;
    }

    public Patient() {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

}
