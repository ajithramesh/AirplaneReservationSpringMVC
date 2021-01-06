
<%@page import="com.neu.flightTicket.model.CustomerInfo"%>


<%
	if (session.getAttribute("custinfo") == null) {
	request.getRequestDispatcher(request.getContextPath() + "/login/").forward(request, response);
}
CustomerInfo us = (CustomerInfo) session.getAttribute("custinfo");
boolean isAdmin = false;
if (us.getUser_role().equalsIgnoreCase("admin")) {
	isAdmin = true;
}
%>

<%
	if (isAdmin) {
%>
<div class="container">
	<h3 class="logo"><span style="color: orange;font-weight:bold">Air</span> Deccan</h3>
	<h3>
		Hi, ${userdetails.username} [Admin]<a
			href="${pageContext.request.contextPath}/login/logout"><span
			class="logout">Logout</span></a>
	</h3>
</div>

<div class="titlecontainer">
	<a href="${pageContext.request.contextPath}/addFlight">Add Plane</a>
	&ensp;&ensp;&ensp;|&ensp;&ensp;&ensp; <a
		href="${pageContext.request.contextPath}/updateFlights">View and Edit Plane</a> &ensp;&ensp;&ensp;|&ensp;&ensp;&ensp; <a
		href="${pageContext.request.contextPath}/viewPassengers">View Bookings </a> &ensp;&ensp;&ensp;|&ensp;&ensp;&ensp; <a
		href="${pageContext.request.contextPath}/searchFlightPage">Search Flights</a>
</div>
<%
	} else {
%>
<div class="container">
	<h3 class="logo">Air Deccan</h3>
	<h3>
		Hi, ${userdetails.username} [User]<a
			href="${pageContext.request.contextPath}/login/logout"><span
			class="logout">Logout</span></a>
	</h3>
</div>
<div class="titlecontainer">
<a href="${pageContext.request.contextPath}/searchFlightPage">Search Flights</a>
&ensp;&ensp;&ensp;|&ensp;&ensp;&ensp;
<a href="${pageContext.request.contextPath}/viewPassengers">My Bookings </a>
&ensp;&ensp;&ensp;|&ensp;&ensp;&ensp;
</div>
<%
	}
%>
