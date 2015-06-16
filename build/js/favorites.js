/**
 * JavaScript for managing favorites
 * @author Michael DUBUIS
 */
var favoritesList = "favoritesList";
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// Ask to the model to add itemKey in Favorites
function addFavorites(itemKey){
	var content = {"itemKey":itemKey};
	var data = {"query":"addFavorites", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to remove itemKey in Favorites
function removeFavorites(itemKey){
	var content = {"itemKey":itemKey};
	var data = {"query":"removeFavorites", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to send item's itemKey from Favorites 
function loadItemFavorites(itemKey){
	var content = {"itemKey":itemKey};
	var data = {"query":"loadItemFavorites", "content":content};
	webSocket.send(JSON.stringify(data));
}

//Ask to the model to send all data items from Favorites (for the current user)
function loadItemsFavorites(){
	var content = {};
	var data = {"query":"loadItemsFavorites", "content":content};
	webSocket.send(JSON.stringify(data));
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function favoritesItemsLoaded(content){
	$("#"+favoritesList).append(newRowItem(content));
}