function init(){
	registQuickGeneralTable("ecuMessage/getEcu");
	
	$('#sysECUAddBtn').click(function(){
		if(!confirm("确定添加ECU？")) return;
		var newEcuNum = $('#sysECUAddNum').val();
		if(newEcuNum<=0 || newEcuNum>100){
			alert("输入数量需要在1-100之间！");
			return;
		}
		let data = {};
		data["number"] = newEcuNum;
		console.log(create("ecuMessage/createEcu", data));
		$("#searchButton").click();
	});
	//ECU删除
	$('#sysECUDelBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		console.log("Select:", item);
		if(!item.length>0){
			alert("您没有选择数据");
			return;
		}
		if(!confirm("确认删除ECU?")) return;
		var list = JSON.stringify(item);
		console.log(del("ecuMessage/delEcu", list));
		$("#searchButton").click();
	});
	
	//自定义ECUID
	$('#ECUKeySetBtn').click(function(){
		var inputEcuSet = $("#ecu_key_set").val(); 
		//console.log("inputEcuSet:",inputEcuSet);
		if(inputEcuSet.length!=32 || !ValidRegex["ecuid"].test(inputEcuSet)){
			alert("请填写16字节（32字符）的ECUID！");
			return;
		}
		if(!confirm("确认新建ECUID？")) return;
		console.log(create("ecuMessage/createEcu/" + inputEcuSet));
		$("#searchButton").click();
	});	
}
init();