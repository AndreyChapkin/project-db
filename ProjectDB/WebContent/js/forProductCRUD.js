$(document).ready(function(){
	//Для newProductView.jsp и для editProductView.jsp
	fl=false;
	mfl= false;
	var sel = $('#groupSelector');
	var mSel = $('#measureSelector');
	$("#groupButton").click(function(){
		fl=!fl;
		if(fl){
			$(sel).animate({width:'toggle'},250,function(){
				$(sel).replaceWith("<input type=\"text\" name=\"prodGroupName\" id=\"groupInput\">");
				$('#groupInput').hide().animate({width:'toggle'},250);
			})
		}
		else{
			$('#groupInput').animate({width:'toggle'},250,function(){
				$('#groupInput').replaceWith(sel);
				$(sel).hide().animate({width:'toggle'},250);
			})
		}
	})
	$("#measureButton").click(function(){
		mfl=!mfl;
		if(mfl){
			$(mSel).animate({width:'toggle'},250,function(){
				$(mSel).replaceWith("<input style=\"width: 100px;\" type=\"text\" name=\"measUnitName\" id=\"measureInput\" size=\"2\">");
				$('#measureInput').hide().animate({width:'toggle'},250);
			})
		}
		else{
			$('#measureInput').animate({width:'toggle'},250,function(){
				$('#measureInput').replaceWith(mSel);
				$(mSel).hide().animate({width:'toggle'},250);
			})
		}
	})
	//-------------------------------------------------------------------------------------------------
	var old;
	var handler = function(){
		old = $(this);
		$(old).before('<span id=\"question\">Точно?&nbsp;&nbsp;<span>').text('Да').after('&nbsp;&nbsp;<span id=\"noDelete\">Нет</span>');
		
		$(document).on('click','#noDelete',function(){
			$('#question').remove();
			$(old).text('Удалить').off().on('click',handler);
			$(this).remove();
			return false;
		})
		$(old).off('click');
		return false;
	}
	$('a.delete').on('click',handler);
})