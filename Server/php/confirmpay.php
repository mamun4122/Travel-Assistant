<?php
	if(isset($_POST["userid"])!=""&&isset($_POST["bkash"])!=""&&isset($_POST["purchaseid"])!="")
	{
		$id=$_POST["userid"];
		$bkash=$_POST["bkash"];
		$purchaseid=$_POST["purchaseid"];
		//echo $pass;
		if($id==""||$bkash==""||$purchaseid=="")
		{
			$response["success"]=0;
			$response["message"]="Invalid Credintetials";
			die(json_encode($response));	
		}
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
			
		}
		
		$query = " SELECT userid FROM `purchase` WHERE `purchaseid` LIKE '$purchaseid' ";
		
		$row=mysqli_query($con,$query);
		$row=mysqli_fetch_row($row);
		if($row[0]!=$id)
		{
			$response["success"]=0;
			$response["message"]="No purchaseid exist with this id";
			die(json_encode($response));
			
		}
		mysqli_query($con,"UPDATE `travel`.`purchase` SET `status` = '2' WHERE `purchase`.`purchaseid` = $purchaseid");
		
		$response["success"]=1;
		$response["message"]="Payment successful";
		die(json_encode($response));
		
		
		
		
	}
	else
	{
		/*?>
		<h1>Registration</h1> 
		<form action="register.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    <br /><br /> 
			Password:<br /> 
		    <input type="password" name="phonenumber" placeholder="password" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Registration" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php*/
	
	$response["success"]=0;
	$response["message"]="Invalid Credintetials";
	die(json_encode($response));
	
}
	
	




?>