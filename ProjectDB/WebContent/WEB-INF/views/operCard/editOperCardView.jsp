<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Редактирование ОК</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forEditOperCardView.js"></script>
<style>
	body{
		background-color: #ddd;
	}
	header{
		color: white;
		background-color: grey;
		padding: 5px;
		width: 1512px;
	}
	
	#rectangle{
		position: absolute;
		z-index: -1;
		background-color: plum;
		width: 1100px;
		height: 66px;
	}
	
	input[type=submit]{
		background-color: orange;
		color: black;
		margin-left: 20px;
	}
	#tableDiv table{
		table-layout: fixed;
		width: 1100px;
		margin-top: 0;
		border: 3px solid black;
		border-bottom: transparent;
		border-spacing: 0;
		border-collapse: collapse;
	}
	#tableDiv table td{
		text-align: center;
		border: 2px solid black;
		padding: 2px;
		height: 12px;
	}
	#tableDiv #firstHead{
		margin-top: 18px;
	}
	#tableDiv #firstHead td, #tableDiv #secondHead td,
	#tableDiv #secondInHelp tr:nth-child(2) td{
		border-bottom: transparent;
	}
	#tableDiv #secondHead tr td:nth-child(-n+4),
	#tableDiv #thirdHead tr td:nth-child(-n+4){
		text-align: left;
		font-size: 14px;
		padding: 2px;
	}
	#tableDiv #thirdHead td, #tableDiv #helpTable table td,
	#tableDiv #bodyHead table tr>td{
		border-top: transparent;
	}
	#tableDiv #helpTable, #tableDiv #bodyHead, #tableDiv #bodyHead table,
	#tableDiv #bodyHead>tr>td,#tableDiv #bodyHead table>tr>td,
	#tableDiv #helpTable table{
		border: transparent;
	}
	#tableDiv #helpTable td, #tableDiv #bodyHead tr>td{
		padding: 0;
	}

	#tableDiv #bodyHead .firstBody tr td, #tableDiv #bodyHead .secondBody tr td,
	#tableDiv #bodyHead .thirdBody tr td{
		padding-top: 4px;
		padding-bottom: 4px;
	}
	#tableDiv #bodyHead table, #tableDiv #helpTable table{
		width: 1098px;
	}

	.addAbove{
		margin-left:10px;
		background-color: cyan;
	}
	.addBelow{
		background-color: cyan;
	}
	.remove{
		background-color: red;
	}
	
	#scrollableDiv{
		position:relative;
		height: 375px;
		width:1120px;
		overflow-y: auto;
	}
	
	#tableDiv{
		position: absolute;
		left: 430px;
	}
	
	#chooseDiv{
		background-color: orange;
		position: absolute;
		top:56px;
		left:0px;
		width:430px;
	}
	
	#chooseDiv table{
	position:relative;
	left: 5px;
	background-color:white;
	margin-top:5px;
	table-layout: fixed;
	border-spacing: 0;
	border-collapse: collapse;
	border: 2px solid black;
	}
	
	#chooseDiv table th,#chooseDiv table td{
	border: 2px solid black;
	padding: 2px;
	}
	
	#chooseDiv label{
	padding: 2px;
	padding-left: 4px;
	padding-rignt: 4px;
	}
	
	#chooseDiv tbody tr:hover{
	cursor: pointer;
	}
	#toolChooserDiv {
		overflow-y: auto;
		height: 660px;
	}
	
	#chooseDiv > input:checked + label, #chooseDiv > div{
	background-color:white;
	}
	
	#chooseDiv > div,
	#chooseDiv > input{display: none;}
	
	#chooseDiv > input:nth-of-type(1):checked ~ div:nth-of-type(1) { 
		display: block;
	}	

</style>
</head>
<body>
	<div id="rectangle"></div>
	<div id="chooseDiv">
		<input type="radio" name="tabRadio" id="materialRadio" checked="checked">
		<label for="materialRadio">Оснастка</label>
		
		<div id="toolChooserDiv">
			<table id="toolChooser">
				<thead>
					<tr>
						<th>Код</th>
						<th>Наименование</th>
						<th>ГОСТ</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${toolList}" var="tool">
					<tr>	
						<td class="toolCodeCell">${tool.toolCode}</td>
						<td class="toolNameCell">${tool.toolName}</td>
						<td class="toolStandartCell">${tool.toolStandart}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<header>
		<h1 style="display:inline;">Редактирование операционной карты</h1>&nbsp;&nbsp;&nbsp;&nbsp;
	</header>
	<div id="tableDiv">
	<form method="POST" action="${pageContext.request.contextPath}/editOperCard">
	<table id="firstHead">
		<tr>
			<td></td>
			<td style ="width: 250px">
				<input style ="width: 230px" type="text" name="docName" value="${applicationScope.techOper.linkedObjs[0].docName}"/>
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
				${applicationScope.techOper.assocObj.assocObj.prodSign}
			</td>
			<td rowspan="0"></td>
			<td rowspan="0" width="180px">
				<input style ="width: 160px" type="text" name="docSign" value="${applicationScope.techOper.linkedObjs[0].docSign}"/>
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
			${applicationScope.techOper.assocObj.assocObj.prodName}
			</td>
			<td rowspan="0" style ="width: 40px">
			<input style ="width: 30px;" type="text" name="docLetter" value="${applicationScope.techOper.linkedObjs[0].docLetter}"/>
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
							${applicationScope.techOper.typicOperation.operName}
						</td>
						<td>
							${applicationScope.techOper.assocObj.typicBlank.material.matName}
							${applicationScope.techOper.assocObj.typicBlank.material.matSort}
							${applicationScope.techOper.assocObj.typicBlank.material.matStandart}
						</td>
						<td>
							<input style ="width: 70px;" type="text" name="hardness" value="${applicationScope.techOper.assocObj.typicBlank.material.hardness}"/>
						</td>
						<td>
							${applicationScope.techOper.assocObj.assocObj.measureUnit.measUnitName}
						</td>
						<td>
							${applicationScope.techOper.assocObj.assocObj.prodWeight}
						</td>
						<td>
							${applicationScope.techOper.assocObj.typicBlank.profile} x ${applicationScope.techOper.assocObj.blankSize}
						</td>
						<td>
							${applicationScope.techOper.assocObj.blankWeight}
						</td>
						<td>
							${applicationScope.techOper.nspd}
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
							${applicationScope.techOper.equipment.eqCode}, 
							${applicationScope.techOper.equipment.eqName}
						</td>
						<td>
							<input style ="width: 220px;" type="text" name="programSign" value="${applicationScope.techOper.programSign}"/>
						</td>
						<td>
							<input style ="width: 50px;" type="text" name="mainTime" value="${applicationScope.techOper.mainTime}"/>
						</td>
						<td>
							<input style ="width: 50px;" type="text" name="assistTime" value="${applicationScope.techOper.assistTime}"/>
						</td>
						<td>
							${applicationScope.techOper.prepareTime}
						</td>
						<td>
							${applicationScope.techOper.unitTime}
						</td>
						<td>
							<input style ="width: 240px;" type="text" name="lcl" value="${applicationScope.techOper.lcl}"/>
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
		<c:forEach items="${applicationScope.techOper.techTransits}" var="techTran">
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="firstBody">
					<tr>
						<td style ="width: 40px">
							О <c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style="text-align:left; padding-left:4px;">
							<input class="tranNum" style ="width: 20px" type="text" name="tranNumber" value="${techTran.tranNumber}" readonly/>. 
							<input style ="width: 700px" type="text" name="tranDescrip" value="${techTran.tranDescrip}"/>
							<button type="button" class="addAbove">+Вверх</button>
							<button type="button" class="addBelow">+Вниз</button>
							<button type="button" class="remove">X</button>
						</td>
						<td style ="width: 60px">
							<input style ="width: 50px" type="text" name="tranMainTime" value="${techTran.tranMainTime}"/>
						</td>
						<td style ="width: 60px">
							<input style ="width: 50px" type="text" name="tranAssistTime" value="${techTran.tranAssistTime}"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<c:set var="i" value="${i+1}"/>
		<tr>
			<td>
				<table class="secondBody">
					<tr>
						<td style ="width: 40px">
						Т <c:if test="${i<10}">0</c:if>${i}
						</td>
						<td style="text-align:left; padding-left:4px;">
							<c:set var="tranTools" value=""/>
							<c:forEach items="${techTran.tools}" var="tool">
								<c:set var="tranTools" value="${tranTools}${tool.toolCode}, ${tool.toolName}, ${tool.toolStandart}; "/>	
							</c:forEach>
							<input style ="width: 1040px" type="text" name="toolsPile" value="${tranTools}"/>			
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
							<input style ="width: 70px" type="text" name="adjustPosNumber" value="${techTran.adjustPosNumber}"/>
						</td>
						<td style ="width: 120px">
							<input style ="width:110px" type="text" name="treatWidth" value="${techTran.treatWidth}"/>
						</td>
						<td style ="width: 80px">
							<input style ="width: 70px" type="text" name="strokeLength" value="${techTran.strokeLength}"/>
						</td>
						<td style ="width: 50px">
							<input style ="width: 40px" type="text" name="cutDepth" value="${techTran.cutDepth}"/>
						</td>
						<td style ="width: 50px">
							<input style ="width: 40px" type="text" name="strokeCount" value="${techTran.strokeCount}"/>
						</td>
						<td style ="width: 80px">
							<input style ="width: 70px" type="text" name="supValue" value="${techTran.supValue}"/>
						</td>
						<td style ="width: 60px">
							<input style ="width: 50px" type="text" name="rotSpeed" value="${techTran.rotSpeed}"/>
						</td>
						<td style ="width: 60px">
							<input style ="width: 50px" type="text" name="cutSpeed" value="${techTran.cutSpeed}"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</c:forEach>
	</table>
	</div>
	<br>
	<input type="submit" value="Изменить"/>	
	</form>
	</div>
</body>
</html>