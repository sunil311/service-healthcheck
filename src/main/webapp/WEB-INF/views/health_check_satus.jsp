<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Service Status</title>
<jsp:include page="resources.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$('#serviceTable').DataTable({
			"lengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ]
		});
	});
	$(function() {
		$("#dialog-form").dialog({
			autoOpen : false,
            height: 200,
            width:500
		});
		$("#add_new_service").click(function() {
			$("#dialog-form").dialog("open");
		});
	});
</script>
<style>
.ui-widget-header,.ui-state-default,ui-button {
	background: #b9cd6d;
	border: 1px solid #b9cd6d;
	color: #FFFFFF;
	font-weight: bold;
}
</style>
</head>
<body>
	<button id="add_new_service">Add new service</button>
	<div align="center">
		<table id="serviceTable" class="display" width="80%" cellspacing="0">
			<thead>
				<tr>
					<th>Service Id</th>
					<th>Service Name</th>
					<th>Service URL</th>
					<th>Service Status</th>
					<th>Remove Service</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${serviceList }" var="service">
					<tr>
						<td>${service.id }</td>
						<td>${service.serviceName}</td>
						<td>${service.serviceUrl }</td>
						<td><c:choose>
								<c:when test="${service.active}">
									<label style="color: green">Running.</label>
								</c:when>
								<c:otherwise>
									<label style="color: red">Stopped</label>
								</c:otherwise>
							</c:choose></td>
						<td><a href="removeService?id=${service.id }">Remove</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="dialog-form" title="Create new user">
		<form action="addService">
			<label for="serviceName">Service Name</label> 
			<input type="text" name="serviceName" id="serviceName" placeholder="My Service Name"> <br />
			<label for="serviceUrl">Service URL</label> 
			<input type="text" name="serviceUrl" id="serviceUrl" placeholder="servie url"><br />
			<input type="submit" tabindex="-1" value="Add Service"/>
		</form>
	</div>
</body>
</html>