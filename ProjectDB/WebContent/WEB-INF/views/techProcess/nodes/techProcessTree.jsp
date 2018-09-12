<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="version">
<ul>
	<c:forEach items = "${childTechProcs}" var="childTechProc">	
			<il>
				
				<c:if test="${childTechProc.linkedObjs[0]['class'].simpleName eq 'Document' }">
				<div class="linkedObjDiv">
					<h3>Документ:</h3>
					${childTechProc.linkedObjs[0].docName} (${childTechProc.linkedObjs[0].docSign})&nbsp;
					<a href="${pageContext.request.contextPath}/showTechProc?techProcId=${childTechProc.objId}">Посмотреть</a>&nbsp;
					<c:if test="${sessionScope.user.editRight eq true}">
					<a href="${pageContext.servletContext.contextPath}/newTechProc?productId=${productId}&parentId=${childTechProc.objId}">Создать новую версию</a>
					</c:if>
					<p class="docInfo">
					<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechProc.createTime}"/></span>&nbsp;&nbsp;
					<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechProc.editTime}"/></span>
					</p>
				</div>
				</c:if>
				<c:set var="childTechProcs" value="${childTechProc.childObjs}" scope="request"/>
				<jsp:include page="techProcessTree.jsp"/>
			</il>
	</c:forEach>
</ul>
</div>