//Just kitte by Kiri.
//Functions and init event in offline-down html.


$(document).ready(function(){
	//点击新建用户
	$('#registerBtn').click(function(){
		var inputUser     = $("#inputUser").val(); 
		var inputPass     = $("#inputPassword").val(); 
		var inputPass2 	  = $("#inputConfirmPassword").val(); 
		var inputRightSet = $("#userRightSet").val(); 

		console.log("inputUser:",inputUser);
		console.log("inputPass:",inputPass);
		console.log("inputPass2:",inputPass2);
		console.log("inputRightSet:",inputRightSet);

		if(inputUser.length<=0 || inputPass.length<=0 || inputPass2.length<=0 || inputRightSet.length<=0){
			alert("请填写所有表单内容！");
			return;
		}else if(inputPass != inputPass2){
			alert("两次输入的密码不相同！");
			return;
		}else if(inputUser.length>15){
			alert("用户名过长！");
			return;
		}else if(inputRightSet !="user" && inputRightSet !="manager"){
			alert("权限设置不正确！");
			return;
		}

		var confirmMsg=confirm("确认新建用户？");
    	if(confirmMsg==true){	
    		//转换为json串
    		var jsonUserString   = JSON.stringify(inputUser);
    		var jsonPasswdString = JSON.stringify(inputPass);
    		var jsonRightString  = JSON.stringify(inputRightSet);

			$.ajax({ 
				url: './php/sys-userCreate.php',
				type: 'post',
				data:{
					'PostVal1': jsonUserString ,
					'PostVal2': jsonPasswdString ,
					'PostVal3': jsonRightString ,
				},
				dataType:"json",
				async: false,    //取消异步
				success: function(data){  
                	if(data.status==000){
		                alert("后台执行成功："+data.msg);						
					}
					else{
						alert("后台执行失败："+data.msg);
					}					
				},
	            error: function(err){
					alert("后台出错！请查看服务器日志");
                    alert(err.responseText);
	            }
	        });  
        }	
	});	
		

});

