package cn.anthony.boot.domain;

import java.util.Date;

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
    public String operationDpt;
    public String bedNumber;
    public String preDiagnosis;
    public String operataionDiagnosis;
    public String operationTitle;
    public String doctor;
    public String assistant;
    public Date beginTime;
    public Date endTime;
    public String anaesthetic;
    public String bloodLoss;
    public String bloodTransfusion;
    public String detail;
    public String sign;
    public Date recordTime;
    public String sourceFile;
}
