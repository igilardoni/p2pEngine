/**
 * JavaScript for managing search
 * @author Michael DUBUIS
 */
var itemSearchFieldAreLoaded = false;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/**
 * Ask to the model to send item's searchable field
 */
function loadItemSearchField(){
	if(itemSearchFieldAreLoaded)
		return;
	sendQueryEmpty("loadItemSearchField");
	itemSearchFieldAreLoaded = true;
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
	var description = content.description.length>400?content.description.substring(0, 400):content.description;
	var row = $("<tr id=\""+removePunctuation(content.itemKey)+"\">" +
				"<td class=\"rowTitle resume\" onclick=\"loadItemSearch('" + content.itemKey + "');\">" +
					content.title +
				"</td>" +
				"<td class=\"rowDescription\" onclick=\"loadItemSearch('"+content.itemKey+"');\">" +
					"<label class=\"resume\">" + 
						description +
					"</label>" +
					"<div class=\"searchDisplayer hidden\">" +
						"<p>" +
							"<label class=\"label\">Title : </label>" +
							"<label id=\"title\">" +
								content.title +
							"</label>" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Category</label>" +
							"<label id=\"category\">" +
								content.category +
							"</label>" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Type : </label>" +
							"<label id=\"type\">" +
								content.type +
							"</label>" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Description : </label>" +
							"<label id=\"description\">" +
								content.description +
							"</label>" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Image : </label>" +
							"<img id=\"image\" src=\"" +
								content.image +
							"\">" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Location : </label>" +
							"<label id=\"country\">" +
								content.country +
							"</label>" +
						"</p>" +
						"<p>" +
							"<label class=\"label\">Contact : </label>" +
							"<label id=\"contact\">" +
								content.contact +
							"</label>" +
						"</p>" +
					"</div>" +
				"</td>" +
				"<td class=\"rowActions\">" +
					"<a class=\"button buttonFavorites\" onclick=\"addItemFavorites('"+content.itemKey+"');\" />" +
				"</td>" +
			"</tr>");
	return row;
}