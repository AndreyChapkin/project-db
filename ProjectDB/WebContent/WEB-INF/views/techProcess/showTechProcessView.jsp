<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Маршрутная карта</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forShowTechProcessView.js"></script>
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
		position: relative;
		text-align: center;
		border: 2px solid black;
		padding: 2px;
		height: 12px;
	}
	#firstHead{
		margin-top: 5px;
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
	#bodyHead table tr>td,
	#bodyTable table tr>td{
		border-top: transparent;
	}
	#control, #control td, #helpTable, #bodyHead,
	#bodyHead table, #bodyHead table>tr>td,
	#bodyTable,
	#bodyTable table, #bodyTable>tr>td,#bodyTable table>tr>td{
		border: transparent;
	}
	#bodyHead>tbody>tr>td{
		border-bottom: transparent;
	}
	#helpTable td, #bodyHead tr>td, #bodyTable tr>td{
		padding: 0;
	}
	#helpTable table{
		width: 926px;
		border: transparent;
	}

	#helpTable #secondInHelp tr:first-child td:nth-child(n+2){
		font-size: 14px;
	}
	#bodyTable .firstBody tr td,#bodyTable .secondBody tr td{
		padding-top: 4px;
		padding-bottom: 4px;
	}
	#bodyTable .firstBody tr td,#bodyTable .secondBody tr td{
		height: 20px;
	}
	#bodyHead table,
	#bodyTable table{
		width: 1098px;
	}
	.firstBody tr:hover, .secondBody tr:hover{
		background-color: yellow;
	}
	.firstBody tr td:first-child:hover{
		background-color: red;
		cursor: pointer;
	}
	.showOperCard{
		position:relative;
		left: 20px;
		display: inline;
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
	
	.showOperCard{
	position: absolute;
	left: 450px;
	top: 3px;
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
		<h1>Маршрутная карта</h1>
	</header>
	<div id="tableDiv">
	<table id="control">
		<tr>
			<td style="text-align:right">
				<c:if test="${sessionScope.user.editRight eq true}">
				<a href="${pageContext.request.contextPath}/editTechProc?techProcId=${techProc.objId}">Редактировать</a>
				<a href="${pageContext.request.contextPath}/delTechProc?techProcId=${techProc.objId}" style="margin-left:30px; background-color: red">Удалить</a>
				</c:if>
			</td>
		</tr>
	</table>
	<table id="firstHead">
		<tr>
			<td></td>
			<td style ="width: 250px">
				${techProc.linkedObjs[0].docName}
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
			<td>${techProc.creator.surname}</td>
			<td></td>
			<td><fmt:formatDate dateStyle="short" value = "${techProc.createTime}"/></td>
			<td rowspan="0"></td>
			<td rowspan="0" width="180px">
				${techProc.assocObj.prodSign}
			</td>
			<td rowspan="0"></td>
			<td rowspan="0" width="180px">
				${techProc.linkedObjs[0].docSign}
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
			${techProc.assocObj.prodName}
			</td>
			<td rowspan="0" style ="width: 40px">
				${techProc.linkedObjs[0].docLetter}
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
						${techProc.typicBlank.typicSize}&nbsp;&nbsp;
						${techProc.typicBlank.blankStandart}&nbsp;&nbsp;
						${techProc.typicBlank.material.matSort}&nbsp;&nbsp;
						${techProc.typicBlank.material.matStandart}&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td rowspan="0" style ="width: 170px; border-right: 3px solid black;">
				${techProc.specInstruction}
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
						${techProc.typicBlank.material.matCode}
						</td>
						<td>
						${techProc.assocObj.measureUnit.measUnitName}
						</td>
						<td>
						${techProc.assocObj.prodWeight}
						</td>
						<td>
						${techProc.normUnit}
						</td>
						<td>
						${techProc.materialConsump}
						</td>
						<td>
						${techProc.materialUseCoef}
						</td>
						<td>
						${techProc.typicBlank.blankCode}
						</td>
						<td>
						${techProc.typicBlank.profile} x
						${techProc.blankSize}
						</td>
						<td>
						${techProc.detailCount}
						</td>
						<td>
						${techProc.blankWeight}
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
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
		</table>
		<div id="scrollableDiv">
		<table id="bodyTable">
		<c:set var="i" value="2"/>
		<c:forEach begin="1" items="${techProc.linkedObjs}" var="techOper">
		<c:set var="i" value="${i+1}"/>
			<tr>
				<td>
					<table class="firstBody">
						<tr>
							<td style ="width: 40px">
								A <c:if test="${i<10}">0</c:if>${i}
							</td>
							<td style ="width: 45px">
								${techOper.shopNumber}
							</td>
							<td style ="width: 45px">
								${techOper.areaNumber}
							</td>
							<td style ="width: 45px">
								${techOper.placeNumber}
							</td>
							<td style ="width: 45px">
								${techOper.operNumber}
							</td>
							<td style ="width: 286px; text-align:left; padding-left:4px;">
								${techOper.typicOperation.operCode},
								${techOper.typicOperation.operName}
							</td>
							<td style="text-align:left; padding-left:4px;">
								${techOper.appDocSign}
								
								<div class="showOperCard">
								<c:choose>
									<c:when test="${techOper.typicOperation.operName eq 'Контрольная'}">
										<a href="${pageContext.request.contextPath}/ctrlCardList?techOperId=${techOper.objId}">Карты контроля</a>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/operCardList?techOperId=${techOper.objId}">Опер. карты</a>&nbsp;	
									</c:otherwise>
								</c:choose>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test="${techOper.equipment.eqName ne null}">
			<c:set var="i" value="${i+1}"/>
			<tr>
				<td>
					<table class="secondBody">
						<tr>
							<td style ="width: 40px">Б <c:if test="${i<10}">0</c:if>${i}</td>
							<td style ="width: 472px; text-align:left; padding-left:4px;">
								${techOper.equipment.eqCode},
								${techOper.equipment.eqName}
							</td>
							<td style ="width: 40px">
								${techOper.mechDegree.mechCode}
							</td>
							<td>
								${techOper.profession.profCode}
							</td>
							<td style ="width: 50px">
								${techOper.workCategory}
							</td>
							<td style ="width: 50px">
								${techOper.workCondition.workCode}
								${techOper.timeNorm.normSign}
							</td>
							<td style ="width: 50px">
								${techOper.perfNumber}
							</td>
							<td style ="width: 50px">
								${techOper.nspd}
							</td>
							<td style ="width: 50px">
								${techOper.operNormUnit}
							</td>
							<td style ="width: 50px">
								${techOper.prodBatchValue }
							</td>
							<td style ="width: 50px">
								${techOper.unitTimeCoef}
							</td>
							<td style ="width: 50px">
								${techOper.prepareTime}
							</td>
							<td style ="width: 50px">
								${techOper.unitTime}
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