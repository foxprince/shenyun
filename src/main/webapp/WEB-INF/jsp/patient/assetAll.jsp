<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="外来资料全关联" />
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
    
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>外来资料全关联</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./assetAll">外来资料</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-default">
    <div class="box-header with-border">
      <h3 class="box-title">已导入的外来资料</h3>
      <!-- search form -->
          <form action="/patient/queryAssetNr" method="get"  class="sidebar-form">
            <div class="input-group">
              <input type="text" id="queryName" name="name" class="form-control" placeholder="病人...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
          </form>
    </div>
<div class="box-body">
  <div class="box box-info">
    <div class="box-header">
      <h3 class="box-title">病人列表</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach  var="item" items="${itemList}">
          <label><a href="/patient/query?name=${item}">${item}</a></label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
      </div>
      <html:page url="?1=1" />
    </div>
    </div>


  </div>
  </div>
            
          
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
  	<%@ include file="../include/script.jspf"%>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>

</body>
</html>
