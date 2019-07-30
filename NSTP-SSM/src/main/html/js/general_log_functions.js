//Refactored by Kiri.
//The general functions used in all pages.

//To get user's name
//It will be removed later I think.
function getUsrName(){
	var usrName = "";
	$.ajax({ 
		url:  './php/login-usr.php',
		type: 'get',
		async:false,
		dataType:"JSON",  	
		success: function(data){  
			usrName = data.usr;
			// alert("ajax返回"+usrName);        //输出php回传的错误信息
		},
        error: function(){
			alert("获取用户的过程中出现问题");
        }
    })
    return usrName;
}

//Used in page -> index, unindentified-function page, 
//	user-function page, root-user-function page.
function logoutFunc(){
	if(confirm("确定要注销吗？")){
		$.ajax({ 
			type: "POST", 
			url: "./php/login.php?action=logout", 
			// dataType: "json", 			 
			success: function(result){ 
			  	// alert("退出成功！点击确定跳转");
			  	window.location.href='./login.html';			  
			},
			error: function(result){
					alert("发生了未知错误！");
	        }
		});
	} 
}

//The login Judgement used for each page.
//It will be removed later .
//If unindentified return 0, else 1.
function loginJugg(){
	var pagename=$("#pagenum").attr("name");
	var usrNameTemp = getUsrName();
	if(usrNameTemp==""){
		loginDivCtrl(false,"",pagename);
		return 0;
	}else{
		loginDivCtrl(true,usrNameTemp,pagename);
		return 1;
	}
}

//Add bar at top-right corner.

function loginDivCtrl(isLogin,usrname,page){
	//page == 1  主页， 不跳转
	if(isLogin){
		var str="<li><a href='#'>欢迎您 "+usrname+ "</a></li><li><a href='#' id='logout'>注销</a></li>";
		$('#loginCtrl').append(str);
		$("#logout").click(function(){ logoutFunc(); })//changed here
	}
	else{
		if(page == "page1"){			
			var str="<li><a href='./login.html'>登录</a></li><li><a href='./signup.html' id='signup'>注册</a></li>";
			$('#loginCtrl').append(str);
		}else{
		//alert("请先登录");
		window.location.href='./login.html';
		}
	}
}

function loginTrigger(){
	var user = $("#inputUser").val(); 
	var pass = $("#inputPassword").val(); 

	if(user==""){ 
		alert("用户名不能为空！"); 
		$("#inputUser").focus(); 
		return false; 
	} 
	if(pass==""){ 
		alert("密码不能为空！"); 
		$("#inputPassword").focus(); 
		return false; 
	} 
	$.ajax({ 
		type: "POST", 
		url: "./php/login.php?action=login", 
		dataType: "json", 
		data: {"user":user,"pass":pass}, 
		success: function(json){ 
		  if(json.status==1){ 
		  	//alert(json.msg);   //取消登录成功后的alert
		  	window.location.href='./function-shell-root.html';
		  }else{ 
		    alert(json.msg); 
		  } 
		},
		error: function(result){
				alert("失败啦老铁！,错误未知！");
            }
	}); 
}