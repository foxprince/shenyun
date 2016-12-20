package cn.anthony.boot.domain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import cn.anthony.boot.util.Constant;
import cn.anthony.boot.util.PatientUtil;
import cn.anthony.util.DateUtil;
import cn.anthony.util.StringTools;
import lombok.Data;

@QueryEntity
@Document(collection = "patient")
@Data
public class Patient extends GenericNoSQLEntity {
	private static final long serialVersionUID = -9199964027188332358L;
	public String pId;/* 病案号 */
	public String name;
	public Integer age;
	public String sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date dateOfBirth;
	public String certNo;/* 身份证号码 */
	// 籍贯
	public String nativeplace;
	// 民族
	public String nationality;
	// 户口地址
	public String registeredaddress;
	// 国籍
	public String country;
	// 出生地
	public String birthplace;
	// 数据来源
	public String source;
	// 扩展属性
	public Map<String, ExtendObject> extendMap = new TreeMap<String, ExtendObject>();

	public List<File> getPacsFiles() {
		return PatientUtil.getPacsFiles(new File(Constant.PACS_DIR), getpId());
	}

	/**
	 * 只返回 子目录及文件名
	 * 
	 * @return
	 */
	public List<String> getPacsFilesName() {
		List<String> l = new ArrayList<String>();
		for (File f : getPacsFiles())
			l.add(f.getAbsolutePath().substring(f.getAbsolutePath().indexOf("dicom") + 5));
		return l;
	}

	public List<FrontPage> frontRecords = new ArrayList<FrontPage>();// 首页纪录
	public List<InHospital> inRecords = new ArrayList<InHospital>();/* 入院纪录 */
	public List<Operation> operations = new ArrayList<Operation>();/* 手术纪录 */
	public List<OutHospital> outRecords = new ArrayList<OutHospital>();/* 出院纪录 */
	public List<Remark> remarks = new ArrayList<Remark>();/* 备注 */
	public List<Remark> getRemarks() {
		Collections.reverse(remarks);
		return remarks;
	}
	transient public Diag firstDiag;
	transient public OutDiag outDiag;
	transient public SevereDetail severeDetail;
	transient public OperationDetail operationDetail;
	transient public Somatoscopy somatoscopy;
	transient public Somatoscopy.SpecialExamination specialExamination;
	transient public 高级皮层功能 高级皮层功能;
	transient public 颅神经 颅神经;
	transient public 反射 反射;
	transient public 视力 视力;
	transient public 眼底 眼底;
	transient public 动眼神经 动眼神经;
	transient public 痛触觉 痛触觉;
	transient public 头部反射 头部反射;
	transient public 听力 听力;

	@Data
	public static class OperationDetail {
		public String code, checkDate, title, chief, assistant1, assistant2, oclass, nnis, qkyh, mzfs, asa, mzDoc;

		public OperationDetail(String code, String checkDate, String title, String chief, String assistant1,
				String assistant2, String oclass, String nnis, String qkyh, String mzfs, String asa, String mzDoc) {
			super();
			this.code = code;
			this.checkDate = checkDate;
			this.title = title;
			this.chief = chief;
			this.assistant1 = assistant1;
			this.assistant2 = assistant2;
			this.oclass = oclass;
			this.nnis = nnis;
			this.qkyh = qkyh;
			this.mzfs = mzfs;
			this.asa = asa;
			this.mzDoc = mzDoc;
		}

		public OperationDetail() {
		}

	}

	@Data
	public static class SevereDetail {
		public String name;
		Date inTime, outTime;
		Long minutes;

		public SevereDetail(String name, String inTime, String outTime) {
			super();
			this.name = name;
			this.inTime = DateUtil.parse(inTime);
			this.outTime = DateUtil.parse(outTime);
			this.minutes = (this.outTime.getTime() - this.inTime.getTime()) / (60 * 1000);
		}

		public String getInTimeDesc() {
			return DateUtil.format(inTime, DateUtil.TIME_FORMAT);
		}

		public String getOutTimeDesc() {
			return DateUtil.format(outTime, DateUtil.TIME_FORMAT);
		}

		public String getDuration() {
			return DateUtil.getDHM(minutes);
		}

		public SevereDetail() {
			super();
		}
	}

	@Data
	public static class OutDiag {
		public String type;// main other
		public String diag, code, have;

		public OutDiag(String diag, String code, String have) {
			super();
			this.diag = diag;
			this.code = code;
			this.have = have;
			if (diag.startsWith("主要"))
				type = "main";
			else
				type = "other";
		}

		public OutDiag() {
			// TODO Auto-generated constructor stub
		}

		public String getTypeDesc() {
			if (type.equals("main"))
				return "主要诊断";
			else
				return "其他诊断";
		}
	}

	@Data
	public static class Diag {
		public Diag() {
		}

		public Diag(String s) {
			this.type = s;
		}

		public String type;// 初步诊断 确定诊断 补充诊断 更正
		public String detail;
		public Date diagDate;
		public String signature;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			// sb.append(type + ":");
			sb.append(StringTools.printString(detail) + "<br/>");
			if (diagDate != null)
				sb.append("时间：" + DateUtil.format(diagDate, "yyyy年MM月dd日"));
			sb.append("<br/>签名：" + StringTools.printString(signature));
			return sb.toString();
		}
	}

	public Patient(String pId, String name, Integer age, String sex, Date dateOfBirth, String certNo,
			List<FrontPage> frontRecords, List<InHospital> inRecords, List<Operation> operations,
			List<OutHospital> outRecords, boolean active, String activeDesc) {
		super();
		this.pId = pId;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.dateOfBirth = dateOfBirth;
		this.certNo = certNo;
		this.frontRecords = frontRecords;
		this.inRecords = inRecords;
		this.operations = operations;
		this.outRecords = outRecords;
		this.active = active;
		this.activeDesc = activeDesc;
	}

	@Transient // 此注解用于不映射到数据库
	private boolean active = true;
	@JsonIgnore
	@Transient
	transient private String activeDesc;

	public Patient() {
		super();
	}

	public Patient(String pId) {
		super();
		this.pId = pId;
	}

	public String getActiveDesc() {
		if (active)
			return "开";
		else
			return "关";
	}

	public String getSexDesc() {
		if (sex.equalsIgnoreCase("1"))
			return "男";
		else
			return "女";
	}

	public String getFormatDateOfBirth() {
		if (dateOfBirth != null)
			return new SimpleDateFormat("yyyy年MM月dd日").format(dateOfBirth);
		else
			return "";
	}

	public String getActualAge() {
		Calendar cal = Calendar.getInstance();
		if (dateOfBirth != null)
			cal.setTime(dateOfBirth);
		else
			return "";
		return "" + (Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR));
	}

	public void addFront(FrontPage in) {
		if (frontRecords == null)
			frontRecords = new LinkedList<FrontPage>();
		frontRecords.add(in);
	}

	public void addIn(InHospital in) {
		if (inRecords == null)
			inRecords = new LinkedList<InHospital>();
		inRecords.add(in);
	}

	public void addOut(OutHospital in) {
		if (outRecords == null)
			outRecords = new LinkedList<OutHospital>();
		outRecords.add(in);
	}

	public void addOperation(Operation in) {
		if (operations == null)
			operations = new LinkedList<Operation>();
		operations.add(in);
	}
	
	public void addRemark(Remark in) {
		if (remarks == null)
			remarks = new LinkedList<Remark>();
		remarks.add(in);
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	@Data
	public static class 高级皮层功能 {
		public String 神志, 精神状态, 性格人格, 头部, 语言, 脑膜刺激征;
	}

	@Data
	public static class 颅神经 {
		public String 嗅觉;
		public 视力 视力;
		public String 视野;
		public 眼底 眼底;
		public 动眼神经 动眼神经;
		public String 眼球运动, 复视;
		public 痛触觉 痛触觉;
		public String 洋葱样皮样感觉障碍, 运动;
		public 头部反射 头部反射;
		public 听力 听力;
		public String 发音, 咽反射, 味觉, 耸肩, 头侧转, 舌;
	}

	@Data
	public static class 视力 {
		public String 粗测;
		public String 视力左侧;
		public String 指数距离左侧;
		public String 指动距离左侧;
		public String 光感左侧;
		public String 失明左侧;
		public String 视力右侧;
		public String 指数距离右侧;
		public String 指动距离右侧;
		public String 光感右侧;
		public String 失明右侧;

		public 视力(String 粗测, String 视力左侧, String 指数距离左侧, String 指动距离左侧, String 光感左侧, String 失明左侧, String 视力右侧,
				String 指数距离右侧, String 指动距离右侧, String 光感右侧, String 失明右侧) {
			super();
			this.粗测 = 粗测;
			this.视力左侧 = 视力左侧;
			this.指数距离左侧 = 指数距离左侧;
			this.指动距离左侧 = 指动距离左侧;
			this.光感左侧 = 光感左侧;
			this.失明左侧 = 失明左侧;
			this.视力右侧 = 视力右侧;
			this.指数距离右侧 = 指数距离右侧;
			this.指动距离右侧 = 指动距离右侧;
			this.光感右侧 = 光感右侧;
			this.失明右侧 = 失明右侧;
		}

		public 视力() {
			super();
		}
	}

	@Data
	public static class 眼底 {
		public String 视盘左侧;
		public String 血管左侧;
		public String 视网膜左侧;
		public String 视盘右侧;
		public String 血管右侧;
		public String 视网膜右侧;

		public 眼底(String 视盘左侧, String 血管左侧, String 视网膜左侧, String 视盘右侧, String 血管右侧, String 视网膜右侧) {
			super();
			this.视盘左侧 = 视盘左侧;
			this.血管左侧 = 血管左侧;
			this.视网膜左侧 = 视网膜左侧;
			this.视盘右侧 = 视盘右侧;
			this.血管右侧 = 血管右侧;
			this.视网膜右侧 = 视网膜右侧;
		}

		public 眼底() {
			super();
		}
	}

	@Data
	public static class 动眼神经 {
		public String 眼睑下垂左侧;
		public String 眼球突出左侧;
		public String 眼球下陷左侧;
		public String 瞳孔大小左侧;
		public String 瞳孔形状左侧;
		public String 直接对光反射左侧;
		public String 间接对光反射左侧;
		public String 调节反射左侧;
		public String 辐辏反射左侧;
		public String 眼睑下垂右侧;
		public String 眼球突出右侧;
		public String 眼球下陷右侧;
		public String 瞳孔大小右侧;
		public String 瞳孔形状右侧;
		public String 直接对光反射右侧;
		public String 间接对光反射右侧;
		public String 调节反射右侧;
		public String 辐辏反射右侧;

		public 动眼神经(String 眼睑下垂左侧, String 眼球突出左侧, String 眼球下陷左侧, String 瞳孔大小左侧, String 瞳孔形状左侧, String 直接对光反射左侧,
				String 间接对光反射左侧, String 调节反射左侧, String 辐辏反射左侧, String 眼睑下垂右侧, String 眼球突出右侧, String 眼球下陷右侧,
				String 瞳孔大小右侧, String 瞳孔形状右侧, String 直接对光反射右侧, String 间接对光反射右侧, String 调节反射右侧, String 辐辏反射右侧) {
			super();
			this.眼睑下垂左侧 = 眼睑下垂左侧;
			this.眼球突出左侧 = 眼球突出左侧;
			this.眼球下陷左侧 = 眼球下陷左侧;
			this.瞳孔大小左侧 = 瞳孔大小左侧;
			this.瞳孔形状左侧 = 瞳孔形状左侧;
			this.直接对光反射左侧 = 直接对光反射左侧;
			this.间接对光反射左侧 = 间接对光反射左侧;
			this.调节反射左侧 = 调节反射左侧;
			this.辐辏反射左侧 = 辐辏反射左侧;
			this.眼睑下垂右侧 = 眼睑下垂右侧;
			this.眼球突出右侧 = 眼球突出右侧;
			this.眼球下陷右侧 = 眼球下陷右侧;
			this.瞳孔大小右侧 = 瞳孔大小右侧;
			this.瞳孔形状右侧 = 瞳孔形状右侧;
			this.直接对光反射右侧 = 直接对光反射右侧;
			this.间接对光反射右侧 = 间接对光反射右侧;
			this.调节反射右侧 = 调节反射右侧;
			this.辐辏反射右侧 = 辐辏反射右侧;
		}

		public 动眼神经() {
			super();
		}
	}

	@Data
	public static class 痛触觉 {
		public String 第一支左侧;
		public String 第二支左侧;
		public String 第三支左侧;
		public String 第一支右侧;
		public String 第二支右侧;
		public String 第三支右侧;

		public 痛触觉(String 第一支左侧, String 第二支左侧, String 第三支左侧, String 第一支右侧, String 第二支右侧, String 第三支右侧) {
			super();
			this.第一支左侧 = 第一支左侧;
			this.第二支左侧 = 第二支左侧;
			this.第三支左侧 = 第三支左侧;
			this.第一支右侧 = 第一支右侧;
			this.第二支右侧 = 第二支右侧;
			this.第三支右侧 = 第三支右侧;
		}

		public 痛触觉() {
			super();
		}
	}

	@Data
	public static class 头部反射 {
		public String 粗测;
		public String 皱额左侧;
		public String 闭目左侧;
		public String 鼻唇沟左侧;
		public String 口角偏左侧;
		public String 鼓腮左侧;
		public String 面肌抽搐左侧;
		public String 味觉左侧;
		public String 皱额右侧;
		public String 闭目右侧;
		public String 鼻唇沟右侧;
		public String 口角偏右侧;
		public String 鼓腮右侧;
		public String 面肌抽搐右侧;
		public String 味觉右侧;

		public 头部反射(String 粗测, String 皱额左侧, String 闭目左侧, String 鼻唇沟左侧, String 口角偏左侧, String 鼓腮左侧, String 面肌抽搐左侧,
				String 味觉左侧, String 皱额右侧, String 闭目右侧, String 鼻唇沟右侧, String 口角偏右侧, String 鼓腮右侧, String 面肌抽搐右侧,
				String 味觉右侧) {
			super();
			this.粗测 = 粗测;
			this.皱额左侧 = 皱额左侧;
			this.闭目左侧 = 闭目左侧;
			this.鼻唇沟左侧 = 鼻唇沟左侧;
			this.口角偏左侧 = 口角偏左侧;
			this.鼓腮左侧 = 鼓腮左侧;
			this.面肌抽搐左侧 = 面肌抽搐左侧;
			this.味觉左侧 = 味觉左侧;
			this.皱额右侧 = 皱额右侧;
			this.闭目右侧 = 闭目右侧;
			this.鼻唇沟右侧 = 鼻唇沟右侧;
			this.口角偏右侧 = 口角偏右侧;
			this.鼓腮右侧 = 鼓腮右侧;
			this.面肌抽搐右侧 = 面肌抽搐右侧;
			this.味觉右侧 = 味觉右侧;
		}

		public 头部反射() {
			super();
		}
	}

	@Data
	public static class 听力 {
		public String 合作;
		public String schwaban试验;
		public String weber试验;
		public String rinne试验;
		public String 眼球震颤;

		public 听力(String 合作, String schwaban试验, String weber试验, String rinne试验, String 眼球震颤) {
			super();
			this.合作 = 合作;
			this.schwaban试验 = schwaban试验;
			this.weber试验 = weber试验;
			this.rinne试验 = rinne试验;
			this.眼球震颤 = 眼球震颤;
		}

		public 听力() {
			super();
		}
	}

	@Data
	public static class 反射 {
		public String 腹壁反射;
		public String 左侧腹壁反射上;
		public String 右侧腹壁反射上;
		public String 左侧腹壁反射中;
		public String 右侧腹壁反射中;
		public String 左侧腹壁反射下;
		public String 右侧腹壁反射下;
		public String 提睾反射左侧;
		public String 提睾反射右侧;
		public String 肛门反射左侧;
		public String 肛门反射右侧;
		public String 肱二头肌左侧;
		public String 肱三头肌左侧;
		public String 桡骨膜左侧;
		public String 膝反射左侧;
		public String 踝反射左侧;
		public String 髌痉挛左侧;
		public String 踝痉挛左侧;
		public String 肱二头肌右侧;
		public String 肱三头肌右侧;
		public String 桡骨膜右侧;
		public String 膝反射右侧;
		public String 踝反射右侧;
		public String 髌痉挛右侧;
		public String 踝痉挛右侧;
		public String hoffmann左侧;
		public String babinski左侧;
		public String chaddock左侧;
		public String oppenheim左侧;
		public String gordon左侧;
		public String 其他左侧;
		public String hoffmann右侧;
		public String babinski右侧;
		public String chaddock右侧;
		public String oppenheim右侧;
		public String gordon右侧;
		public String 其他右侧;

		public 反射() {
		}

		public 反射(String 腹壁反射, String 左侧腹壁反射上, String 右侧腹壁反射上, String 左侧腹壁反射中, String 右侧腹壁反射中, String 左侧腹壁反射下,
				String 右侧腹壁反射下, String 提睾反射左侧, String 提睾反射右侧, String 肛门反射左侧, String 肛门反射右侧, String 肱二头肌左侧,
				String 肱三头肌左侧, String 桡骨膜左侧, String 膝反射左侧, String 踝反射左侧, String 髌痉挛左侧, String 踝痉挛左侧, String 肱二头肌右侧,
				String 肱三头肌右侧, String 桡骨膜右侧, String 膝反射右侧, String 踝反射右侧, String 髌痉挛右侧, String 踝痉挛右侧, String hoffmann左侧,
				String babinski左侧, String chaddock左侧, String oppenheim左侧, String gordon左侧, String 其他左侧,
				String hoffmann右侧, String babinski右侧, String chaddock右侧, String oppenheim右侧, String gordon右侧,
				String 其他右侧) {
			super();
			this.腹壁反射 = 腹壁反射;
			this.左侧腹壁反射上 = 左侧腹壁反射上;
			this.右侧腹壁反射上 = 右侧腹壁反射上;
			this.左侧腹壁反射中 = 左侧腹壁反射中;
			this.右侧腹壁反射中 = 右侧腹壁反射中;
			this.左侧腹壁反射下 = 左侧腹壁反射下;
			this.右侧腹壁反射下 = 右侧腹壁反射下;
			this.提睾反射左侧 = 提睾反射左侧;
			this.提睾反射右侧 = 提睾反射右侧;
			this.肛门反射左侧 = 肛门反射左侧;
			this.肛门反射右侧 = 肛门反射右侧;
			this.肱二头肌左侧 = 肱二头肌左侧;
			this.肱三头肌左侧 = 肱三头肌左侧;
			this.桡骨膜左侧 = 桡骨膜左侧;
			this.膝反射左侧 = 膝反射左侧;
			this.踝反射左侧 = 踝反射左侧;
			this.髌痉挛左侧 = 髌痉挛左侧;
			this.踝痉挛左侧 = 踝痉挛左侧;
			this.肱二头肌右侧 = 肱二头肌右侧;
			this.肱三头肌右侧 = 肱三头肌右侧;
			this.桡骨膜右侧 = 桡骨膜右侧;
			this.膝反射右侧 = 膝反射右侧;
			this.踝反射右侧 = 踝反射右侧;
			this.髌痉挛右侧 = 髌痉挛右侧;
			this.踝痉挛右侧 = 踝痉挛右侧;
			this.hoffmann左侧 = hoffmann左侧;
			this.babinski左侧 = babinski左侧;
			this.chaddock左侧 = chaddock左侧;
			this.oppenheim左侧 = oppenheim左侧;
			this.gordon左侧 = gordon左侧;
			this.其他左侧 = 其他左侧;
			this.hoffmann右侧 = hoffmann右侧;
			this.babinski右侧 = babinski右侧;
			this.chaddock右侧 = chaddock右侧;
			this.oppenheim右侧 = oppenheim右侧;
			this.gordon右侧 = gordon右侧;
			this.其他右侧 = 其他右侧;
		}
	}

	public void delRemark(String remarkId) {
		for(Iterator<Remark> iter = remarks.iterator();iter.hasNext();)
			if(iter.next().getId().equalsIgnoreCase(remarkId))
		        iter.remove();
	}
}
