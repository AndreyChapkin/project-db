<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="version">
<ul>
	<c:forEach items = "${childProds}" var="childProd">	
			<il>
				<div class="linkedObjDiv">
				<h3>${childProd.prodName}&nbsp;(${childProd.prodSign})&nbsp;[${childProd.productGroup.prodGroupName}]</h3>
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.servletContext.contextPath}/newProd?parent=${childProd.objId}">Создать новую версию</a>&nbsp;&nbsp;
				<a href="${pageContext.servletContext.contextPath}/editProd?prodId=${childProd.objId}">Редактировать</a>&nbsp;&nbsp;
				<a class="delete" id="delete_${childProd.objId}" style="background-color: red" href="${pageContext.servletContext.contextPath}/deleteProd?prodId=${childProd.objId}">Удалить</a>
				</c:if>
				<p>
				Кол-во: ${childProd.batchValue}&nbsp;&nbsp;&nbsp;Масса: ${childProd.prodWeight} ${childProd.measureUnit.measUnitName}&nbsp;&nbsp;
				<a style="background-color: plum" href="${pageContext.request.contextPath}/techProcList?productId=${childProd.objId}">Маршрутные карты</a>&nbsp;&nbsp;
				<a style="background-color: plum" href="${pageContext.request.contextPath}/docList?productId=${childProd.objId}">Документация</a><br/><br/>
				<span style="color:green">Создан: ${childProd.creator.surname} <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childProd.createTime}"/></span>&nbsp;&nbsp;
					<span style="color:blue">Изменен: ${childProd.editor.surname} <fmt:formatDate type = "both" dateStyle="short" timeStyle="short" value = "${childProd.editTime}"/></span>&nbsp;${childProd.creator.name}&nbsp;
				</p>
				</div>
				<c:set var="childProds" value="${childProd.childObjs}" scope="request"/>
				<jsp:include page="productTree.jsp"/>
			</il>
	</c:forEach>
</ul>
</div>