package model.communications;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import model.Peer;
import model.User;
import model.UsersManagement;
import model.search.RemoteRessource;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;

/**
 * Gère l'envoit et la reception de message. Les transmet aux services correspondants.
 * @author Prudhomme Julien
 *
 */
public class Chatter implements PipeMsgListener{

	public static final String toServiceTag = "ToService";
	private static final String SERVICE_NAME = "Chatter";
	private Peer peer;
	private HashMap<String, MessageService> services = new HashMap<String, MessageService>();
	private PipeAdvertisement pipeAdv;
	private HashMap<String, OutputPipe> usersPeerID = new HashMap<String, OutputPipe>();
	private String pipeName;
	
	/**
	 * Creer le chatter. Tout les messages sont recu et envoyer a partir d'ici.
	 * @param peer l'instance de Peer
	 */
	public Chatter(Peer peer) {
		this.peer = peer;
		
		pipeName = SERVICE_NAME + peer.getPeerId();
		pipeName = Integer.toString(pipeName.hashCode());
		initPipeAdv();
	}
	
	/**
	 * Génère l'advertisement qui décrit le pipe qui sert a communiquer
	 */
	private void initPipeAdv() {
		pipeAdv = Peer.get_PipeAdvertisement(IDFactory.newPipeID(peer.getPeerGroupID(), SERVICE_NAME.getBytes()), false);
		
		try {
			peer.getPipeService().createInputPipe(pipeAdv, this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * On reçoit un msg: on le transmet au service associé.
	 */
	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		// TODO Auto-generated method stub
		Message msg = event.getMessage();
		try {
			byte[] toServiceBytes = msg.getMessageElement(toServiceTag).getBytes(true);
			String toService = new String(toServiceBytes);
			if(services.containsKey(toService)) {
				services.get(toService).putMessage(msg);
			} else {
				System.out.println("service " + toService + " inconnue");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * On ajoute un service de reception.
	 * @param service
	 */
	public void addService(MessageService service) {
		services.put(service.getServiceName(), service);
	}
	
	/**
	 * Envoit un message. Il est plus efficace de se servir d'une méthode contenu dans les services pour envoyer
	 * les messages leurs correspondants.
	 * @param userName
	 * @param msg
	 * @return
	 */
	public boolean sendToUser(String userName, Message msg) {
		OutputPipe sender = null;
		if(this.usersPeerID.containsKey(userName)) { //on recupere le pipe de communication si il existe
			sender = this.usersPeerID.get(userName);
		}
		else { //sinon on le creer et on le sauvegarde
			RemoteRessource<User> res = new RemoteRessource<User>(peer.getDiscovery(), "login", 5000);
			User u = res.getRemoteRessource(userName);
			if(u != null) { //on a trouvé l'utilisateur
				System.out.println(u.getPeerID());
				HashSet<PeerID> to = new HashSet<PeerID>();
				try {
					to.add((PeerID)IDFactory.fromURI(new URI(u.getPeerID())));
					sender = peer.getPipeService().createOutputPipe(pipeAdv, to, 10000);
					this.usersPeerID.put(userName, sender);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			else return false;
		}
		try {
			sender.send(msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
