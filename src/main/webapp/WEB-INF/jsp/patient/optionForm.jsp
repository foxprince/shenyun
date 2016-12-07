<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="自定义选项" />
</c:import>
<style>
.detectCanvas {
	cursor: pointer;
}

.box.box-warning {
	border-left: 1px solid #f39c12;
	border-right: 1px solid #f39c12;
	border-bottom: 1px solid #f39c12;
}

.box-warning .box-header {
	border-bottom: 1px solid #f39c12;
}

.box.box-danger {
	border-left: 1px solid #f56954;
	border-right: 1px solid #f56954;
	border-bottom: 1px solid #f56954;
}

.box-danger .box-header {
	border-bottom: 1px solid #f56954;
}

.box.box-primary {
	border-left: 1px solid #3c8dbc;
	border-right: 1px solid #3c8dbc;
	border-bottom: 1px solid #3c8dbc;
}

.box-primary .box-header {
	border-bottom: 1px solid #3c8dbc;
}

.box.box-info {
	border-left: 1px solid #00c0ef;
	border-right: 1px solid #00c0ef;
	border-bottom: 1px solid #00c0ef;
}

.box-info .box-header {
	border-bottom: 1px solid #00c0ef;
}

.box.box-success {
	border-left: 1px solid #00a65a;
	border-right: 1px solid #00a65a;
	border-bottom: 1px solid #00a65a;
}

.box-success .box-header {
	border-bottom: 1px solid #00a65a;
}

.form-group {
	margin: 0px;
}
</style>

<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">
		<!-- topbar -->
		<%@ include file="../include/topbar.jspf"%>
		<!-- left menu-->
		<%@ include file="../include/menu.jspf"%>

		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>自定义选项</h1>
				<ol class="breadcrumb">
					<li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
					<li class="active"><a href="./optionList">自定义选项列表</a></li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="box box-default">
					<form:form modelAttribute="customeOption" method="POST"
						id="add-user-form" action="/customeOption/${customeOption.action}">
						<div class="box-header with-border">
							<h3 class="box-title">请选择条件</h3>
						</div>
						<div class="box-body">
							<div class="box box-info">
								<div class="box-header">
									<h3 class="box-title">基本条件</h3>
									<label><input id="patientCheckAll" type="checkbox">全选</label>

									<!-- tools box -->
									<div class="pull-right box-tools">
										<button class="btn btn-info btn-sm" data-widget="collapse"
											data-toggle="tooltip" title="Collapse">
											<i class="fa fa-minus"></i>
										</button>
										<button class="btn btn-info btn-sm" data-widget="remove"
											data-toggle="tooltip" title="Remove">
											<i class="fa fa-times"></i>
										</button>
									</div>
									<!-- /. tools -->
								</div>
								<!-- /.box-header -->

								<div class="box-body pad">
									<div class="form-group">
										<c:forEach var="item" items="${patientOptions}">
											<label><form:checkbox class="minimal" op="patient"
													value="${item.name}" path="fields" />${item.label}</label>
											<i style="margin: 0px 10px">|</i>
										</c:forEach>
									</div>
								</div>
							</div>

							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">病案首页</h3>
									<label><input id="frontPageCheckAll" type="checkbox">全选</label>
									<!-- tools box -->
									<div class="pull-right box-tools">
										<button class="btn btn-primary btn-sm" data-widget="collapse"
											data-toggle="tooltip" title="Collapse">
											<i class="fa fa-minus"></i>
										</button>
										<button class="btn btn-primary btn-sm" data-widget="remove"
											data-toggle="tooltip" title="Remove">
											<i class="fa fa-times"></i>
										</button>
									</div>
									<!-- /. tools -->
								</div>
								<!-- /.box-header -->
								<div class="box-body pad">
									<div class="form-group">
										<c:forEach var="item" items="${frontPageOptions}">
											<label><form:checkbox class="minimal"
													value="${item.name}" op="frontPage" path="fields" />${item.label}</label>
											<i style="margin: 0px 10px">|</i>
										</c:forEach>
									</div>
								</div>
							</div>

							<div class="box box-warning">
								<div class="box-header">
									<h3 class="box-title">入院选项</h3>
									<label><input id="inHospitalCheckAll" type="checkbox">全选</label>
									<!-- tools box -->
									<div class="pull-right box-tools">
										<button class="btn btn-danger btn-sm" data-widget="collapse"
											data-toggle="tooltip" title="Collapse">
											<i class="fa fa-minus"></i>
										</button>
										<button class="btn btn-danger btn-sm" data-widget="remove"
											data-toggle="tooltip" title="Remove">
											<i class="fa fa-times"></i>
										</button>
									</div>
									<!-- /. tools -->
								</div>
								<!-- /.box-header -->
								<div class="box-body pad">
									<div class="form-group">
										<c:forEach var="item" items="${inOptions}">
											<label><form:checkbox class="minimal"
													value="${item.name}" op="inHospital" path="fields" />${item.label}</label>
											<i style="margin: 0px 10px">|</i>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="box box-danger">
								<div class="box-header">
									<h3 class="box-title">手术选项</h3>
									<label><input id="operationCheckAll" type="checkbox">全选</label>
									<!-- tools box -->
									<div class="pull-right box-tools">
										<button class="btn btn-danger btn-sm" data-widget="collapse"
											data-toggle="tooltip" title="Collapse">
											<i class="fa fa-minus"></i>
										</button>
										<button class="btn btn-danger btn-sm" data-widget="remove"
											data-toggle="tooltip" title="Remove">
											<i class="fa fa-times"></i>
										</button>
									</div>
									<!-- /. tools -->
								</div>
								<!-- /.box-header -->
								<div class="box-body pad">
									<div class="form-group">
										<c:forEach var="item" items="${operationOptions}">
											<label><form:checkbox class="minimal"
													value="${item.name}" op="operation" path="fields" />${item.label}</label>
											<i style="margin: 0px 10px">|</i>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">出院选项</h3>
									<label><input id="outHospitalCheckAll" type="checkbox">全选</label>
									<!-- tools box -->
									<div class="pull-right box-tools">
										<button class="btn btn-success btn-sm" data-widget="collapse"
											data-toggle="tooltip" title="Collapse">
											<i class="fa fa-minus"></i>
										</button>
										<button class="btn btn-success btn-sm" data-widget="remove"
											data-toggle="tooltip" title="Remove">
											<i class="fa fa-times"></i>
										</button>
									</div>
									<!-- /. tools -->
								</div>
								<!-- /.box-header -->
								<div class="box-body pad">
									<div class="form-group">
										<c:forEach var="item" items="${outOptions}">
											<label><form:checkbox class="minimal"
													value="${item.name}" op="outHospital" path="fields" />${item.label}</label>
											<i style="margin: 0px 10px">|</i>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
						<div class="box-header with-border">
							<label>选项名称：</label>
							<form:input path="name" />
							<input type="hidden" name="id" value="${customeOption.id }" />
						</div>
				</div>

				<div class="pull-center">
					<button type="submit" class="btn btn-info">提&nbsp;交</button>
				</div>
				</form:form>


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

	<script>
    $("#patientCheckAll").click( 
      function(){ 
        if(this.checked){ 
          $("input[op='patient']").each(function(){this.checked=true;}); 
        }else{ 
          $("input[op='patient']").each(function(){this.checked=false;}); 
        } 
      } 
    );
    $("#frontPageCheckAll").click( 
          function(){ 
            if(this.checked){ 
              $("input[op='frontPage']").each(function(){this.checked=true;}); 
            }else{ 
              $("input[op='frontPage']").each(function(){this.checked=false;}); 
            } 
          } 
        );
    $("#inHospitalCheckAll").click( 
          function(){ 
            if(this.checked){ 
              $("input[op='inHospital']").each(function(){this.checked=true;}); 
            }else{ 
              $("input[op='inHospital']").each(function(){this.checked=false;}); 
            } 
          } 
        );
    $("#operationCheckAll").click( 
          function(){ 
            if(this.checked){ 
              $("input[op='operation']").each(function(){this.checked=true;}); 
            }else{ 
              $("input[op='operation']").each(function(){this.checked=false;}); 
            } 
          } 
        );
    $("#outHospitalCheckAll").click( 
          function(){ 
            if(this.checked){ 
              $("input[op='outHospital']").each(function(){this.checked=true;}); 
            }else{ 
              $("input[op='outHospital']").each(function(){this.checked=false;}); 
            } 
          } 
        );
    
    </script>
</body>
</html>
