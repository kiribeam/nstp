//The file with quick function to save position

//Regist quick Log reader for logs
function registQuickLogger(url){
	$("#tableBlock").append($.parseHTML(printGeneralTable()));
	console.log(ValidRegex["ecuid"]);
	registQuickSearchButton("ecuid", url); 
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#username").val($("#currentUser").text());
	$("#searchButton").click();	
}
function registQuickGeneralTable(url){
	$("#tableBlock").append($.parseHTML(printGeneralTable()));
	registQuickSearchButton( "ecuid",  url);
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#username").val($("#currentUser").text());
	$("#searchButton").click();	
}
//Used for one search line table
function registQuickOneLineReader(url, field, regex){
	$("#tableBlock").append($.parseHTML(printOneLineTable(field)));
	$("#searchButton").click(function(){
		var fieldValue = $("#"+field).val();
		var page = $("#pageNumber").val();
		var userData = {};
		if(!fill(userData, field, fieldValue, ValidRegex[regex])) return;	
		var data = read(url + page, userData);
		printTable(data, "generalTable", "checkGroup");	
	});
	registQuickNextPageButton();
	registQuickPrevPageButton();
	$("#searchButton").click();		
}

//Regist search button  in a quick way
function registQuickSearchButton(fieldName, url){
	$("#searchButton").click(function(){
		let data = readUserPageEcu("username", "pageNumber", fieldName, fieldName , url);
		printTable(data, "generalTable", "checkGroup");
		//console.log("print table int search");
	});
}

function registQuickDeleteButton(url){
	$("#deleteButton").click(function(){
		var item = selectCheckbox("checkGroup");
		if(!item || !item.length>0) {
			alert("Nothing selected!");
			return;
		}
		console.log(del(url,JSON.stringify(item)));
		$("#searchButton").click();
	});
}
function registQuickSingleDeleteButton(url){
	$("#deleteButton").click(function(){
		var item = selectCheckbox("checkGroup");
		if(!item || item.length!=1) {
			alert("Single selected!");
			return;
		}
		console.log(del(url,JSON.stringify(item)));
		$("#searchButton").click();
	});
}

function registQuickPrevPageButton(){
	$("#prevPage").click(function(){
		let tmp = $("#pageNumber").val();
		if(!tmp || !ValidRegex["number"].test(tmp) || tmp<=1) tmp=2;
		$("#pageNumber").val(parseInt(tmp)-1);
		$("#searchButton").click();
	});
}

function registQuickNextPageButton(){
	$("#nextPage").click(function(){
		let tmp = $("#pageNumber").val();
		if(!tmp || !ValidRegex["number"].test(tmp) || tmp<0) tmp=0;
		$("#pageNumber").val(parseInt(tmp)+1);
		$("#searchButton").click();
	});
}


function registPgpSearchButton(url){
	$("#searchButton").click(function(){
		let data = readPgpMessage(url);
		printTable(data, "generalTable", "checkGroup");
	});
}


//A hot pattern of page-user-id-group
//be careful about null input and no defined length used here!!
function readUserPageEcu(inputUser, inputPage, inputId, regexId, url){
	var ecu = $("#"+inputId).val();
	var page = $("#" + inputPage).val();
	var user = $("#" + inputUser).val();
	var userGroup = $("#userGroup").val();
	var userData = {};
	if(!fill(userData, inputId, ecu, ValidRegex[regexId])) return;
	if(!fill(userData, inputPage, page, ValidRegex["number"])) return;
	if(!fill(userData, inputUser, user, ValidRegex["name"])) return;
	if(!fill(userData, "userGroup", userGroup, ValidRegex["userGroup"])) return;
	var data = read(url, userData);
	return data; 
}


function readPgpMessage(url){
	var fingerPrint  = $("#fingerPrint").val();
	var page = $("#pageNumber").val();
	var user = $("#username").val();
	var status = $("#status").val();
	var userGroup = $("#userGroup").val();
	var userData = {};
	console.log(userData);
	if(!fill(userData, "fingerPrint", fingerPrint, ValidRegex["fingerPrint"])) return;
	if(!fill(userData, "pageNumber", page, ValidRegex["number"])) return;
	if(!fill(userData, "username", user, ValidRegex["name"])) return;
	if(!fill(userData, "status", status, ValidRegex["number"])) return;
	if(!fill(userData, "userGroup", userGroup, ValidRegex["userGroup"])) return;
	var data = read(url, userData);
	return data; 
}
//attrValue will be filled into data object
//this version is basic with null or arbitrary size but matches regex
function fill(data, attrName, attrValue, regex){
	if(!attrValue || regex.test(attrValue)){
		data[attrName] = attrValue;
		return true;
	}
	alert("input " + attrName + " wrong");
	return false;
}

//A quick way to create Ecu table
function printGeneralTable(){
	var addition = getAdditionInputText("userGroup", 9, 3, "");		
	return printBasicTable("ecuid", addition);
}

function printPgpTable(){
	var addition2 = getAdditionInputText("status", 2, 1, 0);		
	var addition1 = getAdditionInputText("userGroup",2,2,"");
	var addition = addition1 + addition2;
	return printBasicTable("fingerPrint", addition);
}

function printEcuKeyTable(){
	var addition2 = getAdditionInputText('status',1, 1, 0);
	var addition3 = getAdditionInputText('keyId', 2, 1, 0);
	var addition1 = getAdditionInputText("userGroup", 2, 3, "");		
	var addition =  addition1 + addition2 + addition3;
	return printBasicTable("ecuid", addition);
}

function printEcuKeyChangeTable(){
	var addition2 = getAdditionInputText('keyId', 4, 1, 0);
	var addition1 = getAdditionInputText("userGroup", 2, 3, "");		
	var addition = addition1 + addition2;
	return printBasicTable("ecuid", addition);	
}

function printUserGroupRoleTable(){
	var addition1 = getAdditionInputText("username", 2, 2, '');
	var addition2 = getAdditionInputText("role",1, 2, '');
	var addition3 = getAdditionInputText("userGroup", 2, 3, '');
	var line1 = '<div class="col-sm-12">' + addition1+addition2+addition3
					+'</div>'
					+'<div style="height: 40px"></div>';
	return printTwoLineTable(line1);
}




function printOneLineTable(field){
	var addition1 = getAdditionInputText(field,1, 2,'');
	var addition2 = getPageAdditionPart("pageNumber", "searchButton");
	var line3 = '<div class="col-sm-12">'+addition1 + addition2+'</div>';
	return printTableHtml('', '', line3, "generalTable");		
}

function printTwoLineTable(line1){
	var addition1 = '<div class="col-sm-3"></div>';
	var addition2 = getPageAdditionPart("pageNumber", "searchButton");
	var line3 = '<div class="col-sm-12">'+addition1 + addition2+'</div>';	
	return printTableHtml(line1, '', line3, "generalTable");
}
//format search columns 
function getAdditionInputText(field, col, subcol, val){
	var addition = 			
		'<label for="' + field + '" class="col-sm-'+col+' text-right" style="color :#ffffff">' +
		 	field.replace(field[0],field[0].toUpperCase()) +
		':</label>' +
		'<div class="col-sm-'+subcol+'">' +
			'<input type="text" class="form-control" id="' + 
			field +
			'"  value="'+ val + '">' +
		'</div>';
	return addition;
}

//page line not full  -> col-sm-9
function getPageAdditionPart(page, searchButton){
	let pageAddition =
		getAdditionInputText(page, 2, 2, "1")+
	'<div class="col-sm-1"></div>'+
	'<div class="col-sm-1">' +
		'<button id="prevPage" class="btn btn-primary">' +
			'<i class="fa fa-random"></i>' +
			'Prev' +
		'</button>' + 
	'</div>' +
	'<div class="col-sm-1">' +
		'<button id="nextPage" class="btn btn-primary">' +
			'<i class="fa fa-random"></i>' +
			'next' +
		'</button>' + 
	'</div>' +
	'<div class="col-sm-2">'+
	'<button id="'+searchButton+'" class="btn btn-primary">' +
		'<i class="fa fa-random"></i>' +
		'Search' +
	'</button>' +
	'</div>';
	return pageAddition;
}

//basic style
function printBasicTable(itemName, addition){
	let line1 =	'<div class="col-sm-12">' 
		+ getAdditionInputText(itemName, 2, 5, '') 
		+ getAdditionInputText("username", 2, 3, '')
		+'</div>'
		+'<div style="height: 40px"></div>';  
	//console.log(line1);
	let line2 =
		'<div class="col-sm-12">' +
			addition +
		'</div>'; 
	let line3 =		
		'<div class="col-sm-12" style="height : 10px"></div>' +
		'<div class="col-sm-12">' +
			'<div class="col-sm-3">' +
			'</div>' +
			getPageAdditionPart("pageNumber", "searchButton")+
		'</div>'; 
	return printTableHtml(line1, line2, line3, "generalTable");
}

//A Normal way to create general table
function printTableHtml(line1, line2, line3, tableName){

	let line4 =
		'<div class="col-sm-12" style="height : 10px"></div>' +
		'<div class="myTable" style="max-height: 350px;">' +
			'<table id="'+tableName+'" class="table table-bordered table-hover">' +
			'</table>' +
		'</div>' ;
		return line1+line2+line3+line4;
}



