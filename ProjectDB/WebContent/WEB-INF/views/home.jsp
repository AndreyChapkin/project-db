<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forHomeView.js"></script>
<title>Начальное меню</title>
<style>
	body{
		background-color: #ddd;
	}

	#menuDiv{
		position: absolute;
		top: 40px;
		left: 10px;
		right: 0px;
		height:360px;
		width: 350px;
		overflow-y: auto;
	}
	a{
		display: inline-block; 
	    padding: 4px; 
	    width: 210px;
	  	text-decoration: none;
	    cursor: pointer;
	    background: orange; 
	    color: black; 
	    margin-bottom: 20px;
	}
	#newMesButton{
		display: inline-block; 
	    padding: 4px; 
	    width: 210px;
	  	text-decoration: none;
	    cursor: pointer;
	    background: orange; 
	    color: black; 
	}
	#changeView{
	    padding: 4px; 
	    width: 180px;
	    cursor: pointer;
	    background: orange; 
	    color: black; 
	    margin-right: 30px;
	}
	#exitHref{
		width: 50px;
		background: grey;
		color: white; 
	}
	#exitHref:hover{
		background: red;
		color: black; 
	}
	a:hover{
		background: plum; 
	}
	ul{
		list-style-type: none;
	}
	#userInfo table td{
		padding: 4px;
		width: 100%;
	}
	#userInfo table{
		width: 100%;
		border-collapse: collapse;
	}
	#userInfo{
		position: absolute;
		left: 50px;
		top: 380px;
		background: white;
		width: 300px;
	}
	#messageDiv{
		position: absolute;
		padding-top: 0px;
		left: 500px;
		top: 50px;
		width: 700px;
		height: 650px;
	}
	#sendMesForm{
		display: none;
		margin-top: 10px;
		magrin-bottom: 10px;
	}
	#messageDiv h2, h3{
		display: inline-block;
		padding: 2px;
		margin-top: 0px;
		background: grey;
		color: white;
	}
	#messageDiv h4{
		margin-bottom: 5px;
		margin-top: 0px;
	}
	#messageDiv p{
		display: block;
		padding: 2px;
		background: white;
		width: 90%;
	}
	#scrollableDiv{
		position: relative;
		
		border-left: 2px solid white;
		border-right: 2px solid white;
		margin-top: 20px;
		overflow-y: auto;
		height: 570px;
	}
	#outScrollableDiv{
		position: relative;
		border-left: 2px solid white;
		border-right: 2px solid white;
		margin-top: 20px;
		overflow-y: auto;
		height: 570px;
	}
</style>
</head>
<body>
	<c:if test="${sessionScope.login ne 'admin'}">
	<div id="userInfo">
		<table>
			<tr>
				<td style="background: plum;">Пользователь</td>
			</tr>
			<tr>
				<td>
				${sessionScope.user.name}
				</td>
			</tr>
			<tr>
				<td>${sessionScope.user.surname}</td>
			</tr>
			<tr>
				<td>${sessionScope.user.post}</td>
			</tr>
		</table>
	</div>
	</c:if>
	<div id="menuDiv">	
		<ul>
			<li><a id="exitHref" href="${pageContext.request.contextPath}/login">Выход</a></li>
			<li><a href="${pageContext.request.contextPath}/docList">Документация</a></li>
			<li><a href="${pageContext.request.contextPath}/prodList">Изделия</a></li>
			<li><a href="${pageContext.request.contextPath}/techProcList">Маршрутные карты</a></li>
			<li><a href="${pageContext.request.contextPath}/operCardList">Операционные карты</a></li>
			<li><a href="${pageContext.request.contextPath}/ctrlCardList">Карты технического контроля</a></li>
			<c:if test="${sessionScope.login eq 'admin'}">
				<li><a href="${pageContext.request.contextPath}/userList">Пользователи</a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${sessionScope.login ne 'admin'}">
		<div id="messageDiv">
			<h2>Сообщения</h2><br/>
			<button id="changeView" type="button">См. отправленные</button>
			<button id="newMesButton" type="button">Написать сообщение</button>
			<div id="sendMesForm">
				<form method="POST" action="${pageContext.request.contextPath}/newMes">
					<table>
						<tr>
							<td>Кому:</td>
							<td>
								<select style="width: 100px;" name="recipientLogin" id="measureSelector">
										<c:forEach items="${allUsers}" var="allUser">
											<option value="${allUser.surname}">${allUser.surname}</option>
										</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Заголовок</td>
							<td>
							<input style ="width:550px;" type="text" name="title"/>
							</td>
						</tr>
						<tr>
							<td>Текст</td>
							<td>
								<textarea style ="width:550px; resize:none;" rows=5 name="text"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="Отправить"/>	
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="scrollableDiv">
				<ul>
					<c:forEach items="${mesList}" var="mes">
						<li>
							<article>
								<h4>От: ${mes.sender.surname},  ${mes.sender.post}</h4>
								<h4>Когда: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${mes.sendTime}"/></h4><br/>
								<h3>${mes.title}</h3>
								<button class="removeMes" id="${mes.mesId}" style="margin-left: 10px; background: coral;" type="button">Удалить</button>
								<p>${mes.text}</p>
								<hr/>
							</article>
							
						</li>
					</c:forEach>
				</ul>
			</div>
			<div id="outScrollableDiv" style="display: none;">
				<ul>
					<c:forEach items="${outMesList}" var="mes">
						<li>
							<article>
								<h4>Кому: ${mes.recipient.surname},  ${mes.recipient.post}</h4>
								<h4>Когда: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${mes.sendTime}"/></h4><br/>
								<h3>${mes.title}</h3>
								<button class="removeOutMes" id="${mes.mesId}" style="margin-left: 10px; background: coral;" type="button">Удалить</button>
								<p>${mes.text}</p>
								<hr/>
							</article>	
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:if>
</body>
</html>