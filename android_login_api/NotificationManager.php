<?php
require 'config/config.php';
	class NotficationManager {
		public function __construct() {
			
		}

		public function getNotificationOfUser($user_info){
			$data = array();
			$sql = "SELECT * FROM notifications WHERE receiver = '".$user_info."' AND seen = false";
			$config = new config();
			$result = $config->execute($sql);
			if($config->num_rows($result)>0){
				while($row = mysqli_fetch_assoc($result)) {
					    $str = new stdClass();
					    $str->message = $row['message'];
					    $str->sender = $row['sender'];
					    $str->receiver = $row['receiver'];
					    array_push($data, $str);
				}
			}
			return $data;
		}

		public function allNotification($user_info){
			$data = array();
			$sql = "SELECT * FROM notifications WHERE receiver = '".$user_info."' OR sender= '".$user_info."' ORDER BY date DESC ";
			$config = new config();
			$result = $config->execute($sql);
			
			if($config->num_rows($result)>0){
				while($row = mysqli_fetch_assoc($result)) {
					    $str = new stdClass();
					    array_push($data, $row);
					    /*$str->message = $row['message'];
					    $str->sender = $row['sender'];
					    $str->receiver = $row['receiver'];
					    array_push($data, $str);*/
				}
			}
			return $data;
		}

		public function addNotification($message,$sender,$receiver){
			$sql = "INSERT INTO notifications (message,sender,receiver) VALUES ('".$message."','".$sender ."','".$receiver."');";
			$config = new config();
			$result = $config->execute($sql);

		}

		public function updateSeenNotificationOfUser($user_info){
			$sql = "UPDATE notifications SET seen = true WHERE receiver = '".$user_info."' ";
			$config = new config();
			$result = $config->execute($sql);
		}

	}
?>