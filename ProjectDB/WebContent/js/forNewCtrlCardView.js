$(document).ready(function(){
	var firstBody =$('#bodyHead tr:nth-child(2)').clone();
	var prevent;
	var insertfirstBody;
	var strNum = parseInt($('#bodyHead tr:last td:first').text())+1;
	var paramNumber = parseInt($('#bodyHead>tbody>tr:last-child').find('td:nth-child(2) input[name=paramNumber]').attr('value'));
	
	$('#newParam').click(function(){
		if(strNum<10)
			firstBody.find('tr>td:first-child').text('0'+strNum);
		else
			firstBody.find('tr>td:first-child').text(+strNum);
		strNum++;
		insertfirstBody = firstBody.clone();
		insertfirstBody.find('td:nth-child(2) input[name=paramNumber]').attr('value',++paramNumber);
		insertfirstBody.appendTo('#bodyHead');
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
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
		choosedTr.find('.toolStandartCell').text()+'\n';
		var choosedABTools = choosedAB.find('textarea').text();
		choosedAB.find('textarea').text(choosedABTools+appendTool);
	})
})