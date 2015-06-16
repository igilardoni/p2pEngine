/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function includeLogin(){
	removeHeader();
	removeMenu();
	$("#content").empty();
	$("#content").append(getLoginForm());
}

function includeRegistration(){
	removeHeader();
	removeMenu();
	$("#content").empty();
	$("#content").append(getRegistrationForm());
}

function includeHome(){
	includeHeader();
	includeMenu();
	$("#content").replaceWith(getHome());
}

function includeSearch(){
	includeHeader();
	includeMenu();
	$("#content").replaceWith(getSearchItem());
	loadItemSearchField();
}

function includeContrat(){
	$("#content").empty();
	// TODO
	// $("#content").load("./include/contrat.html");
}

function includeWebmail(){
	$("#content").empty();
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
	$("#content").empty();
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