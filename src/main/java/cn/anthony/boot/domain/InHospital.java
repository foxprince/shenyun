package cn.anthony.boot.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InHospital {
    /*
     * 姓名：周惠英 科室：神经外科第1页 住院号：184470
     * 
     * 入 院 记 录 姓名：周惠英 入院日期：2015-02-26 14:02 性别：女 病史采集日期：2015-02-26 14:41 年龄：62岁
     * 婚姻状况：已婚 民族：汉族 病史叙述人：本人 出生地：北京市 可靠性：可靠 职业：其他 亲属姓名、电话：李殿祥13611352590
     */
    public String admissionDept;
    public String admissionNo;
    public Date inDate;
    public Date takingDate;/* 病史采集日期 */
    public String takingFrom;/* 病史叙述人 */
    public String reliability;/* 可靠性 */
    public String contact;/* 亲属姓名、电话：李殿祥13611352590 */
    public String selfDesc;/* 自述病史 */
    public String nowMedicalHistory;/* 现病史 */
    public String pastMedicalHistory;/* 既往史 */
    public String infectiousHistory;
    public String lifeHistory;/* 个人生活史 */
    public String familyHistory;/* 家族史 */
    public Somatoscopy somatoscopy;/* 体格检查 */
    // 辅助检查
    public String auxiliaryExamination;
    public Diag firstDiag;
    public Diag confirmDiag;
    public List<Diag> supplyDiags;
    public Diag correctDiag;

    public static class Diag {
	public Diag() {
	}
	public Diag(String s) {
	    this.type = s;
	}
	public String type;// 初步诊断 确定诊断 补充诊断 更正
	public String detail;
	public Date diagDate;
	public String signature;

	public String toString() {
	    return type + ":" + detail + "," + diagDate + " " + signature;
	}
    }
    public String sourceFile;

    public InHospital() {
	this.somatoscopy = new Somatoscopy();
	this.firstDiag = new Diag("初步诊断");
	this.confirmDiag = new Diag("确定诊断");
	this.correctDiag = new Diag("更正诊断");
	this.supplyDiags = new ArrayList<Diag>();
    }
    public String getAdmissionDept() {
	return admissionDept;
    }

    public String getAdmissionNo() {
	return admissionNo;
    }

    public Date getInDate() {
	return inDate;
    }

    public Date getTakingDate() {
	return takingDate;
    }

    public String getTakingFrom() {
	return takingFrom;
    }

    public String getReliability() {
	return reliability;
    }

    public String getContact() {
	return contact;
    }

    public String getSelfDesc() {
	return selfDesc;
    }

    public String getNowMedicalHistory() {
	return nowMedicalHistory;
    }

    public String getPastMedicalHistory() {
	return pastMedicalHistory;
    }

    public String getInfectiousHistory() {
	return infectiousHistory;
    }

    public String getLifeHistory() {
	return lifeHistory;
    }

    public String getFamilyHistory() {
	return familyHistory;
    }


    public String getSourceFile() {
	return sourceFile;
    }

    public Somatoscopy getSomatoscopy() {
	return somatoscopy;
    }

    public String getAuxiliaryExamination() {
	return auxiliaryExamination;
    }

    public Diag getFirstDiag() {
	return firstDiag;
    }

    public Diag getConfirmDiag() {
	return confirmDiag;
    }

    public List<Diag> getSupplyDiags() {
	return supplyDiags;
    }

    public Diag getCorrectDiag() {
	return correctDiag;
    }


}
