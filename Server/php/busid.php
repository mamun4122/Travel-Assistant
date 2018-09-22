<?php

	if(isset($_POST["busname"])!=""&&isset($_POST["route"])!=""&&isset($_POST["datename"])!=""&&isset($_POST["timename"])!="")
	{
		$bus=$_POST["busname"];
		$route=$_POST["route"];
		$date=$_POST["datename"];
		$time=$_POST["timename"];
		if($bus==""||$route==""||$date==""||$time=="")
		{
			$response["success"]=0;
			$response["message"]="Enter valid information";
			die(json_encode($response));
		}
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
		}
		
		$query = " SELECT * FROM `busdate` WHERE `name` LIKE '$bus' AND `date` LIKE '$date' AND `route` LIKE '$route' AND `time` LIKE '$time' ";
		//echo $date;
		$row=mysqli_query($con,$query);
		if(!$row)
		{
			$response["success"]=1;
			$response["message"]="NO available id found";
			//$response["date"]=""
			die(json_encode($response));
			
		}
		//$row=mysqli_fetch_array($row,MYSQLI_NUM);
		$row=mysqli_fetch_row($row);
		if(!$row[0]!="")
		{
			$response["success"]=0;
			$response["message"]="NO available id found";
			die(json_encode($response));
			
		}
		//echo $row;
		$response["success"]=1;
		$response["message"]="Successful";
		$response["busid"]   = $row[0];
		$vuavar=$row[0];
		//echo $response["busid"];
		
		$vua="SELECT * FROM `busseat` WHERE `id` LIKE $vuavar ";
		$userdetails=mysqli_query($con,$vua);
		$userdetails=mysqli_fetch_row($userdetails);
		if($userdetails[0]=="")
		{
			$vua="INSERT INTO `busseat` (`id`) VALUES ('$vuavar')";
			$userdetails=mysqli_query($con,$vua);
		
			
		}
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
		$response["message"]="Enter all the fields";
		die(json_encode($response));
	}


?>