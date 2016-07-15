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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	File dir = new File("E:\\project\\神云系统\\pacs");
	for(File f: getPacsFiles(dir,"184470"))
	    System.out.println(f.getName());
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

    public static List<File> getPacsFiles(File dir,String patientNo) {
	try {
	    return processPacs(dir, (File f) -> {
	        return f.isDirectory() && f.getName().indexOf("-"+patientNo+"-")>0;
	    });
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
    private static List<File> processPacs(File dir, FileFilter filter) {
	List<File> l = new ArrayList<File>();
	File[] fs = dir.listFiles(filter);
	for (int i = 0; i < fs.length; i++) {
	    if (fs[i].isDirectory()) {
		l.addAll(Arrays.asList(fs[i].listFiles((File pathname)-> {
			return pathname.getName().endsWith(".jpg");
		    })));
	    }
	}
	return l;
    }

    public static void process(File dir, FileFilter filter) throws Exception {
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
    public static StringBuilder extractText(File f, boolean filterBlank) {
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
	    if (filterBlank && StringTools.checkNull(s) != null)
		sb.append(s + "\n");
	    else
		sb.append(s + "\n");
	// System.out.println(sb.toString());
	return sb;
    }

    public static Patient extractPatientFromFile(File file) {
	StringBuilder s = extractText(file, true);
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
		    l.add(new SevereDetail(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			    i < ss.length ? ss[i++] : ""));
		i += 3;
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return l;
    }

    private static List<OutDiag> extractDiags(FrontPage f, StringBuilder s) {
	List<OutDiag> l = new ArrayList<OutDiag>();
	String t = StringTools.e(s, "病情", "入院病情：1.有，2.临床未确定，3.情况不明，4.无");
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
		    l.add(new OutDiag(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			    i < ss.length ? ss[i++] : ""));
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
		l.add(new OperationDetail(i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "", i < ss.length ? ss[i++] : "",
			i < ss.length ? ss[i++] : ""));
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

    public static InHospital extractInFromFile(File file) {
	StringBuilder s = extractText(file, false);
	InHospital i = new InHospital();
	i.srcFile = file.getAbsolutePath();
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
	    i.firstDiag.detail = StringTools.e(s, "初步诊断", "签名");
	    i.firstDiag.signature = StringTools.e(s, "签名", "时间");
	    try {
		i.firstDiag.diagDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringTools.e(s, "时间", "\n"));
	    } catch (ParseException e) {
	    }
	} else {
	    i.auxiliaryExamination = StringTools.e(s, "辅助检查", "</text>");
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
	    } catch (ParseException e) {
	    }
	}
	return i;
    }

    public static Operation extractOperationFromFile(File file) {
	StringBuilder s = extractText(file, true);
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
		o.endTime = DateUtils.parseDate(StringTools.pe(StringTools.e(s, "开始", "完毕"), "(\\d{2}时\\d{2}分)"),
			"yyyy-MM-dd HH:mm", "HH时mm分");
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
	    o.recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTools.e(s, "日期", "</text>"));
	} catch (Exception e) {
	}
	o.srcFile = file.getAbsolutePath();
	return o;
    }

    public static OutHospital extractOutFromFile(File file) {
	StringBuilder s = extractText(file, true);
	OutHospital o = new OutHospital();
	o.department = extractTag("科别", ".*?", file);
	try {
	    o.outDate = new SimpleDateFormat("yyyy年MM月dd日")
		    .parse(extractTag("出院时间[:|：]{0,1}", "\\d{4}年\\d{2}月\\d{2}日", file));// StringTools.e(s,
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
