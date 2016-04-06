<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Service Status</title>
</head>
<body>
	<div class="home-content" align="center">
		<h2>
			${service.serviceName} &nbsp;
			<c:choose>
				<c:when test="${service.active}">
					<label>is running.</label>
				</c:when>
				<c:otherwise>
					<label>is not running.</label>
				</c:otherwise>
			</c:choose>
		</h2>

	</div>
</body>
</html>