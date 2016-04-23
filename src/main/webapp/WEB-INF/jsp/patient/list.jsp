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
            <li class="active"><a href="./listPage">病人列表</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">

              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Data Table With Full Features</h3>
                  <div class="box-content">
						<html:form id="patientForm" modelAttribute="pageRequest" formUrl="/patient/listPage">
						  <table class="table table-condensed">
							<tr bgcolor="#F3F3F3">
								<td>
									<html:inputField name="name" label="病人姓名"/>
								</td>
								<td align="center" >
									  <button type="submit" class="btn btn-primary">搜</button>
									  <input type="hidden" name="action" value="query"/>
								</td>
							</tr>
						  </table>
						</html:form>
					</div>
                </div><!-- /.box-header -->
                <div class="box-body">
                  	
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>姓名</th>
                        <th>首页纪录数</th>
                        <th>入院纪录数</th>
                        <th>手术记录数</th>
                        <th>出院记录数</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td><a href="./?id=${item.id} ">${item.name}</a> </td>
                        <td>${fn:length(patient.frontRecords)} </td>
                        <td>${fn:length(patient.inRecords)}</td>
                        <td>${fn:length(patient.operations)}</td>
                        <td>${fn:length(patient.outRecords)}</td>
                      </tr>
                      </c:forEach>
                    </tbody>
                    <tfoot>
                      <tr>
                        
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
	</div>
	
	<%@ include file="../include/script.jspf" %>
	<!-- page script -->
    <script src="../plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="../plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
      $(function () {
        $("#example1").DataTable();
      });
    </script>
	
</body>
</html>
