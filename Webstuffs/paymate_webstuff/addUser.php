<?php

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    //Getting values
    $firstName       = $_POST['firstName'];
    $lastName        = $_POST['lastName'];
    $email           = $_POST['email'];
    $password        = $_POST['password'];
    $profileImageUrl = $_POST['profileImageUrl'];
    
    $address1 = $_POST['addressLine1'];
    $address2 = $_POST['addressLine2'];
    $town     = $_POST['town'];
    $county   = $_POST['county'];
    //Importing our db connection script
    require_once('dbConnect.php');
    
    
    
    $sql1   = "select addressId from address where addressLine1='$address1' and addressLine2 ='$address2' and town='$town' and county='$county'";
    //Executing query to database
    //$sql = "SELECT id FROM Users WHERE username='$name' limit 1";
    $result = mysqli_query($con, $sql1);
 
    $value  = mysqli_fetch_array($result);
    $addrId = $value['addressId'];
    
    if ($addrId > 0) {
        $addrId = $value['addressId'];
        
        $sql1    = "select groupId from groups where addressid='$addrId';";
        //Executing query to database
        //$sql = "SELECT id FROM Users WHERE username='$name' limit 1";
        $result  = mysqli_query($con, $sql1);
        $value   = mysqli_fetch_array($result);
        $groupId = $value['groupId'];
        
    } else {
        
        //Inserting address into the Db
		$sql = "INSERT INTO address (addressLine1,addressLine2,town,county) VALUES ('$address1', '$address2', '$town','$county')";
        //Creating an sql query
        //Executing query to database
        if (mysqli_query($con, $sql)) {
      //      echo 'adress Added Successfully';
        } else {
			echo 'Could Not Add adress part';
        }
        
        $sql1   = "select addressId from address where addressLine1='$address1' and addressLine2 ='$address2' and town='$town' and county='$county'";
        //Executing query to database
        //$sql = "SELECT id FROM Users WHERE username='$name' limit 1";
        $result = mysqli_query($con, $sql1);

        $value  = mysqli_fetch_array($result);
        $addrId = $value['addressId'];
        
        //Inserting into the groups pages
        $groupInsert = "INSERT INTO groups (groupName, addressId) VALUES ('$address1', '$addrId')";
        //Creating an sql query
        //$result=mysqli_query($con, $sql1);
        //Executing query to database
        if (mysqli_query($con, $groupInsert)) {
       //     echo 'group Added Successfully';
        } else {
			echo 'Could Not Add group part';
        }
        
      
        
        $sql1    = "select groupId from groups where addressid='$addrId';";
        //Executing query to database
        //$sql = "SELECT id FROM Users WHERE username='$name' limit 1";
        $result  = mysqli_query($con, $sql1);
        $value   = mysqli_fetch_array($result);
        $groupId = $value['groupId'];
        
        
        
    }
    
    /*
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //$value = mysqli_fetch_object($result);
    //$nombre = $value['addressId'];
    //$nombre =$value->addressId
    //Creating an sql query
	$sql = "INSERT INTO user (firstName,lastName,email,password,profileImageUrl,addressId, groupId) VALUES ('$firstName','$lastName','$email','$password','$profileImageUrl', 
	'$addrId', '$groupId')";
    //Creating an sql query
    //$result=mysqli_query($con, $sql1);
    //Executing query to database
    if (mysqli_query($con, $sql)) {
        echo 'User Added Successfully';
    } else {
        echo 'Could Not Add User';
    }
	*/
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
		$user = $db->storeUser($firstName,$lastName,$email, $password, $profileImageUrl, 
	$addrId, $groupId);
        if ($user) {
            // user stored successfully
              $response["error"] = FALSE;
   //     $response["uid"] = $user["unique_id"];
        $response["user"]["firstName"] = $user["firstName"];
        $response["user"]["lastName"] = $user["lastName"];
		$response["user"]["password"] = $user["password"];
		$response["user"]["addressId"] = $user["addressId"];
		$response["user"]["groupId"] = $user["groupId"];
        echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
    
    // 
    
    //Closing the database 
    mysqli_close($con);
}

?>