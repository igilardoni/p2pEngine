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
	$("#contentStart").append(getLoginForm());
	if(username.length > 0) $("#username").val(username);
}

function includeRegistration(){
	var username = $("#username").val();
	var pwd = $("#password").val();
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(getRegistrationForm());
	if(username.length > 0) $("#username").val(username);
	if(pwd.length > 0) $("#password").val(pwd);
}

function includeHome(){
	emptyContent();
	$("#content").replaceWith(getHome());
	allMenuWhite();
	$("nav .homeButton").addClass("selected");
	scrollTop();
	loadItems();
	clearFavoritesTable();
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
	$("#content").append(getContrat());
	allMenuWhite();
	$("nav .contratButton").addClass("selected");
	loadContrats();
	scrollTop();
}

function includeWebmail(){
	emptyContent();
	$("#content").append(getWebmail());
	allMenuWhite();
	$("nav .messageButton").addClass("selected");
	scrollTop();
}

function includeMenu(){
	$("nav").empty();
	$("nav").replaceWith(getMenu());
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
	$("#content").append(getUpdateAccountForm());
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
	$("aside").append(getFavoritesDisplay());
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
	$("#content").append(getBootstrapInvitation());
	loadIP();
	allMenuWhite();
}

function includeBoostrapSetting(){
	emptyContent();
	$("#contentStart").append(getBootstrapSetting());
}

function allMenuWhite(){
	$("nav .homeButton").removeClass("selected");
	$("nav .searchButton").removeClass("selected");
	$("nav .contratButton").removeClass("selected");
	$("nav .messageButton").removeClass("selected");
}