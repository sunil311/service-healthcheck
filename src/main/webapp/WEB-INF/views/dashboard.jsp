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
	function drawChart() {
		var data = new google.visualization.DataTable();
	      data.addColumn('string', 'Service Status');
	      data.addColumn('number', 'Service Status Count');
	      data.addRows([
	        ['Running Services', ${runningService}],
	        ['Stopped Services', ${stoppedServices}]
	      ]);

	      var options = {
	        title: 'Service Healthcheck Report',
	        is3D: true
	      };


		var chart = new google.visualization.PieChart(document
				.getElementById('serviceChart'));

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
				
				 <div >
		  	<div class="panel panel-default report-graph">
        	 <div class="panel-heading">Service healthcheck Report</div>
       		 <div class="panel-body">
				<div id="serviceChart" style="width: 500px; height: 500px; margin: 0 auto;"></div>
       		 </div>
   		 </div>
		  </div>
			</div>
		 
			
		</div>
	</div>
	
</body>
</html>
