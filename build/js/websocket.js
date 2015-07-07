/**
 * JavaScript for open and close WebSocket
 *            for send message to Java
 *            for catch message from Java
 * @author Michael DUBUIS
 */
var webSocket;
var websocket2;
var serverAdress = "ws://localhost:8080/EchoChamber/serv";

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
	data = JSON.parse(data);
	console.log("Input query : "+data.query+"\n\t"+JSON.stringify(data.content));
	switch(data.query){
	case "registration":					registration(data.content); break;
	case "accountUpdated":					accountUpdated(data.content); break;
	case "accountLoaded":					accountLoaded(data.content); break;
	case "login":							login(data.content); break;
	case "logout":							logout(data.content); break;
	
	case "itemRemoved":						itemRemoved(data.content); break;
	case "itemsLoaded":						itemsLoaded(data.content); break;
	case "itemUpdated":						itemUpdated(data.content); break;
	case "itemAdded":						itemAdded(data.content); break;
	case "itemLoaded":						itemLoaded(data.content); break;
	case "categoryLoaded":					categoryLoaded(data.content); break;
	case "typeLoaded":						typeLoaded(data.content); break;
	case "itemSearchFieldLoaded":			itemSearchFieldLoaded(data.content); break;
	case "itemSearchFieldCategoryLoaded":	itemSearchFieldsLoaded(data.content); break;
	case "itemSearchFieldTypeLoaded":		itemSearchFieldsLoaded(data.content); break;
	
	case "favoritesItemsLoadingStart":		favoritesItemsLoadingStart(data.content); break;
	case "favoritesItemsLoaded":			favoritesItemsLoaded(data.content); break;
	case "favoritesItemsLoadingEnd":		favoritesItemsLoadingEnd(data.content); break;
	case "itemFavoritesRemoved":			itemFavoritesRemoved(data.content); break;
	case "favoritesItemLoaded":				favoritesItemLoaded(data.content); break;
	
	case "displayHome":						displayHome(data.content); break;
	case "displayItem":						displayItem(data.content); break;
	case "displayAccount":					displayAccount(data.content); break;
	case "displayContrat":					displayContrat(data.content); break;
	
	case "messageNotSent":					messageNotSent(data.content); break;
	
	case "contratCreated":					contratCreated(data.content); break;
	case "itemForContratLoaded":			itemForContratLoaded(data.content); break;
	default: alert(data.query+" unknow !"); // TODO For debugging delete when finished 
	}
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