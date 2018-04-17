$(document).ready(function(){
	
	$("#to_register").click(function(){
		$.ajax({ 
			url: webPath+"/register/register/toRegister", 
			type:'post',
			data: {
				username:$("#username").val(),
				email:$("#email").val(),
				password:hex_md5($("#password").val()) 
			},
			dataType:'json',
			success: function(data){
				if(data.type == 'success'){
					swal({
					    title: data.content,    
					    type: data.type,    
					    confirmButtonText: "关闭" 
					})
					return;
				}
	      }});
	});
	
	$("#toLogin").click(function(){
		$.ajax({ 
			url: webPath+"/toLogin", 
			type:'post',
			data: {
				username:$("#username1").val(),
				password:hex_md5($("#password1").val()) 
			},
			dataType:'json',
			success: function(data){
				if(data.type == 'success'){
					window.location.href=webPath+data.content;
				}else{
					swal({
					    title: data.content,    
					    type: data.type,    
					    confirmButtonText: "关闭" 
					})
					return;
				}
	      }});
	});
	
});