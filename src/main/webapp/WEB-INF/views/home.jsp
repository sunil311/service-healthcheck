<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
<jsp:include page="resources.jsp"></jsp:include>
</head>
<body>
	<div class="home-content" align="center">
		<h2>Hello ${username}! You are most welcome!</h2>
	</div>
	<div>
		<div>
			<a href="dashboard">Dashboard</a>
		</div>
		<div>
			<a href="service_status">Services</a>
		</div>
	</div>
</body>
</html>
