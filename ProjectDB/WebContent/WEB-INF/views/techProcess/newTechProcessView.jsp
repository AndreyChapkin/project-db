<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Новая маршрутная карта</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forNewTechProcessView.js"></script>
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
	#tableDiv  #firstHead{
		margin-top: 18px;
	}
	#tableDiv  #firstHead td,#tableDiv #secondHead td,
	#tableDiv  #secondInHelp tr:nth-child(2) td{
		border-bottom: transparent;
	}
	#tableDiv  #secondHead tr td:nth-child(-n+4),
	#tableDiv  #thirdHead tr td:nth-child(-n+4){
		text-align: left;
		font-size: 14px;
		padding: 2px;
	}
	#tableDiv #thirdHead td, #tableDiv #helpTable table td,
	#tableDiv #bodyHead table tr>td{
		border-top: transparent;
	}
	#tableDiv #helpTable,#tableDiv #bodyHead,#tableDiv #bodyHead table,
	#tableDiv #bodyHead>tr>td, #tableDiv #bodyHead table>tr>td{
		border: transparent;
	}
	#tableDiv #helpTable td,#tableDiv  #bodyHead tr>td{
		padding: 0;
	}
	#tableDiv #helpTable table{
		width: 926px;
		border: transparent;
	}

	#tableDiv #helpTable #secondInHelp tr:first-child td:nth-child(n+2){
		font-size: 14px;
	}
	#tableDiv #bodyHead .firstBody tr td,#tableDiv #bodyHead .secondBody tr td{
		padding-top: 4px;
		padding-bottom: 4px;
	}
	#bodyHead table{
		width: 1098px;
	}
	
	#scrollableDiv{
		position:relative;
		height: 415px;
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
	#materialChooserDiv, #blankChooserDiv,
	#typOperChooserDiv, #equipmentChooserDiv{
		overflow: auto;
		height: 660px;
	}
	
	#chooseDiv > input:checked + label, #chooseDiv > div{
	background-color:white;
	}
	
	#chooseDiv > div,
	#chooseDiv > input{display: none;}
	
	#chooseDiv > input:nth-of-type(1):checked ~ div:nth-of-type(1),
	#chooseDiv > input:nth-of-type(2):checked ~ div:nth-of-type(2),
	#chooseDiv > input:nth-of-type(3):checked ~ div:nth-of-type(3),
	#chooseDiv > input:nth-of-type(4):checked ~ div:nth-of-type(4) { 
		display: block;
	}	

</style>
</head>
<body>
	<div id="rectangle"></div>
	<div id="chooseDiv">
		<input type="radio" name="tabRadio" id="materialRadio" checked="checked">
		<label for="materialRadio">Материал </label>
		<input type="radio"  name="tabRadio" id="blankRadio">
		<label for="blankRadio">Заготовка </label>
		<input type="radio"  name="tabRadio" id="operationRadio">
		<label for="operationRadio">Операция </label>
		<input type="radio"  name="tabRadio" id="EquipmentRadio">
		<label for="EquipmentRadio"> Оборудование </label>
		<div id="materialChooserDiv">
			<table id="materialChooser">
				<thead>
					<tr>
						<th>Код</th>
						<th>Наимен.</th>
						<th>Марка</th>
						<th>ГОСТ</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${matList}" var="mat">
					<tr>	
						<td class="matCodeCell">${mat.matCode}</td>
						<td class="matNameCell">${mat.matName}</td>
						<td class="matSortCell">${mat.matSort}</td>
						<td class="matStandartCell">${mat.matStandart}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="blankChooserDiv">
			<table id="blankChooser">
				<thead>
					<tr>
						<th>Код</th>
						<th>Наимен.</th>
						<th>Типоразмер</th>
						<th>Профиль</th>
						<th>ГОСТ</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${blankList}" var="blank">
					<tr>	
						<td class="blankCodeCell">${blank.blankCode}</td>
						<td class="blankNameCell">${blank.blankName}</td>
						<td class="typicSizeCell">${blank.typicSize}</td>
						<td class="profileCell">${blank.profile}</td>
						<td class="blankStandartCell">${blank.blankStandart}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="typOperChooserDiv">
			<table id="typOperChooser">
				<thead>
					<tr>
						<th>Код</th>
						<th>Наименование</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${typOperList}" var="typOper">
					<tr>	
						<td class="operCodeCell">${typOper.operCode}</td>
						<td class="operNameCell">${typOper.operName}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="equipmentChooserDiv">
			<table id="equipmentChooser">
				<thead>
					<tr>
						<th>Код</th>
						<th>Наименование</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${equipList}" var="equip">
					<tr>	
						<td class="eqCodeCell">${equip.eqCode}</td>
						<td class="eqNameCell">${equip.eqName}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${parentId == 0 or parentId == null}">
			<header>
				<h1 style="display:inline;">Создание новой маршрутной карты</h1>&nbsp;&nbsp;&nbsp;&nbsp;
			</header>
			<div id="tableDiv">
			<form method="POST" action="${pageContext.request.contextPath}/newTechProc">
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
							${applicationScope.tpProd.prodSign}
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
						${applicationScope.tpProd.prodName}
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
									<td style ="width: 40px">М 01</td>
									<td>
									<input style ="width: 160px" type="text" name="typicSize" value="Типоразмер"/>
									<input style ="width: 160px" type="text" name="blankStandart" value="ГОСТ загот-ки"/>/
									<input style ="width: 160px" type="text" name="matSort" value="Марка мат-ла"/>
									<input style ="width: 160px" type="text" name="matStandart" value="ГОСТ мат-ла"/>
									</td>
								</tr>
							</table>
						</td>
						<td rowspan="0" style ="width: 170px; border-right: 3px solid black;">
							<textarea style ="width:160px; resize:none;" rows=3 name="specInstruction">Особые указания</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<table id="secondInHelp">
								<tr>
									<td style ="width: 40px; border-bottom: transparent;" rowspan="0">М 02</td>
									<td style ="width: 140px">Код</td>
									<td style ="width: 50px">ЕВ</td>
									<td style ="width: 58px">МД</td>
									<td style ="width: 30px">ЕН</td>
									<td style ="width: 50px">Н.расх.</td>
									<td style ="width: 38px">КИМ</td>
									<td style ="width: 80px">Код загот.</td>
									<td>Профиль и размеры</td>
									<td style ="width: 30px">КД</td>
									<td style ="width: 58px">МЗ</td>
								</tr>
								<tr>
									<td>
									<input style ="width: 120px" type="text" name="matCode"/>
									</td>
									<td>
									${applicationScope.tpProd.measureUnit.measUnitName}
									</td>
									<td>
									${applicationScope.tpProd.prodWeight}
									</td>
									<td>
									<input style ="width: 25px" type="text" name="normUnit"/>
									</td>
									<td>
									<input style ="width: 40px" type="text" name="materialConsump"/>
									</td>
									<td>
									<input style ="width: 32px" type="text" name="materialUseCoef"/>
									</td>
									<td>
									<input style ="width: 70px" type="text" name="blankCode"/>
									</td>
									<td>
									<input style ="width: 100px" type="text" name="profile"/> x
									<input style ="width: 100px" type="text" name="blankSize"/>
									</td>
									<td>
									<input style ="width: 22px" type="text" name="detailCount"/>
									</td>
									<td>
									<input style ="width: 50px" type="text" name="blankWeight"/>
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
									<td style ="width: 40px">А</td>
									<td style ="width: 45px">Цех</td>
									<td style ="width: 45px">Уч.</td>
									<td style ="width: 45px">РМ</td>
									<td style ="width: 45px">Опер.</td>
									<td style ="width: 290px">Код, наименование операции</td>
									<td>Обозначение документа</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table id="secondBodyHead">
								<tr>
									<td style ="width: 40px">Б</td>
									<td style ="width: 476px">Код, наименование оборудования</td>
									<td style ="width: 40px">СМ</td>
									<td>Проф.</td>
									<td style ="width: 50px">Р</td>
									<td style ="width: 50px">УТ</td>
									<td style ="width: 50px">КР</td>
									<td style ="width: 50px">КОИД</td>
									<td style ="width: 50px">ЕН</td>
									<td style ="width: 50px">ОП</td>
									<td style ="width: 50px">Кшт.</td>
									<td style ="width: 50px">Тпз</td>
									<td style ="width: 50px">Tшт.</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table class="firstBody">
								<tr>
									<td style ="width: 40px">A 03</td>
									<td style ="width: 45px">
										<input style ="width: 35px" type="text" name="shopNumber"/>
									</td>
									<td style ="width: 45px">
										<input style ="width: 35px" type="text" name="areaNumber"/>
									</td>
									<td style ="width: 45px">
										<input style ="width: 35px" type="text" name="placeNumber"/>
									</td>
									<td style ="width: 45px">
										<input style ="width: 35px" type="text" name="operNumber"/>
									</td>
									<td style ="width: 286px; text-align:left; padding-left:4px;">
										<input style ="width: 40px" type="text" name="operCode"/>
										<input style ="width: 210px" type="text" name="operName"/>
									</td>
									<td style="text-align:left; padding-left:4px;">
										<input style ="width: 240px" type="text" name="appDocSign"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr style="display:none">
						<td>
							<table class="secondBody">
								<tr>
									<td style ="width: 40px">Б 04</td>
									<td style ="width: 472px; text-align:left; padding-left:4px;">
										<input style ="width: 50px" type="text" name="eqCode"/>
										<input style ="width: 220px" type="text" name="eqName"/>
									</td>
									<td style ="width: 40px">
										<input style ="width: 30px" type="text" name="mechCode"/>
									</td>
									<td>
										<input style ="width: 45px" type="text" name="profCode"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 35px" type="text" name="workCategory"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 15px" type="text" name="workCode"/>
										<input style ="width: 15px" type="text" name="normSign"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="perfNumber"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="nspd"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="operNormUnit"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="prodBatchValue"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="unitTimeCoef"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="prepareTime"/>
									</td>
									<td style ="width: 50px">
										<input style ="width: 40px" type="text" name="unitTime"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</div>
				<br>
				<input type="submit" value="Создать"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="newOperation" value="+операция"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="newEquipment" value="+оборудование"/>
			</form>
		</div>
		</c:when>
		<c:otherwise>
			<header>
				<h1 style="display:inline;">Создание новой версии маршрутной карты</h1>&nbsp;&nbsp;&nbsp;&nbsp;
			</header>
			<div id="tableDiv">
			<form method="POST" action="${pageContext.request.contextPath}/newTechProc">
				<table id="firstHead">
					<tr>
						<td></td>
						<td style ="width: 250px">
							<input style ="width: 230px" type="text" name="docName" value="${parentTechProc.linkedObjs[0].docName}"/>
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
						<td>Фамилия</td>
						<td>Подпись</td>
						<td>23.23.13</td>
						<td rowspan="0"></td>
						<td rowspan="0" width="180px">
							${parentTechProc.assocObj.prodSign}
						</td>
						<td rowspan="0"></td>
						<td rowspan="0" width="180px">
						<input style ="width: 160px" type="text" name="docSign" value="${parentTechProc.linkedObjs[0].docSign}"/>
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
						${parentTechProc.assocObj.prodName}
						</td>
						<td rowspan="0" style ="width: 40px">
						<input style ="width: 30px;" type="text" name="docLetter" value="${parentTechProc.linkedObjs[0].docLetter}"/>
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
									<td style ="width: 40px">М 01</td>
									<td>
									<input style ="width: 160px" type="text" name="typicSize" value="${parentTechProc.typicBlank.typicSize}"/>
									<input style ="width: 160px" type="text" name="blankStandart" value="${parentTechProc.typicBlank.blankStandart}"/>/
									<input style ="width: 160px" type="text" name="matSort" value="${parentTechProc.typicBlank.material.matSort}"/>
									<input style ="width: 160px" type="text" name="matStandart" value="${parentTechProc.typicBlank.material.matStandart}"/>
									</td>
								</tr>
							</table>
						</td>
						<td rowspan="0" style ="width: 170px; border-right: 3px solid black;">
							<textarea style ="width:160px; resize:none;" rows=3 name="specInstruction">${parentTechProc.specInstruction}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<table id="secondInHelp">
								<tr>
									<td style ="width: 40px; border-bottom: transparent;" rowspan="0">М 02</td>
									<td style ="width: 140px">Код</td>
									<td style ="width: 50px">ЕВ</td>
									<td style ="width: 58px">МД</td>
									<td style ="width: 30px">ЕН</td>
									<td style ="width: 50px">Н.расх.</td>
									<td style ="width: 38px">КИМ</td>
									<td style ="width: 80px">Код загот.</td>
									<td>Профиль и размеры</td>
									<td style ="width: 30px">КД</td>
									<td style ="width: 58px">МЗ</td>
								</tr>
								<tr>
									<td>
									<input style ="width: 120px" type="text" name="matCode" value="${parentTechProc.typicBlank.material.matCode}"/>
									</td>
									<td>
									${parentTechProc.assocObj.measureUnit.measUnitName}
									</td>
									<td>
									${parentTechProc.assocObj.prodWeight}
									</td>
									<td>
									<input style ="width: 25px" type="text" name="normUnit" value="${parentTechProc.normUnit}"/>
									</td>
									<td>
									<input style ="width: 40px" type="text" name="materialConsump" value="${parentTechProc.materialConsump}"/>
									</td>
									<td>
									<input style ="width: 32px" type="text" name="materialUseCoef" value="${parentTechProc.materialUseCoef}"/>
									</td>
									<td>
									<input style ="width: 70px" type="text" name="blankCode" value="${parentTechProc.typicBlank.blankCode}"/>
									</td>
									<td>
									<input style ="width: 100px" type="text" name="profile" value="${parentTechProc.typicBlank.profile}"/> x
									<input style ="width: 100px" type="text" name="blankSize" value="${parentTechProc.blankSize}"/>
									</td>
									<td>
									<input style ="width: 22px" type="text" name="detailCount" value="${parentTechProc.detailCount}"/>
									</td>
									<td>
									<input style ="width: 50px" type="text" name="blankWeight" value="${parentTechProc.blankWeight}"/>
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
									<td style ="width: 40px">А</td>
									<td style ="width: 45px">Цех</td>
									<td style ="width: 45px">Уч.</td>
									<td style ="width: 45px">РМ</td>
									<td style ="width: 45px">Опер.</td>
									<td style ="width: 290px">Код, наименование операции</td>
									<td>Обозначение документа</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table id="secondBodyHead">
								<tr>
									<td style ="width: 40px">Б</td>
									<td style ="width: 476px">Код, наименование оборудования</td>
									<td style ="width: 40px">СМ</td>
									<td>Проф.</td>
									<td style ="width: 50px">Р</td>
									<td style ="width: 50px">УТ</td>
									<td style ="width: 50px">КР</td>
									<td style ="width: 50px">КОИД</td>
									<td style ="width: 50px">ЕН</td>
									<td style ="width: 50px">ОП</td>
									<td style ="width: 50px">Кшт.</td>
									<td style ="width: 50px">Тпз</td>
									<td style ="width: 50px">Tшт.</td>
								</tr>
							</table>
						</td>
					</tr>
					<c:set var="i" value="2"/>
					<c:forEach begin="1" items="${parentTechProc.linkedObjs}" var="techOper">
					<c:set var="i" value="${i+1}"/>
						<tr>
							<td>
								<table class="firstBody">
									<tr>
										<td style ="width: 40px">
											
											A <c:if test="${i<10}">0</c:if>${i}
										</td>
										<td style ="width: 45px">
											<input style ="width: 35px" type="text" name="shopNumber" value="${techOper.shopNumber}"/>
										</td>
										<td style ="width: 45px">
											<input style ="width: 35px" type="text" name="areaNumber" value="${techOper.areaNumber}"/>
										</td>
										<td style ="width: 45px">
											<input style ="width: 35px" type="text" name="placeNumber" value="${techOper.placeNumber}"/>
										</td>
										<td style ="width: 45px">
											<input style ="width: 35px" type="text" name="operNumber" value="${techOper.operNumber}"/>
										</td>
										<td style ="width: 286px; text-align:left; padding-left:4px;">
											<input style ="width: 40px" type="text" name="operCode" value="${techOper.typicOperation.operCode}"/>,
											<input style ="width: 210px" type="text" name="operName" value="${techOper.typicOperation.operName}"/>
										</td>
										<td style="text-align:left; padding-left:4px;">
											<input style ="width: 240px" type="text" name="appDocSign" value="${techOper.appDocSign}"/>
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
										<td style ="width: 40px">Б <c:if test="${i<10}">0</c:if>${i}</td>
										<td style ="width: 472px; text-align:left; padding-left:4px;">
											<input style ="width: 50px" type="text" name="eqCode" value="${techOper.equipment.eqCode}"/>,
											<input style ="width: 220px" type="text" name="eqName" value="${techOper.equipment.eqName}"/>
										</td>
										<td style ="width: 40px">
											<input style ="width: 30px" type="text" name="mechCode" value="${techOper.mechDegree.mechCode}"/>
										</td>
										<td>
											<input style ="width: 45px" type="text" name="profCode" value="${techOper.profession.profCode}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 35px" type="text" name="workCategory" value="${techOper.workCategory}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 15px" type="text" name="workCode" value="${techOper.workCondition.workCode}"/>
											<input style ="width: 15px" type="text" name="normSign" value="${techOper.timeNorm.normSign}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="perfNumber" value="${techOper.perfNumber}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="nspd" value="${techOper.nspd}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="operNormUnit" value="${techOper.operNormUnit}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="prodBatchValue" value="${techOper.prodBatchValue}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="unitTimeCoef" value="${techOper.unitTimeCoef}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="prepareTime" value="${techOper.prepareTime}"/>
										</td>
										<td style ="width: 50px">
											<input style ="width: 40px" type="text" name="unitTime" value="${techOper.unitTime}"/>
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
				<input type="button" id="newOperation" value="+операция"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="newEquipment" value="+оборудование"/>
			</form>
		</div>
		</c:otherwise>
	</c:choose>
</body>
</html>