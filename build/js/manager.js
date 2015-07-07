/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function emptyContent(){
	$("#content").empty();
	$("#contentStart").empty();
}

function includeLogin(){
	removeHeader();
	removeMenu();
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(getLoginForm());
}

function includeRegistration(){
	removeHeader();
	removeMenu();
	removeFavorites();
	removeFooter();
	emptyContent();
	$("#contentStart").append(getRegistrationForm());
}

function includeHome(){
	includeHeader();
	includeMenu();
	emptyContent();
	$("#content").replaceWith(getHome());
	scrollTop();
	loadItems();
}

function includeSearch(){
	includeHeader();
	includeMenu();
	emptyContent();
	$("#content").replaceWith(getSearchItem());
	scrollTop();
	loadItemSearchField();
}

function includeContrat(){
	emptyContent();
	$("#content").replaceWith(getContrat());
	loadContrats();
	scrollTop();
}

function includeWebmail(){
	emptyContent();
	$("#content").replaceWith(getWebmail());
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