package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.manager.Manager;
import model.data.user.User;
import model.network.search.ItemSearcher;
import model.network.search.SearchListener;
import util.DateConverter;


/**
 * Echo server class intercept messages PREVENTING JS and made the call
 * For functions in the model.
 *
 */


/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo .
 * TODO Prévoir le changement d'indexation sur identifiant unique (itemKey)
 */
@ServerEndpoint("/serv") 
public class EchoServer {
	ManagerBridge managerBridge =  new ManagerBridge();
	MessageSender messageSender = new MessageSender();

	/**
	 * @OnOpen allows us to intercept the creation of a new session.
	 * The session class allows us to send data to the user.
	 * In the method onOpen, we'll let the user know that the handshake was 
	 * successful.
	 */
	@OnOpen
	public void onOpen(Session session,EndpointConfig config){
		System.out.println("INFO : "+EchoServer.class.getName()+".onOpen : Connection Established");
	}

	/**
	 * When a user sends a message to the server, this method will intercept the message
	 * and allow us to react to it. For now the message is read as a String.
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, final Session session){
		JSONObject jsonObject = getJSON(message);
		try {
			if(jsonObject.get("content") == null){
				System.err.println("content null");
				return;
			}
			switch((String) jsonObject.get("query")){
			case "signIn" : signIn(jsonObject.get("content").toString(), session); break;
			case "register" : register(jsonObject.get("content").toString(), session); break;
			default:
				System.out.println("unknown : "+((String) jsonObject.get("query")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
	
	private void register(String s, Session session) throws JSONException, IOException {
		JSONObject content = getJSON(s);
		String nick = content.getString("username");
		String password = content.getString("password");
		String name = content.getString("name");
		String firstName = content.getString("firstname");
		String email = content.getString("email");
		String phone = content.getString("phone");
		managerBridge.registration(nick, password, name, firstName, email, phone);
		
		JSONObject data = new JSONObject();
		data.put("query", "registration");
		content.put("ok", "ok");
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}

	private JSONObject getJSON(String string){
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void signIn(String s, Session session) throws JSONException, IOException, EncodeException {
		JSONObject jsonObject = getJSON(s);
		if(managerBridge.login((String) jsonObject.getString("username"), (String) jsonObject.getString("password"))){
			JSONObject data = new JSONObject();
			data.put("query", "login");
			JSONObject content = new JSONObject();
			content.put("ok", "ok");
			content.put("message", "user logged");
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}else{
			JSONObject data = new JSONObject();
			data.put("query", "login");
			JSONObject content = new JSONObject();
			content.put("ok", "no");
			content.put("message", "unknown account");
			data.put("content", content);
			System.out.println(data.toString());
			session.getBasicRemote().sendText(data.toString());
		}
	}

	/**
	 * The user closes the connection.
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("INFO : "+EchoServer.class.getName()+".onOpen : Session has ended");
	}
}
