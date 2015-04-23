package model.network.communication.service;

import java.math.BigInteger;

import util.Hasher;
import util.secure.AsymKeysImpl;
import model.user.User;
import net.jxta.endpoint.Message;

public class TransmitAccountService extends Service {

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

	private boolean checkMessageFormat(Message m) {
		return 
				m.getMessageElement("to") != null &
				m.getMessageElement("userSignR") != null &
				m.getMessageElement("userSignS") != null &
				m.getMessageElement("nick") != null &
				m.getMessageElement("publicKey") != null &
				m.getMessageElement("p") != null &
				m.getMessageElement("g") != null &
				m.getMessageElement("privateKey") != null &
				m.getMessageElement("name") != null &
				m.getMessageElement("firstName") != null &
				m.getMessageElement("hashPwd") != null &
				m.getMessageElement("email") != null &
				m.getMessageElement("phone") != null;
	}
	
	private boolean checkValidation(User u, BigInteger s, BigInteger r){
		return true;
	}
	
	@Override
	public boolean handleMessage(Message m) {
		if(!checkMessageFormat(m)) return false;
		
		String to = new String(m.getMessageElement("to").getBytes(true));
		BigInteger userSignR = new BigInteger(m.getMessageElement("userSignR").getBytes(true));
		BigInteger userSignS = new BigInteger(m.getMessageElement("userSignS").getBytes(true));
		
		BigInteger publicKey = new BigInteger(m.getMessageElement("publicKey").getBytes(true));
		BigInteger privateKey = new BigInteger(m.getMessageElement("privateKey").getBytes(true));
		BigInteger p = new BigInteger(m.getMessageElement("p").getBytes(true));
		BigInteger g = new BigInteger(m.getMessageElement("g").getBytes(true));
		AsymKeysImpl key = new AsymKeysImpl();
		key.setG(g);
		key.setP(p);
		key.setPublicKey(publicKey);
		key.setPrivateKey(privateKey);
		
		String nick = new String(m.getMessageElement("nick").getBytes(true));
		String name = new String(m.getMessageElement("name").getBytes(true));
		String firstName = new String(m.getMessageElement("firstName").getBytes(true));
		String hashPwd = new String(m.getMessageElement("hashPwd").getBytes(true));
		String email = new String(m.getMessageElement("email").getBytes(true));
		String phone = new String(m.getMessageElement("phone").getBytes(true));
		
		User user = new User(nick, hashPwd, name, firstName, email, phone, key);
		
		if(checkValidation(user, userSignS, userSignR)){
			// TODO process in case of wrong message !
			System.err.println("THIS MESSAGE IS SHIT !");
		}
		
		// TODO I don't know who have to get this fuckin user !!!!
		
		return true;
	}

}
