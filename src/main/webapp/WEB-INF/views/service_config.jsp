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
       
            width:600
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
			<h1 class="page-header">Services</h1>
		</div>
			<div class="service">
						
							<a href="#myModal" class="btn btn-lg btn-primary" data-toggle="modal">Add new service</a>
	<div>
		<table id="serviceTable" class="display table">
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
			
			</div>
	
		</div>
</div>
	
	
	<div id="myModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Add new service</h4>
                </div>
                <div class="modal-body">
                
                
                
                    <form  action="addService">
        <div class="form-group">
            <label for="serviceName" class="control-label ">Service Name</label>
            <div class="">
                <input type="text" class="form-control" id="serviceName" name="serviceName" placeholder="Service Name">
            </div>
        </div>
        <div class="form-group">
            <label for="serviceUrl" class="control-label">Service URL</label>
            <div class="">
                <input type="text" class="form-control" id="serviceUrl" name="serviceUrl" placeholder="Service URL">
            </div>
        </div>
        <div class="form-group">
            <label for="serviceUserId" class="control-label">User Name</label>
            <div class="">
                <input type="text" class="form-control" id="serviceUserId" name="serviceUserId" placeholder="Service User Id">
            </div>
        </div>
        <div class="form-group">
            <label for="servicePassword" class="control-label">Password</label>
            <div class="">
                <input type="text" class="form-control" id="servicePassword" name="servicePassword" placeholder="Service Password">
            </div>
        </div>
       
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" tabindex="-1" value="Add Service" class="btn btn-primary">Add Service </button>
                       </form>
                </div>
            </div>
        </div>
    </div>
	
	
</body>
</html>