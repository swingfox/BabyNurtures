<?php
include 'config/config.php';

//if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $tag = $_POST['tag'];
    $config = new config();
    $input = $_POST['created_by'];
    //echo json_encode($input);
    $response = array();
    //$tutor_array = array();
    
     $select = $config->execute("SELECT * FROM baby WHERE created_by like '%$input%'");

    if ($config->num_rows($select) > 0) {
        while ($row = $config->fetch_assoc($select)) {
            $tutor_array[] = $row;
        }
        $config->set_tut_array = $tutor_array;
        //$config->set_arr($tutor_array);
        //echo json_encode($tutor_array);

        echo json_encode($config->get_arr());
    } 

    else {


        $tutor_array[] = $select;
        $config->set_tut_array = $tutor_array;

       // $config->set_tut_array = 'No results found';
        echo json_encode($config->get_arr());
    }

    //echo json_encode($tutor_array);
// } else {
//  $response["error: "] = TRUE;
//     $response["error_msg"] = "Required parameter 'tag' is missing!";
//     echo json_encode($response);
// }

//echo json_encode($config->get_arr());

?>
