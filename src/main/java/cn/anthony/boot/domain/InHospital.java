package cn.anthony.boot.domain;

import java.util.Date;

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
    public String somatoscopy;/* 体格检查 */
    public String firstDiag;
    public String confirmDiag;
    public String supplyDiag;
    public String correctDiag;
    public String sourceFile;
    class Diagnosis {
	public String type;//// 初步诊断,确定诊断,补充诊断,更正诊断
	public String conclusion;
	public String sign;
	public Date cdate;

    }

}
