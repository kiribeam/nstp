function init(){
	$("#tableBlock").append($.parseHTML(printPgpTable()));
	registPgpSearchButton("pgp/getPk");//TODO
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#searchButton").click();
	
	$('#manaKeyUntrustBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log("Select:", item);
		if(item.length == 0){
			alert("您没有选择数据");
			return;
		}
		if(!confirm("确认取消信任?")) return;
    	console.log(update('pgp/untrust', JSON.stringify(item)));
    	$("#searchButton").click();
	});
	
	$('#manaKeyDelBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log("Select:", item);
		if(item.length == 0){
			alert("您没有选择数据");
			return;
		}
		if(!confirm("确认Delete?")) return;
    	console.log(del('pgp/del', JSON.stringify(item)));
     	$("#searchButton").click();
	});
	
	$('#manaKeyTrustBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log("Select:", item);
		if(item.length == 0){
			alert("您没有选择数据");
			return;
		}
		if(!confirm("请确认公钥指纹")) return;
    	console.log(update('pgp/trust', JSON.stringify(item)));
    	$("#searchButton").click();
	});
}
init();