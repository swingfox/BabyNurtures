<?php
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 $file_path = "picture/";
  $babyId = $_POST['babyId'];
  $directory = $_POST['directorypicture'];
  

  $path = $file_path . basename( $_FILES['uploaded_file']['name']);

   $directory_path = $directory.$path;
   if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $path)) {
     $db->UpdatePicture($babyId,$directory_path);
   } else{
       echo "fail";
   }
   
   
   
?>