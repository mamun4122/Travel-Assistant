<?php

	if(isset($_POST["busname"])!=""&&isset($_POST["route"])!=""&&isset($_POST["datename"])!="")
	{
		$bus=$_POST["busname"];
		$route=$_POST["route"];
		$date=$_POST["datename"];
		if($bus==""||$route==""||$date=="")
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
		
		$query = " SELECT  time FROM `busdate` WHERE `name` LIKE '$bus' AND `date` LIKE '$date' AND `route` LIKE '$route' ";
		//echo $date;
		$row=mysqli_query($con,$query);
		if(!$row)
		{
			$response["success"]=2;
			$response["message"]="NO available date found";
			$response["posts"]=array();
			$post=array();
			$post["time"]="Not available right now";
			array_push($response["posts"],$post);
			//$response["date"]=""
			die(json_encode($response));
			
		}
		//$row=mysqli_fetch_array($row,MYSQLI_NUM);
		$row=mysqli_fetch_all($row);
		if(!$row)
		{
			$response["success"]=2;
			$response["message"]="NO available date found";
			$response["posts"]=array();
			$post=array();
			$post["time"]="Not available right now";
			array_push($response["posts"],$post);
			//$response["date"]=""
			die(json_encode($response));
			
		}
		//echo $row;
		$response["success"]=1;
		$response["message"]="Successful";
		$response["posts"]   = array();
		foreach ($row as $rows)
		{
			$post             = array();
			$post["time"]  = $rows[0];
        //update our repsonse JSON data
			array_push($response["posts"], $post);
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