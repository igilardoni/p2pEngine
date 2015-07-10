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

function sendMessage(){
	var content = {
			subject:$("#subject").val(),
			receiver:$("#receiver").val(),
			typeReceiver:$("#typeReceiver").val(),
			message:$("#message").val()
	}
	sendQuery("sendMessage", content);
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
		$(h2).append(key);
		var p = document.createElement("p");
		$(p).append(value);
		$("#"+messageDisplay).append(h2);
		$("#"+messageDisplay).append(p);
	});
}
function messageNotSent(content) {
	$("#webmailErrorBox").empty();
	$("#webmailErrorBox").append(document.createTextNode("Message not send : "+content.error));
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
	$(div).attr("id", "messageDisplay");
	for ( var i = 0 ; i < writeMessage.length ; i++ ) {
		$(div).append(getElement(writeMessage[i]));
	}
	return div;
}

function newRowMessage(content){
	var row = document.createElement("tr");
	$(row).attr("id", content.itemKey);
	$(row).attr("onclick", "loadMessage('"+content.id+"');")
	// Date cell
	var cell1 = document.createElement("td");
	$(cell1).attr("class", "rowDate");
	$(cell1).append(document.createTextNode(content.date));
	$(row).append(cell1);
	// Subject cell
	var cell2 = document.createElement("td");
	$(cell2).attr("class", "rowSubject");
	$(cell2).append(document.createTextNode(content.subject));
	$(row).append(cell2);
	// From cell
	var cell3 = document.createElement("td");
	$(cell3).attr("class", "rowFrom");
	$(cell3).append(document.createTextNode(content.sender));
	$(row).append(cell3);
	// Buttons Cell
	var cell4 = document.createElement("td");
	$(cell4).attr("class", "rowActions");
	// Remove Button
	var removeButton = document.createElement("a");
	$(removeButton).attr("class", "buttonRemove");
	$(removeButton).attr("onclick", "removeMessage('"+content.id+"');");
	//removeButton.appendChild(document.createTextNode("Remove"));
	$(cell4).append(removeButton);
	$(row).append(cell4);
	return row;
}