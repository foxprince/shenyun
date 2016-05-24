<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>病人信息-打印版</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../dist/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="../dist/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../dist/css/AdminLTE.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body onload="window.print();">
    <div class="wrapper">
        <!-- Content Header (Page header) -->

        <!-- Main content -->
          
          <div class="box-body  invoice-info">
            <div class="col-sm-4 invoice-col">
              <div class="box box-primary">
                <div class="box-body box-profile">
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
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
            <div class="col-sm-4 invoice-col">
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
                </div>
              </div>
            </div>
            <div class="col-sm-4 invoice-col">
              <div class="box box-primary">
                  <div class="box-header with-border">
                  <h3 class="box-title">其他信息</h3>
                  </div>
                  <div class="box-body">
                  <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                      <b>国籍：</b> <p class="pull-right">${patient.frontRecords.get(0).country}</p>
                    </li>
                    <li class="list-group-item">
                      <b>出生地：</b> <p class="pull-right">${patient.frontRecords.get(0).birthplace}</p>
                    </li>
                    <li class="list-group-item">
                      <b>籍贯：</b> <p class="pull-right">${patient.frontRecords.get(0).nativeplace}</p>
                    </li>
                    <li class="list-group-item">
                      <b>民族：</b> <p class="pull-right">${patient.frontRecords.get(0).nationality}</p>
                    </li>
                  </ul>
                  </div>
              </div><!-- /.box-body -->
            </div><!-- /.box -->
          </div>
            <div class="box-body invoice-info">
                <!-- <ul class="nav nav-tabs">
                  <li class="active"><a href="#front" data-toggle="tab">病案首页</a></li>
                  <li><a href="#inHospital" data-toggle="tab">入院纪录</a></li>
                  <li><a href="#operation" data-toggle="tab">手术记录</a></li>
                  <li><a href="#outHospital" data-toggle="tab">出院纪录</a></li>
                  <li><a href="#pacs" data-toggle="tab">PACS影像</a></li>
                  <li><a href="#dsa" data-toggle="tab">DSA影像</a></li>
                </ul> -->
                <div class="box box-info">
                  <div class="box-header">
                  <h3 class="box-title">病案首页</h3>
                  <div class="box-body pad"><%@ include file="front.jspf" %></div></div></div>
                <div class="box box-info">
                  <div class="box-header">
                  <h3 class="box-title">入院纪录</h3>
                  <div class="box-body pad"><%@ include file="inHospital.jspf" %></div></div></div>
                <div class="box box-info">
                  <div class="box-header">
                  <h3 class="box-title">手术记录</h3>
                  <div class="box-body pad"><%@ include file="operation.jspf" %></div></div></div>
                <div class="box box-info">
                  <div class="box-header">
                  <h3 class="box-title">出院纪录</h3>
                  <div class="box-body pad"><%@ include file="outHospital.jspf" %></div></div></div>
          </div><!-- /.col -->
      </div><!-- /.content-wrapper -->

    <!-- AdminLTE App -->
    <script src="../../dist/js/app.min.js"></script>
  </body>
</html>
