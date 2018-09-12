<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изделия</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forProductCRUD.js"></script>
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
	
	#prodListDiv{
		position: absolute;
		top: 80px;
		left: 0px;
		right: 0px;
		height:650px;
		overflow-y: auto;
	}
	
	.linkedObjDiv{
	 	display: inline-block;
		border: 2px solid grey;

		padding: 10px;
		margin-bottom: 10px;
	}
	
	a{
	display: inline-block; 
    padding: 4px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	
	div .version{
		border-left: 2px solid grey;
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
		<h1>Все изделия</h1>
		<c:if test="${sessionScope.user.editRight eq true}">
		<a href="${pageContext.servletContext.contextPath}/newProd">Создать новое изделие</a>
		</c:if>
	</header>
	<div id="prodListDiv">
	<ul>
		<c:forEach items = "${prodList}" var="prod">
			<c:if test="${prod.parentObj eq null}">	
				<il>
					<div class="linkedObjDiv">
					<h3>${prod.prodName}&nbsp;(${prod.prodSign})&nbsp;[${prod.productGroup.prodGroupName}]</h3>
					<c:if test="${sessionScope.user.editRight eq true}">
					<a href="${pageContext.servletContext.contextPath}/newProd?parent=${prod.objId}">Создать новую версию</a>&nbsp;&nbsp;
					<a href="${pageContext.servletContext.contextPath}/editProd?prodId=${prod.objId}">Редактировать</a>&nbsp;&nbsp;
					<a class="delete" id="delete_${prod.objId}" style="background-color: red" href="${pageContext.servletContext.contextPath}/deleteProd?prodId=${prod.objId}">Удалить</a>
					</c:if>
					<p class="prodInfo">
					Кол-во: ${prod.batchValue}&nbsp;&nbsp;&nbsp;Масса: ${prod.prodWeight} ${prod.measureUnit.measUnitName}&nbsp;&nbsp;
					<a style="background-color: plum" href="${pageContext.request.contextPath}/techProcList?productId=${prod.objId}">Маршрутные карты</a>&nbsp;&nbsp;
					<a style="background-color: plum" href="${pageContext.request.contextPath}/docList?productId=${prod.objId}">Документация</a><br><br>
					<span style="color:green">Создан: ${prod.creator.surname} <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${prod.createTime}"/></span>&nbsp;&nbsp;
					<span style="color:blue">Изменен:  ${prod.editor.surname} <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${prod.editTime}"/></span>&nbsp;${prod.creator.name}&nbsp;
					</p>
					</div>
					<c:set var="childProds" value="${prod.childObjs}" scope="request"/>
					<jsp:include page="nodes/productTree.jsp"/>
				</il>
			</c:if>
		</c:forEach>
	</ul>
	</div>
</body>
</html>