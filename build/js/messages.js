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
			message:$("#message").val()
	}
	sendQuery("sendMessage", content);
}

function removeMessage(id) {
	var content = {"id":id};
	sendQuery("removeMessage", content);
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
	printFeedback(content.feedback, false);
}
function messageRemoved(content) {
	$("#"+messagesList+" #"+removePunctuation(content.id)).detach();
	printFeedback(content.feedback, true);
}
function messageNotRemoved(content) {
	printFeedback(content.feedback, false);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getWebmail(){
	return getElement(webmailForm);
}

function cancelMessage() {
	$("#messageDisplay").empty();
}

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
	return getElement(writeMessage);
}

function newRowMessage(content){
	var row = getElement(messageRow);
	$(row).attr("id", removePunctuation(content.id));
	$(row).find(".rowDate").append(content.date);
	$(row).find(".rowDate").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".rowSubject").append(content.subject);
	$(row).find(".rowSubject").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".rowFrom").append(content.sender);
	$(row).find(".rowFrom").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".buttonRemove").attr("onclick", "removeMessage('"+content.id+"');");
	return row;
}