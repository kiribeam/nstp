function changeBeast(link){
	var ajaxData;
	$.ajax({
		url: "/NSTP-SSM/" + "beasts/" + link,
		type : "get",
		dataType : "json",
		success : function(data){ 
			$('#beast').html(data); 
			init();
		},
		error: function(data){
			//TODO
			alert(data);
		}
	});	
}
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
			$(this).children(":first").next().css("background", "#3E4561");
			$(this).children(":first").next().css("color", "#FFFFFF");
			changeBeast(link);
		});
	});
});