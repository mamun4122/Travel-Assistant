<?php

	if(isset($_POST["busname"])!=""&&isset($_POST["route"])!=""&&isset($_POST["date"])!=""&&isset($_POST["time"])!=""&&isset($_POST["userid"])!="")
	{
		$bus=$_POST["busname"];
		$route=$_POST["route"];
		$date=$_POST["date"];
		$time=$_POST["time"];
		$id=$_POST["userid"];
		$seat=$_POST["seat"];
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
		}
		$query=" INSERT INTO `travel`.`purchase` (`userid`, `busname`, `route`, `seat`, `date`, `time`) VALUES ( '$id', '$bus', '$route', '$seat', '$date', '$time') ";
		$row=mysqli_query($con,$query);
		if(!$row)
		{
			$response["success"]=0;
			$response["message"]="Database error";
			//$response["date"]=""
			die(json_encode($response));
			
		}
		$query = " SELECT purchaseid FROM `purchase` WHERE `userid` LIKE '$id' AND `date` LIKE '$date' AND `route` LIKE '$route' AND `time` LIKE '$time' AND `busname` LIKE '$bus' AND `seat` LIKE '$seat' ";
		$row=mysqli_query($con,$query);
		//$row=mysqli_fetch_array($row,MYSQLI_NUM);
		$row=mysqli_fetch_row($row);
		if(!$row[0]!="")
		{
			$response["success"]=0;
			$response["message"]="NO id found";
			die(json_encode($response));
			
		}
		//echo $row;
		$response["success"]=1;
		$response["message"]="Successful";
		$response["purchaseid"]   = $row[0];
		die(json_encode($response));
		
		
		
		
		
	}
	else
	{
		/*
		
		?>
		<h1>booking</h1> 
		<form action="booking.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="busname" placeholder="username" /> 
		    <br /><br /> 
		    <input type="submit" value="booking" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php*/
		
		
		$response["success"]=0;
		$response["message"]="Sorry Error ocuured";
		die(json_encode($response));
	}


?>