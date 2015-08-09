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
	emptyForm = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * * *  MENU * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu = null;
$('<div>').load("/SXPManager/html/menu.html", function() {
	menu = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = null;
$('<div>').load("/SXPManager/html/header.html", function() {
	header = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * * * BOOSTRAP* * * * * * * * * * * * * * * * * * * * * * * * */
var boostrapInvitation = null;
$('<div>').load("/SXPManager/html/bootstrapInvitation.html", function() {
	boostrapInvitation = $(this);
});
var boostrapSetting = null;
$('<div>').load("/SXPManager/html/bootstrapSetting.html", function() {
	boostrapSetting = $(this);
});

/* * * * * * * * * * * * * * * * * * * * REGISTRATION FORM * * * * * * * * * * * * * * * * * * * * * * */
var registrationForm = null;
$('<div>').load("/SXPManager/html/registrationForm.html", function() {
	registrationForm = $(this);
});
/* * * * * * * * * * * * * * * * * * * * ACCOUNT UPDATE FORM * * * * * * * * * * * * * * * * * * * * * */
var updateAccountForm = null;
$('<div>').load("/SXPManager/html/updateAccountForm.html", function() {
	updateAccountForm = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * ITEM FORM * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = null;
$('<div>').load("/SXPManager/html/itemAddForm.html", function() {
	itemAddForm = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * ITEMS TABLE * * * * * * * * * * * * * * * * * * * * * * * * */
var itemTable = null;
$('<div>').load("/SXPManager/html/itemTable.html", function() {
	itemTable = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * SEARCH FORM * * * * * * * * * * * * * * * * * * * * * * * * */
var searchForm = null;
$('<div>').load("/SXPManager/html/searchForm.html", function() {
	searchForm = $(this);
});
var itemSearchTable = null;
$('<div>').load("/SXPManager/html/itemSearchTable.html", function() {
	itemSearchTable = $(this);
});
var itemSearchDisplayer = null;
$('<div>').load("/SXPManager/html/itemSearchDisplayer.html", function() {
	itemSearchDisplayer = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm = null;
$('<div>').load("/SXPManager/html/webmailForm.html", function() {
	webmailForm = $(this);
});
var writeMessage = null;
$('<div>').load("/SXPManager/html/writeMessage.html", function() {
	writeMessage = $(this);
});
var messageRow = null;
$('<div>').load("/SXPManager/html/messageRow.html", function() {
	messageRow = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer = null;
$('<div>').load("/SXPManager/html/itemFavoritesDisplayer.html", function(){
	itemFavoritesDisplayer = $(this);
});
var itemFavoritesTable = null;
$('<div>').load("/SXPManager/html/itemFavoritesTable.html", function() {
	itemFavoritesTable = $(this);
});
/* * * * * * * * * * * * * * * * * * * * * CONTRACTS * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratDisplay = null;
$('<div>').load("/SXPManager/html/contratDisplay.html", function() {
	contratDisplay = $(this);
});
var contratTable = null;
$('<div>').load("/SXPManager/html/contratTable.html", function() {
	contratTable = $(this);
});
var contratForm = null;
$('<div>').load("/SXPManager/html/contratForm.html", function() {
	contratForm = $(this);
});
var itemContratTable = null;
$('<div>').load("/SXPManager/html/itemContratTable.html", function() {
	itemContratTable = $(this);
});
var clauseContratForm = null;
$('<div>').load("/SXPManager/html/clauseContratForm.html", function() {
	clauseContratForm = $(this);
});
var clauseContratDisplay = null;
$('<div>').load("/SXPManager/html/clauseContratDisplay.html", function() {
	clauseContratDisplay = $(this);
});
var contratRow = null;
$('<div>').load("/SXPManager/html/contratRow.html", function() {
	contratRow = $(this);
});
var ruleRow = null;
$('<div>').load("/SXPManager/html/ruleRow.html", function() {
	ruleRow = $(this);
});
var itemContratRow = null;
$('<div>').load("/SXPManager/html/itemContratRow.html", function() {
	itemContratRow = $(this);
});

/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * *
 * Premiere page qu'on inclu on attends le chargement complet avant la suite  */
var loginForm = null;
$("<div>").load("/SXPManager/html/loginForm.html", function() {
	loginForm = $(this);
	includeLogin();
});


