<html>
<head>
<title>Home</title>
<jsp:include page="resources.jsp"></jsp:include>

</head>
<body>

<div class="container">
        <div class="row">
            <div class="">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h2 class="panel-title">Please Sign In</h2>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="loggedMeIn" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" type="text" name="username" placeholder="Enter username" autofocus="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password">
                                </div>
                                
                                <!-- Change this to a button or input when using this as a form -->
                               
                                <input type="submit" class="btn btn-lg btn-primary btn-block" name="Submit" value="Login">
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
