<?php

/**
Script Developed by keval choudhary on 11-04-2017
for more, please visit www.creativek.me
*/

// create associative array
$response = array();

// chech for valid paramters
if(isset($_POST["user-name"],$_POST["password"])){
	$user_name =  $_POST["user-name"];
	$password = $_POST["password"];
}else{
$response["result"] = FALSE;
$response["message"] = "Some Information was not received";
echo(json_encode($response));
exit();
}

// process your data here like, connecting to databse and other logic codding etc
if($user_name == "creativek" && $password == "creative123"){
	$response["result"] = TRUE;
	$response["message"] = "RIGHT CREDENTIAL";
}else{
	$response["result"] = TRUE;
	$response["message"] = "WRONG CREDENTIAL";
}

//convert array into json and print it
echo(json_encode($response));
?>
