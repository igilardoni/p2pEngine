package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;



import net.jxta.peer.PeerID;
import util.DateConverter;
import util.VARIABLES;
import util.secure.AsymKeysImpl;
import model.Application;
import model.data.item.Category;
import model.data.item.Item;
import model.data.manager.Manager;
import model.data.user.Message;
import model.data.user.User;
import model.network.search.Search;
import model.network.search.SearchListener;


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
	ManagerBridge managerB =  new ManagerBridge();

	/**
	 * @OnOpen allows us to intercept the creation of a new session.
	 * The session class allows us to send data to the user.
	 * In the method onOpen, we'll let the user know that the handshake was 
	 * successful.
	 */
	@OnOpen
	public void onOpen(Session session,EndpointConfig config){
		//System.out.println(session.getId() + " has opened a connection");
		System.out.println("Connection Established");
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
		/*String category;
		String description;
		String image;
		String country;
		String contact;
		String lifeTime;
		String type;
		String itemKey;
		*/
		switch (token) {
		case "/index": // user Login
			nick = requet[2];
			password = requet[1];
			if(managerB.login(nick, password)){
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
			managerB.registration(nick, password, name, firstName, email, phone);
			try {
				session.getBasicRemote().sendText("Se_connecter.html#tologin:");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		
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
		case "/new_objet_add" : //add new object
			/*
			 * requet[1] : title
			 * requet[2] : category
			 * requet[3] : description
			 * requet[4] : image
			 * requet[5] : country
			 * requet[6] : contact
			 * requet[7] : lifeTime
			 * requet[8] : type
			 */
			// Tu réalises que j'avais mis des variables pour que ce soit lisible et que tu es juste revenu en arrière... ça n'a vraiment aucun intérêt !!!
			managerB.addItem(requet[1], requet[2], requet[3], requet[4]+":"+requet[5], requet[6],requet[7], requet[8], requet[9]);
			break;

			case "/new_objet_update" : // modifying an object
				/*
				 * requet[1] : title
				 * requet[2] : category
				 * requet[3] : description
				 * requet[4] : image
				 * requet[5] : country
				 * requet[6] : contact
				 * requet[7] : lifeTime
				 * requet[8] : type
				 */
				// idem que le commentaire du dessus !!!
				managerB.updateItem(requet[1], requet[2], requet[3], requet[4]+":"+requet[5], requet[6], requet[7], requet[8], requet[9]);
			
			try {
				session.getBasicRemote().sendText("update_objet:");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

			//case load all information of usercurrent
		case "/load_use": // C'est pareil, j'ai fait une fonction propre dans le manager Bridge !
			 nick = Application.getInstance().getManager().getCurrentUser().getNick();
			 name = Application.getInstance().getManager().getCurrentUser().getName();
			String firstname = Application.getInstance().getManager().getCurrentUser().getFirstName();
			 email = Application.getInstance().getManager().getCurrentUser().getEmail();
			String numbertel = Application.getInstance().getManager().getCurrentUser().getPhone();
			try {
				session.getBasicRemote().sendText("load_user:"+nick+":"+name+":"+firstname+":"+email+":"+numbertel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			//case load item of usercurrent
		case "/load_item":
			// Même chose il y a une fonction dans le ManagerBridge... en plus, j'avais modifié pour que ce soit propre mais tu as décidé d'annuler l'intégralité de mon travail !!!!!!!!!!!!!!!!!!!!
			 manager = Application.getInstance().getManager();
			ArrayList<Item> it = manager.getUserItems(manager.getCurrentUser().getKeys().getPublicKey().toString(16));
			if(!it.isEmpty()){
				for (int i = 0; i < it.size(); i++) {
					try {
						session.getBasicRemote().sendText("load_item:"+it.get(i).getTitle()+":"+it.get(i).getCountry()+":"+it.get(i).getDescription());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			break;

			
		case "/zoom_item": //Return current object
			// ENCORE UNE FOIS, j'avais fait ça proprement en utilisant ManagerBridge !!!!
			Manager manager1 = Application.getInstance().getManager();
			Item item_search = manager1.getItemCurrentUser(requet[1]);

			Long enddingDate = item_search.getLifeTime() + item_search.getDate();
			try {
				session.getBasicRemote().sendText("zoom_item_result:"+
						item_search.getTitle()+":"+item_search.getCategory().getStringChoice()+":"+
						item_search.getCountry()+":"+DateConverter.getString(enddingDate)+":"+item_search.getType()+":"+item_search.getDescription()+":"+item_search.getImage()
						+":"+item_search.getDate()+":"+item_search.getContact());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

			/*
			 * 	Return current object
			 * 	requet[1] : title
			 */
			
		case "/remove_item": //  remove objetc

		
			managerB.removeItem(requet[1]);


			break;


		case "/update_compte_user": // Update current user token
			nick = requet[1];
			name = requet[2];
			firstName = requet[3];
			email = requet[4];
			phone = requet[5];
			newPassword = requet[6];
			oldPassword = requet[7];

			System.out.println(nick+" "+name+" "+firstName+" "+email+" "+phone+" "+newPassword+" "+oldPassword+" ");
			
			if(managerB.updateAccount(nick, oldPassword, newPassword, name, firstName, email, phone)){
				System.out.println("TRUEEE UPDATE");
				try {
					session.getBasicRemote().sendText("load_update_user:");
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}else{
				System.out.println("Update False");
				try {
					session.getBasicRemote().sendText("update_user_false:");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			break;
		/*case "/new_objet_add" : // Add item token
			title = 		requet[1];
			category = 		requet[2];
			description = 	requet[3];
			image = 		requet[4]+":"+requet[5];
			country = 		requet[6];
			contact = 		requet[7];
			lifeTime = 		requet[8];
			type = 			requet[9];
			managerB.addItem(title, category, description, image, country, contact, lifeTime, type);
			break;
		 */
		/*case "/new_objet_update" : // Update Item token
			title = 		requet[1];
			category = 		requet[2];
			description = 	requet[3];
			image = 		requet[4]+":"+requet[5];
			country = 		requet[6];
			contact = 		requet[7];
			lifeTime = 		requet[8];
			type = 			requet[9];
			managerB.updateItem(title, category, description, image, country, contact, lifeTime, type);
			try {
				session.getBasicRemote().sendText("update_objet:"); // ????
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		*/
		///////////////////////////////////////////////// REDIRECTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		/*case "/search": // "Search" redirection token
			try {
				session.getBasicRemote().sendText("Search.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newindex": // "Index" redirection token
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newobjet": // "New Object" redirection token
			try {
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/newchat": // "Chat" redirection token
			try {
				session.getBasicRemote().sendText("Message.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/contrat": // "Deal" redirection token
			try {
				session.getBasicRemote().sendText("Contrat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/user_compte": // "Account" redirection token
			try {
				session.getBasicRemote().sendText("User_compte.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			*/
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
		/*case "/load_use": // Load the current user and return to Javascript
=======
		case "/load_use": // Load the current user and return to Javascript
>>>>>>> branch 'master' of https://github.com/pja35/p2pEngine.git
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
		case "/remove_item":  // Remove item token
			itemKey = requet[1];
			managerB.removeItem(itemKey);
			break;
			*/
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
			String result=sendTextToNick(msg, nick)?"sendt":"sendf";
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
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session){
		System.out.println("Session has ended");
	}

	/**
	 * Send a message to a nickname
	 * Used when unknown publicKey but have nickname
	 * @param message - String message
	 * @param nick - String receiver's nickname
	 * @deprecated
	 * TODO A SUPPRIMER !!!!! J'avais fait ça proprement et j'avais supprimer cette fonction, tu m'expliques pourquoi elle est revenu ???????????????,??
	 */
	private boolean sendTextToNick(String message, String nick){
		boolean sendOnTime = false;
		Search<User> search = new Search<User>(Application.getInstance().getNetwork().getGroup("users").getDiscoveryService(), "nick", true);
		search.search(nick, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<Search<User>.Result> results = search.getResultsWithPeerID();
		AsymKeysImpl to;
		AsymKeysImpl from = Application.getInstance().getManager().getCurrentUser().getKeys();
		ArrayList<String> keyUsed = new ArrayList<String>();
		Message msg = null;
		for (Search<User>.Result r : results) {
			if(!r.result.checkSignature(r.result.getKeys())){
				results.remove(r);
			}else{
				to = r.result.getKeys();
				if(!keyUsed.contains(to.getPublicKey().toString(16))){
					msg = new Message(to, from, message);
					msg.sign(from);
					keyUsed.add(to.getPublicKey().toString(16));
				}
				sendOnTime |= Application.getInstance().getCommunication().sendMessage(msg.toString(), "ChatService", r.peerID);
				Application.getInstance().getManager().addMessage(msg);
			}
		}
		return sendOnTime;
	}

	/**
	 * Send a message to a publicKey
	 * Used when known publicKey
	 * @param message - String message
	 * @param publicKey - String(hexa) receiver's publicKey  
	 */
	private boolean sendTextToPublicKey(String message, String publicKey){
		boolean sendOnTime = false;
		Search<User> search = new Search<User>(Application.getInstance().getNetwork().getGroup("users").getDiscoveryService(), "publicKey", true);
		search.search(publicKey, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<Search<User>.Result> results = search.getResultsWithPeerID();
		ArrayList<PeerID> ids = new ArrayList<PeerID>();
		AsymKeysImpl to = null;
		AsymKeysImpl from = Application.getInstance().getManager().getCurrentUser().getKeys();
		Message msg = null;
		for (Search<User>.Result r : results) {
			if(!r.result.checkSignature(r.result.getKeys())){
				results.remove(r);
			}else{
				ids.add(r.peerID);
				to = r.result.getKeys();
			}
		}
		if(to != null){
			msg = new Message(to, from, message);
			msg.sign(from);
			sendOnTime |= Application.getInstance().getCommunication().sendMessage(msg.toString(), "ChatService", (PeerID[]) ids.toArray());
			Application.getInstance().getManager().addMessage(msg);
		}else{
			System.err.println(EchoServer.class.getClass().getName()+" : sendTextPublicKey Account not found");
		}

		return sendOnTime;
	}
}
