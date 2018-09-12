<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="version">
<ul>
	<c:forEach items = "${childDocs}" var="childDoc">	
			<il>
					<div class="linkedObjDiv">
					<h3>Документ:</h3>
					${childDoc.docName} (${childDoc.docSign})&nbsp;
					<c:if test="${sessionScope.user.editRight eq true}">
					<a href="${pageContext.servletContext.contextPath}/newDoc?parentId=${childDoc.objId}&productId=${prod.objId}">Создать новую версию</a>&nbsp;&nbsp;
					</c:if>
					<a href="${pageContext.request.contextPath}/showDoc?docId=${childDoc.objId}">Посмотреть</a>&nbsp;<br><br>
					<p class="docInfo">
					<span style="color:green">Создан: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childDoc.createTime}"/></span>&nbsp;&nbsp;
					<span style="color:blue">Изменен: <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childDoc.editTime}"/></span>
					</p>
					</div>
					<c:set var="childDocs" value="${childDoc.childObjs}" scope="request"/>
					<jsp:include page="documentTree.jsp"/>
				</il>
	</c:forEach>
</ul>
</div>