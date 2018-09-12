<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Пользователи</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forUserCRUD.js"></script>
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
	
	h1, h3{
		display:inline;
		margin-right: 10px;
		
	}
	
	#rectangle{
		position: absolute;
		z-index: -1;
		background-color: plum;
		width: 1100px;
		height: 66px;
	}
	
	#UserListDiv{
		position: absolute;
		top: 100px;
		left: 20px;
		right: 0px;
		height:600px;
		overflow-y: auto;
	}
	
	input[type=submit], a{
	display: inline-block; 
    padding: 4px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	input[type=submit]{
		margin-top: 10px;
		margin-bottom: 10px;
	}
	table{
		border-collapse: collapse;
		spacing: 0px;
	}
	table td, table th{
		border: 1px solid grey;
		padding: 2px;
	}
	form table td{
		border: transparent;
	}
	
	#homeHref{
		position: absolute;
		top: 22px;
		left: 1080px;
	}
	
</style>
</head>
<body>
	<div id="rectangle"></div>
	<a href="${pageContext.servletContext.contextPath}/home" id="homeHref">
			<img  src="img/home_icon.png" width="20px" height="20px"/>
	</a>
	<header>
		<h1>Все пользователи</h1>
	</header>
	<div id="UserListDiv">
	<form method="POST" action="${pageContext.servletContext.contextPath}/userList">
		<table>
		<tbody>
			<tr>
				<td>Имя</td>
				<td>
					<input type="text" name="name"/>
				</td>
			</tr>
			<tr>
				<td>Фамилия</td>
				<td>
					<input type="text" name="surname"/>
				</td>
			</tr>
			<tr>
				<td>Должность</td>
				<td>
					<input type="text" name="post"/>
				</td>
			</tr>
			<tr>
				<td>Пароль</td>
				<td>
					<input type="text" name="password"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Создать"/>
				</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	</form>
	<table style="margin-top: 10px;">
		<thead>
			<tr>
				<th>Имя</th>
				<th>Фамилия</th>
				<th>Должность</th>
				<th>Ред. права</th>
				<th>Пароль</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items = "${userList}" var="user">
			<tr>
				<td>${user.name}</td>
				<td>${user.surname}</td>
				<td>${user.post}</td>
				<td>${user.editRight}</td>
				<td>${user.password}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>