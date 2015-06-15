/**
 * JavaScript for managing items
 * @author Michael DUBUIS
 */
var itemList = "itemList";
var itemSearchList = "itemSearchList";
var itemForm = "itemForm";
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// Ask to the model to add a new item
function addItem(){
	var title = $("#title").val();
	var category = $("#category").val();
	var contact = $("#contact").val();
	var country = $("#country").val();
	var date = $("#date").val();
	var description = $("#description").val();
	var image = $("#image").val();
	var lifetime = $("#lifetime").val();
	var type = $("#type").val();
	// Fields verification
	var content = {
			"title":title,
			"category":category,
			"contact":contact,
			"country":country,
			"date":date,
			"description":description,
			"image":image,
			"lifetime":lifetime,
			"type":type
	};
	var data = {"query":"addItem", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to remove item's itemKey
function removeItem(itemKey){
	var content = {"itemKey":itemKey};
	var data = {"query":"removeItem", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to update item's itemKey
function updateItem(itemKey){
	var title = $("#title").val();
	var category = $("#category").val();
	var contact = $("#contact").val();
	var country = $("#country").val();
	var date = $("#date").val();
	var description = $("#description").val();
	var image = $("#image").val();
	var lifetime = $("#lifetime").val();
	var type = $("#type").val();
	// Fields verification
	var content = {
			"itemKey":itemKey,
			"title":title,
			"category":category,
			"contact":contact,
			"country":country,
			"date":date,
			"description":description,
			"image":image,
			"lifetime":lifetime,
			"type":type
	};
	var data = {"query":"updateItem", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model data of item's itemKey
function loadItem(itemKey){
	var content = {"itemKey":itemKey};
	var data = {"query":"loadItem", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to send all data items (for the current user)
function loadItems(){
	var content = {};
	var data = {"query":"loadItems", "content":content};
	webSocket.send(JSON.stringify(data));
}

function editItem(itemKey){
	var content = {
			"itemKey":itemKey
	};
	var data = {"query":"loadItem", "content":content};
	webSocket.send(JSON.stringify(data));
}

function loadCategories(){
	var content = {};
	var data = {"query":"loadCategories", "content":content};
	webSocket.send(JSON.stringify(data));
}

function loadItemSearchField(){
	var content = {};
	var date = {"query":"loadItemSearchField", "content":content};
	webSocket.send(JSON.stringify(data));
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function itemRemoved(content){
	$("#"+content.itemKey).detach();
	$("#"+itemForm).replaceWith(getItemAddForm());
}

function itemUpdated(content){
	$("#"+content.itemKey).replaceWith(newRowItem(content));
	$("#"+itemForm).replaceWith(getItemAddForm());
}

function itemsLoaded(content){
	$("#"+itemList).append(newRowItem(content));
}

function itemAdded(content){
	$("#"+itemList).append(newRowItem(content));
	$("#"+itemForm).replaceWith(getItemAddForm());
}

function itemLoaded(content){
	$.each(content, function(key, value){
		$("#"+itemForm+" #"+key).val(value);
	});
	$("#addButton").attr("onclick", "updateItem('"+content.itemKey+"');");
	$("#addButton").attr("value", "Update Item");
}

function categoryLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.category));
	$("#category").append(option);
}

function itemSearchFieldLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.field));
	$("#field").append(option);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function newRowItem(content){
	var row = document.createElement("tr");
	row.setAttribute("id", content.itemKey);
	// Title cell
	var cell1 = document.createElement("td");
	cell1.appendChild(document.createTextNode(content.title));
	row.appendChild(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	cell2.appendChild(document.createTextNode(content.description));
	row.appendChild(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	// Edit Button
	var removeButton = document.createElement("input");
	removeButton.setAttribute("class", "buttonEdit");
	removeButton.setAttribute("type", "button");
	removeButton.setAttribute("onclick", "editItem('"+content.itemKey+"');");
	removeButton.setAttribute("value", "Edit");
	cell3.appendChild(removeButton);
	// Remove Button
	var removeButton = document.createElement("input");
	removeButton.setAttribute("class", "buttonRemove");
	removeButton.setAttribute("type", "button");
	removeButton.setAttribute("onclick", "removeItem('"+content.itemKey+"');");
	removeButton.setAttribute("value", "Remove");
	cell3.appendChild(removeButton);
	row.appendChild(cell3);
	return row;
}

// TODO A SUPPRIMER OU A VOIR !
function updateSeachField(){
	if($("#field").val == "category"){
		var select = document.createElement("select");
		select.setAttribute("name", "category");
		select.setAttribute("id", "category");
		$("#search").replaceWith(select);
		loadCategories();
		$("#category").attr("id", "search");
	}
}