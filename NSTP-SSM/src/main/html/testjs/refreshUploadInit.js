function init(){
	registQuickGeneralTable("ecuMessage/getEcu");

	$('#refreshUploadBtn').click(function(){
		var item = selectCheckbox("checkGroup");
		if(!item || item.length != 1){
			alert("One Single Select!");
			return;
		}
		if(!confirm("确认发起刷新文件处理请求？")) return;
	    var id = item[0];
	    var moduleId = $("#moduleId").val();
	    var bcid = $("#bcid").val();
	    var ecuName = $("#ecuName").val();
	    var appNbid = $("#appNbid").val();
	    var appSwLength = $("#appSwLength").val();
	    var appSwLocation = $("#appSwLocation").val();
	    var subjectName = $("#subjectName").val();
	    var certificateId = $("#certificateId").val();
	    var keyNbid = $("#keyNbid").val();
	    
	    var header = "" + moduleId + bcid + ecuName;
	    if(!header|| !ValidRegex["hex"].test(header) || header.length != 24){
	    	alert("Wrong Input !");
	    	return;
	    }
	    if(!appNbid || !ValidRegex["hex"].test(appNbid) || appNbid.length != 4){
	    	alert("Wrong input !");
	    	return;
	    }
	    if(!appSwLength || !ValidRegex["hex"].test(appSwLength) || appSwLength.length != 4){
	    	alert("Wrong Input!");
	    	return;
	    }

	    var al = parseInt(appSwLength, 16);
	    if(!appSwLocation || !ValidRegex["hex"].test(appSwLocation) || appSwLocation.length != al*16){
	    	alert("Wrong Input!");
	    	return;
	    }

	    var signInfo = "" + subjectName + certificateId + keyNbid;
	    if(!signInfo || !ValidRegex["hex"].test(signInfo) || signInfo.length != 52){
	    	alert("Wrong Input~");
	    	return;
	    }
	    
	   	var $form = $('#refreshForm');
    	$form.attr('action', "refresh/upload" + "/" + id);
    	$form.attr('enctype', "multipart/form-data");
    	$form.attr('method', "post");
    	let itemInput1 = $("<input>");
    	itemInput1.attr("name", "header");
    	itemInput1.attr("value", header);
    	itemInput1.attr("type", "hidden");
    	let itemInput2 = $("<input>");
    	itemInput2.attr("name", "app");
    	itemInput2.attr("value", appNbid + appSwLength + appSwLocation);
    	itemInput2.attr("type", "hidden");
    	let itemInput3 = $("<input>");
    	itemInput3.attr("name", "signInfo");
    	itemInput3.attr("value", signInfo);
    	itemInput3.attr("type", "hidden");
    	$form.append(itemInput1);
    	$form.append(itemInput2);
    	$form.append(itemInput3);
    	$form.submit();

	    $("#searchButton").click();
	});
}

init();