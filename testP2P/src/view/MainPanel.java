package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.Advertisable;
import model.Objet;
import model.ObjetsManagement;
import model.SearchListener;
import model.User;

/**
 * Contient les onglets Mes annonces et Rechercher sur la page principale
 * @author
 *
 */

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements SearchListener{
	private JTextField recherche;
	private APanel annonceContainer;
	private APanel rechercheContainer;
	
	private JTabbedPane tabbedPane;
	
	private JScrollPane scrpnlMesAnnonces;
	private JScrollPane scrpnlRechercher;
	
	private JPanel pnlRechercher;
	private JPanel pnlHaut;
	private JPanel pnlBas;
	private JPanel pnlRecherche;
	private JPanel pnlMesAnnonces;
	
	private JButton btnRechercher;
	private final JCheckBox rdbtnTroc;
	private final JCheckBox rdbtnVente;
	
	
	
	public MainPanel() {
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		pnlMesAnnonces = new JPanel();
		tabbedPane.addTab(Langues.getString("MainPanel.tabMesAnnonces.text"), null, pnlMesAnnonces, null);
		pnlMesAnnonces.setLayout(new BoxLayout(pnlMesAnnonces, BoxLayout.Y_AXIS));
		
		scrpnlMesAnnonces = new JScrollPane();
		scrpnlMesAnnonces.getVerticalScrollBar().setUnitIncrement(16);
		scrpnlMesAnnonces.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		annonceContainer = new APanel();
		annonceContainer.setPreferredSize(new Dimension(0, 0));
		scrpnlMesAnnonces.setViewportView(annonceContainer);
		annonceContainer.setLayout(new BoxLayout(annonceContainer, BoxLayout.Y_AXIS));
		
		getUserObjet(annonceContainer);
		
		
		pnlMesAnnonces.add(scrpnlMesAnnonces);
		
		pnlRechercher = new JPanel();
		tabbedPane.addTab(Langues.getString("MainPanel.tabRecherche.text"), null, pnlRechercher, null);
		pnlRechercher.setLayout(new BorderLayout(0, 0));
		
		pnlHaut = new JPanel();
		pnlHaut.setBounds(0, 0, 445, 26);
		pnlRechercher.add(pnlHaut, BorderLayout.NORTH);
		
		pnlRecherche = new JPanel();
		GroupLayout gl_pnlHaut = new GroupLayout(pnlHaut);
		gl_pnlHaut.setHorizontalGroup(
			gl_pnlHaut.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlRecherche, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
		);
		gl_pnlHaut.setVerticalGroup(
			gl_pnlHaut.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlHaut.createSequentialGroup()
					.addComponent(pnlRecherche, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlRecherche.setLayout(new BoxLayout(pnlRecherche, BoxLayout.X_AXIS));
		
		recherche = new JTextField();
		pnlRecherche.add(recherche);
		recherche.setColumns(10);
		
		btnRechercher = new JButton(Langues.getString("MainPanel.btnRecherche.text"));
		pnlRecherche.add(btnRechercher);
		
		rdbtnTroc = new JCheckBox(Langues.getString("MainPanel.rbtnTroc.text"));
		rdbtnTroc.setSelected(true);
		pnlRecherche.add(rdbtnTroc);
		
		rdbtnVente = new JCheckBox(Langues.getString("MainPanel.rbtnVente.text"));
		rdbtnVente.setSelected(true);
		pnlRecherche.add(rdbtnVente);
		pnlHaut.setLayout(gl_pnlHaut);
		
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rechercheContainer.removeAll();
				Application.getInstance().updateUI();
				controller.SearchController validator = new controller.SearchController(recherche.getText(), rdbtnTroc.isSelected(), rdbtnVente.isSelected(), getThis());
				if(validator.validate()) {
					validator.process();
				}
			}
		});
		
		pnlBas = new JPanel();
		pnlBas.setBounds(0, 26, 445, 234);
		pnlRechercher.add(pnlBas);
		
		scrpnlRechercher = new JScrollPane();
		scrpnlRechercher.getVerticalScrollBar().setUnitIncrement(16);
		pnlBas.setLayout(new BoxLayout(pnlBas, BoxLayout.Y_AXIS));
		scrpnlRechercher.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlBas.add(scrpnlRechercher);
		
		rechercheContainer = new APanel();
		scrpnlRechercher.setViewportView(rechercheContainer);
		rechercheContainer.setPreferredSize(new Dimension(0,0));
		rechercheContainer.setLayout(new BoxLayout(rechercheContainer, BoxLayout.Y_AXIS));
		
		setLayout(new CardLayout(0, 0));
		add(tabbedPane, "name_111035141476854");

	}
	
	public void revalidate() {
		if(annonceContainer != null) getUserObjet(annonceContainer);
		super.revalidate();
	}
	
	public MainPanel getThis() {
		return this;
	}
	
	/**
	 * Recupere les objets de l'utilisateur lors de la connexion
	 */
	private void getUserObjet(APanel panel) {
		User user = Application.getInstance().getUsers().getConnectedUser();
		panel.removeAll();
		
		if(user == null) panel.add(new JLabel(Langues.getString("MainPanel.lblConnectezVous.text")));
		else if(user.getObjets().size() == 0) panel.add(new JLabel(Langues.getString("MainPanel.lblAucuneAnnonce.text")));
		else {
			ObjetsManagement objets = user.getObjets();
			for(int i = 0; i < objets.size(); i++) {
				panel.add(new AnnoncePanel(objets.get(i), i));
			}
		}
	}

	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		rechercheContainer.add(new AnnonceRecherchePanel(obj, 0));
	}
}
