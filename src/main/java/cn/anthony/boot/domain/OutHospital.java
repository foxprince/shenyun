package cn.anthony.boot.domain;

import java.util.Date;

public class OutHospital {
    /*
     * 科别：神经外科 入院时间：2015年02月26日 出院时间：2015年03月05日 入院时情况(包括检验异常结果等)：
     * 患者9月前无明显诱因出现头晕症状，无恶心呕吐，无意识障碍，无肢体活动障碍，于当地医院就诊，查头CT及MR提示未见明显异常。
     * 7月前行头颅CTA检查示双侧颈内动脉动脉瘤，6月前来我院就诊，行一侧动脉瘤支架辅助栓塞，再次入院以“颅内动脉动脉瘤”收入院。病程中无肢体偏瘫，
     * 无抽搐，无意识障碍，病后精神饮食正常，大小便正常，体重无明显变化。既往高血压病史3年，口服坎地沙坦控制血压，有糖尿病病史3年，口服拜糖平控制血糖。
     * 40年前有肺结核病史，已治愈；10年前因咳血住我院呼吸内科，诊断支气管扩张症。否认冠心病史否认输血史，否认药物、食物过敏史，预防接种史按计划进行，
     * 否认手术外伤史。 入院诊断： 颈内动脉动脉瘤（双侧），颅内动脉瘤支架辅助栓塞术后 入院后诊疗经过：
     * 入院后抽血行血细胞分析，电解质，肝肾功能，心电图检查未见明显异常，于2015年3月2日在全麻下行颅内动脉瘤栓塞术，术后病情稳定。
     * 出院时情况(包括主要化验结果等)： 神志清楚，双侧瞳孔等大圆形，直径约3.0mm，对光反射灵敏，颈软，四肢活动自如，生理反射存在，病理反射未引出。
     * 出院诊断： 颈内动脉动脉瘤（ICA）(双) 颈内动脉动脉瘤栓塞术后 手术名称及伤口愈合情况： 颅内动脉瘤栓塞术 出院医嘱： 1.控制高血压
     * 2.继续口服波立维抗血小板治疗 3.控制血糖 4.有情况及时就诊 备注： (注：出院记录打印时一式三份) 医生签名：
     */
    public String department;
    public Date outDate;
    public String inDescriotion;
    public String inDiagnosis;
    public String treatment;
    public String outDescription;
    public String outDiagnosis;
    public String operationDesc;
    public String dischargeOrder;// 出院医嘱
    public String note;
    public String sign;
    public String sourceFile;

    public String getDepartment() {
	return department;
    }

    public Date getOutDate() {
	return outDate;
    }

    public String getInDescription() {
	return inDescriotion;
    }

    public String getInDiagnosis() {
	return inDiagnosis;
    }

    public String getTreatment() {
	return treatment;
    }

    public String getOutDescription() {
	return outDescription;
    }

    public String getOutDiagnosis() {
	return outDiagnosis;
    }

    public String getOperationDesc() {
	return operationDesc;
    }

    public String getDischargeOrder() {
	return dischargeOrder;
    }

    public String getNote() {
	return note;
    }

    public String getSign() {
	return sign;
    }

    public String getSourceFile() {
	return sourceFile;
    }
}
