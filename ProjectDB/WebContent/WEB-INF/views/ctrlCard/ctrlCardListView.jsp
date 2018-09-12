<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Карты контроля</title>
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
	
	#rectangle{
		position: absolute;
		z-index: -1;
		background-color: plum;
		width: 1100px;
		height: 66px;
	}
	
	#ctrlCardListDiv{
		position: absolute;
		top: 80px;
		left: 0px;
		right: 0px;
		height:600px;
		overflow-y: auto;
	}
	
	a{
	display: inline-block; 
    padding: 4px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	
	h1, h3{
		display:inline;
		margin-right: 10px;
	}
	.linkedObjDiv{
	 	display: inline-block;
		border: 2px solid grey;
		padding: 10px;
		margin-bottom: 10px;
	}
	
	.docInfo{
		margin-top: 5px;
		margin-bottom: 0px;
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
		<h1>Карты контроля</h1>
		<c:if test="${sessionScope.user.editRight eq true}">
		<a href="${pageContext.servletContext.contextPath}/newCtrlCard?techOperId=${techOperId}">Создать карту контроля</a>
		</c:if>
	</header>
	<div id="ctrlCardListDiv">
	<ul>
		<c:forEach items = "${techCtrlList}" var="techCtrl">
			<c:if test="${techCtrl.parentObj eq null}">	
				<il>
					<c:if test="${techCtrl.linkedObjs[0]['class'].simpleName eq 'Document' }">
					<div class="linkedObjDiv">
						<h3>Документ:</h3>
						${techCtrl.linkedObjs[0].docName} (${techCtrl.linkedObjs[0].docSign})&nbsp;
						<a href="${pageContext.request.contextPath}/showCtrlCard?techCtrlId=${techCtrl.objId}">Посмотреть</a>&nbsp;
						<c:if test="${sessionScope.user.editRight eq true}">
						<a href="${pageContext.servletContext.contextPath}/newCtrlCard?parentId=${techCtrl.objId}&techOperId=${techOperId}">Создать новую версию</a><br/>
						</c:if>
						<p class="docInfo">
							<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techCtrl.linkedObjs[0].createTime}"/></span>&nbsp;&nbsp;
							<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${techCtrl.linkedObjs[0].editTime}"/></span>
						</p>
					</div>
					</c:if>
					<c:set var="childTechCtrls" value="${techCtrl.childObjs}" scope="request"/>
					<jsp:include page="nodes/techCtrlTree.jsp"/>
				</il>
			</c:if>
		</c:forEach>
	</ul>
	</div>
</body>
</html>