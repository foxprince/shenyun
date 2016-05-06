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
                        <th>年龄</th>
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
                      	<th colspan="11"></th>
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
	<!-- page script -->
    <script src="../plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="../plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
      $(function () {
    	  $("#example1").dataTable( {
    		 "processing": true,
    		 "serverSide": true,
    	     "ajax": {
                 "url": '/patient/dataTableList?${_csrf.parameterName}=${_csrf.token}',
                 "type": 'POST',
                 //"data": function ( d ) { return JSON.stringify( d );}
             },
             "columns": [
    	                 { "name": "id","data": "id" },{ "name": "name","data": "name" },
    	                 { "name": "fs"  ,"data": "fs"   },
    	                 { "name": "is"  ,"data": "is"   },
    	                 { "name": "os"  ,"data": "os"   },
    	                 { "name": "xs"  ,"data": "xs"   }
    	               ],
              "columnDefs": [ 
                              {
            	    "targets": 1,
            	    "data": "name",
            	    "render": function ( data, type, full, meta ) {
            	    	return '<a href="./?id='+full+'">'+data+'</a>';
            	    }
            	  } ,{
            	    "targets": 0,
            	    "data": "name",
            	    "render": function ( data, type, full, meta ) {
            	    	return '<a href="./?id='+full+'">'+data+'</a>';
            	    }
            	  } ]
    	    } );
      });
    </script>
	
</body>
</html>