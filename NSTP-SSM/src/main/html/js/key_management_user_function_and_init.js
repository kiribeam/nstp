
//Just kitte by Kiri.
//Functions and init event in keymgmt-user html.





$(document).ready(function(){
	$('#userUploadBtn').click(function(){
		//先判断有无填入值
		if($('#userUploadfile').val()==""){
			alert("File 不能为空！");
		}else{
			var confirmMsg=confirm("确认上传?");
	    	if(confirmMsg==true){	
	    		//获取表单内的文件内容
	    		var jsonFd = new FormData();
	    		// jsonFd.append("file",document.getElementById("uploadfileFrom"));
	    		jsonFd.append("file",$('input[name=userUploadfile]')[0].files[0]);
				
				$.ajax({ 
					url:  './php/userKey-upload.php',
					type: 'post',
					data:jsonFd,
					dataType:"JSON",  
					contentType: false,
					processData: false,
					async:false,              //为什么需要加取消异步？？
					cache: false,				
					success: function(data){  
						 alert(data.status);        //输出php回传的错误信息						
					},
		            error: function(err){
						alert("后台公钥管理出错！");
                        alert(err.responseText);
		            }
	        	});
		    }
		}
	});
	
	$('#userKeyDelBtn').click(function(){
		var selectPKArr = selectTable("#userKeyTempTable",2);
		console.log("PKArr:",selectPKArr);
		if(selectPKArr.length<=0){
			alert("您没有选择数据");
			return;
		}
		var confirmMsg=confirm("确认Delete?");
    	if(confirmMsg==true){	
			var jsonEcuString = JSON.stringify(selectPKArr);
			// var jsonFileString = JSON.stringify(selectFileArr);
			$.ajax({ 
				url: './php/manaKey-del.php',
				type: 'get',
				data:{
					PostVal1: jsonEcuString ,
					// PostVal2: jsonFileString ,
				},
				dataType:"json",
                async: false,    //取消异步
				success: function(data){  
		                alert(data.msg);
						console.log("delnum:",data.result.delnum);
						window.location.reload(true);
                        //window.location.href='./keymgmt-user.html';
					},
	            error: function(err){
					alert("后台公钥管理出错！");
                    alert(err.responseText);
	            }
	        });  
        }	
	});
	
	$('#userKeyListTable').load("./php/tbl-userKeyShow.php");
	$('#userKeyTempTable').load("./php/tbl-userKeyTempShow.php");

});