package cn.anthony.boot.web;

import cn.anthony.boot.domain.*;
import cn.anthony.util.RefactorUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PatientSearch extends Patient {
	public static void main(String[] args) {
		SearchModel sm = new SearchModel();
		sm.addField("ZZHEN_DOCTOR_NAME", "王", "and", "eq");
		System.out.println(sm.getKeyValueMap());
		PatientSearch ps = new PatientSearch();
		// ps.age
		RefactorUtil.setObjectValue(ps, sm.getKeyValueMap());
		System.out.println(ps);
	}

	public PatientSearch() {
		frontPage = new FrontPage();
		inHospital = new InHospital();
		operation = new Operation();
		outHospital = new OutHospital();
	}

	Integer size, page;
	FrontPage frontPage;
	InHospital inHospital;
	Operation operation;
	OutHospital outHospital;
	boolean needSave;
	// 保留以下字段是为了让标准搜索的再搜索功能依然可用
	Integer minAge, maxAge;
	String admissionDept, marriageStatus, mainDiag, KZR_DOCTOR_NAME, ZZ_DOCTOR_NAME, dischargeDept, ZY_DOCTOR_NAME, ZZHEN_DOCTOR_NAME,
			company;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date inDateBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date inDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date outDateBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date outDateEnd;
}
