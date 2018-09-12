<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="version">
<ul>
	<c:forEach items = "${childTechCtrls}" var="childTechCtrl">	
			<il>
				<c:if test="${childTechCtrl.linkedObjs[0]['class'].simpleName eq 'Document' }">
				<div class="linkedObjDiv">
					<h3>Документ:</h3>
					${childTechCtrl.linkedObjs[0].docName} (${childTechCtrl.linkedObjs[0].docSign})&nbsp;
					<a href="${pageContext.request.contextPath}/showCtrlCard?techCtrlId=${childTechCtrl.objId}">Посмотреть</a>&nbsp;
					<c:if test="${sessionScope.user.editRight eq true}">
					<a href="${pageContext.servletContext.contextPath}/newCtrlCard?parentId=${childTechCtrl.objId}&techOperId=${techOperId}">Создать новую версию</a><br/>
					</c:if>
					<p class="docInfo">
						<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechCtrl.createTime}"/></span>&nbsp;&nbsp;
						<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childTechCtrl.editTime}"/></span>
					</p>
				</div>
				</c:if>
				<c:set var="childTechCtrls" value="${childTechCtrl.childObjs}" scope="request"/>
				<jsp:include page="techCtrlTree.jsp"/>
			</il>
	</c:forEach>
</ul>
</div>