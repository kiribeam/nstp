//This file is the global function library wrote by Kiri.
//only basic function
//Global Regex
var ValidRegex = {
		ecuid : /^[A-F|0-9]{1,32}$/i,
		name : /^[A-Z|0-9]{4,20}$/i,
		number : /^[0-9]+$/,
		mail : /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/i,
		hex : /^([A-F|0-9]{2})+$/i,
		fingerPrint: /^[a-f|0-9]{1,32}$/i,
		bit: /^[1|0]$/,
		keyId: /^[1-9]$|^1[0-9]$|^20$/,
		password: /^.{6,25}$/i,
		userGroup: /^[0-9|A-Z]{1,32}$/i,
		remark: /^[\s|\w]{0,49}$/
}

//Global function

//Basic empty call
function init(){}

//Automatically print table based on Object list => cute!
//It requires only one id to identify which to be operated!
//This id must be appeared in spring return value that "id"=>"???"
function printTable(data, tableId, checkGroup){

	//console.log(data);
	let table = $("#"+tableId);	
	table.children().remove();
	table.append('<thead class="text-center" sytle="text-align; center;"></thead>');
	let thead = table.children(":first-child");
	thead.append("<tr class=\"mytable-head\"></tr>");
	thead.children(":first-child").append("<th>"+ ""+"</th>");
	if(!data) return;
	for(let key in data[0]){
		if(key != "id") thead.children(":first-child").append("<th>" + key + "</th>");
	}
	let checkboxPrefix = "<input type=\"checkbox\" name=\"" + checkGroup + "\" value=\"";
	let checkboxSuffix = "\" />";
	table.append('<tbody></tbody>');
	let tbody = table.children(":last-child");
	for(let obj in data){
		tbody.append("<tr style=\"background: #ffffff; height: 10px;\"></tr>");
		tbody.children(":last-child").append("<td>" + checkboxPrefix + data[obj]["id"] + checkboxSuffix + "</td>");
		for(let key in data[obj]){
			if(key != "id") tbody.children(":last-child").append("<td>" + data[obj][key] + "</td>");
		}
	}
}

//Select check box printed  
function selectCheckbox(group){
	var items = [];
	$("input:checkbox[name='" + group + "']:checked").each(function(){
		items.push($(this).val());
	});
	console.log(items);
	return items;
}

//Restful CRUD
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
			alert(data.responseText);
			if(data.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
			ajaxData=null;
		}
	});
	return ajaxData;
}

function create(basicUrl, userData){
	var ajaxData;
	$.ajax({
		url : basicUrl,
		async :false,
		type : "post",
		dataType : "json",
		data: userData,
		success : function(data){ 
			ajaxData=data;
			console.log("success");
		},
		error: function(data){
			alert(data.responseText);
			if(data.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
			ajaxData=null;
		}
	});
	return ajaxData;
}

function uploadFile(basicUrl, formData){
	var ajaxData;
	$.ajax({ 
		url:  basicUrl,
		type: 'post',
		data: formData,
		dataType: "JSON",  
		contentType: false,
		processData: false,
		async: false,
		cache: false,				
		success: function(data){  
			ajaxData=data;
			console.log(data);
		},
	    error: function(data){
	    	alert(data.responseText);
			if(data.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
	    	ajaxData=null;
	    }
	});	
	return ajaxData; 
}

function downloadFile(url, method, data){
	if(!method) method = "get";
	var $form = $('<form id="tempDown" method="'+ method + '"></form>');
	$form.attr('action', url);
	$form.appendTo($('body'));
	$form.submit();
	$("#tempDown").remove();	
}

function update(basicUrl, userData){
	var ajaxData;
	$.ajax({
		url : basicUrl,
		async :false,
		type : "put",
		dataType : "json",
		contentType : "application/json",
		data: userData,
		success : function(data){ 
			console.log("success");
			ajaxData=data;
		},
		error: function(data){
			alert(data.responseText);
			if(data.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
			ajaxData = null;
		}
	});	
	return ajaxData;
}

function del(basicUrl, userData){
	var ajaxData;
	var rUrl= basicUrl;
	$.ajax({
		url : rUrl,
		async :false,
		type : "delete",
		data : userData,
		dataType : "json",
		contentType : "application/json",
		success : function(data){ ajaxData = data},
		error: function(data){
			alert(data.responseText);
			if(data.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
			ajaxData = null;
		}
	});
	return ajaxData;	
}
//Change Beast page
function changeBeast(id, link){
	$.ajax({
		url: "beasts/" + link,
		type : "get",
		success : function(data){ 
			$('#' + id).children().remove();
			$('#' + id).html(data); 
		},
		error: function(data){
			if(data.status == 401){
				alert("Please login!");
				window.location.href = 'UserLogin.html';
				return;
			}
			$('#' + id).html("<h1>404</h1>");
		}
	});	
}

