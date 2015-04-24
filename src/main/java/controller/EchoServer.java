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
    	System.out.println(contents[0]+" "+contents[1]);
    	if(verif(contents[0], contents[1])){
    		try {
				session.getBasicRemote().sendText("connecter.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
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
    
    
    public boolean verif(String login, String password){
    	if(login.equals("toto") && password.equals("toto"))
    		return true;
    	return false;
    }
    
    public static void main(String[] args){
    	
    }
    
}