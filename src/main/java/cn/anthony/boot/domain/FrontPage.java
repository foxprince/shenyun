package cn.anthony.boot.domain;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import cn.anthony.util.DateUtil;

public class FrontPage {
    transient static Map<String, String> PAY_TYPE_MAP = new TreeMap<String, String>() {
	private static final long serialVersionUID = -4638190003036563391L;
	{
	    put("1", "城镇职工基本医疗保险");
	    put("2", "城镇居民基本医疗保险");
	    put("3", "新型农村合作医疗");
	    put("4", "贫困救助");
	    put("5", "商业医疗保险");
	    put("6", "全公费");
	    put("7", "全自费");
	    put("8", "其他社会保险");
	    put("9", "其他");
	}
    };
    transient static Map<String, String> ENTRY_MAP = new TreeMap<String, String>() {
	{
	    put("1", "急诊");
	    put("2", "门诊");
	    put("3", "其他医疗机构转入");
	    put("4", "其他");
	}
    };
    public String getAdmissionTimeDesc() {
	return DateUtil.format(admissionTime, "yyyy年MM月dd日HH时");
    }

    public String getDischargeTimeDesc() {
	return DateUtil.format(dischargeTime, "yyyy年MM月dd日HH时");
    }

    public String getPatientclassDesc() {
	return PAY_TYPE_MAP.get(patientclass);
    }

    public String getAdmissionwayDesc() {
	return ENTRY_MAP.get(admissionway);
    }
    // 医疗机构
    public String medicalInstitution;
    // （组织机构代码
    public String organizationCode;
    // 病案号
    public String adminission_no;
    // 医疗付费方式 1.城镇职工基本医疗保险 2.城镇居民基本医疗保险 3.新型农村合作医疗 4.贫困救助 5.商业医疗保险 6.全公费 7.全自费
    // 8.其他社会保险 9.其他
    public String patientclass;
    // 第
    public String numberOfTimes;
    // 门诊号
    public String outpatientnum;
    // 健康卡号
    public String healthCardNo;
    // 病理号
    public String pathological;
    // 姓名
    public String name;
    //
    public String sex;
    // 出生日期 1943年04月21日
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
    // 邮编
    public String homePostcode;
    // 户口地址
    public String registeredaddress;
    // 邮编
    public String registeredemail;
    // 工作单位及地址
    public String company;
    // 单位电话
    public String businessphone;
    // 邮编
    public String businesspostcode;
    // 病人来源
    public String patientfrom;
    // 保健人员分类
    public String sponsor;
    // 保健人员在岗状态
    public String sponsorstatues;
    // 联系人姓名
    public String contactName;
    // 联系人姓名关系
    public String relation;
    // 联系人姓名地址
    public String contactaddress;
    // 联系人姓名电话
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
    public Date admissionTime;
    // 入院科别
    public String admissionDept;
    // 病房
    public String admissionWard;
    // 出院时间
    public Date dischargeTime;
    // 出院科别
    public String dischargeDept;
    // 病房
    public String dischargeWard;
    // 实际住院天数
    public Integer inhopitalday;
    // 转科科别
    public String changedept;
    // 门（急）诊诊断
    public String REGISTER_DIAGNOSIS;
    // 疾病编码
    public String REGISTER_CODE;
    // 病情
    public String B0C09B20353444959A675308E7E03FF5;
    // 出院诊断 主要诊断 疾病
    public String mainDiag;
    // 主要诊断 编码
    public String mainDiagCode;
    // 入院病情：1.有，2.临床未确定，3.情况不明，4.无
    public String admissionCondition;
    // 1
    public String E0CE652177A5410183E5A601BF0D5479;
    // 其他诊断 疾病
    public String otherDiag1;
    // 其他诊断 编码
    public String otherDiagCode1;
    // Q28.802
    public String admissionCondition1;
    // 病情
    // public String 3A077F2D3157424595099E81A6B3A9F7;
    // 其他诊断
    public String otherDiag11;
    //
    // public String 1F3B818798AF4387A6FFE357C269477C;
    //
    // public String 11E0B12464DE4628A2989A12A2A0732C;
    // 损伤、中毒的外部原因
    public String EXTERNAL_CAUESES;
    // 疾病编码
    public String EXTERNAL_CODE;
    // 病理诊断
    public String PATHOLOGY_DIAGNOSIS;
    // 疾病编码
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
    // 输血品种- 红细胞
    public String redBloodCell;
    // 输血品种-血小板
    public String platelet;
    // 输血品种-血浆
    public String plasma;
    // 输血品种-全血
    public String wholeBlood;
    // 输血品种-其它
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
    public OperationDetail oDetail;
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
    public String SEVERENAME1;
    // -
    public String SEVERECODE1;
    // -
    public String INSEVERETIME1;
    // -
    public String OUTSEVERETIME1;
    // -
    public String SEVERENAME2;
    // -
    public String SEVERECODE2;
    // -
    public String INSEVERETIME2;
    // -
    public String OUTSEVERETIME2;
    // -
    public String SEVERENAME3;
    // -
    public String SEVERECODE3;
    // -
    public String INSEVERETIME3;
    // -
    public String OUTSEVERETIME3;
    // -
    public String SEVERENAME4;
    // -
    public String SEVERECODE4;
    // -
    public String INSEVERETIME4;
    // -
    public String OUTSEVERETIME4;
    // -
    public String SEVERENAME5;
    // -
    public String SEVERECODE5;
    // -
    public String INSEVERETIME5;
    // -
    public String OUTSEVERETIME5;
    // 呼吸机使用时间：-小时
    public Integer respirator;
    // 肿瘤分期：TNMT
    public String ZLFQ;
    // T
    public String T;
    // N
    public String N;
    // M
    public String M;
    // ；
    public String FQ;
    // 0期 Ⅰ期 Ⅱ期 Ⅲ期 Ⅳ期；
    public String BX;
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
    public String sourceFile;

    public class OperationDetail {
	public String src;

	public String getSrc() {
	    return src;
	}
    }
    public String getSourceFile() {
	return sourceFile;
    }

    public FrontPage() {
	this.oDetail = new OperationDetail();
    }

    public String getMedicalInstitution() {
	return medicalInstitution;
    }

    public String getOrganizationCode() {
	return organizationCode;
    }

    public String getAdminission_no() {
	return adminission_no;
    }

    public String getPatientclass() {
	return patientclass;
    }

    public String getNumberOfTimes() {
	return numberOfTimes;
    }

    public String getOutpatientnum() {
	return outpatientnum;
    }

    public String getHealthCardNo() {
	return healthCardNo;
    }

    public String getPathological() {
	return pathological;
    }

    public String getName() {
	return name;
    }

    public String getSex() {
	return sex;
    }

    public Date getDateOfBirthday() {
	return dateOfBirthday;
    }

    public Integer getAge() {
	return age;
    }

    public String getCountry() {
	return country;
    }

    public String getBirthplace() {
	return birthplace;
    }

    public String getNativeplace() {
	return nativeplace;
    }

    public String getNationality() {
	return nationality;
    }

    public String getCertNo() {
	return certNo;
    }

    public String getOccupation() {
	return occupation;
    }

    public String getMarriageStatus() {
	return marriageStatus;
    }

    public String getHomeAddress() {
	return homeAddress;
    }

    public String getMobilephone() {
	return mobilephone;
    }

    public String getHomePostcode() {
	return homePostcode;
    }

    public String getRegisteredaddress() {
	return registeredaddress;
    }

    public String getRegisteredemail() {
	return registeredemail;
    }

    public String getCompany() {
	return company;
    }

    public String getBusinessphone() {
	return businessphone;
    }

    public String getBusinesspostcode() {
	return businesspostcode;
    }

    public String getPatientfrom() {
	return patientfrom;
    }

    public String getSponsor() {
	return sponsor;
    }

    public String getSponsorstatues() {
	return sponsorstatues;
    }

    public String getContactName() {
	return contactName;
    }

    public String getRelation() {
	return relation;
    }

    public String getContactaddress() {
	return contactaddress;
    }

    public String getContactphone() {
	return contactphone;
    }

    public String getAdmissionway() {
	return admissionway;
    }

    public String getPresentProvince() {
	return PresentProvince;
    }

    public String getPresentProvinceCode() {
	return PresentProvinceCode;
    }

    public String getPresentCity() {
	return PresentCity;
    }

    public String getPresentCityCode() {
	return PresentCityCode;
    }

    public String getPresentStreet() {
	return PresentStreet;
    }

    public String getResidenceProvince() {
	return ResidenceProvince;
    }

    public String getResidenceProvinceCode() {
	return ResidenceProvinceCode;
    }

    public String getResidenceCity() {
	return ResidenceCity;
    }

    public String getResidenceCityCode() {
	return ResidenceCityCode;
    }

    public String getResidenceStreet() {
	return ResidenceStreet;
    }

    public Date getAdmissionTime() {
	return admissionTime;
    }

    public String getAdmissionDept() {
	return admissionDept;
    }

    public String getAdmissionWard() {
	return admissionWard;
    }

    public Date getDischargeTime() {
	return dischargeTime;
    }

    public String getDischargeDept() {
	return dischargeDept;
    }

    public String getDischargeWard() {
	return dischargeWard;
    }

    public Integer getInhopitalday() {
	return inhopitalday;
    }

    public String getChangedept() {
	return changedept;
    }

    public String getREGISTER_DIAGNOSIS() {
	return REGISTER_DIAGNOSIS;
    }

    public String getREGISTER_CODE() {
	return REGISTER_CODE;
    }

    public String getB0C09B20353444959A675308E7E03FF5() {
	return B0C09B20353444959A675308E7E03FF5;
    }

    public String getMainDiag() {
	return mainDiag;
    }

    public String getMainDiagCode() {
	return mainDiagCode;
    }

    public String getAdmissionCondition() {
	return admissionCondition;
    }

    public String getE0CE652177A5410183E5A601BF0D5479() {
	return E0CE652177A5410183E5A601BF0D5479;
    }

    public String getOtherDiag1() {
	return otherDiag1;
    }

    public String getOtherDiagCode1() {
	return otherDiagCode1;
    }

    public String getAdmissionCondition1() {
	return admissionCondition1;
    }

    public String getOtherDiag11() {
	return otherDiag11;
    }

    public String getEXTERNAL_CAUESES() {
	return EXTERNAL_CAUESES;
    }

    public String getEXTERNAL_CODE() {
	return EXTERNAL_CODE;
    }

    public String getPATHOLOGY_DIAGNOSIS() {
	return PATHOLOGY_DIAGNOSIS;
    }

    public String getPATHOLOGY_CODE() {
	return PATHOLOGY_CODE;
    }

    public String getDrugAllergy() {
	return drugAllergy;
    }

    public String getALLERGIC_DRUG() {
	return ALLERGIC_DRUG;
    }

    public String getDeadAutopsy() {
	return deadAutopsy;
    }

    public String getABO() {
	return ABO;
    }

    public String getRh() {
	return Rh;
    }

    public String getRedBloodCell() {
	return redBloodCell;
    }

    public String getPlatelet() {
	return platelet;
    }

    public String getPlasma() {
	return plasma;
    }

    public String getWholeBlood() {
	return wholeBlood;
    }

    public String getOther() {
	return other;
    }

    public String getKZR_DOCTOR_NAME() {
	return KZR_DOCTOR_NAME;
    }

    public String getZRFZR_DOCTOR_NAME() {
	return ZRFZR_DOCTOR_NAME;
    }

    public String getZZ_DOCTOR_NAME() {
	return ZZ_DOCTOR_NAME;
    }

    public String getZY_DOCTOR_NAME() {
	return ZY_DOCTOR_NAME;
    }

    public String getZZHEN_DOCTOR_NAME() {
	return ZZHEN_DOCTOR_NAME;
    }

    public String getZR_NURSE_NAME() {
	return ZR_NURSE_NAME;
    }

    public String getJX_DOCTOR_NAME() {
	return JX_DOCTOR_NAME;
    }

    public String getSX_DOCTOR_NAME() {
	return SX_DOCTOR_NAME;
    }

    public String getCoder() {
	return coder;
    }

    public String getZK_DJ1() {
	return ZK_DJ1;
    }

    public String getZK_DOCTOR_NAME() {
	return ZK_DOCTOR_NAME;
    }

    public String getQualityControlDate() {
	return qualityControlDate;
    }

    public String getZK_NURSE_NAME() {
	return ZK_NURSE_NAME;
    }

    public String getImagSignature1() {
	return imagSignature1;
    }

    public String getTB_DOC_CODE() {
	return TB_DOC_CODE;
    }

    public String getTB_DOC_NAME() {
	return TB_DOC_NAME;
    }

    public OperationDetail getoDetail() {
	return oDetail;
    }

    public Integer getBeforeday() {
	return beforeday;
    }

    public Integer getBeforehours() {
	return beforehours;
    }

    public Integer getBeforeminutes() {
	return beforeminutes;
    }

    public Integer getAfterday() {
	return afterday;
    }

    public Integer getAfterhours() {
	return afterhours;
    }

    public Integer getAfterminutes() {
	return afterminutes;
    }

    public String getSEVERENAME1() {
	return SEVERENAME1;
    }

    public String getSEVERECODE1() {
	return SEVERECODE1;
    }

    public String getINSEVERETIME1() {
	return INSEVERETIME1;
    }

    public String getOUTSEVERETIME1() {
	return OUTSEVERETIME1;
    }

    public String getSEVERENAME2() {
	return SEVERENAME2;
    }

    public String getSEVERECODE2() {
	return SEVERECODE2;
    }

    public String getINSEVERETIME2() {
	return INSEVERETIME2;
    }

    public String getOUTSEVERETIME2() {
	return OUTSEVERETIME2;
    }

    public String getSEVERENAME3() {
	return SEVERENAME3;
    }

    public String getSEVERECODE3() {
	return SEVERECODE3;
    }

    public String getINSEVERETIME3() {
	return INSEVERETIME3;
    }

    public String getOUTSEVERETIME3() {
	return OUTSEVERETIME3;
    }

    public String getSEVERENAME4() {
	return SEVERENAME4;
    }

    public String getSEVERECODE4() {
	return SEVERECODE4;
    }

    public String getINSEVERETIME4() {
	return INSEVERETIME4;
    }

    public String getOUTSEVERETIME4() {
	return OUTSEVERETIME4;
    }

    public String getSEVERENAME5() {
	return SEVERENAME5;
    }

    public String getSEVERECODE5() {
	return SEVERECODE5;
    }

    public String getINSEVERETIME5() {
	return INSEVERETIME5;
    }

    public String getOUTSEVERETIME5() {
	return OUTSEVERETIME5;
    }

    public Integer getRespirator() {
	return respirator;
    }

    public String getZLFQ() {
	return ZLFQ;
    }

    public String getT() {
	return T;
    }

    public String getN() {
	return N;
    }

    public String getM() {
	return M;
    }

    public String getFQ() {
	return FQ;
    }

    public String getBX() {
	return BX;
    }

    public String getIncli() {
	return incli;
    }

    public String getClivar() {
	return clivar;
    }

    public String getQuitcli() {
	return quitcli;
    }

    public String getInhosgrade() {
	return inhosgrade;
    }

    public String getOuthosgrade() {
	return outhosgrade;
    }

    public String getBirthWeight() {
	return birthWeight;
    }

    public String getAdmissionWeight() {
	return admissionWeight;
    }

    public String getHospitalizationPlanAgain() {
	return hospitalizationPlanAgain;
    }

    public String getPurpose() {
	return purpose;
    }

    public String getOutclass() {
	return outclass;
    }

    public String getOutToHospital() {
	return outToHospital;
    }

    public String getOutToCommunity() {
	return outToCommunity;
    }
}
