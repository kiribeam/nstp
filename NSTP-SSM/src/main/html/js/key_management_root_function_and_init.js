//Just kitte by Kiri.
//Functions and init event in keymgmt-root html.


$(document).ready(function(){
	$('#manaKeyUntrustBtn').click(function(){
		var selectPKArr = selectTable("#serverKeyListTable",2);      	//选择复选框中的ecuid
		console.log("PKArr:",selectPKArr);
		if(selectPKArr.length<=0){
			alert("您没有选择数据");
			return;
		}
		var confirmMsg=confirm("确认取消信任?");
    	if(confirmMsg==true){	
			// get selectDelECUID
			var jsonEcuString = JSON.stringify(selectPKArr);			
			$.ajax({ 
				url: './php/manaKey-del.php',
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
	
	$('#manaKeyDelBtn').click(function(){
		var selectPKArr = selectTable("#serverKeyTempListTable",2);
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
	
	$('#manaKeyTrustBtn').click(function(){
		var selectPKArr = selectTable("#serverKeyTempListTable",2);
		var selectFingerArr = selectTable("#serverKeyTempListTable",6);
		console.log("PKArr:",selectPKArr);
		if(selectPKArr.length<=0){
			alert("您没有选择数据");
			return;
		}		
		var confirmMsg=confirm("请确认公钥指纹: "+selectFingerArr[0]);
    	if(confirmMsg==true){	
			// get selectDelECUID
			var jsonEcuString = JSON.stringify(selectPKArr);
			// var jsonFileString = JSON.stringify(selectFileArr);
			$.ajax({ 
				url: './php/manaKey-trust.php',
				type: 'get',
				data:{
					PostVal1: jsonEcuString ,
					// PostVal2: jsonFileString ,
				},
				dataType:"json",
				async: false,     //取消异步
				success: function(data){  
		                alert(data.msg);
						console.log("trusenum:",data.result.num);
						window.location.reload(true);
						//window.location.href='./keymgmt-root.html';
					},
	            error: function(err){
					alert("后台公钥管理出错！");
					alert(err.responseText);
	            }
	        });  
        }	
	});
	
	$('#serverPKDown').click(function(){
		var confirmMsg=confirm("点击确认下载版本号为X的服务器公钥");
    	if(confirmMsg==true){	
			var serverPKfilepath = "../serverPKpath/"; 
			var serverPKfilename = "serverPKv0.1.txt"
			var downloadlink = './php/serverKey-trans.php?filepathH=' + serverPKfilepath + '&filenameH=' + serverPKfilename;
			window.open(downloadlink);
        }	
	});
	$('#serverKeyListTable').load("./php/tbl-mgmtKeyShow.php");
	$('#serverKeyTempListTable').load("./php/tbl-mgmtKeyTempShow.php");

});