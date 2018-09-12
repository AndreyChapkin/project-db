<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Новый документ</title>
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
	input[type=submit], button{
		background-color: orange;
		color: black;
		margin-left: 20px;
	}
	input[type=text], select{
		width: 300px;
	}
	
	#docForm{
		position: absolute;
		padding: 20px;
		top: 80px;
		left: 0px;
		right: 0px;
		width: 750px;
	}	
	#addFileInput{
	margin-left: 0px;
	margin-right: 10px;
	}
	.fileInput{
	margin-bottom: 20px;
	}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${parentId == 0}">
			<div id="rectangle"></div>
			<header><h1>Создание нового документа</h1></header>
			<div id="docForm">
			<form method="POST" action="${pageContext.request.contextPath}/newDoc" enctype="multipart/form-data">
				Название документа <input style="position:relative; left: 25px;" type="text" name="docName"/><br><br>
				Обзоначение документа <input type="text" name="docSign"/><br><br>
				Тип документа <input style="position:relative; left: 60px;" type="text" name="docType"/><br><br>
				Литера <input style="position:relative; left: 115px;" type="text" name="docLetter"/><br><br>
				Файл документа <button type="button" id="addFileInput">+</button> <input type="file" name="techFile" class="fileInput"/><br><br>
				<input type="submit" value="Создать"/> 
			</form>
			</div>
		</c:when>
		
		<c:otherwise>
			<div id="rectangle"></div>
			<header><h1>Создание новой версии документа parentId=${parentDoc.docName}</h1></header>
			<div id="docForm">
			<form method="POST" action="${pageContext.request.contextPath}/newDoc" enctype="multipart/form-data">
				Название документа <input style="position:relative; left: 25px;" type="text" name="docName" value="${parentDoc.docName}"><br><br>
				Обзоначение документа <input type="text" name="docSign" value="${parentDoc.docSign}"/><br><br>
				Тип документа <input style="position:relative; left: 60px;" type="text" name="docType" value="${parentDoc.docType}"/><br><br>
				Литера <input style="position:relative; left: 115px;" type="text" name="docLetter" value="${parentDoc.docLetter}"/><br><br>
				Файл документа <button type="button" id="addFileInput">+</button> <input type="file" name="techFile" class="fileInput"/><br><br>
				<input type="submit" value="Создать"/> 
			</form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>