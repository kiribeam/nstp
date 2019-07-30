//Just kitte by Kiri.
//Functions and init event in reflesh-upload html.


$('#refreshEcuidTable').load("./php/tbl-refreshEculist.php");
// Here used jsonFD-Formdata to upload file easier.
$(document).ready(function(){
	$('#refreshUploadBtn').click(function(){
		//先判断有无填入值
		var selectEcuArr = selectTable("#refreshEcuidTable",1);      	//选择复选框中的ecuid
		console.log("EcuArr:",selectEcuArr);
		if(selectEcuArr.length<=0){
			alert("您没有选择Ecuid数据!");
			return;
		}else if(selectEcuArr.length>1){
			alert("只能选择一条ecu记录！");
		}else{
			var confirmMsg=confirm("确认发起刷新文件处理请求？");
	    	if(confirmMsg==true){	
	    		//获取表单内的ecuid和文件内容
	    		//var jsonEcuString = JSON.stringify(selectEcuArr);
	    		var Ecuid = selectEcuArr[0];
	    		var jsonFd = new FormData();
	    		// jsonFd.append("file",document.getElementById("uploadfileFrom"));
	    		jsonFd.append("file",$('input[name=myfile]')[0].files[0]);
	    		jsonFd.append("ecuid",Ecuid);
				
				$.ajax({ 
					url:  './php/refresh.php',
					type: 'post',
					data:jsonFd,
					dataType:"JSON",  
					contentType: false,
					processData: false,
					cache: false,				
                    //async:false,
					success: function(data){ 
						if(data.status=="200"){		//运行成功，并打开下载链接
							alert(data.msg);
							//here may cause big security bug.
							var downloadlink = './php/refresh-trans.php?filepathH=' + data.result.filepath + '&filenameH=' + data.result.filename;
							window.open(downloadlink);
						}else {						//100或其它, 运行错误	
							alert(data.msg);								
						} 
					},
		            error: function(err){
						alert("后台出错！");
                        alert(err.responseText);
		            }
	        	});
		    }
		}
	});
	
	
});