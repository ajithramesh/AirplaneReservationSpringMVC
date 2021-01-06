<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="customer.css">
<title>Search Flights</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url('https://wallpapercave.com/wp/wy48Yro.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

h3 {
	float: right;
	font-weight: 300 !important;
	display: inline;
}

.container {
	margin: 0px;
	padding-left: 10%;
	padding-right: 10%;
	width: 100%;
	color: #ffffff;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

.error {
	color: red;
}

a {
	color: #8B0000;
}

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
}

.titlecontainer {
	display: flex;
	background: linear-gradient(to right, rgba(255, 255, 255, 0.2),
		rgba(255, 255, 255, 0.7));
	color: orange;
	height: 5%;
	color: white;
	padding-top: 8px;
}

.title {
	float: left;
	margin-bottom: 10%;
	padding-left: 5%;
	text-align: center;
}

.logout:hover {
	font-size: 14px;
	color: red;
	font-style: italic;
}

.inquiry {
	background-color: rgba(0, 0, 0, 0.5) !important;
	color: #ffffff;
	padding: 5%;
	padding-top: 2%;
	padding-bottom: 2%;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
}

.form-group {
	text-align: left;
}

.container-fluid {
	padding-top: 2%;
	display: flex;
	justify-content: center;
}

.logout {
	font-size: 14px;
	color: #ffffff;
	font-style: italic;
}

.submit {
	padding: 10px;
	text-align: center;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="inquiry">

			<form:form action="${pageContext.request.contextPath}/searchFlights"
				method="post" modelAttribute="searchFlight">
				<h3 class="title">SEARCH FLIGHT</h3>
				<div class="col-md-12" style="text-align: center;">


					<div class="form-group">
						From:
						<form:input type="text" name="srcLocation" class="form-control"
							placeholder="From" path="srcLocation" />
						<form:errors path="srcLocation" cssClass="error" />
					</div>
					<div class="form-group">
						To:
						<form:input type="text" name="destLocation" class="form-control"
							placeholder="to" path="destLocation" />
						<form:errors path="destLocation" cssClass="error" />
					</div>
					<div class="form-group">
						Depart on:
						<form:input type="date" class="form-control"
							placeholder="Depart on" path="dept_time" id='dept_time' />
						<form:errors path="dept_time" cssClass="error" />
					</div>
					<div class="form-group">
						Passenger:
						<form:input type="number" name="pass_count" class="form-control"
							value="1" placeholder="Passenger" path="pass_count" />
						<form:errors path="pass_count" cssClass="error" />
					</div>
					<input type="submit" value="submit" name="submit"
						class="btn btn-danger">
				</div>
			</form:form>
		</div>
		<script>
			document.getElementById('departure_time').valueAsDate = new Date();
		</script>

	</div>

</body>
</html>