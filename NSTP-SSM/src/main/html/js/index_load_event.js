//Just kitte by Kiri.
//need general-log-function.js
$(function(){
//密钥管理功能按钮
	$('#subBlock3').click(function(){
		var isLogin = loginJugg();
		if(isLogin){
			$('#loginCtrl').remove();     //为了刷新效果
			window.location.href='./function-shell-root.html';
		}else{
			//alert("请先登录");
			window.location.href='./login.html';
		}
	})
//下线模拟功能按钮
	$('#subBlock2').click(function(){
		var isLogin = loginJugg();
		if(isLogin){
			$('#loginCtrl').remove();     //为了刷新效果
			window.location.href='./function-shell-root.html';
		}else{
			//alert("请先登录");
			window.location.href='./login.html';
		}
	})
//安全刷新功能按钮
	$('#subBlock1').click(function(){
		var isLogin = loginJugg();
		if(isLogin){
			$('#loginCtrl').remove();     //为了刷新效果
			window.location.href='./function-shell-root.html';
		}else{
			//alert("请先登录");
			window.location.href='./login.html';
		}
	})
	$(".sub-header").hide();
	$(".subTransparent").hide();
	$(".sub-block1").css("cursor","pointer");
	$(".sub-block2").css("cursor","pointer");
	$(".sub-block3").css("cursor","pointer");
	$(".sub-header").css("cursor","pointer");

	$('#subBlock1').hover(
		function(){
			$("#subBlockP1").css("filter","blur(2.5px)");
			$('#subBlockP1').css("background-color","#353535")
			$('#subBlockP1').css("opacity","0.7")
			$("#GoRefresh").stop().slideDown();

		},function(){
			$("#subBlockP1").css("filter","blur(0px)");
			$('#subBlockP1').css("background-color","transparent")
			$('#subBlockP1').css("opacity","1")		
			$("#GoRefresh").stop().slideUp();	
		});
	$('#subBlock2').hover(
		function(){
			$("#subBlockP2").css("filter","blur(2.5px)");
			$('#subBlockP2').css("background-color","#353535")
			$('#subBlockP2').css("opacity","0.4")
			$("#GoDownload").stop().slideDown();

		},function(){
			$("#subBlockP2").css("filter","blur(0px)");
			$('#subBlockP2').css("background-color","transparent")
			$('#subBlockP2').css("opacity","1")		
			$("#GoDownload").stop().slideUp();	
		});
	$('#subBlock3').hover(
		function(){
			$("#subBlockP3").css("filter","blur(2.5px)");
			$('#subBlockP3').css("background-color","#353535")
			$('#subBlockP3').css("opacity","0.4")
			$("#GoKeymanager").stop().slideDown();

		},function(){
			$("#subBlockP3").css("filter","blur(0px)");
			$('#subBlockP3').css("background-color","transparent")
			$('#subBlockP3').css("opacity","1")		
			$("#GoKeymanager").stop().slideUp();	
		}
	);
});
