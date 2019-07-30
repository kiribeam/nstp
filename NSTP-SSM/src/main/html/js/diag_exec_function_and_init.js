//Just kitte by Kiri.
//Functions and init event in offline-down html.

$('#diagEcuidTable').load("./php/tbl-diagEculist.php");
$(document).ready(function(){
	
	$('#diagRequestBtn').click(function(){
		var selectEcuArr   = selectTable("#diagEcuidTable",1);      	//选择复选框中的ecuid
		var selectHeadByte = $('#diag_exec_head').val();
		var selectRandByte = $('#diag_exec_rand').val();
		var selectKeyID    = $('#diag_key_id').val();

		console.log("EcuArr:",selectEcuArr);
		if(selectEcuArr.length<=0){
			alert("请选择EcuID数据");
			return;
		}else if(selectEcuArr.length>1){
			alert("只能选择一条记录");
			return;
		}else if(selectHeadByte=="" ||selectRandByte==""){
			alert("请填写表单内容");
			return;
		}else if(selectHeadByte.length != 1*2 ||  selectRandByte.length != 15*2){
			alert("表单内数值字符数不正确。请检查");
			return;
		}else if(selectKeyID >20 || selectKeyID <= 0){
			alert("Key ID 的范围在1-20之间");
			return;
		}

		var confirmMsg=confirm("确认发起安全诊断申请？");
    	if(confirmMsg==true){	
    		//转换为json串
    		var jsonHeadByteString = JSON.stringify(selectHeadByte);
    		var jsonRandByteString = JSON.stringify(selectRandByte);
    		var jsonKeyIDString    = JSON.stringify(selectKeyID);
			var jsonEcuString = JSON.stringify(selectEcuArr);

			$.ajax({ 
				url: './php/diag-exec.php',
				type: 'post',
				data:{
					'PostVal1': jsonEcuString ,
					'PostVal2': jsonHeadByteString ,
					'PostVal3': jsonRandByteString ,
					'PostVal4': jsonKeyIDString ,
				},
				dataType:"json",
				async: false,    //取消异步
				success: function(data){  
		                alert(data.msg);
		                //操作完成，显示结果
						//alert(data.session_key);
						//alert(data.response);
						$("#diag_session_key_res").val(data.session_key);
						$("#diag_response_res").val(data.response);
						//window.location.reload();						
					},
	            error: function(err){
					alert("后台出错！");
                    alert(err.responseText);
	            }
	        });  
        }	
	});	
	
	//$('#offlineEcuidTable').load("./php/tbl-offlineEculist.php");

});

