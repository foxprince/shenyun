package cn.anthony.boot.domain;

import cn.anthony.boot.util.Constant;
import lombok.Data;

@Data
public class ExtendObject {
    
    public String type = "";

    public ExtendObject() {
	super();
    }

    public String getTypeDesc() {
	return Constant.getExtendTypeDesc(type);
    }

    @Data
    public static class 出血组 extends ExtendObject {
	// 患者姓名 病历编号 性别 年龄 身份证号 诊断 基本病情 标签 出生年月日 科室 就诊日期 编号种类 编号 其他编号种类 编号 联系人
	// 手机号 固定电话 邮箱 患者职业 介绍人 地址 备注 获得Excel表格的内容:
	public String 诊断;
	public String 基本病情;
	public String 标签;
	public String 就诊日期;
	public String 联系人;
	public String 手机号;
	public String 固定电话;
	public String 地址;

	public 出血组(String 诊断, String 基本病情, String 标签, String 就诊日期, String 联系人, String 手机号, String 固定电话, String 地址) {
	    super();
	    this.type = "cxz";
	    this.诊断 = 诊断;
	    this.基本病情 = 基本病情;
	    this.标签 = 标签;
	    this.就诊日期 = 就诊日期;
	    this.联系人 = 联系人;
	    this.手机号 = 手机号;
	    this.固定电话 = 固定电话;
	    this.地址 = 地址;
	}

	public 出血组() {
	}

    }
    @Data
    public static class ICU extends ExtendObject {
	public String 姓名;
	public String 性别;
	public String 年龄;
	public String 住院号;
	public String 影像号;
	public String 入院日期;
	public String 出生日期;
	public String 联系人;
	public String 电话;
	public String 地址;
	public String ICU治疗原因;
	public String 出院诊断;
	public String 既往病史;
	public String 脑疝呼吸;
	public String HH评分;
	public String GCS评分;
	public String 并发症;
	public String 手术时间;
	public String 手术名称;
	public String 脑室穿刺;
	public String 降颅内压;
	public String 体温;
	public String 镇静药;
	public String SWI;
	public String 血管痉挛;
	public String 癫痫;
	public String 围手术期高血压;
	public String 内科问题;
	public String 营养支持;
	public String ICU住院天数;
	public ICU(String 姓名, String 性别, String 年龄, String 住院号, String 影像号, String 入院日期, String 出生日期, String 联系人,
		String 电话, String 地址, String iCU治疗原因, String 出院诊断, String 既往病史, String 脑疝呼吸, String hH评分, String gCS评分,
		String 并发症, String 手术时间, String 手术名称, String 脑室穿刺, String 降颅内压, String 体温, String 镇静药, String sWI,
		String 血管痉挛, String 癫痫, String 围手术期高血压, String 内科问题, String 营养支持, String iCU住院天数) {
	    super();this.type = "icu";
	    this.姓名 = 姓名;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.住院号 = 住院号;
	    this.影像号 = 影像号;
	    this.入院日期 = 入院日期;
	    this.出生日期 = 出生日期;
	    this.联系人 = 联系人;
	    this.电话 = 电话;
	    this.地址 = 地址;
	    this.ICU治疗原因 = iCU治疗原因;
	    this.出院诊断 = 出院诊断;
	    this.既往病史 = 既往病史;
	    this.脑疝呼吸 = 脑疝呼吸;
	    this.HH评分 = hH评分;
	    this.GCS评分 = gCS评分;
	    this.并发症 = 并发症;
	    this.手术时间 = 手术时间;
	    this.手术名称 = 手术名称;
	    this.脑室穿刺 = 脑室穿刺;
	    this.降颅内压 = 降颅内压;
	    this.体温 = 体温;
	    this.镇静药 = 镇静药;
	    this.SWI = sWI;
	    this.血管痉挛 = 血管痉挛;
	    this.癫痫 = 癫痫;
	    this.围手术期高血压 = 围手术期高血压;
	    this.内科问题 = 内科问题;
	    this.营养支持 = 营养支持;
	    this.ICU住院天数 = iCU住院天数;
	}
    }
    @Data //颅底组
    public static class Ldz extends ExtendObject {
	public String 患者姓名;
	public String 性别;
	public String 年龄;
	public String 病案号;
	public String 入院诊断;
	public String 确定诊断;
	public String 入院日期;
	public String 手术日期;
	public String 手术名称;
	public String 手术医生;
	public String 出院日期;
	public String 转入日期;
	public String 转出日期;
	public String 二次手术日期名称;
	public String 三次手术日期名称;
	public String 主管医生;
	public String 收治医生;
	public String 病理诊断;
	public String 家庭住址;
	public String 外阜;
	public String 本市;
	public String 就诊途径;
	public String 患者电话;
	public String 手术光盘号;
	public String 是否愿意住高间;
	public String 是否入住了高间;
	public Ldz(String 患者姓名, String 性别, String 年龄, String 病案号, String 入院诊断, String 确定诊断, String 入院日期, String 手术日期,
		String 手术名称, String 手术医生, String 出院日期, String 转入日期, String 转出日期, String 二次手术日期名称, String 三次手术日期名称,
		String 主管医生, String 收治医生, String 病理诊断, String 家庭住址, String 外阜, String 本市, String 就诊途径, String 患者电话,
		String 手术光盘号, String 是否愿意住高间, String 是否入住了高间) {
	    super();this.type = "ldz";
	    this.患者姓名 = 患者姓名;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.病案号 = 病案号;
	    this.入院诊断 = 入院诊断;
	    this.确定诊断 = 确定诊断;
	    this.入院日期 = 入院日期;
	    this.手术日期 = 手术日期;
	    this.手术名称 = 手术名称;
	    this.手术医生 = 手术医生;
	    this.出院日期 = 出院日期;
	    this.转入日期 = 转入日期;
	    this.转出日期 = 转出日期;
	    this.二次手术日期名称 = 二次手术日期名称;
	    this.三次手术日期名称 = 三次手术日期名称;
	    this.主管医生 = 主管医生;
	    this.收治医生 = 收治医生;
	    this.病理诊断 = 病理诊断;
	    this.家庭住址 = 家庭住址;
	    this.外阜 = 外阜;
	    this.本市 = 本市;
	    this.就诊途径 = 就诊途径;
	    this.患者电话 = 患者电话;
	    this.手术光盘号 = 手术光盘号;
	    this.是否愿意住高间 = 是否愿意住高间;
	    this.是否入住了高间 = 是否入住了高间;
	}
    }
    @Data //小儿组
    public static class Xrz extends ExtendObject {
	public String 床号;
	public String 住院号;
	public String 姓名;
	public String 性别;
	public String 年龄;
	public String 住院时间;
	public String 出院时间;
	public String 手术时间;
	public String 手术名称;
	public String 术者;
	public String 造影时间;
	public String 造影;
	public String 造影术者;
	public String 入院诊断;
	public String 诊断1;
	public String 诊断2;
	public String 诊断3;
	public String 诊断4;
	public String 病理;
	public String 抢救病例;
	public String 讨论病历;
	public String 疑难病例;
	public String 补充;
	public String 家庭住址;
	public String 电话;
	public String 手机;
	public String 随访时间;
	public Xrz(String 床号, String 住院号, String 姓名, String 性别, String 年龄, String 住院时间, String 出院时间, String 手术时间,
		String 手术名称, String 术者, String 造影时间, String 造影, String 造影术者, String 入院诊断, String 诊断1, String 诊断2,
		String 诊断3, String 诊断4, String 病理, String 抢救病例, String 讨论病历, String 疑难病例, String 补充, String 家庭住址,
		String 电话, String 手机, String 随访时间) {
	    super();this.type = "xrz";
	    this.床号 = 床号;
	    this.住院号 = 住院号;
	    this.姓名 = 姓名;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.住院时间 = 住院时间;
	    this.出院时间 = 出院时间;
	    this.手术时间 = 手术时间;
	    this.手术名称 = 手术名称;
	    this.术者 = 术者;
	    this.造影时间 = 造影时间;
	    this.造影 = 造影;
	    this.造影术者 = 造影术者;
	    this.入院诊断 = 入院诊断;
	    this.诊断1 = 诊断1;
	    this.诊断2 = 诊断2;
	    this.诊断3 = 诊断3;
	    this.诊断4 = 诊断4;
	    this.病理 = 病理;
	    this.抢救病例 = 抢救病例;
	    this.讨论病历 = 讨论病历;
	    this.疑难病例 = 疑难病例;
	    this.补充 = 补充;
	    this.家庭住址 = 家庭住址;
	    this.电话 = 电话;
	    this.手机 = 手机;
	    this.随访时间 = 随访时间;
	}
    }
    @Data //功能组
    public static class Gnz extends ExtendObject {
	public String 床号;
	public String 住院号;
	public String 姓名;
	public String 性别;
	public String 年龄;
	public String 住院时间;
	public String 出院时间;
	public String 手术时间;
	public String 手术名称;
	public String 术者;
	public String 造影时间;
	public String 造影;
	public String 造影术者;
	public String 入院诊断;
	public String 诊断1;
	public String 诊断2;
	public String 诊断3;
	public String 诊断4;
	public String 部位;
	public String 病理;
	public String 级别类型;
	public String 抢救病例;
	public String 讨论病历;
	public String 疑难病例;
	public String 补充;
	public String 家庭住址;
	public String 电话;
	public String 手机;
	public String 随访时间;
	public Gnz(String 床号, String 住院号, String 姓名, String 性别, String 年龄, String 住院时间, String 出院时间, String 手术时间,
		String 手术名称, String 术者, String 造影时间, String 造影, String 造影术者, String 入院诊断, String 诊断1, String 诊断2,
		String 诊断3, String 诊断4, String 部位, String 病理, String 级别类型, String 抢救病例, String 讨论病历, String 疑难病例,
		String 补充, String 家庭住址, String 电话, String 手机, String 随访时间) {
	    super();this.type = "gnz";
	    this.床号 = 床号;
	    this.住院号 = 住院号;
	    this.姓名 = 姓名;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.住院时间 = 住院时间;
	    this.出院时间 = 出院时间;
	    this.手术时间 = 手术时间;
	    this.手术名称 = 手术名称;
	    this.术者 = 术者;
	    this.造影时间 = 造影时间;
	    this.造影 = 造影;
	    this.造影术者 = 造影术者;
	    this.入院诊断 = 入院诊断;
	    this.诊断1 = 诊断1;
	    this.诊断2 = 诊断2;
	    this.诊断3 = 诊断3;
	    this.诊断4 = 诊断4;
	    this.部位 = 部位;
	    this.病理 = 病理;
	    this.级别类型 = 级别类型;
	    this.抢救病例 = 抢救病例;
	    this.讨论病历 = 讨论病历;
	    this.疑难病例 = 疑难病例;
	    this.补充 = 补充;
	    this.家庭住址 = 家庭住址;
	    this.电话 = 电话;
	    this.手机 = 手机;
	    this.随访时间 = 随访时间;
	}
    }
    @Data //肿瘤组
    public static class Zlz extends ExtendObject {
	public String 床号;
	public String 住院号;
	public String 姓名;
	public String 性别;
	public String 年龄;
	public String 住院时间;
	public String 出院时间;
	public String 手术时间;
	public String 手术名称;
	public String 术者;
	public String 肿瘤部位;
	public String 肿瘤类型;
	public String 肿瘤级别;
	public String 手术切除;
	public String 造影时间;
	public String 造影;
	public String 造影术者;
	public String 入院诊断;
	public String 诊断1;
	public String 诊断2;
	public String 诊断3;
	public String 诊断4;
	public String 抢救病例;
	public String 讨论病历;
	public String 疑难病例;
	public String 补充;
	public String 家庭住址;
	public String 电话;
	public String 手机;
	public String 随访时间;
	public Zlz(String 床号, String 住院号, String 姓名, String 性别, String 年龄, String 住院时间, String 出院时间, String 手术时间,
		String 手术名称, String 术者, String 肿瘤部位, String 肿瘤类型, String 肿瘤级别, String 手术切除, String 造影时间, String 造影,
		String 造影术者, String 入院诊断, String 诊断1, String 诊断2, String 诊断3, String 诊断4, String 抢救病例, String 讨论病历,
		String 疑难病例, String 补充, String 家庭住址, String 电话, String 手机, String 随访时间) {
	    super();this.type = "zlz";
	    this.床号 = 床号;
	    this.住院号 = 住院号;
	    this.姓名 = 姓名;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.住院时间 = 住院时间;
	    this.出院时间 = 出院时间;
	    this.手术时间 = 手术时间;
	    this.手术名称 = 手术名称;
	    this.术者 = 术者;
	    this.肿瘤部位 = 肿瘤部位;
	    this.肿瘤类型 = 肿瘤类型;
	    this.肿瘤级别 = 肿瘤级别;
	    this.手术切除 = 手术切除;
	    this.造影时间 = 造影时间;
	    this.造影 = 造影;
	    this.造影术者 = 造影术者;
	    this.入院诊断 = 入院诊断;
	    this.诊断1 = 诊断1;
	    this.诊断2 = 诊断2;
	    this.诊断3 = 诊断3;
	    this.诊断4 = 诊断4;
	    this.抢救病例 = 抢救病例;
	    this.讨论病历 = 讨论病历;
	    this.疑难病例 = 疑难病例;
	    this.补充 = 补充;
	    this.家庭住址 = 家庭住址;
	    this.电话 = 电话;
	    this.手机 = 手机;
	    this.随访时间 = 随访时间;
	}
	
    }
    @Data //脊柱组
    public static class Jzz extends ExtendObject {
	public String 姓名;
	public String 住院号;
	public String 性别;
	public String 年龄;
	public String 家庭住址;
	public String 家庭固话;
	public String 手机;
	public String 住院时间;
	public String 手术时间;
	public String 出院时间;
	public String 诊断;
	public String 病理;
	public String 手术方式;
	public String 所属类别;
	public String 术中时段;
	public String 术中用时分钟;
	public String 出血毫升;
	public String 术者;
	public String 补充;
	public String 列1;
	public String 预案;
	public String 视频;
	public String 存储;
	public String 术后影像;
	public String 病历;
	public String 术前评分;
	public String 术后评分;
	public String 术中固定;
	public String 耗材厂家;
	public String 耗材明细;
	public String 随访3个月;
	public String 随访6个月;
	public String 随访1年;
	public Jzz(String 姓名, String 住院号, String 性别, String 年龄, String 家庭住址, String 家庭固话, String 手机, String 住院时间,
		String 手术时间, String 出院时间, String 诊断, String 病理, String 手术方式, String 所属类别, String 术中时段, String 术中用时,
		String 出血, String 术者, String 补充, String 列1, String 预案, String 视频, String 存储, String 术后影像, String 病历,
		String 术前评分, String 术后评分, String 术中固定, String 耗材厂家, String 耗材明细, String 随访3个月, String 随访6个月,
		String 随访1年) {
	    super();this.type = "jzz";
	    this.姓名 = 姓名;
	    this.住院号 = 住院号;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.家庭住址 = 家庭住址;
	    this.家庭固话 = 家庭固话;
	    this.手机 = 手机;
	    this.住院时间 = 住院时间;
	    this.手术时间 = 手术时间;
	    this.出院时间 = 出院时间;
	    this.诊断 = 诊断;
	    this.病理 = 病理;
	    this.手术方式 = 手术方式;
	    this.所属类别 = 所属类别;
	    this.术中时段 = 术中时段;
	    this.术中用时分钟 = 术中用时;
	    this.出血毫升 = 出血;
	    this.术者 = 术者;
	    this.补充 = 补充;
	    this.列1 = 列1;
	    this.预案 = 预案;
	    this.视频 = 视频;
	    this.存储 = 存储;
	    this.术后影像 = 术后影像;
	    this.病历 = 病历;
	    this.术前评分 = 术前评分;
	    this.术后评分 = 术后评分;
	    this.术中固定 = 术中固定;
	    this.耗材厂家 = 耗材厂家;
	    this.耗材明细 = 耗材明细;
	    this.随访3个月 = 随访3个月;
	    this.随访6个月 = 随访6个月;
	    this.随访1年 = 随访1年;
	}
    }
    @Data //病房李组
    public static class Bflz extends ExtendObject {
	public String 姓名;
	public String 床号;
	public String 分组;
	public String 病历号;
	public String DSA号;
	public String 主管医生;
	public String 性别;
	public String 年龄;
	public String 入院日期;
	public String 确定诊断时间;
	public String 出院日期;
	public String 联系地址;
	public String 联系电话1;
	public String 联系电话2;
	public String 入院目的;
	public String 确定诊断1;
	public String 病变部位1;
	public String 更正诊断;
	public String 更正病变部位;
	public String 确定诊断2;
	public String 病变部位2;
	public String 确定诊断3;
	public String 病变部位3;
	public String 治疗方法;
	public String 耗材;
	public String 介入日;
	public String 血管造影日;
	public String 外科手术日;
	public String 影像资料;
	public String 出院复查时间;
	public String 是否通知;
	public String 随访复查;
	public String 再次复查时间;
	public String 脑动静脉畸形;
	public String 临床主要症状和体征;
	public String 脑出血;
	public String 头痛;
	public String 头痛部位;
	public String 疼痛时间;
	public String 疼痛频率;
	public String 疼痛的性质;
	public String 严重程度;
	public String 头痛伴随症状;
	public String 有无癫痫发作;
	public String 癫痫初次发作时间;
	public String 癫痫发作形式;
	public String 癫痫发作频率;
	public String 癫痫发作诱发因素;
	public String 癫痫是否服药;
	public String 神经功能缺失;
	public String 神经功能缺失的表现;
	public String 其他症状;
	public String 影像学表现单一部位;
	public String 影像学表现多个病变;
	public String 影像学表现大小;
	public String 影像学表现供血情况;
	public String 穿支动脉供血;
	public String 脉络膜前动脉供血情况;
	public String 脉络膜后内侧动脉供血情况;
	public String 脉络膜后外侧动脉供血情况;
	public String 影像学表现静脉引流;
	public String 影像学表现深静脉引流;
	public String 其他血管异常软膜动脉瘘;
	public String 其他血管异常动脉瘤;
	public String 其他血管异常静脉瘤或扩张;
	public String 其他血管异常引流静脉狭窄;
	public String 其他血管异常静脉窦狭窄;
	public String 其他血管异常静脉窦变异;
	public String 其他血管异常硬脑膜动静脉瘘;
	public String 其他血管异常静脉畸形;
	public String 其他血管异常海绵状血管瘤;
	public String 其他血管异常毛细血管扩张症;
	public String 重要功能区分级;
	public String 大小分级;
	public String 深静脉引流分级;
	public String SpetzlerMartin分级;
	public String 合并症;
	public String 治疗;
	public String 癫痫药物;
	public String 头痛药物;
	public String 介入栓塞序号;
	public String 栓塞时间;
	public String 介入术者;
	public String 超选及栓塞动脉途径;
	public String 栓塞用材料;
	public String 并发症1;
	public String 并发症2;
	public String 并发症3;
	public String 术中出血原因;
	public String 出血处理;
	public String 再次栓塞;
	public String 开颅手术序号;
	public String 开颅手术时间;
	public String 开刀术者;
	public String 开颅目的;
	public String 开颅并发症脑出血;
	public String 开颅并发症神经功能障碍;
	public String 再次开颅手术;
	public String 放疗时间;
	public String 放疗序号;
	public String 再次放疗;
	public String 患者的最终治疗途径;
	public String 合并症动脉瘤处理与否;
	public String 合并症硬脑膜动静脉瘘的处理;
	public String 合并症软膜动静脉瘘的处理;
	public String 合并症其他;
	public String 近期影像学治疗结果;
	public String 近期临床治疗结果;
	public String 远期影像学治疗结果3月以上随访;
	public String 远期临床结果6个月以上随访;
	public String 再次治疗序号;
	public String 再次治疗时间;
	public String 再次治疗术者;
	public String 再次治疗途径;
	public String 再次治疗期间重大临床症状和体征;
	public String 再次治疗期间脑出血;
	public String 再次治疗期间头痛;
	public String 再次治疗期间癫痫;
	public String 再次治疗期间神经功能缺失;
	public String 再次治疗期间其他症状;
	public String 动脉瘤;
	public String 临床表现;
	public String 蛛网膜下腔出血;
	public String 本次为第几次出血;
	public String 本次出血时间;
	public String 未破裂动脉瘤临床表现;
	public String 未破裂动脉瘤症状出现或偶然发现时间;
	public String 主诉及最明显不适;
	public String 治疗方式;
	public String 介入治疗方式;
	public String 支架种类;
	public String 密网支架种类;
	public String 介入伴随治疗;
	public String 介入术中意外;
	public String 栓塞动脉瘤结果;
	public String 介入血管结果;
	public String 介入并发症;
	public String 术后短期治疗随访结果;
	public String 动脉瘤病史;
	public String 随访时间点最后一次术后或诊断动脉瘤后;
	public String 目前主诉和最明显不适;
	public Bflz(String 姓名, String 床号, String 分组, String 病历号, String dSA号, String 主管医生, String 性别, String 年龄,
		String 入院日期, String 确定诊断时间, String 出院日期, String 联系地址, String 联系电话1, String 联系电话2, String 入院目的,
		String 确定诊断1, String 病变部位1, String 更正诊断, String 更正病变部位, String 确定诊断2, String 病变部位2, String 确定诊断3,
		String 病变部位3, String 治疗方法, String 耗材, String 介入日, String 血管造影日, String 外科手术日, String 影像资料,
		String 出院复查时间, String 是否通知, String 随访复查, String 再次复查时间, String 脑动静脉畸形, String 临床主要症状和体征, String 脑出血,
		String 头痛, String 头痛部位, String 疼痛时间, String 疼痛频率, String 疼痛的性质, String 严重程度, String 头痛伴随症状,
		String 有无癫痫发作, String 癫痫初次发作时间, String 癫痫发作形式, String 癫痫发作频率, String 癫痫发作诱发因素, String 癫痫是否服药,
		String 神经功能缺失, String 神经功能缺失的表现, String 其他症状, String 影像学表现单一部位, String 影像学表现多个病变, String 影像学表现大小,
		String 影像学表现供血情况, String 穿支动脉供血, String 脉络膜前动脉供血情况, String 脉络膜后内侧动脉供血情况, String 脉络膜后外侧动脉供血情况,
		String 影像学表现静脉引流, String 影像学表现深静脉引流, String 其他血管异常软膜动脉瘘, String 其他血管异常动脉瘤, String 其他血管异常静脉瘤或扩张,
		String 其他血管异常引流静脉狭窄, String 其他血管异常静脉窦狭窄, String 其他血管异常静脉窦变异, String 其他血管异常硬脑膜动静脉瘘, String 其他血管异常静脉畸形,
		String 其他血管异常海绵状血管瘤, String 其他血管异常毛细血管扩张症, String 重要功能区分级, String 大小分级, String 深静脉引流分级,
		String spetzlerMartin分级, String 合并症, String 治疗, String 癫痫药物, String 头痛药物, String 介入栓塞序号, String 栓塞时间,
		String 介入术者, String 超选及栓塞动脉途径, String 栓塞用材料, String 并发症1, String 并发症2, String 并发症3, String 术中出血原因,
		String 出血处理, String 再次栓塞, String 开颅手术序号, String 开颅手术时间, String 开刀术者, String 开颅目的, String 开颅并发症脑出血,
		String 开颅并发症神经功能障碍, String 再次开颅手术, String 放疗时间, String 放疗序号, String 再次放疗, String 患者的最终治疗途径,
		String 合并症动脉瘤处理与否, String 合并症硬脑膜动静脉瘘的处理, String 合并症软膜动静脉瘘的处理, String 合并症其他, String 近期影像学治疗结果,
		String 近期临床治疗结果, String 远期影像学治疗结果3月以上随访, String 远期临床结果6个月以上随访, String 再次治疗序号, String 再次治疗时间,
		String 再次治疗术者, String 再次治疗途径, String 再次治疗期间重大临床症状和体征, String 再次治疗期间脑出血, String 再次治疗期间头痛,
		String 再次治疗期间癫痫, String 再次治疗期间神经功能缺失, String 再次治疗期间其他症状, String 动脉瘤, String 临床表现, String 蛛网膜下腔出血,
		String 本次为第几次出血, String 本次出血时间, String 未破裂动脉瘤临床表现, String 未破裂动脉瘤症状出现或偶然发现时间, String 主诉及最明显不适,
		String 治疗方式, String 介入治疗方式, String 支架种类, String 密网支架种类, String 介入伴随治疗, String 介入术中意外, String 栓塞动脉瘤结果,
		String 介入血管结果, String 介入并发症, String 术后短期治疗随访结果, String 动脉瘤病史, String 随访时间点最后一次术后或诊断动脉瘤后,
		String 目前主诉和最明显不适) {
	    super();this.type = "bflz";
	    this.姓名 = 姓名;
	    this.床号 = 床号;
	    this.分组 = 分组;
	    this.病历号 = 病历号;
	    DSA号 = dSA号;
	    this.主管医生 = 主管医生;
	    this.性别 = 性别;
	    this.年龄 = 年龄;
	    this.入院日期 = 入院日期;
	    this.确定诊断时间 = 确定诊断时间;
	    this.出院日期 = 出院日期;
	    this.联系地址 = 联系地址;
	    this.联系电话1 = 联系电话1;
	    this.联系电话2 = 联系电话2;
	    this.入院目的 = 入院目的;
	    this.确定诊断1 = 确定诊断1;
	    this.病变部位1 = 病变部位1;
	    this.更正诊断 = 更正诊断;
	    this.更正病变部位 = 更正病变部位;
	    this.确定诊断2 = 确定诊断2;
	    this.病变部位2 = 病变部位2;
	    this.确定诊断3 = 确定诊断3;
	    this.病变部位3 = 病变部位3;
	    this.治疗方法 = 治疗方法;
	    this.耗材 = 耗材;
	    this.介入日 = 介入日;
	    this.血管造影日 = 血管造影日;
	    this.外科手术日 = 外科手术日;
	    this.影像资料 = 影像资料;
	    this.出院复查时间 = 出院复查时间;
	    this.是否通知 = 是否通知;
	    this.随访复查 = 随访复查;
	    this.再次复查时间 = 再次复查时间;
	    this.脑动静脉畸形 = 脑动静脉畸形;
	    this.临床主要症状和体征 = 临床主要症状和体征;
	    this.脑出血 = 脑出血;
	    this.头痛 = 头痛;
	    this.头痛部位 = 头痛部位;
	    this.疼痛时间 = 疼痛时间;
	    this.疼痛频率 = 疼痛频率;
	    this.疼痛的性质 = 疼痛的性质;
	    this.严重程度 = 严重程度;
	    this.头痛伴随症状 = 头痛伴随症状;
	    this.有无癫痫发作 = 有无癫痫发作;
	    this.癫痫初次发作时间 = 癫痫初次发作时间;
	    this.癫痫发作形式 = 癫痫发作形式;
	    this.癫痫发作频率 = 癫痫发作频率;
	    this.癫痫发作诱发因素 = 癫痫发作诱发因素;
	    this.癫痫是否服药 = 癫痫是否服药;
	    this.神经功能缺失 = 神经功能缺失;
	    this.神经功能缺失的表现 = 神经功能缺失的表现;
	    this.其他症状 = 其他症状;
	    this.影像学表现单一部位 = 影像学表现单一部位;
	    this.影像学表现多个病变 = 影像学表现多个病变;
	    this.影像学表现大小 = 影像学表现大小;
	    this.影像学表现供血情况 = 影像学表现供血情况;
	    this.穿支动脉供血 = 穿支动脉供血;
	    this.脉络膜前动脉供血情况 = 脉络膜前动脉供血情况;
	    this.脉络膜后内侧动脉供血情况 = 脉络膜后内侧动脉供血情况;
	    this.脉络膜后外侧动脉供血情况 = 脉络膜后外侧动脉供血情况;
	    this.影像学表现静脉引流 = 影像学表现静脉引流;
	    this.影像学表现深静脉引流 = 影像学表现深静脉引流;
	    this.其他血管异常软膜动脉瘘 = 其他血管异常软膜动脉瘘;
	    this.其他血管异常动脉瘤 = 其他血管异常动脉瘤;
	    this.其他血管异常静脉瘤或扩张 = 其他血管异常静脉瘤或扩张;
	    this.其他血管异常引流静脉狭窄 = 其他血管异常引流静脉狭窄;
	    this.其他血管异常静脉窦狭窄 = 其他血管异常静脉窦狭窄;
	    this.其他血管异常静脉窦变异 = 其他血管异常静脉窦变异;
	    this.其他血管异常硬脑膜动静脉瘘 = 其他血管异常硬脑膜动静脉瘘;
	    this.其他血管异常静脉畸形 = 其他血管异常静脉畸形;
	    this.其他血管异常海绵状血管瘤 = 其他血管异常海绵状血管瘤;
	    this.其他血管异常毛细血管扩张症 = 其他血管异常毛细血管扩张症;
	    this.重要功能区分级 = 重要功能区分级;
	    this.大小分级 = 大小分级;
	    this.深静脉引流分级 = 深静脉引流分级;
	    SpetzlerMartin分级 = spetzlerMartin分级;
	    this.合并症 = 合并症;
	    this.治疗 = 治疗;
	    this.癫痫药物 = 癫痫药物;
	    this.头痛药物 = 头痛药物;
	    this.介入栓塞序号 = 介入栓塞序号;
	    this.栓塞时间 = 栓塞时间;
	    this.介入术者 = 介入术者;
	    this.超选及栓塞动脉途径 = 超选及栓塞动脉途径;
	    this.栓塞用材料 = 栓塞用材料;
	    this.并发症1 = 并发症1;
	    this.并发症2 = 并发症2;
	    this.并发症3 = 并发症3;
	    this.术中出血原因 = 术中出血原因;
	    this.出血处理 = 出血处理;
	    this.再次栓塞 = 再次栓塞;
	    this.开颅手术序号 = 开颅手术序号;
	    this.开颅手术时间 = 开颅手术时间;
	    this.开刀术者 = 开刀术者;
	    this.开颅目的 = 开颅目的;
	    this.开颅并发症脑出血 = 开颅并发症脑出血;
	    this.开颅并发症神经功能障碍 = 开颅并发症神经功能障碍;
	    this.再次开颅手术 = 再次开颅手术;
	    this.放疗时间 = 放疗时间;
	    this.放疗序号 = 放疗序号;
	    this.再次放疗 = 再次放疗;
	    this.患者的最终治疗途径 = 患者的最终治疗途径;
	    this.合并症动脉瘤处理与否 = 合并症动脉瘤处理与否;
	    this.合并症硬脑膜动静脉瘘的处理 = 合并症硬脑膜动静脉瘘的处理;
	    this.合并症软膜动静脉瘘的处理 = 合并症软膜动静脉瘘的处理;
	    this.合并症其他 = 合并症其他;
	    this.近期影像学治疗结果 = 近期影像学治疗结果;
	    this.近期临床治疗结果 = 近期临床治疗结果;
	    this.远期影像学治疗结果3月以上随访 = 远期影像学治疗结果3月以上随访;
	    this.远期临床结果6个月以上随访 = 远期临床结果6个月以上随访;
	    this.再次治疗序号 = 再次治疗序号;
	    this.再次治疗时间 = 再次治疗时间;
	    this.再次治疗术者 = 再次治疗术者;
	    this.再次治疗途径 = 再次治疗途径;
	    this.再次治疗期间重大临床症状和体征 = 再次治疗期间重大临床症状和体征;
	    this.再次治疗期间脑出血 = 再次治疗期间脑出血;
	    this.再次治疗期间头痛 = 再次治疗期间头痛;
	    this.再次治疗期间癫痫 = 再次治疗期间癫痫;
	    this.再次治疗期间神经功能缺失 = 再次治疗期间神经功能缺失;
	    this.再次治疗期间其他症状 = 再次治疗期间其他症状;
	    this.动脉瘤 = 动脉瘤;
	    this.临床表现 = 临床表现;
	    this.蛛网膜下腔出血 = 蛛网膜下腔出血;
	    this.本次为第几次出血 = 本次为第几次出血;
	    this.本次出血时间 = 本次出血时间;
	    this.未破裂动脉瘤临床表现 = 未破裂动脉瘤临床表现;
	    this.未破裂动脉瘤症状出现或偶然发现时间 = 未破裂动脉瘤症状出现或偶然发现时间;
	    this.主诉及最明显不适 = 主诉及最明显不适;
	    this.治疗方式 = 治疗方式;
	    this.介入治疗方式 = 介入治疗方式;
	    this.支架种类 = 支架种类;
	    this.密网支架种类 = 密网支架种类;
	    this.介入伴随治疗 = 介入伴随治疗;
	    this.介入术中意外 = 介入术中意外;
	    this.栓塞动脉瘤结果 = 栓塞动脉瘤结果;
	    this.介入血管结果 = 介入血管结果;
	    this.介入并发症 = 介入并发症;
	    this.术后短期治疗随访结果 = 术后短期治疗随访结果;
	    this.动脉瘤病史 = 动脉瘤病史;
	    this.随访时间点最后一次术后或诊断动脉瘤后 = 随访时间点最后一次术后或诊断动脉瘤后;
	    this.目前主诉和最明显不适 = 目前主诉和最明显不适;
	}
	
	
    }
}
