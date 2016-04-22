package cn.anthony.boot.domain;

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
    public class SpecialExamination {
	/*
	 * 神志清醒
	 * 精神状态：一般表现良好，情绪良好，定向力正常，计算力正常，远记忆力正常，近记忆力正常，理解力正常，妄想无，幻觉无，错觉无，自知力正常
	 * 头部：形状正常，压痛无，听诊正常，头皮正常 语言：正常 脑膜刺激征 mentalStatus; head; language;
	 * meningismus; smelling; sight; visionField; eyeGround; eyeMovement;
	 * diplopia;//复视;
	 */
	public String src;

	public String getSrc() {
	    return src;
	}
    }

    public String getGeneral() {
	return general;
    }

    public String getT() {
	return T;
    }

    public String getP() {
	return P;
    }

    public String getR() {
	return R;
    }

    public String getBP() {
	return BP;
    }

    public String getSkin() {
	return skin;
    }

    public String getSuperficialLymph() {
	return superficialLymph;
    }

    public String getSkull() {
	return skull;
    }

    public String getEye() {
	return eye;
    }

    public String getEar() {
	return ear;
    }

    public String getNode() {
	return node;
    }

    public String getMouse() {
	return mouse;
    }

    public String getThroat() {
	return throat;
    }

    public String getNeck() {
	return neck;
    }

    public String getThorax() {
	return thorax;
    }

    public String getLung() {
	return lung;
    }

    public String getHeart() {
	return heart;
    }

    public String getBloodVessels() {
	return bloodVessels;
    }

    public String getAbdomen() {
	return abdomen;
    }

    public String getLiver() {
	return liver;
    }

    public String getSpleen() {
	return spleen;
    }

    public String getKidney() {
	return kidney;
    }

    public String getVulva() {
	return vulva;
    }

    public String getSpine() {
	return spine;
    }

    public String getLimbs() {
	return limbs;
    }

    public String getNervousSystem() {
	return nervousSystem;
    }

    public SpecialExamination getsExamination() {
	return sExamination;
    }
}


