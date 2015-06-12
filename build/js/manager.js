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
	$("#content").empty();
	$("#content").replaceWith(getHome());
}

function includeSearch(){
	$("#content").empty();
	// TODO
	// $("#content").load("./include/search.html");
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
	$("nav").load("./include/menu.html");
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
	
}