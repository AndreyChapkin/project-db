$(document).ready(function(){
	var firstBody =$('#bodyHead tr:nth-child(2)').clone().css('background-color','greenyellow');
	firstBody.find('input').attr('value','');
	firstBody.find('textarea').text('');
	var strNum = parseInt($('#bodyHead tr:last td:first').text())+1;
	
	//ДОБАВИТЬ СВЕРХУ
	var actionAbove = function(){
		paramInd = $($('.addAbove')).index(this);
		selParam = $('#bodyHead').children('tbody').children('tr').eq(paramInd+1);
		var newP = firstBody.clone();
		selParam.before(newP);
		var num=1;
		$('.paramNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addParamCtrl',
			type: 'POST',
			data: {'inputId': ''+paramInd},
			success: function(text){
			}
		})
		newP.find('button.addAbove').off().on('click', actionAbove);
		newP.find('button.addBelow').on('click', actionBelow);
		newP.find('button.remove').off().on('click', remove);
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
		paramInd = $($('.addBelow')).index(this);
		selParam = $('#bodyHead').children('tbody').children('tr').eq(paramInd+1);
		var newP = firstBody.clone();
		selParam.after(newP);
		var num=1;
		$('.paramNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addParamCtrl',
			type: 'POST',
			data: {'inputId': ''+(paramInd+1)},
			success: function(text){
			}
		})
		newP.find('button.addAbove').off().on('click', actionAbove);
		newP.find('button.addBelow').on('click', actionBelow);
		newP.find('button.remove').on('click', remove);
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
		paramInd = $($('.remove')).index(this);
		selParam = $('#bodyHead').children('tbody').children('tr').eq(paramInd+1);
		selParam.remove();
		var num=1;
		$('.paramNum').each(function(){
			$(this).attr('value',num++);
		})
		$.ajax({
			url:'addParamCtrl',
			type: 'POST',
			data: {'removeId': ''+paramInd},
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
		choosedTr.find('.toolStandartCell').text()+'\n';
		var choosedABTools = choosedAB.find('textarea').text();
		choosedAB.find('textarea').text(choosedABTools+appendTool);
	})
})