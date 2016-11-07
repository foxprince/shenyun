<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="病人首页"/>
</c:import>
    <script>
    function test(id) {
    	$('#generatePdfBtn').html('生成中...');
    	$.get(
				"pdf", 
				{id:id}, 
				function(data){
					if(data=='error'){
						alert('未知错误，请稍后重试'+data);
					}
					else {
						$('#generatePdfBtn').html('点击下载');
					}
				}
		);
    };
    </script>
    
<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">
	<!-- topbar -->
	<%@ include file="../include/topbar.jspf" %>
	<!-- left menu-->
	<%@ include file="../include/menu.jspf" %>
	
	<div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            病人首页
          </h1>
          <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
            <li><a href="./listPage">病人列表</a></li>
            <li class="active"><a href="./">病人详情</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">

          <div class="row">
            <div class="col-md-3">

              <!-- Profile Image -->
              <div class="box box-primary">
                <div class="box-body box-profile">
                  <img class="profile-user-img img-responsive img-circle" src="../resources/dist/img/${patient.sex eq 1?"Patient-Male.png":"Patient-Female.png"}" alt="病人照片">
                  <h3 class="profile-username text-center">${patient.name} </h3>
                  <p class="text-muted text-center">今年${patient.actualAge}岁，${patient.sexDesc} </p>

                  <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                      <b>出生日期</b> <p class="pull-right">${patient.formatDateOfBirth}</p>
                    </li>
                    <li class="list-group-item">
                      <b>住院次数</b> <p class="pull-right">${fn:length(patient.inRecords)}</p>
                    </li>
                    <li class="list-group-item">
                      <b>手术次数</b> <p class="pull-right">${fn:length(patient.operations)}</p>
                    </li>
                    <li class="list-group-item">
                      <b>病案号</b> <p class="pull-right">${patient.pId}</p>
                    </li>
                  </ul>

          <div class='row no-print'> <div class='col-xs-12'><a href='./print?id=${patient.id}' target='_blank' class='btn btn-default'><i class='fa fa-print'></i> 打印</a><a id='generatePdfBtn'  href="./zip?id=${patient.id }&patientNo=${ patient.pId}" class='btn btn-primary pull-right' style='margin-right: 5px;'><i class='fa fa-download'></i> 导出压缩文件</a> </div> </div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->

              <!-- About Me Box -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">详细信息</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <strong><i class="fa fa-book margin-r-5"></i>  身份证号码</strong>
                  <p class="text-muted"> ${patient.certNo } </p>
                  <hr>
                  <strong><i class="fa fa-map-marker margin-r-5"></i> 家庭住址</strong>
                  <p class="text-muted">${patient.frontRecords.get(0).homeAddress} </p>
                  <hr>
                  <strong><i class="glyphicon glyphicon-earphone"></i>  联系电话</strong>
                  <p class="text-muted">${patient.frontRecords.get(0).mobilephone} </p>
                  <hr>
                  <strong><i class="fa fa-pencil margin-r-5"></i> 诊断</strong>
                  <p>
                    <span class="label label-success">${patient.frontRecords.get(0).mainDiag}</span>
                    <%-- <span class="label label-primary">${patient.inRecords.get(0).confirmDiag.detail}</span>
                    <span class="label label-warning">${patient.outRecords.get(0).outDiagnosis }</span> --%>
                  </p>
                  <hr>

                  <strong><i class="fa fa-file-text-o margin-r-5"></i> 备注</strong>
                  <p  class="text-muted">${patient.frontRecords.get(0).country}</p>
                  <p  class="text-muted">出生地：${patient.frontRecords.get(0).birthplace}</p>
                  <p class="text-muted">籍贯：${patient.frontRecords.get(0).nativeplace}</p>
                  <p class="text-muted">民族：${patient.frontRecords.get(0).nationality}</p>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div><!-- /.col -->
            <div class="col-md-9">
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                  <li class="active"><a href="#front" data-toggle="tab">病案首页</a></li>
                  <li><a href="#inHospital" data-toggle="tab">入院纪录</a></li>
                  <li><a href="#operation" data-toggle="tab">手术记录</a></li>
                  <li><a href="#outHospital" data-toggle="tab">出院纪录</a></li>
                  <li><a href="#pacs" data-toggle="tab">PACS影像</a></li>
                  <li><a href="#dsa" data-toggle="tab">DSA影像</a></li>
                  <c:forEach var="item" items="${patient.extendMap}">
                    <li><a href="#${item.key }" data-toggle="tab">${item.value.typeDesc}</a></li>
                  </c:forEach>
                </ul>
                <div class="tab-content">
                  <%@ include file="front.jspf" %>
                  <%@ include file="inHospital.jspf" %>
                  <%@ include file="operation.jspf" %>
                  <%@ include file="outHospital.jspf" %>
                  <%@ include file="pacs.jspf" %>
                  <%@ include file="dsa.jspf" %>
                  
                  <c:forEach var="item" items="${patient.extendMap}">
                    <div class="tab-pane" id="${item.key }">
                    <!-- The timeline -->
                    <section class="invoice">
                      <!-- title row -->
                      <div class="row">
                        <div class="col-xs-12">
                          <h2 class="page-header">
                            <i class="fa fa-globe"></i> ${item.value.typeDesc}
                            <small class="pull-right"></small>
                          </h2>
                        </div><!-- /.col -->
                      </div>
                      <div class='row margin-bottom'>
                        <spring:eval expression="T(cn.anthony.util.RefactorUtil).getObjectParaMap(item.value)" var="fieldsMap" />
                        <ul>
                        <c:forEach var="item2" items="${fieldsMap}">
                            <li><p><strong>${item2.key }：</strong></p> <pre>${item2.value }</pre></li>
                        </c:forEach>
                        </ul>
                      </div>
                    </section>
                  </div><!-- /.tab-pane -->
                  </c:forEach>
                </div><!-- /.tab-content -->
              </div><!-- /.nav-tabs-custom -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
	
	<%@ include file="../include/sidebar.jspf" %>
	<%@ include file="../include/script.jspf" %>
    <%@ include file="../include/footer.jspf" %>
  
	</div>
</body>
</html>
