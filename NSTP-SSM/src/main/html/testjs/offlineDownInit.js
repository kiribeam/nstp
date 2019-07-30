function init(){
	$("#tableBlock").append($.parseHTML(printEcuKeyTable()));
	console.log("init");
	
	$("#searchButton").click(function(){
		var ecu = $("#ecuid").val();
		var page = $("#pageNumber").val();
		var user = $("#username").val();
		var status = $("#status").val();
		var keyId = $("#keyId").val();
		var userData = {};
		if(!fill(userData, "ecuid", ecu, ValidRegex["ecuid"])) return;
		if(!fill(userData, "pageNumber", page, ValidRegex["number"])) return;
		if(!fill(userData, "username", user, ValidRegex["name"])) return;
		if(!fill(userData, "status", status, ValidRegex["number"])) return;
		if(!fill(userData, "keyId", keyId, ValidRegex["number"])) return;
		var data = read("ecuKey/getEcuKey", userData);
		//if(!data.length > 0) return;
		printTable(data, "generalTable", "checkGroup");
	});

	$("#username").val($("#currentUser").text());
	$("#searchButton").click();	

	registQuickNextPageButton();

	registQuickPrevPageButton();	

	$('#offlineDownBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log(item);
		var inputAuthID = $('#offlineDown_authid').val();
		var inputKeyID = $('#offlineDown_kid').val();
		var inputKey = $('#offline_key_set').val();
		var inputWP = $('#WP_val').val();
		var inputBP = $('#BP_val').val();
		var inputDP = $('#DP_val').val();
		var inputKU = $('#KU_val').val();
		var inputDWC = $('#DWC_val').val();
		var inputCU = $('#CU_val').val();

		if(item.length<=0){
			alert("请从列表中选择ECUID");
			return;
		}
		if(!(ValidRegex["bit"].test(inputWP)
				&&ValidRegex["bit"].test(inputBP)
				&&ValidRegex["bit"].test(inputDP)
				&&ValidRegex["bit"].test(inputKU)
				&&ValidRegex["bit"].test(inputDWC)
				&&ValidRegex["bit"].test(inputCU)
				)) {
			alert("Input flag wrong");
			return;
		}
		if(inputKey.length>0 
				&& (!ValidRegex["hex"].test(inputKey) 
				|| inputKey.length != 32)){
			alert("Input Key wrong");
			return;
		}
		if(!(ValidRegex["keyId"].test(inputAuthID) 
				&& ValidRegex["keyId"].test(inputKeyID))){
			alert("Key id wrong!");
			return;
		}
		if(!confirm("确认发起下载申请？")) return;
    	var keyFlag = inputWP.toString() + inputBP.toString() + inputDP.toString() + 
    			inputKU.toString() + inputDWC.toString()+ inputCU.toString();    		
    	var urlSuffix = "/" + inputAuthID + "/" + inputKeyID + "/" + keyFlag; 
    	if(inputKey.length > 0) urlSuffix = urlSuffix + "/" + inputKey;
    	//A form to download file!
    	//Can't save file by using ajax
    	var $form = $('<form id="tempDown" method="post"></form>');
    	$form.attr('action', "offlineDown/getBin" + urlSuffix);
    	let itemInput = $("<input>");
    	itemInput.attr("name", "items");
    	itemInput.attr("value", item);
    	itemInput.attr("type", "hidden");
    	$form.append(itemInput);
    	$form.appendTo($('body'));
    	$form.submit();
    	$("#tempDown").remove();	
    	//refresh
    	$("#searchButton").click();
    	//changeBeast("beast", "offline-down.html");
    	console.log(data);
	});
}

init();