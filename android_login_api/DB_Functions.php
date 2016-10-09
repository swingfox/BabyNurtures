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
    public function storeUser($name, $email, $password, $birthday, $height, $weight, $nationality, $gender , $country, $city, $babysitter) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("INSERT INTO users(unique_id, name, email, encrypted_password, birthday, height, weight, nationality, gender , country, city, babysitter, salt, created_at) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$birthday', '$height', '$weight', '$nationality','$gender', '$country',  '$city',  '$babysitter',  '$salt', NOW())");
        
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
    public function getUserByEmailAndPassword($email, $password) {
        $result = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
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
    public function isUserExisted($email) {
        $result = mysql_query("SELECT email from users WHERE email = '$email'");
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
     * Write a message
     */
    public function writeMessage($sender,$receiver,$message){
        $result = mysql_query("INSERT INTO chat (sender,receiver,message) VALUES ($sender,$receiver,$message)");
        return true;
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





public function storeList($task, $startTime, $endTime, $created_by){

        $result = mysql_query("INSERT INTO todolist(task, startTime, endTime, created_by, created_at) VALUES ('$task', '$startTime', '$endTime', '$created_by', NOW() )");

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


public function displayList($created_by){

    $sql = "SELECT * FROM `todolist` WHERE created_by = '$created_by' ORDER BY `startTime` ASC, `endTime` ASC";
    $result = mysql_query($sql);//"SELECT * FROM todolist where created_by = '$created_by'");
    echo $sql;
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







}
/*function xlog($msg){

        $file = fopen("C:/app-logs/mylogs.log", "a");
        fwrite($file, $msg."\n");
        fclose($file);
    }
*/
?>
