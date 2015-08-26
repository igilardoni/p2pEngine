/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * VARIABLES * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratList = "contratList";
var itemContratList = "itemContratList"
var favoritesList = "favoritesList";
var itemList = "itemList";
var itemSearchList = "itemSearchList";
var itemForm = "itemForm";
var messagesList = "messagesList";
var messageDisplay = "messageDisplay";


var emptyForm = null;
$('<div>').load("/SXPManager/html/emptyForm.html", function() {
	emptyForm = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * * *  MENU * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu = null;
$('<div>').load("/SXPManager/html/menu.html", function() {
	menu = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = null;
$('<div>').load("/SXPManager/html/header.html", function() {
	header = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * * * BOOSTRAP* * * * * * * * * * * * * * * * * * * * * * * * */
var bootstrapInvitation = null;
$('<div>').load("/SXPManager/html/bootstrap/bootstrapInvitation.html", function() {
	bootstrapInvitation = $(this).children();
});
var bootstrapSetting = null;
$('<div>').load("/SXPManager/html/bootstrap/bootstrapSetting.html", function() {
	bootstrapSetting = $(this).children();
});

/* * * * * * * * * * * * * * * * * * * * REGISTRATION FORM * * * * * * * * * * * * * * * * * * * * * * */
var registrationForm = null;
$('<div>').load("/SXPManager/html/registrationForm.html", function() {
	registrationForm = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * ACCOUNT UPDATE FORM * * * * * * * * * * * * * * * * * * * * * */
var updateAccountForm = null;
$('<div>').load("/SXPManager/html/updateAccountForm.html", function() {
	updateAccountForm = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * ITEM PAGE * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = null;
$('<div>').load("/SXPManager/html/items/itemAddForm.html", function() {
	itemAddForm = $(this).children();
});
var itemTable = null;
$('<div>').load("/SXPManager/html/items/itemTable.html", function() {
	itemTable = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * SEARCH FORM * * * * * * * * * * * * * * * * * * * * * * * * */
var searchForm = null;
$('<div>').load("/SXPManager/html/search/searchForm.html", function() {
	searchForm = $(this).children();
});
var itemSearchTable = null;
$('<div>').load("/SXPManager/html/search/itemSearchTable.html", function() {
	itemSearchTable = $(this).children();
});
var itemSearchDisplayer = null;
$('<div>').load("/SXPManager/html/search/itemSearchDisplayer.html", function() {
	itemSearchDisplayer = $(this).children();
});
var searchItemRow = null;
$('<div>').load("/SXPManager/html/search/searchItemRow.html", function() {
	searchItemRow = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm = null;
$('<div>').load("/SXPManager/html/messages/webmailForm.html", function() {
	webmailForm = $(this).children();
});
var writeMessage = null;
$('<div>').load("/SXPManager/html/messages/writeMessage.html", function() {
	writeMessage = $(this).children();
});
var messageRow = null;
$('<div>').load("/SXPManager/html/messages/messageRow.html", function() {
	messageRow = $(this).children();
});
var messageDisplay = null;
$('<div>').load("/SXPManager/html/messages/messageDisplay.html", function() {
	messageDisplay = $(this).children();
});
var conversationRow = null;
$('<div>').load("/SXPManager/html/messages/conversationRow.html", function() {
	conversationRow = $(this).children();
});
var conversationDisplay = null;
$('<div>').load("/SXPManager/html/messages/conversationDisplay.html", function() {
	conversationDisplay = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer = null;
$('<div>').load("/SXPManager/html/favorites/itemFavoritesDisplayer.html", function(){
	itemFavoritesDisplayer = $(this).children();
});
var itemFavoritesTable = null;
$('<div>').load("/SXPManager/html/favorites/itemFavoritesTable.html", function() {
	itemFavoritesTable = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * CONTRACTS * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratDisplay = null;
$('<div>').load("/SXPManager/html/contract/contractDisplay.html", function() {
	contratDisplay = $(this).children();
});
var contratTable = null;
$('<div>').load("/SXPManager/html/contract/contractTable.html", function() {
	contratTable = $(this).children();
});
var contratForm = null;
$('<div>').load("/SXPManager/html/contract/contractForm.html", function() {
	contratForm = $(this).children();
});
var contratRow = null;
$('<div>').load("/SXPManager/html/contract/contractRow.html", function() {
	contratRow = $(this).children();
});
var contratItemRow = null;
$('<div>').load("/SXPManager/html/contract/contractItemRow.html", function() {
	contratItemRow = $(this).children();
});
var itemContratTable = null;
$('<div>').load("/SXPManager/html/contract/itemContratTable.html", function() {
	itemContratTable = $(this).children();
});
var clauseContratForm = null;
$('<div>').load("/SXPManager/html/contract/clauseContratForm.html", function() {
	clauseContratForm = $(this).children();
});
var clauseContratDisplay = null;
$('<div>').load("/SXPManager/html/contract/clauseContratDisplay.html", function() {
	clauseContratDisplay = $(this).children();
});
var contratRuleRow = null;
$('<div>').load("/SXPManager/html/contract/contractRuleRow.html", function() {
	contratRuleRow = $(this).children();
});
var contratSignatorieRow = null;
$('<div>').load("/SXPManager/html/contract/contractSignatorieRow.html", function() {
	contratSignatorieRow = $(this).children();
});
var contratClauseDisplay = null;
$('<div>').load("/SXPManager/html/contract/contractClauseDisplay.html", function() {
	contratClauseDisplay = $(this).children();
});
var contratClauseForm = null;
$('<div>').load("/SXPManager/html/contract/contractClauseForm.html", function() {
	contratClauseForm = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * * VIEWERS * * * * * * * * * * * * * * * * * * * * * * * * * */
var viewAnnounce = null; // What is that ?
$("<div>").load("/SXPManager/html/viewAnnounce.html", function() {
	viewAnnounce = $(this).children();
});

/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * */
var loginForm = null;
$("<div>").load("/SXPManager/html/loginForm.html", function() {
	loginForm = $(this).children();
	$("#contentStart").append($(this).children());
});
/* * * * * * * * * * * * * * * * * * * * * * BYE PAGE * * * * * * * * * * * * * * * * * * * * * * * * * */
var closePage = null;
$('<div>').load("/SXPManager/html/closePage.html", function() {
	closePage = $(this).children();
});
