<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
<jsp:include page="resources.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="home-content" align="center">
		<h2>Hello ${username}! You are most welcome!</h2>
	</div>

</body>
</html>
