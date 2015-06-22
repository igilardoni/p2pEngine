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
/**
 * Ask to the model to add a new item
 */
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
	sendQuery("addItem", content);
}
/**
 * Ask to the model to remove item's itemKey
 * @param itemKey
 */
function removeItem(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("removeItem", content);
}
/**
 * Ask to the model to update item's itemKey
 * @param itemKey
 */
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
	sendQuery("updateItem", content);
}
/**
 * Ask to the model data of item's itemKey
 * @param itemKey
 */
function loadItem(itemKey){
	var content = {
			"itemKey":itemKey
			};
	sendQuery("loadItem", content);
}
/**
 * Ask to the model to send all data items (for the current user)
 */
function loadItems(){
	sendQueryEmpty("loadItems");
}
/**
 * Ask to the model to send item's itemKey
 * @param itemKey
 */
function editItem(itemKey){
	var content = {
			"itemKey":itemKey
	};
	sendQuery("loadItem", content);
}
/**
 * Ask to the model to send item's category
 */
function loadCategories(){
	sendQueryEmpty("loadCategories");
}
/**
 * Ask to the model to send item's type
 */
function loadType(){
	sendQueryEmpty("loadType");
}
/**
 * Ask to the model to send item's searchable field
 */
function loadItemSearchField(){
	sendQueryEmpty("loadItemSearchField");
}
/**
 * Ask to the model to send item's category for searching
 */
function loadItemSearchFieldCategory(){
	sendQueryEmpty("loadItemSearchFieldCategory");
}
/**
 * Ask to the model to send item's type for searching
 */
function loadItemSearchFieldType(){
	sendQueryEmpty("loadItemSearchFieldType");
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
	$("#addButton").val("Update Item");
}

function categoryLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.category));
	$("#category").append(option);
}

function typeLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.type));
	$("#type").append(option);
}

function itemSearchFieldLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.field));
	$("#field").append(option);
}

function itemSearchFieldsLoaded(content){
	var option = document.createElement("option");
	option.appendChild(document.createTextNode(content.option));
	$("#searchField").append(option);
}

function updateSearchField(){
	var field = $("#field").val();
	switch(field){
	case "category":
		var select = document.createElement("select");
		select.setAttribute("id", "searchField");
		select.setAttribute("name", "searchField");
		$("#searchField").replaceWith(select);
		loadItemSearchFieldCategory();
		break;
	case "type":
		var select = document.createElement("select");
		select.setAttribute("id", "searchField");
		select.setAttribute("name", "searchField");
		$("#searchField").replaceWith(select);
		loadItemSearchFieldType();
		break;
	default:
		var input = document.createElement("input");
		input.setAttribute("id", "searchField");
		input.setAttribute("name", "searchField");
		input.setAttribute("type", "text");
		$("#searchField").replaceWith(input);
	}
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/**
 * Get a form for add/update item, without button
 * @returns Element "div"
 */
function getItemFormWithoutButton(){
	var div = document.createElement("div");
	div.setAttribute("id", itemForm);
	for ( var i = 0 ; i < formItemAdd.length; i++) {
		var p = document.createElement("p");
		var element = document.createElement(formItemAdd[i].element);
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formItemAdd[i].label))
		label.setAttribute("for", formItemAdd[i].attributes.name);
		label.setAttribute("id", "label_"+formItemAdd[i].attributes.name);
		p.appendChild(label);
		$.each(formItemAdd[i].attributes, function(key, value){
			element.setAttribute(key, value);
		});
		p.appendChild(element);
		div.appendChild(p);
	}
	loadCategories();
	loadType();
	return div;
}
/**
 * Get a form for add item (with buttons "Add" and "Cancel")
 * @returns Element "div"
 */
function getItemAddForm(){
	var div = getItemFormWithoutButton();
	for( var i = 0 ; i < buttonItemAdd.length; i++){
		var a = document.createElement("a");
		$.each(buttonItemAdd[i], function(key, value){
			a.setAttribute(key, value);
		});
		div.appendChild(a);
	}
	return div;
}
/**
 * Get a form for search item with button "Search"
 * @returns Element "div"
 */
function getItemSearchForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "search");
	for ( var i = 0 ; i < formSearchItem.length; i++) {
		var p = document.createElement("p");
		var element = document.createElement(formSearchItem[i].element);
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formSearchItem[i].label))
		label.setAttribute("for", formSearchItem[i].attributes.name);
		label.setAttribute("id", "label_"+formSearchItem[i].attributes.name);
		p.appendChild(label);
		$.each(formSearchItem[i].attributes, function(key, value){
			element.setAttribute(key, value);
		});
		p.appendChild(element);
		div.appendChild(p);
	}
	for( var i = 0 ; i < buttonSearchItem.length; i++){
		var p = document.createElement("p");
		var input = document.createElement("input");
		$.each(buttonSearchItem[i], function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	return div;
}
/**
 * Get a table for display item list
 * @param id - put this id to the table
 * @returns Element "table"
 */
function getTableItem(id){
	var table = document.createElement("table");
	table.setAttribute("id", id);
	var theader = document.createElement("thead");
	var tr = document.createElement("tr");
	for( var i = 0 ; i < tableItem.length; i++){
		var th = document.createElement("th");
		$.each(tableItem[i].attributes, function(key, value){
			th.setAttribute(key, value);
		});
		th.appendChild(document.createTextNode(tableItem[i].text));
		tr.appendChild(th);
	}
	theader.appendChild(tr);
	table.appendChild(theader);
	return table;
}
/**
 * Get a row for an item
 * @param content JSONObject (title, description and itemKey)
 * @returns Element "tr"
 */
function newRowItem(content){
	var row = document.createElement("tr");
	row.setAttribute("id", content.itemKey);
	// Title cell
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowTitle");
	cell1.appendChild(document.createTextNode(content.title));
	row.appendChild(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowDescription");
	cell2.appendChild(document.createTextNode(content.description));
	row.appendChild(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowActions");
	// Edit Button
	var removeButton = document.createElement("a");
	removeButton.setAttribute("class", "buttonEdit");
	removeButton.setAttribute("onclick", "editItem('"+content.itemKey+"');");
	//removeButton.appendChild(document.createTextNode("Edit"));
	cell3.appendChild(removeButton);
	// Remove Button
	var removeButton = document.createElement("a");
	removeButton.setAttribute("class", "buttonRemove");
	removeButton.setAttribute("onclick", "removeItem('"+content.itemKey+"');");
	//removeButton.appendChild(document.createTextNode("Remove"));
	cell3.appendChild(removeButton);
	row.appendChild(cell3);
	return row;
}
/**
 * Erase form with id "itemForm"
 */
function cancelItem(){
	$("#"+itemForm).replaceWith(getItemAddForm());
}