$(document).ready(function(){
	$('#showSketches').click(function(){
		$('#operSketches').toggle('slow');
	})
	$('#showUploadForm').click(function(){
		$('#uploadForm').toggle();
	})
	//УДАЛИТЬ
	$('.removeImage').click( function(){
		removeId = $(this).attr('id');		
		$(this).parent().remove();
		$.ajax({
			url:'removeImage',
			type: 'POST',
			data: {'removeId': ''+removeId},
			success: function(text){
			}
		})
		return false;
	});
})