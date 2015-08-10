/**
 * JavaScript for managing favorites
 * @author Michael DUBUIS
 */
var itemsFavoritesAreLoaded = false;
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
	if(confirm("Are you sure to delete this item from Favorites ?")) {
		var content = {"itemKey":itemKey};
		sendQuery("removeItemFavorites", content);
	}
}

// Ask to the model to send item's itemKey from Favorites 
function loadItemFavorites(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("loadItemFavorites", content);
}

//Ask to the model to send all data items from Favorites (for the current user)
function loadItemsFavorites(){
	if(itemsFavoritesAreLoaded)
		return;
	sendQueryEmpty("loadItemsFavorites");
	itemsFavoritesAreLoaded = true;
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function favoritesItemsLoaded(content){
	$("#"+favoritesList).append(newRowFavorites(content));
	$("#"+itemList+" #"+removePunctuation(content.itemKey)).find("a.buttonFavorites").addClass("inFavorites");
	$("#"+itemList+" #"+removePunctuation(content.itemKey)).find("a.buttonFavorites").removeClass("buttonFavorites");
}

function favoritesItemLoaded(content){
	displayItemFavorites(content);
}

function itemFavoritesRemoved(content){
	var id = "favorites"+removePunctuation(content.itemKey);
	$("#"+id).detach();
	removeDisplayItemFavorites();
	$("#"+itemList+" #"+removePunctuation(content.itemKey)).find("a.inFavorites").addClass("buttonFavorites");
	$("#"+itemList+" #"+removePunctuation(content.itemKey)).find("a.inFavorites").removeClass("inFavorites");
}

function favoritesItemsLoadingStart(content){
	/*var div = document.createElement("div");
	div.attr("id","loading");
	div.append(document.createTextNode("Loading..."));
	$("aside").append(div);*/
}
function favoritesItemsLoadingEnd(content){
	$("aside #loading").remove();
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getFavoritesDisplay(){
	var div = document.createElement("div");
	$(div).append(getElement(itemFavoritesTable));
	var loading = document.createElement("div");
	$(loading).attr("id", "loading");
	$(div).append(loading);
	return div;
}

function getItemFavoritesDisplay(){
	return getElement(itemFavoritesDisplayer);
}

function clearFavoritesTable(){
	$("aside #favoritesList tbody").empty();
}
function newRowFavorites(content){
	var row = document.createElement("tr");
	$(row).attr("id", "favorites"+removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	$(cell1).append(document.createTextNode(content.title));
	$(cell1).attr("onclick", "loadItemFavorites('"+content.itemKey+"');");
	$(row).append(cell1);
	// Buttons Cell
	var cell3 = document.createElement("td");
	$(cell3).attr("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	$(removeButton).attr("class", "button buttonRemove");
	$(removeButton).attr("onclick", "removeItemFavorites('"+content.itemKey+"');");
	//$(removeButton).append(document.createTextNode("Remove"));
	$(cell3).append(removeButton);
	$(row).append(cell3);
	return row;
}

function removeDisplayItemFavorites(){
	$("aside #itemFavoritesDisplayer").remove();
}

function displayItemFavorites(content){
	removeDisplayItemFavorites();
	$("aside").append(getItemFavoritesDisplay());
	$.each(content, function(key, value){
		if(key=="image")
			$("#itemFavoritesDisplayer"+" #"+key).attr("src", value);
		else{
			$("#itemFavoritesDisplayer"+" #"+key).empty();
			var text = document.createTextNode(value);
			$("#itemFavoritesDisplayer"+" #"+key).append(text);
		}
	});
}

function switchFavorites(){
	if($("aside").hasClass("hidden")){
		displayFavorites();
		$("nav .favoritesButton").addClass("selected");
	} else {
		hideFavorites();
		$("nav .favoritesButton").removeClass("selected");
	}
}