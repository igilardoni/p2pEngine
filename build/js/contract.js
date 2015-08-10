/**
 * JavaScript for managing contrats
 * @author Michael DUBUIS
 */
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
	$("#content").append(getElement(contratForm));
	$("#objects").append(getElement(itemContratTable));
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
	if(titleNewContrat == undefined)
		titleNewContrat = "";
	var content = {"title":titleNewContrat};
	sendQuery("newContrat", content);
}
function removeContrat(id) {
	if(confirm("Are you sure to remove this contract ?")){
		var content = {
				"contratID":id
		}
		sendQuery("removeContrat", content);
		return true;
	}
	return false;
}
function deleteContrat() {
	if(removeContrat($("#contratID").text()))
		includeContrat();
}
function removeItemContrat(itemKey) {
	if(confirm("Are you sure to remove this item in contract ?")){
		var contratID = $("#contratID").text();
		var content = {
				"contratID":contratID,
				"itemKey":itemKey
		};
		sendQuery("removeItemContrat", content);
	}
}
function renameContrat() {
	var contratID = $("#contratID").text();
	var title = $("#contratForm"+" #title").val();
	var content = {
			"contratID":contratID,
			"title":title
	};
	sendQuery("renameContrat", content);
}
function changeContratExchangeRule(itemKey, itemTitle, from, fromNick, select) {
	var to = $(select).val();
	var toNick = $(select).find("option:selected").text();
	var contratID = $("#contratID").text();
	
	var content = {
			"contratID":contratID,
			"itemKey":itemKey,
			"itemTitle":itemTitle,
			"from":from,
			"fromFriendlyNick": fromNick,
			"to":to,
			"toFriendlyNick":toNick
	};
	sendQuery("changeContratExchangeRule", content);
}
function addClause() {
	var content = {
			"contratID":$("#contratID").text()
	}
	sendQuery("addClause", content);
}
function saveClause(id) {
	var content = {
			"contratID":$("#contratID").text(),
			"id":id,
			"title":$("#"+removePunctuation(id)).find("#title").val(),
			"value":$("#"+removePunctuation(id)).find("#value").val()
	};
	sendQuery("saveClause", content);
}
function removeClause(id) {
	var content = {
			"contratID":$("#contratID").text(),
			"id":id
	};
	sendQuery("removeClause", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function contratCreated(content) {
	emptyContent();
	$("#content").append(getElement(contratForm));
	$("#contratID").append(content.contratID);
	$("#objects").append(getElement(itemContratTable));
	var editButton = $("#contratForm h1 a");
	$("#contratForm").find("h1").text(content.title);
	$("#contratForm").find("h1").append(editButton);
	var contentBis = {
			"contratID" : content.contratID
	};
	sendQuery("loadContentContrat", contentBis);
}
function itemForContratLoaded(content) {
	$("#contratID").text(content.contratId);
	$("#"+itemContratList).append(newRowItemContrat(content));
	printFeedback(content.feedback, true);
}
function itemForContratNotLoaded(content) {
	printFeedback(content.feedback, false);
}
function contratsLoaded(content) {
	$("#"+contratList).append(newRowContrat(content));
}
function transfertRuleLoaded(content){
	$("#rules table").append(newRowTransfertRule(content));
}
function clauseLoaded(content) {
	$(displayClause(content)).insertBefore($("#clauses .buttonAdd"));
}
function clauseAdded(content) {
	$(formClause(content)).insertBefore($("#clauses .buttonAdd"));
}
function clauseSaved(content){
	$("#"+removePunctuation(content.id)).replaceWith(displayClause(content));
}
function clauseRemoved(content){
	printFeedback(content.feedback, true);
	$("#"+removePunctuation(content.id)).detach();
}
function clauseNotRemoved(content){
	printFeedback(content.feedback, false);
}
function contratLoaded(content) {
	var editButton = $("#contratForm h1 a");
	$("#contratForm").find("h1").text(content.title);
	$("#contratForm").find("h1").append(editButton);
	$("#contratID").append(content.contratID);
	var contentBis = {
			"contratID" : content.contratID
	};
	sendQuery("loadContentContrat", contentBis);
}
function contratNotLoaded(content) {
	printFeedback(content.feedback, false);
}
function signatoryAdded(content) {
	$("#signatories").append(newRowSignatory(content));
}
function contratRemoved(content) {
	var id = removePunctuation(content.contratID);
	$("#"+id).detach();
	printFeedback(content.feedback, true);
}
function contratNotRemoved(content) {
	printFeedback(content.feedback, false);
}
function itemContratNotRemoved(content) {
	printFeedback(content.feedback, false);
}
function itemContratRemoved(content) {
	if($("#contratID").text() != content.contratID)
		return;
	$("#itemContratList #"+removePunctuation(content.itemKey)).detach();
	$("#rules #"+removePunctuation(content.itemKey)).detach();
	printFeedback(content.feedback, true);
}
function signatoryRemoved(content) {
	if($("#contratID").text() != content.contratID)
		return;
	$("#signatories #signatories"+content.publicKey).detach();
}
function contractRenamed(content) {
	if(content.contratID != $("#contratID").text())
		return;
	printFeedback(content.feedback, true);
	var button = {element:"a", attributes:{"class":"button buttonEdit", onclick:"renameContractForm()", title:"Rename"}, inside:[]};
	$("#contratForm h1").empty();
	$("#contratForm h1").append(content.title);
	$("#contratForm h1").append(getElement(button));
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function renameContractForm(){
	var name = $("#contratForm h1").text();
	var input = document.createElement("input");
	$(input).attr("type", "text");
	$(input).attr("value", name);
	$(input).attr("id", "title");
	$(input).attr("name", "title");
	var button = document.createElement("a");
	$(button).addClass("button");
	$(button).addClass("buttonValidate");
	$(button).attr("onclick", "renameContrat();");
	$("#contratForm h1").empty();
	$("#contratForm h1").append(input);
	$("#contratForm h1").append(button);
}

function getContrat(){
	return getElement(contratTable);
}

function newRowContrat(content) {
	var row = $("<tr><td class=\"rowTitle\"></td><td class=\"rowState\"></td><td class=\"rowActions\"><a class=\"button buttonRemove\"></a></td></tr>");
	
	$(row).attr("id", removePunctuation(content.contratID));
	$(row).find(".rowTitle").append(content.title);
	$(row).find(".rowTitle").attr("onclick", "loadContrat('"+content.contratID+"');");
	$(row).find(".rowState").append(content.state);
	$(row).find(".rowState").attr("onclick", "loadContrat('"+content.contratID+"');");
	$(row).find(".rowActions buttonRemove").attr("onclick", "removeContrat('"+content.contratID+"');");
	return row;
}
// For add an item in table item contrat list
function newRowItemContrat(content) {
	var row = getElement(itemContratRow);
	$(row).attr("id", removePunctuation(content.itemKey));
	$(row).find(".rowTitle").append(content.title);
	if(content.description.length > 100)
		$(row).find(".rowDescription").append(content.description.substring(0, 100)+" [...]");
	else
		$(row).find(".rowDescription").append(content.description);
	$(row).find(".rowActions .buttonRemove").attr("onclick", "removeItemContrat('"+content.itemKey+"');");
	return row;
}
// For add a transfert rule in table
function newRowTransfertRule(content) {
	var row = getElement(ruleRow);
	$(row).find(".labelItem").text(content.itemTitle);
	$(row).find(".itemKey").text(content.itemKey);
	$(row).find(".labelFrom").text(content.fromFriendlyNick);
	$(row).find(".publicKey").text(content.from);
	$('#signatories td').each(function(){ 
		   var label1 = $(this).find("label.hidden").text();
		   var label2 = $(this).find("label:not(.hidden)").text();
		   var option = document.createElement("option");
		   $(option).val(label1);
		   $(option).append(label2);
		   $(row).find(".userSelect").append(option);
	});
	$(row).find(".userSelect").val(content.to);
	$(row).find(".userSelect").attr("onchange", "changeContratExchangeRule('" +
			content.itemKey+"','" +
			content.itemTitle+"','"+
			content.from+"','" +
			content.fromFriendlyNick+
			"',this);");
	return row;
}
// For add a signatory in table
function newRowSignatory(content) {
	var row = document.createElement("tr");
	$(row).attr("id", "signatories"+removePunctuation(content.publicKey));
	
	var cell1 = document.createElement("td");
	var label11 = document.createElement("label");
	$(label11).append(content.publicKey);
	$(label11).attr("class", "hidden");
	var label21 = document.createElement("label");
	$(label21).append(content.friendlyNick);
	$(cell1).append(label11);
	$(cell1).append(label21);
	
	$(row).append(cell1);
	
	return row;
}
function displayClause(content) {
	var div = getElement(clauseContratDisplay);
	$(div).attr("id", removePunctuation(content.id));
	$(div).find("#title").text(content.title);
	$(div).find("#value").text(content.value);
	$(div).find(".buttonEdit").attr("onclick", "editClause('"+content.id+"');");
	$(div).find(".buttonRemove").attr("onclick", "removeClause('"+content.id+"');");
	return div;
}
function editClause(id) {
	var content = {
		"id" : id,
		"title" : $("#"+removePunctuation(id)).find("#title").text(),
		"value" : $("#"+removePunctuation(id)).find("#value").text()
	};
	$("#"+removePunctuation(id)).replaceWith(formClause(content));
}
function formClause(content) {
	var div = getElement(clauseContratForm);
	$(div).attr("id", removePunctuation(content.id));
	$(div).find("#title").val(content.title);
	$(div).find("#value").val(content.value);
	$(div).find(".buttonValidate").attr("onclick", "saveClause('"+content.id+"');");
	$(div).find(".buttonRemove").attr("onclick", "removeClause('"+content.id+"');");
	return div;
}