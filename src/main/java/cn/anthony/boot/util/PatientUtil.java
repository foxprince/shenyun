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

import org.apache.commons.lang3.time.DateUtils;

import cn.anthony.boot.domain.Diag;
import cn.anthony.boot.domain.FrontPage;
import cn.anthony.boot.domain.InHospital;
import cn.anthony.boot.domain.Operation;
import cn.anthony.boot.domain.OutHospital;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.Somatoscopy;
import cn.anthony.util.FileTools;
import cn.anthony.util.ParseUtil;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.SAXUtil;
import cn.anthony.util.StringTools;

public class PatientUtil {
    public static void main(String[] args) throws ParseException {
	File file = new File("E:\\project\\神云系统\\KYBLSJ201405-201412\\KYBLSJ\\KYBLSJ_08\\出院记录_20140813\\DischargeSummary_000412730700_1.xml");
	// System.out.println(extractAllText(file).toString());
	OutHospital in = (extractOutFromFile(file));
	System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(in)));
	// System.out.println("==================================================");
	// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(in.somatoscopy)));
	// System.out.println("==================================================");
	// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(in.somatoscopy.sExamination)));
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
	FrontPage f = extractFrontPage(file);
	f.operationDetail = "手术、操作及大型设备检查编码\n" + StringTools.e(s, "手术、操作及大型设备检查编码", "颅脑损伤患者昏迷时间");
	p.addFront(f);
	return p;
    }

    public static FrontPage extractFrontPage(File file) {
	FrontPage f = new FrontPage();
	RefactorUtil.setObjectValue(f, SAXUtil.getFrontPageMap(file));
	f.sourceFile = file.getAbsolutePath();
	return f;
    }

    public static InHospital extractInFromFile(File file) {
	StringBuilder s = extractText(file);
	InHospital i = new InHospital();
	i.sourceFile = file.getAbsolutePath();
	i.admissionDept = extractTag("科室", ".*?", file);
	i.admissionNo = extractPIdTag(file);
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
	i.pastMedicalHistory = StringTools.pe(s.toString(), "既往史([\\s\\S]*?)(传染病史|个人生活史|个人史)");
	if (s.indexOf("传染病史") >= 0)
	    i.infectiousHistory = StringTools.pe(s.toString(), "传染病史([\\s\\S]*?)(个人生活史|个人史)");
	i.lifeHistory = StringTools.pe2(s.toString(), "(个人生活史|个人史)([\\s\\S]*?)家族史");
	i.familyHistory = StringTools.e(s, "家族史", "体 格 检 查");
	i.somatoscopy = ParseUtil.extractSomatoscopy(StringTools.e(s, "体 格 检 查", "辅助检查"));
	if (s.indexOf("初步诊断") >= 0) {
	    i.auxiliaryExamination = StringTools.e(s, "辅助检查", "初步诊断");
	    Diag d = new Diag("初步诊断");
	    d.detail = StringTools.e(s, "初步诊断", "签名");
	    d.signature = StringTools.e(s, "签名", "时间");
	    try {
		d.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
	    } catch (ParseException e) {
	    }
	    i.addDiag(d);
	} else {
	    i.auxiliaryExamination = StringTools.e(s, "辅助检查", "</text>");
	}
	if (s.indexOf("确定诊断") > 0) {
	    Diag d = new Diag("确定诊断");
	    d.detail = StringTools.e(s, "确定诊断", "签名");
	    d.signature = StringTools.e(s, "签名", "时间");
	    try {
		d.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
	    } catch (ParseException e) {
	    }
	    i.addDiag(d);
	}
	while (s.indexOf("补充诊断") >= 0) {
	    Diag diag = new Diag("补充诊断");
	    diag.detail = StringTools.e(s, "补充诊断", "签名");
	    diag.signature = StringTools.e(s, "签名", "时间");
	    try {
		diag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
	    } catch (ParseException e) {
	    }
	    i.addDiag(diag);
	}
	if (s.indexOf("更正诊断") > 0) {
	    Diag d = new Diag("更正诊断");
	    d.detail = StringTools.e(s, "更正诊断", "签名");
	    d.signature = StringTools.e(s, "签名", "时间");
	    try {
		d.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
	    } catch (ParseException e) {
	    }
	    i.addDiag(d);
	}
	return i;
    }

    private static Somatoscopy extractSomatoscopy(String s) {
	Somatoscopy so = new Somatoscopy();
	so.T = StringTools.pe(s, "T[:|：](.*?)℃");
	so.P = StringTools.pe(s, "P[:|：](.*?)次/分");
	so.R = StringTools.pe(s, "P[:|：](.*?)次/分");
	so.BP = StringTools.pe(s, "BP[:|：](.*?)mmHg");
	StringBuilder b = new StringBuilder(s);
	so.general = StringTools.e(b, "mmHg", "皮肤粘膜");
	so.skin = StringTools.e(b, "皮肤粘膜", "浅表淋巴结");
	so.superficialLymph = StringTools.e(b, "浅表淋巴结", "头部五官");
	so.skull = StringTools.e(b, "头颅", "眼");
	so.eye = StringTools.e(b, "眼", "耳");
	so.ear = StringTools.e(b, "耳", "鼻");
	so.node = StringTools.e(b, "鼻", "口腔");
	so.mouse = StringTools.e(b, "口腔", "咽");
	so.throat = StringTools.e(b, "咽", "颈    部");
	so.neck = StringTools.e(b, "颈    部", "胸    部");
	so.thorax = StringTools.e(b, "胸廓", "肺");
	so.lung = StringTools.e(b, "肺", "心");
	so.heart = StringTools.e(b, "心", "周围血管征");
	so.bloodVessels = StringTools.e(b, "周围血管征", "腹    部");
	so.abdomen = StringTools.e(b, "腹    部", "肝（胆）");
	try {
	    so.liver = StringTools.e(b, "肝（胆）", "脾");
	} catch (Exception e) {
	    System.out.println("ERRRRRRRRRR:脾");
	}
	so.spleen = StringTools.e(b, "脾", "肾");
	so.kidney = StringTools.e(b, "肾", "外阴及肛门");
	so.vulva = StringTools.e(b, "外阴及肛门", "脊    柱");
	so.spine = StringTools.e(b, "脊    柱", "四肢（关节）");
	so.limbs = StringTools.e(b, "四肢（关节）", "神经系统");
	if (b.indexOf("专科情况") > 0) {
	    so.nervousSystem = StringTools.e(b, "神经系统", "专科情况");
	    so.sExamination = StringTools.e(b, "专科情况", "辅助检查");
	} else
	    so.nervousSystem = StringTools.e(b, "神经系统", "辅助检查");
	return so;
    }

    public static Operation extractOperationFromFile(File file) {
	StringBuilder s = extractText(file);
	Operation o = new Operation();
	o.operationDpt = StringTools.e(s, "科别", "床位号");
	o.bedNumber = StringTools.e(s, "床位号", "术前诊断");
	o.preDiagnosis = StringTools.e(s, "术前诊断", "术中诊断");
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
		o.endTime = DateUtils.parseDate(StringTools.pe(StringTools.e(s, "开始", "完毕"), "(\\d{2}时\\d{2}分)"), "yyyy-MM-dd HH:mm", "HH时mm分");
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
	    o.bloodTransfusion = StringTools.e(s, "输血量", "手术经过");
	} else
	    o.anaesthetic = StringTools.e(s, "麻醉方法", "手术经过、术中出现的情况及处理等");
	o.detail = StringTools.pe(s.toString(), "手术经过、术中出现的情况及处理等：([\\s\\S]*?)(医师签名|签名)");
	o.sign = StringTools.pe(s.toString(), "签名：(.*?)(记录日期|日期)");
	try {
	    o.recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "日期", "</text>"));
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
	o.inDescriotion = StringTools.e(s, "入院时情况(包括检验异常结果等)", "入院诊断");
	o.inDiagnosis = StringTools.e(s, "入院诊断", "入院后诊疗经过");
	o.treatment = StringTools.e(s, "入院后诊疗经过", "出院时情况");
	o.outDescription = StringTools.e(s, "出院时情况(包括主要化验结果等)", "出院诊断");
	o.outDiagnosis = StringTools.e(s, "出院诊断", "手术名称及伤口愈合情况");
	o.operationDesc = StringTools.e(s, "手术名称及伤口愈合情况", "出院医嘱");
	o.dischargeOrder = StringTools.pe(s.toString(), "出院医嘱([\\s\\S]*?)(备注|医生签名)");
	if (s.indexOf("备注") >= 0)
	    o.note = StringTools.e(s, "备注", "医生签名");
	o.sign = StringTools.e(s, "医生签名", "</text>");
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
}
