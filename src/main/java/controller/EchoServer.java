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
			case "signOut" : signOut(jsonObject.get("content").toString(), session); break;
			case "register" : register(jsonObject.get("content").toString(), session); break;
			case "addItem" : addItem(jsonObject.get("content").toString(), session); break;
			case "loadItems" : loadItems(jsonObject.get("content").toString(), session); break;
			case "loadItem" : loadItem(jsonObject.get("content").toString(), session); break;
			case "updateItem" : updateItem(jsonObject.get("content").toString(), session); break;
			case "removeItem" : removeItem(jsonObject.get("content").toString(), session); break;
			case "loadCategories" : loadCategories(jsonObject.get("content").toString(), session); break;
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
	private JSONObject getJSON(String string){
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void signIn(String s, Session session) throws JSONException, IOException, EncodeException {
		JSONObject c = getJSON(s);
		if(managerBridge.login((String) c.getString("username"), (String) c.getString("password"))){
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
			session.getBasicRemote().sendText(data.toString());
		}
	}
	
	private void signOut(String s, Session session) throws JSONException, IOException{
		String username = managerBridge.getCurrentUser().getNick();
		managerBridge.logout();
		JSONObject data = new JSONObject();
		data.put("query", "logout");
		JSONObject content = new JSONObject();
		content.put("username", username);
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}
	
	private void register(String s, Session session) throws JSONException, IOException {
		JSONObject c = getJSON(s);
		String nick = c.getString("username");
		String password = c.getString("password");
		String name = c.getString("name");
		String firstName = c.getString("firstname");
		String email = c.getString("email");
		String phone = c.getString("phone");
		managerBridge.registration(nick, password, name, firstName, email, phone);
		
		JSONObject data = new JSONObject();
		data.put("query", "registration");
		c.put("ok", "ok");
		data.put("content", c);
		session.getBasicRemote().sendText(data.toString());
	}
	
	private void addItem(String s, Session session) throws JSONException, IOException{
		JSONObject c = getJSON(s);
		String category = c.getString("category");
		String contact = c.getString("contact");
		String country = c.getString("country");
		String description = c.getString("description");
		String image = c.getString("image");
		String lifeTime = c.getString("lifetime");
		String title = c.getString("title");
		String type = c.getString("type");
		String itemKey = managerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
		// Answer
		if(itemKey == null || itemKey.isEmpty()){
			// Send error message
		}else{
			JSONObject data = new JSONObject();
			data.put("query", "itemAdded");
			JSONObject content = new JSONObject();
			content.put("itemKey", itemKey);
			content.put("title", title);
			content.put("description", description);
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}

	private void loadItems(String s, Session session) throws JSONException, IOException{
		ArrayList<Item> items = managerBridge.getCurrentUserItems();
		if(items == null || items.isEmpty()) return;
		for (Item item : items) {
			JSONObject data = new JSONObject();
			data.put("query", "itemsLoaded");
			JSONObject content = new JSONObject();
			content.put("itemKey", item.getItemKey());
			content.put("title", item.getTitle());
			content.put("description", item.getDescription());
			data.put("content", content);
			session.getBasicRemote().sendText(data.toString());
		}
	}
	
	private void loadItem(String s, Session session) throws JSONException, IOException{
		JSONObject c = getJSON(s);
		String itemKey = c.getString("itemKey");
		Item item = managerBridge.getCurrentUserItem(itemKey);
		JSONObject data = new JSONObject();
		data.put("query", "itemLoaded");
		JSONObject content = new JSONObject();
		content.put("itemKey", itemKey);
		content.put("title", item.getTitle());
		content.put("description", item.getDescription());
		content.put("category", item.getCategory().getStringChoice());
		content.put("contact", item.getContact());
		content.put("country", item.getCountry());
		content.put("image", item.getImage());
		content.put("lifetime", item.getLifeTime());
		content.put("type", item.getType());
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}
	
	private void updateItem(String s, Session session) throws JSONException, IOException{
		JSONObject c = getJSON(s);
		String itemKey = c.getString("itemKey");
		String category = c.getString("category");
		String contact = c.getString("contact");
		String country = c.getString("country");
		String description = c.getString("description");
		String image = c.getString("image");
		String lifeTime = c.getString("lifetime");
		String title = c.getString("title");
		String type = c.getString("type");
		managerBridge.updateItem(itemKey, title, category, description, image, country, contact, lifeTime, type);

		JSONObject data = new JSONObject();
		data.put("query", "itemUpdated");
		JSONObject content = new JSONObject();
		content.put("itemKey", itemKey);
		content.put("title", title);
		content.put("description", description);
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}
	
	private void removeItem(String s, Session session) throws JSONException, IOException{
		JSONObject c = getJSON(s);
		String itemKey = c.getString("itemKey");
		managerBridge.removeItem(itemKey);
		JSONObject data = new JSONObject();
		data.put("query", "itemRemoved");
		JSONObject content = new JSONObject();
		content.put("itemKey", itemKey);
		data.put("content", content);
		session.getBasicRemote().sendText(data.toString());
	}
	
	private void loadCategories(String s, Session session) throws JSONException, IOException{
		ArrayList<String> listCat = Category.getAllCategorie();
		for (String string : listCat) {
			JSONObject data = new JSONObject();
			data.put("query", "categoryLoaded");
			JSONObject content = new JSONObject();
			content.put("category", string);
			data.put("content", content);
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
