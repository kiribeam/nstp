//start trial
$(function(){
	setNavigateBar();
})

//register
$(function(){
	$(".link").each(function(){
		$(this).click(function(){
			$(this).next().toggle();
		});
	});
	
	$(".changeBeast").each(function(){
		$(this).click(function(){
			$(".changeBeast").each(function(){
				$(this).children(":first").next().css("background", "#ffffff");    //按下样式
				$(this).children(":first").next().css("color", "#333");
				});
			var link = $(this).children(":first").text();
			$(this).children(":first").next().css("background", "#ff0033");//3E4561
			$(this).children(":first").next().css("color", "#FFFFFF");
			changeBeast("beast", link);
		});
	});
});

changeBeast("beast", "illustration.html");