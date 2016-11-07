<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="标准查询" />
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
        <h1>标准查询</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./initSearch">标准查询</a></li>
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
                       <input type="checkbox" name="needSave" id="needSave" />
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
                            <form:radiobutton path="frontPage.marriageStatus" value="1" />已婚 </LABEL> 
                            <LABEL class="radio-inline"><form:radiobutton path="frontPage.marriageStatus" 
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
                            <form:input class="form-control" path="frontPage.nationality" type="text" placeholder="民族"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="certNo">身份证</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="frontPage.certNo" type="text" placeholder="身份证号码"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="country">国别</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="frontPage.country" type="text" placeholder="国别"/>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="registeredaddress">户口地址</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="frontPage.registeredaddress" type="text" placeholder="户口地址"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="company">工作单位</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="frontPage.company" type="text" placeholder="工作单位"/>
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
                              <form:input class="form-control" path="frontPage.mainDiag" type="text" placeholder="诊断病情"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionDept">入院科室</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="inHospital.admissionDept" type="text" placeholder="入院科室"/>
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
                          <LABEL class="col-md-3 col-sm-3 control-label" for="KZR_DOCTOR_NAME">主诊医生</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input name="KZR_DOCTOR_NAME" class="form-control" path="frontPage.ZZHEN_DOCTOR_NAME" type="text"
                              placeholder="主诊医生" value=""/>
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
                            <form:input class="form-control" path="frontPage.ZZ_DOCTOR_NAME" type="text" placeholder="主治医师"/>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargeDept">出院科室</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="frontPage.dischargeDept" type="text" placeholder="出院科室"/>
                          </DIV>
                        </DIV>
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionWard">入院病房</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="frontPage.admissionWard" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${admissionWardList}" />
                            </form:select>
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
                            <form:select path="frontPage.dischargeWard" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${dischargeWardList}" />
                            </form:select>
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
                            <form:input class="form-control"  path="frontPage.ZY_DOCTOR_NAME" type="text" placeholder="住院医师"/>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    
                    <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZY_DOCTOR_NAME">转科病房</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="frontPage.changedept" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${changedeptList}" />
                            </form:select>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    <div class="box box-success">
    <div class="box-header">
      <h3 class="box-title">排序选项</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-success btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-success btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
        <DIV class="row">
          <div class="form-group">
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 年龄</label>
            <LABEL class="radio-inline"><INPUT name="sort" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 入院时间</label>
            <LABEL class="radio-inline"><INPUT name="sort" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 出院时间</label>
            <LABEL class="radio-inline"><INPUT name="sort" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          </div>
        </DIV>
        <DIV class="row">
          <div class="form-group">
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 籍贯</label>
            <LABEL class="radio-inline"><INPUT name="sort1" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 出生地</label>
            <LABEL class="radio-inline"><INPUT name="sort2" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 民族</label>
            <LABEL class="radio-inline"><INPUT name="sort3" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          </div>
        </DIV>
        <DIV class="row">
          <div class="form-group">
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 住院次数</label>
            <LABEL class="radio-inline"><INPUT name="sort4" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 主要诊断</label>
            <LABEL class="radio-inline"><INPUT name="sort5" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 手术</label>
            <LABEL class="radio-inline"><INPUT name="sort6" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          </div>
        </DIV>
        <DIV class="row">
          <div class="form-group">
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 主诊医生</label>
            <LABEL class="radio-inline"><INPUT name="sort7" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 主治医师</label>
            <LABEL class="radio-inline"><INPUT name="sort8" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          <DIV class="col-md-4 col-sm-4">
            <label class="col-md-6 col-sm-6 control-label"><input type="checkbox" class="minimal" > 住院医师</label>
            <LABEL class="radio-inline"><INPUT name="sort9" type="radio" value="asc"> 顺序 </LABEL> <LABEL class="radio-inline"><INPUT name="sort" type="radio" checked="" value="desc"> 逆序 </LABEL>
          </DIV>
          </div>
        </DIV>
    </div>
  </div>
                  </DIV>
                </DIV>
                <DIV class="panel-heading clearfix ">
                  <DIV class="col-sm-4 pull-center">
                    <BUTTON class="btn btn-success btn-sm" type="submit">查询病案</BUTTON>
                  </DIV>
                  <div class="col-sm-8 pull-left">
                    <label class="col-sm-4 control-label">保存查询历史</label>
                    <div class="checkbox col-sm-4 ">
                       <input type="checkbox" name="needSave" id="needSave" />
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
    <!-- jQuery 2.1.4 -->
    <script src="../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
  <!-- Bootstrap 3.3.5 -->
  <script src="../resources/bootstrap/js/bootstrap.min.js"></script>
  <!-- SlimScroll -->
  <script src="../resources/plugins/slimScroll/jquery.slimscroll.min.js"></script>
  <!-- FastClick -->
  <script src="../resources/plugins/fastclick/fastclick.min.js"></script>
  <!-- AdminLTE App -->
  <script src="../resources/dist/js/app.min.js"></script>
  <!-- AdminLTE for demo purposes -->
  <script src="../resources/dist/js/demo.js"></script>
</body>
</html>
