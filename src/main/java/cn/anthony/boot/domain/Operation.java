package cn.anthony.boot.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.anthony.util.DateUtil;
import lombok.Data;

@Data
public class Operation {
    /*
     * 科别 神经外科 床位号 41301 术前诊断：颈内动脉动脉瘤（ICA）(双) 术中诊断：颅内动脉瘤 手术名称：颅内动脉瘤支架辅助栓塞术
     * 手术医师：叶明 助手姓名： 手术时间：2015-03-02 13:09开始 2015-03-02 14:30完毕
     * 麻醉方法：全身麻醉.静脉麻醉失血量：5 ml 输血量：0 ml 手术经过、术中出现的情况及处理等：
     * 气管插管全麻成功后，病人取仰卧位，右侧腹股沟常规消毒铺巾，Seldinger技术穿刺右侧股动脉成功置入5F鞘，
     * 造影导丝配合5F导引导管行全脑血管造影，并行3D成像，右侧颈内动脉床突段见囊性动脉瘤，动脉瘤大小约0.5x0.4,瘤颈宽，指向上，
     * 载瘤动脉无狭窄及痉挛，局部无穿支发出。选择合适的工作角度，导引导管在路图下送至右侧颈内动脉，重新造影，在路图下，
     * 微导管在微导丝引导下送入动脉瘤腔内合适位置，造影证实后做路图，选择数个弹簧圈送至动脉瘤腔内，造影后见成篮满意，并将支架导管送至颈内动脉分叉处，
     * 予以半释放后将弹簧圈予以解脱，之后依次用弹簧圈栓塞，栓塞后造影显示动脉瘤栓塞良好，动脉瘤栓塞致密。将支架全部释放后再次造影，显示颅内血管显影良好，
     * 结束手术。 术中顺利，术中麻醉满意。 手术取标本肉眼所见：
     * 
     * 手术取标本送病理：
     * 
     * 手术医师签名： 记录日期：2015-03-02 20:52
     */
    public String operationDpt;// 科别
    public String bedNumber;// 床位号
    public String preDiagnosis;// 术前诊断
    public String operataionDiagnosis;// 术中诊断
    public String operationTitle;// 手术名称
    public String doctor;// 手术医师
    public String assistant;// 助手
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date beginTime;// 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date endTime;// 结束时间
    public String anaesthetic;// 麻醉方法
    public String bloodLoss;// 失血量
    public String bloodTransfusion;// 输血量
    public String detail;// 手术经过
    public String bb;//手术取标本肉眼所见
    public String bl;//手术取标本送病理,1:是，2:否
    public String sign;//
    public Date recordTime;//
    public String srcFile;//

    public String getBeginTimeDesc() {
	return DateUtil.format(beginTime, "yyyy-MM-dd HH:mm");
    }

    public String getEndTimeDesc() {
	return DateUtil.format(endTime, "yyyy-MM-dd HH:mm");
    }
    
    public String getRecordTimeDesc() {
	return DateUtil.format(recordTime, "yyyy-MM-dd HH:mm");
    }
    public String getOperationDpt() {
	return operationDpt;
    }

    public String getBedNumber() {
	return bedNumber;
    }

    public String getPreDiagnosis() {
	return preDiagnosis;
    }

    public String getOperataionDiagnosis() {
	return operataionDiagnosis;
    }

    public String getOperationTitle() {
	return operationTitle;
    }

    public String getDoctor() {
	return doctor;
    }

    public String getAssistant() {
	return assistant;
    }

    public Date getBeginTime() {
	return beginTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public String getAnaesthetic() {
	return anaesthetic;
    }

    public String getBloodLoss() {
	return bloodLoss;
    }

    public String getBloodTransfusion() {
	return bloodTransfusion;
    }

    public String getDetail() {
	return detail;
    }

    public String getSign() {
	return sign;
    }

    public Date getRecordTime() {
	return recordTime;
    }

    public String getSourceFile() {
	return srcFile;
    }
}
