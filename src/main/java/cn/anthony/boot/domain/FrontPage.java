package cn.anthony.boot.domain;

import cn.anthony.boot.util.Constant;
import cn.anthony.util.DateUtil;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@QueryEntity
public class FrontPage {
	public String getAdmissionTimeDesc() {
		return DateUtil.format(admissionTime, "yyyy年MM月dd日HH时");
	}

	public String getDischargeTimeDesc() {
		return DateUtil.format(dischargeTime, "yyyy年MM月dd日HH时");
	}

	public String getPatientclassDesc() {
		if (patientclass != null)
			return Constant.PAY_TYPE_MAP.get(patientclass);
		else
			return "";
	}

	public String getAdmissionwayDesc() {
		if (admissionway != null)
			return Constant.ENTRY_MAP.get(admissionway);
		else
			return "";
	}

	// 医疗机构
	public String medicalInstitution;
	// （组织机构代码
	public String organizationCode;
	// 病案号
	public String adminission_no;
	// 医疗付费方式
	public String patientclass;
	// 第几次住院
	public String numberOfTimes;
	// 门诊号
	public String outpatientnum;
	// 健康卡号
	public String healthCardNo;
	// 病理号
	public String pathological;
	// 姓名
	public String name;
	// 性别
	public String sex;
	// 出生日期 1943年04月21日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date dateOfBirthday;
	// 年龄
	public Integer age;
	// 国籍
	public String country;
	// 出生地
	public String birthplace;
	// 籍贯
	public String nativeplace;
	// 民族
	public String nationality;
	// 身份证号
	public String certNo;
	// 职业
	public String occupation;
	// 婚姻
	public String marriageStatus;
	// 现住址
	public String homeAddress;
	// 电话
	public String mobilephone;
	// 家庭邮编
	public String homePostcode;
	// 户口地址
	public String registeredaddress;
	// 户口邮编
	public String registeredemail;
	// 工作单位及地址
	public String company;
	// 单位电话
	public String businessphone;
	// 单位邮编
	public String businesspostcode;
	// 病人来源
	public String patientfrom;
	// 保健人员分类
	public String sponsor;
	// 保健人员在岗状态
	public String sponsorstatues;
	// 联系人姓名
	public String contactName;
	// 联系人关系
	public String relation;
	// 联系人地址
	public String contactaddress;
	// 联系人电话
	public String contactphone;
	// 入院途径 1.急诊 2.门诊 3.其他医疗机构转入 9.其他
	public String admissionway;
	//
	public String PresentProvince;
	//
	public String PresentProvinceCode;
	//
	public String PresentCity;
	//
	public String PresentCityCode;
	//
	public String PresentStreet;
	//
	public String ResidenceProvince;
	//
	public String ResidenceProvinceCode;
	//
	public String ResidenceCity;
	//
	public String ResidenceCityCode;
	//
	public String ResidenceStreet;
	// 入院时间 2015年02月13日18时
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date admissionTime;
	// 入院科别
	public String admissionDept;
	// 入院病房
	public String admissionWard;
	// 出院时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date dischargeTime;
	// 出院科别
	public String dischargeDept;
	// 出院病房
	public String dischargeWard;
	// 实际住院天数
	public Integer inhopitalday;
	// 转科科别
	public String changedept;
	// 门（急）诊诊断
	public String REGISTER_DIAGNOSIS;
	// 门（急）诊疾病编码
	public String REGISTER_CODE;
	// 病情
	public String B0C09B20353444959A675308E7E03FF5;
	// 出院诊断 其他诊断
	public List<Patient.OutDiag> outDiags;
	// 出院诊断 主要诊断 疾病
	public String mainDiag;
	// // 出院诊断 主要诊断 编码
	public String mainDiagCode;
	// // 出院诊断 入院病情：1.有，2.临床未确定，3.情况不明，4.无
	public String admissionCondition;
	// // 1
	// public String E0CE652177A5410183E5A601BF0D5479;
	// // 出院诊断 其他诊断 疾病
	// public String otherDiag1;
	// // 出院诊断 其他诊断 编码
	// public String otherDiagCode1;
	// // Q28.802
	// public String admissionCondition1;
	// 病情
	// public String 3A077F2D3157424595099E81A6B3A9F7;
	// 其他诊断
	// public String otherDiag11;
	//
	// public String 1F3B818798AF4387A6FFE357C269477C;
	//
	// public String 11E0B12464DE4628A2989A12A2A0732C;
	// 损伤、中毒的外部原因
	public String EXTERNAL_CAUESES;
	// 损伤、中毒疾病编码
	public String EXTERNAL_CODE;
	// 病理诊断
	public String PATHOLOGY_DIAGNOSIS;
	// 病理诊断疾病编码
	public String PATHOLOGY_CODE;
	// 药物过敏：1.无 2.有
	public String drugAllergy;
	// 过敏药物
	public String ALLERGIC_DRUG;
	// 死亡患者尸检
	public String deadAutopsy;
	// ABO血型：1.A 2.B 3.O 4.AB 5.不详 6.未查
	public String ABO;
	// Rh血型：1.阴 2.阳 3.不详 4.未查
	public String Rh;
	// 输血品种- 红细胞 单位
	public String redBloodCell;
	// 输血品种-血小板 袋
	public String platelet;
	// 输血品种-血浆 ml
	public String plasma;
	// 输血品种-全血 ml
	public String wholeBlood;
	// 输血品种-其它 ml
	public String other;
	// 科 主 任
	public String KZR_DOCTOR_NAME;
	// 主任（副主任）医师
	public String ZRFZR_DOCTOR_NAME;
	// 主治医师
	public String ZZ_DOCTOR_NAME;
	// 住院医师
	public String ZY_DOCTOR_NAME;
	// 主诊医师
	public String ZZHEN_DOCTOR_NAME;
	// 责任护士
	public String ZR_NURSE_NAME;
	// 进修医师
	public String JX_DOCTOR_NAME;
	// 实习医师
	public String SX_DOCTOR_NAME;
	// 编 码 员
	public String coder;
	// 病案质量:1.甲 2.乙 3.丙
	public String ZK_DJ1;
	// 质控医师
	public String ZK_DOCTOR_NAME;
	// 质控日期
	public String qualityControlDate;
	// 质控护士
	public String ZK_NURSE_NAME;
	// 填报医师签名
	public String imagSignature1;
	// 填报医师代码
	public String TB_DOC_CODE;
	// 填报医师名称
	public String TB_DOC_NAME;
	// 手术详情
	public List<Patient.OperationDetail> operationDetails;
	// 颅脑损伤患者昏迷时间：入院前-天-小时-分钟 入院后-天-小时-分钟
	// 入院前
	public Integer beforeday;
	// 天
	public Integer beforehours;
	// 小时
	public Integer beforeminutes;
	// 分钟 入院后
	public Integer afterday;
	// 天
	public Integer afterhours;
	// 小时
	public Integer afterminutes;
	// 重症监护室名称、代码 进重症监护室时间 出重症监护室时间
	// 出重症监护室时间
	public List<Patient.SevereDetail> severeDetails;
	// public String SEVERENAME1;
	// // -
	// public String SEVERECODE1;
	// // -
	// public String INSEVERETIME1;
	// // -
	// public String OUTSEVERETIME1;
	// // -
	// public String SEVERENAME2;
	// // -
	// public String SEVERECODE2;
	// // -
	// public String INSEVERETIME2;
	// // -
	// public String OUTSEVERETIME2;
	// // -
	// public String SEVERENAME3;
	// // -
	// public String SEVERECODE3;
	// // -
	// public String INSEVERETIME3;
	// // -
	// public String OUTSEVERETIME3;
	// // -
	// public String SEVERENAME4;
	// // -
	// public String SEVERECODE4;
	// // -
	// public String INSEVERETIME4;
	// // -
	// public String OUTSEVERETIME4;
	// // -
	// public String SEVERENAME5;
	// // -
	// public String SEVERECODE5;
	// // -
	// public String INSEVERETIME5;
	// // -
	// public String OUTSEVERETIME5;
	// 呼吸机使用时间：-小时
	public Integer respirator;
	// 肿瘤分期：TNMT
	// public String ZLFQ;
	// // T
	// public String T;
	// // N
	// public String N;
	// // M
	// public String M;
	// // ；
	// public String FQ;
	// // 0期 Ⅰ期 Ⅱ期 Ⅲ期 Ⅳ期；
	// public String BX;
	// 临床路径：进入路径：1.是 2.否
	public String incli;
	// 变异：1.有 2.无
	public String clivar;
	// 退出路径：1.是 2.否
	public String quitcli;
	// 日常生活能力评定量表(ADL)得分：入院
	public String inhosgrade;
	// 日常生活能力评定量表(ADL)得分：出院
	public String outhosgrade;
	// 新生儿出生体重-克
	public String birthWeight;
	// 新生儿入院体重-克
	public String admissionWeight;
	// 是否有出院31天内再住院计划：1.无 2.有
	public String hospitalizationPlanAgain;
	// 再住院目的
	public String purpose;
	// 离院方式：1.医嘱离院 2.医嘱转院 3.医嘱转社区卫生服务机构/乡镇卫生院
	public String outclass;
	// 医嘱转院，拟接收医疗机构名称
	public String outToHospital;
	// 医嘱转社区卫生服务机构/乡镇卫生院，拟接收医疗机构名称
	public String outToCommunity;
	public String srcFile;

	public FrontPage() {
	}
}
