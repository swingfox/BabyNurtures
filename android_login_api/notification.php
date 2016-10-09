<?php
require 'vendor/autoload.php';
require 'config/config.php';
use Parse\ParseObject;
use Parse\ParseQuery;
use Parse\ParseACL;
use Parse\ParsePush;
use Parse\ParseUser;
use Parse\ParseInstallation;
use Parse\ParseException;
use Parse\ParseAnalytics;
use Parse\ParseFile;
use Parse\ParseCloud;
use Parse\ParseClient;

$config = new config();

$app_id = 'igRNX7jzc4CFPNNH72b5NKhX06ufgJ7ZoU39Ro3G';
$rest_key = 'xQXx4Vx1f1GUggrKdFYObxXotkA75sy5qA04ryLt';
$master_key = '3bySsNm2DqOu7QuOBegQvk4k6EiRED14F22aze9X';

$username = $_POST['username'];

ParseClient::initialize($app_id, $rest_key, $master_key);
if ($_POST['tag'] == "notificationActivities") {
	$message = $_POST['message'];
	$toParent = $_POST['toParent'];
	$data = array("alert" => $message);
	$query = ParseInstallation::query();
	$query->equalTo("email", $toParent);
	ParsePush::send(array(
	    "where" => $query,
	    "data" => $data
	));
} else {
	$result = $config->execute("SELECT * FROM users WHERE username = '".$_POST['username']."'");
	$res =  $config->fetch_array($result);
	$result_username = $res['username'];

	$result = $config->execute("SELECT * FROM babysitter WHERE created_by = '".$_POST['username']."'");
	$res1 =  $config->fetch_array($result);

	if ($result_username == $res1['created_by']) {
		require 'NotificationManager.php';
		$notifMgr = new NotficationManager();
		$notifMgr->addNotification($_POST['task'],$result_username,$res1['name']);
		/*
		$data = array("alert" => "Task: ".$_POST['task']." Start: ".$_POST['start']." End: ".$_POST['end']);
		$query = ParseInstallation::query();
		$query->equalTo("email", $res1['name']);
		ParsePush::send(array(
		    "where" => $query,
		    "data" => $data
		));*/
		echo "nice";
	} else {
		echo json_encode("Dili equal ang username");
	}
}
?>
