//getUserName() ->

//To get user's name
//It will be removed later I think.
function getUserName(){
	var username = "";
	var ajaxData = read("user/username", null);
    return ajaxData["username"];
}

//Used in page -> index, unindentified-function page, 
//	user-function page, root-user-function page.
function logoutFunc(){
	if(!confirm("确定要注销吗？")) return;
	$.ajax({ 
		type: "get", 
		url: "user/logout", 
		success: function(result){ 
			window.location.href='UserLogin.html';			  
		},
		error: function(result){
			if(result.status == 401){
				window.location.href = 'UserLogin.html';
				return;
			}
			alert("发生了未知错误！");
	    }
	});
}

function setNavigateBar(){
	let username= getUserName();
	//Here may set user account manage! TODO or Not TODO, it's a question.
	let str="<li><a href='#'>欢迎您 "+ "<p id='currentUser'>" + username + "</p>"+"</a></li><li><a href='#' id='logout'>注销</a></li>";
	$('#loginCtrl').append(str);
	$("#logout").click(function(){ logoutFunc(); })//changed here
	return true;
}