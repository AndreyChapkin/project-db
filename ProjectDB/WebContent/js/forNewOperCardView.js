$(document).ready(function(){
	var firstBody =$('#bodyHead tr:nth-child(2)').clone();
	var secondBody=$('#bodyHead tr:nth-child(3)').clone();
	var thirdBody=$('#bodyHead tr:nth-child(4)').clone();
	var prevent;
	var insertfirstBody;
	var strNum = parseInt($('#bodyHead tr:last td:first').text().substring(2))+1;
	var tranNumber = parseInt(firstBody.find('td:nth-child(2) input[name=tranNumber]').attr('value'));
	//Добавление нового перехода
	$('#newTransit').click(function(){
		if(strNum<10)
			firstBody.find('tr>td:first-child').text('О 0'+strNum);
		else
			firstBody.find('tr>td:first-child').text('О '+strNum);
		strNum++;
		insertfirstBody = firstBody.clone();
		insertfirstBody.find('td:nth-child(2) input[name=tranNumber]').attr('value',++tranNumber);
		prevent = insertfirstBody.appendTo('#bodyHead').prev();
		if(prevent.find('tr td:first').text()[0] == 'О'){
			thirdBody.clone().css('display','none').insertAfter(prevent);
			secondBody.clone().css('display','none').insertAfter(prevent);
		}
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
	})
	//Добавление оснастки и режимов резания
	$('#newTools').click(function(){
		if(strNum<10){
			secondBody.find('tr>td:first-child').text('Т 0'+strNum);
			strNum++;
			thirdBody.find('tr>td:first-child').text('Р 0'+strNum);
		}
		else{
			secondBody.find('tr>td:first-child').text('Т '+strNum);
			strNum++;
			thirdBody.find('tr>td:first-child').text('Р '+strNum);
		}
		strNum++;
		secondBody.clone().appendTo('#bodyHead');
		thirdBody.clone().appendTo('#bodyHead');
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
	})
	//Корректировка конца таблицы перед отправкой формы
	$('input[type=submit]').mousedown(function(){
		if($('#bodyHead tr:last td:first').text()[0] == 'О'){	
			secondBody.clone().css('display','none').appendTo('#bodyHead');
			thirdBody.clone().css('display','none').appendTo('#bodyHead');
		}
	})
	
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