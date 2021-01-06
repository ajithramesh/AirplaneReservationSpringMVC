
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Update Flight Page</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image:
		url('https://www.telegraph.co.uk/content/dam/Travel/2016/April/flyover-AP99499604.jpg');
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

a {
	color: #8B0000;
}

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
}

.addFlight {
	float: left;
	background: green !important;
	border: 1px solid #ffffff;
}

.logout:hover {
	font-size: 14px;
	color: red;
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

.btn {
	display: inline-block;
	margin-bottom: 15px;
	font-weight: 400;
	text-align: center;
	background: #ffffff;
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

h3 {
	float: right;
	font-weight: 300 !important;
	display: inline;
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

#admin td {
	vertical-align: middle;
	text-align: center;
}

.error {
	color: red;
}

#admin td, #admin th {
	border: 1px solid #ddd;
	padding: 7px;
}

.addFlight_Table {
	padding: 10px;
	background-color: rgba(0, 0, 0, 0.7) !important;
}

#admin {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
	margin-bottom: 0px;
}

#admin tr:nth-child(even) {
	background-color: #f2f2f2;
}

#admin tr:hover {
	background-color: #ddd;
}

#admin th {
	text-align: left;
	background-color: #913d4e;
	color: white;
	vertical-align: middle;
	text-align: center;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<!--<div style="display: block;width: 100%">
    <button class="btn btn-primary addFlight">Add Flight</button>
   </div> -->
		<div
			style="display: block; width: 100%; text-align: center; font-size: 16px; padding-right: 7%; font-weight: bold;">
			Update Flights Here</div>

	</div>
	<b class="error">${message}</b>

	<div class="">
		<div class="">
			<table class="table" id="admin">
				<thead>
					<tr>
						<th>Flight Id</th>
						<th>Source</th>
						<th>Destination</th>
						<th>Departure Time</th>
						<th>Arrival Time</th>
						<th>Total Seats</th>
						<th>No of Bookings</th>
						<th>Ticket Cost</th>
						<th>Update</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="flight" items="${flightList}">


						<form:form
							action="${pageContext.request.contextPath}/updateFlights"
							method="post" modelAttribute="flight">
							<tr id='addr0' data-id="0">

								<td data-name="plane_id"><input readonly type="text"
									name='plane_id' style="outline: none;" placeholder='flight_id'
									class="form-control text-center" path="plane_id"
									value="${flight.plane_id }" /></td>


								<td data-name="srcLoc"><input type="text" name='srcLoc'
									required="required" placeholder='from' class="form-control"
									path="srcLoc" value="${flight.srcLoc }" /></td>


								<td data-name="destLoc"><input type="text" name='destLoc'
									required="required" placeholder='Dest' class="form-control"
									path="destLoc" value="${flight.destLoc }" /></td>


								<td data-name="dept_time"><input type="text"
									required="required" name='dept_time'
									placeholder='Departure Time' class="form-control"
									path="dept_time" value="${flight.dept_time }" /></td>


								<td data-name="arrival_time"><input type="text"
									required="required" name='arrival_time'
									placeholder='Arrival Time' class="form-control"
									path="arrival_time" value="${flight.arrival_time }" /></td>


								<td data-name="seatsQty"><input type="number"
									required="required" name='seatsQty' placeholder='Total Seats'
									min="10" max="500" title="min 10 and max 500"
									class="form-control" path="seatsQty"
									value="${flight.seatsQty }" /></td>



								<td data-name="seatsBooked"><input type="text"
									name='seatsBooked' placeholder='Booked Seats'
									readonly="readonly" class="form-control" path="seatsBooked"
									value="${flight.seatsBooked }" /></td>
								<td data-name="ticketPrice"><input type="number"
									name='ticketPrice' placeholder='Ticket Cost'
									required='required' min="100" max="5000"
									title="min 100 and max 5000" class="form-control"
									path="ticketPrice" value="${flight.ticketPrice }" /></td>

								<td data-name="submit"><input type="submit" value="Update"></td>
							</tr>



						</form:form>

					</c:forEach>
					<tr id='addr1' data-id="1">
						<td data-name="from"><form:errors path="plane_id"
								cssClass="error" /></td>
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
						<td data-name="from"><form:errors path="seatsBooked"
								cssClass="error" /></td>
						<td data-name="from"><form:errors path="ticketPrice"
								cssClass="error" /></td>


					</tr>
				</tbody>
			</table>


		</div>
	</div>
</body>
</html>