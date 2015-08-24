/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function emptyContent(){
	$("#content").empty();
	$("#contentStart").empty();
}

function includeLogin(){
	var username = $("#username").val();
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(loginForm);
	if(username !== undefined && username.length > 0) $("#username").val(username);
}

function includeRegistration(){
	var username = $("#username").val();
	var pwd = $("#password").val();
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(registrationForm);
	if(username !== undefined && username.length > 0) $("#username").val(username);
	if(pwd !== undefined && pwd.length > 0) $("#password").val(pwd);
}

function includeHome(){
	emptyContent();
	$("#content").append("<p class=\"feedbackBox\" onclick=\"clearFeedback();\"></p>");
	$("#content").append(itemTable);
	allMenuWhite();
	$("nav .homeButton").addClass("selected");
	scrollTop();
	loadItems();
	loadItemsFavorites();
}

function includeSearch(){
	emptyContent();
	$("#content").replaceWith(getSearchItem());
	allMenuWhite();
	$("nav .searchButton").addClass("selected");
	scrollTop();
	loadItemSearchField();
}

function includeContrat(){
	emptyContent();
	$("#content").append(contratTable);
	allMenuWhite();
	$("nav .contratButton").addClass("selected");
	loadContrats();
	scrollTop();
}

function includeItemViewer() {
	$("#wrapperView").empty();
	$("#wrapperView").append(viewAnnounce);
	$("#wrapperView").fadeIn();
}

function includeWebmail(){
	emptyContent();
	$("#content").append(webmailForm);
	allMenuWhite();
	$("nav .messageButton").addClass("selected");
	scrollTop();
	loadMessages();
	loadConversations();
}

function includeMenu(){
	$("nav").empty();
	$("nav").append(getMenu());
}

function removeMenu(){
	$("nav").empty();
}

function includeHeader(){
	$("header").empty();
	$("header").append(getHeader());
}

function removeHeader(){
	$("header").empty();
}

function includeAccount(){
	emptyContent();
	$("#content").append(updateAccountForm);
	allMenuWhite();
}

function displayFavorites(){
	$("aside").removeClass("hidden");
}

function hideFavorites(){
	$("aside").addClass("hidden");
}

function includeFavorites(){
	$("aside").empty();
	$("aside").append(itemFavoritesTable);
	//loadItemsFavorites();
	hideFavorites();
}

function removeFavorites(){
	$("aside").empty();
}

function includeFooter(){
	
}

function removeFooter(){
	$("footer").empty();
}

function scrollTop(){
	window.scrollTo(0, 0);
}

function includeBoostrapInvitation(){
	emptyContent();
	$("#content").append(bootstrapInvitation);
	loadIP();
	allMenuWhite();
}

function includeBoostrapSetting(){
	emptyContent();
	$("#contentStart").append(bootstrapSetting);
}

function allMenuWhite(){
	$("nav .homeButton").removeClass("selected");
	$("nav .searchButton").removeClass("selected");
	$("nav .contratButton").removeClass("selected");
	$("nav .messageButton").removeClass("selected");
}