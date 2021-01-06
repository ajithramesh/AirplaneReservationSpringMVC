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
<title>Book Flight</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image:
		url('https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F28%2F2018%2F03%2Fempty-airplane-cabin-SEATSWAP0318.jpg&q=85');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
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

a {
	color: #8B0000;
}

.container-fluid {
	padding-top: 2%;
	display: flex;
	justify-content: center;
}

.logout:hover {
	font-size: 14px;
	color: red;
	font-style: italic;
}

.container {
	margin: 0px;
	padding-left: 10%;
	padding-right: 10%;
	width: 100%;
	color: #ffffff;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

.logout {
	font-size: 14px;
	color: #ffffff;
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

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
}

.submit {
	padding: 10px;
	text-align: center;
}

.title {
	float: left;
	margin-bottom: 7%;
	padding-left: 3%;
}

h3 {
	float: right;
	font-weight: 300 !important;
	display: inline;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="inquiry">
			<form:form action="${pageContext.request.contextPath}/finalBook"
				method="post" modelAttribute="bookFlight" name='ticketForm'
				id="bookFlight">
				<h3 class="title text-center">Book A Flight</h3>
				<div class="col-md-12" style="text-align: center;">
					<h4>
						<b>${flight.srcLoc}</b> to <b>${flight.destLoc}</b>
					</h4>
					<h5>${bookFlight.dept_time}</h5>
					<input readonly type="hidden" name='f_id' placeholder='flight_id'
						class="form-control" path="f_id" value="${flight.plane_id }" />
					<div class="form-group">
						<form:input type="hidden" name='sourceLocation'
							placeholder='location' class="form-control" path="sourceLocation"
							value="${flight.srcLoc }" />
					</div>

					<div class="form-group">
						<form:input type="hidden" name='destLocation'
							placeholder='destination' class="form-control"
							path="destLocation" value="${flight.destLoc }" />
					</div>

					<div class="form-group">
						<form:input type="hidden" name='dept_time'
							placeholder='departure_time' class="form-control"
							path="dept_time" value="${bookFlight.dept_time }" />
					</div>

					<div class="form-group">
						Passenger Name:
						<form:input type="text" name="passenger_name" class="form-control"
							required="required" placeholder="Passenger Name"
							path="passenger_name" />
					</div>
					<div class="form-group">
						Age:
						<form:input type="number" name="passenger_age"
							class="form-control" min="18" max="100"
							title="Age should be in between 18-100" required="required"
							placeholder="Age" path="passenger_age" />
					</div>
					<div class="form-group">
						Mobile Number:
						<form:input type="number" name="phone_number" class="form-control"
							required="required" placeholder="Mobile Number"
							path="phone_number" />
					</div>
					<div class="form-group">
						Email:
						<form:input type="email" name="passenger_email"
							class="form-control" required="required" placeholder="Email"
							path="passenger_email" />
					</div>

					<input type="button" value="Make Payment" name="MakePaymebnt"
						onclick="fnLoad();" class="btn btn-success">
				</div>
			</form:form>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Payment Screen</h4>
					</div>
					<div class="modal-body" id='paymentScreen'>
						<div class="row">
							<div class="col-xs-12 col-md-8">
								<div class="panel panel-default credit-card-box">
									<div class="panel-heading display-table">
										<div class="row display-tr">
											<h3 class="panel-title display-td">Payment Details</h3>
											<div class="display-td">
												<img class="img-responsive pull-right"
													src="http://i76.imgup.net/accepted_c22e0.png">
											</div>
										</div>
									</div>
									<div class="panel-body">
										<form role="form" id="payment-form"
											action="${pageContext.request.contextPath}/finalBook"
											method="post" modelAttribute="bookFlight" name='ticketForm'
											id="bookFlight" onsubmit="return fnProceed();">
											<div class="row">
												<div class="col-xs-12">
													<div class="form-group">
														<label for="cardNumber">CARD NUMBER</label>
														<div class="input-group">
															<input type="text" class="form-control" name="cardNumber"
																placeholder="Valid Card Number" autocomplete="cc-number"
																pattern="[0-9]{16}" title="Card No Should be 16 digits"
																required autofocus /> <span class="input-group-addon"><i
																class="fa fa-credit-card"></i></span>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-7 col-md-7">
													<div class="form-group">
														<label for="cardExpiry"><span class="hidden-xs">EXPIRATION</span><span
															class="visible-xs-inline">EXP</span> DATE</label> <input
															type="tel" class="form-control" name="cardExpiry"
															placeholder="MM / YY" autocomplete="cc-exp"
															pattern="([0-9]{2}[/]?){2}" required />


													</div>
												</div>
												<div class="col-xs-5 col-md-5 pull-right">
													<div class="form-group">

														<label for="cardCVC">CV CODE</label> <input type="number"
															class="form-control" name="cardCVC" max="999"
															pattern=".{3,}" placeholder="CVC"
															title="Please enter 3 digits" autocomplete="cc-number"
															required="required" />
													</div>
												</div>
											</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="form-group">
												<label for="couponCode">COUPON CODE</label> <input
													type="number" class="form-control" name="couponCode" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<button class="btn btn-success btn-lg btn-block"
												type="submit">Book Ticket</button>
										</div>
									</div>
									<div class="row" style="display: none;">
										<div class="col-xs-12">
											<p class="payment-errors"></p>
										</div>
									</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script>
		function fnLoad() {

			var temp = $('#bookFlight').serialize();
			console.log(temp);
			if (document.getElementById('bookFlight').checkValidity()) {
				$
						.ajax(
								{
									url : "${pageContext.request.contextPath}/checkBookedHistory",
									data : $('#bookFlight').serialize(),
									method : "POST"
								})
						.done(
								function(data) {

									if (data.success == 'true') {
										alert('You have already booked a ticket for the same passenger. ');
									} else {
										$('#myModal').modal('show');

									}
								});
			} else {
				alert('Kindly fill up the form. ');
			}

		}

		function fnProceed() {
			alert('Successfully completed the Payment. Proceeding....');
			$('#myModal').modal('hide');
			document.getElementById('bookFlight').submit();

			return false;
		}
	</script>
</body>
</html>