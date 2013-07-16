<html>
<head>
<title>Digital Signage Web Interface</title>
</head>
<body>
	<?
		$signage = java_bean("signage");
		
	?>
	
	<?=$signage->reloadScreen( 1 );?>
</body>
</html>