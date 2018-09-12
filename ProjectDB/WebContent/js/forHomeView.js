$(document).ready(function(){
	var changeText = 'См. входящие';
	$('#newMesButton').click(function(){
		$('#sendMesForm').slideToggle();
	})
	$('#changeView').click(function(){
		var buffer = $(this).text();
		$(this).text(changeText);
		changeText = buffer;
		$('#scrollableDiv').slideToggle();
		$('#outScrollableDiv').slideToggle();
	})
	$('.removeMes').click( function(){
		removeId = $(this).attr('id');		
		$(this).parent().remove();
		$.ajax({
			url:'removeMes',
			type: 'POST',
			data: {'removeId': ''+removeId},
			success: function(text){
			}
		})
		return false;
	});
	$('.removeOutMes').click( function(){
		removeId = $(this).attr('id');		
		$(this).parent().remove();
		$.ajax({
			url:'removeMes',
			type: 'POST',
			data: {'removeOutId': ''+removeId},
			success: function(text){
			}
		})
		return false;
	});
	
})