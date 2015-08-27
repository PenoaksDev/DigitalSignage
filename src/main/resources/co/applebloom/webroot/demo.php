<html>
<head><title>Digital Signage Demo Display</title></head>
<body style="background: url('http://localhost:8080/images/bg_demo.jpg');">
	<p>
		Text before button
		<button>This is a custom tagged button</button>
		Text after button
	</p>
	
	<h1><?= java_bean("signage")->hello(); ?></h1>
	
	<!-- Javascript does not seem to function on a Resin server. Not sure if this is something we will want. -->
	<script type="text/javascript">
		alert( "This website uses Javascript" );
	</script>
</body>
</html>
