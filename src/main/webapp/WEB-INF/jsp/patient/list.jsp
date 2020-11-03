<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="病人列表" />
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
        <h1>${source}病人列表</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./list?source=${source}">病人列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <div class="box">
              <div class="box-header">
                <div class="box-content">
                    <div class="row">
			        <html:form id="patientForm" modelAttribute="pageRequest" formUrl="/patient/search">  
                      <DIV class="panel-body">
                  <DIV class="container-fluid">
                    <DIV class="row">
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionTime">入院起始</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="inDateBegin" type="date" />
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="admissionTime2">入院截止</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:input class="form-control" path="inDateEnd" type="date" />
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime">出院起始</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <input class="form-control" name="frontRecords.dischargeTime" type="date" />
                            <input type="hidden" name="frontRecords.dischargeTime_option" value="ge"/> 
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="dischargetime2">出院截止</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <input class="form-control" name="frontRecords.dischargeTime" type="date" />
                            <input type="hidden" name="frontRecords.dischargeTime_option" value="le"/> 
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                   <DIV class="row">
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm ">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="mainDiag">诊断病情</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                              <form:input class="form-control" path="frontPage.mainDiag" type="text" placeholder="诊断病情"/>
                          </DIV>
                        </DIV>
					  </DIV>
                      <DIV class="col-md-3 col-sm-3">   
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="KZR_DOCTOR_NAME">主诊医生</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="frontPage.ZZHEN_DOCTOR_NAME" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${zzhenDoctorList}" />
                            </form:select>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZZ_DOCTOR_NAME">主治医师</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="frontPage.ZZ_DOCTOR_NAME" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${zzDoctorList}" />
                            </form:select>
                          </DIV>
                        </DIV>
                      </DIV>
                      <DIV class="col-md-3 col-sm-3">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZY_DOCTOR_NAME">住院医师</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="frontPage.ZY_DOCTOR_NAME" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${zyDoctorList}" />
                            </form:select>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV>
                    
                    <%-- <DIV class="row">
                      <DIV class="col-md-4 col-sm-4">
                        <DIV class="form-group form-group-sm">
                          <LABEL class="col-md-3 col-sm-3 control-label" for="ZY_DOCTOR_NAME">病案来源</LABEL>
                          <DIV class="col-md-9 col-sm-9">
                            <form:select path="source" >
                                <OPTION value="">全部</OPTION>
                                <form:options items="${sourceList}" />
                            </form:select>
                          </DIV>
                        </DIV>
                      </DIV>
                    </DIV> --%>
                  </DIV>
                </DIV>
                      
                      <!-- <div class="col-sm-4"><input type="button" value="导出数据" onclick="download()"/></div> -->
                      <DIV class="col-sm-4 pull-center">
	                    <BUTTON class="btn btn-success btn-sm" type="submit" >查询病案</BUTTON>
	                  </DIV>
                      <div class="col-sm-4">
                        <A class="btn btn-success" data-toggle="modal" data-target="#csvOutput">导出数据 </A>
                      </div>
                    </div>
                </div>
              </div>
              </html:form>
              <!-- /.box-header -->
              <div class="box-body">
                <table id="example11" class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th>病案号<img class="sortImg" onclick="clickSort(this)" sort="pId" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>姓名<img class="sortImg" onclick="clickSort(this)" sort="name" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>入院年龄<img class="sortImg" onclick="clickSort(this)" sort="age" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>性别<img class="sortImg" onclick="clickSort(this)" sort="sex" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>入院时间<img class="sortImg" onclick="clickSort(this)" sort="frontRecords.admissionTime" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>出院时间<img class="sortImg" onclick="clickSort(this)" sort="frontRecords.dischargeTime" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>主要诊断<img class="sortImg" onclick="clickSort(this)" sort="frontRecords.mainDiag" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>住院医师<img class="sortImg" onclick="clickSort(this)" sort="frontRecords.ZY_DOCTOR_NAME" src="../resources/dist/img/sort_asc.png"/></th>
                      <th>主诊医师<img class="sortImg" onclick="clickSort(this)" sort="frontRecords.ZZHEN_DOCTOR_NAME" src="../resources/dist/img/sort_asc.png"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.pId}</td>
                        <td><a href="./?id=${item.id} ">${item.name}</a></td>
                        <td>${item.age}</td>
                        <td>${item.sexDesc}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.minInDate}" /></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.maxOutDate}" /></td>
                        <td>${item.frontRecords.get(0).mainDiag}</td>
                        <td>${item.frontRecords.get(0).ZY_DOCTOR_NAME}</td>
                        <td>${item.frontRecords.get(0).ZZHEN_DOCTOR_NAME}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                  <tfoot>
                    <tr>
                      <th colspan="10"></th>
                    </tr>
                  </tfoot>
                </table>
                <html:page url="?1=1" />
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
    <DIV tabindex="-1" class="modal fade" id="csvOutput" role="dialog" aria-labelledby="myModalLabel">
      <DIV class="modal-dialog" role="document">
        <DIV class="modal-content">
          <DIV class="modal-header">
            <BUTTON class="close" aria-label="Close" type="button" data-dismiss="modal">
              <SPAN aria-hidden="true">×</SPAN>
            </BUTTON>
            <H4 class="modal-title" id="myModalLabel">导出选项</H4>
          </DIV>
          <DIV class="modal-body">
            <DIV class="container-fluid" id="checkboxes">
              <form action="export" method="post" target="_blank">
                
                <c:forEach var="item" items="${customeOptions}">
                  <h4 class="btn btn-primary disabled">${item.name}</h4>
                  <input id="customeCheckAll" name="${item.arrayString}" type="checkbox" filedArray='${item.arrayString}'>选择</label>
                  <i style="margin: 0px 10px">|</i>
                 </c:forEach>
                        
                <div class="box-body">
                  <div class="box box-info">
                    <div class="box-header">
                      <h4 class="box-title">基本条件</h4>
                      <label><input id="patientCheckAll" type="checkbox" checked="true">全选</label>
                      <div class="pull-right box-tools">
                        <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                        <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body pad">
                      <div class="form-group">
                        <c:forEach var="item" items="${patientOptions}">
                          <label><input type="checkbox" op="patient" class="minimal" name="fields"
                            value="${item.name}" checked="true">${item.label}</label>
                          <i style="margin: 0px 10px">|</i>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="box box-primary">
                    <div class="box-header">
                      <h4 class="box-title">病案首页</h4>
                      <label><input id="frontPageCheckAll" type="checkbox" checked="true">全选</label>
                      <div class="pull-right box-tools">
                        <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                        <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body pad">
                      <div class="form-group">
                        <c:forEach var="item" items="${frontPageOptions}">
                          <label><input type="checkbox" class="minimal" op="frontPage" name="fields"
                            value="${item.name}" checked="true">${item.label}</label>
                          <i style="margin: 0px 10px">|</i>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="box box-warning">
                    <div class="box-header">
                      <h4 class="box-title">入院选项</h4>
                      <label><input id="inHospitalCheckAll" type="checkbox" checked="true">全选</label>
                      <div class="pull-right box-tools">
                        <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                        <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body pad">
                      <div class="form-group">
                        <c:forEach var="item" items="${inOptions}">
                          <label><input type="checkbox" class="minimal" op="inHospital" name="fields"
                            value="${item.name}" checked="true">${item.label}</label>
                          <i style="margin: 0px 10px">|</i>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="box box-danger">
                    <div class="box-header">
                      <h4 class="box-title">手术选项</h4>
                      <label><input id="operationCheckAll" type="checkbox" checked="true">全选</label>
                      <div class="pull-right box-tools">
                        <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                        <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body pad">
                      <div class="form-group">
                        <c:forEach var="item" items="${operationOptions}">
                          <label><input type="checkbox" class="minimal" op="operation" name="fields"
                            value="${item.name}" checked="true">${item.label}</label>
                          <i style="margin: 0px 10px">|</i>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="box box-success">
                    <div class="box-header">
                      <h4 class="box-title">出院选项</h4>
                      <label><input id="outHospitalCheckAll" type="checkbox" checked="true">全选</label>
                      <div class="pull-right box-tools">
                        <button class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                        <button class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
                      </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body pad">
                      <div class="form-group">
                        <c:forEach var="item" items="${outOptions}">
                          <label><input type="checkbox" class="minimal" op="outHospital" name="fields"
                            value="${item.name}" checked="true">${item.label}</label>
                          <i style="margin: 0px 10px">|</i>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                </div>
            </DIV>
          </DIV>
          <DIV class="modal-footer">
            <input class="btn btn-success" type="submit" value="导出EXCEL文件" /> <input type="hidden"
              name="${_csrf.parameterName}" value="${_csrf.token}" />
            <BUTTON class="btn btn-primary" type="button" data-dismiss="modal">关闭</BUTTON>
          </DIV>
          </form>
        </DIV>
      </DIV>
    </DIV>
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
  $(function () {
	  var sort = getParams("sort");
	  if(sort!=null){
		  var array = sort.split(",");
		  $(".sortImg").each(function(){
			  for(var i= 0;i<array.length;i++){  
			  if(array[i]==$(this).attr("sort")) {
		      	$(this).attr("src","../resources/dist/img/sort_"+array[i+1]+".png");
		      	$(this).parent().addClass("bg-green-active");
		      }
			  }
		  });
	  }
    });	
    function download(){
        var url="./download";
        window.open(url);
    };
    function getParams(key) {
    	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    	var r = window.location.search.substr(1).match(reg);
    	if (r != null) {
    		return unescape(r[2]);
    	}
    	return null;
    }
    function confirmHandle() {
    	var selected = [];
    	var href = "./export";
    	$('#checkboxes input:checked').each(function() {
    		selected.push($(this).attr('id'));
    	});
    	window.open(href + '?fields=' + selected.join(','));
    }
    function clickSort(item){
	    	$sorting = $(item).attr("sort");
	    		if($(item).attr("src")=="../resources/dist/img/sort_asc.png") {
				$sorting +=",desc";
				$(item).attr("src","../resources/dist/img/sort_desc.png");
			}
			else {
				$sorting +=",asc";
				$(item).attr("src","../resources/dist/img/sort_asc.png");
			}
			href = window.location.href;
			if(window.location.href.lastIndexOf("&sort")>0)
				href = window.location.href.substring(0,window.location.href.lastIndexOf("&sort"));
			window.location.href=href+"&sort="+$sorting;
	}
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
    
    $("#customeCheckAll").click( 
        	function(){ 
        		$("input[name='fields']").each(function(){this.checked=false;}); 
        		var fs = this.name.split("-");
        		if(this.checked){ 
        			$("input[name='fields']").each(function(){if(fs.contains(this.value)) this.checked=true;}); 
        		}else{ 
        			$("input[name='fields']").each(function(){if(fs.contains(this.value)) this.checked=false;}); 
        		} 
        	} 
        );
    </script>
</body>
</html>
