<?php
	if(isset($_POST["seatupdate"])!="")
	{
		$seat=$_POST["seatupdate"];
		//echo $pass;
		
		$con=mysqli_connect("localhost","mamun","mamun","travel");
		if(mysqli_connect_errno())
		{
			$response["success"]=0;
			$response["message"]="Database connection error";
			die(json_encode($response));
			
		}
		
		$query = " $seat " ;
		//echo $query;
		$row=mysqli_query($con,$query);
		$login_ok=false;
		if(!$row)
		{
			$response["success"]=0;
			$response["message"]="Database connection error 2";
			die(json_encode($response));
			
		}
			$response["success"] = 1;
			$response["message"] = "success";
			die(json_encode($response));
		
		
		
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
	$response["message"]="failure";
	die(json_encode($response));
	
}
	
	




?>