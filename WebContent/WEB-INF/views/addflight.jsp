<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Add Flight Page</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url('https://jennstrathman.files.wordpress.com/2011/03/delta-airpolanes.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

.container {
	margin: 0px;
	padding-left: 10%;
	padding-right: 10%;
	width: 100%;
	color: #ffffff;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

h3 {
	float: right;
	font-weight: 300 !important;
	display: inline;
}

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
}

.logout {
	font-size: 14px;
	color: #ffffff;
	font-style: italic;
}

.btn:hover {
	background: #568bb7;
	color: #ffffff;
}

.form_container {
	background-color: rgba(0, 0, 0, 0.5) !important;
	padding: 5%;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0px 0px 7px #777;
}

.container-fluid {
	padding-top: 7%;
	display: flex;
	justify-content: flex-start;
	align-items: center;
}

.btn {
	display: inline-block;
	margin-bottom: 15px;
	font-weight: 400;
	text-align: center;
	background: #ffffff;
}

a {
    color: #8B0000;
}

.addFlight_Table {
	padding: 10px;
	background-color: rgba(0, 0, 0, 0.7) !important;
}


.logout:hover {
	font-size: 14px;
	color: red;
	font-style: italic;
}


.addFlight {
	float: left;
	background: green !important;
	border: 1px solid #ffffff;
}

#admin {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
	margin-bottom: 0px;
}

#admin td, #admin th {
	border: 1px solid #ddd;
	padding: 7px;
}

#admin tr:nth-child(even) {
	background-color: #f2f2f2;
}

#admin tr:hover {
	background-color: #ddd;
}

.titlecontainer{
	display: flex;
	background: linear-gradient(to right, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.7));
	color: orange;
	height: 5%;
	color: white;
	padding-top: 8px;
}

#admin th {
	text-align: left;
	background-color: #913d4e;
	color: white;
	vertical-align: middle;
	text-align: center;
}

#admin td {
	vertical-align: middle;
	text-align: center;
}

.error {
	color: red;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container-fluid">
		<div style="display: block; width: 100%; text-align: center; font-size: 16px; padding-right: 7%; font-weight: bold;"> Add Planes </div>
	</div>

	<div class="addFlight_Table">
		<div class="table-responsive">
			<form:form action="${pageContext.request.contextPath}/addflight"
				onsubmit="return fnCheck();" id='addFlight' method="post"
				modelAttribute="flight">
				<table class="table" id="admin">
					<thead>
						<tr>
							<th>Source</th>
							<th>Destination</th>
							<th>Departure Time</th>
							<th>Arrival Time</th>
							<th>Total Seats</th>
							<th>Ticket Price</th>
							<th>Add Flight</th>
						</tr>
					</thead>
					<tbody>
						<tr id='addr0' data-id="0">

							<td data-name="from"><form:input type="text" name='from'
									required='required' placeholder='from' class="form-control"
									path="srcLoc" /></td>


							<td data-name="Dest"><form:input type="text" name='Dest'
									required='required' placeholder='Dest' class="form-control"
									path="destLoc" /></td>


							<td data-name="Departure_Time"><form:input type="time"
									required='required' name='Departure_Time'
									placeholder='Departure Time' class="form-control"
									path="dept_time" /></td>

							<td data-name="Arrival_Time"><form:input type="time"
									required='required' name='Arrival_Time'
									placeholder='Arrival Time' class="form-control"
									path="arrival_time" /></td>

							<td data-name="Total_Seats"><form:input type="number"
									required='required' name='Total_Seats'
									placeholder='Total Seats' min="10" max="500"
									title="min 10 and max 500" class="form-control" path="seatsQty" /></td>

							<td data-name="ticketCost"><form:input type="number"
									required='required' min="100" max="5000"
									title="min 100 and max 5000" name='ticketCost'
									placeholder='Ticket Cost' class="form-control"
									path="ticketPrice" /></td>

							<td data-name="submit"><input type="submit"
								value="Add Flight"></td>
						</tr>
						<tr id='addr1' data-id="1">
							<td data-name="from"><form:errors path="srcLoc"
									cssClass="error" /></td>
							<td data-name="from"><form:errors path="destLoc"
									cssClass="error" /></td>
							<td data-name="from"><form:errors path="dept_time"
									cssClass="error" /></td>
							<td data-name="from"><form:errors path="arrival_time"
									cssClass="error" /></td>
							<td data-name="from"><form:errors path="seatsQty"
									cssClass="error" /></td>
							<td data-name="from"><form:errors path="ticketPrice"
									cssClass="error" /></td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
	<script>
		function fnCheck() {
			if (document.getElementById('addFlight').checkValidity()) {
				if (document.forms[0].location.value.toUpperCase() == document.forms[0].destination.value
						.toUpperCase()) {
					alert('You can not have same source and destination. ');
					return false;
				}
				return true;
			}
			return false;
		}
	</script>
</body>
</html>