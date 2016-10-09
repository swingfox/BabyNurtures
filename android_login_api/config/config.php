<?php
/**
* Web hosting configuration
* localhost: mysql13.000webhost.com
* username: a3635431_radmin
* database: a3635431_grab
*/
class config {
	
	public $set_tut_array;

	public function __construct() {}
	public function connect() {
		return mysqli_connect('localhost', 'root', '', 'android_api');
		//return mysqli_connect('localhost', 'root', 'admin@pass', 'android_api');
	}
	// NAME SHOULD BE QUERY not "EXECUTE"
	public function execute($query) { 
	// 	$this->xlog($query);
		return mysqli_query($this->connect(), $query);
	}
	
	public function num_rows($result) {
		return mysqli_num_rows($result);
	}
	
	public function fetch_array($result) {
		return mysqli_fetch_array($result);
	}
	
	public function fetch_assoc($result) {
		return mysqli_fetch_assoc($result);
	}

	public function image_path() {
		return "http://192.168.1.100/grab-a-tutor/pictures/";
	}
	
	public function close_connection() {
		mysqli_close($this->connect());
	}

	public function xlog($msg) {
		$file = fopen("C:/app-logs/mylogs.log","a");
		fwrite($file, $msg."\n");
		fclose($file);	
	}

	public function set_arr($array) {
		$this->set_tut_array = $array;
	}

	public function get_arr() {
		return $this->set_tut_array;
	}

	public function json($msg) {
		$file = fopen("C:/app-logs/myjson.json","a");
		fwrite($file,$msg."\n");
		fclose($file);
	}
}	
?>