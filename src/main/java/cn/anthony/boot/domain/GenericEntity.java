package cn.anthony.boot.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class GenericEntity implements Serializable {
    private static final long serialVersionUID = 4365837246243902781L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "ctime", nullable = false)
    protected Timestamp ctime;

    @JsonIgnore
    transient protected Boolean create = false;
    @JsonIgnore
    transient protected String action;
    @JsonIgnore
    transient protected String actionDesc;

    public GenericEntity() {
	this.ctime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public String getAction() {
	if (isCreate())
	    return "add";
	else
	    return "edit";
    }

    public String getActionDesc() {
	if (isCreate())
	    return "添加";
	else
	    return "修改";
    }

    public boolean isCreate() {
	if (id == null)
	    create = true;
	return create;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Timestamp getCtime() {
	return ctime;
    }

    public void setCtime(Timestamp ctime) {
	this.ctime = ctime;
    }
}
