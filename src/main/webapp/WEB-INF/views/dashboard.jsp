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
<div class="header">
 <nav role="navigation" class="navbar navbar-inverse container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="#" class="navbar-brand">Healthcheck</a>
        </div>
        <!-- Collection of nav links and other content for toggling -->
        <div id="navbarCollapse" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="dashboard">Dashboard</a></li>
                <li><a href="service_status">Services</a></li>
               
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Login</a></li>
            </ul>
        </div>
    </nav>
</div>
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
