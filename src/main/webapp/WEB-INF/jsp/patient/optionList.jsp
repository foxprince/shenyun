<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/" %>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="自定义选项"/>
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
            选项列表
          </h1>
          <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
            <li class="active"><a href="./list">自定义选项列表</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">

              <div class="box">
                
                <div class="box-body">
                  <table id="example11" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>时间</th>
                        <th>名称</th>
                        <th>包含选项个数</th>
                        <th>操作</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.formatCtime}</td>
                        <td>${item.name}</td>
                        <td>${fn:length(item.fields)}</td>
                        <td><a href="/customeOption/edit?id=${item.id}"><button class="btn btn-block btn-info">查看/修改</button></a></td>
                        <td><a href="/customeOption/delete?id=${item.id}"><button class="btn btn-block btn-warning">删除</button></a></td>
                      </tr>
                      </c:forEach>
                    </tbody>
                    <tfoot>
                      <tr>
                      	<th colspan="11">
                        <DIV class="col-sm-12 pull-center">
                    <a class="btn btn-success btn-sm" href="/customeOption/add">添加</a>
                  </DIV>
                        </th>
                      </tr>
                    </tfoot>
                  </table>
                  
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
	<!-- jQuery 2.1.4 -->
    <script src="../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
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
    <!-- page script -->
    <script src="../resources/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="../resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
    
  
	<%@ include file="../include/footer.jspf" %>
	<%@ include file="../include/sidebar.jspf" %>
	</div><!-- ./wrapper -->
	
	
</body>
</html>
