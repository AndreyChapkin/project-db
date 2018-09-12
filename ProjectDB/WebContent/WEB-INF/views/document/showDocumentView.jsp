<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Документ</title>
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
	
	#docInfo{
		margin-top: 40px;
		margin-left: 20px;
	}
	
	#editDocHref, #delDocHref{
	display: inline-block; 
    padding: 2px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	
	#AllImagesDiv{
	position: absolute;
	left: 350px;
	top: 90px;
	height: 650px;
	width: 1100px;
	overflow: auto;
	}
	.imageDiv{
		text-align: center;
		height: 600px;
		width: 500px;
		float:left;
		display: block-inline;
		margin: 10px;
	}
	.imageDiv .removeImage{
		position: absolute;
		background-color: red;
	}
	img, object{
		width: 80%;
		height: 90%;
		margin-bottom: 10px;
		cursor: pointer;
	}
	#bigImage{
		display: none;
		width: 96%;
		height: 96%;
		left: 2%;
		top: 2%;
		position: absolute;
		z-index: 5;
		background: red;
	}
	#bigImage img{
		width: 100%;
		height: 100%;
	}
</style>
</head>
<body>
	<div id="bigImage"></div>
	<div id="rectangle"></div>
	<header>
		<h1>Документ</h1>
	</header>
	<div id="docInfo">
		<p>
		<c:if test="${sessionScope.user.editRight eq true}">
			<a id="editDocHref" href="${pageContext.servletContext.contextPath}/editDoc?docId=${doc.objId}">Редактировать</a>&nbsp;&nbsp;
			<a id="delDocHref" style="margin-left:30px; background-color: red" href="${pageContext.servletContext.contextPath}/delDoc?docId=${doc.objId}&productId=${doc.assocObj.objId}">Удалить</a><br/>
		</c:if>
		</p>
		<h3>Тип:</h3> ${doc.docType}<br/><br/>
		<h3>Название:</h3> ${doc.docName}<br/><br/>
		<h3>Обозначение:</h3> ${doc.docSign}<br/><br/>
		<h3>Литера:</h3> ${doc.docLetter}
	</div>
	<div id="AllImagesDiv">
	<c:forEach items="${doc.techFiles}" var="file">
		<div class="imageDiv">
			<button style="margin-right: 50px;" type="button" class="removeImage" id="${file.fileId}">X</button>
			<c:if test="${not empty file.fileData}">
				<c:choose>
					<c:when test="${file.fileType == 'pdf'}">
						<object data="imageFromFile?fileId=${file.fileId}&type=pdf" type="application/pdf">
				    		<embed src="imageFromFile?fileId=${file.fileId}&type=pdf" type="application/pdf">
				   			</embed>
				   		</object><br/>
				   		<span>${file.fileName}</span>
				   	</c:when>
				   	<c:otherwise>
				   		<c:choose>
				   			<c:when test="${file.fileType == 'jpg' or file.fileType == 'jpeg' or file.fileType == 'png'}">
								<img src="imageFromFile?fileId=${file.fileId}&type=image"><br/>
								<span>${file.fileName}</span>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/imageFromFile?fileId=${file.fileId}">
									<img src="img/document_logo.png"><br/>
									<span>${file.fileName}</span>
								</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>	
	</c:forEach>
	</div>
</body>
</html>