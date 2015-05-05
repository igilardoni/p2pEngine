package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.Application;
import model.item.Category;
import model.item.Item;
import model.item.Item.TYPE;
import model.manager.Manager;
import model.user.User;
 
/* TODO MEDHI CREE D'AUTRES CLASSES TU VA PAS TOUT FOUTRE ICI 
 * Regarde le controller de l'ancien projet.
 * */

/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo .
 */
@ServerEndpoint("/serv") 
public class EchoServer {
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
    public void onMessage(String message, Session session){
    	System.out.println(message+" : heey heeeeey");
    	String[] contents = message.split(":");
    	switch (contents[0]) {
		case "/index":
			if(Verifying(contents[2], contents[1])){

				try {
	    			session.getBasicRemote().sendText("index.html:");
				} catch (IOException e) {
					e.printStackTrace();
				}		
	    	}
			break;
		case "/register":
			add_new_user(contents[1],contents[2], contents[3], contents[4], contents[5], contents[6]);
				try {
					session.getBasicRemote().sendText("Se_connecter.html#tologin:");
				} catch (IOException e) {
					e.printStackTrace();
				
			}
			break;
			
		case "/newobjet":
			try {
				//System.out.println(contents[1]+" "+contents[2]+" "+contents[3]+" "+contents[4]+" "+contents[5]+" "+contents[6]);
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "/search":
			try {
				System.out.println("RECHEEEEEEERCH");
				session.getBasicRemote().sendText("Search.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
			
			
			
			
			 
		case "/new_objet_add" :
			User owner = Application.getInstance().getManager().getCurrentUser();
			
			Category category = new Category("");
			
			Item item = new Item(owner, contents[1], category, contents[6], contents[3], "country","contact", 0, 0, TYPE.WISH);
			item.sign(owner.getKeys());
			
			Application.getInstance().getManager().addItem(item);
			Manager a = Application.getInstance().getManager();
			ArrayList<Item> items = a.getUserItems(a.getCurrentUser().getKeys().getPublicKey().toString(16));

			break;
	
		case "/newindex":
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "/newchat":
			try {
				session.getBasicRemote().sendText("chat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
			
		case "/contrat":
			try {
				session.getBasicRemote().sendText("Contrat.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
			
		case "/load_use":
			System.out.println("JE SUIS ICI UN NEW LOAD");
			String nick = Application.getInstance().getManager().getCurrentUser().getNick();
			try {
    			session.getBasicRemote().sendText("load_user:"+nick);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "/load_item":
	
			Manager manager = Application.getInstance().getManager();
			ArrayList<Item> it = manager.getUserItems(manager.getCurrentUser().getKeys().getPublicKey().toString(16));
			if(it.size() !=0){
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
			
		case "/zoom_item":
			Manager zoom_item = Application.getInstance().getManager();
			Item item_search = zoom_item.getItemCurrentUser(contents[1]);
			try {
				session.getBasicRemote().sendText("zoom_item_result:"+
						item_search.getTitle()+":"+item_search.getCategory()+":"+
						item_search.getCountry());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
			
		case "/remove_item":
			Manager remove_item = Application.getInstance().getManager();
			Item item_remove = remove_item.getItemCurrentUser(contents[1]);
			remove_item.removeItem(item_remove);
			
			
		default:
			break;
		}
    	
    	
       
    }
   /* @OnMessage
    public void echo(String message, Session session) throws IOException {
    	String[] contents = message.split(":");
    	System.out.println(contents[0]+" "+contents[1]);
    	if(verif(contents[0], contents[1])){
    		session.getBasicRemote().sendText("Se_connecter.html");		
    	}
    	
       
    }
   */
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
    public boolean Verifying(String login, String password){
    	return Application.getInstance().getManager().login(login, password);
    	
    }
    
    //add new user
    public void add_new_user(String nick,String password, String name, String firstName, String email, String phone){
    	User user = new User(nick, password, name, firstName, email, phone);
    	user.sign(user.getKeys());
    	Application.getInstance().getManager().addUser(user);
   }
    
    
    
   
    
    
    public static void main(String[] args){
    	
    }
    
}
