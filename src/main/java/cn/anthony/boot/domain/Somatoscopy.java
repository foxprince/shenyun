package cn.anthony.boot.domain;

import lombok.Data;

import java.util.List;

@Data
public class Somatoscopy {
	/*
	 * 一般情况： T:36.6℃P:80次/分R:18次/分BP:130/70mmHg 发育正常,营养良好，身高172厘米，体重74公斤。
	 * 神志清晰，自主体位，面容无异常,与医生合作。
	 */
	public String general;
	public String T;
	public String P;
	public String R;
	public String BP;
	public String skin;
	public String superficialLymph;// 浅表淋巴结
	public String skull;
	public String eye;
	public String ear;
	public String node;
	public String mouse;
	public String throat;
	public String neck;
	public String thorax;
	public String lung;
	public String heart;
	public String bloodVessels;
	public String abdomen;
	public String liver;
	public String spleen;
	public String kidney;
	public String vulva;
	public String spine;
	public String limbs;
	public String nervousSystem;
	public SpecialExamination sExamination;

	public Somatoscopy() {
		this.sExamination = new SpecialExamination();
	}

	@Data//入院后化验检查
	public static class LaboratoryExamination {
		public String 血红蛋白血小板,白细胞纤维蛋白原,部分活化凝血酶原时间国际标准化比值;
		public String 丙氨酸氨基转移酶天冬氨酸氨基转移酶,血肌酐尿素氮,高密度脂蛋白胆固醇低密度脂蛋白胆固醇;
		public String 甘油三酯总胆固醇,载脂蛋白A载脂蛋白B,空腹血葡萄糖;
		public String 糖化血红蛋白,超敏CRP;
	}

	@Data
	public static class SpecialExamination {
		/*
		 * 神志清醒
		 * 精神状态：一般表现良好，情绪良好，定向力正常，计算力正常，远记忆力正常，近记忆力正常，理解力正常，妄想无，幻觉无，错觉无，自知力正常
		 * 头部：形状正常，压痛无，听诊正常，头皮正常 语言：正常 脑膜刺激征 mentalStatus; head; language;
		 * meningismus; smelling; sight; visionField; eyeGround; eyeMovement;
		 * diplopia;//复视;
		 */
		public List<String> 脑血管病相关危险因素,其他相关疾病病例;
		public Patient.高级皮层功能 高级皮层功能;
		public Patient.颅神经 颅神经;
		public String 运动系统, 共济运动, 感觉系统;
		public Patient.反射 反射;
		public String 自主神经与内分泌系统;
		public String src;

		public SpecialExamination() {
			高级皮层功能 = new Patient.高级皮层功能();
			颅神经 = new Patient.颅神经();
			反射 = new Patient.反射();
		}
	}

	public SpecialExamination getsExamination() {
		return sExamination;
	}
}
