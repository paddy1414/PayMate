<?php

/**
 * A class file to connect to database
 */
class DB_CONNECT {

  private $con;
    // constructor
    function __construct() {
        // connecting to database
			 $this->connect();
    }
	

   // destructor
    function __destruct() {
        // closing db connection
    
    }

    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/config.php';

        // Connecting to mysql database
		// Connecting to mysql database
        $this->conn = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
		   //    $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());

        // Selecing database
     //  $this->conn = new mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

        // returing connection cursor
        return $this->conn;
    }
	
	function connectforget() {
        // import database connection variables
        require_once __DIR__ . '/config.php';
		
        // Connecting to mysql database
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
		
        // Selecing database
        $db = mysqli_select_db($con, DB_DATABASE) or die(mysqli_error());

        // returing connection cursor
        return $con;
    }

    /**
     * Function to close db connection
     */
	   /**
     * Function to close db connection
     */
    
	
 
	

}

?>