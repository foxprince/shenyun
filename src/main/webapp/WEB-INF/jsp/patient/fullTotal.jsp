<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="高级统计" />
</c:import>
  <style>
  .detectCanvas{cursor:pointer;}
  .box.box-warning {border-left:1px solid #f39c12;border-right:1px solid #f39c12;border-bottom:1px solid #f39c12;}
  .box-warning .box-header{border-bottom:1px solid #f39c12;}
  .box.box-danger {border-left:1px solid #f56954;border-right:1px solid #f56954;border-bottom:1px solid #f56954;}
  .box-danger .box-header{border-bottom:1px solid #f56954;}
  .box.box-primary {border-left:1px solid #3c8dbc;border-right:1px solid #3c8dbc;border-bottom:1px solid #3c8dbc;}
  .box-primary .box-header{border-bottom:1px solid #3c8dbc;}
  .box.box-info {border-left:1px solid #00c0ef;border-right:1px solid #00c0ef;border-bottom:1px solid #00c0ef;}
  .box-info .box-header{border-bottom:1px solid #00c0ef;}
  .box.box-success {border-left:1px solid #00a65a;border-right:1px solid #00a65a;border-bottom:1px solid #00a65a;}
  .box-success .box-header{border-bottom:1px solid #00a65a;}
  .form-group{margin:0px;}
  </style>

<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
  <form id="totalForm"  action="/total/initTotal">
    
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>高级统计</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="initFullTotal">高级统计</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-default">
    <div class="box-header with-border">
      <h3 class="box-title">请选择组合统计条件</h3>
    </div>
<div class="box-body">
  
    <div class="box box-primary">
      <div class="box-header">
        <h3 class="box-title">统计选项</h3>
      </div><!-- /.box-header -->
      <div class="box-body pad">
        <div class="form-group">
          <c:forEach var="item" items="${totalOptions}">
             <label><input type="checkbox" class="minimal" name="fields" value="${item.name}" >${item.label}</label>
            <i style="margin:0px 10px">|</i>
          </c:forEach>
         </div>
      </div>
    </div>
    
    <div class="box box-info">
      <div class="box-header">
        <h3 class="box-title">统计条件</h3>
      </div><!-- /.box-header -->
      <div class="box-body pad">
        <div class="form-group">
          <c:forEach var="item" items="${whereOptions}">
             <spring:eval expression="T(cn.anthony.boot.util.Constant).getKeyDesc(item.key)" var="keyDesc" />
             <label><input type="hidden" class="opt" name="${item.key}" value="${item.value}" >${keyDesc}：${item.value}</label>
            <i style="margin:0px 10px">|</i>
          </c:forEach>
         </div>
      </div>
    </div>

</div>
  </div>
  
  
  <div class="pull-center">
  <input type="hidden" name="clause" id="clause"/>
  <button type="submit" class="btn btn-info" onclick="confirmHandle(); return false;">提&nbsp;交</button></div>
  </form>         
            
          
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
  function confirmHandle() {
  	var selected = [];
  	$('.opt').each(function() {
  		selected.push($(this).attr('name')+":"+$(this).attr('value'));
  	});
  	$('#clause').val(selected.join(','));
  	//alert($('#clause').val());
  	$('#totalForm').submit();
  }
  </script>
</body>
</html>
