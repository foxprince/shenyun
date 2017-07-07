package cn.anthony.boot.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import cn.anthony.util.StringTools;
import lombok.Data;

@Component
@Data
public class Constant {
	public static String PACS_DIR = ResourceBundle.getBundle("application").getString("PACS_DIR");// =

	@Data
	public static class CheckOption {
		String inputType = "text", name, label, value;

		public CheckOption(String type, String name, String label) {
			super();
			this.inputType = type;
			this.name = name;
			this.label = label;
		}

		public CheckOption(String inputType, String name, String label, String value) {
			super();
			this.inputType = inputType;
			this.name = name;
			this.label = label;
			this.value = value;
		}
	}

	public static void main(String[] args) {
		System.out.println(getKeyDesc("sex"));
	}

	public static String getKeyDesc(String key) {
		String find = key.substring(key.indexOf(".") >= 0 ? key.indexOf(".") + 1 : 0);
		if (totalKeyMap.containsKey("frontRecords." + find))
			return totalKeyMap.get("frontRecords." + find).getLabel();
		if (patientKeyMap.containsKey("patient." + find))
			return patientKeyMap.get("patient." + find).getLabel();
		if (frontPageKeyMap.containsKey("frontPage." + find))
			return frontPageKeyMap.get("frontPage." + find).getLabel();
		if (inKeyMap.containsKey("inHospital." + find))
			return inKeyMap.get("inHospital." + find).getLabel();
		if (operKeyMap.containsKey("operation." + find))
			return operKeyMap.get("operation." + find).getLabel();
		if (outKeyMap.containsKey("outHospital." + find))
			return outKeyMap.get("outHospital." + find).getLabel();
		return null;
	}

	public static String[] toNames(String[] keys) {
		String[] names = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			names[i] = keys[i];
			if (patientKeyMap.containsKey(keys[i]))
				names[i] = patientKeyMap.get(keys[i]).getLabel();
			if (frontPageKeyMap.containsKey(keys[i]))
				names[i] = frontPageKeyMap.get(keys[i]).getLabel();
			if (inKeyMap.containsKey(keys[i]))
				names[i] = inKeyMap.get(keys[i]).getLabel();
			if (operKeyMap.containsKey(keys[i]))
				names[i] = operKeyMap.get(keys[i]).getLabel();
			if (outKeyMap.containsKey(keys[i]))
				names[i] = outKeyMap.get(keys[i]).getLabel();
		}
		return names;
	}

	public static final String[] pieColors = new String[] { "#00a65a", "#f56954", "#f39c12", "#f39c12", "#d2d6de", "#32d6de", "#d223de",
			"#d2d645", "#57d6de" };
	public static final String[] pieHighlights = new String[] { "#00a65b", "#f56955", "#f39c14", "#f39c15", "#d2d6ee", "#32d7de",
			"#d224de", "#d2d675", "#57d7de" };
	private static final String DEFAULT_TYPE = "text";
	private static final String DATETIME_TYPE = "datetime-local";
	private static final String DATE_TYPE = "date";
	// public static final String PACS_DIR = "E:\\project\\神云系统\\pacs\\";//
	// "E:\\xampp\\htdocs\\auth\\Public\\Admin\\img\\dicom\\";
	public static Map<String, CheckOption> totalKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(totalKeyMap, "frontRecords.age", "年龄");
		defaultPut(totalKeyMap, "frontRecords.sex", "性别，1：男，2：女");
		defaultPut(totalKeyMap, "frontRecords.name", "姓名");
		defaultPut(totalKeyMap, "frontRecords.nativeplace", "籍贯");
		defaultPut(totalKeyMap, "frontRecords.nationality", "民族");
		defaultPut(totalKeyMap, "frontRecords.country", "国籍");
		defaultPut(totalKeyMap, "frontRecords.birthplace", "出生地");
		// defaultPut(totalKeyMap, "frontRecords.registeredaddress", "户口地址");
		// defaultPut(totalKeyMap, "frontRecords.homeAddress", "现住址");
		defaultPut(totalKeyMap, "frontRecords.admissionDept", "入院科别");
		defaultPut(totalKeyMap, "frontRecords.admissionWard", "入院病房");
		defaultPut(totalKeyMap, "frontRecords.dischargeDept", "出院科别");
		defaultPut(totalKeyMap, "frontRecords.dischargeWard", "出院病房");
		defaultPut(totalKeyMap, "frontRecords.inhopitalday", "实际住院天数");
		defaultPut(totalKeyMap, "frontRecords.changedept", "转科科别");
		defaultPut(totalKeyMap, "frontRecords.REGISTER_DIAGNOSIS", "门（急）诊诊断　");
		// defaultPut(totalKeyMap, "frontRecords.REGISTER_CODE", "门（急）诊诊断疾病编码");
		defaultPut(totalKeyMap, "frontRecords.mainDiag", "出院主要诊断");
		defaultPut(totalKeyMap, "frontRecords.ZRFZR_DOCTOR_NAME", "主任（副主任）医师");
		defaultPut(totalKeyMap, "frontRecords.ZZ_DOCTOR_NAME", "主治医师");
		defaultPut(totalKeyMap, "frontRecords.ZY_DOCTOR_NAME", "住院医师");
		defaultPut(totalKeyMap, "frontRecords.ZZHEN_DOCTOR_NAME", "主诊医师");
		defaultPut(totalKeyMap, "frontRecords.ZR_NURSE_NAME", "责任护士");
		// defaultPut(totalKeyMap, "frontRecords.JX_DOCTOR_NAME", "进修医师");
		// defaultPut(totalKeyMap, "frontRecords.SX_DOCTOR_NAME", "实习医师");
		defaultPut(totalKeyMap, "frontRecords.operationDetails.title", "手术、操作及大型设备检查名称");
		defaultPut(totalKeyMap, "frontRecords.operationDetails.chief", "手术操作医师");
	}
	public static Map<String, CheckOption> patientKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(patientKeyMap, "patient.age", "年龄");
		defaultPut(patientKeyMap, "patient.sex", "性别");
		defaultPut(patientKeyMap, DATE_TYPE, "patient.dateOfBirth", "出生日期");
		defaultPut(patientKeyMap, "patient.name", "姓名");
		defaultPut(patientKeyMap, "patient.certNo", "身份证号码");
		defaultPut(patientKeyMap, "patient.nativeplace", "籍贯");
		defaultPut(patientKeyMap, "patient.nationality", "民族");
		defaultPut(patientKeyMap, "patient.registeredaddress", "户口地址");
		defaultPut(patientKeyMap, "patient.country", "国籍");
		defaultPut(patientKeyMap, "patient.birthplace", "出生地");
	}
	public static Map<String, CheckOption> frontPageKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(frontPageKeyMap, "frontPage.medicalInstitution", "医疗机构");
		defaultPut(frontPageKeyMap, "frontPage.organizationCode", "组织机构代码");
		defaultPut(frontPageKeyMap, "frontPage.adminission_no", "病案号");
		defaultPut(frontPageKeyMap, "frontPage.patientclass", "医疗付费方式");
		defaultPut(frontPageKeyMap, "frontPage.numberOfTimes", "第几次住院");
		defaultPut(frontPageKeyMap, "frontPage.outpatientnum", "门诊号");
		defaultPut(frontPageKeyMap, "frontPage.healthCardNo", "健康卡号");
		defaultPut(frontPageKeyMap, "frontPage.pathological", " 病理号");
		defaultPut(frontPageKeyMap, "frontPage.occupation", " 职业");
		defaultPut(frontPageKeyMap, "frontPage.marriageStatus", "婚姻 ");
		defaultPut(frontPageKeyMap, "frontPage.homeAddress", "现住址");
		defaultPut(frontPageKeyMap, "frontPage.mobilephone", "现住址电话");
		defaultPut(frontPageKeyMap, "frontPage.homePostcode", "现住址邮编");
		defaultPut(frontPageKeyMap, "frontPage.registeredemail", "户口地址邮编");
		defaultPut(frontPageKeyMap, "frontPage.company", "工作单位及地址");
		defaultPut(frontPageKeyMap, "frontPage.businessphone", "单位电话");
		defaultPut(frontPageKeyMap, "frontPage.businesspostcode", "单位邮编");
		defaultPut(frontPageKeyMap, "frontPage.patientfrom", "病人来源");
		defaultPut(frontPageKeyMap, "frontPage.sponsor", "保健人员分类");
		defaultPut(frontPageKeyMap, "frontPage.sponsorstatues", "保健人员在岗状态");
		defaultPut(frontPageKeyMap, "frontPage.contactName", "联系人姓名");
		defaultPut(frontPageKeyMap, "frontPage.relation", "联系人关系");
		defaultPut(frontPageKeyMap, "frontPage.contactaddress", "联系人地址");
		defaultPut(frontPageKeyMap, "frontPage.contactphone", "联系人电话");
		defaultPut(frontPageKeyMap, "frontPage.admissionway", "入院途径");
		defaultPut(frontPageKeyMap, DATE_TYPE, "frontPage.admissionTime", "入院时间");
		defaultPut(frontPageKeyMap, "frontPage.admissionDept", "入院科别");
		defaultPut(frontPageKeyMap, "frontPage.admissionWard", "病房");
		defaultPut(frontPageKeyMap, DATE_TYPE, "frontPage.dischargeTime", "出院时间");
		defaultPut(frontPageKeyMap, "frontPage.dischargeDept", "出院科别");
		defaultPut(frontPageKeyMap, "frontPage.dischargeWard", "病房");
		defaultPut(frontPageKeyMap, "frontPage.inhopitalday", "实际住院天数");
		defaultPut(frontPageKeyMap, "frontPage.changedept", "转科科别");
		defaultPut(frontPageKeyMap, "frontPage.REGISTER_DIAGNOSIS", "门（急）诊诊断　");
		defaultPut(frontPageKeyMap, "frontPage.REGISTER_CODE", "门（急）诊诊断疾病编码");
		defaultPut(frontPageKeyMap, "frontPage.mainDiag", "出院主要诊断");
		defaultPut(frontPageKeyMap, "frontPage.mainDiagCode", "出院主要诊断编码");
		defaultPut(frontPageKeyMap, "frontPage.outDiag.diag", "出院其他诊断");
		defaultPut(frontPageKeyMap, "frontPage.outDiag.code", "出院其他诊断编码");
		defaultPut(frontPageKeyMap, "frontPage.EXTERNAL_CAUESES", "损伤、中毒的外部原因");
		defaultPut(frontPageKeyMap, "frontPage.EXTERNAL_CODE", "疾病编码");
		defaultPut(frontPageKeyMap, "frontPage.PATHOLOGY_DIAGNOSIS", "病理诊断");
		defaultPut(frontPageKeyMap, "frontPage.PATHOLOGY_CODE", "病理诊断疾病编码");
		defaultPut(frontPageKeyMap, "frontPage.drugAllergy", "是否药物过敏(1.无 2.有)");
		defaultPut(frontPageKeyMap, "frontPage.ALLERGIC_DRUG", "过敏药物");
		defaultPut(frontPageKeyMap, "frontPage.deadAutopsy", "死亡患者尸检");
		defaultPut(frontPageKeyMap, "frontPage.ABO", "输血：ABO血型");
		defaultPut(frontPageKeyMap, "frontPage.Rh", "输血：Rh血型");
		defaultPut(frontPageKeyMap, "frontPage.redBloodCell", "输血：红细胞");
		defaultPut(frontPageKeyMap, "frontPage.platelet", "输血：血小板");
		defaultPut(frontPageKeyMap, "frontPage.plasma", "输血：血浆");
		defaultPut(frontPageKeyMap, "frontPage.wholeBlood", "输血：全血");
		defaultPut(frontPageKeyMap, "frontPage.other", "输血：其它");
		defaultPut(frontPageKeyMap, "frontPage.KZR_DOCTOR_NAME", "科 主 任");
		defaultPut(frontPageKeyMap, "frontPage.ZRFZR_DOCTOR_NAME", "主任（副主任）医师");
		defaultPut(frontPageKeyMap, "frontPage.ZZ_DOCTOR_NAME", "主治医师");
		defaultPut(frontPageKeyMap, "frontPage.ZY_DOCTOR_NAME", "住院医师");
		defaultPut(frontPageKeyMap, "frontPage.ZZHEN_DOCTOR_NAME", "主诊医师");
		defaultPut(frontPageKeyMap, "frontPage.ZR_NURSE_NAME", "责任护士");
		defaultPut(frontPageKeyMap, "frontPage.JX_DOCTOR_NAME", "进修医师");
		defaultPut(frontPageKeyMap, "frontPage.SX_DOCTOR_NAME", "实习医师");
		defaultPut(frontPageKeyMap, "frontPage.coder", "编 码 员");
		defaultPut(frontPageKeyMap, "frontPage.ZK_DJ1", "病案质量(1.甲 2.乙 3.丙)");
		defaultPut(frontPageKeyMap, "frontPage.ZK_DOCTOR_NAME", "质控医师");
		defaultPut(frontPageKeyMap, "frontPage.qualityControlDate", "质控日期");
		defaultPut(frontPageKeyMap, "frontPage.ZK_NURSE_NAME", "质控护士");
		defaultPut(frontPageKeyMap, "frontPage.TB_DOC_CODE", "填报医师代码");
		defaultPut(frontPageKeyMap, "frontPage.TB_DOC_NAME", "填报医师");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.code", "手术、操作及大型设备检查编码");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.checkDate", "手术、操作及大型设备检查日期");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.title", "手术、操作及大型设备检查名称");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.chief", "手术操作医师");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.assistant1", "手术操作I助");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.assistant2", "手术操作II助");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.oclass", "手术级别");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.nnis", "NNIS评分");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.qkyh", "切口愈合等级");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.asa", "ASA分级");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.mzfs", "麻醉方式");
		defaultPut(frontPageKeyMap, "frontPage.operationDetail.mzDoc", "麻醉医师");
		defaultPut(frontPageKeyMap, "frontPage.beforeday", "颅脑损伤患者昏迷时间：入院前-天");
		defaultPut(frontPageKeyMap, "frontPage.beforehours", "小时");
		defaultPut(frontPageKeyMap, "frontPage.beforeminutes", "分钟");
		defaultPut(frontPageKeyMap, "frontPage.afterday", "颅脑损伤患者昏迷时间：入院后-天");
		defaultPut(frontPageKeyMap, "frontPage.afterhours", "小时");
		defaultPut(frontPageKeyMap, "frontPage.afterminutes", "分钟");
		defaultPut(frontPageKeyMap, "frontPage.severeDetail.name", "重症监护室名称");
		defaultPut(frontPageKeyMap, DATETIME_TYPE, "frontPage.severeDetail.inTime", "进重症监护室时间");
		defaultPut(frontPageKeyMap, DATETIME_TYPE, "frontPage.severeDetail.outTime", "出重症监护室时间");
		defaultPut(frontPageKeyMap, "frontPage.severeDetail.minutes", "重症监护室总时间（分钟）");
		defaultPut(frontPageKeyMap, "frontPage.respirator", "呼吸机使用时间(小时)");
		defaultPut(frontPageKeyMap, "frontPage.incli", "临床进入路径：1.是 2.否");
		defaultPut(frontPageKeyMap, "frontPage.clivar", "临床变异：1.有 2.无");
		defaultPut(frontPageKeyMap, "frontPage.quitcli", "临床退出路径：1.是 2.否");
		defaultPut(frontPageKeyMap, "frontPage.inhosgrade", "日常生活能力评定量表(ADL)得分-入院");
		defaultPut(frontPageKeyMap, "frontPage.outhosgrade", "日常生活能力评定量表(ADL)得分-出院");
		defaultPut(frontPageKeyMap, "frontPage.birthWeight", "新生儿出生体重");
		defaultPut(frontPageKeyMap, "frontPage.admissionWeight", "新生儿入院体重");
		defaultPut(frontPageKeyMap, "frontPage.hospitalizationPlanAgain", "是否有出院31天内再住院计划：1.无 2.有");
		defaultPut(frontPageKeyMap, "frontPage.purpose", " 再住院目的");
		defaultPut(frontPageKeyMap, "frontPage.outclass", "离院方式：1.医嘱离院 2.医嘱转院 3.医嘱转社区卫生服务机构/乡镇卫生院");
		defaultPut(frontPageKeyMap, "frontPage.outToHospital", "医嘱转院，拟接收医疗机构名称");
		defaultPut(frontPageKeyMap, "frontPage.outToCommunity", "医嘱转社区卫生服务机构/乡镇卫生院，拟接收医疗机构名称");
	};
	public static Map<String, CheckOption> inKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(inKeyMap, "inHospital.takingFrom", "病史叙述人");
		defaultPut(inKeyMap, "inHospital.reliability", "可靠性");
		defaultPut(inKeyMap, "inHospital.contact", "亲属姓名、电话");
		defaultPut(inKeyMap, "inHospital.selfDesc", "自述病史");
		defaultPut(inKeyMap, "inHospital.nowMedicalHistory", "现病史");
		defaultPut(inKeyMap, "inHospital.pastMedicalHistory", "既往史");
		defaultPut(inKeyMap, "inHospital.infectiousHistory", "传染史");
		defaultPut(inKeyMap, "inHospital.lifeHistory", "个人生活史");
		defaultPut(inKeyMap, "inHospital.familyHistory", "家族史");
		defaultPut(inKeyMap, "inHospital.general", "一般情况");
		defaultPut(inKeyMap, "inHospital.T", "体温");
		defaultPut(inKeyMap, "inHospital.P", "心率");
		defaultPut(inKeyMap, "inHospital.R", "R");
		defaultPut(inKeyMap, "inHospital.BP", "血压");
		defaultPut(inKeyMap, "inHospital.skin", "皮肤粘膜");
		defaultPut(inKeyMap, "inHospital.superficialLymph", "浅表淋巴结");
		defaultPut(inKeyMap, "inHospital.skull", "头颅");
		defaultPut(inKeyMap, "inHospital.eye", "眼");
		defaultPut(inKeyMap, "inHospital.ear", "耳");
		defaultPut(inKeyMap, "inHospital.node", "鼻");
		defaultPut(inKeyMap, "inHospital.mouse", "口腔");
		defaultPut(inKeyMap, "inHospital.throat", "咽");
		defaultPut(inKeyMap, "inHospital.neck", "颈    部");
		defaultPut(inKeyMap, "inHospital.thorax", "胸廓");
		defaultPut(inKeyMap, "inHospital.lung", "肺");
		defaultPut(inKeyMap, "inHospital.heart", "心");
		defaultPut(inKeyMap, "inHospital.bloodVessels", "周围血管征");
		defaultPut(inKeyMap, "inHospital.abdomen", "腹    部");
		defaultPut(inKeyMap, "inHospital.liver", "肝（胆）");
		defaultPut(inKeyMap, "inHospital.spleen", "脾");
		defaultPut(inKeyMap, "inHospital.kidney", "肾");
		defaultPut(inKeyMap, "inHospital.vulva", "外阴及肛门");
		defaultPut(inKeyMap, "inHospital.spine", "脊    柱");
		defaultPut(inKeyMap, "inHospital.limbs", "四肢（关节）");
		defaultPut(inKeyMap, "inHospital.nervousSystem", "神经系统");
		defaultPut(inKeyMap, "inHospital.firstDiag.detail", "初步诊断");
		defaultPut(inKeyMap, "inHospital.confirmDiag.detail", "确定诊断");
		defaultPut(inKeyMap, "inHospital.correctDiag.detail", "更正诊断");
		defaultPut(inKeyMap, "inHospital.supplyDiags.detail", "补充诊断");
		defaultPut(inKeyMap, "inHospital.神志", "神志");
		defaultPut(inKeyMap, "inHospital.精神状态,", "精神状态");
		defaultPut(inKeyMap, "inHospital.性格人格,", "性格人格");
		defaultPut(inKeyMap, "inHospital.头部,", "头部");
		defaultPut(inKeyMap, "inHospital.语言,", "语言");
		defaultPut(inKeyMap, "inHospital.脑膜刺激征", "脑膜刺激征");
		defaultPut(inKeyMap, "inHospital.嗅觉", "嗅觉");
		defaultPut(inKeyMap, "inHospital.视野,", "视野");
		defaultPut(inKeyMap, "inHospital.眼球运动,", "眼球运动");
		defaultPut(inKeyMap, "inHospital.复视,", "复视");
		defaultPut(inKeyMap, "inHospital.洋葱样皮样感觉障碍,", "洋葱样皮样感觉障碍");
		defaultPut(inKeyMap, "inHospital.运动,发音,咽反射,", "运动,发音,咽反射");
		defaultPut(inKeyMap, "inHospital.味觉,", "味觉");
		defaultPut(inKeyMap, "inHospital.耸肩,", "耸肩");
		defaultPut(inKeyMap, "inHospital.头侧转,", "头侧转");
		defaultPut(inKeyMap, "inHospital.舌", "舌");
		defaultPut(inKeyMap, "inHospital.视力左侧", "视力左侧");
		defaultPut(inKeyMap, "inHospital.指数距离左侧", "指数距离左侧");
		defaultPut(inKeyMap, "inHospital.指动距离左侧", "指动距离左侧");
		defaultPut(inKeyMap, "inHospital.光感左侧", "光感左侧");
		defaultPut(inKeyMap, "inHospital.失明左侧", "失明左侧");
		defaultPut(inKeyMap, "inHospital.视力右侧", "视力右侧");
		defaultPut(inKeyMap, "inHospital.指数距离右侧", "指数距离右侧");
		defaultPut(inKeyMap, "inHospital.指动距离右侧", "指动距离右侧");
		defaultPut(inKeyMap, "inHospital.光感右侧", "光感右侧");
		defaultPut(inKeyMap, "inHospital.失明右侧", "失明右侧");
		defaultPut(inKeyMap, "inHospital.视盘左侧", "视盘左侧");
		defaultPut(inKeyMap, "inHospital.血管左侧", "血管左侧");
		defaultPut(inKeyMap, "inHospital.视网膜左侧", "视网膜左侧");
		defaultPut(inKeyMap, "inHospital.视盘右侧", "视盘右侧");
		defaultPut(inKeyMap, "inHospital.血管右侧", "血管右侧");
		defaultPut(inKeyMap, "inHospital.视网膜右侧", "视网膜右侧");
		defaultPut(inKeyMap, "inHospital.眼睑下垂左侧", "眼睑下垂左侧");
		defaultPut(inKeyMap, "inHospital.眼球突出左侧", "眼球突出左侧");
		defaultPut(inKeyMap, "inHospital.眼球下陷左侧", "眼球下陷左侧");
		defaultPut(inKeyMap, "inHospital.瞳孔大小左侧", "瞳孔大小左侧");
		defaultPut(inKeyMap, "inHospital.瞳孔形状左侧", "瞳孔形状左侧");
		defaultPut(inKeyMap, "inHospital.直接对光反射左侧", "直接对光反射左侧");
		defaultPut(inKeyMap, "inHospital.间接对光反射左侧", "间接对光反射左侧");
		defaultPut(inKeyMap, "inHospital.调节反射左侧", "调节反射左侧");
		defaultPut(inKeyMap, "inHospital.辐辏反射左侧", "辐辏反射左侧");
		defaultPut(inKeyMap, "inHospital.眼睑下垂右侧", "眼睑下垂右侧");
		defaultPut(inKeyMap, "inHospital.眼球突出右侧", "眼球突出右侧");
		defaultPut(inKeyMap, "inHospital.眼球下陷右侧", "眼球下陷右侧");
		defaultPut(inKeyMap, "inHospital.瞳孔大小右侧", "瞳孔大小右侧");
		defaultPut(inKeyMap, "inHospital.瞳孔形状右侧", "瞳孔形状右侧");
		defaultPut(inKeyMap, "inHospital.直接对光反射右侧", "直接对光反射右侧");
		defaultPut(inKeyMap, "inHospital.间接对光反射右侧", "间接对光反射右侧");
		defaultPut(inKeyMap, "inHospital.调节反射右侧", "调节反射右侧");
		defaultPut(inKeyMap, "inHospital.辐辏反射右侧", "辐辏反射右侧");
		defaultPut(inKeyMap, "inHospital.第一支左侧", "第一支左侧");
		defaultPut(inKeyMap, "inHospital.第二支左侧", "第二支左侧");
		defaultPut(inKeyMap, "inHospital.第三支左侧", "第三支左侧");
		defaultPut(inKeyMap, "inHospital.第一支右侧", "第一支右侧");
		defaultPut(inKeyMap, "inHospital.第二支右侧", "第二支右侧");
		defaultPut(inKeyMap, "inHospital.第三支右侧", "第三支右侧");
		defaultPut(inKeyMap, "inHospital.皱额左侧", "皱额左侧");
		defaultPut(inKeyMap, "inHospital.闭目左侧", "闭目左侧");
		defaultPut(inKeyMap, "inHospital.鼻唇沟左侧", "鼻唇沟左侧");
		defaultPut(inKeyMap, "inHospital.口角偏左侧", "口角偏左侧");
		defaultPut(inKeyMap, "inHospital.鼓腮左侧", "鼓腮左侧");
		defaultPut(inKeyMap, "inHospital.面肌抽搐左侧", "面肌抽搐左侧");
		defaultPut(inKeyMap, "inHospital.味觉左侧", "味觉左侧");
		defaultPut(inKeyMap, "inHospital.皱额右侧", "皱额右侧");
		defaultPut(inKeyMap, "inHospital.闭目右侧", "闭目右侧");
		defaultPut(inKeyMap, "inHospital.鼻唇沟右侧", "鼻唇沟右侧");
		defaultPut(inKeyMap, "inHospital.口角偏右侧", "口角偏右侧");
		defaultPut(inKeyMap, "inHospital.鼓腮右侧", "鼓腮右侧");
		defaultPut(inKeyMap, "inHospital.面肌抽搐右侧", "面肌抽搐右侧");
		defaultPut(inKeyMap, "inHospital.味觉右侧", "味觉右侧");
		defaultPut(inKeyMap, "inHospital.schwaban试验", "schwaban试验");
		defaultPut(inKeyMap, "inHospital.weber试验", "weber试验");
		defaultPut(inKeyMap, "inHospital.rinne试验", "rinne试验");
		defaultPut(inKeyMap, "inHospital.眼球震颤", "眼球震颤");
		defaultPut(inKeyMap, "inHospital.腹壁反射", "腹壁反射");
		defaultPut(inKeyMap, "inHospital.左侧腹壁反射上", "左侧腹壁反射上");
		defaultPut(inKeyMap, "inHospital.右侧腹壁反射上", "右侧腹壁反射上");
		defaultPut(inKeyMap, "inHospital.左侧腹壁反射中", "左侧腹壁反射中");
		defaultPut(inKeyMap, "inHospital.右侧腹壁反射中", "右侧腹壁反射中");
		defaultPut(inKeyMap, "inHospital.左侧腹壁反射下", "左侧腹壁反射下");
		defaultPut(inKeyMap, "inHospital.右侧腹壁反射下", "右侧腹壁反射下");
		defaultPut(inKeyMap, "inHospital.提睾反射左侧", "提睾反射左侧");
		defaultPut(inKeyMap, "inHospital.提睾反射右侧", "提睾反射右侧");
		defaultPut(inKeyMap, "inHospital.肛门反射左侧", "肛门反射左侧");
		defaultPut(inKeyMap, "inHospital.肛门反射右侧", "肛门反射右侧");
		defaultPut(inKeyMap, "inHospital.肱二头肌左侧", "肱二头肌左侧");
		defaultPut(inKeyMap, "inHospital.肱三头肌左侧", "肱三头肌左侧");
		defaultPut(inKeyMap, "inHospital.桡骨膜左侧", "桡骨膜左侧");
		defaultPut(inKeyMap, "inHospital.膝反射左侧", "膝反射左侧");
		defaultPut(inKeyMap, "inHospital.踝反射左侧", "踝反射左侧");
		defaultPut(inKeyMap, "inHospital.髌痉挛左侧", "髌痉挛左侧");
		defaultPut(inKeyMap, "inHospital.踝痉挛左侧", "踝痉挛左侧");
		defaultPut(inKeyMap, "inHospital.肱二头肌右侧", "肱二头肌右侧");
		defaultPut(inKeyMap, "inHospital.肱三头肌右侧", "肱三头肌右侧");
		defaultPut(inKeyMap, "inHospital.桡骨膜右侧", "桡骨膜右侧");
		defaultPut(inKeyMap, "inHospital.膝反射右侧", "膝反射右侧");
		defaultPut(inKeyMap, "inHospital.踝反射右侧", "踝反射右侧");
		defaultPut(inKeyMap, "inHospital.髌痉挛右侧", "髌痉挛右侧");
		defaultPut(inKeyMap, "inHospital.踝痉挛右侧", "踝痉挛右侧");
		defaultPut(inKeyMap, "inHospital.Hoffmann左侧", "Hoffmann左侧");
		defaultPut(inKeyMap, "inHospital.Babinski左侧", "Babinski左侧");
		defaultPut(inKeyMap, "inHospital.Chaddock左侧", "Chaddock左侧");
		defaultPut(inKeyMap, "inHospital.Oppenheim左侧", "Oppenheim左侧");
		defaultPut(inKeyMap, "inHospital.Gordon左侧", "Gordon左侧");
		defaultPut(inKeyMap, "inHospital.Hoffmann右侧", "Hoffmann右侧");
		defaultPut(inKeyMap, "inHospital.Babinski右侧", "Babinski右侧");
		defaultPut(inKeyMap, "inHospital.Chaddock右侧", "Chaddock右侧");
		defaultPut(inKeyMap, "inHospital.Oppenheim右侧", "Oppenheim右侧");
		defaultPut(inKeyMap, "inHospital.Gordon右侧", "Gordon右侧");
	};
	public static Map<String, CheckOption> operKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(operKeyMap, "operation.operationDpt", "科别");
		defaultPut(operKeyMap, "operation.bedNumber", "床位号");
		defaultPut(operKeyMap, "operation.preDiagnosis", "术前诊断");
		defaultPut(operKeyMap, "operation.operataionDiagnosis", "术中诊断");
		defaultPut(operKeyMap, "operation.operationTitle", "手术名称");
		defaultPut(operKeyMap, "operation.doctor", "手术医师");
		defaultPut(operKeyMap, "operation.assistant", "助手");
		defaultPut(operKeyMap, DATETIME_TYPE, "operation.beginTime", "开始时间");
		defaultPut(operKeyMap, DATETIME_TYPE, "operation.endTime", "结束时间");
		defaultPut(operKeyMap, "operation.anaesthetic", "麻醉方法");
		defaultPut(operKeyMap, "operation.bloodLoss", "失血量");
		defaultPut(operKeyMap, "operation.bloodTransfusion", "输血量");
		defaultPut(operKeyMap, "operation.detail", "手术经过");
		defaultPut(operKeyMap, "operation.bb", "手术取标本肉眼所见");
		defaultPut(operKeyMap, "operation.bl", "手术取标本送病理");
	};
	public static Map<String, CheckOption> outKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(outKeyMap, "outHospital.department", "科别");
		defaultPut(outKeyMap, DATE_TYPE, "outHospital.outDate", "出院时间");
		defaultPut(outKeyMap, "outHospital.inDescriotion", "入院时情况");
		defaultPut(outKeyMap, "outHospital.inDiagnosis", "入院诊断");
		defaultPut(outKeyMap, "outHospital.treatment", "入院后诊疗经过");
		defaultPut(outKeyMap, "outHospital.outDescription", "出院时情况");
		defaultPut(outKeyMap, "outHospital.outDiagnosis", "出院诊断");
		defaultPut(outKeyMap, "outHospital.operationDesc", "手术名称及伤口愈合情况");
		defaultPut(outKeyMap, "outHospital.dischargeOrder", "出院医嘱");
	};
	public static Map<String, CheckOption> remarkKeyMap = new LinkedHashMap<String, CheckOption>();
	static {
		defaultPut(remarkKeyMap, "remark.content", "备注内容");
		defaultPut(remarkKeyMap, "remark.doctor", "医生");
		defaultPut(remarkKeyMap, "remark.operator", "录入者");
		defaultPut(remarkKeyMap, "remark.label", "标签");
	};
	public static Map<String, CheckOption> bloodKeyMap = new LinkedHashMap<String, CheckOption>() {
		{
			putAll(patientKeyMap);
		}
	};
	static {
		defaultPut(bloodKeyMap, "operation.operationTitle", "手术名称");
		defaultPut(bloodKeyMap, "operation.doctor", "手术医师");
		defaultPut(bloodKeyMap, "operation.assistant", "助手");
		defaultPut(bloodKeyMap, DATETIME_TYPE, "operation.beginTime", "开始时间");
		defaultPut(bloodKeyMap, DATETIME_TYPE, "operation.endTime", "结束时间");
		defaultPut(bloodKeyMap, "operation.anaesthetic", "麻醉方法");
		defaultPut(bloodKeyMap, "operation.bloodLoss", "失血量");
		defaultPut(bloodKeyMap, "operation.bloodTransfusion", "输血量");
	}
	public static Map<String, CheckOption> ALL_KEY_MAP = new LinkedHashMap<String, CheckOption>() {
		{
			putAll(patientKeyMap);
			putAll(frontPageKeyMap);
			putAll(inKeyMap);
			putAll(operKeyMap);
			putAll(outKeyMap);
		}
	};

	public static String getJSArrayString(Collection<CheckOption> c) {
		StringBuilder sb = new StringBuilder();
		for (CheckOption o : c)
			sb.append("\"" + o.getName() + "\",");
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String getAndOrString(String s) {
		if (StringTools.checkNull(s) == null)
			return "与";
		else if (s.equalsIgnoreCase("and"))
			return "与";
		else if (s.equalsIgnoreCase("or"))
			return "或";
		else if (s.equalsIgnoreCase("not"))
			return "非";
		else
			return s;
	}

	protected static void defaultPut(Map<String, CheckOption> m, String type, String name, String label) {
		m.put(name, new CheckOption(type, name, label));
	}

	protected static void defaultPut(Map<String, CheckOption> m, String name, String label) {
		m.put(name, new CheckOption(DEFAULT_TYPE, name, label));
	}

	public static String getOptionString(String s) {
		if (StringTools.checkNull(s) == null)
			return "等于";
		else if (s.equalsIgnoreCase("eq"))
			return "等于";
		else if (s.equalsIgnoreCase("contains"))
			return "包含";
		else if (s.equalsIgnoreCase("notIn"))
			return "不包含";
		else if (s.equalsIgnoreCase("ge"))
			return "大于";
		else if (s.equalsIgnoreCase("le"))
			return "小于";
		else if (s.equalsIgnoreCase("ne"))
			return "不等于";
		else
			return s;
	}

	public static Map<String, String> PAY_TYPE_MAP = new TreeMap<String, String>() {
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
	public static Map<String, String> ENTRY_MAP = new TreeMap<String, String>() {
		{
			put("1", "急诊");
			put("2", "门诊");
			put("3", "其他医疗机构转入");
			put("4", "其他");
		}
	};

	public static String getExtendTypeDesc(String type) {
		switch (type) {
		case "cxz":
			return "出血张组";
		case "icu":
			return "ICU组";
		case "bflz":
			return "出血李组";
		case "jzz":
			return "脊柱组";
		case "ldz":
			return "颅底组";
		case "xrz":
			return "小儿组";
		case "gnz":
			return "功能组";
		case "zlz":
			return "肿瘤组";
		case "remark":
			return "备注";
		default:
			return type;
		}
	}
}
