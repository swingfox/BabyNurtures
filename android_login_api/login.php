<?php

/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data

  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user == TRUE) {
            // user found$response["error"] = FALSE;
                $response["uid"] = $user["unique_id"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["birthday"] = $user["birthday"];
                $response["user"]["height"] = $user["height"];
                $response["user"]["weight"] = $user["weight"];
                $response["user"]["nationality"] = $user["nationality"];
                $response["user"]["gender"] = $user["gender"];
                $response["user"]["country"] = $user["country"];
                $response["user"]["city"] = $user["city"];
                $response["user"]["babysitter"] = $user["babysitter"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
             $userx = $db->isUserExisted($email);

            if($userx == TRUE){
                $response["error"] = TRUE;
                $response["error_msg"] = "Incorrect password!";
                 echo json_encode($response);

            }else{
                 $response["error"] = TRUE;
                $response["error_msg"] = "User does not Exist!";
                 echo json_encode($response);
            
            }
        }

}
else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}
?>
