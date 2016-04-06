<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<div class="home-content" align="center">
		<h2>Hello ${username}! You are most welcome!</h2>
	</div>
	<div>
		<h2>There are ${numOfServicesAvailable } available. You can check
			their status by clicking any of them.</h2>
	</div>
	<c:forEach var="service" items="${serviceList }">
		<c:out value="${service}" />
		
		<!-- This anchor tag need to be replace with an AJAX call -->
		<a href="checkServiceStatus?serviceName=${service}"> Check Service Status</a>
		<br />
	</c:forEach>
</body>
</html>
