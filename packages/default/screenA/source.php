<html>
<head>
<title>Digital Signage Demo Display</title>
</head>
<body
	style="background: url('http://localhost:8080/images/bg_demo.jpg');">
	<p>
		Text before button
		<button>This is a custom tagged button</button>
		Text after button
	</p>

	<object classid="co.applebloom.apps.signage.components.MediaFrame">
		<param name="padding" value="10" />
		<param name="stringWidth" value="1050" />
		<param name="stringHeight" value="600" />
	</object>



	<h1>
		<?= java_bean("signage")->hello(); ?>
	</h1>

	<!-- Javascript does not seem to function on a Resin server. Not sure if this is something we will want. -->
	<script type="text/javascript">
		alert( "This website uses Javascript" );
	</script>
</body>
</html>
