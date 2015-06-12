/**
 * JavaScript for include or remove fragments
 * @author Michael DUBUIS
 */
function includeLogin(){
	$("#content").empty();
	$("#content").load("./include/login.html");
	$("#username").val("Eldoran");
	$("footer").append("<p>test</p>");
	
}

function includeRegistration(){
	$("#content").empty();
	$("#content").load("./include/registration.html");
}

function includeHome(){
	$("#content").empty();
	$("#content").load("./include/home.html");
}

function includeSearch(){
	$("#content").empty();
	// $("#content").load("./include/search.html");
}

function includeContrat(){
	$("#content").empty();
	// $("#content").load("./include/contrat.html");
}

function includeWebmail(){
	$("#content").empty();
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
	$("header").load("./include/header.html");
}

function removeHeader(){
	$("header").empty();
}