<?php

class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($username, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("INSERT INTO users(unique_id, username, email, encrypted_password, salt, created_at, first_time,type) VALUES('$uuid', '$username', '$email', '$encrypted_password',  '$salt', NOW(),'Yes','1')");
        
  //      xlog($result);

        // check for successful store
        if ($result) {
            // get user details 
            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM users WHERE uid = $uid");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByUserAndPassword($username, $password) {
        $result = mysql_query("SELECT * FROM users WHERE username = '$username'") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }

     public function getSitterByUserAndPassword($username, $password) {
        $result = mysql_query("SELECT * FROM babysitter WHERE name = '$username'") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }


    /**
     * Check user is existed or not
     */
    public function isUserExisted($username) {
        $result = mysql_query("SELECT username from users WHERE username = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }

    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }


public function storeBaby($firstname, $middlename, $lastname,$birthday,$height,$weight,$nationality,$gender,$city,$country,$created_by){
    $picture = "http://172.31.11.32:80/android_login_api/picture/Default.png";
    
    $result = mysql_query("INSERT INTO baby(profile_picture,
                                            firstname,
                                            middlename,
                                            lastname,
                                            birthday,
                                            height,
                                            weight,
                                            nationality,
                                            gender,
                                            city,
                                            country,
                                            created_by,
                                            created_at) VALUES ('$picture',
                                                            '$firstname',
                                                            '$middlename',
                                                            '$lastname',
                                                            '$birthday',
                                                            '$height',
                                                            '$weight',
                                                            '$nationality',
                                                            '$gender',
                                                            '$city',
                                                            '$country',
                                                            '$created_by',
                                                            NOW())");

     if ($result) {
            // get user details 
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM baby WHERE id = $id");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
}

 public function isSitterExisted($username) {
        $result = mysql_query("SELECT name from babysitter WHERE name = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }

public function storeBabySitter($username,$password,$created_by){
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

    $result = mysql_query("INSERT INTO babysitter (name, encrypted_password, salt, created_by, created_at,type) 
        VALUES ('$username','$encrypted_password','$salt','$created_by', NOW(),'0' ) ");

        // check for successful store
        if ($result) {
            // get user details 
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM babysitter WHERE id = $id");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
}



public function storeList($task, $startTime, $endTime, $created_for, $created_by){

        $result = mysql_query("INSERT INTO todolist(task, startTime, endTime, created_for,created_by, created_at) VALUES ('$task', '$startTime', '$endTime', '$created_for','$created_by', NOW() )");

         if ($result) {
            // get user details 
            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM todolist WHERE uid = $uid");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }


public function displayList($created_by, $created_for){


    $sql = "SELECT * FROM `todolist` WHERE created_by = '$created_by' ORDER BY `startTime` ASC, `endTime` ASC";
    $result = mysql_query($sql);//"SELECT * FROM todolist where created_by = '$created_by'");
    $num_rows = mysql_num_rows($result);

    while($row = mysql_fetch_array($result)){


            $r[] = $row;
            $check = $row['uid'];

    }

    if($check == NULL){


        $r[$num_rows] = "Record is not available!";
        echo json_encode($r);
    }else{
        $r[$num_rows] = "Success!";
        echo json_encode($r);
    }

}


public function deleteAll($created_by){


    $result = mysql_query("DELETE FROM todolist WHERE created_by = '$created_by'");

    if ($result) {
            // get user details 
          return true;
        } else {
            return false;
            
        }

    

}

public function updateAccount($mainUser){

    $result = mysql_query("UPDATE users SET 
                            first_time = 'No'
                            WHERE username = '$mainUser' ");

     if ($result) {
            // get user details 
            $result = mysql_query("SELECT * FROM users WHERE username = '$mainUser'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
}

public function UpdatePicture($id, $path){

    $result = mysql_query("UPDATE baby SET 
                            profile_picture = '$path'
                            WHERE id = '$id' ");

    /* if ($result) {
            // get user details 
            $result = mysql_query("SELECT * FROM users WHERE username = '$mainUser'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }*/
}


}
/*function xlog($msg){

        $file = fopen("C:/app-logs/mylogs.log", "a");
        fwrite($file, $msg."\n");
        fclose($file);
    }
*/
?>
