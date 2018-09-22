<?php

	if(isset($_POST["busid"])!="")
	{
		$id=$_POST["busid"];
		if($id=="")
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
		
		$query = " SELECT  * FROM `busseat` WHERE `id` LIKE '$id' ";
		
		$row=mysqli_query($con,$query);
		$row=mysqli_fetch_row($row);
		if(!$row)
		{
			$response["success"]=2;
			$response["message"]="NO available date found";
			$response["posts"]=array();
			$post=array();
			$post["date"]="Not available seat now";
			array_push($response["posts"],$post);
			//$response["date"]=""
			die(json_encode($response));
			
		}
		//echo $row;
		$indx=1;
		$response["success"]=1;
		$response["message"]="Successful";
		//$response["posts"]   = array();
		for($indx=1;$indx<=20;$indx++)
		{
			$response[$indx]=$row[$indx];
		}
	die(json_encode($response));
		
		
		
		
		
	}
	else
	{
		/*
		?>
		<h1>booking</h1> 
		<form action="busseat.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="busid" placeholder="username" /> 
		    <br /><br /> 
		    <input type="submit" value="busseat" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php*/
		
		
		$response["success"]=0;
		$response["message"]="Enter all the fields";
		die(json_encode($response));
	}


?>