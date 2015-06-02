package controller;

import java.io.IOException;
import java.math.BigInteger;
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
import model.data.item.Item.TYPE;
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
		
		String[] requet = message.split(":");
		
		switch (requet[0]) {
		case "/index":
			if(managerB.login(requet[2], requet[1])){
				try {
					session.getBasicRemote().sendText("index.html:");
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
			break;
		case "/register":
			
			managerB.registration(requet[1],requet[2], requet[3], requet[4], requet[5], requet[6]);
			try {
				session.getBasicRemote().sendText("Se_connecter.html#tologin:");
			} catch (IOException e) {
				e.printStackTrace();

			}
			break;

		//Just for redirection
		case "/newobjet":
			try {
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "/search":
			try {
				session.getBasicRemote().sendText("Search.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/new_objet_add" :
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
			managerB.addItem(requet[1], requet[2], requet[3], requet[4]+":"+requet[5], requet[6],requet[7], requet[8], requet[9]);
			break;

			case "/new_objet_update" :
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
			managerB.updateItem(requet[1], requet[2], requet[3], requet[4]+":"+requet[5], requet[6], requet[7], requet[8], requet[9]);
			
			try {
				session.getBasicRemote().sendText("update_objet:");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;
			
			//Just for redirection
			case "/newindex":
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

			//Just for redirection
			case "/newchat":
			try {
				session.getBasicRemote().sendText("Message.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

			//Just for redirection
		case "/contrat":
			try {
				session.getBasicRemote().sendText("Contrat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

			//Just for redirection
		case "/user_compte":
			try {
				session.getBasicRemote().sendText("User_compte.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

			//case load all information of usercurrent
		case "/load_use":
			String nick = Application.getInstance().getManager().getCurrentUser().getNick();
			String name = Application.getInstance().getManager().getCurrentUser().getName();
			String firstname = Application.getInstance().getManager().getCurrentUser().getFirstName();
			String email = Application.getInstance().getManager().getCurrentUser().getEmail();
			String numbertel = Application.getInstance().getManager().getCurrentUser().getPhone();
			try {
				session.getBasicRemote().sendText("load_user:"+nick+":"+name+":"+firstname+":"+email+":"+numbertel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			//case load item of user curren
		case "/load_item":

			Manager manager = Application.getInstance().getManager();
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

			//Return current object
		case "/zoom_item":

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
			
		case "/remove_item":

		
			managerB.removeItem(requet[1]);


			break;

		case "/update_compte_user" :
		
			if(managerB.updateAccount(requet[1], requet[7], requet[5], requet[2], requet[3], requet[4], requet[6])){
				try {
					session.getBasicRemote().sendText("load_update_user:");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}else{
				try {
					session.getBasicRemote().sendText("update_user_false:");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			break;

		case "/search_itme":
			System.out.println("JE SUIS LA!");
			SearchItemController sc = new SearchItemController();
			sc.addListener(new SearchListener<Item>() {

				@Override
				public void searchEvent(Item event) {
					try {
						System.out.println("ok!!!!!");
						session.getBasicRemote().sendText("result_search_item:"+event.getTitle()+":"+event.getDescription()+":"+event.getCountry());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			});
			sc.startSearch(requet[1]);


			break;

		case "/log_out":

			Application.getInstance().getManager().logout();


			try {
				session.getBasicRemote().sendText("log_index:");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}

			break;
			
		case "/send_message" :
			String result="";
			if(sendTextToNick(requet[1],requet[2]))
				result="sendt";
			else
				result="sendf";
			
			try {
				session.getBasicRemote().sendText("result_sendMessage:"+result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}
			
			break;


		default:
			break;
		}

	}
	
	
	/* Voila ca c'est un style de controlleur stu veux .. */
	private static void addItem(String title, String category, String description, String image, String country, String contact, long lifeTime, String type ){
		User owner = Application.getInstance().getManager().getCurrentUser();
		Category c = new Category(category);
		Item.TYPE t;
		//Long l = Long.parseLong(lifeTime);
		switch(type.toUpperCase()){
		case "WISH":
			t = TYPE.WISH;
			break;
		case "PROPOSAL":
			t = TYPE.PROPOSAL;
			break;
		default:
			t = TYPE.WISH;
		}
		Item item = new Item(owner, title, c, description, image, country, contact, 0, lifeTime, t);
		item.sign(owner.getKeys());
		Application.getInstance().getManager().addItem(item, true);
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

	//Verifying user account
	private static boolean login(String login, String password){
		return Application.getInstance().getManager().login(login, password);

	}

	/**
	 * Add NEW User to current Manager
	 * @param nick
	 * @param password
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 */
	private static void addUser(String nick,String password, String name, String firstName, String email, String phone){
		User user = new User(nick, password, name, firstName, email, phone);
		Application.getInstance().getManager().registration(user);
	}

	/**
	 * Send a message to a nickname
	 * Used when unknown publicKey but have nickname
	 * @param message - String message
	 * @param nick - String receiver's nickname
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


	public static void main(String[] args){

	}

}
