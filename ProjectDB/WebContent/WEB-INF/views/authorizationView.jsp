<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Авторизация</title>
<style>
	body{
		background-color: #ddd;
	}
	
	header{
		color: white;
		background-color: grey;
		padding: 5px;
		width: 1512px;
		margin-left: 0px;
	}
	
	h1{
		background: grey;
		color: white;
		display: block;
		padding: 10px;
		width: 420px;
		margin-bottom: 0px;
	}
	#formDiv{
		padding: 20px;
		width: 396px;
		border: 2px solid grey;
	}
	#formDiv table td{
		padding-bottom: 20px;
	}
	#formDiv table{
		margin-top: 5%;
		margin-left: 20%;
	}
	input[type=submit]{
	display: inline-block; 
    padding: 4px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	#wrapper{
		position: absolute;
		left: 500px;
		top: 200px;
		width: 440px;
	}
</style>
</head>
<body>
	<div id="wrapper">
	<h1>Авторизация</h1>
	<div id="formDiv">
	<form method="POST" action="${pageContext.request.contextPath}/login">
		<table>
			<tr>
				<td>Логин:</td>
				<td><input type="text" name="login"/></td>
			</tr>
			<tr>
				<td>Пароль:</td>
				<td><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit" value="Войти"/></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
</body>
</html>