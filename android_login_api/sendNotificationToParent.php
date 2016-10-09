<?PHP
	$sql = "SELECT created_by FROM babysitter WHERE name = '".$_GET['username']."'";
	require 'NotificationManager.php';
	$config = new config();
	$result = $config->execute($sql);
	$notifMgr = new NotficationManager();
	$count = $config->num_rows($result);

	if($count>0){
		//$row = mysqli_fetch_assoc($result);
		while($row = mysqli_fetch_assoc($result)) {
			echo $row['created_by'];
            $notifMgr->addNotification($_GET['message'],$_GET['username'],$row['created_by']);
		}
	}   
	else{
			$notifMgr->addNotification("wala ni sud","ian","Tester");
	}
?>