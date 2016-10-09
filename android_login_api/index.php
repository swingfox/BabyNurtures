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
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // check for tag type
       if ($tag == 'login') {
        // Request type is check Login
        $username = $_POST['username'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByUserAndPassword($username, $password);
        if ($user == TRUE) {
            // user found$response["error"] = FALSE;
                $response["uid"] = $user["unique_id"];
                $response["user"]["username"] = $user["username"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["first_time"] = $user["first_time"];
                $response["user"]["type"] = $user["type"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
             $userx = $db->isUserExisted($username);

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

} else if ($tag == 'loginSitter') {
        // Request type is check Login
        $username = $_POST['username'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getSitterByUserAndPassword($username, $password);
        if ($user == TRUE) {
            // user found$response["error"] = FALSE;
                $response["id"] = $user["id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["created_by"] = $user["created_by"];
                $response["user"]["type"] = $user["type"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
             $userx = $db->isUserExisted($username);

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
 else if($tag == 'profile'){

    $user = $db->getDetails();
    $response["name"] = $user["name"];
    echo json_encode($response);
}
else if($tag == 'todo') {
        // Request type is Register new user
        $task = $_POST['task'];
        $startTime = $_POST['startTime'];
        $endTime = $_POST['endTime'];
        $created_for = $_POST['created_for'];
        $created_by = $_POST['created_by'];
        
            // store user
            $task = $db->storeList($task, $startTime, $endTime, $created_for,$created_by);
            if ($task) {
                // ADDDED CODE BY IAN:
                    require 'NotificationManager.php';
                    $sql = "SELECT name from babysitter WHERE created_by = '".$task["created_by"]."'";
                    $config = new config();
                    $result = $config->execute($sql);
                    $notifMgr = new NotficationManager();
                    if($config->num_rows($result)>0){
                        while($row = mysqli_fetch_assoc($result)) {
                            $notifMgr->addNotification($task["task"],$task["created_by"],$row["name"]);        
                        }
                    }
                    else{
                        $notifMgr->addNotification("wala ni sud","ian","Tester");
                    }
                //
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $task["uid"];
                $response["task"]["task"] = $task["task"];
                $response["task"]["startTime"] = $task["startTime"];
                $response["task"]["endTime"] = $task["endTime"];
                $response["task"]["created_by"] = $task["created_by"];
                $response["task"]["created_for"] = $task["created_for"];
                $response["task"]["created_at"] = $task["created_at"];
                $response["task"]["updated_at"] = $task["updated_at"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Listing";
                echo json_encode($response);
            }
        }
    else if ($tag == 'register') {
        // Request type is Register new user
        $username = $_POST['username'];
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check if user is already existed
        if ($db->isUserExisted($username)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed!";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($username, $email, $password);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $user["unique_id"];
                $response["user"]["username"] = $user["username"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["first_time"] = $user["first_time"];
                $response["user"]["type"] = $user["type"];

                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    }
     else if ($tag == 'registerBaby') {
        // Request type is Register new user
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
        $created_by = $_POST['created_by'];


        // check if user is already existed
      //  if ($db->isUserExisted($username)) {
            // user is already existed - error response
        //    $response["error"] = TRUE;
        //    $response["error_msg"] = "User already existed!";
        //    echo json_encode($response);
       // } else {
            // store user
            $user = $db->storeBaby($firstname, $middlename, $lastname,$birthday,$height,$weight,$nationality,$gender,$city,$country,$created_by);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $user["id"];
                $response["user"]["firstname"] = $user["firstname"];
                $response["user"]["middlename"] = $user["middlename"];
                $response["user"]["lastname"] = $user["lastname"];
                $response["user"]["birthday"] = $user["birthday"];
                $response["user"]["height"] = $user["height"];
                $response["user"]["weight"] = $user["weight"];
                $response["user"]["nationality"] = $user["nationality"];
                $response["user"]["gender"] = $user["gender"];
                $response["user"]["city"] = $user["city"];
                $response["user"]["country"] = $user["country"];
                $response["user"]["created_by"] = $user["created_by"];

                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        //}
    }

     else if ($tag == 'babySitregister') {
        // Request type is Register new user
        $username = $_POST['username'];
        $password = $_POST['password'];
        $created_by = $_POST['created_by'];
        $mainUser = $_POST['mainUser'];


        // check if user is already existed
        if ($db->isSitterExisted($username)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed!";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeBabySitter($username,$password,$created_by);
            if ($user) {
                // user stored successfully
                $test = $db->updateAccount($mainUser);
                $response["error"] = FALSE;
                $response["uid"] = $user["id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["created_by"] = $user["created_by"];
                $response["user"]["type"] = $user["type"];
                $response["test"]["username"] = $test["username"];
                $response["test"]["email"] = $test["email"];
                $response["test"]["first_time"] = $test["first_time"];
                $response["test"]["type"] = $test["type"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    }


else if($tag == 'ViewList'){

                $created_by = $_POST['created_by'];
                $created_for = $_POST['created_for'];

                /*$created_by = */$db->displayList($created_by, $created_for);
               /* if ($created_by) {
                // user stored successfully
                $response["error"] = FALSE;
                echo json_encode($response);
                }
                else{
                    $response["error"] = TRUE;
                $response["error_msg"] = "No Available Activities yet!";
                echo json_encode($response);

                }       */
                
}
else if($tag == 'DeleteList'){

             $created_by = $_POST['created_by'];

           $task = $db->deleteAll($created_by);

           if($task){

            $response["error"] = FALSE;
            echo json_encode($response);
           }else{

            $response["error"] = TRUE;
            $response["error_msg"] = "Unable to Delete List!";
            echo json_encode($response);
           }




        }


             else if($tag == 'todo') {
        // Request type is Register new user
        $task = $_POST['task'];
        $startTime = $_POST['startTime'];
        $endTime = $_POST['endTime'];
        $created_by = $_POST['created_by'];
        
            // store user
            $task = $db->storeList($task, $startTime, $endTime, $created_by);
            if ($task) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $task["uid"];
                $response["task"]["task"] = $task["task"];
                $response["task"]["startTime"] = $task["startTime"];
                $response["task"]["endTime"] = $task["endTime"];
                $response["task"]["created_by"] = $task["created_by"];
                $response["task"]["created_at"] = $task["created_at"];
                $response["task"]["updated_at"] = $task["updated_at"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Listing";
                echo json_encode($response);
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
