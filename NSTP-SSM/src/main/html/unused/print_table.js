
function printTable(data, tableId, checkGroup){

	table = $("#"+tableId);	
	table.children().remove();
	table.append('<thead class="text-center" sytle="text-align; center;"></thead>');
	thead = table.children(":first-child");
	thead.append("<tr class=\"mytable-head\"></tr>");
	thead.children(":first-child").append("<th>"+ ""+"</th>");
	for(key in data[0]){
		thead.children(":first-child").append("<th>" + key + "</th>");
	}
	checkboxPrefix = "<input type=\"checkbox\" name=\"" + checkGroup + "\" value=\"";
	checkboxSuffix = "\" />";
	table.append('<tbody></tbody>');
	tbody = table.children(":last-child");
	for(obj in data){
		tbody.append("<tr style=\"background: #ffffff; height: 10px;\"></tr>");
		tbody.children(":last-child").append("<td>" + checkboxPrefix + data[obj]["id"] + checkboxSuffix + "</td>");
		for(key in data[obj]){
			tbody.children(":last-child").append("<td>" + data[obj][key] + "</td>");
		}
	}
}
