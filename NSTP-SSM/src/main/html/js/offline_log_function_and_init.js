//Just kitte by Kiri.
//Functions and init event in reflesh-log html.
function offlineShow(userSelect){
	var userSelectName = userSelect;
	var jsonSelectString = JSON.stringify(userSelectName);
	// var jsonFileString = JSON.stringify(selectFileArr);
	$.ajax({ 
		url: './php/tbl-offlinelog.php',
		type: 'get',
		data:{
			PostVal1: jsonSelectString ,
		},
		dataType:"html",
		success: function(data){  
            $("#hexListTable").append(data);
		},
        error: function(err){
			alert("后台出错！");
	        alert(err.responseText);
    	}
    });  	
}
/* 加载复选框 */
$('#userSelect').load("./php/tbl-userSelectBox.php");

$(document).ready(function(){
	var userSelectName = "all";
	offlineShow(userSelectName);
	//获取select动作及被选中的值 
//in refresh-log.html line 79 
	$('#userSelect').change(function(){
		var userSelectName = $('#userSelect').val();
		console.log(userSelectName);
		$("#hexListTable").empty()
		offlineShow(userSelectName);
	});
	
	$('#offlineLogDelBtn').click(function(){
		alert("目前尚不能删除下线模拟记录。");
	});
	
	$('#downloadBtn').click(function(){
		var confirmMsg=confirm("确认下载选中文件?");
    	if(confirmMsg==true){	
			// get selectDelECUID
			var selectEcuArr = selectTable("#hexListTable",2);      	//选择复选框中的ecuid
			console.log("ecuArr:",selectEcuArr);
			// selectFileArr = selectDelFile();		//选择复选框中的file name
			// console.log("fileArr:",selectFileArr);

			//如果为空
			//如果不为空
			var jsonEcuString = JSON.stringify(selectEcuArr);
			// var jsonFileString = JSON.stringify(selectFileArr);
			$.ajax({ 
				url: './php/refresh-predown.php',
				type: 'get',
				data:{
					PostVal1: jsonEcuString ,
				},
				dataType:"json",
				success: function(data){  
					if(data.status==200){
		                alert(data.msg+"点击确定进行下载");
						var downloadlink = './php/refresh-trans.php?filepathH=' + data.result.filepath + '&filenameH=' + data.result.filename;
						window.open(downloadlink);
					}
					else{
						alert(data.msg);
					}
				},
	            error: function(result){
					alert("后台文件查询出错！！");
	            }
	        });  
        }	
	});
	
	
});
