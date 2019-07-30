function init(){
	registQuickGeneralTable("ecuMessage/getEcu");
	$("#diagRequestBtn").click(function(){
		var item = selectCheckbox("checkGroup");
		if(item.length != 1){
			alert("One Single Select!");
			return;
		}
		var head = $('#diag_exec_head').val();
		var rand = $('#diag_exec_rand').val();
		var keyId = $('#diag_key_id').val();

		if(head =="" || rand ==""){
			alert("请填写表单内容");
			return;
		}else if(head.length != 1*2 ||  rand.length != 15*2){
			alert("表单内数值字符数不正确。请检查");
			return;
		}else if(keyId >20 || keyId<= 0){
			alert("Key ID 的范围在1-20之间");
			return;
		}
		if(!confirm("确认发起安全诊断申请？")) return;
		var url = 'diagnostic/exec/' + item[0] + "/" + head + "/" + rand +"/" + keyId;
		var data = update(url);
		$("#diag_session_key_res").val(data["sessionKey"]);
		$("#diag_response_res").val(data["response"]);
	});	
}
init();