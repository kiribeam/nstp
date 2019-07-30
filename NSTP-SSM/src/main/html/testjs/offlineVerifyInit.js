function init(){
	$("#tableBlock").append($.parseHTML(printEcuKeyChangeTable()));
	console.log("print");
	$("#searchButton").click(function(){
		var ecu = $("#ecuid").val();
		var page = $("#pageNumber").val();
		var user = $("#username").val();
		var keyId = $("#keyId").val();
		var userData = {};
		if(!fill(userData, "ecuid", ecu, ValidRegex["ecuid"])) return;
		if(!fill(userData, "pageNumber", page, ValidRegex["number"])) return;
		if(!fill(userData, "username", user, ValidRegex["name"])) return;
		if(!fill(userData, "keyId", keyId, ValidRegex["number"])) return;
		var data = read("ecuKey/getKeyChange", userData);
		//if(!data.length > 0) return;
		printTable(data, "generalTable", "checkGroup");
	});

	$("#username").val($("#currentUser").text());
	$("#searchButton").click();		
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$('#offlineVerifyBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		if(item.length!=1){
			alert("请从列表中选择一条记录");
			return;
		}
		if(!confirm("将此ECUID、KeyID对应的密钥设置为已验证？")) return;
		console.log(update("offlineDown/verifyKey/" + item[0] , "a"));
		$("#searchButton").click();
	});
}
init();