package controller;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.Application;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
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
    	String[] contents = message.split(":");
    	switch (contents[0]) {
		case "/index":
			if(Verifying(contents[1], contents[2])){
				System.out.println("je suis ici");
	    		try {
					session.getBasicRemote().sendText("index.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	    	}
			break;
		case "/register":
			if(add_new_user(contents[1],contents[2], contents[3], contents[4], contents[5], contents[6])){
				System.out.println("un nouveau utilisateur inscrit");
				try {
					session.getBasicRemote().sendText("Se_connecter.html#tologin");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			break;
			
		case "/new_objet":
			System.out.println("HAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAY OBJET");
			break;
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
    	//System.out.println(login+":"+password);
    	if(login.equals("toto") && password.equals("toto"))
    		return true;
    	return false;
    }
    
    //add new user
    public boolean add_new_user(String nick,String password, String name, String firstName, String email, String phone){
    	//call model
    	return true;
    }
    
    public static void main(String[] args){
    	
    }
    
}