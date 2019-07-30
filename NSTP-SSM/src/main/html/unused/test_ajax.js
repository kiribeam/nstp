$(document).ready(function(){
	data = getEcuData("diagnostic", 0, "root", "666");
	printTable(data, "testtable", "testGroup");
	$("#testtable").listview('refresh');
});