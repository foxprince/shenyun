package cn.anthony.boot.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "patient")
public class Patient extends GenericNoSQLEntity {

    private static final long serialVersionUID = -9199964027188332358L;

    public String name;
    public Integer age;
    public Integer inTimes;
    public String sex;
    public Date dateOfBirth;
    public String certNo;/* 身份证号码 */
    public String marriage;
    public String pId;/* 病案号 */
    public String clinicNo;/* 门诊号 */
    public String country;
    public String birthPlace;
    public String nativePlace;// 籍贯
    public String nationality;// 民族
    public String homeAddress;
    public String phone;
    public String payType;
    public String healthCardNo;
    public String pathologicNo;
    public String occupation;
    public String provinve;
    public String city;
    public String street;
    public String company;
    public String businessPhone;
    public String contactName;
    public String contactRelation;
    public String contactAddress;
    public String contactPhone;

    public Date admissionTime;
    public String admissionDept;
    public String admissionWard;
    public Date dischargeTime;
    public String dischargeDept;
    public String dischargeWard;
    public Integer inDays;
    public Object diagDetail;

    public List<InHospital> inRecords;/* 入院纪录 */
    public List<Operation> operations;/* 手术纪录 */
    public List<OutHospital> outRecords;/* 出院纪录 */
    public String sourceFile;

    @Transient // 此注解用于不映射到数据库
    private boolean active = true;
    @JsonIgnore
    transient private String activeDesc;

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

    public Patient() {
	super();
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
}
