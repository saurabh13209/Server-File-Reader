<?php
$dir = $_GET['link'];
$a = scandir($dir);
$i=2;
$arrayMain = array();
$index=0;
for($i ; $i<sizeof($a);$i++){
    if(!(substr($a[$i],0,1) === ".")){
        $arrayMain[$index] = $a[$i];
	$index++;
    }
}
echo json_encode($arrayMain);
?>
