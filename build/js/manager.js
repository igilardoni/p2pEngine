/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function includeLogin(){
	$("#content").empty();
	$("#content").append(getLoginForm());
}

function includeRegistration(){
	$("#content").empty();
	$("#content").append(getRegistrationForm());
}

function includeHome(){
	$("#content").empty();
	$("#content").load("./include/home.html");
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
	// TODO
}