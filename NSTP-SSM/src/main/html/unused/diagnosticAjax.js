function getEcuData(operation,page, user, ecuid){
	var ajaxData;
	$.ajax({
		url: "/NSTP-SSM/" + operation + "/" + page + "/" + user + "/" + ecuid,
		async :false,
		type : "get",
		dataType : "json",
		success : function(data){ ajaxData = data},
		error: function(data){
			alert(data);
		}
	});
	return ajaxData;
}