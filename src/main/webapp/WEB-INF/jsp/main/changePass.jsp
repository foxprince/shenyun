<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/checkLogin.jspf"%>
<!DOCTYPE html>
<html lang="en">
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="修改密码"/>
</c:import>	

<body>
	<!-- topbar -->
	<%@ include file="../include/topbar.jspf" %>
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- left menu-->
			<%@ include file="../include/menu.jspf" %>
			<div id="content" class="span10">
			<!-- content starts -->
			
			<div>
				<ul class="breadcrumb">
					<li><a href="../main/">首页</a> <span class="divider">/</span></li>
					<li><a href="../main/changePass.jsp">修改密码</a> <span class="divider">/</span></li>
				</ul>
			</div>
			
			<div class="row-fluid sortable ui-sortable" >
				<div class="box span12">		
					<div class="box-header well" data-original-title>
						<h2>
							<i class="icon-edit"></i> 修改密码
						</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a> <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a> <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal" name="addAdmin" action="../main/action.jsp" method="post" onsubmit="return check_input()" >
							<fieldset>
							  <div class="control-group">
								<label class="control-label" >当前密码</label>
								<div class="controls">
								  <input type="password" name="currPass"  />
								</div>
								</div>
							  <div class="control-group">
								<label class="control-label" >新密码</label>
								<div class="controls">
								  <input type="password" name="loginPwd"  />
								</div>
								</div>
							  <div class="control-group">
								<label class="control-label" >再次确认</label>
								<div class="controls">
								  <input type="password" name="reLoginPwd"  />
								</div>
							  </div>
							  <div class="form-actions">
								<button type="submit" class="btn btn-primary">提交</button>
								<button class="btn">Cancel</button>
								input type="hidden" name="action" value="changePass" />
							  </div>
							</fieldset>
						  </form>
					</div>
				</div>
				<!--/span-->
			</div>			
			
		  </div><!--/#content.span10-->
		</div><!--/fluid-row-->
				
		<hr>
		<!-- footer -->
		<%@ include file="../include/footer.jspf" %>
		
	</div><!--/.fluid-container-->
	
	<%@ include file="../include/script.jspf" %>
<script>
<!--
function check_input(){
	form = document.addAdmin;
	tmp = form.loginPwd.value;
	if(tmp!=form.reloginPwd.value){
		alert("两次输入的密码不符！");
		return false;
	}
	
	return confirm("您确认提交吗？");
}
//-->
</script>
</body>
</html>
