//Just kite by Kiri.
//The initialize


$(document).ready(function(){
	$("#inputUser").focus();
	$("#loginBtn").click(function(){
		let username = $("#inputUser").val();
		let password = $("#inputPassword").val();
		if(!ValidRegex["name"].test(username)){
			alert("Wrong name");
			return;
		}
		if(!ValidRegex["password"].test(password)){
			alert("Wrong pass");
			return;
		}
		var data = {};
		data["username"] = username;
		data["password"] = password;
		console.log(data);
		var ajaxData = create("login", data);
		console.log(ajaxData);
		if(ajaxData) $(location).attr('href', 'NstpSystem.html'); 
	});
	
	$("#inputPassword").keyup(function(e){
		if (e.which == 13) {
        	$("#loginBtn").click();
		}
	});
});
