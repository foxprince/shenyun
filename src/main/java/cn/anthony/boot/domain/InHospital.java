package cn.anthony.boot.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import cn.anthony.util.DateUtil;
import lombok.Data;

@QueryEntity
@Entity
@Data
public class InHospital extends GenericEntity {
    /*
     * 姓名：周惠英 科室：神经外科第1页 住院号：184470
     * 
     * 入 院 记 录 姓名：周惠英 入院日期：2015-02-26 14:02 性别：女 病史采集日期：2015-02-26 14:41 年龄：62岁
     * 婚姻状况：已婚 民族：汉族 病史叙述人：本人 出生地：北京市 可靠性：可靠 职业：其他 亲属姓名、电话：李殿祥13611352590
     */
    @ManyToOne(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;
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
    @OneToOne
    @MapsId // One-to-one association that assumes both the source and target
	    // share the same primary key values.
    public Somatoscopy somatoscopy;/* 体格检查 */
    // 辅助检查
    public String auxiliaryExamination;
    // public Diag firstDiag;
    // public Diag confirmDiag;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "inHospital")
    public List<Diag> diags;
    // public Diag correctDiag;
    public String sourceFile;

    public InHospital() {
	this.somatoscopy = new Somatoscopy();
	// this.firstDiag = new Diag("初步诊断");
	// this.confirmDiag = new Diag("确定诊断");
	// this.correctDiag = new Diag("更正诊断");
	this.diags = new ArrayList<Diag>();
    }

    public void addDiag(Diag d) {
	diags.add(d);
    }

    public String getInDateDesc() {
	return DateUtil.format(inDate, "yyyy年MM月dd日 HH时mm分");
    }

    public String getTakingDateDesc() {
	return DateUtil.format(takingDate, "yyyy年MM月dd日 HH时mm分");
    }

    public Somatoscopy getSomatoscopy() {
	return somatoscopy;
    }
}
