package cn.anthony.boot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.anthony.boot.domain.InHospital;
import cn.anthony.boot.domain.Operation;
import cn.anthony.boot.domain.OutHospital;
import cn.anthony.boot.domain.Patient;
import cn.anthony.util.FileTools;
import cn.anthony.util.StringTools;

public class PatientUtil {
    public static void main(String[] args) throws ParseException {
	File file = new File("E:\\project\\神云系统\\data\\数据导出-2015-1-1日-8月10日\\KYBLSJ_07\\出院记录_20150708\\DischargeSummary_000455580200_1.xml");
	System.out.println(new SimpleDateFormat("yyyy年MM月dd日").parse(extractTag("出院时间", "\\d{4}年\\d{2}月\\d{2}日", file)));
	// InHospital in = (extractInFromFile(file));
	System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(extractOutFromFile(file))));
    }

    public static StringBuilder extractText(File f) {
	List<String> l = FileTools.quickReadFile(f.getAbsolutePath());
	int start = 0;
	int end = 0;
	int i = 0;
	for (String s : l) {
	    i++;
	    if (s.contains("<text>"))
		start = i;
	    if (s.contains("</text>")) {
		end = i;
		break;
	    }
	}
	l = l.subList(start, end);
	StringBuilder sb = new StringBuilder();
	for (String s : l)
	    if (StringTools.checkNull(s) != null)
		sb.append(s.trim() + "\n");
	// System.out.println(sb.toString());
	return sb;
    }

    public static Patient extractPatientFromFile(File file) {
	StringBuilder s = extractText(file);
	Patient p = new Patient();
	p.pId = StringTools.e(s, "病案号：", "医疗付费方式");
	p.payType = StringTools.e(s, "医疗付费方式：", "第");
	p.clinicNo = StringTools.e(s, "门诊号：", "健康卡号");
	p.healthCardNo = StringTools.e(s, "健康卡号：", "病理号");
	p.pathologicNo = StringTools.e(s, "病理号：", "姓名");
	p.name = StringTools.e(s, "姓名", "性别");
	p.sex = StringTools.e(s, "性别", "1.男 2.女");
	try {
	    StringBuilder s0 = new StringBuilder(StringTools.e(s, "出生日期", "年龄"));
	    Calendar cal = Calendar.getInstance();
	    cal.set(Integer.parseInt(StringTools.e(s0, "", "年")), Integer.parseInt(StringTools.e(s0, "年", "月")) - 1,
		    Integer.parseInt(StringTools.e(s0, "月", "日")));
	    p.dateOfBirth = cal.getTime();
	} catch (Exception e) {
	}
	p.age = Integer.parseInt(StringTools.pe(s.toString(), "年龄.*?(\\d+).*国籍"));
	p.country = StringTools.e(s, "国籍", "出生地");
	p.birthPlace = StringTools.e(s, "出生地", "籍贯");
	p.nativePlace = StringTools.e(s, "籍贯", "民族");
	p.nationality = StringTools.e(s, "民族", "身份证号");
	p.certNo = StringTools.e(s, "身份证号", "职业");
	p.occupation = StringTools.e(s, "职业", "婚姻");
	p.marriage = StringTools.e(s, "婚姻", "1.未婚 2.已婚");
	p.homeAddress = StringTools.e(s, "现住址", "电话");
	p.phone = StringTools.e(s, "电话", "邮编");
	p.company = StringTools.e(s, "工作单位及地址", "单位电话");
	p.contactName = StringTools.e(s, "联系人姓名", "关系");
	p.contactRelation = StringTools.pe(s.toString(), "联系人姓名.*关系(.*)地址");
	Pattern pa = Pattern.compile("(.*)\\d{6}.*\\d{6}.*\\d{6}(.*)\\d{6}" + "(.*)");
	Matcher m = pa.matcher(StringTools.e(s, "3.其他医疗机构转入  9.其他", "入院时间"));
	if (m.find()) {
	    try {
		p.provinve = (m.group(1).trim());
		p.city = (m.group(2).trim());
		p.street = (m.group(3).trim());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	p.sourceFile = file.getAbsolutePath();
	return p;
    }

    public static InHospital extractInFromFile(File file) {
	StringBuilder s = extractText(file);
	InHospital i = new InHospital();
	i.admissionDept = extractTag("科室", ".*?", file);
	i.admissionNo = extractPIdTag(file);// StringTools.e(s, "住院号：", "入 院 记
					    // 录");
	try {
	    i.inDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "入院日期：", "性别"));
	    i.takingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "病史采集日期：", "年龄"));
	} catch (Exception e) {
	}
	i.takingFrom = StringTools.e(s, "病史叙述人：", "出生地");
	i.reliability = StringTools.e(s, "可靠性：", "职业");
	i.contact = StringTools.e(s, "亲属姓名、电话：", "主诉");
	i.selfDesc = StringTools.e(s, "主诉", "现病史");
	i.nowMedicalHistory = StringTools.e(s, "现病史", "既往史");
	i.pastMedicalHistory = StringTools.e(s, "既往史", "传染病史");
	i.infectiousHistory = StringTools.e(s, "传染病史", "个人生活史");
	i.lifeHistory = StringTools.e(s, "个人生活史", "家族史");
	i.familyHistory = StringTools.e(s, "家族史", "体 格 检 查");
	if (s.indexOf("初步诊断") > 0) {
	    i.somatoscopy = StringTools.e(s, "体 格 检 查", "初步诊断");
	    i.firstDiag = StringTools.pe(s.toString(), "初步诊断：\n(.*\n)签名");
	    i.confirmDiag = StringTools.pe(s.toString(), "确定诊断：\n(.*\n)签名");
	    try {
		i.supplyDiag = StringTools.pe(s.toString(), "补充诊断：\n(.*?\n)签名");
		i.correctDiag = StringTools.pe(s.toString(), "更正诊断：\n(.*?\n)签名");
	    } catch (Exception e) {
	    }
	} else
	    i.somatoscopy = StringTools.e(s, "体 格 检 查", "</text>");
	return i;
    }

    public static Operation extractOperationFromFile(File file) {
	StringBuilder s = extractText(file);
	Operation o = new Operation();
	o.operationDpt = StringTools.e(s, "科别", "床位号");
	o.bedNumber = StringTools.e(s, "床位号", "术前诊断");
	o.preDiagnosis = StringTools.e(s, "术前诊断：", "术中诊断");
	try {
	    o.operataionDiagnosis = StringTools.e(s, "术中诊断：", "手术名称");
	    o.operationTitle = StringTools.e(s, "手术名称：", "手术医师");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	o.doctor = StringTools.pe(s.toString(), "手术医师(.*\n)助手[姓名|名称]");
	o.assistant = StringTools.pe2(s.toString(), "(助手姓名|助手名称)：{0,1}(.*\n)手术时间");
	try {
	    o.beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "手术时间：", "开始"));
	    o.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "开始", "完毕"));
	} catch (Exception e) {
	}
	o.anaesthetic = StringTools.pe(s.toString(), "麻醉方法：(.*?)[血量|失血量]");
	o.bloodLoss = StringTools.pe(s.toString(), "血量：(.*?)[血量|输血量]");
	o.bloodTransfusion = StringTools.e(s, "输血量：", "手术经过");
	o.detail = StringTools.e(s, "手术经过、术中出现的情况及处理等：", "医师签名");
	o.sign = StringTools.e(s, "医师签名：", "日期");
	try {
	    o.recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "日期：", "</text>"));
	} catch (Exception e) {
	}
	o.sourceFile = file.getAbsolutePath();
	return o;
    }

    public static OutHospital extractOutFromFile(File file) {
	StringBuilder s = extractText(file);
	OutHospital o = new OutHospital();
	o.department = extractTag("科别", ".*?", file);
	try {
	    o.outDate = new SimpleDateFormat("yyyy年MM月dd日").parse(extractTag("出院时间[:|：]{0,1}", "\\d{4}年\\d{2}月\\d{2}日", file));// StringTools.e(s,
	} catch (Exception e) {
	}
	o.inDescriotion = StringTools.e(s, "入院时情况(包括检验异常结果等)：", "入院诊断");
	o.inDiagnosis = StringTools.e(s, "入院诊断：", "入院后诊疗经过");
	o.treatment = StringTools.e(s, "入院后诊疗经过：", "出院时情况");
	o.outDescription = StringTools.e(s, "出院时情况(包括主要化验结果等)：", "出院诊断");
	o.outDiagnosis = StringTools.e(s, "出院诊断：", "手术名称及伤口愈合情况");
	o.operationDesc = StringTools.e(s, "手术名称及伤口愈合情况：", "出院医嘱");
	o.dischargeOrder = StringTools.e(s, "出院医嘱：", "备注");
	o.note = StringTools.e(s, "备注：", "医生签名");
	o.sign = StringTools.e(s, "医生签名：", "</text>");
	o.sourceFile = file.getAbsolutePath();
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

    public static String extractTag(String obj, String match, File file) {
	Pattern tagPattern = Pattern.compile("<text>(\\s*" + obj + ")(:|：){0,1}</text>");
	Pattern contentPattern = Pattern.compile("<text>(" + match + ")</text>");
	Matcher tagMatcher = null;
	Matcher contentMatcher = null;
	String content = null;
	FileReader fr = null;
	BufferedReader br = null;
	try {
	    fr = new FileReader(file);
	    br = new BufferedReader(fr);
	    String ln;
	    boolean ready = false;
	    while ((ln = br.readLine()) != null) {
		tagMatcher = tagPattern.matcher(ln);
		if (tagMatcher.matches()) {
		    ready = true;
		} else if (ready) {
		    contentMatcher = contentPattern.matcher(ln);
		    if (contentMatcher.find()) {
			content = contentMatcher.group(1);
			break;
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
	return content.trim();
    }
}
