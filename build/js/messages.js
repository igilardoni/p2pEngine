/**
 * 
 */
var messagesList = "messagesList";
var messageDisplay = "messageDisplay";
var messagesAreLoaded = false;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function loadMessages(){
	if(messagesAreLoaded)
		return;
	sendQueryEmpty("loadMessages");
	messagesAreLoaded = true;
}

/**
 * @deprecated
 */
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

function loadSearchUsers() {
	$("#searchUsersDiv").fadeIn();
}

function searchUsers() {
	var content = {
			search:$("#searchUserInput").val()
	}
	sendQuery("searchUsers", content);
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
	var display = getClone(messageDisplay);
	$(display).find("#senderMessage").text(content.sender);
	$(display).find("#subjectMessage").text(content.subject);
	$(display).find("#dateMessage").text(content.date);
	$(display).find("#contentMessage").append(textWithSlashNToBr(content.message));
	$("#messageDisplay").append(display);
	$("#"+removePunctuation(content.id)).removeClass("unreadedMessage");
	$("#"+removePunctuation(content.id)).addClass("readedMessage");
	if($(".unreadButton").hasClass("selected"))
		$("#"+removePunctuation(content.id)).addClass("hidden");
}
function messageNotSent(content) {
}
function messageRemoved(content) {
	$("#"+messagesList+" #"+removePunctuation(content.id)).detach();
}
function messageNotRemoved(content) {
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getWebmail(){
	loadMessages();
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

function newMessage() {
	$(".writeButton").addClass("selected");
	$("#messageDisplay").empty();
	$("#messageDisplay").append(getClone(writeMessage));
}

function newRowMessage(content){
	var row = getClone(messageRow);
	
	$(row).attr("id", removePunctuation(content.id));
	if(content.isRead === undefined)
		$(row).addClass("sentMessage hidden");
	else if(content.isRead)
		$(row).addClass("readedMessage");
	else
		$(row).addClass("unreadedMessage");
	$(row).find(".rowDate").append(content.date);
	$(row).find(".rowDate").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".rowSubject").append(content.subject);
	$(row).find(".rowSubject").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".rowFrom").append(content.sender);
	$(row).find(".rowFrom").attr("onclick", "loadMessage('"+content.id+"');");
	$(row).find(".buttonRemove").attr("onclick", "removeMessage('"+content.id+"');");
	return row;
}

function showSubMenu(menu) {
	var siblings = $(menu).siblings();
	for(var i = 0; i < siblings.length; i++ ) {
		if($(siblings[i]).hasClass("hidden")){
			$(siblings[i]).removeClass("hidden");
			$(menu).addClass("selected");
		} else {
			$(siblings[i]).addClass("hidden");
			$(menu).removeClass("selected");
		}
	}
}

function showMessageReceived() {
	$(".receivedButton").addClass("selected");
	$(".unreadButton").removeClass("selected");
	$(".sentButton").removeClass("selected");
	$(".unreadedMessage").removeClass("hidden");
	$(".readedMessage").removeClass("hidden");
	$(".sentMessage").addClass("hidden");
}

function showMessageUnread() {
	$(".receivedButton").removeClass("selected");
	$(".unreadButton").addClass("selected");
	$(".sentButton").removeClass("selected");
	$(".unreadedMessage").removeClass("hidden");
	$(".readedMessage").addClass("hidden");
	$(".sentMessage").addClass("hidden");
}

function showMessageSent() {
	$(".receivedButton").removeClass("selected");
	$(".unreadButton").removeClass("selected");
	$(".sentButton").addClass("selected");
	$(".unreadedMessage").addClass("hidden");
	$(".readedMessage").addClass("hidden");
	$(".sentMessage").removeClass("hidden");
}