package cn.anthony.boot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.anthony.boot.domain.Patient.动眼神经;
import cn.anthony.boot.domain.Patient.反射;
import cn.anthony.boot.domain.Patient.听力;
import cn.anthony.boot.domain.Patient.头部反射;
import cn.anthony.boot.domain.Patient.痛触觉;
import cn.anthony.boot.domain.Patient.眼底;
import cn.anthony.boot.domain.Patient.视力;
import cn.anthony.boot.domain.Somatoscopy;
import cn.anthony.boot.domain.Somatoscopy.SpecialExamination;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.StringTools;

public class ParseUtil {
    public static String extractAllTag(File file, String tag) {
	StringBuilder sb = new StringBuilder();
	List<String> l = new ArrayList<String>();
	FileReader fr = null;
	BufferedReader br = null;
	try {
	    fr = new FileReader(file);
	    br = new BufferedReader(fr);
	    String ln;
	    boolean ready = false;
	    while ((ln = br.readLine()) != null) {
		// (<s[\\s\\S]*?/>)
		if (ln.startsWith("<tag>\n") || ln.endsWith("</tag>\n"))
		    l.add(ln + "\n");
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
	return sb.toString();
    }

    public static void main(String[] args) throws Exception {
	List<String> l = new ArrayList<String>();
	// l.add("一般情况：");
	// l.add("T:36.2℃ P:64次/分 R:18次/分 BP:110/70mmHg");
	// l.add("发育正常,营养良好，身高170厘米，体重88公斤。");
	// l.add("神志清晰，自主体位，面容无异常,与医生合作。");
	// l.add("皮肤粘膜：");
	// l.add("胸部及左下肢可见陈旧术痕，颜色正常，无水肿，弹性正常，无皮疹，无皮下结节及肿块，无蜘蛛痣，无出血点，无淤斑。");
	// l.add("浅表淋巴结:");
	// l.add("浅表淋巴结未触及肿大。");
	// l.add("头部五官：");
	// l.add("头颅：外形无畸形，无包块，毛发分布均匀，颅骨无压痛，无瘢痕。");
	// l.add("眼：眼睑无水肿，双眼球无突出，双眼球无震颤，无眼球活动受限，结膜无充血、无水肿，巩膜无黄染，角膜透明，角膜反射存在，双侧瞳孔等大等圆，直径：左3.0mm，右3.0mm，对光反射灵敏，辐辏反射正常。");
	// l.add("耳：耳廓外形正常,外耳道无分泌物，乳突无压痛，听力粗测正常");
	// l.add("鼻：鼻外形正常，鼻中隔居中，无鼻翼扇动，无分泌物，无出血，鼻腔通畅，鼻唇沟对称，上颌窦、筛窦、额窦、无压痛。");
	// l.add("口腔：口唇无苍白、无发绀，牙龈无肿胀、无出血，口腔粘膜完整，伸舌居中，无震颤，口腔无异味，无缺齿。");
	// l.add("咽：咽无充血，悬雍垂居中，扁桃体双侧无肿大，无脓性分泌物，发音清晰。");
	// l.add("颈 部：");
	// l.add("颈项运动自如，无颈抵抗，未见颈动脉异常搏动，未见颈静脉怒张，未及血管杂音，气管位置居中，甲状腺未触及肿大，质软，无压痛,未及震颤，未闻及血管杂音");
	// l.add("胸 部：");
	// l.add("胸廓：无畸形，运动对称，无胸骨压痛，无皮下气肿。");
	// l.add("肺：呼吸运动一致，语颤双侧对称，双肺叩诊清音，语音传导正常，双肺呼吸音清，未闻及明显干湿性啰音。无胸膜摩擦音。");
	// l.add("心：心前区无隆起，心尖搏动点位于左侧第5肋间锁骨中线内1cm,未见异常搏动,未触及震颤，未触及心包摩擦感，心率64次/分，心律齐，心音正常，P2&lt;A2，未闻及杂音，无心包摩擦音。");
	// l.add("周围血管征：未见异常血管征。");
	// l.add("腹 部：");
	// l.add("腹部平坦，腹式呼吸正常，未见肠胃蠕动波，未见腹壁静脉曲张，腹软，无肌紧张，无压痛及反跳痛，无腹部包块，腹部叩诊音鼓音，移动性浊音阴性，肠鸣音5次/分，未及血管杂音。");
	// l.add("肝（胆）：肝肋下未及。Murphy征阴性，肝颈静脉回流征阴性。肝浊音界存在，肝上界位于右锁骨中线第Ⅴ肋间。");
	// l.add("脾：脾肋下未及，脾区无叩痛。");
	// l.add("肾：双肾未及，季肋点无压痛，上输尿管点无压痛，中输尿管点无压痛，肋脊点无压痛，肋腰点无压痛，肾区无叩痛。");
	// l.add("外阴及肛门：");
	// l.add("肛门及外生殖器未见异常。");
	// l.add("脊 柱：");
	// l.add("见专科。");
	l.add("四肢（关节）：");
	l.add("见专科。");
	l.add("神经系统：");
	l.add("见专科。");
	l.add("专科情况：");
	// l.add("ddddd");
	// l.add("ggg");
	// System.out.println(StringTools.formatMap(getMap(l)));
	StringBuilder sb = new StringBuilder();
	for (String s : l)
	    sb.append(s + "\n");
	System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(extractSomatoscopy(sb.toString()))));
    }

    private static List<String> EXA_LIST = new ArrayList<String>();

    static {
	EXA_LIST.add("一般情况");
	EXA_LIST.add("皮肤粘膜");
	EXA_LIST.add("浅表淋巴结");
	EXA_LIST.add("头部五官");
	EXA_LIST.add("头颅");
	EXA_LIST.add("眼");
	EXA_LIST.add("耳");
	EXA_LIST.add("鼻");
	EXA_LIST.add("口腔");
	EXA_LIST.add("咽");
	EXA_LIST.add("颈    部");
	EXA_LIST.add("胸    部");
	EXA_LIST.add("胸廓");
	EXA_LIST.add("肺");
	EXA_LIST.add("心");
	EXA_LIST.add("周围血管征");
	EXA_LIST.add("腹    部");
	EXA_LIST.add("肝（胆）");
	EXA_LIST.add("脾");
	EXA_LIST.add("肾");
	EXA_LIST.add("外阴及肛门");
	EXA_LIST.add("脊    柱");
	EXA_LIST.add("四肢（关节）");
	EXA_LIST.add("神经系统");
	EXA_LIST.add("见专科。");
	EXA_LIST.add("专科情况");
    }

    public static Somatoscopy extractSomatoscopy(String s) {
	Somatoscopy so = new Somatoscopy();
	Map<String, String> m = getMap(StringTools.split(new StringBuilder(s), "\n"));
	so.general = m.get("一般情况");
	if (so.general != null) {
	    so.T = StringTools.pe(so.general, "T[:|：](.*?)℃");
	    so.P = StringTools.pe(so.general, "P[:|：](.*?)次/分");
	    so.R = StringTools.pe(so.general, "P[:|：](.*?)次/分");
	    so.BP = StringTools.pe(so.general, "BP[:|：](.*?)mmHg");
	}
	so.skin = m.get("皮肤粘膜");
	so.superficialLymph = m.get("浅表淋巴结");
	so.skull = m.get("头颅");
	so.eye = m.get("眼");
	so.ear = m.get("耳");
	so.node = m.get("鼻");
	so.mouse = m.get("口腔");
	so.throat = m.get("咽");
	so.neck = m.get("颈    部");
	so.thorax = m.get("胸廓");
	so.lung = m.get("肺");
	so.heart = m.get("心");
	so.bloodVessels = m.get("周围血管征");
	so.abdomen = m.get("腹    部");
	so.liver = m.get("肝（胆）");
	so.spleen = m.get("脾");
	so.kidney = m.get("肾");
	so.vulva = m.get("外阴及肛门");
	so.spine = m.get("脊    柱");
	so.limbs = m.get("四肢（关节）");
	so.nervousSystem = m.get("神经系统");
	try {
	    if (m.get("专科情况") != null)
		so.sExamination = extractSpecialExam(so, m.get("专科情况"));
	} catch (StringIndexOutOfBoundsException e) {
	    so.sExamination = new Somatoscopy.SpecialExamination();
	    so.sExamination.src = m.get("专科情况");
	}
	return so;
    }

    private static SpecialExamination extractSpecialExam(Somatoscopy so, String e) {
	SpecialExamination se = new Somatoscopy.SpecialExamination();
	StringBuilder s = new StringBuilder(e);
	se.高级皮层功能.神志 = StringTools.e(s, "神志", "精神状态");
	if (s.indexOf("性格/人格") > 0) {
	    se.高级皮层功能.精神状态 = StringTools.e(s, "精神状态", "性格/人格");
	    if (s.indexOf("头部") > 0)
		se.高级皮层功能.性格人格 = StringTools.e(s, "性格/人格", "头部");
	} else if (s.indexOf("头部") > 0) {
	    se.高级皮层功能.精神状态 = StringTools.e(s, "精神状态", "头部");
	    if (s.indexOf("语言") >= 0) {
		se.高级皮层功能.头部 = StringTools.e(s, "头部", "语言");
		se.高级皮层功能.语言 = StringTools.e(s, "语言", "脑膜刺激征");
	    } else
		se.高级皮层功能.头部 = StringTools.e(s, "头部", "脑膜刺激征");
	}
	se.高级皮层功能.脑膜刺激征 = StringTools.e(s, "脑膜刺激征", "颅神经");
	se.颅神经.嗅觉 = StringTools.e(s, "嗅觉", "Ⅱ：视力");
	String c = "\n";
	String[] ss;
	try {
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "Ⅱ：视力", "视野"), c);
	    se.颅神经.视力 = new 视力(ss[0], ss[7], ss[8], ss[9], ss[10], ss[11], ss[13],
		    ss[14], ss[15], ss[16], ss[17]);
	} catch (Exception ex) {
	}
	try {
	    se.颅神经.视野 = StringTools.e(s, "视野", "眼底");
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "视盘", "Ⅲ、Ⅳ、Ⅵ"), c);
	    se.颅神经.眼底 = new 眼底(ss[3], ss[4], ss[5], ss[7], ss[8], ss[9]);
	} catch (Exception ex) {
	}
	try {
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "Ⅲ、Ⅳ、Ⅵ", "眼球运动"), c);
	    se.颅神经.动眼神经 = new 动眼神经(ss[10], ss[11], ss[12], ss[13], ss[14], ss[15],
		    ss[16], ss[17], ss[18], ss[20], ss[21], ss[22], ss[23], ss[24], ss[25], ss[26], ss[27], ss[28]);
	} catch (Exception ex) {
	}
	if (s.indexOf("复视") >= 0) {
	    se.颅神经.眼球运动 = StringTools.e(s, "眼球运动", "复视");
	    se.颅神经.复视 = StringTools.e(s, "复视", "Ⅴ：感觉");
	} else
	    se.颅神经.眼球运动 = StringTools.e(s, "眼球运动", "Ⅴ：感觉");
	if (s.indexOf("第一支") >= 0 && s.indexOf("洋葱样皮样感觉障碍") > 0) {
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "第一支", "洋葱样皮样感觉障碍"), c);
	    if (s.indexOf("运动") > 0 && s.indexOf("运动") < s.indexOf("Ⅶ")) {
		se.颅神经.洋葱样皮样感觉障碍 = StringTools.e(s, "洋葱样皮样感觉障碍", "运动");
		try {
		    se.颅神经.痛触觉 = new 痛触觉(ss[3], ss[4], ss[5], ss[7], ss[8], ss[9]);
		} catch (Exception ex) {
		}
		if (s.indexOf("运动") > 0 && s.indexOf("反射") > 0) {
		    se.颅神经.运动 = StringTools.e(s, "运动", "反射");
		    // StringTools.e(s, "反射", "Ⅶ：检查");
		}
	    }
	}

	ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "Ⅶ：检查", "Ⅷ：听力"), c);
	try {
	    se.颅神经.头部反射 = new 头部反射(ss[0], ss[8], ss[9], ss[10], ss[11], ss[12], ss[13],
		    ss[14], ss[16], ss[17], ss[18], ss[19], ss[20], ss[21], ss[22]);
	} catch (Exception ex) {
	}
	try {
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "Ⅷ：听力", "Ⅸ、Ⅹ、Ⅺ"), c);
	    if (ss[0].indexOf("不合作") >= 0)
		se.颅神经.听力 = new 听力(ss[0], null, null, null, null);
	    else
		se.颅神经.听力 = new 听力(ss[0], ss[1], ss[2], ss[3], ss[4]);
	} catch (Exception ex) {
	}
	try {
	    ss = StringTools.splitToArray(StringTools.eWithoutTrim(s, "Ⅸ、Ⅹ、Ⅺ", "运动系统"), c);
	    if (!(ss[0].indexOf("不合作") >= 0)) {
		se.颅神经.发音 = ss[1];
		se.颅神经.咽反射 = ss[2];
		se.颅神经.味觉 = ss[3];
		se.颅神经.耸肩 = ss[4];
		se.颅神经.头侧转 = ss[5];
		se.颅神经.舌 = ss[6];
	    }
	} catch (Exception ex) {
	}
	if (s.indexOf("共济运动") > 0 && s.indexOf("感觉系统") > 0) {
	    se.运动系统 = StringTools.e(s, "运动系统", "共济运动");
	    se.共济运动 = StringTools.e(s, "共济运动", "感觉系统");
	    se.感觉系统 = StringTools.e(s, "感觉系统", "反射");
	} else
	    se.运动系统 = StringTools.e(s, "运动系统", "反射");
	String t = null;
	if (s.indexOf("自主神经与内分泌系统") > 0) {
	    t = StringTools.eWithoutTrim(s, "腹壁反射", "自主神经与内分泌系统");
	    se.自主神经与内分泌系统 = s.substring("自主神经与内分泌系统".length());
	} else
	    t = s.substring("腹壁反射".length());
	List<String> l = StringTools.split(new StringBuilder(t), c);
	// 没有提睾反射的情况
	try {
	    if (l.size() > 49) {
	    if (t.indexOf("提睾反射") > 0)
		if (l.size() >= 69)
		se.反射 = new 反射(l.get(0), l.get(5), l.get(6), l.get(8), l.get(9),
			l.get(11), l.get(12), l.get(17), l.get(18), l.get(20), l.get(21), l.get(32), l.get(33),
			l.get(34), l.get(35), l.get(36), l.get(37), l.get(38), l.get(40), l.get(41), l.get(42),
			l.get(43), l.get(44), l.get(45), l.get(46), l.get(56), l.get(57), l.get(58), l.get(59),
			l.get(60), l.get(61), l.get(63), l.get(64), l.get(65), l.get(66), l.get(67), l.get(68));
		else
		    se.反射 = new 反射(l.get(0), l.get(5), l.get(6), l.get(8), l.get(9), l.get(11), l.get(12), null, null,
			    l.get(18), l.get(19), l.get(30), l.get(31), l.get(32), l.get(33), l.get(34), l.get(35),
			    l.get(36), l.get(38), l.get(39), l.get(40), l.get(41), l.get(42), l.get(43), l.get(44),
			    l.get(54), l.get(55), l.get(56), l.get(57), l.get(58), l.get(59), l.get(61), l.get(62),
			    l.get(63), l.get(64), l.get(65), l.get(66));
	    else if (t.indexOf("肛门反射") > 0)
		se.反射 = new 反射(l.get(0), l.get(5), l.get(6), l.get(8), l.get(9),
			l.get(11), l.get(12), null, null, l.get(18), l.get(19), l.get(30), l.get(31), l.get(32),
			l.get(33), l.get(34), l.get(35), l.get(36), l.get(38), l.get(39), l.get(40), l.get(41),
			l.get(42), l.get(43), l.get(44), l.get(54), l.get(55), l.get(56), l.get(57), l.get(58),
			l.get(59), l.get(61), l.get(62), l.get(63), l.get(64), l.get(65), l.get(66));
	    else
		se.反射 = new 反射(l.get(0), l.get(5), l.get(6), l.get(8), l.get(9),
			l.get(11), l.get(12), null, null, null, null, l.get(20), l.get(21), l.get(22), l.get(23),
			l.get(24), l.get(25), l.get(26), l.get(28), l.get(29), l.get(30), l.get(31), l.get(32),
			l.get(33), l.get(34), l.get(44), l.get(45), l.get(46), l.get(47), l.get(48), l.get(49),
			l.get(51), l.get(52), l.get(53), l.get(54), l.get(55), l.get(56));
	    }
	} catch (Exception ex) {
	    System.out.println(t);
	    // System.out.println(l);
	    ex.printStackTrace();
	}

	return se;
    }

    public static Map<String, String> getMap(List<String> l) {
	Map<String, String> m = new TreeMap<String, String>();
	String t;
	for (int i = 0; i < l.size(); i++) {
	    String s = l.get(i);
	    StringBuilder sb = new StringBuilder();
	    String key = StringTools.pe(s, "^(.*?)[:|：]");
	    if (key != null && EXA_LIST.contains(key)) {
		t = s.substring(s.indexOf(key) + key.length());
		sb.append(t.startsWith(":") || t.startsWith("：") ? t.substring(1) : t);
		String s2 = null, k2 = null;
		while (i < l.size() - 1) {
		    i++;
		    s2 = l.get(i);
		    k2 = StringTools.pe(s2, "^(.*?)[:|：]");
		    if (k2 != null && EXA_LIST.contains(k2)) {
			i--;
			break;
		    } else
			sb.append("\n" + s2);
		}
	    } else {
		sb.append(s + "\n");
	    }
	    if (key != null)
		m.put(key, sb.toString());
	}
	return m;
    }
}
