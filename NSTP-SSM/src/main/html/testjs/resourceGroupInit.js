function init(){
	registQuickOneLineReader("getResourceGroup/", "rGroup", "userGroup");
	registQuickSingleDeleteButton("delResourceGroup");
	
	$("#addButton").click(function(){
		var gid = parseInt($("#gid").val());
		if(!gid || gid<1){
			alert("Wrong GroupId");
			return;
		}
		var gname = $("#gname").val();
		if(!gname || !ValidRegex["userGroup"].test(gname)){
			alert("Wrong Group Name");
			return;
		}
		var remark = $("#remark").val();
		if(!remark) remark = '';
		if(!ValidRegex["remark"].test(remark)){
			alert("Wrong Remark");
			return;
		}
		let data = {};
		data["gid"] = gid;
		data["gname"] = gname;
		data["remark"] = remark;
		create("addResourceGroup", data);
		$("#searchButton").click();
		
	});
}
init();