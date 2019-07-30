function selectCheckbox(group){
	var items = [];
	$("input:checkbox[name='" + group + "']:checked").each(function(){
		items.push($(this).val());
	});
	console.log(items);
	return items;
}