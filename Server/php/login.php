<?php
	if(isset($_POST["username"])!=""&&isset($_POST["password"])!="")
	{
		$user=$_POST["username"];
		$pass=$_POST["password"];
		//echo $pass;
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
			
		}
		
		$query = " SELECT * FROM `user` WHERE `username` LIKE '$user' ";
		
		$row=mysqli_query($con,$query);
		$login_ok=false;
		if(!$row)
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
			
		}
		$row=mysqli_fetch_row($row);
		if($pass==$row[2]&&$pass!="")
		{
			$login_ok=true;
		}
		if($login_ok)
		{
			$response["success"]=1;
			$response["message"]="login successful";
			$response["id"]=$row[0];
			$response["phone"]=$row[3];
			die(json_encode($response));
			
		}else
		{
			$response["success"] = 0;
			$response["message"] = "Invalid Credentials!";
			die(json_encode($response));
			
		}
		
		
		
	}
	else
	{
		/*?>
		<h1>Login</h1> 
		<form action="login.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form> 
		<a href="register.php">Register</a>
	<?php*/
	
	$response["success"]=0;
	$response["message"]="Enter Both username and password";
	die(json_encode($response));
	
}
	
	




?>