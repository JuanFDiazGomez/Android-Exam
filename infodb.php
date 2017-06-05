<?php
	$array;
	$conn = new mysqli("localhost:3306","root","root","empresas");
	if($conn->connect_error){
		die("Connection failed: " . $conn->connect_error);
	}
	$sql = "SELECT * FROM empresas WHERE nombre='".$_POST['nombre']."'";
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		$array[0]=$result->fetch_assoc();
	}
	print_r(json_encode($array));
?>