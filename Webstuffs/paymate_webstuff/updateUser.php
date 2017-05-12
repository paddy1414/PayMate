<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //Getting values 
    $id              = $_POST['userId'];
    $firstName       = $_POST['firstName'];
    $lastName        = $_POST['lastName'];
    $email           = $_POST['email'];
    $password        = $_POST['password'];
    $profileImageURl = $_POST['profileImageURl'];
    
    //importing database connection script 
    require_once('dbConnect.php');
    
    //Creating sql query 
    $sql = "UPDATE user SET firstName = '$firstName', lastName = '$lastName', email = '$email',password = '$password',profileImageURL = '$profileImageURL'   WHERE userId = $id;";
    
    //Updating database table 
    if (mysqli_query($con, $sql)) {
        echo 'User Updated Successfully';
    } else {
        echo 'Could Not Update user Try Again';
    }
    
    //closing connection 
    mysqli_close($con);
}