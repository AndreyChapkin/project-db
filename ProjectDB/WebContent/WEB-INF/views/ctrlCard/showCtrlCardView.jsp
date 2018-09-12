<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Карта контроля</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
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

	#bodyHead .firstBody tr td,#bodyHead .secondBody tr td{
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
	
	a{
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

</style>
</head>
<body>
	<div id="rectangle"></div>
	<header>
		<h1>Карта контроля</h1>
	</header>
	<div id="tableDiv">
	<table id="control">
		<tr>
			<td style="text-align:right">
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.request.contextPath}/editCtrlCard?techOperId=${techOper.objId}&techCtrlId=${techCtrl.objId}">Редактировать</a>
				<a href="${pageContext.request.contextPath}/delCtrlCard?techCtrlId=${techCtrl.objId}&techOperId=${techOper.objId}" style="margin-left:30px; background-color: red">Удалить</a>
				</c:if>
			</td>
		</tr>
	</table>
	<table id="firstHead">
		<tr>
			<td></td>
			<td style ="width: 250px">
				${techCtrl.linkedObjs[0].docName}
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
				${techCtrl.linkedObjs[0].docSign}
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
				${techCtrl.linkedObjs[0].docLetter}
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
						<td style ="width: 552px">Наименование операции</td>
						<td>Наименование, марка материала</td>
						<td style ="width: 70px">МД</td>
					</tr>
					<tr>
						<td>${techOper.typicOperation.operCode}, 
							${techOper.typicOperation.operName}
						</td>
						<td>
							${techOper.assocObj.typicBlank.material.matName} 
							, ${techOper.assocObj.typicBlank.material.matSort}
						</td>
						<td>
							${techOper.assocObj.assocObj.prodWeight}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table id="secondInHelp">
					<tr>
						<td style ="width: 410px">Наименование оборудования</td>
						<td style ="width: 70px">Тв</td>
						<td style ="width: 70px">То</td>
						<td></td>
						<td style ="width: 161px">Обозначение ИОТ</td>
					</tr>
					<tr style ="height: 40px;">
						<td>
							${techOper.equipment.eqCode}, 
							${techOper.equipment.eqName}
						</td>
						<td>
							${techOper.assistTime}
						</td>
						<td>
							${techOper.mainTime}
						</td>
						<td></td>
						<td>
							${techCtrl.intsructSign}
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
						<td style ="width: 310px">Контролируемые параметры</td>
						<td style ="width: 200px">Код средства ТО</td>
						<td >Наименование средств ТО</td>
						<td style ="width: 90px">Объем и ПК</td>
						<td style ="width: 70px">То/Тв</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:set var="i" value="0"/>
		<c:forEach items="${techCtrl.paramControls}" var="paramCtrl">
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="firstBody">
					<tr>
						<td style ="width: 40px">
							<c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style ="width: 305px; text-align:left; padding-left:5px;">
							${paramCtrl.paramNumber}. 
							${paramCtrl.parameter}
						</td>
						<td style ="width: 200px">
							<c:if test="${fn:length(paramCtrl.ctrlDevices) > 0}">
							${paramCtrl.ctrlDevices[0].toolCode}
							</c:if>
						</td>
						<td>
							<c:if test="${fn:length(paramCtrl.ctrlDevices) > 0}">
							${paramCtrl.ctrlDevices[0].toolName}, 
							${paramCtrl.ctrlDevices[0].toolStandart}
							</c:if>
						</td>
						<td style ="width: 90px">
							${paramCtrl.valuePC}
						</td>
						<td style ="width: 70px">
							${paramCtrl.timesRatio}
						</td>
					</tr>	
				</table>
			</td>
		</tr>
		<c:forEach items="${paramCtrl.ctrlDevices}" begin="1" var="ctrlDevice">
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="firstBody">
					<tr>
						<td style ="width: 40px">
							<c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style ="width: 305px; text-align:left; padding-left:5px;">
						</td>
						<td style ="width: 200px">
							${ctrlDevice.toolCode}
						</td>
						<td >
							${ctrlDevice.toolName}, 
							${ctrlDevice.toolStandart}
						</td>
						<td style ="width: 90px">
						</td>
						<td style ="width: 70px">
						</td>
					</tr>	
				</table>
			</td>
		</tr>
		</c:forEach>
		</c:forEach>
	</table>
	</div>
	</div>
</body>
</html>