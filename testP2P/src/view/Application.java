package view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.AdvertisementInstaciator;
import model.Peer;
import model.UsersManagement;
import model.communications.AccordService;
import model.communications.ChatService;
import model.communications.Chatter;
import model.communications.FriendRequestService;
import net.jxta.exception.PeerGroupException;

/**
 * Class "main" du programme
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance;
	private UsersManagement users;
	private Window window;
	private static final String USERS_DIR = "users.data";
	private static final String LOCALE_DIR = "locale.data";
	private Peer peer;
	private Chatter chatter;
	private ChatService chatService;
	private FriendRequestService friendRequest;
	private AccordService accords;
	
	
	public Application() {
		instance = this;
		loadUsers();
		loadLocale();
		startNetwork();
		
		chatter = new Chatter(peer);
		
		chatService = new ChatService(users);
		chatter.addService(chatService);
		
		friendRequest = new FriendRequestService(users);
		chatter.addService(friendRequest);
		
		accords = new AccordService(users);
		chatter.addService(accords);
		
		users.publishUsers(peer.getDiscovery());
	}
	
	public Peer getPeer() {
		return peer;
	}
	
	public FriendRequestService getFriendRequestService() {
		return friendRequest;
	}
	
	public AccordService getAccordService() {
		return accords;
	}
	
	public ChatService getChatService() {
		return chatService;
	}
	
	public Chatter getChatter() {
		return chatter;
	}
	
	private void startNetwork() {
		Logger.getLogger("net.jxta").setLevel(Level.OFF);
		AdvertisementInstaciator.RegisterAllAdv();
		int port = 9000 + new Random().nextInt(100);
		peer = new Peer(port);
		try {
			peer.start();
			peer.fetch_advertisements();
		} catch (PeerGroupException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		Application.setInstance(app);
		app.lauch();
	}
	
	/**
	 * Lance le programme, cree une nouvelle fenetre principale window
	 */
	private void lauch() {
		window = new Window();
		window.setVisible(true);
		window.addWindowListener(this.getWindowListener());
	}

	public static Application getInstance() {
		return instance;
	}
	
	public static void setInstance(Application app) {
		instance = app;
	}
	
	public UsersManagement getUsers() {
		return this.users;
	}
	
	/**
	 * Rafraichi la fenetre principale de l'application
	 */
	public void updateUI() {
		window.revalidate();
		window.repaint();
	}
	
	/**
	 * Charge la listes des utilisateurs enregistres sur la machine dans users.data
	 */
	public void loadUsers() {
		ObjectInputStream stream = null;
		try {
			final FileInputStream fichier = new FileInputStream(Application.USERS_DIR);
			stream = new ObjectInputStream(fichier);
			this.users = (UsersManagement) stream.readObject();
		} catch (Exception e) {
			this.users = new UsersManagement();
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Sauvegarde la listes des utilisateurs sur la machine dans users.data
	 */
	public void saveUsers() {
		ObjectOutputStream stream = null;
		try {
			final FileOutputStream fichier = new FileOutputStream(Application.USERS_DIR);
			stream = new ObjectOutputStream(fichier);
			stream.writeObject(this.getUsers());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	private WindowListener getWindowListener() {
		return new WindowListener() {

			public void windowActivated(WindowEvent e) {
				updateUI();
			}

			public void windowClosed(WindowEvent e) {}
			
			/**
			 * On sauvegarde tout ce qui a ete fait avant de fermer le programme...
			 */
			public void windowClosing(WindowEvent e) {
				saveUsers();
			}

			public void windowDeactivated(WindowEvent e) {}


			public void windowDeiconified(WindowEvent e) {}

			public void windowIconified(WindowEvent e) {}
			
			/**
			 * On met a jour la vue des l'ouverture pour prendre en compte les donnees chargees.
			 */
			public void windowOpened(WindowEvent e) {
				updateUI();	
			}
			
		};
	}
	
	/**
	 * Sauvegarde les donnees locales dans locale.data
	 */
	public static void saveLocale(Locale l) {
		ObjectOutputStream stream = null;
		try {
			final FileOutputStream fichier = new FileOutputStream(Application.LOCALE_DIR);
			stream = new ObjectOutputStream(fichier);
			stream.writeObject(l);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Charge les donnees locales depuis locale.data
	 */
	public void loadLocale() {
		ObjectInputStream stream = null;
		try {
			final FileInputStream fichier = new FileInputStream(Application.LOCALE_DIR);
			stream = new ObjectInputStream(fichier);
			Locale.setDefault((Locale) stream.readObject());
		} catch (Exception e) {
			//pas de fichier, on laisse la langue par défaut
			//e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Ouvre une nouvelle conversation
	 */
	public void openConvers(String user) {
		window.getMessagePanel().setVisible(true);
		window.getMessagePanel().setNewConvers(user);
	
	}
	
	
	/**
	 * Relance la fenetre principale de l'application
	 */
	public static void restartUI() {
		Langues.updateLanguage();
		Application.getInstance().window.dispose();
		Application.getInstance().lauch();
	}
}
