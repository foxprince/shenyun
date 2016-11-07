<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="标准统计" />
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
        <h1>按${keyDesc}统计病人数</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
            <!-- AREA CHART -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">区域图</h3>
                <div class="box-tools pull-right">
                  <button class="btn btn-box-tool" data-widget="collapse">
                    <i class="fa fa-minus"></i>
                  </button>
                  <button class="btn btn-box-tool" data-widget="remove">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="box-body">
                <div class="chart">
                  <canvas id="areaChart" style="height: 250px"></canvas>
                </div>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
            <!-- DONUT CHART -->
            <div class="box box-danger">
              <div class="box-header with-border">
                <h3 class="box-title">饼状图</h3>
                <div class="box-tools pull-right">
                  <button class="btn btn-box-tool" data-widget="collapse">
                    <i class="fa fa-minus"></i>
                  </button>
                  <button class="btn btn-box-tool" data-widget="remove">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="box-body">
                <canvas id="pieChart" style="height: 250px"></canvas>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
          </div>
          <!-- /.col (LEFT) -->
          <div class="col-md-6">
            <!-- LINE CHART -->
            <div class="box box-info">
              <div class="box-header with-border">
                <h3 class="box-title">线图</h3>
                <div class="box-tools pull-right">
                  <button class="btn btn-box-tool" data-widget="collapse">
                    <i class="fa fa-minus"></i>
                  </button>
                  <button class="btn btn-box-tool" data-widget="remove">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="box-body">
                <div class="chart">
                  <canvas id="lineChart" style="height: 250px"></canvas>
                </div>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
            <!-- BAR CHART -->
            <div class="box box-success">
              <div class="box-header with-border">
                <h3 class="box-title">柱状图</h3>
                <div class="box-tools pull-right">
                  <button class="btn btn-box-tool" data-widget="collapse">
                    <i class="fa fa-minus"></i>
                  </button>
                  <button class="btn btn-box-tool" data-widget="remove">
                    <i class="fa fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="box-body">
                <div class="chart">
                  <canvas id="barChart" style="height: 230px"></canvas>
                </div>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
          </div>
          <!-- /.col (RIGHT) -->
        </div>
        <!-- /.row -->
        <!-- /.row -->
        <div class="row">
          <div class="box">
            <div class="box-header">
              <div class="box-content">
                <h1>所有${keyDesc}分布</h1> 
          <c:forEach var="item" items="${whereOptions}">
             <spring:eval expression="T(cn.anthony.boot.util.Constant).getKeyDesc(item.key)" var="keyDesc" />
             <label><input type="hidden" class="opt" name="${item.key}" value="${item.value}" >${keyDesc}：${item.value}</label>
            <i style="margin:0px 10px">|</i>
          </c:forEach>
                <form action="initFullTotal">
                  <input type="submit" class="btn btn-success pull-right" value="按条件统计"/>
                
              </div>
            </div>
            <div class="box-body">
              <table id="example11" class="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>序号</th>
                    <th>选为条件</th>
                    <c:forEach var="item" items="${columns}">
                      <th>${item}</th>
                    </c:forEach>
                    <th>病人总数</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="item" items="${keyCount}">
                    <tr>
                      <td></td>
                      <spring:eval expression="T(cn.anthony.boot.util.ControllerUtil).getClauseString(item,key)" var="clausePara" />
                      <td><input type="radio" name="clause" value="${clausePara}"/></td>
                      <c:forEach var="item2" items="${item.columnKey}">
                        <td>${item2}</td>
                      </c:forEach>
                      <td>
                      <spring:eval expression="T(cn.anthony.boot.util.ControllerUtil).getSearchParaString(whereOptions,item,key)" var="searchPara" />
                      <a href="/patient/fullSearch?${searchPara} ">${item.count}</a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
                <tfoot>
                  <tr>
                    <th colspan="12"></th>
                  </tr>
                </tfoot>
              </table>
            </div>
            </form>
            <!-- /.box-body -->
          </div>
        </div>
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
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
  <!-- ChartJS 1.0.1 -->
  <script src="../resources/plugins/chartjs/Chart.min.js"></script>
  <script>
      $(function () {
        /* ChartJS
         * -------
         * Here we will create a few charts using ChartJS
         */

        //--------------
        //- AREA CHART -
        //--------------

        // Get context with jQuery - using jQuery's .get() method.
        var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
        // This will get the first returned node in the jQuery collection.
        var areaChart = new Chart(areaChartCanvas);

        var areaChartData = {
          labels: ${lables},
          datasets: [
            {
              label: "Electronics",
              fillColor: "rgba(210, 214, 222, 1)",
              strokeColor: "rgba(210, 214, 222, 1)",
              pointColor: "rgba(210, 214, 222, 1)",
              pointStrokeColor: "#c1c7d1",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(220,220,220,1)",
              data: ${valueGroup}
            },
            {
              label: "Digital Goods",
              fillColor: "rgba(60,141,188,0.9)",
              strokeColor: "rgba(60,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: ${valueGroup}
            }
          ]
        };

        var areaChartOptions = {
          //Boolean - If we should show the scale at all
          showScale: true,
          //Boolean - Whether grid lines are shown across the chart
          scaleShowGridLines: false,
          //String - Colour of the grid lines
          scaleGridLineColor: "rgba(0,0,0,.05)",
          //Number - Width of the grid lines
          scaleGridLineWidth: 1,
          //Boolean - Whether to show horizontal lines (except X axis)
          scaleShowHorizontalLines: true,
          //Boolean - Whether to show vertical lines (except Y axis)
          scaleShowVerticalLines: true,
          //Boolean - Whether the line is curved between points
          bezierCurve: true,
          //Number - Tension of the bezier curve between points
          bezierCurveTension: 0.3,
          //Boolean - Whether to show a dot for each point
          pointDot: false,
          //Number - Radius of each point dot in pixels
          pointDotRadius: 4,
          //Number - Pixel width of point dot stroke
          pointDotStrokeWidth: 1,
          //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
          pointHitDetectionRadius: 20,
          //Boolean - Whether to show a stroke for datasets
          datasetStroke: true,
          //Number - Pixel width of dataset stroke
          datasetStrokeWidth: 2,
          //Boolean - Whether to fill the dataset with a color
          datasetFill: true,
          //String - A legend template
          //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
          maintainAspectRatio: true,
          //Boolean - whether to make the chart responsive to window resizing
          responsive: true
        };

        //Create the line chart
        areaChart.Line(areaChartData, areaChartOptions);

        //-------------
        //- LINE CHART -
        //--------------
        var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas);
        var lineChartOptions = areaChartOptions;
        lineChartOptions.datasetFill = false;
        lineChart.Line(areaChartData, lineChartOptions);

        //-------------
        //- PIE CHART -
        //-------------
        // Get context with jQuery - using jQuery's .get() method.
        var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
        var pieChart = new Chart(pieChartCanvas);
        var PieData = ${pieData};
        var pieOptions = {
          //Boolean - Whether we should show a stroke on each segment
          segmentShowStroke: true,
          //String - The colour of each segment stroke
          segmentStrokeColor: "#fff",
          //Number - The width of each segment stroke
          segmentStrokeWidth: 2,
          //Number - The percentage of the chart that we cut out of the middle
          percentageInnerCutout: 50, // This is 0 for Pie charts
          //Number - Amount of animation steps
          animationSteps: 100,
          //String - Animation easing effect
          animationEasing: "easeOutBounce",
          //Boolean - Whether we animate the rotation of the Doughnut
          animateRotate: true,
          //Boolean - Whether we animate scaling the Doughnut from the centre
          animateScale: false,
          //Boolean - whether to make the chart responsive to window resizing
          responsive: true,
          // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
          maintainAspectRatio: true,
          //String - A legend template
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        pieChart.Doughnut(PieData, pieOptions);

        //-------------
        //- BAR CHART -
        //-------------
        var barChartCanvas = $("#barChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas);
        var barChartData = areaChartData;
        barChartData.datasets[1].fillColor = "#00a65a";
        barChartData.datasets[1].strokeColor = "#00a65a";
        barChartData.datasets[1].pointColor = "#00a65a";
        var barChartOptions = {
          //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
          scaleBeginAtZero: true,
          //Boolean - Whether grid lines are shown across the chart
          scaleShowGridLines: true,
          //String - Colour of the grid lines
          scaleGridLineColor: "rgba(0,0,0,.05)",
          //Number - Width of the grid lines
          scaleGridLineWidth: 1,
          //Boolean - Whether to show horizontal lines (except X axis)
          scaleShowHorizontalLines: true,
          //Boolean - Whether to show vertical lines (except Y axis)
          scaleShowVerticalLines: true,
          //Boolean - If there is a stroke on each bar
          barShowStroke: true,
          //Number - Pixel width of the bar stroke
          barStrokeWidth: 2,
          //Number - Spacing between each of the X value sets
          barValueSpacing: 5,
          //Number - Spacing between data sets within X values
          barDatasetSpacing: 1,
          //String - A legend template
          //Boolean - whether to make the chart responsive
          responsive: true,
          maintainAspectRatio: true
        };

        barChartOptions.datasetFill = false;
        barChart.Bar(barChartData, barChartOptions);
      });
    </script>
    <script language="javascript" type="text/javascript"> 
window.onload = function(){ 
    var oTable = document.getElementById("example11"); 
    for(var i=1;i<oTable.rows.length-1;i++){ 
        oTable.rows[i].cells[0].innerHTML = (i); 
    } 
} 
</script> 
</body>
</html>
