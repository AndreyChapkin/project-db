<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Новая карта контроля</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forNewCtrlCardView.js"></script>
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
	
	input[type=submit], input[type=button]{
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
	#tableDiv #firstHead td,#tableDiv #secondHead td,
	#tableDiv #secondInHelp tr:nth-child(2) td{
		border-bottom: transparent;
	}
	#tableDiv #secondHead tr td:nth-child(-n+4),
	#tableDiv #thirdHead tr td:nth-child(-n+4){
		text-align: left;
		font-size: 14px;
		padding: 2px;
	}
	#tableDiv #thirdHead td, #helpTable table td,
	#tableDiv #bodyHead table tr>td{
		border-top: transparent;
	}
	#tableDiv #helpTable,#tableDiv #bodyHead,#tableDiv #bodyHead table,
	#tableDiv #bodyHead>tr>td,#tableDiv #bodyHead table>tr>td,
	#tableDiv #helpTable table{
		border: transparent;
	}
	#tableDiv #helpTable td,#tableDiv #bodyHead tr>td{
		padding: 0;
	}

	#tableDiv #bodyHead .firstBody tr td,#tableDiv #bodyHead .secondBody tr td{
		padding-top: 4px;
		padding-bottom: 4px;
	}
	#tableDiv #bodyHead table,#tableDiv #helpTable table{
		width: 1098px;
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
	<c:choose>
		<c:when test="${parentId == 0 or parentId == null}">
			<header>
				<h1 style="display:inline;">Создание новой карты контроля</h1>&nbsp;&nbsp;&nbsp;&nbsp;
				
			</header>
			<div id="tableDiv">
			<form method="POST" action="${pageContext.request.contextPath}/newCtrlCard">
				<table id="firstHead">
					<tr>
						<td></td>
						<td style ="width: 250px">
							<input style ="width: 230px" type="text" name="docName" value="Наименование документа"/>
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
						<input style ="width: 160px" type="text" name="docSign" value="Обозначение документа"/>
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
						<input style ="width: 30px;" type="text" name="docLetter" value="Л-ра"/>
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
									<td>${applicationScope.techOper.typicOperation.operCode}, 
										${applicationScope.techOper.typicOperation.operName}
									</td>
									<td>
										${applicationScope.techOper.assocObj.typicBlank.material.matName} 
										,${applicationScope.techOper.assocObj.typicBlank.material.matSort}
									</td>
									<td>
										${applicationScope.techOper.assocObj.assocObj.prodWeight}
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
										${applicationScope.techOper.equipment.eqCode}, 
										${applicationScope.techOper.equipment.eqName}
									</td>
									<td>
										<input style ="width: 50px;" type="text" name="mainTime"/>
									</td>
									<td>
										<input style ="width: 50px;" type="text" name="assistTime"/>
									</td>
									<td></td>
									<td>
										<input style ="width: 150px;" type="text" name="instructSign"/>
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
					<tr>
						<td>
							<table class="firstBody">
								<tr>
									<td style ="width: 40px">01</td>
									<td style ="width: 310px; text-align:left;">
										<input style ="width: 20px; margin-left:5px;" type="text" name="paramNumber" value="1" readonly/>.
										<input style ="width: 250px;" type="text" name="parameter"/>
									</td>
									<td style ="width: 200px">
									</td>
									<td >
										<textarea id="ddd" rows="3" style ="width: 360px; resize: none" type="text" name="devicesPile"></textarea>
									</td>
									<td style ="width: 90px">
										<input style ="width: 70px;" type="text" name="valuePC"/>
									</td>
									<td style ="width: 70px">
										<input style ="width: 50px;" type="text" name="timesRatio"/>
									</td>
								</tr>	
							</table>
						</td>
					</tr>
				</table>
				</div>
				<br>
				<input type="submit" value="Создать"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="newParam" value="+параметр"/>		
			</form>
			</div>
		</c:when>	
		<c:otherwise>
			<header>
				<h1 style="display:inline;">Создание новой версии карты контроля</h1>
			</header>
			<div id="tableDiv">
			<form method="POST" action="${pageContext.request.contextPath}/newCtrlCard">
				<table id="firstHead">
					<tr>
						<td></td>
						<td style ="width: 250px">
							<input style ="width: 230px" type="text" name="docName" value="${parentTechCtrl.linkedObjs[0].docName}"/>
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
							${parentTechCtrl.assocObj.assocObj.assocObj.prodSign}
						</td>
						<td rowspan="0"></td>
						<td rowspan="0" width="180px">
						<input style ="width: 160px" type="text" name="docSign" value="${parentTechCtrl.linkedObjs[0].docSign}"/>
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
						${parentTechCtrl.assocObj.assocObj.assocObj.prodName}
						</td>
						<td rowspan="0" style ="width: 40px">
						<input style ="width: 30px;" type="text" name="docLetter" value="${parentTechCtrl.linkedObjs[0].docLetter}"/>
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
									<td>${applicationScope.techOper.typicOperation.operCode}, 
										${applicationScope.techOper.typicOperation.operName}
									</td>
									<td>
										${applicationScope.techOper.assocObj.typicBlank.material.matName} 
										, ${applicationScope.techOper.assocObj.typicBlank.material.matSort}
									</td>
									<td>
										${applicationScope.techOper.assocObj.assocObj.prodWeight}
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
										${applicationScope.techOper.equipment.eqCode}, 
										${applicationScope.techOper.equipment.eqName}
									</td>
									<td>
										<input style ="width: 50px;" type="text" name="mainTime" value="${parentTechCtrl.assocObj.mainTime}"/>
									</td>
									<td>
										<input style ="width: 50px;" type="text" name="assistTime" value="${parentTechCtrl.assocObj.assistTime}"/>
									</td>
									<td></td>
									<td>
										<input style ="width: 150px;" type="text" name="instructSign" value="${parentTechCtrl.intsructSign}"/>
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
					<c:forEach items="${parentTechCtrl.paramControls}" var="paramCtrl">
					<c:set var="i" value="${i+1}"/>
					<tr>
						<td>
							<table class="firstBody">
								<tr>
									<td style ="width: 40px">
										<c:if test="${i<10}">0</c:if>${i}
									</td>
									<td style ="width: 305px; text-align:left; padding-left:5px;">
										<input class="paramNum" style ="width: 20px; margin-left:5px;" type="text" name="paramNumber" value="${paramCtrl.paramNumber}" readonly/>.
										<input style ="width: 250px;" type="text" name="parameter" value="${paramCtrl.parameter}"/>
									</td>
									<td style ="width: 200px">
									</td>
									<td>
										<textarea id="ddd" rows="3" style ="width: 360px; resize: none" type="text" name="devicesPile"><c:forEach items="${paramCtrl.ctrlDevices}" var="device">${device.toolCode}, ${device.toolName}, ${device.toolStandart}&#13;&#10;</c:forEach></textarea>
									</td>
									<td style ="width: 90px">
										<input style ="width: 70px;" type="text" name="valuePC" value="${paramCtrl.valuePC}"/>
									</td>
									<td style ="width: 70px">
										<input style ="width: 50px;" type="text" name="timesRatio" value="${paramCtrl.timesRatio}"/>
									</td>
								</tr>	
							</table>
						</td>
					</tr>
					</c:forEach>
				</table>
				</div>
				<br>
				<input type="submit" value="Создать"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="newParam" value="+параметр"/>	
			</form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>