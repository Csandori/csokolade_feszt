<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="//fonts.googleapis.com/css?family=Oswald" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Bejelentkezés</title>
<style>
input[type=text], input[type=password] {
	text-align: center;
	width: 25%;
	padding: 12px 20px;
	margin: 0 auto;
	border: none;
	background-color: #92b8f4;
	color: white;
	border-radius: 6px;
	opacity: 0.7;
	margin: 0 auto;
}

input::-webkit-input-placeholder {
	color: #37455b; /*Change the placeholder color*/
	opacity: 1; /*Change the opacity between 0 and 1*/
}

input[type=button] {
	width: 20%;
	background-color: white;
	border: none;
	border-radius: 6px;
	color: #92b8f4;
	padding: 15px 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 2px 2px;
	cursor: pointer;
	opacity: 0.9;
	background-color: white;
}

h1 {
	letter-spacing: 4px;
	font-family: Oswald;
	font-style: normal;
	font-variant: normal;
	color: #92b8f4;
}

h2 {
	letter-spacing: 4px;
	font-family: Oswald;
	font-style: normal;
	font-variant: normal;
	color: #92b8f4;
}

h5 {
	color: white;
	position: fixed;
}

.jobblent {
	bottom: 10px;
	right: 10px;
	margin: 0px;
	position: fixed;
}

.ballent {
	bottom: 10px;
	left: 10px;
	margin: 0px;
	position: fixed;
}

.login {
	text-align: center;
	align: center;
	margin-left: 5%;
	margin-top: 20%;
	position: flex;
	text-align: center;
	height: 60%;
	width: 60%;
	position: flex;
}

body {
}
</style>
</head>
<body>
	<div class="login">
		<h1>BEJELENTKEZÉS</h1>
		<h1></h1>
		<form>
			<input type="text" name="name" id="name" placeholder="Felhasználónév"
				autofocus /><br> <br> <input type="password"
				id="password" name="password" placeholder="Jelszó" /><br> <br>
			<input type="button" onclick="login()" value="Bejelentkezés" />
		</form>
		<h2 id="answer"></h2>
		<i id="spinner" class="fa fa-circle-o-notch fa-spin"
			style="font-size: 24px; color: #92b8f4; display: none"></i>
	</div>
	<h5 class="jobblent">Version: 2018.09.17. 22:44</h5>
	<h5 class="ballent">Csokoládé-Fesztivál</h5>
	<script>
		document.getElementById("name").addEventListener("keyup",
				function(event) {
					event.preventDefault();
					if (event.keyCode === 13) {
						login();
					}
				});

		document.getElementById("password").addEventListener("keyup",
				function(event) {
					event.preventDefault();
					if (event.keyCode === 13) {
						login();
					}
				});

		function login() {
			document.getElementById("answer").style.color = "#92b8f4";
			var str = $("form").serialize();
			$("#results").text(str);
			document.getElementById("answer").innerHTML = "Bejelentkezés...";
			document.getElementById("spinner").style.display = "block";
			$.post("/login", str, function(data, status) {
				document.getElementById("spinner").style.display = "none";
				document.getElementById("answer").innerHTML = data;
				if (data.includes("Rossz")) {
					document.getElementById("answer").style.color = "red";
				} else {
					setTimeout(function() {
						window.location.replace("/weboldal/index.jsp?title=Naturadent");
					}, 1000);
				}

			});
		}
	</script>
</body>
</html>