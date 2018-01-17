<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<style type="text/css">
.btn {
	background: #3498db;
	background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
	background-image: -moz-linear-gradient(top, #3498db, #2980b9);
	background-image: -ms-linear-gradient(top, #3498db, #2980b9);
	background-image: -o-linear-gradient(top, #3498db, #2980b9);
	background-image: linear-gradient(to bottom, #3498db, #2980b9);
	-webkit-border-radius: 24;
	-moz-border-radius: 24;
	border-radius: 24px;
	font-family: Arial;
	color: #ffffff;
	font-size: 18px;
	padding: 10px 20px 10px 20px;
	text-decoration: none;
}

.btn:hover {
	background: #3cb0fd;
	background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
	background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
	background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
	background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
	background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
	text-decoration: none;
}
</style>

</head>
<body>
	<h1>Hello World!</h1>

	<P>The time on the server is ${serverTime}.</P>
	</br>
	<P>My First Report</P>

	<div>
		<form id="reportForm" method="POST" target="_blank">
			<div>Click To View Report</div>

			</br> </br>

			<div>
				<div>View Report As</div>
				</br>
				<div>
					<input type="button" class="btn" value="PDF" id="showPdf" /> <input
						type="button" class="btn" value="HTML" id="showHtml" /> <input
						type="button" class="btn" value="EXCEL" id="showExcel" />
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript"
		src="<c:url value="http://localhost:8095/demo/resources/js/jquery.js"></c:url>">
		
	</script>

	<script>
		$(document)
			.ready(
				function() {
	
					$('#showPdf')
						.click(
							function() {
								$('#reportForm')
									.attr('action',
										"http://localhost:8095/demo/build/buildPdfReport");
								$('#reportForm').submit();
							});
	
					$('#showHtml')
						.click(
							function() {
								$('#reportForm')
									.attr('action',
										"http://localhost:8095/demo/build/buildHtmlReport");
								$('#reportForm').submit();
	
							}
	
					);
					$('#showExcel')
						.click(
							function() {
								$('#reportForm')
									.attr('action',
										"http://localhost:8095/demo/build/buildExcelReport");
								$('#reportForm').submit();
							});
				});
	</script>




</body>
</html>
