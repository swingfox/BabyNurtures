<?php
define('DB_SERVER','localhost');
define('DB_USER','root');
define('DB_PASS' ,'');
define('DB_NAME', 'android_api');

class DB_con
{
 function __construct()
 {
  $conn = mysql_connect(DB_SERVER,DB_USER,DB_PASS) or die('localhost connection problem'.mysql_error());
  mysql_select_db(DB_NAME, $conn);
 }
 
 public function select()
 {
  $res=mysql_query("SELECT * FROM chat");
  return $res;
 }
 
 public function delete($table,$id)
 {
  $res = mysql_query("DELETE FROM $table WHERE message_id=".$id);
  return $res;
 }
 
 public function update($table,$message_id,$sender,$receiver,$message,$isread,$date)
 {
  $res = mysql_query("UPDATE $table SET  sender='$sender', receiver='$receiver', message='$message', read='$isRead', date='$date' WHERE user_id=".$id);
  return $res;
 }
}

?>
message_id = "message_id" ;
$sender = "sender";
$receiver = "receiver";
$message = "message";
$isRead = "read";
$date = "date";