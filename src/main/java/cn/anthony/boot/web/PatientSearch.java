package cn.anthony.boot.web;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.anthony.boot.domain.FrontPage;
import cn.anthony.boot.domain.InHospital;
import cn.anthony.boot.domain.Operation;
import cn.anthony.boot.domain.OutHospital;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.SearchModel;
import cn.anthony.util.RefactorUtil;
import lombok.Data;

@Data
public class PatientSearch extends Patient {
    public static void main(String[] args) {
	SearchModel sm = new SearchModel();
	sm.addField("ZZHEN_DOCTOR_NAME", "王");
	System.out.println(sm.getKeyValueMap());
	PatientSearch ps = new PatientSearch();
	RefactorUtil.setObjectValue(ps, sm.getKeyValueMap());
	
	System.out.println(ps);
    }
    
    public PatientSearch() {
	frontPage = new FrontPage();
	inHospital = new InHospital();
	operation = new Operation();
	outHospital = new OutHospital();
    }
    Integer minAge,maxAge,size,page ;
    //String admissionDept,marriageStatus,mainDiag,KZR_DOCTOR_NAME,ZZ_DOCTOR_NAME,dischargeDept,ZY_DOCTOR_NAME,ZZHEN_DOCTOR_NAME,company;
    FrontPage frontPage;
    InHospital inHospital;
    Operation operation;
    OutHospital outHospital;
    boolean needSave;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inDateBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inDateEnd;
    
}
