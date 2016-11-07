<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/" %>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="历史查询纪录"/>
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
            <li class="active"><a href="./list">历史查询纪录</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">

              <div class="box">
                <div class="box-header">
                  <div class="box-content">
						<html:form id="patientForm" modelAttribute="pageRequest" formUrl="/searchModel/listPage">
						  <table class="table table-condensed">
							<tr bgcolor="#F3F3F3">
								<td>
					  <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime2">开始</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <INPUT name="searchBegin" class="form-control" id="dischargetime2" type="date">
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime2">截止</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <INPUT name="searchEnd" class="form-control" id="dischargetime2" type="date">
                          </DIV>
                        </DIV>
                      </DIV>
								</td>
								<td align="center" >
									  <button type="submit" class="btn btn-primary pull-left">搜</button>
								</td>
							</tr>
						  </table>
						</html:form>
					</div>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <table id="example11" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>时间</th>
                        <th>操作者</th>
                        <th>查询条件</th>
                        <th>记录数</th>
                        <th>操作</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.formatCtime}</td>
                        <td>${item.operator}</td>
                        <td>${item.fieldsDesc}</td>
                        <td>${item.hits}</td>
                        <td><a href="/searchModel/reSearch?searchId=${item.id}"><button class="btn btn-block btn-info">再次查询</button></a></td>
                        <td><a href="/searchModel/delete?id=${item.id}"><button class="btn btn-block btn-warning">删除</button></a></td>
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
      
	<!-- jQuery 2.1.4 -->
    <script src="../resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    
	<%@ include file="../include/footer.jspf" %>
	<%@ include file="../include/sidebar.jspf" %>
	</div><!-- ./wrapper -->
	
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
