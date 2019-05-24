<?php
	ini_set("display_errors","1");
	error_reporting(E_ALL);
	$file_path = "SFR/media/";
	$file_path = $file_path. basename($_FILES['uploaded_file']['name']);
	
	if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'],$file_path)){
		echo"success";
	}else{
		echo"error";
	}
?>
