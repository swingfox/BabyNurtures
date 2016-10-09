<?php
	require 'NotificationManager.php';
	$data = new stdClass();
	$uid = $_POST["uid"];
	$taskSent = $_POST["task"];
	$startTime = $_POST["startTime"];
	$endTime = $_POST["endTime"];
	$notifMgr = new NotficationManager();
	$config = new config();
	$sql = "SELECT * FROM todolist WHERE uid=$uid";	
	$result = $config->execute($sql);
	if($config->num_rows($result)>0){
		$row = mysqli_fetch_assoc($result);
		$task = $row['task'];
		$createdBy = $row['created_by'];
		$createdFor = $row['created_for'];

		$sql = "SELECT name FROM babysitter WHERE created_by = '$createdBy'";
		$result = $config->execute($sql);
		if($config->num_rows($result)>0){
			$row2 = mysqli_fetch_assoc($result);
			$babysitter = $row2['name'];
			$sql = "UPDATE todolist SET task = '$taskSent', startTime='$startTime',endTime='$endTime' WHERE uid=$uid";
			$result = $config->execute($sql);
			if($result){
				echo 'true';
				$notifMgr->addNotification("Updated ".$task." to ".$taskSent,$createdBy,$babysitter);
			}
			else
				echo 'false';	
		}
		else
			echo 'false';
	}
	else
		echo 'false';	
	
?>