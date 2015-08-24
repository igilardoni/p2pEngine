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
	var link = "mailto:"+$("#emailReceiver").val()
        + "?subject=" + content.subject
        + "&body=" + content.text;
	//window.location.href = link;
	window.open(link, "_blank");
}

function sponsorBootstrapSaved(content){
	includeBoostrapSetting();
}