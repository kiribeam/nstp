function read(basicUrl, userData){
	var ajaxData;
	$.ajax({
		url : basicUrl,
		async :false,
		type : "get",
		dataType : "json",
		data : userData,
		success : function(data){ ajaxData = data},
		error: function(data){
			console.log("failed" + data);
			alert("fail");//TODO
		}
	});
	return ajaxData;
}

function create(basicUrl, userData){
	$.ajax({
		url : basicUrl,
		async :false,
		type : "post",
		dataType : "json",
		data: userData,
		success : function(data){ 
			console.log("success");
		},
		error: function(data){
			console.log("failed");
		}
	});
}

function update(basicUrl, userData){
	var ajaxData;
	$.ajax({
		url : basicUrl,
		async :false,
		type : "put",
		dataType : "json",
		data: userData,
		success : function(data){ 
			console.log("success");
			ajaxData=data;
		},
		error: function(data){
			console.log("failed");
		}
	});	
	return ajaxData;
}

function del(basicUrl, dataList){
	var ajaxData;
	var rUrl= basicUrl;
	for(i=0; i<dataList.length; i++){
		rUrl += "/" + dataList[i];
	}
	$.ajax({
		url : rUrl,
		async :false,
		type : "delete",
		dataType : "json",
		success : function(data){ ajaxData = data},
		error: function(data){
			console.log("failed" + data);
		}
	});
	return ajaxData;	
}