//Just kitte by Kiri.
//Init log judgement for user-right.
//It will be removed for security i think.

$(document).ready(function(){
	$.ajax({ 
		url: './php/userRight-select.php',
		type: 'get',
		data:{
		},
		dataType:"json",
		success: function(data){  
				if(data.status==200){
					//console.log("data.result.userRight")
	                if(data.result.userRight=="manager"){
	                	$("#managerControl").show();
	                	$("#userSelectform").show();
	                	$("#deleteBtn").show();
	                	$("#downloadBtn").show();
	                }				
				}
				else{
					alert(data.msg);
				}
			},
        error: function(result){
			alert("失败啦老铁！,错误未知!！");
        }
    });
	$("#loginBtn").click(function(){
		loginTrigger();
	}); 
	$("#inputPassword").keydown(function(e){
		if(e.keyCode==13){
			loginTrigger();
		}
	});
	$("#inputUser").keydown(function(e){
		if(e.keyCode==13){
			loginTrigger();
		}
	});
	$(function(){ 
		// $(document).ready(function(){
		loginJugg();
	});
});
