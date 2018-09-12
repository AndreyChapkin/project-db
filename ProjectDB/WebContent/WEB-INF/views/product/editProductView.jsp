<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Новое изделие</title>
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
	
	h1{
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
	input[type=submit], input[type=button]{
		background-color: orange;
		color: black;
		margin-left: 20px;
	}
	input[type=text], select{
		width: 300px;
	}
	
	#prodForm{
		position: absolute;
		padding: 20px;
		top: 80px;
		left: 0px;
		right: 0px;
		height:250px;
		width: 750px;
		overflow-y: auto;
	}
</style>
</head>
<body>
	<div id="rectangle"></div>
	<header><h1>Редактировать изделие</h1></header>
	<div id="prodForm">
	<form method="POST" action="${pageContext.request.contextPath}/editProd">
		
		Название &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="prodName" value="${prod.prodName}"/><br><br>
		Масса &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="prodWeight" value="${prod.prodWeight}"/>
		
		<select style="width: 100px;" name="measUnitName" id="measureSelector">
			<c:forEach items="${measUnitList}" var="measUnit">
				<option
				<c:if test="${prod.measureUnit.measUnitName eq measUnit.measUnitName}">selected</c:if>
				 value="${measUnit.measUnitName}">${measUnit.measUnitName}</option>
			</c:forEach>
		</select>
		<input type="button" id="measureButton" value="Новая единица измерения"/><br><br>
		
		Количество &nbsp;&nbsp;<input type="text" name="batchValue" value="${prod.batchValue}"/><br><br>
		Обозначение <input type="text" name="prodSign" value="${prod.prodSign}"/><br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;
		<select name="prodGroupName" id="groupSelector">
			<c:forEach items="${prodGroupList}" var="prodGr">
				<option 
				<c:if test="${prod.productGroup.prodGroupName eq prodGr.prodGroupName}">selected</c:if> 
				value="${prodGr.prodGroupName}">
					${prodGr.prodGroupName}
				</option>
			</c:forEach>
		</select>
		<input type="button" id="groupButton" value="Новая группа"/><br><br>
		<input type="submit" value="Изменить"/>
	</form>
	</div>
</body>
</html>