<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Операционная карта</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forShowOperCardView.js"></script>
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
	table{
		table-layout: fixed;
		width: 1100px;
		margin-top: 0;
		border: 3px solid black;
		border-bottom: transparent;
		border-spacing: 0;
		border-collapse: collapse;
	}
	table td{
		text-align: center;
		border: 2px solid black;
		padding: 2px;
		height: 12px;
	}
	#firstHead{
		margin-top: 15px;
	}
	#firstHead td, #secondHead td,
	#secondInHelp tr:nth-child(2) td{
		border-bottom: transparent;
	}
	#secondHead tr td:nth-child(-n+4),
	#thirdHead tr td:nth-child(-n+4){
		text-align: left;
		font-size: 14px;
		padding: 2px;
	}
	#thirdHead td, #helpTable table td,
	#bodyHead table tr>td{
		border-top: transparent;
	}
	#helpTable,#bodyHead,#bodyHead table,
	#bodyHead>tr>td,#bodyHead table>tr>td,
	#helpTable table, #control, #control td{
		border: transparent;
	}
	#helpTable td, #bodyHead tr>td{
		padding: 0;
	}

	#bodyHead .firstBody tr td,#bodyHead .secondBody tr td,
	#bodyHead .thirdBody tr td{
		padding-top: 4px;
		padding-bottom: 4px;
	}
	#bodyHead table, #helpTable table{
		width: 1098px;
	}
	
	#scrollableDiv{
		position:relative;
		height: 380px;
		width:1120px;
		overflow-y: auto;
	}
	
	#uploadDiv{
		margin: 10px;
	}
	
	a, button{
	display: inline-block; 
    padding: 2px; 
  	text-decoration: none;
    cursor: pointer;
    background: orange; 
    color: black; 
	}
	
	#tableDiv{
	position: absolute;
	left: 205px;
	top: 80px;
	}
	
	#operSketches{
		display: none;
		background-color:rgba(200,200,200,0.5);
		overflow: scroll;
		left: 205px;
		top: 120px;
		position: absolute;
		height: 600px;
		width: 1100px;
		z-index: 2;
	}
	#operSketches img{
		width: 90%;
		height: 100%;
	}
	#showUploadForm{
		display: inline;
	}
	#uploadForm{
		display: none;
	}
	.image{
		position: relative;
		margin: auto;
		margin-bottom: 10px;
    	width: 80%;
	}
	.image .removeImage{
		position: absolute;
		right: 0px;
		background-color: red;
	}
	
</style>
</head>
<body>
	<div id="operSketches">	
		<button type="button" id="showUploadForm">Добавить эскизы</button>
		<div id="uploadDiv">
			<form method="POST" action="${pageContext.request.contextPath}/uploadImage" id="uploadForm" enctype="multipart/form-data">
				<input type="hidden" name="techOperId" value="${techOper.objId}"/>
				<input type="file" name="dataFile"/>
				<input type="submit" value="Отправить" id="sendFile"/>
			</form>
		</div>
		
		<c:forEach items="${techOper.linkedObjs[0].techFiles}" var="file">
		<div class="image">
			<button style="margin-right: 50px;" type="button" class="removeImage" id="${file.fileId}">&nbsp;X&nbsp;</button>
			<c:if test="${not empty file.fileData}">
				<c:choose>
					<c:when test="${file.fileType == 'pdf'}">
						<object data="imageFromFile?fileId=${file.fileId}&type=pdf" type="application/pdf" width="750px" height="750px">
				    		<embed src="imageFromFile?fileId=${file.fileId}&type=pdf" type="application/pdf">
				   			</embed>
				   		</object>
				   	</c:when>
				   	<c:otherwise>
							<img src="imageFromFile?fileId=${file.fileId}&type=image">
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		</c:forEach>	
	</div>
	
	<div id="rectangle"></div>
	<header>
		<h1>Операционная карта</h1>
	</header>
	<div id="tableDiv">
	<table id="control">
		<tr>
			<td style="text-align:right">
				<button style="margin-right: 50px;" type="button" id="showSketches">Посмотреть эскизы</button>
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.request.contextPath}/editOperCard?techOperId=${techOper.objId}">Редактировать</a>
				<a href="${pageContext.request.contextPath}/delOperCard?techOperId=${techOper.objId}" style="margin-left:30px; background-color: red;">Удалить</a>
				</c:if>
			</td>
		</tr>
	</table>
	<table id="firstHead">
		<tr>
			<td></td>
			<td style ="width: 250px">
				${techOper.linkedObjs[0].docName}
			</td>
			<td style ="width: 20px"></td>
			<td style ="width: 20px"></td>
		</tr>
	</table>		
	<table id="secondHead">
		<colgroup>
			<col span="1" width="85px">
			<col span="1" width="120px">
			<col span="1" width="60px">
			<col span="1" width="60px">
		</colgroup>
		<tr>
			<td>Разработал</td>
			<td></td>
			<td></td>
			<td></td>
			<td rowspan="0"></td>
			<td rowspan="0" width="180px">
				${techOper.assocObj.assocObj.prodSign}
			</td>
			<td rowspan="0"></td>
			<td rowspan="0" width="180px">
				${techOper.linkedObjs[0].docSign}
			</td>
		</tr>
		<tr>
			<td>Проверил</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Принял</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<table id="thirdHead">
			<colgroup>
			<col span="1" width="85px">
			<col span="1" width="120px">
			<col span="1" width="60px">
			<col span="1" width="60px">
		</colgroup>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td rowspan="0">
			${techOper.assocObj.assocObj.prodName}
			</td>
			<td rowspan="0" style ="width: 40px">
			${techOper.linkedObjs[0].docLetter}
			</td>
			<td rowspan="0" style ="width: 40px"></td>
			<td rowspan="0" style ="width: 40px"></td>
		</tr>
		<tr>
			<td>Н. контр.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<table id="helpTable">
		<tr>
			<td>
				<table id="firstInHelp">
					<tr>
						<td style ="width: 280px">Наименование операции</td>
						<td style ="width: 240px">Материал</td>
						<td style ="width: 90px">Твердость</td>
						<td style ="width: 40px">ЕВ</td>
						<td>МД</td>
						<td style ="width: 250px">Профиль и размеры</td>
						<td>МЗ</td>
						<td style ="width: 60px">КОИД</td>
					</tr>
					<tr>
						<td>
							${techOper.typicOperation.operName}
						</td>
						<td>
							${techOper.assocObj.typicBlank.material.matName}
							${techOper.assocObj.typicBlank.material.matSort}
							${techOper.assocObj.typicBlank.material.matStandart}
						</td>
						<td>
							${techOper.assocObj.typicBlank.material.hardness }
						</td>
						<td>
							${techOper.assocObj.assocObj.measureUnit.measUnitName}
						</td>
						<td>
							${techOper.assocObj.assocObj.prodWeight}
						</td>
						<td>
							${techOper.assocObj.typicBlank.profile} x ${techOper.assocObj.blankSize}
						</td>
						<td>
							${techOper.assocObj.blankWeight}
						</td>
						<td>
							${techOper.nspd}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table id="secondInHelp">
					<tr>
						<td style ="width: 280px">Оборудование, устройство ЧПУ</td>
						<td style ="width: 240px">Обозначение программы</td>
						<td>То</td>
						<td>Тв</td>
						<td>Тпз</td>
						<td>Тшт</td>
						<td style ="width: 260px">СОЖ</td>
					</tr>
					<tr style ="height: 40px;">
						<td>
							${techOper.equipment.eqCode}, 
							${techOper.equipment.eqName}
						</td>
						<td>
							${techOper.programSign}
						</td>
						<td>
							${techOper.mainTime}
						</td>
						<td>
							${techOper.assistTime}
						</td>
						<td>
							${techOper.prepareTime}
						</td>
						<td>
							${techOper.unitTime}
						</td>
						<td>
							${techOper.lcl}
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id="scrollableDiv">
	<table id="bodyHead">
		<tr>
			<td>
				<table id="firstBodyHead">
					<tr>
						<td style ="width: 40px">Р</td>
						<td></td>
						<td style ="width: 80px">ПИ</td>
						<td style ="width: 120px">D или B</td>
						<td style ="width: 80px">L</td>
						<td style ="width: 50px">t</td>
						<td style ="width: 50px">i</td>
						<td style ="width: 80px">S</td>
						<td style ="width: 60px">n</td>
						<td style ="width: 60px">V</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:set var="i" value="0"/>
		<c:forEach items="${techOper.techTransits}" var="techTran">
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="firstBody">
					<tr>
						<td style ="width: 40px">
							О <c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style="text-align:left; padding-left:4px;">
							${techTran.tranNumber}. 
							${techTran.tranDescrip}
						</td>
						<td style ="width: 60px">
							${techTran.tranMainTime}
						</td>
						<td style ="width: 60px">
							${techTran.tranAssistTime}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${fn:length(techTran.tools) > 0}">
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="secondBody">
					<tr>
						<td style ="width: 40px">
						Т <c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style="text-align:left; padding-left:4px;">
							<c:forEach items="${techTran.tools}" var="tool">
								${tool.toolCode}, ${tool.toolName}, ${tool.toolStandart}; 
							</c:forEach>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="thirdBody">
					<tr>
						<td style ="width: 40px">
						Р <c:if test="${i<10}">0</c:if>${i}
						</td>
						<td></td>
						<td style ="width: 80px">
							${techTran.adjustPosNumber}
						</td>
						<td style ="width: 120px">
							${techTran.treatWidth}
						</td>
						<td style ="width: 80px">
							${techTran.strokeLength}
						</td>
						<td style ="width: 50px">
							${techTran.cutDepth}
						</td>
						<td style ="width: 50px">
							${techTran.strokeCount}
						</td>
						<td style ="width: 80px">
							${techTran.supValue}
						</td>
						<td style ="width: 60px">
							${techTran.rotSpeed}
						</td>
						<td style ="width: 60px">
							${techTran.cutSpeed}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:if>
		</c:forEach>
	</table>
	</div>
	</div>
</body>
</html>