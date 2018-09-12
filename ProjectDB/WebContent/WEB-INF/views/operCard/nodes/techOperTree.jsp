<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="version">
<ul>
	<c:forEach items = "${childTechOpers}" var="childTechOper">	
		<il>
			<c:if test="${childTechOper.linkedObjs[0]['class'].simpleName eq 'Document' }">
			<div class="linkedObjDiv">
				<h3>Документ:</h3>
				${childTechOper.linkedObjs[0].docName} (${childTechOper.linkedObjs[0].docSign})&nbsp;
				<a href="${pageContext.request.contextPath}/showOperCard?techOperId=${childTechOper.objId}">Посмотреть</a>&nbsp;
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.servletContext.contextPath}/newOperCard?techOperId=${childTechOper.objId}&parentId=${childTechOper.objId}">Создать новую версию</a>
				</c:if>
			<p class="docInfo">
				<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechOper.linkedObjs[0].createTime}"/></span>&nbsp;&nbsp;
				<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechOper.linkedObjs[0].editTime}"/></span>
			</p>
			</div>
			</c:if>
			<c:set var="childTechOpers" value="${childTechOper.childObjs}" scope="request"/>
			<jsp:include page="techOperTree.jsp"/>
		</il>
	</c:forEach>
</ul>
</div>