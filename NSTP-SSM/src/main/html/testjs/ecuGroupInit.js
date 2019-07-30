function init(){
	registQuickGeneralTable('ecuGroup/getEcuGroup');
	registQuickSingleDeleteButton('ecuGroup/del');
	
	$("#addButton").click(function(){
		var item = selectCheckbox("checkGroup");
		if(!item || item.length!=1){
			alert("One Single Select!");
			return;
		}
		var gname = $("#groupName").val();
		if(!gname || !ValidRegex["userGroup"].test(gname)){
			alert("Wrong Group Name!");
			return;
		}
		let data = {};
		data["id"] = item[0];
		data["gname"] = gname;
		
		create("ecuGroup/add", data);
		$("#searchButton").click();
		
	});
}

init();