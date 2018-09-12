$(document).ready(function(){
	if($('header h1').text() == 'Создание новой маршрутной карты'){
		var firstBody =$('#bodyHead tr:nth-child(3)').clone();
		var secondBody=$('#bodyHead tr:nth-child(4)').detach().show();
	}
	else{
		var firstBody =$('#bodyHead tr:nth-child(3)').clone();
		firstBody.find('input').val('');
		var secondBody=$('#bodyHead tr:nth-child(4)').clone();
		secondBody.find('input').val('');
	}
	var prevent;
	//Добавление операции
	var strNum = parseInt($('#bodyHead tr:last td:first').text().substring(2))+1;
	$('#newOperation').click(function(){
		if(strNum<10)
			$(firstBody).find('tr>td:first-child').text('A 0'+strNum);
		else
			$(firstBody).find('tr>td:first-child').text('A '+strNum);
		strNum++;
		prevent = $($(firstBody).clone()).appendTo('#bodyHead').prev();
		if($(prevent).find('tr td:first').text()[0] == 'A')
			$($(secondBody).clone().css('display','none')).insertAfter(prevent);
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
	})
	//Добавление оборудования
	$('#newEquipment').click(function(){
		if(strNum<10)
			$(secondBody).find('tr>td:first-child').text('Б 0'+strNum);
		else
			$(secondBody).find('tr>td:first-child').text('Б '+strNum);
		strNum++;
		$($(secondBody).clone()).appendTo('#bodyHead');
		//Для новых строк
		$('#bodyHead tr table td:first-child').off().click(function(event){
			choosedAB.css('background-color','transparent');
			choosedAB = $(event.target).parent();
			choosedAB.css('background-color','cyan');
		})
	})
	//Добавление в конец строки Б перед отправкой формы
	$('input[type=submit]').mousedown(function(){
		if($('#bodyHead tr:last td:first').text()[0] == 'A'){	
			$($(secondBody).clone().css('display','none')).appendTo('#bodyHead');
		}
	})
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