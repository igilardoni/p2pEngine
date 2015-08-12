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
	$("#"+messageDisplay).empty();
	var display = $("<div class=\"meta\">" +
				"<label class=\"label\">Sender : </label>" +
				content.sender +
				"<label class=\"label\">Date : </label>" +
				content.date +
				"<label class=\"label\">Subject : </label>" +
				content.subject +
			"</div>" +
			"<div>" +
				"<p>" +
					textWithSlashNToBr(content.message) +
				"</p>" +
			"</div>");
	$("#"+messageDisplay).append(display);
	$("#"+removePunctuation(content.id)).removeClass("unreadedMessage");
	$("#"+removePunctuation(content.id)).addClass("readedMessage");
	if($(".unreadButton").hasClass("selected"))
		$("#"+removePunctuation(content.id)).addClass("hidden");
	/*$.each(content, function(key, value){
		var h2 = document.createElement("h2");
		$(h2).append(key);
		var p = document.createElement("p");
		$(p).append(value);
		$("#"+messageDisplay).append(h2);
		$("#"+messageDisplay).append(p);
	});*/
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
	$("#messageDisplay").append(writeMessage);
}

function newMessage2(){
	$(".writeButton").addClass("selected");
	 var writer = $("<div id=\"messageSender\">" +
		"<ul class=\"newMessageHeader\">" +
			"<li>" +
				"<p>" +
					"<label>Subject</label>" +
					"<input type=\"text\" id=\"subject\" name=\"subject\" />" +
				"</p>" +
			"</li>" +
			"<li>" +
				"<p>" +
					"<label>Receiver</label>" +
					"<input type=\"text\" id=\"receiver\" name=\"receiver\" />" +
				"</p>" +
			"</li>" +
		"</ul>" +
		"<p class=\"messageContent\">" +
			"<label>Message :</label>" +
			"<textarea id=\"message\" name=\"message\"></textarea>" +
		"</p>" +
		"<p class=\"newMessageFooter\">" +
			"<a onclick=\"sendMessage();\" class=\"button buttonSend\">Send</a>" +
			"<a onclick=\"cancelMessage();\" class=\"button buttonCancel\">Cancel</a>" +
		"</p>" +
	"</div>");
	 $("#messageDisplay").empty();
	 $("#messageDisplay").append(writer);
}

function getNewMessageForm(){
	return getElement(writeMessage);
}

function newRowMessage(content){
	var row = $("<tr>" +
		"<td class=\"rowDate\"></td>" +
		"<td class=\"rowSubject\"></td>" +
		"<td class=\"rowFrom\"></td>" +
		"<td class=\"rowActions\"><a class=\"button buttonRemove\"></a></td>" +
	"</tr>");
	
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