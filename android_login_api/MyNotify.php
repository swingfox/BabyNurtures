<?PHP
require 'NotificationManager.php';
$data = new stdClass();
$user = $_GET["user"];
$notifMgr = new NotficationManager();
$data = $notifMgr->getNotificationOfUser($user);
//$notifMgr->updateSeenNotificationOfUser($user);
header('Content-Type: application/json');
echo json_encode($data);


?>