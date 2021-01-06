<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>

<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url("https://media.giphy.com/media/IzL667EYx01N8TXN9k/giphy.gif");
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}


input:focus {
	outline: 0 0 0 0 !important;
	box-shadow: 0 0 0 0 !important;
}


a {
    color: #8B0000;
}

.form-group {
	color: #c9c9c9;
}


.signinfont{
	color: white;
}

.login_btn {
	color: black;
	background-color: #ffc312;
	width: 100px;
}

.form_container {
	background-color: rgba(0, 0, 0, 0.5) !important;
	width: 300px;
	height: 370px;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 5px 10px 7px #888888;
}

.login_btn:hover {
	color: black;
	background-color: #ffff99;
}

.titlecontainer{
	display: flex;
	background: linear-gradient(to right, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.7));
	color: orange;
	height: 5%;
	color: white;
	padding-top: 8px;
}

h3 {
	color: #ffffff;
}

.container {
	height: 75%;
	align-content: center;
	display: flex;
	justify-content: center;
	align-items: center;
}

.register {
	color: white;
}

.register a {
	margin-left: 4px;
}

</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="style.css">
<title>Login Page</title>
</head>
<body>
<div class="w3-container w3-red" style="margin-left: 40px; padding-top: 40px;">
  <h1><span style="color: orange;font-weight:bold">Air</span> Deccan</h1>
</div>
	<div class="container">
		<div class="form_container" align="center">

			<form:form action="${pageContext.request.contextPath}/login/validatedLogin" id="loginForm"
				method="post" modelAttribute="userdetails">
				<div class="signinfont"><h3>Sign In</h3></div><br/>
				<div class="form-group">
					Username
					<form:input type="text" class="form-control" path="username"
						placeholder="username" />
				</div>
				<div class="form-group">
					Password
					<form:input type="password" class="form-control" path="pwd"
						placeholder="password" />
				</div>
				<div class="form-group">
							<input type="button" value="Login" onclick="javascript:fnLogin();"
						class="btn float-right login_btn">
				</div>
				<div class="register">
					New member? <br/><a href="${pageContext.request.contextPath}/login/signup">Register</a>
				</div>

			</form:form>
		</div>
	</div>
	
	<script>
function fnLogin(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/login/checkLogin",
		 data:$('#loginForm').serialize(),
		 method:"POST"
		})
		  .done(function( data ) {
			
		 	if(data.success=='true'){
			 		window.location.href='${pageContext.request.contextPath}/login/redirectLogin';
			 	}else{
				 	alert("Invalid Credentials");
				 	
				 	}
		  });
}
   
	</script>
</body>
</html>