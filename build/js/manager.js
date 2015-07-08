/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function emptyContent(){
	$("#content").empty();
	$("#contentStart").empty();
}

function includeLogin(){
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(getLoginForm());
}

function includeRegistration(){
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(getRegistrationForm());
}

function includeHome(){
	emptyContent();
	$("#content").replaceWith(getHome());
	allMenuWhite();
	$("nav .homeButton").css("background-color", "#707070");
	scrollTop();
	loadItems();
}

function includeSearch(){
	emptyContent();
	$("#content").replaceWith(getSearchItem());
	allMenuWhite();
	$("nav .searchButton").css("background-color", "#707070");
	scrollTop();
	loadItemSearchField();
}

function includeContrat(){
	emptyContent();
	$("#content").replaceWith(getContrat());
	allMenuWhite();
	$("nav .contratButton").css("background-color", "#707070");
	loadContrats();
	scrollTop();
}

function includeWebmail(){
	emptyContent();
	$("#content").replaceWith(getWebmail());
	allMenuWhite();
	$("nav .messageButton").css("background-color", "#707070");
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

function includeFavorites(){
	$("aside").empty();
	$("aside").append(getFavoritesDisplay());
	loadItemsFavorites();
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

function allMenuWhite(){
	$("nav .homeButton").css("background-color", "#404040");
	$("nav .searchButton").css("background-color", "#404040");
	$("nav .contratButton").css("background-color", "#404040");
	$("nav .messageButton").css("background-color", "#404040");
}