
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
<title>Search Results Page</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image:
		url('https://wonderfulengineering.com/wp-content/uploads/2014/05/airplane-wallpaper-34-610x381.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
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

.btn:hover {
	background: #568bb7;
	color: #ffffff;
}

a {
	color: #8B0000;
}

.logout:hover {
	font-size: 14px;
	color: red;
	font-style: italic;
}

#admin th {
	text-align: left;
	background-color: #4CAF50;
	color: white;
	vertical-align: middle;
	text-align: center;
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

#admin tr:nth-child(even) {
	background-color: #f2f2f2;
}

.btn {
	display: inline-block;
	margin-bottom: 15px;
	font-weight: 400;
	text-align: center;
	background: #ffffff;
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

.logout {
	font-size: 14px;
	color: #ffffff;
	font-style: italic;
}

.addFlight {
	float: left;
	background: green !important;
	border: 1px solid #ffffff;
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

#admin td, #admin th {
	border: 1px solid #ddd;
	padding: 7px;
}

#admin tr:hover {
	background-color: #ddd;
}

#admin td {
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
			Flight Search Results for From : <B>${searchFlight.srcLocation}</B>
			&nbsp; To : <B>${searchFlight.destLocation}</B> Departure On <B>${searchFlight.dept_time}</B>
		</div>

	</div>


	<div class="addFlight_Table">
		<div class="table-responsive">

			<table class="table" id="admin">
				<thead>
					<tr>
						<th>Source</th>
						<th>Destination</th>
						<th>Departure Time</th>
						<th>Arrival Time</th>
						<th>Total Seats</th>
						<th>Booked Seats</th>
						<th>Ticket Price</th>
						<th>Book Seats</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="flight" items="${flightList}">
						<form:form action="${pageContext.request.contextPath}/bookFlight"
							method="post" modelAttribute="flight">
							<input readonly type="hidden" name='plane_id'
								placeholder='flight_id' class="form-control" path="plane_id"
								value="${flight.plane_id }" />

							<input readonly type="hidden" name='dept_time'
								placeholder='departure_date' class="form-control"
								path="dept_time" value="${searchFlight.dept_time}" />
							<tr id='addr0' data-id="0">

								<td data-name="srcLoc"><input readonly type="text"
									name='srcLoc' placeholder='from' class="form-control"
									path="srcLoc" value="${flight.srcLoc }" /></td>
								<td data-name="destLoc"><input readonly type="text"
									name='destLoc' placeholder='from' class="form-control"
									path="destLoc" value="${flight.destLoc }" /></td>
								<td data-name="dept_time"><input readonly type="text"
									name='dept_time' placeholder='from' class="form-control"
									path="dept_time" value="${flight.dept_time }" /></td>
								<td data-name="arrival_time"><input readonly type="text"
									name='arrival_time' placeholder='from' class="form-control"
									path="arrival_time" value="${flight.arrival_time }" /></td>
								<td data-name="seatsQty"><input readonly type="text"
									name='seatsQty' placeholder='from' class="form-control"
									path="seatsQty" value="${flight.seatsQty }" /></td>
								<td data-name="seatsBooked"><input readonly type="text"
									name='seatsBooked' placeholder='from' class="form-control"
									path="seatsBooked" value="${flight.seatsBooked }" /></td>
								<td data-name="ticketPrice"><input readonly type="text"
									name='ticketPrice' placeholder='from' class="form-control"
									path="ticketPrice" value="${flight.ticketPrice }" /></td>
								<td data-name="submit"><input type="submit"
									value="Book Seats"></td>
							</tr>
						</form:form>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>