<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="病人首页"/>
</c:import>
<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">
	<!-- topbar -->
	<%@ include file="../include/topbar.jspf" %>
	<!-- left menu-->
	<%@ include file="../include/menu.jspf" %>
	<div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            病人首页
          </h1>
          <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
            <li><a href="./listPage">病人列表</a></li>
            <li class="active"><a href="./">病人详情</a></li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">

          <div class="row">
            <div class="col-md-3">

              <!-- Profile Image -->
              <div class="box box-primary">
                <div class="box-body box-profile">
                  <img class="profile-user-img img-responsive img-circle" src="../resources/dist/img/${patient.sex eq 1?"Patient-Male.png":"Patient-Female.png"}" alt="病人照片">
                  <h3 class="profile-username text-center">${patient.name} </h3>
                  <p class="text-muted text-center">今年${patient.actualAge}岁，${patient.sexDesc} </p>

                  <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                      <b>出生日期</b> <p class="pull-right">${patient.formatDateOfBirth}</p>
                    </li>
                    <li class="list-group-item">
                      <b>住院次数</b> <p class="pull-right">${fn:length(patient.inRecords)}</p>
                    </li>
                    <li class="list-group-item">
                      <b>手术次数</b> <p class="pull-right">${fn:length(patient.operations)}</p>
                    </li>
                    <li class="list-group-item">
                      <b>病案号</b> <p class="pull-right">${patient.pId}</p>
                    </li>
                  </ul>

          <div class='row no-print'> <div class='col-xs-12'><a href='./print?id=${patient.id}' target='_blank' class='btn btn-default'><i class='fa fa-print'></i> 打印</a><a id='generatePdfBtn'  href="./zip?id=${patient.id }&patientNo=${ patient.pId}" class='btn btn-primary pull-right' style='margin-right: 5px;'><i class='fa fa-download'></i> 导出压缩文件</a> </div> </div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->

              <!-- About Me Box -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">详细信息</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <strong><i class="fa fa-book margin-r-5"></i>  身份证号码</strong>
                  <p class="text-muted"> ${patient.certNo } </p>
                  <hr>
                  <strong><i class="fa fa-map-marker margin-r-5"></i> 家庭住址</strong>
                  <p class="text-muted">${patient.frontRecords.get(0).homeAddress} </p>
                  <hr>
                  <strong><i class="glyphicon glyphicon-earphone"></i>  联系电话</strong>
                  <p class="text-muted">${patient.frontRecords.get(0).mobilephone} </p>
                  <hr>
                  <strong><i class="fa fa-pencil margin-r-5"></i> 诊断</strong>
                  <p>
                    <span class="label label-success">${patient.frontRecords.get(0).mainDiag}</span>
                    <%-- <span class="label label-primary">${patient.inRecords.get(0).confirmDiag.detail}</span>
                    <span class="label label-warning">${patient.outRecords.get(0).outDiagnosis }</span> --%>
                  </p>
                  <hr>

                  <strong><i class="fa fa-file-text-o margin-r-5"></i> 备注</strong>
                  <p  class="text-muted">${patient.frontRecords.get(0).country}</p>
                  <p  class="text-muted">出生地：${patient.frontRecords.get(0).birthplace}</p>
                  <p class="text-muted">籍贯：${patient.frontRecords.get(0).nativeplace}</p>
                  <p class="text-muted">民族：${patient.frontRecords.get(0).nationality}</p>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div><!-- /.col -->
            <div class="col-md-9">
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                  <li class="active"><a href="#front" data-toggle="tab">病案首页</a></li>
                  <li><a href="#inHospital" data-toggle="tab">入院纪录</a></li>
                  <li><a href="#operation" data-toggle="tab">手术记录</a></li>
                  <li><a href="#outHospital" data-toggle="tab">出院纪录</a></li>
                  <li><a href="#pacs" data-toggle="tab">PACS影像</a></li>
                  <li><a href="#dsa" data-toggle="tab">DSA影像</a></li>
                  <li><a href="#asset" data-toggle="tab">外来资料</a></li>
                  <c:forEach var="item" items="${patient.extendMap}">
                    <li><a href="#${item.key }" data-toggle="tab">${item.value.typeDesc}</a></li>
                  </c:forEach>
                  <li><a href="#remark" data-toggle="tab">备注</a></li>
                </ul>
                <div class="tab-content">
                  <%@ include file="front.jspf" %>
                  <%@ include file="inHospital.jspf" %>
                  <%@ include file="operation.jspf" %>
                  <%@ include file="outHospital.jspf" %>
                  <%@ include file="pacs.jspf" %>
                  <%@ include file="dsa.jspf" %>
                  <%@ include file="asset.jspf" %>
                  
                  <c:forEach var="item" items="${patient.extendMap}">
                    <div class="tab-pane" id="${item.key }">
                    <!-- The timeline -->
                    <section class="invoice">
                      <!-- title row -->
                      <div class="row">
                        <div class="col-xs-12">
                          <h2 class="page-header">
                            <i class="fa fa-globe"></i> ${item.value.typeDesc}
                            <small class="pull-right"></small>
                          </h2>
                        </div><!-- /.col -->
                      </div>
                      
                      <div class='row margin-bottom'>
                        <spring:eval expression="T(cn.anthony.util.RefactorUtil).getObjectParaMap(item.value)" var="fieldsMap" />
                        <ul>
                        <c:forEach var="item2" items="${fieldsMap}">
                            <li><p><strong>${item2.key }：</strong></p> <pre>${item2.value }</pre></li>
                        </c:forEach>
                        </ul>
                      </div>
                    </section>
                  </div><!-- /.tab-pane -->
                  </c:forEach>
                  
                  <%@ include file="remark.jspf" %>
                </div><!-- /.tab-content -->
              </div><!-- /.nav-tabs-custom -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
	
	<%@ include file="../include/sidebar.jspf" %>
	<%@ include file="../include/script.jspf" %>
    <%@ include file="../include/footer.jspf" %>
	<script src="../resources/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script src="../resources/plugins/jquery-validation/messages_zh.js"></script>

	<script>
    	$(".imgView").click(function () {
                var src = $(this).attr('orig');
                src = src.replace('/132', '/0');
                var img = new Image();
                img.src = src;
                img.onload = function () {
                    layer.open({
                        type: 1,
                        title: false,
                        shadeClose: true,
                        maxWidth: 500,
                        content: '<img src="' + src + '" style="max-width:500px;" />' //这里content是一个普通的String
                    });
                }
            })
    	$("#remarkForm").validate({
    		rules : {
    			content : "required",
    			doctor : "required",
    			operator : "required"
    		}
    	});
    	function delRemark(ref){
    		$.get("/patient/delRemark?patientId=${patient.id}",
    			{remarkId:ref},
    			function(data){
    				if(data){
    					var divId="remark_"+ref;
    			        $('#'+divId).fadeOut('slow');
    				}else{
    					alert('删除失败。');
    				}
    				return true;
    			}
    		);
    		return false;
    	}
    	function addReamrk(){
    		if($("#remarkForm").valid()){
    		$.ajax({
    			type: "POST",
                url: "/patient/addRemark",
                data: $('#remarkForm').serialize(),
                dataType: "json",
    		    success: function (json) {
                	if (json.id!='') {
                        var	succAlert="";
                		var newRemark="";
                  		newRemark+='<div class="row" id="remark_'+json.id+'">';
                		newRemark+='	<div class="col-md-12">';
                		newRemark+='		<!-- Box Comment -->';
                		newRemark+='		<div class="box box-widget">';
                		newRemark+='			<div class="box-header with-border">';
                		newRemark+='				<div class="user-block">';
                		newRemark+='				<img class="img-circle img-sm" src="/resources/dist/img/user3-128x128.jpg" alt="user image">';
                		newRemark+='					<span class="username">'+json.doctor+'</span>';
                		newRemark+='				</div>';
                		newRemark+='				<!-- /.user-block -->';
                		newRemark+='				<div class="box-tools">';
                		newRemark+='					<button class="btn btn-box-tool" data-toggle="tooltip" title="Mark as read">';
                		newRemark+='						<i class="fa fa-circle-o"></i>';
                		newRemark+='					</button>';
                		newRemark+='					<button class="btn btn-box-tool" data-widget="collapse">';
                		newRemark+='						<i class="fa fa-minus"></i>';
                		newRemark+='					</button>';
                		newRemark+='					<button class="btn btn-box-tool" data-widget="remove">';
                		newRemark+='						<i class="fa fa-times"></i>';
                		newRemark+='					</button>';
                		newRemark+='					<button class="btn btn-danger" id="btn_remark" onclick=delRemark("'+json.id+'") ref="'+json.id+'">删除</button>';
                		newRemark+='				</div>';
                		newRemark+='				<!-- /.box-tools -->';
                		newRemark+='			</div>';
                		newRemark+='			<!-- /.box-header -->';
                		newRemark+='			<div class="box-body">';
                		newRemark+='				<p>'+json.content+'</p>';
                		newRemark+='			</div>';
                		newRemark+='			<!-- /.box-body -->';
                		newRemark+='			<div class="box-footer box-comments">';
                		newRemark+='				<div class="box-comment">';
                		newRemark+='					<!-- User image -->';
                		newRemark+='					<img class="img-circle img-sm" src="/resources/dist/img/user5-128x128.jpg" alt="user image">';
                		newRemark+='					<div class="comment-text">';
                		newRemark+='						<span class="username"> 添加人：'+json.operator+' <span class="text-muted pull-right">'+json.ctime+'</span>';
                		newRemark+='						</span>';
                		newRemark+='					</div>';
                		newRemark+='					<!-- /.comment-text -->';
                		newRemark+='				</div>';
                		newRemark+='				<!-- /.box-comment -->';
                		newRemark+='			</div>';
                		newRemark+='		</div>';
                		newRemark+='		<!-- /.box -->';
                		newRemark+='	</div>';
                		newRemark+='	<!-- /.col -->';
                		newRemark+='</div>';
                        $('#remark_parent').prepend(newRemark);
                        $(newRemark).hide().prependTo($('#remark_parent')).fadeIn('slow');
            			console.log($('#remark_parent').html());
            			$('#remark_parent').show();
            			succAlert+='<div class="alert alert-success alert-dismissable">';
                        succAlert+='<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>';
                        succAlert+='<h4>	<i class="icon fa fa-check"></i> 添加成功!</h4>';
                        succAlert+='备注信息添加成功。';
                      	succAlert+='</div>';
                      	$(succAlert).hide().prependTo($('#remark_parent')).fadeIn('slow');
            			alert('添加成功。');
                    }
                    else {
                    	var	failAlert="";
                    	failAlert+='<div class="alert alert-warning alert-dismissable">';
                    	failAlert+='<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>';
                    	failAlert+='<h4><i class="icon fa fa-warning"></i> 添加失败!</h4>';
                    	failAlert+='添加失败，请填写完整信息。';
                    	failAlert+='</div>';
                    	$(failAlert).hide().prependTo($('#remark_parent')).fadeIn('slow');
                    	//alert('添加失败。');
                    }
                },
                error: function (XmlHttpRequest, textStatus, errorThrown) {
                    console.log(XmlHttpRequest);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
    		});}
    		return false;
    	}
    $("#btnPic").Huploadify({
        auto: true,
        multi: true,
        fileObjName: 'file',
        uploader: '/api/asset/uploadOnly?${_csrf.parameterName}=${_csrf.token}',
        fileSizeLimit: 99999999999,
        showUploadedPercent: true,
        showUploadedQueue: true,
        //simUploadLimit:1,
        buttonText: '选择文件',
        buttonCss: 'btn btn-primary',
        onUploadSuccess: function (file, json) {
            var responseText = JSON.parse(json);
            addTmpl = $("#addTmpl").html();
            if (responseText.code == 0) {
                addTmpl = addTmpl.replace('{{sourceName}}', responseText.data[0]).replace('{{imgUrl}}', responseText.data[1]);
                addTmpl = addTmpl.replace('{{visitUrl}}', '/api/asset/preview?size=orig&fileName='+responseText.data[2]);
                $('#picList').append(addTmpl);
            } else {
                utils.msg('提示', '图片上传失败', 'warning');
            }
        }
    });
    function getThumbName(src) {
    		if(utils.isNullorEmpty(src))
                return "null";
            if(src.toUpperCase().indexOf("WEBP")>0) {return src;}
    	    var i = src.lastIndexOf(".");
    	    return src.substring(0,i).concat("_thumb").concat(src.substring(i));
    	}
    </script>

	</div>
	<script type="text/template"  id="addTmpl">
                        <div class="row">
                            <div class="col-sm-2 text-right control-label">图片</div>
                            <div class="col-sm-8">
                                <span>{{sourceName}}</span>
                                <input type="hidden" name="filenames" value="{{imgUrl}}"/>
                            </div>
                            <div class="col-sm-2" class="btnPic"><a class="delTmpl" href="javascript:;" onclick="delTmpl(this);">删除</a></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 text-right control-label">预览</div>
                            <div class="col-sm-10"><img class="imgView" src="{{visitUrl}}" style="height:50px;" /></div>
                        </div>
    </script>
</body>
</html>
