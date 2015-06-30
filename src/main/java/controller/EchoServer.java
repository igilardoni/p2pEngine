package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.interlocutors.AbstractInterlocutor;
import controller.interlocutors.AddItemFavorites;
import controller.interlocutors.AddItem;
import controller.interlocutors.LoadAccount;
import controller.interlocutors.LoadBase64Image;
import controller.interlocutors.LoadCategories;
import controller.interlocutors.LoadConversation;
import controller.interlocutors.LoadItem;
import controller.interlocutors.LoadItemFavorites;
import controller.interlocutors.LoadItemSearchField;
import controller.interlocutors.LoadItemSearchFieldCategory;
import controller.interlocutors.LoadItemSearchFieldType;
import controller.interlocutors.LoadItems;
import controller.interlocutors.LoadItemsFavorites;
import controller.interlocutors.LoadMessage;
import controller.interlocutors.LoadMessages;
import controller.interlocutors.LoadType;
import controller.interlocutors.Register;
import controller.interlocutors.RemoveItem;
import controller.interlocutors.RemoveItemFavorites;
import controller.interlocutors.SignIn;
import controller.interlocutors.SignOut;
import controller.interlocutors.UpdateAccount;
import controller.interlocutors.UpdateItem;


/**
 * Echo server class intercept messages PREVENTING JS and made the call
 * For functions in the model.
 *
 */


/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo.
 */
@ServerEndpoint("/serv") 
public class EchoServer {
	HashMap<String, AbstractInterlocutor> interlocutors = new HashMap<String, AbstractInterlocutor>();
	
	/**
	 * @OnOpen allows us to intercept the creation of a new session.
	 * The session class allows us to send data to the user.
	 * In the method onOpen, we'll let the user know that the handshake was 
	 * successful.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		System.out.println("INFO : "+EchoServer.class.getName()+".onOpen : Connection Established");
		
		interlocutors.put("signIn", new SignIn());
		interlocutors.put("signOut", new SignOut());
		interlocutors.put("register", new Register());
		interlocutors.put("loadAccount", new LoadAccount());
		interlocutors.put("updateAccount", new UpdateAccount());
		
		interlocutors.put("addItem", new AddItem());
		interlocutors.put("loadItems", new LoadItems());
		interlocutors.put("loadItem", new LoadItem());
		interlocutors.put("updateItem", new UpdateItem());
		interlocutors.put("removeItem", new RemoveItem());
		interlocutors.put("loadCategories", new LoadCategories());
		interlocutors.put("loadType", new LoadType());
		interlocutors.put("loadItemSearchField", new LoadItemSearchField());
		interlocutors.put("loadItemSearchFieldCategory", new LoadItemSearchFieldCategory());
		interlocutors.put("loadItemSearchFieldType", new LoadItemSearchFieldType());
		
		interlocutors.put("loadItemsFavorites", new LoadItemsFavorites());
		interlocutors.put("addItemFavorites", new AddItemFavorites());
		interlocutors.put("removeItemFavorites", new RemoveItemFavorites());
		interlocutors.put("loadItemFavorites", new LoadItemFavorites());
		
		interlocutors.put("loadMessages", new LoadMessages());
		interlocutors.put("loadConversation", new LoadConversation());
		interlocutors.put("loadMessage", new LoadMessage());
		
		interlocutors.put("loadBase64Image", new LoadBase64Image());
	}
	
	/**
	 * The user closes the connection.
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("INFO : "+EchoServer.class.getName()+".onOpen : Session has ended");
	}
	
	/**
	 * When a user sends a message to the server, this method will intercept the message
	 * and allow us to react to it. For now the message is read as a String.
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, final Session session){
		try {
			JSONObject jsonObject = new JSONObject(message);
			if(jsonObject.get("content") == null){
				System.err.println("content null");
				return;
			}
			System.out.println("INFO : "+EchoServer.class.getName()+" Query input -> "+jsonObject.getString("query"));
			System.out.println("\t"+jsonObject.getString("content"));
			if(!interlocutors.containsKey(jsonObject.getString("query"))){
				System.err.println("\t"+jsonObject.getString("query")+" is an unknow query");
				return;
			}
			AbstractInterlocutor absI = interlocutors.get(jsonObject.getString("query")).getClass().newInstance();
			absI.init(jsonObject.getString("content"), session);
			absI.start();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
