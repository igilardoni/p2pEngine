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
var boostrapInvitation = null;
$('<div>').load("/SXPManager/html/bootstrapInvitation.html", function() {
	boostrapInvitation = $(this).children();
});
var boostrapSetting = null;
$('<div>').load("/SXPManager/html/bootstrapSetting.html", function() {
	boostrapSetting = $(this).children();
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
/* * * * * * * * * * * * * * * * * * * * * ITEM FORM * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = null;
$('<div>').load("/SXPManager/html/itemAddForm.html", function() {
	itemAddForm = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * ITEMS TABLE * * * * * * * * * * * * * * * * * * * * * * * * */
var itemTable = null;
$('<div>').load("/SXPManager/html/itemTable.html", function() {
	itemTable = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * SEARCH FORM * * * * * * * * * * * * * * * * * * * * * * * * */
var searchForm = null;
$('<div>').load("/SXPManager/html/searchForm.html", function() {
	searchForm = $(this).children();
});
var itemSearchTable = null;
$('<div>').load("/SXPManager/html/itemSearchTable.html", function() {
	itemSearchTable = $(this).children();
});
var itemSearchDisplayer = null;
$('<div>').load("/SXPManager/html/itemSearchDisplayer.html", function() {
	itemSearchDisplayer = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm = null;
$('<div>').load("/SXPManager/html/webmailForm.html", function() {
	webmailForm = $(this).children();
});
var writeMessage = null;
$('<div>').load("/SXPManager/html/writeMessage.html", function() {
	writeMessage = $(this).children();
});
var messageRow = null;
$('<div>').load("/SXPManager/html/messageRow.html", function() {
	messageRow = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer = null;
$('<div>').load("/SXPManager/html/itemFavoritesDisplayer.html", function(){
	itemFavoritesDisplayer = $(this).children();
});
var itemFavoritesTable = null;
$('<div>').load("/SXPManager/html/itemFavoritesTable.html", function() {
	itemFavoritesTable = $(this).children();
});
/* * * * * * * * * * * * * * * * * * * * * CONTRACTS * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratDisplay = null;
$('<div>').load("/SXPManager/html/contratDisplay.html", function() {
	contratDisplay = $(this).children();
});
var contratTable = null;
$('<div>').load("/SXPManager/html/contratTable.html", function() {
	contratTable = $(this).children();
});
var contratForm = null;
$('<div>').load("/SXPManager/html/contratForm.html", function() {
	contratForm = $(this).children();
});
var itemContratTable = null;
$('<div>').load("/SXPManager/html/itemContratTable.html", function() {
	itemContratTable = $(this).children();
});
var clauseContratForm = null;
$('<div>').load("/SXPManager/html/clauseContratForm.html", function() {
	clauseContratForm = $(this).children();
});
var clauseContratDisplay = null;
$('<div>').load("/SXPManager/html/clauseContratDisplay.html", function() {
	clauseContratDisplay = $(this).children();
});
var ruleRow = null;
$('<div>').load("/SXPManager/html/ruleRow.html", function() {
	ruleRow = $(this).children();
});
var itemContratRow = null;
$('<div>').load("/SXPManager/html/itemContratRow.html", function() {
	itemContratRow = $(this).children();
});

/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * *
 * Premiere page qu'on inclu on attends le chargement complet avant la suite  */
var loginForm = null;
$("<div>").load("/SXPManager/html/loginForm.html", function() {
	loginForm = $(this).children();
	$("#contentStart").append($(this).children());
});


