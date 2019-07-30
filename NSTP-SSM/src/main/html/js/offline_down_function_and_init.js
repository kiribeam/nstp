//Just kitte by Kiri.
//Functions and init event in offline-down html.
//显示系统中的密钥列表
function offlineKeyShow(ecuSelect){
	var ecuSelectName = ecuSelect;
	var jsonSelectString = JSON.stringify(ecuSelectName);
	// var jsonFileString = JSON.stringify(selectFileArr);
	$.ajax({ 
		url: './php/tbl-offlineKeylog.php',
		type: 'get',
		data:{
			PostVal1: jsonSelectString ,
		},
		dataType:"html",
		success: function(data){  
            $("#offlineKeyTable").append(data);
		},
        error: function(err){
			alert("后台出错！");
	        alert(err.responseText);
    	}
    });  	
}
/* 显示可操作的ECU列表 */
$('#offlineEcuidTable').load("./php/tbl-offlineEculist.php");

/* 加载复选框 */
$('#ecuSelect').load("./php/tbl-ecuSelectBox.php");

/* 系统中的密钥列表显示 */
var ecuSelectName = "all";
offlineKeyShow(ecuSelectName);
//获取select动作及被选中的值 
//in refresh-log.html line 79 
$('#ecuSelect').change(function(){
	var ecuSelectName = $('#ecuSelect').val();
	console.log(ecuSelectName);
	$("#offlineKeyTable").empty();
	offlineKeyShow(ecuSelectName);
});

$(document).ready(function(){
	/*下线模拟过程操作   点击按钮  */
	$('#offlineDownBtn').click(function(){
		var selectEcuArr = selectTable("#offlineEcuidTable",1);      	//选择复选框中的ecuid
		var inputAuthID  = $('#offlineDown_authid').val();
		var inputKeyID   = $('#offlineDown_kid').val();
		var inputKey     = $('#offline_key_set').val();
		var inputWP      = $('#WP_val').val();
		var inputBP      = $('#BP_val').val();
		var inputDP      = $('#DP_val').val();
		var inputKU      = $('#KU_val').val();
		var inputDWC     = $('#DWC_val').val();
		var inputCU      = $('#CU_val').val();

		console.log("EcuArr:",selectEcuArr);
		console.log("InputKey:", inputKey);
		// 判断输入值的范围
		if(selectEcuArr.length<=0){
			alert("请从列表中选择ECUID");
			return;
		}else if(inputAuthID=="" || inputKeyID==""){
			alert("AuthId和KeyId不能为空！");
			return;
		}else if(inputAuthID>20 || inputKeyID>20){
			alert("AuthId和KeyId的值在1-20之间");
			return;
		}else if(inputKey!="" && inputKey.length!=32){     //只能为空，或者16字节（32字符）
			alert("Key Value 设置错误！只能设置为空或者16字节（32字符）");
			return;
		}else if(inputWP=="" || inputBP=="" || inputDP=="" || inputKU=="" || inputDWC=="" || inputCU=="" || 
			     inputWP > 1 || inputBP > 1 || inputDP > 1 || inputKU > 1 || inputDWC > 1 || inputCU > 1 ){
			alert("KeyFlag 错误！请设置标志位为0或1");
			return;
		}

		var confirmMsg=confirm("确认发起下载申请？");
    	if(confirmMsg==true){	
    		// 组织传输字符串
    		var KeyFlag = inputWP.toString() + inputBP.toString() + inputDP.toString() + 
    					  inputKU.toString() + inputDWC.toString()+ inputCU.toString() ;    		
    		if(inputKey == "") {inputKey = 0;}   //便于传输数据
    		// alert(KeyFlag);
    		// alert(inputKey);
    		var jsonAuthidString  = JSON.stringify(inputAuthID);
    		var jsonKeyidString     = JSON.stringify(inputKeyID);
    		var jsonKeyFlagString = JSON.stringify(KeyFlag);
    		var jsonKeyValString  = JSON.stringify(inputKey);
			var jsonEcuString     = JSON.stringify(selectEcuArr);

			$.ajax({ 
				url: './php/offline-down.php',
				type: 'post',
				data:{
					'PostVal1': jsonEcuString ,
					'PostVal2': jsonAuthidString ,
					'PostVal3': jsonKeyidString ,
					'PostVal4': jsonKeyFlagString ,
					'PostVal5': jsonKeyValString ,
				},
				dataType:"json",
				async: false,    //取消异步
				success: function(data){  
	                //操作完成，下载文件
	                if(data.status == "000"){
	                	alert(data.msg);
						var downloadlink = './php/refresh-trans.php?filepathH=' + data.result.filepath + '&filenameH=' + data.result.filename;
						window.open(downloadlink);
						window.location.reload();
					}else{
						alert("后台出错: "+data.msg);
					}						
				},
	            error: function(err){
					alert("后台出错！");
                    alert(err.responseText);
	            }
	        });  
        }	
	});	
	/* 验证按钮 */
	$('#offlineVerifyBtn').click(function(){
		var selectEcuArr = selectTable("#offlineKeyTable",1);      	//选择复选框中的ecuid
		var inputKeyID   = selectTable("#offlineKeyTable",2);
		var inputCount   = selectTable("#offlineKeyTable",3);
		var inputStatus   = selectTable("#offlineKeyTable",5);

		console.log("EcuArr:",selectEcuArr);
		console.log("inputKeyID:", inputKeyID);
		console.log("inputCount:", inputCount);
		console.log("inputStatus:", inputStatus);
		// 判断输入值的范围
		if(selectEcuArr.length!=1){
			alert("请从列表中选择一条记录");
			return;
		}

		var confirmMsg=confirm("将此ECUID、KeyID对应的密钥设置为已验证？");
    	if(confirmMsg==true){	
    		// 组织传输字符串
    		var jsonEcuString    = JSON.stringify(selectEcuArr);
    		var jsonKeyidString  = JSON.stringify(inputKeyID);
    		var jsonCountString  = JSON.stringify(inputCount);    		
    		var jsonStatusString = JSON.stringify(inputStatus);
			
			$.ajax({ 
				url: './php/offline-verify.php',
				type: 'post',
				data:{
					'PostVal1': jsonEcuString ,
					'PostVal2': jsonKeyidString ,
					'PostVal3': jsonCountString ,
					'PostVal4': jsonStatusString ,
				},
				dataType:"json",
				async: true,    //取消异步
				success: function(data){  
	                if(data.status == "000"){
	                	alert(data.msg);
						alert("验证成功！");
						window.location.reload();
					}else{
						alert("后台出错: "+data.msg);
					}						
				},
	            error: function(err){
					alert("后台出错！请查看后台日志");
                    alert(err.responseText);
	            }
	        });  
        }	
    });
	/* 删除记录按钮 */

});

