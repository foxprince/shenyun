package cn.anthony.boot.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "patient")
public class Patient extends GenericNoSQLEntity {

    private static final long serialVersionUID = -9199964027188332358L;

    public String pId;/* 病案号 */
    public String name;
    public Integer age;
    public String sex;
    public Date dateOfBirth;
    public String certNo;/* 身份证号码 */
    public List<FrontPage> frontRecords = new ArrayList();// 首页纪录
    public List<InHospital> inRecords = new ArrayList();/* 入院纪录 */
    public List<Operation> operations = new ArrayList();/* 手术纪录 */
    public List<OutHospital> outRecords = new ArrayList();/* 出院纪录 */

    public Patient(String pId, String name, Integer age, String sex, Date dateOfBirth, String certNo, List<FrontPage> frontRecords,
	    List<InHospital> inRecords, List<Operation> operations, List<OutHospital> outRecords, boolean active, String activeDesc) {
	super();
	this.pId = pId;
	this.name = name;
	this.age = age;
	this.sex = sex;
	this.dateOfBirth = dateOfBirth;
	this.certNo = certNo;
	this.frontRecords = frontRecords;
	this.inRecords = inRecords;
	this.operations = operations;
	this.outRecords = outRecords;
	this.active = active;
	this.activeDesc = activeDesc;
    }

    @Transient // 此注解用于不映射到数据库
    private boolean active = true;
    @JsonIgnore
    @Transient
    transient private String activeDesc;

    public Patient() {
	super();
    }
    public Patient(String pId) {
	super();
	this.pId = pId;
    }

    public String getActiveDesc() {
	if (active)
	    return "开";
	else
	    return "关";
    }

    public String getSexDesc() {
	if (sex.equalsIgnoreCase("1"))
	    return "男";
	else
	    return "女";
    }

    public String getFormatDateOfBirth() {
	return new SimpleDateFormat("yyyy年MM月dd日").format(dateOfBirth);
    }

    public String getActualAge() {
	Calendar cal = Calendar.getInstance();
	if (frontRecords != null && frontRecords.size() > 0)
	    cal.setTime(frontRecords.get(0).dateOfBirthday);
	else if (dateOfBirth != null)
	    cal.setTime(dateOfBirth);
	else
	    return "";
	return "" + (Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR));
    }


    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    public void addFront(FrontPage in) {
	if (frontRecords == null)
	    frontRecords = new LinkedList<FrontPage>();
	frontRecords.add(in);
    }
    public void addIn(InHospital in) {
	if (inRecords == null)
	    inRecords = new LinkedList<InHospital>();
	inRecords.add(in);
    }

    public void addOut(OutHospital in) {
	if (outRecords == null)
	    outRecords = new LinkedList<OutHospital>();
	outRecords.add(in);
    }

    public void addOperation(Operation in) {
	if (operations == null)
	    operations = new LinkedList<Operation>();
	operations.add(in);
    }

    public Integer getAge() {
	return age;
    }

    public String getSex() {
	return sex;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public String getCertNo() {
	return certNo;
    }

    public String getpId() {
	return pId;
    }

    public List<InHospital> getInRecords() {
	return inRecords;
    }

    public List<Operation> getOperations() {
	return operations;
    }

    public List<OutHospital> getOutRecords() {
	return outRecords;
    }

    public List<FrontPage> getFrontRecords() {
	return frontRecords;
    }

}
