<!DOCTYPE html>
<html>
<body>

<?php
echo "Today is " . date("Y/m/d") . "<br>";
echo "Today is " . date("Y.m.d") . "<br>";
echo "Today is " . date("d-m-Y") . "<br>";
echo "Today is " . date("Y-m-d h:i:s");
echo "<br>";
$date=new DateTime(); //this returns the current date time
$result = $date->format('Y-m-d-H-i-s');
echo $result;
echo "<br>";
$krr = explode('-',$result);
echo "<br>";
$result = implode("",$krr);
echo $result;
?>

</body>
</html>