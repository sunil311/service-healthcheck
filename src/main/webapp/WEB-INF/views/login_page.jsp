<html>
<head>
<title>Home</title>
<jsp:include page="resources.jsp"></jsp:include>
</head>
<body>
	<section>
	<div class="home-content" align="center">
		<h2>Hello Guest! You want to login?</h2>
		<form action="loggedMeIn">
			<label>Username</label> 
			<input type="text" name="username" placeholder="Enter username" />
			<br/>
			<label>Password</label> 
			<input type="password" name="password" placeholder="Enter password" />
			<br/>
			<input type="submit" name="Submit" value="Login">
		</form>
	</div>
	</section>
</body>
</html>
