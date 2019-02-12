<?php
//$dir = $_GET["path"];
$dir = "../../HelpLab";
$a = scandir($dir);
$i=2;
$arrayMain = array();
for($i ; $i<sizeof($a);$i++){
    $arrayMain[$i-2] = $a[$i];
}
echo json_encode($arrayMain);
?>