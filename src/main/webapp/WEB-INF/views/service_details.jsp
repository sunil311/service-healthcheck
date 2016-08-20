<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Service Details</title>
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

			width : 600
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
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="inner-container">
			<div class="heading">
				<h1 class="page-header">Services Details For Last 24 Hour</h1>
			</div>
			<div class="service">
			 <a href="publishReport/docx" class="btn btn-lg btn-primary" data-toggle="modal">Export Service Report as Docx</a>
			 <a href="publishReport/pdf" class="btn btn-lg btn-primary" data-toggle="modal">Export Service Report as PDF</a>
			 <a href="publishReport/mail" class="btn btn-lg btn-primary" data-toggle="modal">Send Over the mail</a>
			</div>
			<!-- <h3 class="page-header">
				<a href="sendnotification">Click Here </a> to Send webservice status
				notification
			</h3> -->
			<div class="service">
				<div>
					<table id="serviceTable" class="display table">
						<thead>
							<tr>
								<th>Service Name</th>
								<th>Service URL</th>
								<th>Service Status</th>
								<th>Last Status Check Time</th>
								<th>Execution Time</th>
								<th>Details</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entry" items="${serviceMap}">
								<tr>
									<c:forEach var="service" items="${entry.value}">
										<td>${service.serviceName }</td>
										<td>${service.serviceUrl }</td>
										<td>${service.status }</td>
										<td>${service.lastStatusTime }</td>
										<td>${service.executionTime }</td>
										<td><a
											href="getServiceStatusDetails?serviceId=${service.webServiceId }">Get
												Details</a>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>