<?php 
 
 //Getting the requested id
 $id = $_GET['userId'];
 
 //Importing database
 require_once('dbConnect.php');
 
 //Creating sql query with where clause to get an specific employee
 $sql = "SELECT * FROM user WHERE userId=$id";
 
 //getting result 
 $r = mysqli_query($con,$sql);
 
 //pushing result to an array 
 $result = array();
 $row = mysqli_fetch_array($r);
 array_push($result,array(
 "userId"=>$row['id'],
 "firstName"=>$row['name'],
 "lastName"=>$row['lastName'],
 "email"=>$row['email'],
 "password"=>$row['password'],
 "profileImageUrl"=>$row['profileImageUrl']
 ));
 
 //displaying in json format 
 echo json_encode(array('result'=>$result));
 
 mysqli_close($con);