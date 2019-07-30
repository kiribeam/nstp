//Just kitte by Kiri.
//Functions and init event in system-ecu html.

$('#systemEcuidTable').load("./php/tbl-eculist.php");

$(document).ready(function(){
	//随机添加ECUID
	$('#sysECUAddBtn').click(function(){
		var confirmMsg=confirm("确定添加ECU？");
		if(confirmMsg==true){	
			// get new ECU num
			var newEcuNum = $('#sysECUAddNum').val();
			console.log("newECUNum:",newEcuNum);
			//判断输入数据
			if(newEcuNum<=0 || newEcuNum>100){
				alert("输入数量需要在1-100之间！");
				return ;
			}
			else{
				var jsonEcuNum = JSON.stringify(newEcuNum);
				$.ajax({ 
					url: './php/sysecu-create.php',
					type: 'get',
					data:{
						PostVal1: jsonEcuNum ,
					},
					async: false,
					dataType:"json",
					success: function(data){  
						alert(data.msg);					
						window.location.reload();
					},
					error: function(err){
						alert("后台出错!");
						alert(err.responseText);
					}
				});  
			}

		}
	});
	//ECU删除
	$('#sysECUDelBtn').click(function(){
		var selectEcuArr = selectTable("#systemEcuidTable",1);      	//选择复选框中的ecuid
		console.log("EcuArr:",selectEcuArr);
		if(selectEcuArr.length<=0){
			alert("您没有选择数据");
			return;
		}
		var confirmMsg=confirm("确认删除ECU?");
    	if(confirmMsg==true){	
			var jsonEcuString = JSON.stringify(selectEcuArr);
			// var jsonFileString = JSON.stringify(selectFileArr);
			$.ajax({ 
				url: './php/sysecu-del.php',
				type: 'get',
				data:{
					PostVal1: jsonEcuString ,
					// PostVal2: jsonFileString ,
				},
				dataType:"json",
				success: function(data){  
		                alert(data.msg);
						console.log("delnum:",data.result.delnum);
						window.location.reload();						
					},
	            error: function(err){
					alert("后台公钥管理出错！");
                    alert(err.responseText);
	            }
	        });  
        }	
	});
	
	//自定义ECUID
	$('#ECUKeySetBtn').click(function(){
		var inputEcuSet = $("#ecu_key_set").val(); 
		console.log("inputEcuSet:",inputEcuSet);
		if(inputEcuSet.length!=32 ){
			alert("请填写16字节（32字符）的ECUID！");
			return;
		}

		var confirmMsg=confirm("确认新建ECUID？");
    	if(confirmMsg==true){	
    		//转换为json串
    		var jsonEcuSetString   = JSON.stringify(inputEcuSet);


			$.ajax({ 
				url: './php/sysecu-setEcuid.php',
				type: 'post',
				data:{
					'PostVal1': jsonEcuSetString ,
				},
				dataType:"json",
				async: false,    //取消异步
				success: function(data){  
                	if(data.status==000){
		                alert("后台执行成功："+data.msg);	
		                window.location.reload();						
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