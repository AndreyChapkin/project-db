$(document).ready(function(){
	var firstBody =$('#bodyHead tr:nth-child(2)').clone().css('background-color','greenyellow');
	firstBody.find('input').attr('value','');
	var secondBody=$('#bodyHead tr:nth-child(3)').clone().css('background-color','greenyellow');
	secondBody.find('input').attr('value','');
	var thirdBody=$('#bodyHead tr:nth-child(4)').clone().css('background-color','greenyellow');
	thirdBody.find('input').attr('value','');
	var strNum = parseInt($('#bodyHead tr:last td:first').text().substring(2))+1;
	var tranNumber = parseInt(firstBody.find('td:nth-child(2) input[name=tranNumber]').attr('value'));
	
	//ДОБАВИТЬ СВЕРХУ
	var actionAbove = function(){
		tranInd = $($('.addAbove')).index(this);
		selTran = $('#bodyHead').children('tbody').children('tr').eq(tranInd*3+1);
		var newO = firstBody.clone();
		selTran.before(newO).before(secondBody.clone()).before(thirdBody.clone());
		var num=1;
		$('.tranNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addTechTran',
			type: 'POST',
			data: {'inputId': ''+tranInd},
			success: function(text){
			}
		})
		newO.find('button.addAbove').off().on('click', actionAbove);
		newO.find('button.addBelow').on('click', actionBelow);
		newO.find('button.remove').off().on('click', remove);
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
		tranInd = $($('.addBelow')).index(this);
		selTran = $('#bodyHead').children('tbody').children('tr').eq(tranInd*3+1);
		var newO = firstBody.clone();
		selTran.next().next().after(thirdBody.clone()).after(secondBody.clone()).after(newO);
		var num=1;
		$('.tranNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addTechTran',
			type: 'POST',
			data: {'inputId': ''+(tranInd+1)},
			success: function(text){
			}
		})
		newO.find('button.addAbove').off().on('click', actionAbove);
		newO.find('button.addBelow').on('click', actionBelow);
		newO.find('button.remove').on('click', remove);
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
		tranInd = $($('.remove')).index(this);
		selTran = $('#bodyHead').children('tbody').children('tr').eq(tranInd*3+1);
		selTran.next().remove();
		selTran.next().remove();
		selTran.remove();
		var num=1;
		$('.tranNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addTechTran',
			type: 'POST',
			data: {'removeId': ''+tranInd},
			success: function(text){
			}
		})
		return false;
	}	
	$('.remove').click(remove);
	
	//Выделение строки в основной таблице
	var choosedAB = $('#bodyHead tr:nth-child(2)').clone();
	$('#bodyHead tr table td:first-child').off().click(function(event){
		choosedAB.css('background-color','transparent');
		choosedAB = $(event.target).parent();
		choosedAB.css('background-color','cyan');
	})
	// Для справочной таблицы оснастки-----------------------------
	$('#chooseDiv #toolChooser tr').click(function(event){
		var choosedTr = $(event.target).parent();
		var appendTool = choosedTr.find('.toolCodeCell').text()+', '+choosedTr.find('.toolNameCell').text()+', '+
		choosedTr.find('.toolStandartCell').text()+'; ';
		var choosedABTools = choosedAB.find('input[name=toolsPile]').val();
		choosedAB.find('input[name=toolsPile]').val(choosedABTools+appendTool);
	})
	
})