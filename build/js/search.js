/**
 * JavaScript for managing search
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
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
	/*if($("aside").hasClass("hidden"))
		switchFavorites();
	content = {
			"itemKey":itemKey
	};
	sendQuery("loadItemSearch", content);*/
	if($("#"+removePunctuation(itemKey)+" .searchDisplayer").hasClass("hidden")){
		$("#"+removePunctuation(itemKey)+" .searchDisplayer").removeClass("hidden");
		$("#"+removePunctuation(itemKey)+" .resume").addClass("hidden");
	} else {
		$("#"+removePunctuation(itemKey)+" .searchDisplayer").addClass("hidden");
		$("#"+removePunctuation(itemKey)+" .resume").removeClass("hidden");
	}
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
	/*if($("#"+removePunctuation(content.itemKey)).lenght == 0)*/
		$("#"+itemSearchList).append(newRowItemSearch(content));
	/*else
		$("#"+itemSearchList+" #"+removePunctuation(content.itemKey)).replaceWith(newRowItem(content));*/
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
function getSearchItem(){
	var content = document.createElement("div");
	$(content).attr("id", "content");
	var div = getElement(searchForm);
	$(content).append(div);
	$(content).append(getElement(itemSearchTable));
	return content;
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

function newRowItemSearch(content){
	var row = document.createElement("tr");
	$(row).attr("id", removePunctuation(content.itemKey));
	// Title cell
	var cell1 = document.createElement("td");
	$(cell1).attr("class", "rowTitle resume");
	$(cell1).attr("onclick", "loadItemSearch('"+content.itemKey+"');");
	$(cell1).append(document.createTextNode(content.title));
	$(row).append(cell1);
	// Description cell
	var cell2 = document.createElement("td");
	$(cell2).attr("class", "rowDescription");
	$(cell2).attr("onclick", "loadItemSearch('"+content.itemKey+"');");
	var labelDescription = document.createElement("label");
	$(labelDescription).addClass("resume");
	if(content.description.length > 400)
		$(labelDescription).append(document.createTextNode(content.description.substring(0, 400)+" [...]"));
	else
		$(labelDescription).append(document.createTextNode(content.description));
	$(cell2).append(labelDescription);
	
	var divDisplay = getElement(itemSearchDisplayer);
	$(divDisplay).find("#title").text(content.title);
	$(divDisplay).find("#category").text(content.category);
	$(divDisplay).find("#type").text(content.type);
	$(divDisplay).find("#description").text(content.description);
	$(divDisplay).find("#image").attr("src", content.image);
	$(divDisplay).find("#country").text(content.country);
	$(divDisplay).find("#contact").text(content.contact);
	
	$(cell2).append(divDisplay);
	
	
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