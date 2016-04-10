<html>
<head>
<title>Dashboard</title>
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
	<div id="serviceChart" style="width: 600px; height: 600px;"></div>
</body>
</html>
