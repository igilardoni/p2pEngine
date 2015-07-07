/**
 * JavaScript for managing contrats
 * @author Michael DUBUIS
 */
var contratList = "contratList";
var itemContratList = "itemContratList"
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function loadContrats(){
	sendQueryEmpty("loadContrats");
}
function loadContrat(id){
	var content = {"id":id};
	sendQuery("loadContrat", content);
}
function loadItemForContrat(){
	var itemKey = $("#itemFavoritesDisplayer #itemKey").text();
	var contrat = $("#contratID").text();
	
	var content = {
			"itemKey":itemKey,
			"contratId":contrat
	};
	sendQuery("loadItemForContrat", content);
}
function newContrat(){
	sendQueryEmpty("newContrat");
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function contratCreated(content) {
	emptyContent();
	$("#content").append(getElement(contratForm[0]));
	$("#contratID").append(document.createTextNode(content.contratId));
	$("#objects").append(getTableItem(itemContratList));
}
function itemForContratLoaded(content) {
	alert($("#contratID").val());
	$("#contratID").text()==content.contratId
	$("#"+itemContratList).append(newRowItemContrat(content));
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getContrat(){
	var div = document.createElement("div");
	div.setAttribute("id", "content");
	div.appendChild(getElement(contratDisplay[0]));
	div.appendChild(getElement(contratTable[0]));
	//div.appendChild(getElement(contratForm[0]));
	var buttonContrat = document.createElement("input");
	buttonContrat.setAttribute("value", "New Contrat");
	buttonContrat.setAttribute("type", "button");
	buttonContrat.setAttribute("onclick", "newContrat();");
	div.appendChild(buttonContrat);
	return div;
}

function newRowItemContrat(content) {
	var row = document.createElement("tr");
	row.setAttribute("id", "contrat"+removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowTitle");
	cell1.appendChild(document.createTextNode(content.title));
	// cell1.setAttribute("onclick", "loadItemFavorites('"+content.itemKey+"');");
	row.appendChild(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowDescription");
	// cell2.setAttribute("onclick", "loadItemFavorites('"+content.itemKey+"');");
	if(content.description.length > 100)
		cell2.appendChild(document.createTextNode(content.description.substring(0, 100)+" [...]"));
	else
		cell2.appendChild(document.createTextNode(content.description));
	row.appendChild(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	removeButton.setAttribute("class", "buttonRemove");
	removeButton.setAttribute("onclick", "removeItemContrat('"+content.itemKey+"');");
	//removeButton.appendChild(document.createTextNode("Remove"));
	cell3.appendChild(removeButton);
	row.appendChild(cell3);
	return row;
}