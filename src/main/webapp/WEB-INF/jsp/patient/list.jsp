<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/" %>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="病人列表"/>
</c:import>

<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">
	<!-- topbar -->
	<%@ include file="../include/topbar.jspf" %>
	<!-- left menu-->
	<%@ include file="../include/menu.jspf" %>
	
	<div class="content-wrapper" >
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            病人列表
          </h1>
          <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
            <li class="active"><a href="./list">病人列表</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">

              <div class="box">
                <div class="box-header">
                  <div class="box-content">
						<html:form id="patientForm" modelAttribute="pageRequest" formUrl="/patient/listPage">
						  <div class="row">
                            <html:inputField labelCss="col-sm-4" css="col-sm-4" name="name" label="病人姓名"/>
                            <div class="col-sm-4"><button type="submit" class="btn btn-primary pull-left">搜</button></div>
                            <!-- <div class="col-sm-4"><input type="button" value="导出数据" onclick="download()"/></div> -->
                            <div class="col-sm-4">
                              <A class="btn btn-success" data-toggle="modal" data-target="#csvOutput">导出数据 </A>
                            </div>
                          </div>
						</html:form>
					</div>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <table id="example11" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>病案号</th>
                        <th>姓名</th>
                        <th>实际年龄</th>
                        <th>入院年龄</th>
                        <th>性别</th>
                        <th>首页数</th>
                        <th>入院数</th>
                        <th>手术数</th>
                        <th>出院数</th>
                        <th>主要诊断</th>
                        <th>住院医师</th>
                        <th>主诊医师</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.pId}</td>
                        <td><a href="./?id=${item.id} ">${item.name}</a> </td>
                        <td>${item.actualAge}</td>
                        <td>${item.age}</td>
                        <td>${item.sexDesc}</td>
                        <td>${fn:length(item.frontRecords)} </td>
                        <td>${fn:length(item.inRecords)}</td>
                        <td>${fn:length(item.operations)}</td>
                        <td>${fn:length(item.outRecords)}</td>
                        <td>${item.frontRecords.get(0).mainDiag}</td>
                        <td>${item.frontRecords.get(0).ZY_DOCTOR_NAME}</td>
                        <td>${item.frontRecords.get(0).ZZHEN_DOCTOR_NAME}</td>
                      </tr>
                      </c:forEach>
                    </tbody>
                    <tfoot>
                      <tr>
                      	<th colspan="12"></th>
                      </tr>
                    </tfoot>
                  </table>
                  <html:page url="./listPage?1=1" />
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
    <DIV tabindex="-1" class="modal fade" id="csvOutput" role="dialog" aria-labelledby="myModalLabel">
      <DIV class="modal-dialog" role="document">
        <DIV class="modal-content">
          <DIV class="modal-header">
            <BUTTON class="close" aria-label="Close" type="button" data-dismiss="modal">
              <SPAN aria-hidden="true">×</SPAN>
            </BUTTON>
            <H4 class="modal-title" id="myModalLabel">导出选项</H4>
          </DIV>
          <DIV class="modal-body">
            <DIV class="container-fluid" id="checkboxes">
              <DIV class="row">
                <LABEL class="checkbox-inline"><INPUT id="name" type="checkbox" checked="" value="name">
                  患者姓名 </LABEL> <LABEL class="checkbox-inline"><INPUT id="sex" type="checkbox" checked="" value="sex">
                  性别 </LABEL> <LABEL class="checkbox-inline"><INPUT id="age" type="checkbox" checked="" value="age">
                  年龄 </LABEL> <LABEL class="checkbox-inline"><INPUT id="marriageStatus" type="checkbox" checked=""
                  value="marriageStatus"> 婚姻状态 </LABEL> <LABEL class="checkbox-inline"><INPUT id="nationality"
                  type="checkbox" checked="" value="nationality"> 民族 </LABEL> <LABEL class="checkbox-inline"><INPUT
                  id="country" type="checkbox" checked="" value="country"> 国别 </LABEL>
              </DIV>
              <DIV class="row">
                <LABEL class="checkbox-inline"><INPUT id="dischargeDept" type="checkbox" checked=""
                  value="dischargeDept"> 出院科室 </LABEL> <LABEL class="checkbox-inline"><INPUT id="dischargeWard"
                  type="checkbox" checked="" value="dischargeWard"> 出院病房 </LABEL> <LABEL class="checkbox-inline"><INPUT
                  id="dischargeTime" type="checkbox" checked="" value="dischargeTime"> 出院时间 </LABEL> <LABEL
                  class="checkbox-inline"><INPUT id="KZR_DOCTOR_NAME" type="checkbox" checked=""
                  value="KZR_DOCTOR_NAME"> 科主任 </LABEL> <LABEL class="checkbox-inline"><INPUT
                  id="ZZ_DOCTOR_NAME" type="checkbox" checked="" value="ZZ_DOCTOR_NAME"> 主治医师 </LABEL> <LABEL
                  class="checkbox-inline"><INPUT id="ZY_DOCTOR_NAME" type="checkbox" checked=""
                  value="ZY_DOCTOR_NAME"> 住院医师 </LABEL>
              </DIV>
              <DIV class="row">
                <LABEL class="checkbox-inline"><INPUT id="adminission_no" type="checkbox" checked=""
                  value="adminission_no"> 病案号 </LABEL> <LABEL class="checkbox-inline"><INPUT id="numberOfTimes"
                  type="checkbox" checked="" value="numberOfTimes"> 住院次数 </LABEL> <LABEL class="checkbox-inline"><INPUT
                  id="admissionDept" type="checkbox" checked="" value="admissionDept"> 住院科室 </LABEL> <LABEL
                  class="checkbox-inline"><INPUT id="admissionWard" type="checkbox" checked=""
                  value="admissionWard"> 住院病房 </LABEL> <LABEL class="checkbox-inline"><INPUT id="admissionTime"
                  type="checkbox" checked="" value="admissionTime"> 住院时间 </LABEL> <LABEL class="checkbox-inline"><INPUT
                  id="mainDiag" type="checkbox" checked="" value="mainDiag"> 诊断信息 </LABEL>
              </DIV>
              <DIV class="row">
              
              </DIV>          
            </DIV>
          </DIV>
          <DIV class="modal-footer">
            <BUTTON class="btn btn-warning" onclick="confirmHandle()" type="button">Save changes</BUTTON>
            <a class="btn btn-success" href="./export">导出EXCEL文件</a>
            <BUTTON class="btn btn-primary" type="button" data-dismiss="modal">关闭</BUTTON>
          </DIV>
        </DIV>
      </DIV>
    </DIV>
    <%@ include file="../include/footer.jspf" %>
	<%@ include file="../include/sidebar.jspf" %>
	</div><!-- ./wrapper -->
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
    <script>
    function download(){
        var url="./download";
        window.open(url);
    };
    function confirmHandle() {
    	var selected = [];
    	var href = "./export";
    	$('#checkboxes input:checked').each(function() {
    		selected.push($(this).attr('id'));
    	});
    	//console.log('Seleced', selected);
    	//console.log("/auth/index.php/Admin/MsgManage/ouputCsv/sort/adminission_no/order/desc.html");
    	window.open(href + '?fields=' + selected.join(','));
    }
    </script>
	
</body>
</html>
