package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;

import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JRadioButton;
import javax.swing.table.TableCellRenderer;

import p2pEngine.Demande;
import p2pEngine.DemandesTableModel;
import p2pEngine.Objet;
import p2pEngine.ObjetAdvertisement;
import p2pEngine.Objets;
import p2pEngine.Offre;
import p2pEngine.OffresTableModel;
import p2pEngine.Peer;
import p2pEngine.SearchDemande;
import p2pEngine.SearchOffre;
import p2pEngine.Utilisateur;

public class Application {

	private JFrame frame;
	private JTextField nom;
	private JTextField lieu;
	private JTable tableOffre;
	private JTable tableDemande;
	private JTable tableRecherche;
	private JTextField contre;
	
	private Peer peer;
	private SearchOffre searchOffre;
	private SearchDemande searchDemande;
	private JRadioButton rdbtnOffre;
	private JRadioButton rdbtnDemande;
	
	private Objets localOffres = new Objets();
	private Objets localDemandes = new Objets();
	
	private Utilisateur user;
	
	private static OffresTableModel emptyModel = new OffresTableModel(new Objets(), null);

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws PeerGroupException 
	 */
	public static void main(String[] args) throws PeerGroupException, IOException {
		Application window = new Application();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws PeerGroupException 
	 */
	

	
	public Application() throws PeerGroupException, IOException {
		initialize();
		File userFile = new File("user.seralized");
		if(!userFile.exists()) {
			user = new Utilisateur("Nom", "XX.XX.XX.XX.XX", "nom@host.com");
		}
		else user = Utilisateur.deserialiser("user.seralized");
		Objets offres = new Objets();
		Objets demandes = new Objets();
		Objets recherche = new Objets();
		
		OffresTableModel offreTableModel = new OffresTableModel(offres, this);
		DemandesTableModel demandeTableModel =  new DemandesTableModel(demandes, this);
		
		tableOffre.setModel(offreTableModel);
		tableDemande.setModel(demandeTableModel);
		tableRecherche.setModel(emptyModel);
		
		tableOffre.addMouseListener(new JTableButtonMouseListener(tableOffre));
		tableDemande.addMouseListener(new JTableButtonMouseListener(tableDemande));
		tableRecherche.addMouseListener(new JTableButtonMouseListener(tableRecherche));
		

		
		ObjetAdvertisement.register();
		int port = 9000 + new Random().nextInt(100);
		
		 // JXTA logs beaucoup (trop)
        Logger.getLogger("net.jxta").setLevel(Level.OFF);
		
		peer = new Peer(port);
		searchOffre = new SearchOffre(this);
		searchDemande = new SearchDemande(this);
		peer.start();
		peer.fetch_advertisements();
		//patate.publish(peer.getDiscovery());
		//carotte.publish(peer.getDiscovery());
		//laitue.publish(peer.getDiscovery());
		
	}

	private static class JTableButtonRenderer implements TableCellRenderer {        
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;  
        }
    }
	
	private static class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
            int row    = e.getY()/table.getRowHeight(); //get the row of the button

                    /*Checking the row or column is valid or not*/
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    /*perform a click event*/
                    ((JButton)value).doClick();
                }
            }
        }
    }
	
	public Utilisateur getUser() {
		return user;
	}
	
	public void updateTableButton() {
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		
		tableOffre.getColumn("Action").setCellRenderer(buttonRenderer);
		tableDemande.getColumn("Action").setCellRenderer(buttonRenderer);
		tableRecherche.getColumn("Action").setCellRenderer(buttonRenderer);
	}
	
	public void publishOffre(Offre o) {
		localOffres.add(o);
		o.publish(peer.getDiscovery());
		tableOffre.setModel(new OffresTableModel(localOffres, this));
		updateTableButton();
	}
	
	public void publishDemande(Demande o) {
		localDemandes.add(o);
		o.publish(peer.getDiscovery());
		tableDemande.setModel(new DemandesTableModel(localDemandes, this));
		updateTableButton();
	}
	
	public JFrame getWindow() {
		return frame;
	}
	
	public void updateSearchOffre(Objets objs) {
		tableRecherche.setModel(new OffresTableModel(objs, this));
		updateTableButton();
	}
	
	public void updateSearchDemande(Objets objs) {
		tableRecherche.setModel(new DemandesTableModel(objs, this));
		updateTableButton();
	}
	
	public void ajouterOffre() {
		new AjouterOffreWindow(this).setVisible(true);
	}
	
	public void voirObjet(Objet obj) {
		if(obj instanceof Offre) {
			new VoirOffreWindow(this, obj).setVisible(true);
		}
		else new VoirDemandeWindow(this, obj).setVisible(true);
	}
	
	public void voirInfos() {
		new VoirInformations(this).setVisible(true);
	}
	
	public void ajouterDemande() {
		new AjouterDemandeWindow(this).setVisible(true);
	}
	
	public void voirProfil() {
		new EditerProfil(this).setVisible(true);
	}
	
	public void voirReseau() {
		new EditerReseau(this).setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 714, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnPrfrences = new JMenu("Pr\u00E9f\u00E9rences");
		menuBar.add(mnPrfrences);
		
		JMenuItem mntmMonProfil = new JMenuItem("Mon profil");
		mntmMonProfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirProfil();
			}
		});
		mnPrfrences.add(mntmMonProfil);
		
		JMenuItem mntmConnexionAuRseau = new JMenuItem("Connexion au r\u00E9seau");
		mntmConnexionAuRseau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirReseau();
			}
		});
		mnPrfrences.add(mntmConnexionAuRseau);
		
		JMenu mnAPropos = new JMenu("A propos");
		menuBar.add(mnAPropos);
		
		JMenuItem mntmAide = new JMenuItem("Aide");
		mntmAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("cliqu�");
			}
		});
		mnAPropos.add(mntmAide);
		
		JMenuItem mntmInformations = new JMenuItem("Informations");
		mntmInformations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirInfos();
			}
		});
		mnAPropos.add(mntmInformations);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel Offres = new JPanel();
		tabbedPane.addTab("Offres", null, Offres, null);
		Offres.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOffreBtn = new JPanel();
		Offres.add(panelOffreBtn, BorderLayout.NORTH);
		
		JButton btnAjouterUneOffre = new JButton("Ajouter une offre");
		btnAjouterUneOffre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterOffre();
			}
		});
		panelOffreBtn.add(btnAjouterUneOffre);
		
		JScrollPane scrollPaneOffre = new JScrollPane();
		Offres.add(scrollPaneOffre, BorderLayout.CENTER);
		
		tableOffre = new JTable();
		scrollPaneOffre.setViewportView(tableOffre);
		
		JPanel Demandes = new JPanel();
		tabbedPane.addTab("Demandes", null, Demandes, null);
		Demandes.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDemandebtn = new JPanel();
		Demandes.add(panelDemandebtn, BorderLayout.NORTH);
		
		JButton btnAjouterUneDemande = new JButton("Ajouter une demande");
		btnAjouterUneDemande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterDemande();
			}
		});
		panelDemandebtn.add(btnAjouterUneDemande);
		
		JScrollPane scrollPaneDemande = new JScrollPane();
		Demandes.add(scrollPaneDemande, BorderLayout.CENTER);
		
		tableDemande = new JTable();
		scrollPaneDemande.setViewportView(tableDemande);
		
		JPanel Recherche = new JPanel();
		tabbedPane.addTab("Recherche", null, Recherche, null);
		Recherche.setLayout(new BorderLayout(0, 0));
		
		JPanel panelForm = new JPanel();
		Recherche.add(panelForm, BorderLayout.NORTH);
		panelForm.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("80px"),
				ColumnSpec.decode("5px"),
				ColumnSpec.decode("250px:grow"),
				ColumnSpec.decode("150px"),},
			new RowSpec[] {
				RowSpec.decode("32px"),
				RowSpec.decode("32px"),
				RowSpec.decode("16px"),
				RowSpec.decode("16px"),
				RowSpec.decode("32px"),
				RowSpec.decode("32px"),}));
		
		JLabel lblMotsCls = new JLabel("Nom");
		panelForm.add(lblMotsCls, "1, 1, right, center");
		
		nom = new JTextField();
		panelForm.add(nom, "3, 1, fill, default");
		nom.setColumns(10);
		
		JButton btnRechercher = new JButton("Rechercher");
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("cliqu�");
				tableRecherche.setModel(emptyModel);
				//TODO
				if(rdbtnOffre.isSelected()) {
					peer.getDiscovery().getRemoteAdvertisements(null, DiscoveryService.ADV, 
							"fullName", "Offre:" + nom.getText(), 1, searchOffre);
					System.out.println("Recherche de l'offre " + nom.getText() + "...");
				}
				else {
					peer.getDiscovery().getRemoteAdvertisements(null, DiscoveryService.ADV, 
							"fullName", "Demande:" + nom.getText(), 1, searchDemande);
					System.out.println("Recherche de la demande " + nom.getText() + "...");
				}
				
			}
		});
		panelForm.add(btnRechercher, "4, 1");
		
		JLabel lblTypeDannonce = new JLabel("Type");
		panelForm.add(lblTypeDannonce, "1, 2, right, center");
		
		rdbtnOffre = new JRadioButton("Offre");
		rdbtnOffre.setSelected(true);
		panelForm.add(rdbtnOffre, "3, 2");
		
		rdbtnDemande = new JRadioButton("Demande");
		panelForm.add(rdbtnDemande, "4, 2");
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnOffre);
		bg.add(rdbtnDemande);
		
		
		JSeparator separator = new JSeparator();
		panelForm.add(separator, "3, 3");
		
		JLabel lblFiltres = new JLabel("Filtres");
		panelForm.add(lblFiltres, "1, 4, right, center");
		
		JLabel lblAuteur = new JLabel("Lieu");
		panelForm.add(lblAuteur, "1, 5, right, center");
		
		lieu = new JTextField();
		panelForm.add(lieu, "3, 5, fill, default");
		lieu.setColumns(10);
		
		JLabel lblContre = new JLabel("Contre");
		panelForm.add(lblContre, "1, 6, right, center");
		
		contre = new JTextField();
		panelForm.add(contre, "3, 6, fill, default");
		contre.setColumns(10);
		
		JScrollPane scrollPaneRecherche = new JScrollPane();
		Recherche.add(scrollPaneRecherche, BorderLayout.CENTER);
		
		tableRecherche = new JTable();
		scrollPaneRecherche.setViewportView(tableRecherche);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
