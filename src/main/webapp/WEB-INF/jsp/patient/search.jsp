﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="高级查询" />
</c:import>
<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>病人列表</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./list">病人列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <html:form id="patientForm" modelAttribute="pageRequest" formUrl="/patient/search">
              <DIV class="panel panel-default">
                <DIV class="row panel-heading clearfix ">
                  <DIV class="col-sm-4 pull-center">
                    <BUTTON class="btn btn-success btn-sm" type="submit">查询病案</BUTTON>
                  </DIV>
                  <div class="col-sm-8 pull-left">
                    <label class="col-sm-4 control-label">保存查询历史</label>
                    <div class="checkbox col-sm-4 ">
                       <form:checkbox path="needSave" id="needSave" />
                       <form:errors path="needSave" class="control-label" />
                    </div>
                  </div>
                </DIV>
                <DIV class="panel-body">
                  <DIV class="container-fluid">
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-xs-3 col-sm-3 control-label" for="name">病人姓名</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="name" type="text" placeholder="病人"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="age1">年龄</LABEL>
                          <DIV class="col-md-9 col-sm-9 form-inline">
                            <form:input class="form-control" style="width: 100px;" path="minAge" type="text" placeholder="最小"/>
                            ~
                            <form:input class="form-control" style="width: 100px;" path="maxAge" type="text" placeholder="最大"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-2 col-sm-2">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-4 col-sm-4 control-label">性别 </LABEL>
                          <DIV class="col-md-8 col-sm-8">
                            <LABEL class="radio-inline"><form:radiobutton path="sex" value="1" /> 男 </LABEL> <LABEL
                              class="radio-inline"><form:radiobutton path="sex" value="2" /> 女 </LABEL>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-2 col-sm-2">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" style="padding-left: 0px;"> 婚姻 </LABEL>
                          <DIV class="col-md-9 col-sm-9" style="padding-left: 0px;">
                            <LABEL class="radio-inline">
                            <form:radiobutton path="marriageStatus" value="1" />已婚 </LABEL> 
                            <LABEL class="radio-inline"><form:radiobutton path="marriageStatus" 
                              value="0"/> 未婚 </LABEL>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="adminission_no">病案号</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="pId" type="text" placeholder="病案号"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="nationality">民族</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="nationality" type="text" placeholder="民族"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="certNo">身份证</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="certNo" type="text" placeholder="身份证号码"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="country">国别</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="country" type="text" placeholder="国别"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="registeredaddress">户口地址</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="registeredaddress" type="text" placeholder="户口地址"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="company">工作单位</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="company" type="text" placeholder="工作单位"/>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <HR>
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm ">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="mainDiag">诊断病情</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                              <form:input class="form-control" path="mainDiag" type="text" placeholder="诊断病情"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionDept">入院科室</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="admissionDept" type="text" placeholder="入院科室"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionTime">入院起始</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="inDateBegin" type="date" />
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="KZR_DOCTOR_NAME">科主任</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <INPUT name="KZR_DOCTOR_NAME" class="form-control" id="KZR_DOCTOR_NAME" type="text"
                              placeholder="科主任" value="">
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionTime2">入院截止</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="inDateEnd" type="date" />
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZZ_DOCTOR_NAME">主治医师</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="ZZ_DOCTOR_NAME" type="text" placeholder="主治医师"/>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargeDept">出院科室</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="dischargeDept" type="text" placeholder="出院科室"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionWard">入院病房</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <SELECT name="admissionWard" class="form-control" id="admissionWard"><OPTION
                                value="">全部</OPTION>
                              <OPTION value="神外一">神外一</OPTION>
                              <OPTION value="神外二">神外二</OPTION>
                              <OPTION value="神外三">神外三</OPTION>
                              <OPTION value="神外四">神外四</OPTION>
                              <OPTION value="监护室">监护室</OPTION>
                            </SELECT>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime">出院起始</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <INPUT name="dischargetime" class="form-control" id="dischargetime" type="date">
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargeWard">出院病房</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <SELECT name="dischargeWard" class="form-control" id="dischargeWard"><OPTION
                                value="">全部</OPTION>
                              <OPTION value="神外一">神外一</OPTION>
                              <OPTION value="神外二">神外二</OPTION>
                              <OPTION value="神外三">神外三</OPTION>
                              <OPTION value="神外四">神外四</OPTION>
                              <OPTION value="监护室">监护室</OPTION>
                            </SELECT>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime2">出院截止</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <INPUT name="dischargetime2" class="form-control" id="dischargetime2" type="date">
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZY_DOCTOR_NAME">住院医师</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="ZY_DOCTOR_NAME" type="text" placeholder="住院医师"/>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZZHEN_DOCTOR_NAME">主诊医师</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="ZZHEN_DOCTOR_NAME" type="text" placeholder="主诊医师"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label">排序</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <SELECT name="sort" class="form-control" value="admissionTime"><OPTION
                                value="adminission_no">病案号</OPTION>
                              <OPTION value="age">年龄</OPTION>
                              <OPTION value="admissionTime">入院时间</OPTION>
                              <OPTION value="dischargeTime">出院时间</OPTION>
                            </SELECT>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-2 col-sm-2">
                        <DIV class="form-group form-group-sm">
                          <DIV class="col-md-9 col-sm-9" style="padding-left: 0px;">
                            <LABEL class="radio-inline"><INPUT name="order" type="radio" value="asc"> 顺序
                            </LABEL> <LABEL class="radio-inline"><INPUT name="order" type="radio" checked=""
                              value="desc"> 逆序 </LABEL>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                  </DIV>
                </DIV>
                <DIV class="panel-heading clearfix ">
                  <DIV class="col-sm-4 pull-center">
                    <BUTTON class="btn btn-success btn-sm" type="submit">查询病案</BUTTON>
                  </DIV>
                  <div class="col-sm-8 pull-left">
                    <label class="col-sm-4 control-label">保存查询历史</label>
                    <div class="checkbox col-sm-4 ">
                       <form:checkbox path="needSave" id="needSave" />
                       <form:errors path="needSave" class="control-label" />
                    </div>
                  </div>
                </DIV>
              </DIV>
            </html:form>
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
  <!-- jQuery 2.1.4 -->
  <script src="../plugins/jQuery/jQuery-2.1.4.min.js"></script>
  <!-- Bootstrap 3.3.5 -->
  <script src="../bootstrap/js/bootstrap.min.js"></script>
  <!-- SlimScroll -->
  <script src="../plugins/slimScroll/jquery.slimscroll.min.js"></script>
  <!-- FastClick -->
  <script src="../plugins/fastclick/fastclick.min.js"></script>
  <!-- AdminLTE App -->
  <script src="../dist/js/app.min.js"></script>
  <!-- AdminLTE for demo purposes -->
  <script src="../dist/js/demo.js"></script>
</body>
</html>