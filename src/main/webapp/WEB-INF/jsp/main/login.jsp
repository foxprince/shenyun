<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<HTML lang="en">
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="登录"/>
</c:import>
<HEAD>
<META content="IE=11.0000" http-equiv="X-UA-Compatible">
<META charset="UTF-8">
<TITLE>神云医疗数据整合系统</TITLE>
<LINK href="../resources/bootstrap.min.css" rel="stylesheet" type="text/css">
<LINK href="../resources/login.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
  <TABLE width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
    <TBODY>
      <TR>
        <TD height="5" bgcolor="#3b5998"></TD>
      </TR>
      <TR>
        <TD height="12"></TD>
      </TR>
      <TR>
        <TD height="89" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<IMG width="466" height="81"
          src="../resources/dl-logo.gif"></TD>
      </TR>
    </TBODY>
  </TABLE>
  <TABLE width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
    <TBODY>
      <TR>
        <TD width="7" height="534" background="../resources/dl-2.jpg">&nbsp;</TD>
        <TD width="1366" height="534" align="left" background="../resources/dl-1.jpg" valign="top">
          <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
            <TBODY>
              <TR>
                <TD height="48">&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
          <TABLE width="1112" border="0" cellspacing="0" cellpadding="0">
            <TBODY>
              <TR>
                <TD width="788" height="334">&nbsp;</TD>
                <TD width="324" height="334" align="center" background="../resources/dl-3.png" valign="top">
                  <TABLE width="324" border="0" cellspacing="0" cellpadding="0">
                    <TBODY>
                      <TR>
                        <TD height="83" align="center" valign="bottom"><IMG width="298" height="23"
                          src="../resources/dl-4.png"></TD>
                      </TR>
                      <TR>
                        <TD height="20">&nbsp;</TD>
                      </TR>
                    </TBODY>
                  </TABLE>
                  <TABLE>
                    <TBODY>
                      <TR>
                        <TD height="10"></TD>
                      </TR>
                    </TBODY>
                  </TABLE>
                  <FORM class="form-horizontal" role="form" action="/index"
                    method="post">
                    <DIV class="form-group has-success has-feedback">
                      <DIV class="col-sm-11" style="padding-left: 40px;">
                        <DIV class="input-group">
                          <DIV class="input-group-addon">用户名</DIV>
                          <INPUT name="username" class="form-control" id="username" type="text"
                            placeholder="Enter username" value=""> <SPAN
                            class="glyphicon glyphicon-user form-control-feedback"></SPAN>
                        </DIV>
                      </DIV>
                    </DIV>
                    <BR>
                    <TABLE>
                      <TBODY>
                        <TR>
                          <TD height="5"></TD>
                        </TR>
                      </TBODY>
                    </TABLE>
                    <DIV class="form-group has-warning has-feedback">
                      <DIV class="col-sm-11" style="padding-left: 40px;">
                        <DIV class="input-group">
                          <DIV class="input-group-addon">密 &nbsp;&nbsp; 码</DIV>
                          <INPUT name="password" class="form-control" id="password" type="password"
                            placeholder="Enter password" value=""> <SPAN
                            class="glyphicon glyphicon-ok-sign form-control-feedback"></SPAN>
                        </DIV>
                      </DIV>
                    </DIV>
                    <BR>
                    <TABLE>
                      <TBODY>
                        <TR>
                          <TD height="6"></TD>
                        </TR>
                      </TBODY>
                    </TABLE>
                    <DIV class="my-center">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                      <BUTTON class="btn btn-success btn-lg btn-block" type="submit">登&nbsp;&nbsp;&nbsp;录</BUTTON>
                    </DIV>
                  </FORM>
              </TR>
            </TBODY>
          </TABLE>
          <TABLE width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <TBODY>
              <TR>
                <TD height="57" align="center" class="hui">Copyright 2013-2016 宣武医院神经外科  
版本号:1.0.137.19494</TD>
              </TR>
            </TBODY>
          </TABLE>
      </TR>
    </TBODY>
  </TABLE>
</BODY>
</HTML>
