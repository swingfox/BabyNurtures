<?php
require 'NotificationManager.php';
$user = $_GET['username'];
$notifMgr = new NotficationManager();
$notifMgr->updateSeenNotificationOfUser($user);
?>