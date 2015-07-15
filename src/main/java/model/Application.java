package model;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.logging.Level;

import model.advertisement.AdvertisementInstaciator;
import model.data.contrat.Contrat;
import model.data.favorites.Favorites;
import model.data.item.Item;
import model.data.manager.Manager;
import model.data.manager.resiliance.ContratsResiliance;
import model.data.manager.resiliance.FavoritesResiliance;
import model.data.manager.resiliance.ItemResiliance;
import model.data.manager.resiliance.MessageResiliance;
import model.data.manager.resiliance.SharingManager;
import model.data.manager.resiliance.UserResiliance;
import model.data.user.User;
import model.data.user.UserMessage;
import model.network.Network;
import model.network.communication.Communication;
import model.network.communication.service.MessageService;
import model.network.communication.service.InstanceSender.ClassSenderService;
import model.network.communication.service.update.UpdateService;
import model.network.search.Search;
import util.VARIABLES;

/**
 * The main class of the software. This class can be instancied only once. (singleton)
 * Instantiate this class will launch the P2P network.
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance = null; //the current instance of this class.
	
	private EmbeddedRunner server = null;
	private Network network;
	private Manager manager;
	private Communication com;
	private SharingManager sharingManager;
	
	/**
	 * Launch the application.
	 */
	public Application(boolean startLocalServer) {
		if(instance != null) {
			throw new RuntimeException("this class can be instancied only once");
		}
		AdvertisementInstaciator.RegisterAllAdv();
		startNetwork();
		addAllGroups();
		startCommunication();
		manager = new Manager(network);
		manager.recovery(VARIABLES.ManagerFilePath);
		startSharingManager();
		
		if(startLocalServer)
			startLocalServer();	
		
		instance = this;
	}
	
	private void addAllGroups() {
		network.addGroup(Item.class.getSimpleName(), true);
		network.addGroup(User.class.getSimpleName(), true);
		network.addGroup(Contrat.class.getSimpleName(), true);
		network.addGroup(Favorites.class.getSimpleName(), true);
		network.addGroup(UserMessage.class.getSimpleName(), true);
	}
	
	private void startSharingManager() {
		sharingManager = new SharingManager(manager, network, com,  VARIABLES.ReplicationsAccount, VARIABLES.CheckTimeAccount);
		sharingManager.addResiliance(new ContratsResiliance(manager, com));
		sharingManager.addResiliance(new FavoritesResiliance(manager, com));
		sharingManager.addResiliance(new ItemResiliance(manager, com));
		sharingManager.addResiliance(new MessageResiliance(manager, com));
		sharingManager.addResiliance(new UserResiliance(manager, com));
		sharingManager.startSharing();
	}
	
	/**
	 * Start the communication package and adds the services
	 */
	private void startCommunication() {
		try {
			this.com = new Communication(network);
			com.addService(new MessageService());
			com.addService(new UpdateService());
			ClassSenderService.addSenderServices(com);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the network and keep instance reference.
	 * TODO keep reference ?
	 */
	private void startNetwork() {
		Random r = new Random();
		network = new Network(9800, VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		network.setLogger(Level.SEVERE);
		//network.boot("tcp://85.171.121.182:9800");
		network.start();
	}
	
	/**
	 * Launch the localServer for websocket communication between HTML5 UI and the P2P network.
	 */
	private void startLocalServer() {
		try {
			server = new EmbeddedRunner(8080).init().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the unique Application instance
	 * @return the Application instance.
	 */
	public static Application getInstance() {
		if(instance == null) {
			try {
				throw new Exception("there is no instance of this class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Get the current Network.
	 * @return An instanced and started Network.
	 */
	public Network getNetwork() {
		return network;
	}
	
	/**
	 * Get the current Manager.
	 * @return a manager.
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * Get the current Communication
	 * @return
	 */
	public Communication getCommunication(){
		return com;
	}
	
	public void stopServer() {
		if(server == null) return;
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Properly close the app : closing network & server, and saving datas.
	 */
	public void close() {
		System.out.println("closing ...");
		stopServer();
		network.stop();
		sharingManager.stopSharing();
		
		File f = new File(".data");
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);
			fw.write(manager.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("bye !");
		
	}
	
	/**
	 * We start the app here !
	 * @param args
	 */
	public static void main(String[] args) {
		new Application(true);
		Network n = Application.getInstance().getNetwork();
		if(Desktop.isDesktopSupported()) {
		  try {
			Desktop.getDesktop().browse(new URI("http://localhost:8080/EchoChamber/index.html"));
		  } catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		}
		
	}
}
