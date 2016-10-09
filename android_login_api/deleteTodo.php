<?php
	require 'NotificationManager.php';
	$data = new stdClass();
	$todoid = $_GET["todoId"];
	$notifMgr = new NotficationManager();
	$sql = "SELECT * FROM todolist WHERE uid=$todoid";	
	$config = new config();
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
			$sql = "DELETE FROM todolist WHERE uid=$todoid;";
			$config->execute($sql);
			$notifMgr->addNotification("Deleted ".$task,$createdBy,$babysitter);
			echo "true";
		}
		else
			echo "false";
	}
	else
		echo "false";

?>