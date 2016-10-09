<?php 
	function db_connection($servername,$dbname,$username,$password){
		$conn = FALSE;
		try { $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
		} catch (PDOException $e) { $conn = FALSE; }
		return $conn;
	}
?> 