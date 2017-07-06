package cn.anthony.boot.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.anthony.util.DateUtil;

public class GenericNoSQLEntity implements Serializable {
	private static final long serialVersionUID = 4365837246243902781L;
	@Id
	protected String id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat
	protected Date ctime;
	@JsonIgnore
	@Transient
	protected Boolean create = false;
	@JsonIgnore
	@Transient
	transient protected String action;
	@JsonIgnore
	@Transient
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

	public String getFormatCtime() {
		return DateUtil.format(getCtime(), "yyyy-MM-dd HH:mm:ss");
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
}
