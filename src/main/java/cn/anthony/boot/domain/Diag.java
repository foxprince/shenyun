package cn.anthony.boot.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import cn.anthony.util.DateUtil;
import cn.anthony.util.StringTools;
import lombok.Data;

@QueryEntity
@Entity
@Data
public class Diag  extends GenericEntity {
    @ManyToOne(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "in_hospital_id")
    @JsonIgnore
    private InHospital inHospital;
    public Diag() {
    }

    public Diag(String s) {
	this.type = s;
    }

    public String type;// 初步诊断 确定诊断 补充诊断 更正
    public String detail;
    public Date diagDate;
    public String signature;

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(type + ":");
	sb.append(StringTools.checkNull(detail) + "<br/>");
	if (diagDate != null)
	    sb.append("时间：" + DateUtil.format(diagDate, "yyyy年MM月dd日"));
	sb.append("<br/>签名：" + StringTools.printString(signature));
	return sb.toString();
    }
}
