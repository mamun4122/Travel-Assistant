<?php

	if(isset($_POST["userid"])!=""&&$_POST["purchaseid"]!="")
	{
		$id=$_POST["userid"];
		$purchaseid=$_POST["purchaseid"];
		if($purchaseid=="")
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
		
		$query = " SELECT  * FROM `purchase` WHERE `purchaseid` LIKE '$purchaseid' ";
		//echo $date;
		$row=mysqli_query($con,$query);
		//$row=mysqli_fetch_array($row,MYSQLI_NUM);
		$rows=mysqli_fetch_row($row);
		if(!$rows||$rows[0]==""||$rows[1]!=$id)
		{
			//need modification
			$response["success"]=2;
			$response["message"]="NO available found with this id";
			$response["busname"]="No purchase found";
			$response["route"]="No purchase found";
			$response["seat"]="No purchase found";
			$response["date"]="No purchase found";
			$response["time"]="No purchase found";
			$response["status"]="3";
			die(json_encode($response));
			
		}
		//echo $row;
		$response["success"]=1;
		$response["message"]="Successful";
		$response["busname"]=$rows[2];
		$response["route"]=$rows[3];
		$response["seat"]=$rows[4];
		$response["date"]=$rows[5];
		$response["time"]=$rows[6];
		$response["status"]=$rows[7];
		die(json_encode($response));
		
		
		
		
		
	}
	else
	{
		/*
		
		?>
		<h1>booking</h1> 
		<form action="purchase.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="userid" placeholder="username" /> 
		    <br /><br /> 
		    <input type="submit" value="purchase" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php*/
		
		
		$response["success"]=0;
		$response["message"]="Enter all the fields";
		$response["busname"]="Enter purchase code";
		$response["route"]="Enter purchase code";
		$response["seat"]="Enter purchase code";
		$response["date"]="Enter purchase code";
		$response["time"]="Enter purchase code";
		die(json_encode($response));
	}


?>