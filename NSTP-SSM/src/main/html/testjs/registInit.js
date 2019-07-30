function init(){
	$("#regist").click(function(){
		if(!confirm("Regist？")) return;	
		let username = $("#inputUser").val();
		let password = $("#inputPassword").val();
		let checkpass = $("#recheckPassword").val();
		let mail = $("#mailAddress").val();
		if(password != checkpass){
			alert("Incorrect password!")
			return;
		}
		if(!ValidRegex["name"].test(username)){
			alert("Username must be 4-20 characters without special character");
			return;
		}
		if(!ValidRegex["password"].test(password)){
			alert("Password must be 6-25 characters");
			return;
		}
		if(!ValidRegex["mail"].test(mail)){
			alert("Wrong mail");
			return;
		}
		console.log(username + ":" +  password + ":" + checkpass + ":" + mail);
		data = {};
		data["username"] = username;
		data["password"] = password;
		data["mail"] = mail;
		var ajaxData = create("regist", data);
		console.log(ajaxData);

	});
	
}

init();