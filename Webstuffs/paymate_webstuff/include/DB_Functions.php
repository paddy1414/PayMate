<?php



class DB_Functions
{
    
    private $conn;
    
    // constructor
    function __construct()
    {
        require_once 'DB_Connect.php';
        // connecting to database
        $db         = new Db_Connect();
        $this->conn = $db->connect();
    }
    
    // destructor
    function __destruct()
    {
        
    }
    
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($firstName, $lastName, $email, $password, $profileImageUrl, $addrId, $groupId)
    {
        $uuid               = uniqid('', true);
        $hash               = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt               = $hash["salt"]; // salt
        
        $stmt = $this->conn->prepare("INSERT INTO user( unique_id, firstName, lastName,email, password, profileImageUrl, addressId, groupId, salt) VALUES(?, ?, ?, ?, ?, ?,?,?,?)");
        $stmt->bind_param("sssssssss", $uuid, $firstName, $lastName, $email, $encrypted_password, $profileImageUrl, $addrId, $groupId, $salt);
        $result = $stmt->execute();
        $stmt->close();
        
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM user WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            
            return $user;
        } else {
            return false;
        }
    }
    
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password)
    {
        
        $stmt = $this->conn->prepare("SELECT * FROM user WHERE email = ?");
        
        $stmt->bind_param("s", $email);
        
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            
            // verifying user password
            $salt               = $user['salt'];
            $encrypted_password = $user['password'];
            $hash               = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
    
    
    /**
     * Storing new user
     * returns user details
     */
    public function add_a_bill($billName, $totalAmount, $paidBy, $billType, $groupId)
    {
        $uuid       = uniqid('', true);
        $todaysDate = date("Y-m-d h:i:s");
        $checkDate  = $todaysDate;
        $stmt       = $this->conn->prepare("INSERT INTO bills(billSpecialId, billName, totalAmount, paidBy, billType, dateCreated) VALUES(?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssss", $uuid, $billName, $totalAmount, $paidBy, $billType, $todaysDate);
        $result = $stmt->execute();
        $stmt->close();
        
        // check for successful store
        if ($result) {
            
            $stmt = $this->conn->prepare("SELECT * FROM bills WHERE billSpecialId = ?");
            $stmt->bind_param("s", $uuid);
            $stmt->execute();
            $bill = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            
            //getting the BillId
            $sql    = "SELECT * FROM bills WHERE billSpecialId = '$uuid'";
            $result = $this->conn->query($sql);
            
            if ($result->num_rows > 0) {
                
                $response["billInGrffoup"] = array();
                // output data of each row
                while ($row = $result->fetch_assoc()) {
                    $billId = $row["billId"];
                }
                
                return $this->add_users_bill($groupId, $paidBy, $totalAmount, $billId);
            } else {
                echo "0 results ";
            }
            //
            return $bill;
        } else {
            return false;
        }
    }
    
    /**
     * Get All the comments
     */
    
    public function add_users_bill($groupId, $oweTo, $amount, $billId)
    {
        //Getting the groupId to search for
        $sql = "select userId from user where groupId='$groupId'";
        
        $result = $this->conn->query($sql);
        if (!$result) {
            throw new Exception("Database Error [{$this->database->errno}] {$this->database->error}");
        }
        $num_rows = mysqli_num_rows($result);
        
        $amountOwed = $amount / $num_rows + 1;
        if ($result->num_rows > 0) {
            
            $response["add_users_bill"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                
                foreach ($row as $key => $field) {
                    
					$stmt = $this->conn->prepare("INSERT INTO userbill( billId, userId, amountOwed, moneyOwedTo,groupId) VALUES (?, ?, ?, ?, ?)");
					$stmt->bind_param("sssss", $billId, $field, $amountOwed, $oweTo, $groupId);
                    $resultIn = $stmt->execute();
                    $stmt->close();
                }
                
                
            }
            
            return $response;
        } else {
            echo "0 results";
        }
    }
    
    
    
    /**
     * Storing new user
     * returns user details
     */
	public function remove_ub($ubId, $userId, $billId, $paymentType,  $amount)
    {
        
        
        $stmt = $this->conn->prepare("DELETE FROM userbill where ubId =? and userId=?");
        $stmt->bind_param("ss", $ubId, $userId);
        $result = $stmt->execute();
        $stmt->close();
        /*
        // check for successful store
        if ($result) {
        $stmt = $this->conn->prepare("SELECT * FROM userbill WHERE ubId = ?");
        $stmt->bind_param("s", $ubId);
        $stmt->execute();
        $bill = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        
        //  return $bill;
        return $response["TRUE"];
        } else {
        return false;
        }
        */
		$response = $this->add_to_history($userId, $billId, $paymentType,  $amount);
        return $response;
        
        
    }
	//select b.billName, bh.amount, bh.paymentType, bh.datePaid from bills b, billhistory bh where  bh.userId = 101
    
    /**
     * Storing new user
     * returns user details
     */
    public function add_to_history($userId, $billId, $paymentType,  $amount)
    {
        //echo "add_to_history";
        $todaysDate = date("Y-m-d h:i:s");
		$stmt       = $this->conn->prepare("INSERT INTO billhistory( userId, billId, datePaid, paymentType, amount) VALUES (?, ?, ?, ?,? )");
		$stmt->bind_param("sssss", $userId, $billId, $todaysDate, $paymentType, $amount);
        $result = $stmt->execute();
        $stmt->close();
        
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM billhistory WHERE datePaid = ?");
            $stmt->bind_param("s", $todaysDate);
            $stmt->execute();
            $bill = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            
            return $bill;
        } else {
            return false;
        }
    }
	
	/**
     * Get All the comments
     */
    //select b.billName, bh.amount, bh.paymentType, bh.datePaid from bills b, billhistory bh where  bh.userId = 101
    public function get_bill_history($userId)
    {
        
		$sql = "select b.billName, bh.amount, bh.paymentType, bh.datePaid from bills b, billhistory bh where  bh.userId ='$userId'";
        
        $result = $this->conn->query($sql);
        
        if ($result->num_rows > 0) {
            
            $response["billhistory"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $billhistory          = array();
                $billhistory["billName"] = $row["billName"];
                $billhistory["amount"] = $row["amount"];
				$billhistory["paymentType"] = $row["paymentType"];
				$billhistory["datePaid"] = $row["datePaid"];
                
                // push single product into final response array
                array_push($response["billhistory"], $billhistory);
            }
            return $response;
        } else {
            echo "0 results";
        }
    }
    
    /**
     * Get All the comments
     */
    /* 
    public function viewBillHistory($userId)
    {
    
    $sql = "select * from user where billhistory='$userId'";
    
    $result = $this->conn->query($sql);
    
    if ($result->num_rows > 0) {
    
    $response["billhistory"] = array();
    
    // output data of each row
    while ($row = $result->fetch_assoc()) {
    $billhistory          = array();
    $billhistory["fName"] = $row["firstName"];
    $billhistory["userId"] = $row["userId"];
    $billhistory["fName"] = $row["firstName"];
    $billhistory["lName"] = $row["lastName"];
    
    // push single product into final response array
    array_push($response["billhistory"], $usersInGroup);
    }
    return $response;
    } else {
    echo "0 results";
    }
    }
    */
    
    /**
     * Storing new user
     * returns user details
     */
    public function getUserIOweTo($userId)
    {
        
        // $sql = "select u.firstName, u.lastName, b.billName, ub.amountOwed, ub.billId from user u, userbill ub, bills b where u.userId='$userId'  and ub.moneyOwedTo = b.paidBy";
		$sql    = "select ub.ubId, u.firstName, u.lastName, b.billName, ub.amountOwed, ub.billId from user u, userbill ub, bills b where uB.userId='$userId' AND u.userId = '$userId' and ub.billId=b.billId";
        $result = $this->conn->query($sql);
        
        if ($result->num_rows > 0) {
            
            $response["whoiowe"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $whoiowe               = array();
                $whoiowe["ubId"]       = $row["ubId"];
                $whoiowe["billId"]     = $row["billId"];
                $whoiowe["firstName"]  = $row["firstName"];
                $whoiowe["lastName"]   = $row["lastName"];
                $whoiowe["billName"]   = $row["billName"];
                $whoiowe["amountOwed"] = $row["amountOwed"];
                
                
                // push single product into final response array
                array_push($response["whoiowe"], $whoiowe);
            }
            return $response;
        } else {
            echo "0 results";
        }
    }
    /**
     * Storing new user
     * returns user details
     */
    public function getUsersWhoOweMe($userId, $groupID)
    {
        $sql = "SELECT  ub.ubId, u.firstName, u.lastName, b.billName, ub.amountOwed, ub.billId from user u , userbill ub, bills b where uB.moneyOwedTo='$userId' and u.groupId='$groupID' and b.billId=ub.billId and u.userId=ub.userId";
        //$sql = "select u.firstName, u.lastName, b.totalAmount from user u, userbill ub, bills b where u.userId='$userId'  and ub.moneyOwedTo = b.paidBy";
		//SELECT  ub.ubId, u.firstName, u.lastName, b.billName, ub.amountOwed, ub.billId from user u , userbill ub, bills b where uB.moneyOwedTo=104 and u.groupId='$userId' and b.billId=ub.billId and u.userId=ub.userId
        
        $result = $this->conn->query($sql);
        
        if ($result->num_rows > 0) {
            
            $response["WhoOwesMe"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $WhoOwesMe               = array();
                $WhoOwesMe["ubId"]       = $row["ubId"];
                $WhoOwesMe["billId"]     = $row["billId"];
                $WhoOwesMe["firstName"]  = $row["firstName"];
                $WhoOwesMe["lastName"]   = $row["lastName"];
                $WhoOwesMe["billName"]   = $row["billName"];
                $WhoOwesMe["amountOwed"] = $row["amountOwed"];
                
                // push single product into final response array
                array_push($response["WhoOwesMe"], $WhoOwesMe);
            }
            return $response;
        } else {
            echo "0 results";
        }
    }
    
    
    
    /**
     * Storing new user
     * returns user details
     */
    public function add_a_goal($goalName, $targetAmount, $currentAmount, $groupdId)
    {
        $stmt = $this->conn->prepare("INSERT INTO savinggoals( goalName, targetAmount, currentAmount, groupId) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("ssss", $goalName, $targetAmount, $currentAmount, $groupdId);
        $result = $stmt->execute();
        $stmt->close();
        
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM savinggoals WHERE groupId = ?");
            $stmt->bind_param("s", $groupdId);
            $stmt->execute();
            $bill = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            
            return $bill;
        } else {
            return false;
        }
    }
    /**
     * Get All the comments
     */
    
    public function get_others_inGroup($groupId)
    {
        
        $sql = "select firstName, lastName from user where groupId='$groupId'";
        
        $result = $this->conn->query($sql);
        
        if ($result->num_rows > 0) {
            
            $response["usersInGroup"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $usersInGroup          = array();
                $usersInGroup["fName"] = $row["firstName"];
                $usersInGroup["lName"] = $row["lastName"];
                
                // push single product into final response array
                array_push($response["usersInGroup"], $usersInGroup);
            }
            return $response;
        } else {
            echo "0 results";
        }
    }
	

    
    
    
    
    
    /**
     * Get All the comments
     */
    
    public function get_savings_goals($groupId)
    {
        
        $sql = "select * from savinggoals where groupId='$groupId'";
        
        $result = $this->conn->query($sql);
        
        if ($result->num_rows > 0) {
            
            $response["savinggoalsInGroup"] = array();
            
            // output data of each row
            while ($row = $result->fetch_assoc()) {
                $savinggoals                  = array();
                $savinggoals["goalId"]        = $row["goalId"];
                $savinggoals["goalName"]      = $row["goalName"];
                $savinggoals["targetAmount"]  = $row["targetAmount"];
                $savinggoals["currentAmount"] = $row["currentAmount"];
                $savinggoals["groupId"]       = $row["groupId"];
                
                // push single product into final response array
                array_push($response["savinggoalsInGroup"], $savinggoals);
            }
            return $response;
        } else {
            echo "0 results";
        }
    }
    
    
    
    
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email)
    {
        $stmt = $this->conn->prepare("SELECT email from user WHERE email = ?");
        
        $stmt->bind_param("s", $email);
        
        $stmt->execute();
        
        $stmt->store_result();
        
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
    
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password)
    {
        
        $salt      = sha1(rand());
        $salt      = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash      = array(
            "salt" => $salt,
            "encrypted" => $encrypted
        );
        return $hash;
    }
    
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password)
    {
        
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
        
        return $hash;
    }
    
    
    
}

?>
