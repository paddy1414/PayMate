<?php


require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);


if (isset($_POST['userId'])) {
	
	   // receiving the post params
    $groupID = $_POST['userId'];

    $response = $db->getUserIOweTo($userId);

    if ($response != false) {
		  $response["error"] = FALSE;
   // success
    $response["success"] = 1;

    // echoing JSON response
 echo json_encode($response);
 
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "getting group credentials are wrong. Please try again!";
        echo json_encode($response);
    }
}  else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters barIgroupIDdis missing!";
    echo json_encode($response);
}
	
	

?>

