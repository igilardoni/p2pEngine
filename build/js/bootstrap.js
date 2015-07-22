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
	$(".inputWrong").removeClass("inputWrong");
	var emailReceiver = $("#emailReceiver").val();
	if(!isEmail(emailReceiver)){
		$("#emailReceiver").addClass("inputWrong");
		feedback = "Email is invalid !";
		printFeedback(feedback, false);
		return;
	}
	sendQueryEmpty("sendBoostrap");
}

function sponsorBootstrap() {
	/*var f = $("#bootstrapFile")[0].files[0]; 
	var contents;
	if (f) {
		var r = new FileReader();
		r.onload = function(e) { 
			contents = e.target.result;
			var content = {"fileContent":contents}
			sendQuery("sponsorBootstrap", content);
		}
		r.readAsText(f);
	} else { 
		$("#bootstrapFile").addClass("inputWrong");
		return;
	}*/
	if($("#bootstrapIP").val() == ""){
		$("#bootstrapIP").addClass("inputWrong");
		var feedback = "You must copy the IP address sent by your sponsor.";
		printFeedback(feedback, false);
		return;
	}
	var content = {
			"ips":$("#bootstrapIP").val()
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
	var link = "mailto:"+$("#emailReceiver").val()
        + "?subject=" + content.subject
        + "&body=" + content.text;
	window.location.href = link;
}

function bootstrapNotSent(content){
	printFeedback(content.feedback, false);
}

function sponsorBootstrapSaved(content){
	includeBoostrapSetting();
	printFeedback(content.feedback, true);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getBootstrapSetting(){
	return getElement(boostrapSetting[0]);
}

function getBootstrapInvitation(){
	return getElement(boostrapInvitation[0]);
}