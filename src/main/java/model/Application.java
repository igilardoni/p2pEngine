package model;

import model.manager.Manager;
import model.network.Network;
import model.network.communication.Communication;
import model.network.communication.service.ChatService;
import model.network.communication.service.TransmitAccountService;

/**
 * The main class of the software. This class can be instancied only once. (singleton)
 * Instantiate this class will launch the P2P network.
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance = null; //the current instance of this class.
	
	private EmbeddedRunner server;
	private Network network;
	private Manager manager;
	private Communication com;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unchecked")
	public Application() {
		if(instance != null) {
			try {
				throw new Exception("this class can be instancied only once");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		startNetwork();
		startCommunication();
		manager = new Manager();
		com.getService(TransmitAccountService.class.getName()).addListener(manager);
		startLocalServer();
		
		instance = this;
	}
	
	/**
	 * Start the communication package.
	 */
	private void startCommunication() {
		try {
			this.com = new Communication(network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.addService(new ChatService());
		com.addService(new TransmitAccountService());
	}
	
	/**
	 * Start the network and keep instance reference.
	 */
	private void startNetwork() {
		network = new Network(9706, ".peerFolder", "peer name");
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
	
	public void stopServer() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * We start the app here !
	 * @param args
	 */
	public static void main(String[] args) {
		new Application();
	}
}
