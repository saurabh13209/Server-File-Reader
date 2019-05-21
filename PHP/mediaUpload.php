<?php
	ini_set("display_errors","1");
	error_reporting(E_ALL);
	$imageName = $_POST["name"];
	$imageName = "SFR/media/".$imageName;
	$image = $_POST['image'];
	echo($imageName);
	file_put_contents($imageName,base64_decode($image));
?>
