function init(){
	$("#tableBlock").append($.parseHTML(printPgpTable()));
	registPgpSearchButton("pgp/getPk");//TODO
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#username").val($("#currentUser").text());
	$("#searchButton").click();
	
	$('#userUploadBtn').click(function(){
		if($('#userUploadfile').val()==""){
			alert("File 不能为空！");
			return;
		}
		if(!confirm("确认上传?")) return;
	    var jsonFd = new FormData();
	    jsonFd.append("file",$('input[name=userUploadfile]')[0].files[0]);
	    console.log(uploadFile("pgp/uploadPk", jsonFd));//TODO
    	$("#searchButton").click();
	});
	
	$('#userKeyDelBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log("Select:", item);
		if(item.length == 0){
			alert("您没有选择数据");
			return;
		}
		if(!confirm("确认Delete?")) return;
    	console.log(del('pgp/revoke/' + item[0], ''));
    	$("#searchButton").click();
	});
	
	$('#serverPKDown').click(function(){
		if(!confirm("点击确认下载版本号为X的服务器公钥")) return;
		downloadFile('serverpk/server.pub');
	});
}
init();