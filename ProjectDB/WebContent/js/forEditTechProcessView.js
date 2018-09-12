$(document).ready(function(){
	var firstBody = $('#bodyHead tr:nth-child(3)').clone().css('background-color','greenyellow');
	$(firstBody).find('input').attr('value','');
	var secondBody= $('#bodyHead tr:nth-child(4)').clone().css('background-color','greenyellow');
	$(secondBody).find('input').attr('value','');
	var strNum = parseInt($('#bodyHead tr:last td:first').text().substring(2))+1;
	var operInd;
	var selOper;
	
	//ДОБАВИТЬ СВЕРХУ
	var actionAbove = function(){
		operInd = $($('.addAbove')).index(this);
		selOper = $('#bodyHead').children('tbody').children('tr').eq(operInd*2+2);
		
		var newA = firstBody.clone();
		selOper.before(newA).before(secondBody.clone());
		$.ajax({
			url:'addTechOper',
			type: 'POST',
			data: {'inputId': ''+(operInd+1)},
			success: function(text){
			}
		})
		newA.find('button.addAbove').off().on('click', actionAbove);
		newA.find('button.addBelow').on('click', actionBelow);
		newA.find('button.remove').off().on('click', remove);
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
		return false;
	}
	$('.addAbove').click(actionAbove);
	
	//ДОБАВИТЬ СНИЗУ
	var actionBelow = function(){
		operInd = $($('.addBelow')).index(this);
		selOper = $('#bodyHead').children('tbody').children('tr').eq(operInd*2+2);
		
		var newA = firstBody.clone();
		selOper.next().after(secondBody.clone()).after(newA);
		$.ajax({
			url:'addTechOper',
			type: 'POST',
			data: {'inputId': ''+(operInd+2)},
			success: function(text){
			}
		})
		newA.find('button.addAbove').off().on('click', actionAbove);
		newA.find('button.addBelow').on('click', actionBelow);
		newA.find('button.remove').on('click', remove);
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
		return false;
	}
	$('.addBelow').click(actionBelow);
	
	//УДАЛИТЬ
	var remove = function(){
		operInd = $($('.remove')).index(this);
		selOper = $('#bodyHead').children('tbody').children('tr').eq(operInd*2+2);
		selOper.next().remove();
		selOper.remove();
		$.ajax({
			url:'addTechOper',
			type: 'POST',
			data: {'removeId': ''+(operInd+1)},
			success: function(text){
			}
		})
		return false;
	}	
	$('.remove').click(remove);
	// Для справочной таблицы материалов-----------------------------
	$('#chooseDiv #materialChooser tr').click(function(event){
		var choosedTr = $(event.target).parent();
		$('input[name=matCode]').val(choosedTr.find('.matCodeCell').text());
		$('input[name=matSort]').val(choosedTr.find('.matSortCell').text());
		$('input[name=matStandart]').val(choosedTr.find('.matStandartCell').text());
	})
	// Для справочной таблицы заготовок-----------------------------
	$('#chooseDiv #blankChooser tr').click(function(event){
		var choosedTr = $(event.target).parent();
		$('input[name=blankCode]').val(choosedTr.find('.blankCodeCell').text());
		$('input[name=typicSize]').val(choosedTr.find('.typicSizeCell').text());
		$('input[name=profile]').val(choosedTr.find('.profileCell').text());
		$('input[name=blankStandart]').val(choosedTr.find('.blankStandartCell').text());
	})
	// Для справочной таблицы операций-----------------------------
	$('#chooseDiv #typOperChooser tr').click(function(event){
		var choosedTr = $(event.target).parent();
		choosedAB.find('input[name=operCode]').val(choosedTr.find('.operCodeCell').text());
		choosedAB.find('input[name=operName]').val(choosedTr.find('.operNameCell').text());
	})
		//Выделение строки АБ в основной таблице
		var choosedAB = $('#bodyHead tr:nth-child(3)').clone();
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
		
	// Для справочной таблицы оборудования-----------------------------
	$('#chooseDiv #equipmentChooser tr').click(function(event){
		var choosedTr = $(event.target).parent();
		choosedAB.find('input[name=eqCode]').val(choosedTr.find('.eqCodeCell').text());
		choosedAB.find('input[name=eqName]').val(choosedTr.find('.eqNameCell').text());
	})

})