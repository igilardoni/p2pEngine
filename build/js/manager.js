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
	$("#content").replaceWith(getContrat());
	allMenuWhite();
	$("nav .contratButton").addClass("selected");
	loadContrats();
	scrollTop();
}

function includeWebmail(){
	emptyContent();
	$("#content").replaceWith(getWebmail());
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
	loadItemsFavorites();
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

function allMenuWhite(){
	$("nav .homeButton").removeClass("selected");
	$("nav .searchButton").removeClass("selected");
	$("nav .contratButton").removeClass("selected");
	$("nav .messageButton").removeClass("selected");
}