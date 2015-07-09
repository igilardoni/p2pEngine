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
	var content = {"contratID":id};
	sendQuery("loadContrat", content);
	emptyContent();
	$("#content").append(getElement(contratForm[0]));
	$("#objects").append(getTableItem(itemContratList));
}
function loadItemForContrat(){
	var itemKey = $("#itemFavoritesDisplayer #itemKey").text();
	if($("#contratID").text() == ""){
		newContrat();
	}else{
		var contrat = $("#contratID").text();
		var content = {
				"itemKey":itemKey,
				"contratID":contrat
		};
		sendQuery("loadItemForContrat", content);
	}
}
function newContrat(){
	var titleNewContrat = $("#titleNewContrat").val();
	var content = {"title":titleNewContrat};
	sendQuery("newContrat", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function contratCreated(content) {
	emptyContent();
	$("#content").append(getElement(contratForm[0]));
	$("#contratID").append(document.createTextNode(content.contratID));
	$("#objects").append(getTableItem(itemContratList));
	$("#contratForm").find("h1").text(content.title);
}
function itemForContratLoaded(content) {
	$("#contratID").text(content.contratId);
	$("#"+itemContratList).append(newRowItemContrat(content));
}
function contratsLoaded(content) {
	$("#"+contratList).append(newRowContrat(content));
}
function transfertRuleLoaded(content){
	$("#rules table").append(newRowTransfertRule(content));
}
function contratLoaded(content) {
	$("#contratForm").find("h1").text(content.title);
	$("#contratID").append(document.createTextNode(content.contratID));
}
function signatoryAdded(content) {
	$("#signatories").append(newRowSignatory(content));
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
	div.appendChild(getElement(contratDisplay[1]));
	return div;
}
function newRowContrat(content) {
	var row = document.createElement("tr");
	row.setAttribute("id", removePunctuation(content.contratID));
	row.setAttribute("onclick", "loadContrat('"+content.contratID+"');");
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowTitle");
	cell1.appendChild(document.createTextNode(content.title));
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowState");
	cell2.appendChild(document.createTextNode(content.state));
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowActions");
	row.appendChild(cell1);
	row.appendChild(cell2);
	row.appendChild(cell3);
	return row;
}
// For add an item in table item contrat list
function newRowItemContrat(content) {
	var row = document.createElement("tr");
	row.setAttribute("id", "contrat"+removePunctuation(content.contratID));
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
// For add a transfert rule in table
function newRowTransfertRule(content) {
	var row = document.createElement("tr");
	row.setAttribute("id", "rule"+removePunctuation(content.itemKey));
	
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowItem")
	var label11 = document.createElement("label");
	label11.appendChild(document.createTextNode(content.itemKey));
	label11.setAttribute("class", "hidden");
	var label21 = document.createElement("label");
	label21.appendChild(document.createTextNode(content.itemTitle));
	cell1.appendChild(label11);
	cell1.appendChild(label21);
	
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowFrom");
	var label12 = document.createElement("label");
	label12.appendChild(document.createTextNode(content.from));
	label12.setAttribute("id", "from");
	label12.setAttribute("class", "hidden");
	var label22 = document.createElement("label");
	label22.appendChild(document.createTextNode(content.fromFriendlyNick));
	cell2.appendChild(label12);
	cell2.appendChild(label22);
	
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowTo");
	if(content.to)
		cell3.appendChild(document.createTextNode(content.to));
	
	row.appendChild(cell1);
	row.appendChild(cell2);
	row.appendChild(cell3);
	return row;
}
// For add a signatory in table
function newRowSignatory(content) {
	var row = document.createElement("tr");
	row.setAttribute("id", "signatories"+removePunctuation(content.publicKey));
	
	var cell1 = document.createElement("td");
	var label11 = document.createElement("label");
	label11.appendChild(document.createTextNode(content.publicKey));
	label11.setAttribute("class", "hidden");
	var label21 = document.createElement("label");
	label21.appendChild(document.createTextNode(content.friendlyNick));
	cell1.appendChild(label11);
	cell1.appendChild(label21);
	
	row.appendChild(cell1);
	
	return row;
}