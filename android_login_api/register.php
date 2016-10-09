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


if ($tag == 'register') {
        // Request type is Register new user
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $birthday = $_POST['birthday'];
        $height = $_POST['height'];
        $weight = $_POST['weight'];
        $nationality = $_POST['nationality'];
        $gender = $_POST['gender'];
        $country = $_POST['country'];
        $city = $_POST['city'];
        $babysitter = $_POST['babysitter'];

        // check if user is already existed
        if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed!";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($name, $email, $password, $birthday, $height, $weight, $nationality,$gender, $country, $city, $babysitter);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $user["unique_id"];
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
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else {
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
