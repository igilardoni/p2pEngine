package view;

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

import view.interlocutors.AbstractInterlocutor;
import view.interlocutors.AddItem;
import view.interlocutors.AddItemFavorites;
import view.interlocutors.LoadAccount;
import view.interlocutors.LoadBase64Image;
import view.interlocutors.LoadCategories;
import view.interlocutors.LoadContrat;
import view.interlocutors.LoadContrats;
import view.interlocutors.LoadConversation;
import view.interlocutors.LoadItem;
import view.interlocutors.LoadItemFavorites;
import view.interlocutors.LoadItemForContrat;
import view.interlocutors.LoadItemSearchField;
import view.interlocutors.LoadItemSearchFieldCategory;
import view.interlocutors.LoadItemSearchFieldType;
import view.interlocutors.LoadItems;
import view.interlocutors.LoadItemsFavorites;
import view.interlocutors.LoadMessage;
import view.interlocutors.LoadMessages;
import view.interlocutors.LoadType;
import view.interlocutors.NewContrat;
import view.interlocutors.Register;
import view.interlocutors.RemoveItem;
import view.interlocutors.RemoveItemFavorites;
import view.interlocutors.SearchItem;
import view.interlocutors.SendMessage;
import view.interlocutors.SignIn;
import view.interlocutors.SignOut;
import view.interlocutors.UpdateAccount;
import view.interlocutors.UpdateItem;


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
		
		AbstractInterlocutor.setCom(session.getAsyncRemote());
		
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
		interlocutors.put("searchItem", new SearchItem());
		
		interlocutors.put("loadItemsFavorites", new LoadItemsFavorites());
		interlocutors.put("addItemFavorites", new AddItemFavorites());
		interlocutors.put("removeItemFavorites", new RemoveItemFavorites());
		interlocutors.put("loadItemFavorites", new LoadItemFavorites());
		
		interlocutors.put("loadMessages", new LoadMessages());
		interlocutors.put("loadConversation", new LoadConversation());
		interlocutors.put("loadMessage", new LoadMessage());
		interlocutors.put("sendMessage", new SendMessage());
		
		interlocutors.put("newContrat", new NewContrat());
		interlocutors.put("loadItemForContrat", new LoadItemForContrat());
		interlocutors.put("loadContrats", new LoadContrats());
		interlocutors.put("loadContrat", new LoadContrat());
		
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
			AbstractInterlocutor absI = null;
			absI = interlocutors.get(jsonObject.getString("query")).getClass().newInstance();
			absI.init(jsonObject.getString("content"));
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
