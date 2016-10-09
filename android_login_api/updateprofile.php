<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    // require_once 'DB_Functions.php';
    // $db = new DB_Functions();

    // response Array
    require 'config/config.php';
    $config = new config();
    $response = array("tag" => $tag, "error" => FALSE);


    if ($tag == 'updateprofile') {
        // Request type is Register new user
        $id = $_POST['id'];
        $firstname = $_POST['firstname'];
        $middlename = $_POST['middlename'];
        $lastname = $_POST['lastname'];
        $birthday = $_POST['birthday'];
        $height = $_POST['height'];
        $weight = $_POST['weight'];
        $nationality = $_POST['nationality'];
        $gender = $_POST['gender'];
        $city = $_POST['city'];
		$country = $_POST['country'];
        $result = $config->execute("UPDATE baby SET 
        							firstname = '$firstname', 
        							middlename = '$middlename', 
        							lastname = '$lastname', 
        							birthday = '$birthday', 
        							height = '$height', 
        							weight = '$weight', 
        							nationality = '$nationality', 
        							gender = '$gender',
        							country = '$country',
        							city = '$city'
        							WHERE id = '$id'");
        if ($result) {
        	$response = array();
        	$response["error"] = FALSE;
        	$response["fname"] = $firstname;
        	echo json_encode($response);
        } else {
        	$response = array();
        	$response["error"] = FALSE;
        	echo json_encode($response);
        }
        echo json_encode($firstname." ".$middlename." ".$lastname." ".$birthday." ".$height." ".$weight." ".$nationality." ".$gender." ".$city." ".$country);
       }
   }
?>