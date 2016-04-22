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
                  <img class="profile-user-img img-responsive img-circle" src="../dist/img/user4-128x128.jpg" alt="病人照片">
                  <h3 class="profile-username text-center">${patient.name} </h3>
                  <p class="text-muted text-center">${patient.actualAge}岁，${patient.sexDesc} </p>

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
                  </ul>

                  <button class="btn btn-primary btn-block"><b>病案号：${patient.pId}</b></button>
                </div><!-- /.box-body -->
              </div><!-- /.box -->

              <!-- About Me Box -->
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">详细信息</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <strong><i class="fa fa-book margin-r-5"></i>  身份证号码</strong>
                  <p class="text-muted">
                    ${patient.certNo }
                  </p>
                  <hr>
                  <strong><i class="fa fa-map-marker margin-r-5"></i> 家庭住址</strong>
                  <p class="text-muted">${patient.frontRecords.get(0).homeAddress} </p>
                  <hr>
                  <strong><i class="fa fa-pencil margin-r-5"></i> 诊断</strong>
                  <p>
                    <span class="label label-danger">UI Design</span>
                    <span class="label label-success">Coding</span>
                    <span class="label label-info">Javascript</span>
                    <span class="label label-warning">PHP</span>
                    <span class="label label-primary">Node.js</span>
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
                </ul>
                <div class="tab-content">
                  <div class="active tab-pane" id="front">
                    <ul class="timeline timeline-inverse">
                      <c:forEach var="item" items="${patient.frontRecords}">
                      <!-- timeline time label -->
                      <li class="time-label"> <span class="bg-red"> 入院时间：${item.admissionTime } </span> </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time">入园时间<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">${item.admissionDept} -- ${item.admissionWard}</h3>
                          <div class="timeline-body">
                            ${item.contactName }<br/>
                            ${item.relation }<br/>
                            ${item.contactaddress }<br/>
                            医疗付费方式：9第1次住院门诊号：-健康卡号：-病理号：-
                            医疗付费方式：9第1次住院门诊号：-健康卡号：-病理号：-
姓名侯玉霞性别21.男 2.女出生日期1966年01月15日年龄国籍中国出生地山东省籍贯山东省民族汉族身份证号372922650116001职业其他婚姻21.未婚 2.已婚 3.丧偶4.离婚 9.其他现住址山东省曹 县曹城镇小南关55电话18661505296邮编274400户口地址山东省曹 县同上邮编274400工作单位及地址山东省曹县新寨镇政府单位电话-邮编-病人来源-保健人员分类非保健保健人员在岗状态不适用联系人姓名赵文魁关系夫地址同病人电话18661505296入院途径21.急诊 2.门诊 3.其他医疗机构转入 9.其他入院时间2014年05月12日10时入院科别神经外科病房神外四出院时间2014年05月26日09时出院科别神经外科病房神外四实际住院14天转科科别-门（急）诊诊断脑胶质瘤术后疾病编码-
                          </div>
                          <div class="timeline-footer">  </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 门诊</span>
                          <h3 class="timeline-header no-border">入院诊断</h3>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time">出院时间<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">${item.dischargeDept} -- ${item.dischargeWard}</h3>
                          <div class="timeline-body">
                            ${item.mainDiag}<br/>
                            ${item.mainDiagCode}<br/>
                            
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                      </c:forEach>
                    </ul>
                    <div class="post clearfix">
                      <div class='user-block'>
                        <img class='img-circle img-bordered-sm' src='../../dist/img/user7-128x128.jpg' alt='user image'>
                        <span class='username'>
                          <a href="#">Sarah Ross</a>
                          <a href='#' class='pull-right btn-box-tool'><i class='fa fa-times'></i></a>
                        </span>
                        <span class='description'>Sent you a message - 3 days ago</span>
                      </div><!-- /.user-block -->
                      <p>
                        <spring:eval expression="T(cn.anthony.util.StringTools).formatMap(T(cn.anthony.boot.util.RefactorUtil).getObjectParaMap(patient.frontRecords.get(0)))" var="frontRecords" />
						${frontRecords}
                      </p>
                    </div><!-- /.post -->
                  </div>
                  <div class="tab-pane" id="inHospital">
                    <ul class="timeline timeline-inverse">
                      <c:forEach var="item" items="${patient.inRecords}">
                      <!-- timeline time label -->
                      <li class="time-label"> <span class="bg-red"> ${item.inDate } </span> </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time">入院科室：${item.admissionDept }</span>
                          <h3 class="timeline-header">${item.admissionDept} </h3>
                          <div class="timeline-body">
                            联系人：${item.contact }<br/>
                          </div>
                          <div class="timeline-footer">  </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time">病史采集<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header no-border">${item.takingDate }--${item.takingFrom }</h3>
                          <div class="timeline-body">
                            自述病史：${item.selfDesc }<br/>
                            现病史：${item.nowMedicalHistory }<br/>
                            既往史：${item.pastMedicalHistory }<br/>
                            传染病史：${item.infectiousHistory }<br/>
                            个人生活史：${item.lifeHistory }<br/>
                            家族史：${item.familyHistory }<br/>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time">体格检查<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">体格检查 </h3>
                          <div class="timeline-body">
                            ${item.somatoscopy.src}
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time">诊断<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">诊断 </h3>
                          <div class="timeline-body">
                            初步诊断：${item.firstDiag}
                            确定诊断：${item.confirmDiag}
                            补充诊断：${item.supplyDiag}
                            更正诊断：${item.correctDiag}
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                      </c:forEach>
                    </ul>
                    
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="operation">
                    <ul class="timeline timeline-inverse">
                      <c:forEach var="item" items="${patient.operations}">
                      <li class="time-label"> <span class="bg-red"> ${item.operationTitle } </span> </li>
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>
                          <h3 class="timeline-header"><a href="#">${item.operationDpt }</a> 床位：${item.bedNumber }</h3>
                          
                        </div>
                      </li>
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time">开始时间：<i class="fa fa-clock-o"></i> ${item.beginTime }</span>
                          <h3 class="timeline-header no-border"><a href="#">术前诊断</a> ${item.preDiagnosis }</h3>
                          <div class="timeline-body">
                          术中诊断：${item.operataionDiagnosis}<br/>
                       医生：${item.doctor}<br/>
                          助手：${item.assistant}<br/>
                          麻醉方法：${item.anaesthetic}
                          </div>
                        </div>
                      </li>
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
                          <h3 class="timeline-header">手术经过、术中出现的情况及处理等</h3>
                          <div class="timeline-body">
                            ${item.detail}
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <li>
                        <span class="bg-green">
                          3 Jan. 2014
                        </span>
                      </li>
                      <li>
                        <i class="fa fa-camera bg-purple"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
                          <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
                          <div class="timeline-body">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                          </div>
                        </div>
                      </li>
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                      </c:forEach>
                    </ul>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="outHospital">
                    <ul class="timeline timeline-inverse">
                      <c:forEach var="item" items="${patient.outRecords}">
                      <!-- timeline time label -->
                      <li class="time-label"> <span class="bg-red"> ${item.outDate } </span> </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time">科室：${item.department }</span>
                          <h3 class="timeline-header">${item.inDiagnosis} </h3>
                          <div class="timeline-body">
                            ${item.inDescription }<br/>
                          </div>
                          <div class="timeline-footer">  </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time">病史采集<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header no-border"></h3>
                          <div class="timeline-body">
                            
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time">体格检查<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">体格检查 </h3>
                          <div class="timeline-body">
                            
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time">诊断<i class="fa fa-clock-o"></i> </span>
                          <h3 class="timeline-header">诊断 </h3>
                          <div class="timeline-body">
                            
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                      </c:forEach>
                    </ul>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="pacs">
                    <!-- The timeline -->
                    <ul class="timeline timeline-inverse">
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-red">
                          10 Feb. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>
                          <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
                          <div class="timeline-body">
                            Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                            weebly ning heekya handango imeem plugg dopplr jibjab, movity
                            jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                            quora plaxo ideeli hulu weebly balihoo...
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-primary btn-xs">Read more</a>
                            <a class="btn btn-danger btn-xs">Delete</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
                          <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request</h3>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
                          <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>
                          <div class="timeline-body">
                            Take me to your leader!
                            Switzerland is small and neutral!
                            We are more like Germany, ambitious and misunderstood!
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-green">
                          3 Jan. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-camera bg-purple"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
                          <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
                          <div class="timeline-body">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                    </ul>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="dsa">
                    <!-- The timeline -->
                    <ul class="timeline timeline-inverse">
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-red">
                          10 Feb. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-envelope bg-blue"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>
                          <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
                          <div class="timeline-body">
                            Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                            weebly ning heekya handango imeem plugg dopplr jibjab, movity
                            jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                            quora plaxo ideeli hulu weebly balihoo...
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-primary btn-xs">Read more</a>
                            <a class="btn btn-danger btn-xs">Delete</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-user bg-aqua"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
                          <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request</h3>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
                          <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>
                          <div class="timeline-body">
                            Take me to your leader!
                            Switzerland is small and neutral!
                            We are more like Germany, ambitious and misunderstood!
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-green">
                          3 Jan. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-camera bg-purple"></i>
                        <div class="timeline-item">
                          <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
                          <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
                          <div class="timeline-body">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                            <img src="http://placehold.it/150x100" alt="..." class="margin">
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <li>
                        <i class="fa fa-clock-o bg-gray"></i>
                      </li>
                    </ul>
                  </div><!-- /.tab-pane -->
                </div><!-- /.tab-content -->
              </div><!-- /.nav-tabs-custom -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
	
	<%@ include file="../include/footer.jspf" %>
	<%@ include file="../include/sidebar.jspf" %>
	<%@ include file="../include/script.jspf" %>
	</div>
</body>
</html>
