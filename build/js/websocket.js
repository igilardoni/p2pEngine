/**
 * JavaScript for open and close WebSocket
 *            for send message to Java
 *            for catch message from Java
 * @author Michael DUBUIS
 */
var webSocket;
var serverAdress = "ws://localhost:8080/SXPManager/serv";

// open a socket to communicate with the server
function openSocket(){
	if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
		writeResponse("WebSocket OK.");
		return;
	}
	webSocket = new WebSocket(serverAdress);
	webSocket.onmessage = function(event){
		serverReply(event.data);
	};
	webSocket.onclose = function(event){
	};
}

//Distributes to the proper function
function serverReply(data){
	clearFeedback();
	data = JSON.parse(data);
	console.log("Input query : "+data.query+"\n\t"+JSON.stringify(data.content));
	window[data.query](data.content);
	if(data.content.feedback !== undefined)
		printFeedback(data.content.feedback, data.content.feedbackOk);
}

function sendQueryEmpty(query){
	console.log("Output query : "+query);
	var content = {};
	var data = {"query":query, "content":content};
	webSocket.send(JSON.stringify(data));
}

function sendQuery(query, content){
	console.log("Output query : "+query);
	var data = {"query":query, "content":content};
	webSocket.send(JSON.stringify(data));
}

function turnOff(){
	sendQueryEmpty("turnOff");
	$("body").empty();
	$("body").append(closePage);
}