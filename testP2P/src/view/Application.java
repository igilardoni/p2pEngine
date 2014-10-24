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

import model.UsersManagement;

/**
 * Class "main" du programme
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance;
	private UsersManagement users;
	private UsersManagement remote_users;
	private Window window;
	private static final String USERS_DIR = "users.data";
	private static final String LOCALE_DIR = "locale.data";
	
	
	public Application() {
		loadUsers();
		loadLocale();
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		Application.setInstance(app);
		app.lauch();
	}
	
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
	
	public void updateUI() {
		window.revalidate();
		window.repaint();
	}
	
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveUsers() {
		ObjectOutputStream stream = null;
		try {
			final FileOutputStream fichier = new FileOutputStream(Application.USERS_DIR);
			stream = new ObjectOutputStream(fichier);
			stream.writeObject(this.getUsers());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	private WindowListener getWindowListener() {
		return new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				updateUI();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			/**
			 * On sauvegarde tout ce qui a été fait avant de fermer le programme...
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				saveUsers();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			/**
			 * On met a jour la vue dès l'ouverture pour prendre en compte les données chargées.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				updateUI();
				
			}
			
		};
	}
	
	public static void saveLocale(Locale l) {
		ObjectOutputStream stream = null;
		try {
			final FileOutputStream fichier = new FileOutputStream(Application.LOCALE_DIR);
			stream = new ObjectOutputStream(fichier);
			stream.writeObject(l);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void loadLocale() {
		ObjectInputStream stream = null;
		try {
			final FileInputStream fichier = new FileInputStream(Application.LOCALE_DIR);
			stream = new ObjectInputStream(fichier);
			Locale.setDefault((Locale) stream.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void restartUI() {
		Messages.updateLanguage();
		Application.getInstance().window.dispose();
		Application.getInstance().lauch();
	}
	
}
