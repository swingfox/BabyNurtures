<?php
require 'NotificationManager.php';
$data = new stdClass();
$user = $_GET["user"];
$notifMgr = new NotficationManager();
$data = $notifMgr->allNotification($user);
header('Content-Type: application/json');
echo json_encode($data);



?>