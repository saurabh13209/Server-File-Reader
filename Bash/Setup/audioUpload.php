<?php
	ini_set("display_errors","1");
	error_reporting(E_ALL);
	$imageName = $_POST["name"];
	$imageName = "SFR/audio/".$imageName;
	$image = $_POST['image'];
	file_put_contents($imageName,base64_decode($image));
?>
