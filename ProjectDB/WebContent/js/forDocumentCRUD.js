$(document).ready(function(){
	//Для newDocumentView.jsp и для editDocumentView.jsp
	var fileInputClone = $('.fileInput').clone();
	var lastFileInput = $('.fileInput');
	var addFileInput = $('#addFileInput');
	addFileInput.click(function(){
		var newFileInput = fileInputClone.clone();
		lastFileInput.after(newFileInput).after('<br/>');
		lastFileInput = newFileInput;
	})
	
	//Для showDocument.jsp-------------------------------------------------------------
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
	var bigImage = $('#bigImage');
	bigImage.click(function(){
		$(this).slideUp('fast');
	})
	$('img').click(function(){
		bigImage.html($(this).clone());
		bigImage.slideDown('fast');
	})
})