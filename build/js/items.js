/**
 * JavaScript for managing items
 * @author Michael DUBUIS
 */
var itemList = "#itemList";
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
function updateItem(){
	var itemKey = $("#itemKey");
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
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function itemRemoved(content){
	$(itemList+" #"+content.itemKey).remove();
}

function newRowItem(content){
	var row = document.createElement("tr");
	
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 												TESTS												   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

var tableItemSearch = "#itemSearch";

function newRowItem(id, title, description){
	var row = document.createElement("tr");
	var cell1 = document.createElement("td");
	var cell2 = document.createElement("td");
	var cell3 = document.createElement("td");
	
	var cellText1 = document.createTextNode(id);
	cell1.appendChild(cellText1);
	var cellText2 = document.createTextNode(title);
	cell2.appendChild(cellText2);
	var cellText3 = document.createTextNode(description);
	cell3.appendChild(cellText3);
	
	row.appendChild(cell1);
	row.appendChild(cell2);
	row.appendChild(cell3);
	
	return row;
}

function clearRowItemSearch(){
	$(tableItemSearch).empty();
}