<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="数据导入" />
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
        <h1>${typeDesc}数据导入</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/import/list?type=${type}">${typeDesc}数据导入</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-default">
          <div class="box-header with-border">
            <h3 class="box-title">导入数据</h3>
            <div class="box-tools pull-right">
              <button class="btn btn-box-tool" data-widget="collapse">
                <i class="fa fa-minus"></i>
              </button>
              <button class="btn btn-box-tool" data-widget="remove">
                <i class="fa fa-remove"></i>
              </button>
            </div>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <div class="row">
              <div class="col-md-6">
                <div class="form-group">
                  <form action="/import/uploadFile" method="post" enctype="multipart/form-data" id="excelform">
                    <label style="font-size:16px; font-weight:bold;">上传文件：</label><br />
                    <p style="display:inline-block; border-style:solid; border-width:3px; border-color:#0CF; padding:20px;">
                    <input type="file" name="uploadfile" />
                    <input type="hidden" name="type" value="${type}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="button" id="exceluploadbtn" onclick="excelupload()" style="font-size:16px; background-color:#06F; padding:15px 68px; border-width:1px; color:#FFF;">上传</button>
                    </p>
                  </form>
                  <span id="upload-file-message"></span>
                </div>
                <!-- /.form-group -->
              </div>
              <!-- /.col -->
              <div class="col-md-6">
                <div class="form-group">
                  <label style="font-size:16px; font-weight:bold;">导入结果：</label> <br/>
                  <p style="display:inline-block; border-style:solid; border-width:3px; border-color:#5C0; padding:20px;">
                  <span id="importResult"></span>
                  </p>
                </div>
                <!-- /.form-group -->
              </div>
              <!-- /.col -->
            </div>
            <!-- /.row -->
          </div>
          <!-- /.box-body -->
        </div>
        <!-- /.box -->
        <div class="row">
          <div class="col-xs-12">
            <div class="box">
              <div class="box-header with-border">
            <h3 class="box-title">导入纪录</h3>
            <div class="box-tools pull-right">
              <button class="btn btn-box-tool" data-widget="collapse">
                <i class="fa fa-minus"></i>
              </button>
              <button class="btn btn-box-tool" data-widget="remove">
                <i class="fa fa-remove"></i>
              </button>
            </div>
          </div>
          <div class="box-body">
                <table id="example11" class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th>时间</th>
                      <th>操作者</th>
                      <th>文件名</th>
                      <th>记录数</th>
                      <th>成功数</th>
                      <th>新增</th>
                      <th>修改</th>
                    </tr>
                  </thead>
                  <tbody id="data-update">
                     <c:forEach var="item" items="${itemList}">
                      <tr  >
                        <td>${item.formatCtime}</td>
                        <td>${item.operator}</td>
                        <td>${item.srcName}</td>
                        <td>${item.total}</td>
                        <td>${item.success}</td>
                        <td>${item.insertTotal}</td>
                        <td>${item.updateTotal}</td>
                      </tr>
                    </c:forEach>
                   </tbody>
                  <tfoot>
                    <tr>
                      <th colspan="7"></th>
                      
                    </tr>
                  </tfoot>
                </table>
                <html:page url="./listPage?1=1" />
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
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
  <script>
  $(document).ready(function() {
	  //dataTableLoad();//
	} );
  			function dataTableLoad() {
  				$('#example11').DataTable( {
  			        "ajax": {
  			        	"processing": true,
  				        "serverSide": true,
  				        "url":"/import/list.json?type=${type}",
  				        "dataFilter": function(data){
  				            var json = jQuery.parseJSON( data );
  				            json.recordsTotal = json.totalElements;alert(json.recordsTotal);
  				            json.recordsFiltered = json.totalElements;
  				            json.data = json.content;
  				            return JSON.stringify( json ); // return JSON string
  				        }
  				        //"dataSrc": "content"
  			        },
  			        "columns": [
  			      	          { "data": "formatCtime" },
  			      	          { "data": "operator" },
  			      	          { "data": "srcName" },
  			      	          { "data": "total" },
  			      	          { "data": "success" },
  			      	          { "data": "insertTotal" },
  			  	              { "data": "updateTotal" }
  			      	      ]
  			    } );
  			}
			function excelupload(){ 
				  $('#exceluploadbtn').attr('disabled','disabled');
				  $('#exceluploadbtn').html('上传中...');
				  $.ajax({
						url : "/import/uploadFile",
						type : "POST",
						data : new FormData($("#excelform")[0]),
						enctype : 'multipart/form-data',
						processData : false,
						contentType : false,
						cache : false,
						success : function(data) {
							// Handle upload success
							$('#exceluploadbtn').html('上传成功，导入中...');
							$("#upload-file-message").text( "上传成功");
							$('#exceluploadbtn').removeAttr('disabled');
							$('#exceluploadbtn').html('再传一个');
							//updateTable();
							var result = "";
							result += "文件名："+data.srcName+"<br/>";
							result += "记录总数："+data.total+"<br/>";
							result += "成功总数："+data.success+"<br/>";
							result += "新增数："+data.insertTotal+"<br/>";
							result += "修改数："+data.updateTotal+"<br/>";
							$("#importResult").html(result);
						},
						error : function() {
							// Handle upload error
							$("#upload-file-message") .text( "上传失败");
							$('#exceluploadbtn').removeAttr('disabled');
							$('#exceluploadbtn').html('重新上传');
						}
					});
				}

			function updateTable() {
				$.ajax({
			        type: 'get',
			        url: '/import/list.json?type=${type}',
			        dataType: 'json',
			        success: function(data) {
			            var newRows="";
			            for (var i in data.content) {
			                newRows += "<tr><td>" + data.content[i].formatCtime + "</td>"
			                newRows += "<td>" + data.content[i].operator + "</td>"
			                newRows += "<td>" + data.content[i].srcName + "</td>"
			                newRows += "<td>" + data.content[i].total + "</td>"
			                newRows += "<td>" + data.content[i].success + "</td>"
			                newRows += "<td>" + data.content[i].insertTotal + "</td>"
			                newRows += "<td>" + data.content[i].updateTotal + "</td></tr>"
			            }
			            $("#data-update").html(newRows);
			        },
			        error: function() {
			            alert('fail');
			        }
			    });
			}
			
		</script>
</body>
</html>
