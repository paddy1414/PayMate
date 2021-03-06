<?php


require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);


if (isset($_POST['billName'])) {
	
	   // receiving the post params
    $billName = $_POST['billName'];
	$totalAmount = $_POST['totalAmount'];
	$paidBy = $_POST['paidBy'];
	$billType = $_POST['billType'];
	$groupId = $_POST['groupId'];
	
	

	$response = $db->add_a_bill($billName,$totalAmount,$paidBy,$billType,$groupId);

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

