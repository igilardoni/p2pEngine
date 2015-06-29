/**
 * JavaScript for managing favorites
 * @author Michael DUBUIS
 */
var favoritesList = "favoritesList";
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// Ask to the model to add itemKey in Favorites
function addItemFavorites(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("addItemFavorites", content);
}

// Ask to the model to remove itemKey in Favorites
function removeItemFavorites(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("removeItemFavorites", content);
}

// Ask to the model to send item's itemKey from Favorites 
function loadItemFavorites(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("loadItemFavorites", content);
}

//Ask to the model to send all data items from Favorites (for the current user)
function loadItemsFavorites(){
	sendQueryEmpty("loadItemsFavorites");
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function favoritesItemsLoaded(content){
	$("#"+favoritesList).append(newRowFavorites(content));
}

function favoritesItemLoaded(content){
	displayItemFavorites(content);
}

function itemFavoritesRemoved(content){
	var id = "favorites"+removePunctuation(content.itemKey);
	$("#"+id).detach();
}

function favoritesItemsLoadingStart(content){
	/*var div = document.createElement("div");
	div.setAttribute("id","loading");
	div.appendChild(document.createTextNode("Loading..."));
	$("aside").append(div);*/
}
function favoritesItemsLoadingEnd(content){
	$("aside #loading").remove();
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function newRowFavorites(content){
	var row = document.createElement("tr");
	row.setAttribute("id", "favorites"+removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowTitle");
	cell1.appendChild(document.createTextNode(content.title));
	cell1.setAttribute("onclick", "loadItemFavorites('"+content.itemKey+"');");
	row.appendChild(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowDescription");
	cell2.setAttribute("onclick", "loadItemFavorites('"+content.itemKey+"');");
	if(content.description.length > 400)
		cell2.appendChild(document.createTextNode(content.description.substring(0, 200)+" [...]"));
	else
		cell2.appendChild(document.createTextNode(content.description));
	row.appendChild(cell2);
	// Buttons Cell
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	removeButton.setAttribute("class", "buttonRemove");
	removeButton.setAttribute("onclick", "removeItemFavorites('"+content.itemKey+"');");
	//removeButton.appendChild(document.createTextNode("Remove"));
	cell3.appendChild(removeButton);
	row.appendChild(cell3);
	return row;
}

function displayItemFavorites(content){
	$("aside #itemFavoritesDisplayer").remove();
	$("aside").append(getItemFavoritesDisplay());
	$.each(content, function(key, value){
		var text = document.createTextNode(value);
		$("#itemFavoritesDisplayer"+" #"+key).append(text);
	});
}