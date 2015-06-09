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
import model.data.user.User;
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
		Manager manager = Application.getInstance().getManager();

		String[] requet = message.split(":");
		String token = requet[0];
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
		String itemKey; // TODO use it

		switch (token) {
		case "/index": // user Login
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
		case "/log_out": // user logout
			Application.getInstance().getManager().logout();
			try {
				session.getBasicRemote().sendText("log_index:");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/register": // user registration
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
		///////////////////////////////////////////////// REDIRECTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/newobjet": //Just for redirection
			try {
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/search": //Just for redirection 
			try {
				session.getBasicRemote().sendText("Search.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newindex": //Just for redirection
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newchat": //Just for redirection
			try {
				session.getBasicRemote().sendText("Message.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/contrat": //Just for redirection
			try {
				session.getBasicRemote().sendText("Contrat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/user_compte": //Just for redirection
			try {
				session.getBasicRemote().sendText("User_compte.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		//////////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/new_objet_add" : //add new object
			title = 			requet[1];
			category = 			requet[2];
			description = 		requet[3];
			image = 			requet[4]+":"+requet[5];
			country = 			requet[6];
			contact = 			requet[7];
			lifeTime = 			requet[8];
			type = 				requet[9];
			managerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
			break;

		case "/new_objet_update" : // modifying an object
			title = 			requet[1];
			category = 			requet[2];
			description = 		requet[3];
			image = 			requet[4]+":"+requet[5];
			country = 			requet[6];
			contact = 			requet[7];
			lifeTime = 			requet[8];
			type = 				requet[9];
			managerBridge.updateItem(title, category, description, image, country, contact, lifeTime, type);
			try {
				session.getBasicRemote().sendText("update_objet:");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/addToFavories" :
			title = requet[1];
			Item item = new Item(); // TODO Search Objet ou objet passé en paramètre !
			managerBridge.addFavoriteItem(item);
			try {
				session.getBasicRemote().sendText("addToFavoriesOK:");
			} catch (IOException e) {
				e.printStackTrace();
			}
		case "/update_compte_user": // Update current user token
			nick = requet[1];
			name = requet[2];
			firstName = requet[3];
			email = requet[4];
			phone = requet[6];
			newPassword = requet[5];
			oldPassword = requet[7];
			if(managerBridge.updateAccount(nick, oldPassword, newPassword, name, firstName, email, phone)){
				System.out.println("INFO : "+EchoServer.class.getName()+" : Account updated");
				try {
					session.getBasicRemote().sendText("load_update_user:");
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}else{
				System.out.println("INFO : "+EchoServer.class.getName()+" : Account update fail");
				try {
					session.getBasicRemote().sendText("update_user_false:");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/creatContrat" :
			String object = 		requet[1];
			String owner = 			requet[2];
			String montant = 		requet[3];
			String name_per = 		requet[4];
			String taxe = 			requet[5];
			String descriptionCon = requet[6];
			String clause = 		requet[7];
			String mode = 			requet[8];
			// ICI LA FONCTION QUI CREE UN CONRTAT ET GENERE UN PDF
			System.out.println(""+object+":"+owner+":"+montant+":"+name_per+":"+taxe+":"+descriptionCon+":"+clause+":"+mode);
			try {
				session.getBasicRemote().sendText("ContratOK:"+object+":"+owner+":"+montant+":"+name_per+":"+taxe+":"+descriptionCon+":"+clause+":"+mode);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		//////////////////////////////////////////////////// LOADERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/load_use": // Load current user
			User user = managerBridge.getCurrentUser();
			nick = user.getNick();
			name = user.getName();
			firstName = user.getFirstName();
			email = user.getEmail();
			phone = user.getPhone();
			try {
				session.getBasicRemote().sendText("load_user:"+nick+":"+name+":"+firstName+":"+email+":"+phone);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/load_item": // Load items of current user
			ArrayList<Item> items = managerBridge.getCurrentUserItem();
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
		case "/zoom_item": //Return current object
			// TODO Need clean
			Manager manager1 = Application.getInstance().getManager();
			Item item_search = manager1.getItemCurrentUser(requet[1]);

			Long enddingDate = item_search.getLifeTime() + item_search.getDate();
			try {
				session.getBasicRemote().sendText("zoom_item_result:"+
						item_search.getTitle()+":"+item_search.getCategory().getStringChoice()+":"+
						item_search.getCountry()+":"+DateConverter.getString(enddingDate)+":"+item_search.getType()+":"+item_search.getDescription()+":"+item_search.getImage()
						+":"+item_search.getDate()+":"+item_search.getContact());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
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
		case "/load_favories":
			/*
			 * J'ai ajouté une fonction au ManagerBridge pour ça.
			 * TODO >>>>>>>>ATTENTION<<<<<<<<
			 * il FAUT que tu conserves les ItemKey,
			 * à la prochaine release, je change
			 * le système de référencement des objets ! 
			 */
			ArrayList<Item> favoriteItems = managerBridge.getFavoriteItems();
			if(!favoriteItems.isEmpty()){
				for (int i = 0; i < favoriteItems.size(); i++) {
					try {
						session.getBasicRemote().sendText("LoadALLFavories:"+favoriteItems.get(i).getTitle()+":"+favoriteItems.get(i).getCountry()+":"+favoriteItems.get(i).getDescription());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		case "/load_contrat": // TODO load_contrat
			//de la meme manier que  load_favories ici faut mettre en place une fonction qui charge tout les contrat
			// et les envoi un par un , JS intercepte et affiche dans le tableau
			//exemple
			manager = Application.getInstance().getManager();
			ArrayList<Item> it3 = manager.getUserItems(manager.getCurrentUser().getKeys().getPublicKey().toString(16));
			if(!it3.isEmpty()){
				for (int i = 0; i < it3.size(); i++) {
					try {
						session.getBasicRemote().sendText("loadAllcontrat:"+it3.get(i).getTitle()+":"+it3.get(i).getCountry()+":"+it3.get(i).getDescription());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		//////////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		case "/remove_item": //  remove item
			title = requet[1];
			managerBridge.removeItem(title);
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
			String result = messageSender.sendMessageToNick(msg, nick)?"sendt":"sendf";
			try {
				session.getBasicRemote().sendText("result_sendMessage:"+result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		default:
			System.err.println("WARNING : "+EchoServer.class.getName()+".onMessage : "+token+" is an unknow token");
			break;
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
