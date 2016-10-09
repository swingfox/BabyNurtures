<?php
	$todoid = $_POST['todoId'];
	$task = $_POST['task'];
	$sender = $_POST['sender'];
	$receiver = $_POST['receiver'];
	require 'NotificationManager.php';
    $sql = "UPDATE todolist SET finished=1 WHERE uid=".$todoid.";";
	$config = new config();
	$result = $config->execute($sql);	
	$notifMgr = new NotficationManager();
	if($result){
		$message = "Finished ".$task;
		$notifMgr->addNotification($message,$sender,$receiver);        
		echo $message;

	}
?>