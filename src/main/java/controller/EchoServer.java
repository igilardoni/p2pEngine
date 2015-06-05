package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.manager.Manager;
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
		System.out.println("INFO : "+EchoServer.class.getName()+" : Connection Established");
	}

	/**
	 * When a user sends a message to the server, this method will intercept the message
	 * and allow us to react to it. For now the message is read as a String.
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, final Session session){
		Manager manager = Application.getInstance().getManager();
		
		String[] requet = message.split(":");
		String query = requet[0];
		/* Variable for User */
		String nick;
		String password;
		String name;
		String firstName;
		String email;
		String phone;
		String newPassword;
		String oldPassword;
		/* Variables for Item */
		String title;
		String category;
		String description;
		String image;
		String country;
		String contact;
		String lifeTime;
		String type;
		String itemKey;
		
		switch (query) {
		case "/index": // Login query
			nick = requet[2];
			password = requet[1];
			if(managerBridge.login(nick, password)){
				try {
					session.getBasicRemote().sendText("index.html:");
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
			break;
		case "/log_out": // Logout query
			Application.getInstance().getManager().logout();
			try {
				session.getBasicRemote().sendText("log_index:");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/register": // Registration query
			nick = requet[1];
			password = requet[2];
			name = requet[3];
			firstName = requet[4];
			email = requet[5];
			phone = requet[6];
			managerBridge.registration(nick, password, name, firstName, email, phone);
			try {
				session.getBasicRemote().sendText("Se_connecter.html#tologin:");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/update_compte_user": // Update current user query
			nick = requet[1];
			name = requet[2];
			firstName = requet[3];
			email = requet[4];
			phone = requet[5];
			newPassword = requet[5];
			oldPassword = requet[7];
			if(managerBridge.updateAccount(nick, oldPassword, newPassword, name, firstName, email, phone)){
				try {
					session.getBasicRemote().sendText("load_update_user:");
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}else{
				try {
					session.getBasicRemote().sendText("update_user_false:");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/new_objet_add" : // Add item query
			title = 		requet[1];
			category = 		requet[2];
			description = 	requet[3];
			image = 		requet[4]+":"+requet[5];
			country = 		requet[6];
			contact = 		requet[7];
			lifeTime = 		requet[8];
			type = 			requet[9];
			managerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
			break;

		case "/new_objet_update" : // Update Item query
			title = 		requet[1];
			category = 		requet[2];
			description = 	requet[3];
			image = 		requet[4]+":"+requet[5];
			country = 		requet[6];
			contact = 		requet[7];
			lifeTime = 		requet[8];
			type = 			requet[9];
			managerBridge.updateItem(title, category, description, image, country, contact, lifeTime, type);
			try {
				session.getBasicRemote().sendText("update_objet:"); // ????
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		
		///////////////////////////////////////////////// REDIRECTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/search": // "Search" redirection query
			try {
				session.getBasicRemote().sendText("Search.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newindex": // "Index" redirection query
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newobjet": // "New Object" redirection query
			try {
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newchat": // "Chat" redirection query
			try {
				session.getBasicRemote().sendText("Message.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/contrat": // "Deal" redirection query
			try {
				session.getBasicRemote().sendText("Contrat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/user_compte": // "Account" redirection query
			try {
				session.getBasicRemote().sendText("User_compte.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		//////////////////////////////////////////////////// LOADERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/load_categories":
			ArrayList<String> categories = Category.getAllCategorie();
			StringBuffer s = new StringBuffer();
			s.append("resultCategories");
			for (String c : categories) {
				s.append(":");
				s.append(c);
			}
			System.out.println(s.toString());
			try {
				session.getBasicRemote().sendText(s.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/load_use": // Load the current user and return to Javascript
			nick = Application.getInstance().getManager().getCurrentUser().getNick();
			name = Application.getInstance().getManager().getCurrentUser().getName();
			firstName = Application.getInstance().getManager().getCurrentUser().getFirstName();
			email = Application.getInstance().getManager().getCurrentUser().getEmail();
			phone = Application.getInstance().getManager().getCurrentUser().getPhone();
			try {
				session.getBasicRemote().sendText("load_user:"+nick+":"+name+":"+firstName+":"+email+":"+phone);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/load_item": // Load the current user's items and return to Javascript
			ArrayList<Item> items = manager.getUserItems(manager.getCurrentUser().getKeys().getPublicKey().toString(16));
			if(!items.isEmpty()){
				for (int i = 0; i < items.size(); i++) {
					try {
						session.getBasicRemote().sendText("load_item:"+items.get(i).getTitle()+":"+items.get(i).getCountry()+":"+items.get(i).getDescription());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case "/zoom_item": // Load item's data and return to Javascript
			itemKey = requet[1];
			Item item = manager.getItemCurrentUser(itemKey);
			Long enddingDate = item.getLifeTime() + item.getDate();
			try {
				session.getBasicRemote().sendText("zoom_item_result:"+
						item.getTitle()+":"+item.getCategory().getStringChoice()+":"+
						item.getCountry()+":"+DateConverter.getString(enddingDate)+":"+item.getType()+":"+item.getDescription()+":"+item.getImage()
						+":"+item.getDate()+":"+item.getContact());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		//////////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/remove_item":  // Remove item query
			itemKey = requet[1];
			managerBridge.removeItem(itemKey);
			break;
		////////////////////////////////////////////////// COMMUNICATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/search_itme": // Search an item in network
			title = requet[1];
			SearchItemController sc = new SearchItemController();
			sc.addListener(new SearchListener<Item>() {
				@Override
				public void searchEvent(Item event) {
					try {
						session.getBasicRemote().sendText("result_search_item:"+event.getTitle()+":"+event.getDescription()+":"+event.getCountry());
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
			});
			sc.startSearch(title);
			break;
		case "/send_message": // Send a message to a nick's user
			String msg = requet[1];
			nick = requet[2];
			String result=messageSender.sendMessageToNick(msg, nick)?"sendt":"sendf";
			try {
				session.getBasicRemote().sendText("result_sendMessage:"+result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.err.println("WARNING : "+EchoServer.class.getName()+".onMessage : "+query+" is an unknow query");
			break;
		}
	}	
	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("INFO : "+EchoServer.class.getName()+" : Session has ended");
	}
}
