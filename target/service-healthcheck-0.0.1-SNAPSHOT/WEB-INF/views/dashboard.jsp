<html>
<head>
<title>Dashboard</title>

<!-- Latest compiled and minified CSS -->
<jsp:include page="resources.jsp"></jsp:include>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	google.charts.setOnLoadCallback(drawBarChart);
	function drawChart() {
		var data = new google.visualization.DataTable();
	      data.addColumn('string', 'Service Status');
	      data.addColumn('number', 'Service Status Count');
	      data.addRows([
	        ['Running Services', ${runningServiceCount}],
	        ['Stopped Services', ${stoppedServicesCount}]
	      ]);

	      var options = {
	        title: 'Service Healthcheck Report',
	        is3D: true,
	        colors: ['green', 'red']
	      };


		var chart = new google.visualization.PieChart(document
				.getElementById('serviceChart'));

		chart.draw(data, options);
	}

	function drawBarChart() {
		var data = new google.visualization.DataTable();
	      data.addColumn('string', 'Service Status');
	      data.addColumn('number', 'Service Status Count');
	      data.addColumn({type:'string', role:'style'});
	      var json = ${servicesJsonList};
			data.addRows(json);
	      var options = {
	        title: "Web Services(Red: Stopped, Green: Running)",
	        width: 600,
	        height: 400,
	        bar: {groupWidth: "75%"},
	        legend: { position: "none" },
	        hAxis: { textPosition: 'none' },
	        vAxis: { textPosition: 'none',minValue: 0, gridlines:{ color:'transparent' } }	        
	      };
	      var chart = new google.visualization.ColumnChart(document.getElementById("serviceStatusBarChart"));
	      chart.draw(data, options);
	  }
</script>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">

		<div class="inner-container">
			<div class="heading">
				<h1 class="page-header">Dashboard</h1>
			</div>
			<div class="dashboard">

				<div>
					<div class="panel panel-default report-graph">
						<div class="panel-heading">Service healthcheck Report</div>
						<div class="panel-body">
							<div id="serviceChart"
								style="width: 500px; height: 500px; margin: 0 auto;"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default report-graph">
				<div class="panel-body">
					<div id="serviceStatusBarChart"
						style="width: 500px; height: 500px; margin: 0 auto;"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
