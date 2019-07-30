function init(){
	$("#tableBlock").append($.parseHTML(printUserGroupRoleTable()));
	$("#searchButton").click(function(){
		var username = $("#username").val();
		var role = $("#role").val();
		var userGroup = $("#userGroup").val();
		var pageNumber = $("#pageNumber").val();
		var userData = {};
		if(!fill(userData, "username", username, ValidRegex["name"])) return;
		if(!fill(userData, "role", role, ValidRegex["name"])) return;
		if(!fill(userData, "pageNumber", pageNumber, ValidRegex["number"])) return;
		if(!fill(userData, "userGroup", userGroup, ValidRegex["userGroup"])) return;
		var data = read("roleControl/getUserRole", userData);
		printTable(data, "generalTable", "checkGroup");
	});
	registQuickSingleDeleteButton("roleControl/delRole");
	$("#addButton").click(function(){
		var username = $("#addUser").val();
		var rid = $("#rid").val();
		if(!username || !rid){
			alert("All columns must be filled!");
			return;
		}
		var userData = {};	
		if(!fill(userData, "username", username, ValidRegex["name"])) return;
		if(!fill(userData, "rid", rid, ValidRegex["number"])) return;
		if(!fill(userData, "gname", 'aaaa', ValidRegex["userGroup"])) return;
		console.log(create("roleControl/addRole", userData));
		$("#searchButton").click();
	});
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#searchButton").click();	
}
init();