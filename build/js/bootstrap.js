/**
 * JavaScript for managing bootstrap
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function loadIP(){
	sendQueryEmpty("loadIP");
}

function sendBootstrap(){
	var emailReceiver = $("#emailReceiver").val();
	var emailSender = $("#emailSender").val();
	var passwordSender = $("#passwordSender").val();
	var content = {
			"emailReceiver":emailReceiver,
			"emailSender":emailSender,
			"passwordSender":passwordSender
			};
	sendQuery("sendBoostrap", content);
}

function sponsorBootstrap() {
	var f = $("#bootstrapFile")[0].files[0]; 
	var contents;
	if (f) {
		var r = new FileReader();
		r.onload = function(e) { 
			contents = e.target.result;  
		}
		r.readAsText(f);
	} else { 
		alert("Failed to load file");
	}
	
	var content = {
			"fileContent":contents
	}
	sendQuery("sponsorBootstrap", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function ipLoaded(content){
	$("#IP").append("<tr><td>"+content.ip+"</td></tr>");
}

function bootstrapSent(content){
	printFeedback(content.feedback, true);
}

function bootstrapNotSent(content){
	printFeedback(content.feedback, false);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getBootstrapSetting(){
	return getElement(boostrapSetting[0]);
}