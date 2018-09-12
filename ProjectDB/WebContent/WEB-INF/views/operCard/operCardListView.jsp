<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Операционные карты</title>
<style>
	body{
		background-color: #ddd;
	}
	#operCardListDiv{
		height:600px;
		overflow-y: auto;
	}
	
	header{
		color: white;
		background-color: grey;
		padding: 5px;
		width: 1512px;
		margin-left: 0px;
	}
	
	#rectangle{
		position: absolute;
		z-index: -1;
		background-color: plum;
		width: 1100px;
		height: 66px;
	}
	
	header h1, h3{
		display:inline;
		margin-right: 10px;
	}
	div .version{
		border-left: 2px solid #aaa;
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
	
	.docInfo{
		margin: 10px;
	}
	
	#operCardListDiv{
		position: absolute;
		top: 80px;
		left: 0px;
		right: 0px;
		height:600px;
		overflow-y: auto;
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
		<h1>Операционные карты</h1>
		<c:if test="${techOperId ne null}">
			<c:if test="${sessionScope.user.editRight eq true}">
			<a href="${pageContext.servletContext.contextPath}/newOperCard?techOperId=${requestScope.techOper.objId}">Создать операционную карту</a>
			</c:if>
		</c:if>
	</header>
	<div id="operCardListDiv">
	<ul>
		<il>
			<c:if test="${techOper.linkedObjs[0]['class'].simpleName eq 'Document' }">
			<div class="linkedObjDiv">
				<h3>Документ:</h3>
				${requestScope.techOper.linkedObjs[0].docName} (${requestScope.techOper.linkedObjs[0].docSign})&nbsp;
				<a href="${pageContext.request.contextPath}/showOperCard?techOperId=${requestScope.techOper.objId}">Посмотреть</a>&nbsp;
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.servletContext.contextPath}/newOperCard?techOperId=${requestScope.techOper.objId}&parentId=${requestScope.techOper.objId}">Создать новую версию</a>
				</c:if>
			<p class="docInfo">
				<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techOper.linkedObjs[0].createTime}"/></span>&nbsp;&nbsp;
				<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techOper.linkedObjs[0].editTime}"/></span>
			</p>
			</div>
			</c:if>
			<c:set var="childTechOpers" value="${requestScope.techOper.childObjs}" scope="request"/>
			<jsp:include page="nodes/techOperTree.jsp"/>
		</il>
	</ul>
	</div>
	
	<c:if test="${techOperList ne null and techOper eq null}">
	<div id="operCardListDiv">
	<ul>
		<c:forEach items="${techOperList}" var="techOper">
			<il>
			<c:if test="${techOper.linkedObjs[0]['class'].simpleName eq 'Document' }">
			<div class="linkedObjDiv">
				<h3>Документ:</h3>
				${techOper.linkedObjs[0].docName} (${techOper.linkedObjs[0].docSign})&nbsp;
				<a href="${pageContext.request.contextPath}/showOperCard?techOperId=${techOper.objId}">Посмотреть</a>&nbsp;
				<a href="${pageContext.servletContext.contextPath}/newOperCard?techOperId=${techOper.objId}&parentId=${techOper.objId}">Создать новую версию</a>
			<p class="docInfo">
				<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techOper.linkedObjs[0].createTime}"/></span>&nbsp;&nbsp;
				<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techOper.linkedObjs[0].editTime}"/></span>
			</p>
			</div>
			</c:if>
			<c:set var="childTechOpers" value="${techOper.childObjs}" scope="request"/>
			<jsp:include page="nodes/techOperTree.jsp"/>
			</il>
		</c:forEach>
	</ul>
	</div>
	</c:if>
</body>
</html>