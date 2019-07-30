//Just kitte by Kiri.
//Functions and init event in reflesh-log html.
function refreshShow(userSelect){
	var userSelectName = userSelect;
	var jsonSelectString = JSON.stringify(userSelectName);
	// var jsonFileString = JSON.stringify(selectFileArr);
	$.ajax({ 
		url: './php/tbl-refreshlog.php',
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
$('#userSelect').load("./php/tbl-userSelectBox.php");

$(document).ready(function(){
	var userSelectName = "all";
	refreshShow(userSelectName);
	//获取select动作及被选中的值 
//in refresh-log.html line 79 
	$('#userSelect').change(function(){
		var userSelectName = $('#userSelect').val();
		console.log(userSelectName);
		$("#hexListTable").empty()
		refreshShow(userSelectName);
	});
	
	/*删除记录*/
	$('#refreshLogDelBtn').click(function(){
		var confirmMsg=confirm("删除选中记录?");
    	if(confirmMsg==true){	
			// get selectDelECUID
			var selectEcuArr      = selectTable("#hexListTable",1);      	//选择复选框中的ecuid
			var selectDatetimeArr = selectTable("#hexListTable",4); 
			console.log("ecuArr:",selectEcuArr);
			console.log("DatetimeArr:",selectDatetimeArr);
			//如果为空
			//如果不为空
			//haven't accomplished?
			var jsonEcuString = JSON.stringify(selectEcuArr);
			var jsonDatetimeString = JSON.stringify(selectDatetimeArr);
			// var jsonFileString = JSON.stringify(selectFileArr);
			$.ajax({ 
				url: './php/refreshlog-del.php',
				type: 'get',
				data:{
					PostVal1: jsonEcuString ,
					PostVal2: jsonDatetimeString ,
					// PostVal2: jsonFileString ,
				},
				async: false,
				dataType:"json",
				success: function(data){  
	                alert(data.msg);
					console.log("delnum:",data.result.delnum);
					window.location.reload();
				},
	            error: function(err){
					alert("后台删除出错!");
                    alert(err.responseText);
	            }
	        });  
        }	
	});
	
	
	
});
