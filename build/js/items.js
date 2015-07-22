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
	var title = $("#"+itemForm+" #title").val();
	var category = $("#"+itemForm+" #category").val();
	var contact = $("#"+itemForm+" #contact").val();
	var country = $("#"+itemForm+" #country").val();
	var date = $("#"+itemForm+" #date").val();
	var description = $("#"+itemForm+" #description").val();
	var image = $("#"+itemForm+" #image").attr("src");
	if(image == null) image = "";
	var lifetime = $("#"+itemForm+" #lifetime").val();
	var type = $("#"+itemForm+" #type").val();
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
	if(confirm("Are you sure to remove this item ?")){
		var content = {"itemKey":itemKey};
		sendQuery("removeItem", content);
	}
}
/**
 * Ask to the model to update item's itemKey
 * @param itemKey
 */
function updateItem(itemKey){
	var title = $("#"+itemForm+" #title").val();
	var category = $("#"+itemForm+" #category").val();
	var contact = $("#"+itemForm+" #contact").val();
	var country = $("#"+itemForm+" #country").val();
	var date = $("#"+itemForm+" #date").val();
	var description = $("#"+itemForm+" #description").val();
	var image = $("#"+itemForm+" #image").attr("src");
	if(image == null) image = "";
	var lifetime = $("#"+itemForm+" #lifetime").val();
	var type = $("#"+itemForm+" #type").val();
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
function searchItem(){
	if($("#search").val()!=""){
		$("#"+itemSearchList+" tbody").empty();
		var content = {"search":$("#search").val()};
		sendQuery("searchItem", content);
	}else{
		alert("Impossible to launch an empty search !")
	}
}
function loadItemSearch(itemKey){
	if($("aside").hasClass("hidden"))
		switchFavorites();
	content = {
			"itemKey":itemKey
	};
	sendQuery("loadItemSearch", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function itemRemoved(content){
	var id = removePunctuation(content.itemKey);
	$("#"+id).detach();
	$("#"+itemForm).replaceWith(getItemAddForm());
}

function itemUpdated(content){
	var id = removePunctuation(content.itemKey);
	$("#"+id).replaceWith(newRowItem(content));
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
	itemFormComplet();
	setTimeout(function(){
		$.each(content, function(key, value){
			$("#"+itemForm+" #"+key).val(value);
		});
		$("#"+itemForm+" #image").attr("src", content.image);
		$("#itemForm").find("h1").empty();
		$("#itemForm").find("h1").append("Item : "+content.itemKey);
		$("#addButton").attr("onclick", "updateItem('"+content.itemKey+"');");
		$("#addButton").empty();
		$("#addButton").append("Update Item");
	}, 10);
}

function categoryLoaded(content){
	var option = document.createElement("option");
	$(option).append(content.category);
	$("#"+itemForm+" #category").append(option);
}

function typeLoaded(content){
	var option = document.createElement("option");
	$(option).append(content.type);
	$("#"+itemForm+" #type").append(option);
}

function itemSearchFieldLoaded(content){
	var option = document.createElement("option");
	$(option).append(content.field);
	$("#field").append(option);
}

function itemSearchFieldsLoaded(content){
	var option = document.createElement("option");
	$(option).append(content.option);
	$("#searchField").append(option);
}

function updateSearchField(){
	var field = $("#field").val();
	switch(field){
	case "category":
		var select = document.createElement("select");
		$(select).attr("id", "searchField");
		$(select).attr("name", "searchField");
		$("#searchField").replaceWith(select);
		loadItemSearchFieldCategory();
		break;
	case "type":
		var select = document.createElement("select");
		$(select).attr("id", "searchField");
		$(select).attr("name", "searchField");
		$("#searchField").replaceWith(select);
		loadItemSearchFieldType();
		break;
	default:
		var input = document.createElement("input");
		$(input).attr("id", "searchField");
		$(input).attr("name", "searchField");
		$(input).attr("type", "text");
		$("#searchField").replaceWith(input);
	}
}

function itemSearchFound(content){
	// TODO Verify if already exist
	/*if($("#"+content.itemKey).lenght == 0)*/
		$("#"+itemSearchList).append(newRowItemSearch(content));
	/*else
		$("#"+itemSearchList+" #"+content.itemKey).replaceWith(newRowItem(content));*/
}
function itemSearchLoaded(content) {
	printFeedback(content.feedback, true);
	displayItemFavorites(content);
}
function itemSearchNotLoaded(content) {
	printFeedback(content.feedback, false);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getHome(){
	var content = document.createElement("section");
	$(content).attr("id", "content");
	var div = document.createElement("div");
	$(div).attr("id", "items");
	$(div).append(getTableItem(itemList));
	$(content).append(div);
	$(div).append(getItemAddForm());
	$(content).append(div);
	return content;
}

function getSearchItem(){
	var content = document.createElement("div");
	$(content).attr("id", "content");
	var div = document.createElement("div");
	$(div).attr("id", "searchForm");
	for ( var i = 0 ; i < searchForm.length ; i++ ) {
		$(div).append(getElement(searchForm[i]));
	}
	$(content).append(div);
	$(content).append(getTableItem(itemSearchList));
	return content;
}

/**
 * Get a form for add item (with buttons "Add" and "Cancel")
 * @returns Element "div"
 */
function getItemAddForm(){
	var div = document.createElement("div");
	$(div).attr("id", itemForm);
	$(div).append(getElement(itemAddForm[0]));
	return div;
}

function addSearch(){
	var field = $("#field").val();
	var searchField = $("#searchField").val();
	var search = $("#search").val();
	if(search == ""){
		$("#search").val(field+":'"+searchField+"'");
	}else{
		$("#search").val(search+", "+field+":'"+searchField+"'");
	}
	$("#searchField").val("");
}
function resetSearch(){
	$("#search").val("");
}

function itemFormComplet(){
	$("#"+itemForm).empty();
	for ( var i = 1 ; i < itemAddForm.length ; i++ ) {
		$("#"+itemForm).append(getElement(itemAddForm[i]));
	}
	loadCategories();
	loadType();
}
/**
 * Get a table for display item list
 * @param id - put this id to the table
 * @returns Element "table"
 */
function getTableItem(id){
	var table = getElement(itemTable[0]);
	table.setAttribute("id", id);
	return table;
}
/**
 * Get a row for an item
 * @param content JSONObject (title, description and itemKey)
 * @returns Element "tr"
 */
function newRowItem(content){
	var row = document.createElement("tr");
	$(row).attr("id", removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	$(cell1).attr("class", "rowTitle");
	$(cell1).attr("onclick", "editItem('"+content.itemKey+"');");
	$(cell1).append(document.createTextNode(content.title));
	$(row).append(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	$(cell2).attr("class", "rowDescription");
	$(cell2).attr("onclick", "editItem('"+content.itemKey+"');");
	if(content.description.length > 400)
		$(cell2).append(document.createTextNode(content.description.substring(0, 400)+" [...]"));
	else
		$(cell2).append(document.createTextNode(content.description));
	$(row).append(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	$(cell3).attr("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	$(removeButton).attr("class", "button buttonRemove");
	$(removeButton).attr("onclick", "removeItem('"+content.itemKey+"');");
	//$(removeButton).append(document.createTextNode("Remove"));
	$(cell3).append(removeButton);
	$(row).append(cell3);
	// Add to favorites Button
	var favoritesButton = document.createElement("a");
	$(favoritesButton).attr("class", "button buttonFavorites");
	$(favoritesButton).attr("onclick", "addItemFavorites('"+content.itemKey+"');");
	//$(removeButton).append(document.createTextNode("Remove"));
	$(cell3).append(favoritesButton);
	$(row).append(cell3);
	return row;
}

function newRowItemSearch(content){
	var row = document.createElement("tr");
	$(row).attr("id", removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	$(cell1).attr("class", "rowTitle");
	$(cell1).attr("onclick", "loadItemSearch('"+content.itemKey+"');");
	$(cell1).append(document.createTextNode(content.title));
	$(row).append(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	$(cell2).attr("class", "rowDescription");
	$(cell2).attr("onclick", "loadItemSearch('"+content.itemKey+"');");
	if(content.description.length > 400)
		$(cell2).append(document.createTextNode(content.description.substring(0, 400)+" [...]"));
	else
		$(cell2).append(document.createTextNode(content.description));
	$(row).append(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	$(cell3).attr("class", "rowActions");
	// Add to favorites Button
	var favoritesButton = document.createElement("a");
	$(favoritesButton).attr("class", "button buttonFavorites");
	$(favoritesButton).attr("onclick", "addItemFavorites('"+content.itemKey+"');");
	//$(removeButton).append(document.createTextNode("Remove"));
	$(cell3).append(favoritesButton);
	$(row).append(cell3);
	return row;
}

/**
 * Erase form with id "itemForm"
 */
function cancelItem(){
	$("#"+itemForm).replaceWith(getItemAddForm());
}

function removePunctuation(string){
	return string.replace(/[\.,-\/#!$%\^&\*;:{}=\-_`~()]/g, "").trim();
}