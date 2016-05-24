<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="高级查询" />
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
        <h1>高级查询</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./initFullSearch">高级查询</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-default">
    <div class="box-header with-border">
      <h3 class="box-title">请选择查询条件</h3>
    </div>
<div class="box-body">
  <div class="box box-info">
    <div class="box-header">
      <h3 class="box-title">基本条件</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach var="item" items="${patientOptions}">
          <label><input type="checkbox" class="minimal" id="${item.name}" inputType="${item.inputType}" firstSelName="${item.name}_andOr" textName="${item.name}" lastSelName="${item.name}_option">${item.label}</label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
      </div>
    </div>
    </div>

    <div class="box box-primary">
    <div class="box-header">
      <h3 class="box-title">病案首页</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-primary btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-primary btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach var="item" items="${frontPageOptions}">
           <label><input type="checkbox" class="minimal" id="${item.name}" inputType="${item.inputType}" firstSelName="${item.name}_andOr" textName="${item.name}" lastSelName="${item.name}_option">${item.label}</label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
       </div>
    </div>
    </div>

    <div class="box box-warning">
    <div class="box-header">
      <h3 class="box-title">入院选项</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-danger btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-danger btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach var="item" items="${inOptions}">
           <label><input type="checkbox" class="minimal" id="${item.name}" inputType="${item.inputType}" firstSelName="${item.name}_andOr" textName="${item.name}" lastSelName="${item.name}_option">${item.label}</label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
      </div>
    </div>
    </div>
  <div class="box box-danger">
    <div class="box-header">
      <h3 class="box-title">手术选项</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-danger btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-danger btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach var="item" items="${operationOptions}">
           <label><input type="checkbox" class="minimal" id="${item.name}" inputType="${item.inputType}" firstSelName="${item.name}_andOr" textName="${item.name}" lastSelName="${item.name}_option">${item.label}</label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
      </div>
    </div>
    </div>
  <div class="box box-success">
    <div class="box-header">
      <h3 class="box-title">出院选项</h3>
      <!-- tools box -->
      <div class="pull-right box-tools">
      <button class="btn btn-success btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
      <button class="btn btn-success btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
      </div><!-- /. tools -->
    </div><!-- /.box-header -->
    <div class="box-body pad">
      <div class="form-group">
        <c:forEach var="item" items="${outOptions}">
           <label><input type="checkbox" class="minimal" id="${item.name}" inputType="${item.inputType}" firstSelName="${item.name}_andOr" textName="${item.name}" lastSelName="${item.name}_option">${item.label}</label>
          <i style="margin:0px 10px">|</i>
        </c:forEach>
      </div>
    </div>
  </div>
</div>
  </div>
  <html:form id="patientForm" modelAttribute="pageRequest" formUrl="/patient/fullSearch">
  <div class="box box-default" id="option-Content">
    <div class="box-header with-border">
      <h3 class="box-title">请填写条件值</h3>
    </div>
    <script id="template" type="text/template">  
    <div class="panel-body"  style="padding:15px 15px;" id="$key$">
      <div class="col-md-2">
        <div class="form-group">
          <select class="form-control" name="$firstSelName$">
          <option value='and'>与</option>
          <option value='or'>或</option>
          </select>
        </div>
      </div>
      <div class="col-md-1" style="padding:6px 15px;">$option$</div>
      <div class="col-md-2">
        <div class="form-group">
          <select class="form-control" name="$lastSelName$">
          <option value='contains'>包含</option>
          <option value='eq'>等于</option>
          <option value='ge'>大于</option>
          <option value='le'>小于</option>
          <option  value='notIn'>不包含</option>
          <option  value='ne'>不等于</option>
          </select>
        </div>
      </div>
      <div class="col-md-4">
        <input type="$inputType$" class="form-control" placeholder="输入文本" name="$textName$">
      </div>
      <div class="col-md-1">
        <div class="detectCanvas" style="margin-top:6px;" onclick="del(this)">
          <i class="fa fa-fw fa-close"></i>删除
        </div>
      </div>
      <div class="col-md-1">
        <div class="detectCanvas" style="margin-top:6px;" onclick="copy(this)">
          <i class="fa fa-fw fa-pencil"></i>复制
        </div>
      </div>
    </div>
    </script>
    
        <c:forEach var="item" items="${queryOptions}">
    <div class="panel-body"  style="padding:15px 15px;" id="${item.key}">
      <div class="col-md-2">
        <div class="form-group">
          <select class="form-control" name="${item.key}_andOr">
          <option value='and'>与</option>
          <option value='or'>或</option>
          </select>
        </div>
      </div>
      <div class="col-md-1" style="padding:6px 15px;">${item.keyDesc}</div>
      <div class="col-md-2">
        <div class="form-group">
          <select class="form-control" name="${item.key}_option">
          <option value='contains'>包含</option>
          <option value='eq'>等于</option>
          <option value='ge'>大于</option>
          <option value='le'>小于</option>
          <option  value='notIn'>不包含</option>
          <option  value='ne'>不等于</option>
          </select>
        </div>
      </div>
      <div class="col-md-4">
        <input type="${item.inputType}" class="form-control" placeholder="输入文本" name="${item.key}" value="${item.value }">
      </div>
      <div class="col-md-1">
        <div class="detectCanvas" style="margin-top:6px;" onclick="del(this)">
          <i class="fa fa-fw fa-close"></i>删除
        </div>
      </div>
      <div class="col-md-1">
        <div class="detectCanvas" style="margin-top:6px;" onclick="copy(this)">
          <i class="fa fa-fw fa-pencil"></i>复制
        </div>
      </div>
    </div>
        </c:forEach>
    
  </div>
  <div class="pull-center">
    <label class="col-sm-4 control-label">保存查询历史</label>
                    <div class="checkbox col-sm-4 ">
                       <input type="checkbox" name="needSave" id="needSave" />
                    </div>
  <button type="submit" class="btn btn-info">提&nbsp;交</button></div>
  </html:form>         
            
          
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
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
  
<script>
//删除
function del(obj){
	var _id = $(obj).parent().parent().attr("id").replace(".","_");
	$(obj).parent().parent().remove();
	
}

function copy(obj){
	$(obj).parent().parent().after($(obj).parent().parent().prop("outerHTML"));
}

$(function(){
	$('input[type="checkbox"].minimal').click(function(){
		
		var _id = $(this).attr("id").replace(".","_");
		if($(this).get(0).checked)
		{
			//选中复选框
			var _template = $("#template").html();
			_template = _template.replace("$key$",_id+"_panel").replace("$option$",$(this).parent().text().trim())
			.replace("$inputType$",$(this).attr("inputType"))
			.replace("$firstSelName$",$(this).attr("firstSelName"))
			.replace("$lastSelName$",$(this).attr("lastSelName"))
			.replace("$textName$",$(this).attr("textName"));
			
			$("#option-Content").append(_template);
			//$(this).attr("disabled","disabled");
		}else{

			//未选中复选框
			while($("#"+_id+"_panel").length > 0)
				$("#"+_id+"_panel").remove();
		}
	})
})

</script>

</body>
</html>
