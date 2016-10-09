<?php 
require 'include/DB_Functions.php';
$db = new DB_Functions();
$userx = $db->isUserExisted('David');
var_dump(method_exists($db, 'writeMessage'));
//($db->writeMessage('Alchemist','Ogre', 'Sample Message 1'));

?>