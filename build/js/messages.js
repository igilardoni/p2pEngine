/**
 * 
 */
var messagesList = "messagesList";
var messageDisplay = "messageDisplay";
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function loadMessages(){
	sendQueryEmpty("loadMessages");
}

function loadConversation(){
	sendQueryEmpty("loadConversation");
}

function loadMessage(id){
	var content = {"id":id};
	sendQuery("loadMessage", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function messagesLoaded(content) {
	$("#"+messagesList).append(newRowMessage(content));
}
function messageLoaded(content) {
	$("#"+messageDisplay).empty();
	$.each(content, function(key, value){
		var h2 = document.createElement("h2");
		h2.appendChild(document.createTextNode(key));
		var p = document.createElement("p");
		p.appendChild(document.createTextNode(value));
		$("#"+messageDisplay).append(h2);
		$("#"+messageDisplay).append(p);
	});
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function sendMessageTo(){
	var receiver = $("#owner").text();
	var subject = $("#title").text();
	includeWebmail();
	newMessage();
	$("#receiver").val(receiver);
	$("#subject").val("Item : "+subject);
}

function newMessage(){
	$("#"+"messageDisplay").empty();
	$("#"+"messageDisplay").replaceWith(getNewMessageForm());
}

function getNewMessageForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "messageDisplay");
	for ( var i = 0 ; i < writeMessage.length ; i++ ) {
		div.appendChild(getElement(writeMessage[i]));
	}
	return div;
}

function newRowMessage(content){
	var row = document.createElement("tr");
	row.setAttribute("id", content.itemKey);
	row.setAttribute("onclick", "loadMessage('"+content.id+"');")
	// Date cell
	var cell1 = document.createElement("td");
	cell1.setAttribute("class", "rowDate");
	cell1.appendChild(document.createTextNode(content.date));
	row.appendChild(cell1);
	// Subject cell
	var cell2 = document.createElement("td");
	cell2.setAttribute("class", "rowSubject");
	cell2.appendChild(document.createTextNode(content.subject));
	row.appendChild(cell2);
	// From cell
	var cell3 = document.createElement("td");
	cell3.setAttribute("class", "rowFrom");
	cell3.appendChild(document.createTextNode(content.sender));
	row.appendChild(cell3);
	// Buttons Cell
	var cell4 = document.createElement("td");
	cell4.setAttribute("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	removeButton.setAttribute("class", "buttonRemove");
	removeButton.setAttribute("onclick", "removeMessage('"+content.id+"');");
	//removeButton.appendChild(document.createTextNode("Remove"));
	cell4.appendChild(removeButton);
	row.appendChild(cell4);
	return row;
}