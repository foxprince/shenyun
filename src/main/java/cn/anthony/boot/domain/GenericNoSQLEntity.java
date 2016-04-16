package cn.anthony.boot.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GenericNoSQLEntity implements Serializable {
    private static final long serialVersionUID = 4365837246243902781L;
    @Id
    protected String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date ctime;

    @JsonIgnore
    @Transient
    protected Boolean create = false;
    @JsonIgnore
    transient protected String action;
    @JsonIgnore
    transient protected String actionDesc;

    public GenericNoSQLEntity() {
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

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Date getCtime() {
	return ctime;
    }

    public void setCtime(Timestamp ctime) {
	this.ctime = ctime;
    }
}
