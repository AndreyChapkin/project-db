<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Документация</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forDocumentCRUD.js"></script>
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
	
	#docListDiv{
		position: absolute;
		top: 80px;
		left: 0px;
		right: 0px;
		height:600px;
		overflow-y: auto;
	}
	
	#assocObjDiv{
		display: inline-block;
		margin-left: 10px;
		margin-top: 10px;
		padding: 10px;
		border-left: 2px solid grey;
		border-bottom: 2px solid grey;
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
	.docInfo{
		margin-top: 5px;
		margin-bottom: 0px;
	}
	#homeHref{
		position: absolute;
		top: 22px;
		left: 1080px;
	}
</style>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/home" id="homeHref">
			<img  src="img/home_icon.png" width="20px" height="20px"/>
	</a>
	<div id="rectangle"></div>
	<header>
		<h1>Вся документация</h1>
		
	</header>
	<div id="docListDiv">
	<c:if test="${prod != null }">
	<div id="assocObjDiv">
	<h3>Изделие: ${prod.prodName}&nbsp;(${prod.prodSign})</h3>
	<c:if test="${sessionScope.user.editRight eq true}">
	<a href="${pageContext.servletContext.contextPath}/newDoc?productId=${prod.objId}">Создать новый документ</a>
	</c:if>
	</div>
	</c:if>
	<ul>
		<c:forEach items = "${docList}" var="doc">
			<c:if test="${doc.parentObj eq null}">	
				<il>
					<div class="linkedObjDiv">
					<h3>Документ:</h3>
					${doc.docName} (${doc.docSign})&nbsp;
					<c:if test="${sessionScope.user.editRight eq true}">
					<a href="${pageContext.servletContext.contextPath}/newDoc?parentId=${doc.objId}&productId=${prod.objId}">Создать новую версию</a>&nbsp;&nbsp;
					</c:if>
					<a href="${pageContext.request.contextPath}/showDoc?docId=${doc.objId}">Посмотреть</a>&nbsp;<br><br>
					<p class="docInfo">
					<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${doc.createTime}"/></span>&nbsp;&nbsp;
					<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${doc.editTime}"/></span>
					</p>
					</div>
					<c:set var="childDocs" value="${doc.childObjs}" scope="request"/>
					<jsp:include page="nodes/documentTree.jsp"/>
				</il>
			</c:if>
		</c:forEach>
	</ul>
	</div>
</body>
</html>