<?php
	if(isset($_POST["username"])!=""&&isset($_POST["password"])!=""&&isset($_POST["phonenumber"])!="")
	{
		$user=$_POST["username"];
		$pass=$_POST["password"];
		$phone=$_POST["phonenumber"];
		//echo $pass;
		if($user==""||$pass==""||$phone=="")
		{
			$response["success"]=0;
			$response["message"]="Enter Both username,password and phonenumber";
			die(json_encode($response));	
		}
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
			
		}
		
		$query = " SELECT id FROM `user` WHERE `username` LIKE '$user' ";
		
		$row=mysqli_query($con,$query);
		$row=mysqli_fetch_row($row);
		if($row[0]!="")
		{
			$response["success"]=0;
			$response["message"]="Username already exists";
			die(json_encode($response));
			
		}
		mysqli_query($con,"INSERT INTO `user` (`username`, `password`, `phone`) VALUES ( '$user', '$pass', '$phone')");
		$query = " SELECT * FROM `user` WHERE `username` LIKE '$user' ";
		$row=mysqli_query($con,$query);
		$row=mysqli_fetch_row($row);
		$response["success"]=1;
		$response["message"]="Registration successful";
		$response["id"]=$row[0];
		$response["phone"]=$row[3];
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
	$response["message"]="Enter Both username,password and phonenumber";
	die(json_encode($response));
	
}
	
	




?>