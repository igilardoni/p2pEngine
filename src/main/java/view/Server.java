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
import view.interlocutors.LoadBase64Image;
import view.interlocutors.LoadIP;
import view.interlocutors.LoadType;
import view.interlocutors.RemoveItemFavorites;
import view.interlocutors.SendBootstrap;
import view.interlocutors.SponsorBootstrap;
import view.interlocutors.TurnOff;
import view.interlocutors.contrat.AddClause;
import view.interlocutors.contrat.ChangeContratExchangeRule;
import view.interlocutors.contrat.LoadContentContrat;
import view.interlocutors.contrat.LoadContrat;
import view.interlocutors.contrat.LoadContrats;
import view.interlocutors.contrat.LoadItemForContrat;
import view.interlocutors.contrat.NewContrat;
import view.interlocutors.contrat.RemoveClause;
import view.interlocutors.contrat.RemoveContrat;
import view.interlocutors.contrat.RemoveItemContrat;
import view.interlocutors.contrat.RenameContrat;
import view.interlocutors.contrat.SaveClause;
import view.interlocutors.item.AddItem;
import view.interlocutors.item.AddItemFavorites;
import view.interlocutors.item.LoadCategories;
import view.interlocutors.item.LoadItem;
import view.interlocutors.item.LoadItemFavorites;
import view.interlocutors.item.LoadItemSearch;
import view.interlocutors.item.LoadItemSearchField;
import view.interlocutors.item.LoadItemSearchFieldCategory;
import view.interlocutors.item.LoadItemSearchFieldType;
import view.interlocutors.item.LoadItems;
import view.interlocutors.item.LoadItemsFavorites;
import view.interlocutors.item.RemoveItem;
import view.interlocutors.item.UpdateItem;
import view.interlocutors.message.LoadConversation;
import view.interlocutors.message.LoadConversations;
import view.interlocutors.message.LoadMessage;
import view.interlocutors.message.LoadMessages;
import view.interlocutors.message.RemoveConversation;
import view.interlocutors.message.RemoveMessage;
import view.interlocutors.message.SendMessage;
import view.interlocutors.search.SearchItem;
import view.interlocutors.search.SearchUser;
import view.interlocutors.user.LoadAccount;
import view.interlocutors.user.LoadKnownUsers;
import view.interlocutors.user.Register;
import view.interlocutors.user.SignIn;
import view.interlocutors.user.SignOut;
import view.interlocutors.user.UpdateAccount;


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
public class Server {
	HashMap<String, AbstractInterlocutor> interlocutors = new HashMap<String, AbstractInterlocutor>();
	
	/**
	 * @OnOpen allows us to intercept the creation of a new session.
	 * The session class allows us to send data to the user.
	 * In the method onOpen, we'll let the user know that the handshake was 
	 * successful.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		System.out.println("INFO : "+Server.class.getName()+".onOpen : Connection Established");
		
		AbstractInterlocutor.setCom(session.getAsyncRemote());
		/* Users */
		interlocutors.put("signIn", new SignIn());
		interlocutors.put("signOut", new SignOut());
		interlocutors.put("register", new Register());
		interlocutors.put("loadAccount", new LoadAccount());
		interlocutors.put("updateAccount", new UpdateAccount());
		/* Items */
		interlocutors.put("addItem", new AddItem());
		interlocutors.put("loadItems", new LoadItems());
		interlocutors.put("loadItem", new LoadItem());
		interlocutors.put("updateItem", new UpdateItem());
		interlocutors.put("removeItem", new RemoveItem());
		interlocutors.put("loadCategories", new LoadCategories());
		interlocutors.put("loadType", new LoadType());
		/* Search */
		interlocutors.put("loadItemSearchField", new LoadItemSearchField());
		interlocutors.put("loadItemSearchFieldCategory", new LoadItemSearchFieldCategory());
		interlocutors.put("loadItemSearchFieldType", new LoadItemSearchFieldType());
		interlocutors.put("searchItem", new SearchItem());
		interlocutors.put("loadItemSearch", new LoadItemSearch());
		interlocutors.put("searchUsers", new SearchUser());
		/* Favorites */
		interlocutors.put("loadItemsFavorites", new LoadItemsFavorites());
		interlocutors.put("addItemFavorites", new AddItemFavorites());
		interlocutors.put("removeItemFavorites", new RemoveItemFavorites());
		interlocutors.put("loadItemFavorites", new LoadItemFavorites());
		/* Messages */
		interlocutors.put("loadConversations", new LoadConversations());
		interlocutors.put("loadMessages", new LoadMessages());
		interlocutors.put("loadConversation", new LoadConversation());
		interlocutors.put("loadMessage", new LoadMessage());
		interlocutors.put("sendMessage", new SendMessage());
		interlocutors.put("removeMessage", new RemoveMessage());
		interlocutors.put("removeConversation", new RemoveConversation());
		interlocutors.put("getKnownUsers", new LoadKnownUsers());
		/* Contrat */
		interlocutors.put("newContrat", new NewContrat());
		interlocutors.put("loadItemForContrat", new LoadItemForContrat());
		interlocutors.put("loadContrats", new LoadContrats());
		interlocutors.put("loadContrat", new LoadContrat());
		interlocutors.put("loadContentContrat", new LoadContentContrat());
		interlocutors.put("removeContrat", new RemoveContrat());
		interlocutors.put("removeItemContrat", new RemoveItemContrat());
		interlocutors.put("renameContrat", new RenameContrat());
		interlocutors.put("changeContratExchangeRule", new ChangeContratExchangeRule());
		interlocutors.put("addClause", new AddClause());
		interlocutors.put("saveClause", new SaveClause());
		interlocutors.put("removeClause", new RemoveClause());
		/* Others */
		interlocutors.put("loadBase64Image", new LoadBase64Image());
		interlocutors.put("loadIP", new LoadIP());
		interlocutors.put("turnOff", new TurnOff());
		/* Bootstrap */
		interlocutors.put("sendBoostrap", new SendBootstrap());
		interlocutors.put("sponsorBootstrap", new SponsorBootstrap());
	}
	
	/**
	 * The user closes the connection.
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("INFO : "+Server.class.getName()+".onOpen : Session has ended");
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
			// TODO Remove print when GUI is finished
			System.out.println("INFO : "+Server.class.getName()+" Query input -> "+jsonObject.getString("query"));
			System.out.println("\t"+jsonObject.getString("content"));
			if(!interlocutors.containsKey(jsonObject.getString("query"))){
				System.err.println("\t"+jsonObject.getString("query")+" is an unknow query");
				return;
			}
			AbstractInterlocutor absI = null;
			absI = interlocutors.get(jsonObject.getString("query"));
			absI.init(jsonObject.getString("content"));
			absI.run();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
