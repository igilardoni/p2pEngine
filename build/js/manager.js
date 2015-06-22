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
	emptyContent();
	$("#contentStart").append(getLoginForm());
}

function includeRegistration(){
	removeHeader();
	removeMenu();
	emptyContent();
	$("#contentStart").append(getRegistrationForm());
}

function includeHome(){
	includeHeader();
	includeMenu();
	emptyContent();
	$("#content").replaceWith(getHome());
}

function includeSearch(){
	includeHeader();
	includeMenu();
	emptyContent();
	$("#content").replaceWith(getSearchItem());
	loadItemSearchField();
}

function includeContrat(){
	emptyContent();
	// TODO
	// $("#content").load("./include/contrat.html");
}

function includeWebmail(){
	emptyContent();
	// TODO
	// $("#content").load("./include/webmail.html");
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