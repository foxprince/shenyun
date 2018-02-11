package cn.anthony.boot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import cn.anthony.boot.domain.FrontPage;
import cn.anthony.boot.domain.InHospital;
import cn.anthony.boot.domain.Operation;
import cn.anthony.boot.domain.OutHospital;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.Patient.Diag;
import cn.anthony.boot.domain.Patient.OperationDetail;
import cn.anthony.boot.domain.Patient.OutDiag;
import cn.anthony.boot.domain.Patient.SevereDetail;
import cn.anthony.util.FileTools;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.SAXUtil;
import cn.anthony.util.StringTools;

public class PatientUtil {
	public static void main(String[] args) throws Exception {
		File file = new File("/Users/zj/tmp/KYBLSJ/入院记录1/20161001-20161031/HospitalRecord_000529855800_1.xml");
		System.out.println(StringTools.pe(FileUtils.readFileToString(file), "<ElementText(.*)>(.*)</ElementText>"));
		System.out.println(extractText(file,"<ElementText>","</ElementText>",true));
		// System.out.println(file.getAbsolutePath().replaceAll("\\", "/"));
		// Patient p = (extractPatientFromFile(file));
		// System.out.println(p.somatoscopy);
		// System.out.println(p.getFrontRecords().get(0).getMainDiag());
		// for (OutDiag o : p.getFrontRecords().get(0).getOutDiags())
		// System.out.println("----:" + o);
		// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(p)));
		// System.out.println("==================================================");
		// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(in.somatoscopy)));
		// System.out.println("==================================================");
		// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(in.somatoscopy.sExamination)));
	}

	private static List<File> getDirs(String s) {
		StringTokenizer st = new StringTokenizer(s, ",");
		List<File> l = new ArrayList<File>();
		while (st.hasMoreTokens())
			l.add(new File(st.nextToken()));
		return l;
	}

	public static List<File> getPacsFiles(String patientNo) {
		List<File> l = new ArrayList<File>();
		for(File dir : getDirs(Constant.PACS_DIR))
			l.addAll(getPacsFilesInDir(dir, patientNo));
		return l;
	}

	public static List<String> getPacsRelativeNames(String patientNo) {
		List<String> l = new ArrayList<String>();
		for (File f : getPacsFiles(patientNo)) {
			String path = f.getAbsolutePath();
			System.out.println(path);
			l.add(path.substring(path.indexOf(Constant.PACS_DIR) + Constant.PACS_DIR.length()));
			// int i = StringUtils.lastIndexOf(path, "\\",
			// path.lastIndexOf("\\") - 1) + 1;
			// l.add(path.substring(i).replace("\\", "/"));
		}
		return l;
	}

	/**
	 * 病案号的pacs文件夹格式：***-patientNo-***
	 * 
	 * @param dir
	 * @param patientNo
	 * @return
	 */
	private static List<File> getPacsFilesInDir(File dir, String patientNo) {
		try {
			return processPacs(dir, (File f) -> {
				return f.isDirectory() && f.getName().indexOf("-" + patientNo + "-") > 0;
			});
		} catch (Exception e) {
			return new ArrayList<File>();
		}
	}

	private static List<File> processPacs(File dir, FileFilter filter) {
		List<File> l = new ArrayList<File>();
			File[] fs = dir.listFiles(filter);
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].isDirectory()) {
					l.addAll(Arrays.asList(fs[i].listFiles((File pathname) -> {
						String name = pathname.getName().toLowerCase();
						return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".bmp");
					})));
				}
			}
		return l;
	}

	private static void process(File dir, FileFilter filter) throws Exception {
		File[] fs = dir.listFiles(filter);
		for (int i = 0; i < fs.length; i++) {
			File file = fs[i];
			if (file.isDirectory()) {
				process(file, filter);
			} else {
				System.out.println(fs[i].getAbsolutePath());
				extractInFromFile(fs[i]);
			}
		}
	}
	
	public static StringBuilder extractText(File f,String start,String end, boolean filterBlank) {
		try {
			String s = FileUtils.readFileToString(f, "UTF-8");
			return new StringBuilder(StringTools.e(new StringBuilder(s), start, end));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static StringBuilder extractTextList(File f,List<String> startList,String end, boolean filterBlank) {
		try {
			String s = FileUtils.readFileToString(f, "UTF-8");
			return new StringBuilder(StringTools.eList(new StringBuilder(s), startList, end));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Patient extractPatientFromFile(File file) throws IOException, ParseException {
		//StringBuilder s = extractText(file, "<text>","</text>",true);
		StringBuilder s = new StringBuilder(FileUtils.readFileToString(file));
		Patient p = new Patient();
		p.source = "haitai";
		p.srcFile = file.getName();
		p.pId = StringTools.e(s, "病案号", "医疗付费方式");
		p.name = StringTools.e(s, "姓名", "性别");
		p.sex = StringTools.e(s, "性别", "1.男 2.女");
		try {
			StringBuilder s0 = new StringBuilder(StringTools.e(s, "出生日期", "年龄"));
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(StringTools.e(s0, "", "年")), Integer.parseInt(StringTools.e(s0, "年", "月")) - 1,
					Integer.parseInt(StringTools.e(s0, "月", "日")));
			p.dateOfBirth = cal.getTime();
			p.age = Integer.parseInt(StringTools.pe(s.toString(), "年龄.*?(\\d+).*国籍"));
		} catch (Exception e) {
		}
		p.certNo = StringTools.e(s, "身份证号", "职业");
		FrontPage f = extractFrontPageWithText(file);
		f.outDiags = extractDiags(f, s);
		f.operationDetails = extractODetails(f, StringTools.e(s, "II助", "颅脑损伤患者昏迷时间"));
		f.severeDetails = extractSeveres(f, StringTools.e(s, "出重症监护室时间", "呼吸机使用时间"));
		p.addFront(f);
		p.birthplace = f.birthplace;
		p.certNo = f.certNo;
		p.country = f.country;
		p.nationality = f.nationality;
		p.nativeplace = f.nativeplace;
		p.registeredaddress = f.registeredaddress;
		return p;
	}

	private static List<SevereDetail> extractSeveres(FrontPage f, String e) {
		List<SevereDetail> l = new ArrayList<SevereDetail>();
		char c = '\n';
		String[] ss = StringUtils.split(e, c);
		try {
			int i = 0;
			while (i < ss.length) {
				if (!(ss[i].trim().equals("-") || ss[i].trim().equals("-  -")))
					l.add(new SevereDetail(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : ""));
				i += 3;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l;
	}

	private static List<OutDiag> extractDiags(FrontPage f, StringBuilder s) {
		List<OutDiag> l = new ArrayList<OutDiag>();
		String t = StringTools.e(s, "病情", "入院病情：1.有，2.临床未确定");
		char c = '\n';
		String[] ss = StringUtils.split(t, c);
		try {
			int i = 0;
			while (i < ss.length) {
				// if (!(ss[i].trim().equals("主要诊断:") ||
				// ss[i].trim().equals("其他诊断:") ||
				// ss[i].trim().equals("主要诊断：")|| ss[i].trim().equals("其他诊断：")))
				if (ss[i].indexOf("其他诊断") >= 0)
					l.add(new OutDiag(i < ss.length ? ss[i++].substring(5) : "", i < ss.length ? ss[i++] : "",
							i < ss.length ? ss[i++] : ""));
				else
					i += 3;
			}
		} catch (Exception ex) {
		}
		StringTools.e(s, "出院诊断", "病情");
		t = StringTools.e(s, "病情", "损伤、中毒的外部原因");
		ss = StringUtils.split(t, c);
		try {
			int i = 0;
			while (i < ss.length) {
				if (!(ss[i].trim().equals("主要诊断:") || ss[i].trim().equals("其他诊断:") || ss[i].trim().equals("主要诊断：")
						|| ss[i].trim().equals("主要诊断：")))
					l.add(new OutDiag(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : ""));
				else
					i += 3;
			}
		} catch (Exception ex) {
		}
		return l;
	}

	private static List<OperationDetail> extractODetails(FrontPage f, String e) {
		// System.out.println(e);
		List<OperationDetail> l = new ArrayList<OperationDetail>();
		// StringTokenizer st = new StringTokenizer(e, "\n");
		char c = '\n';
		String[] ss = StringUtils.split(e, c);
		try {
			int i = 0;
			while (i < ss.length) {
				l.add(new OperationDetail(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
						i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
						i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
						i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : ""));
			}
		} catch (Exception ex) {
		}
		return l;
	}

	public static FrontPage extractFrontPage(File file) {
		FrontPage f = new FrontPage();
		RefactorUtil.setObjectValue(f, SAXUtil.getFrontPageMap(file));
		f.srcFile = file.getAbsolutePath();
		return f;
	}
	
	public static FrontPage extractFrontPageWithText(File file) throws IOException, ParseException {
		StringBuilder s = new StringBuilder(FileUtils.readFileToString(file));
		FrontPage f = new FrontPage();
		// 医疗机构
		f.medicalInstitution=StringTools.e(s, "医疗机构", "（组织机构代码");
		// （组织机构代码
		f.organizationCode=StringTools.e(s, "（组织机构代码", ")");
		// 病案号
		f.adminission_no=StringTools.e(s, "病案号", "医疗付费方式");
		// 医疗付费方式
		f.patientclass=StringTools.e(s, "医疗付费方式", "第");
		// 第几次住院
		f.numberOfTimes=StringTools.e(s, "次住院", "门诊号");
		// 门诊号
		f.outpatientnum=StringTools.e(s, "门诊号", "健康卡号");
		// 健康卡号
		f.healthCardNo=StringTools.e(s, "健康卡号", "病理号");
		// 病理号
		f.pathological=StringTools.e(s, "病理号", "姓名");
		// 姓名
		f.name=StringTools.e(s, "姓名", "性别");
		// 性别
		f.sex=StringTools.e(s, "性别", "1.男 2.女");
		// 出生日期 1943年04月21日
		try {
			StringBuilder s0 = new StringBuilder(StringTools.e(s, "出生日期", "年龄"));
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(StringTools.e(s0, "", "年")), Integer.parseInt(StringTools.e(s0, "年", "月")) - 1,
					Integer.parseInt(StringTools.e(s0, "月", "日")));
			f.dateOfBirthday = cal.getTime();
			f.age = Integer.parseInt(StringTools.pe(s.toString(), "年龄.*?(\\d+).*国籍"));
		} catch (Exception e) {
		}
		// 国籍
		f.country=StringTools.e(s, "国籍", "出生地");
		// 出生地
		f.birthplace=StringTools.e(s, "出生地", "籍贯");
		// 籍贯
		f.nativeplace=StringTools.e(s, "籍贯", "民族");
		// 民族
		f.nationality=StringTools.e(s, "民族", "身份证号");
		// 身份证号
		f.certNo = StringTools.e(s, "身份证号", "职业");
		// 职业
		f.occupation = StringTools.e(s, "职业", "婚姻");
		// 婚姻
		f.marriageStatus=StringTools.e(s, "婚姻", "1.未婚 2.已婚");
		// 现住址
		f.homeAddress=StringTools.e(s, "现住址", "电话");
		// 电话
		f.mobilephone=StringTools.e(s, "电话", "邮编");
		// 家庭邮编
		f.homePostcode=StringTools.e(s, "邮编", "户口地");
		// 户口地址
		f.registeredaddress=StringTools.eList(s, Arrays.asList("户口地址","户口地"), "邮编");
		// 户口邮编
		f.registeredemail=StringTools.e(s, "邮编", "工作单位及地址");
		// 工作单位及地址
		f.company=StringTools.e(s, "工作单位及地址", "单位电话");
		// 单位电话
		f.businessphone=StringTools.e(s, "单位电话", "邮编");
		// 单位邮编
		f.businesspostcode=StringTools.e(s, "邮编", "病人来源");
		// 病人来源
		f.patientfrom=StringTools.e(s, "病人来源", "保健人员分类");
		// 保健人员分类
		f.sponsor=StringTools.e(s, "保健人员分类", "保健人员在岗状态");
		// 保健人员在岗状态
		f.sponsorstatues=StringTools.e(s, "保健人员在岗状态", "联系人姓名");
		// 联系人姓名
		f.contactName=StringTools.e(s, "联系人姓名", "关系");
		// 联系人关系
		f.relation=StringTools.e(s, "关系", "地址");
		// 联系人地址
		f.contactaddress=StringTools.e(s, "地址", "电话");
		// 联系人电话
		f.contactphone=StringTools.e(s, "电话", "入院途径");
		// 入院途径 1.急诊 2.门诊 3.其他医疗机构转入 9.其他
		f.admissionway=StringTools.e(s, "入院途径", "1.急诊");
		// 入院时间 2015年02月13日18时
		f.admissionTime=DateUtils.parseDate(StringTools.e(s, "入院时间", "入院科别"), "yyyy年MM月dd日HH时");
		// 入院科别
		f.admissionDept=StringTools.e(s, "入院科别", "病房");
		// 入院病房
		f.admissionWard=StringTools.e(s, "病房", "出院时间");
		// 出院时间
		f.dischargeTime=DateUtils.parseDate(StringTools.e(s, "出院时间", "出院科别"), "yyyy年MM月dd日HH时","yyyy年MM月dd日HH时yyyy年MM月dd日");
		// 出院科别
		f.dischargeDept=StringTools.e(s, "出院科别", "病房");
		// 出院病房
		f.dischargeWard=StringTools.e(s, "病房", "实际住院");
		// 实际住院天数
		f.inhopitalday=Integer.parseInt(StringTools.e(s, "实际住院", "天"));
		// 转科科别
		f.changedept=StringTools.eList(s, Arrays.asList("转科科别","转科"), "门（急）诊诊断");
		// 门（急）诊诊断
		f.REGISTER_DIAGNOSIS=StringTools.e(s, "门（急）诊诊断", "疾病编");
		// 门（急）诊疾病编码
		f.REGISTER_CODE=StringTools.eList(s, Arrays.asList("疾病编","疾病编码"), "出院诊断");//放在前面是有道理的
		// 出院诊断 其他诊断
		//public List<Patient.OutDiag> outDiags;
		// 出院诊断 主要诊断 疾病
		f.mainDiag=StringTools.e(s, "主要诊断", "\n");
		// // 出院诊断 主要诊断 编码
		f.mainDiagCode=StringTools.e(s, "", "\n");
		// // 出院诊断 入院病情：1.有，2.临床未确定，3.情况不明，4.无
		f.admissionCondition=StringTools.e(s, "", "其他诊断");
		// 损伤、中毒的外部原因
		f.EXTERNAL_CAUESES=StringTools.e(s, "损伤、中毒的外部原因", "疾病编码");
		// 损伤、中毒疾病编码
		f.EXTERNAL_CODE=StringTools.e(s, "疾病编码", "病理诊断");
		// 病理诊断
		f.PATHOLOGY_DIAGNOSIS=StringTools.e(s, "病理诊断", "疾病编码");
		// 病理诊断疾病编码
		f.PATHOLOGY_CODE=StringTools.e(s, "疾病编码", "药物过敏");
		// 药物过敏：1.无 2.有
		f.drugAllergy=StringTools.e(s, "药物过敏", "1.无 2.有");
		// 过敏药物
		f.ALLERGIC_DRUG=StringTools.e(s, "过敏药物", "死亡患者尸检");
		// 死亡患者尸检
		f.deadAutopsy=StringTools.e(s, "死亡患者尸检", "ABO血型");
		// ABO血型：1.A 2.B 3.O 4.AB 5.不详 6.未查
		f.ABO=StringTools.e(s, "ABO血型", "1.A");
		// Rh血型：1.阴 2.阳 3.不详 4.未查
		f.Rh=StringTools.e(s, "Rh血型", "1.阴");
		// 输血品种- 红细胞 单位
		f.redBloodCell=StringTools.e(s, "输血品种", "单位");
		// 输血品种-血小板 袋
		f.platelet=StringTools.e(s, "血小板", "血浆");
		// 输血品种-血浆 ml
		f.plasma=StringTools.e(s, "血浆", "全血");
		// 输血品种-全血 ml
		f.wholeBlood=StringTools.e(s, "全血", "其它");
		// 输血品种-其它 ml
		f.other=StringTools.e(s, "其它", "科 主 任");
		// 科 主 任
		f.KZR_DOCTOR_NAME=StringTools.e(s, "科 主 任", "主任（副主任）医师");
		// 主任（副主任）医师
		f.ZRFZR_DOCTOR_NAME=StringTools.e(s, "主任（副主任）医师", "主治医师");
		// 主治医师
		f.ZZ_DOCTOR_NAME=StringTools.e(s, "主治医师", "住院医师");
		// 住院医师
		f.ZY_DOCTOR_NAME=StringTools.e(s, "住院医师", "主诊医师");
		// 主诊医师
		f.ZZHEN_DOCTOR_NAME=StringTools.e(s, "主诊医师", "责任");
		// 责任护士
		f.ZR_NURSE_NAME=StringTools.e(s, "责任护士", "进修医师");
		// 进修医师
		f.JX_DOCTOR_NAME=StringTools.e(s, "进修医师", "实习医师");
		// 实习医师
		f.SX_DOCTOR_NAME=StringTools.e(s, "实习医师", "编 码 员");
		// 编 码 员
		f.coder=StringTools.e(s, "编 码 员", "病案质量");
		// 病案质量:1.甲 2.乙 3.丙
		f.ZK_DJ1=StringTools.e(s, "病案质量", "1.甲");
		// 质控医师
		f.ZK_DOCTOR_NAME=StringTools.e(s, "质控医师", "质控日期");
		// 质控日期
		f.qualityControlDate=StringTools.e(s, "质控日期", "质控护士");
		// 质控护士
		f.ZK_NURSE_NAME=StringTools.e(s, "质控护士", "填报医师");
		// 填报医师签名
		//public String imagSignature1;
		// 填报医师代码
		//public String TB_DOC_CODE;
		// 填报医师名称
		//public String TB_DOC_NAME;
		// 颅脑损伤患者昏迷时间：入院前-天-小时-分钟 入院后-天-小时-分钟
		// 入院前
		//f.beforeday=Integer.parseInt(StringTools.e(s, "入院前", "天"));
		// 天
		//f.beforehours=Integer.parseInt(StringTools.e(s, "天", "小时"));
		// 小时
		//f.beforeminutes;
		// 分钟 入院后
		//public Integer afterday;
		// 天
		//public Integer afterhours;
		// 小时
		//public Integer afterminutes;
		f.srcFile = file.getAbsolutePath();
		return f;
	}

	public static InHospital extractInFromFile(File file) throws StringIndexOutOfBoundsException, IOException {
//		List<String> startList = Arrays.asList("<ElementText>","<ElementText name=\"7D1C4C96BDB34EC5BB82726A48011089_3\">","<ElementText name=\"7D1C4C96BDB34EC5BB82726A48011089_1\">");
//		StringBuilder s = null;
//		try{s= extractTextList(file, startList,"</ElementText>",false);}catch(StringIndexOutOfBoundsException e){throw e;}
		StringBuilder s = new StringBuilder(FileUtils.readFileToString(file));
		InHospital i = new InHospital();
		i.srcFile = file.getAbsolutePath();
		//i.admissionDept = StringTools.e(s, "科室：", "住院号");//extractTag("科室", ".*?", s.toString());
		//i.admissionNo = extractPIdTag(file);
		try {
			i.inDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "入院日期：", "性别"));
			i.takingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "病史采集日期：", "年龄"));
		} catch (Exception e) {
		}
		if (s.indexOf("病史叙述人") >= 0)
			i.takingFrom = StringTools.e(s, "病史叙述人", "出生地");
		else if (s.indexOf("病史叙") >= 0)
			i.takingFrom = StringTools.e(s, "病史叙", "出生地");
		if (s.indexOf("职业") > 0 || s.indexOf("主诉") > 0)
			i.reliability = StringTools.pe(s.toString(), "可靠性(.*?)(职业|主诉)");
		if (s.indexOf("亲属姓名、电话") > 0)
			i.contact = StringTools.e(s, "亲属姓名、电话", "主诉");
		i.selfDesc = StringTools.e(s, "主诉", "现病史");
		i.nowMedicalHistory = StringTools.e(s, "现病史", "既往史");
		try{
			i.pastMedicalHistory = StringTools.pe(s.toString(), "既往史([\\s\\S]*?)(传染病史|个人生活史|个人史)");
			if (s.indexOf("传染病史") >= 0)
				i.infectiousHistory = StringTools.pe(s.toString(), "传染病史([\\s\\S]*?)(个人生活史|个人史)");
			i.lifeHistory = StringTools.pe2(s.toString(), "(个人生活史|个人史)([\\s\\S]*?)家族史");
			i.familyHistory = StringTools.e(s, "家族史", "体 格 检 查");
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		if (s.indexOf("辅助检查") >= 0)
			i.somatoscopy = ParseUtil.extractSomatoscopy(StringTools.eList(s, Arrays.asList("体 格 检 查","体  格  检  查"), "辅助检查"));
		if (s.indexOf("辅助检查") >= 0&&s.indexOf("初步诊断") >= 0) {
			i.auxiliaryExamination = StringTools.e(s, "辅助检查", "初步诊断");
			i.firstDiag.detail = StringTools.e(s, "初步诊断", "签名");
			i.firstDiag.signature = StringTools.e(s, "签名", "时间");
			try {
				i.firstDiag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
			} catch (ParseException e) {
			}
		} else if (s.indexOf("辅助检查") >= 0&&s.indexOf("确定诊断") >= 0){
			i.auxiliaryExamination = StringTools.e(s, "辅助检查", "确定诊断");
		}
		if (s.indexOf("确定诊断") > 0) {
			i.confirmDiag.detail = StringTools.e(s, "确定诊断", "签名");
			i.confirmDiag.signature = StringTools.e(s, "签名", "时间");
			try {
				i.confirmDiag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
			} catch (ParseException e) {
			}
		}
		while (s.indexOf("补充诊断") >= 0) {
			Diag diag = new Diag("补充诊断");
			diag.detail = StringTools.e(s, "补充诊断", "签名");
			diag.signature = StringTools.e(s, "签名", "时间");
			try {
				diag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
			} catch (ParseException e) {
			}
			i.supplyDiags.add(diag);
		}
		if (s.indexOf("更正诊断") > 0) {
			i.correctDiag.detail = StringTools.e(s, "更正诊断", "签名");
			i.correctDiag.signature = StringTools.e(s, "签名", "时间");
			try {
				i.correctDiag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
			} catch (Exception e) {
			}
		}
		return i;
	}

	public static Operation extractOperationFromFile(File file) throws IOException {
		//StringBuilder s = extractText(file, "<text>","</text>",true);
		StringBuilder s = new StringBuilder(FileUtils.readFileToString(file));
		Operation o = new Operation();
		o.operationDpt = StringTools.e(s, "科别", "床位号");
		o.bedNumber = StringTools.e(s, "床位号", "术前诊");
		o.preDiagnosis = StringTools.eList(s, Arrays.asList("术前诊断","术前诊"), "术中诊断");
		try {
			o.operataionDiagnosis = StringTools.e(s, "术中诊断", "手术名称");
			o.operationTitle = StringTools.e(s, "手术名称", "手术医师");
		} catch (Exception e) {
			e.printStackTrace();
		}
		o.doctor = StringTools.pe(s.toString(), "手术医师(.*\n)助手(姓名|名称)");
		o.assistant = StringTools.pe2(s.toString(), "(助手姓名|助手名称)：{0,1}(.*\n)手术时间");
		try {
			o.beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "手术时间", "开始"));
			if (s.indexOf("时") > 0 && s.indexOf("分") > 0) {
				o.endTime = DateUtils.parseDate(StringTools.pe(StringTools.e(s, "开始", "完毕"), "(\\d{2}时\\d{2}分)"), "yyyy-MM-dd HH:mm",
						"HH时mm分");
				Calendar b = Calendar.getInstance();
				b.setTime(o.beginTime);
				Calendar c = Calendar.getInstance();
				c.setTime(o.endTime);
				c.set(b.get(Calendar.YEAR), b.get(Calendar.MONTH), b.get(Calendar.DAY_OF_MONTH));
				o.endTime = c.getTime();
			} else
				o.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "开始", "完毕"));
		} catch (Exception e) {
		}
		if (s.indexOf("血量") > 0) {
			o.anaesthetic = StringTools.pe(s.toString(), "麻醉方法：(.*?)(血量|失血量)");
			o.bloodLoss = StringTools.pe(s.toString(), "血量：(.*?)(血量|输血量)");
			if (s.indexOf("输血量") > 0)
				o.bloodTransfusion = StringTools.e(s, "输血量", "手术经过");
		} else
			o.anaesthetic = StringTools.e(s, "麻醉方法", "手术经过、术中出现的情况及处理等");
		if (s.indexOf("手术取标本肉眼所见") > 0) {
			o.detail = StringTools.e(s, "手术经过、术中出现的情况及处理等", "手术取标本肉眼所见");
			o.bb = StringTools.pe(s.toString(), "手术取标本肉眼所见：([\\s\\S]*?)(手术取标本送病理|术取标本送病理)");
		} else if (s.indexOf("手术取标本") > 0) {
			o.detail = StringTools.e(s, "手术经过、术中出现的情况及处理等", "手术取标本");
			o.bl = StringTools.pe(s.toString(), "手术取标本送病理：([\\s\\S]*?)(医师签名|签名)");
		}
		o.sign = StringTools.pe(s.toString(), "签名：(.*?)(记录日期|日期)");
		try {
			//o.recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "日期", "</text>"));
		} catch (Exception e) {
		}
		o.srcFile = file.getAbsolutePath();
		return o;
	}

	public static OutHospital extractOutFromFile(File file) throws IOException {
		//StringBuilder s = extractText(file, "<text>","</text>",true);
		StringBuilder s = new StringBuilder(FileUtils.readFileToString(file));
		OutHospital o = new OutHospital();
		//o.department = extractTag("科别", ".*?", s.toString());
		try {
			o.outDate = new SimpleDateFormat("yyyy年MM月dd日").parse(extractTag("出院时间[:|：]{0,1}", "\\d{4}年\\d{2}月\\d{2}日", s.toString()));// StringTools.e(s,
		} catch (Exception e) {
		}
		o.inDescriotion = StringTools.e(s, "入院时情况(包括检验异常结果等)", "入院诊断");
		o.inDiagnosis = StringTools.e(s, "入院诊断", "入院后诊疗经过");
		o.treatment = StringTools.e(s, "入院后诊疗经过", "出院时情况");
		o.outDescription = StringTools.e(s, "出院时情况(包括主要化验结果等)", "出院诊断");
		o.outDiagnosis = StringTools.e(s, "出院诊断", "手术名称及伤口愈合情况");
		o.operationDesc = StringTools.e(s, "手术名称及伤口愈合情况", "出院医嘱");
		o.dischargeOrder = StringTools.pe(s.toString(), "出院医嘱([\\s\\S]*?)(备注|医生签名)");
		if (s.indexOf("备注") >= 0&&s.indexOf("医生签名") >= 0)
			o.note = StringTools.e(s, "备注", "医生签名");
		//o.sign = StringTools.e(s, "医生签名", "</text>");
		o.srcFile = file.getAbsolutePath();
		return o;
	}

	final static Pattern PID_PATTERN = Pattern.compile("^.*[住院|病案]号[:|：](\\d+)$");
	final static Pattern PID_TAG_PATTERN = Pattern.compile("<text>(住院|病案)号(:|：){0,1}</text>");
	final static Pattern PID_CODE_PATTERN = Pattern.compile("<text>(\\d+)</text>");

	public static String extractPIdTag(File file) {
		Matcher matcher = null;
		Matcher tagMatcher = null;
		String pId = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String ln;
			boolean ready = false;
			Matcher pIdMatcher = null;
			while ((ln = br.readLine()) != null) {
				matcher = PID_PATTERN.matcher(ln);
				if (matcher.find()) {
					pId = matcher.group(1);
					break;
				} else {
					tagMatcher = PID_TAG_PATTERN.matcher(ln);
					if (tagMatcher.matches()) {
						ready = true;
					} else if (ready) {
						pIdMatcher = PID_CODE_PATTERN.matcher(ln);
						if (pIdMatcher.find()) {
							pId = pIdMatcher.group(1);
							break;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pId;
	}

	public static String extractTag(String obj, String match, String src) {
		Pattern tagPattern = Pattern.compile("(\\s*" + obj + ")(:|：){0,1}");
		Pattern contentPattern = Pattern.compile("(" + match + ")");
		Matcher tagMatcher = null;
		Matcher contentMatcher = null;
		String content = null;
				tagMatcher = tagPattern.matcher(src);
				if (tagMatcher.matches()) {
				//	ready = true;
				//} else if (ready) {
					contentMatcher = contentPattern.matcher(src);
					if (contentMatcher.find()) {
						content = contentMatcher.group(1);
					}
				}
		return content.trim();
	}

	public static StringBuilder extractAllText(File file) {
		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String ln;
			boolean ready = false;
			while ((ln = br.readLine()) != null) {
				if (ln.startsWith("<text>") || ln.endsWith("</text>"))
					sb.append(ln + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb;
	}

	public static String assetType(String ext) {
		if(FileTools.imgExts.contains(ext.toLowerCase()))
			return "1";
		else if(FileTools.videoExts.contains(ext.toLowerCase()))
			return "2";
		else if(ext.equalsIgnoreCase("ppt")||ext.equalsIgnoreCase("pptx"))
			return "3";
		return "0";
	}
}
