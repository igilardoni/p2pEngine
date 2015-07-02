package controller;

import java.util.ArrayList;

import model.Application;
import model.data.user.UserMessage;
import model.data.user.User;
import model.network.search.Search;
import net.jxta.peer.PeerID;
import util.VARIABLES;
import util.secure.AsymKeysImpl;
import view.EchoServer;

public class MessageSender {
	/**
	 * Send a message to a nickname
	 * Used when unknown publicKey but have nickname
	 * @param message - String message
	 * @param nick - String receiver's nickname
	 */
	public static boolean sendMessageToNick(String message, String nick){
		boolean sendOneTime = false;
		Search<User> search = new Search<User>(Application.getInstance().getNetwork().getGroup("users").getDiscoveryService(), "nick", true);
		search.search(nick, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<Search<User>.Result> results = search.getResultsWithPeerID();
		AsymKeysImpl to;
		AsymKeysImpl from = Application.getInstance().getManager().getUsers().getCurrentUser().getKeys();
		ArrayList<String> keyUsed = new ArrayList<String>();
		UserMessage msg = null;
		for (Search<User>.Result r : results) {
			if(!r.result.checkSignature(r.result.getKeys())){
				results.remove(r);
			}else{
				to = r.result.getKeys();
				if(!keyUsed.contains(to.getPublicKey().toString(16))){
					msg = new UserMessage(to, from, message);
					msg.sign(from);
					keyUsed.add(to.getPublicKey().toString(16));
				}
				sendOneTime |= Application.getInstance().getCommunication().sendMessage(msg.toString(), "ChatService", r.peerID);
				
				// TODO SEND MESSAGE ! Application.getInstance().getManager().addMessage(msg);
			}
		}
		// TODO Throw Exception for give fail reason
		return sendOneTime;
	}
	
	/**
	 * Send a message to a publicKey
	 * Used when known publicKey
	 * @param message - String message
	 * @param publicKey - String(hexa) receiver's publicKey  
	 * @throws Exception 
	 */
	public static boolean sendMessageToPublicKey(String message, String publicKey) throws Exception{
		// TODO Delete all System.out
		boolean sendOneTime = false;
		Search<User> search = new Search<User>(Application.getInstance().getNetwork().getGroup("users").getDiscoveryService(), "publicKey", true);
		search.search(publicKey, VARIABLES.CheckTimeAccount, VARIABLES.ReplicationsAccount);
		ArrayList<Search<User>.Result> results = search.getResultsWithPeerID();
		ArrayList<PeerID> ids = new ArrayList<PeerID>();
		AsymKeysImpl to = null;
		AsymKeysImpl from = Application.getInstance().getManager().getUsers().getCurrentUser().getKeys();
		UserMessage msg = null;
		for (Search<User>.Result r : results) {
			if(!r.result.checkSignature(r.result.getKeys())){
				results.remove(r);
			}else{
				ids.add(r.peerID);
				to = r.result.getKeys();
			}
		}
		System.out.println("to : "+to);
		if(to != null){
			msg = new UserMessage(to, from, message);
			msg.sign(from);
			sendOneTime |= Application.getInstance().getCommunication().sendMessage(msg.toString(), "ChatService", (PeerID[]) ids.toArray());
			//TODO SEND MESSAGE Application.getInstance().getManager().addMessage(msg);
		}else{
			System.err.println(EchoServer.class.getClass().getName()+".sendTextPublicKey : Account not found");
			throw new Exception("Account not found");
		}
		System.out.println(sendOneTime);
		return sendOneTime;
	}

	/**
	 * return an array list with all message from publicKey to currentUser
	 */
	public static ArrayList<UserMessage> getMessagesfrom(String publicKey) {
		return Application.getInstance().getManager().getCurrentUserConversations().getConversation(publicKey);
	}
}
