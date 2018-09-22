<?php

	if(isset($_POST["purchaseid"])!=""&&isset($_POST["bkash"])!=""&&isset($_POST["status"])!=""&&isset($_POST["userid"])!="")
	{
		$id=$_POST["userid"];
		$purchaseid=$_POST["purchaseid"];
		$bkash=$_POST["bkash"];
		$status=$_POST["status"];
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
		}
		$query=" SELECT * FROM `confirmation` WHERE `purchaseid` = $purchaseid ";
		$row=mysqli_query($con,$query);
		$row=mysqli_fetch_row($row);
		if($row[0]=="")
		{
			$query=" INSERT INTO `travel`.`confirmation` (`purchaseid`, `userid`, `code`, `status`) VALUES ('$purchaseid', '$id', '$bkash', '$status') ";
			$row=mysqli_query($con,$query);
			if(!$row)
			{
				$response["success"]=0;
				$response["message"]="Database error";
				//$response["date"]=""
				die(json_encode($response));
				
			}
				
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