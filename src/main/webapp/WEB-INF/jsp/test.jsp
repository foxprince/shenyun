<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
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
		<tfoot>
			<tr>
				<th>时间</th>
				<th>操作者</th>
				<th>文件名</th>
				<th>记录数</th>
				<th>成功数</th>
				<th>新增</th>
				<th>修改</th>
			</tr>
		</tfoot>
	</table>
</body>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script>
$(document).ready(function() {
    $('#example11').DataTable( {
        "ajax": {
        	"processing": true,
	        "serverSide": true,
	        "url":"/import/list.json?type=cxz",
	        dataFilter: function(data) {
	            var json = jQuery.parseJSON( data );
	            json.recordsTotal = 100;//json.totalElements;
	            json.recordsFiltered = 100;//json.totalElements;
	            json.data = json.content;
	            json.draw = 1;
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
} );
</script>
</html>