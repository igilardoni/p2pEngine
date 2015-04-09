package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import model.ObjetsManagement;


/**
 * Affiche le panier de l'utilisateur courrant
 * @author
 *
 */

@SuppressWarnings("serial")
public class Panier extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private APanel annonceContainer;
	private JPanel buttonPane;
	JScrollPane scrpnlAnnonces;
	private JButton btnPoursuivre;
	private JButton btnViderPanier;
	
	public Panier() {
		setBounds(100, 100, 754, 481);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		scrpnlAnnonces = new JScrollPane();
		scrpnlAnnonces.getVerticalScrollBar().setUnitIncrement(16);
		scrpnlAnnonces.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrpnlAnnonces.setViewportView(annonceContainer);
		contentPanel.add(scrpnlAnnonces);
		
		annonceContainer = new APanel();
		annonceContainer.setPreferredSize(new Dimension(0, 0));
		annonceContainer.setLayout(new BoxLayout(annonceContainer, BoxLayout.Y_AXIS));
		scrpnlAnnonces.setViewportView(annonceContainer);
		
		loadPanier();
			
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnViderPanier = new JButton(Langues.getString("Panier.btnViderPanier.text"));
				btnViderPanier.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Application.getInstance().getUsers().getConnectedUser().getPanier().removeAll();
						revalidate();
					}
				});
				buttonPane.add(btnViderPanier);
			}
			{
				btnPoursuivre = new JButton(Langues.getString("Panier.btnPoursuivre.text"));
				btnPoursuivre.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnPoursuivre.setActionCommand("OK");
				buttonPane.add(btnPoursuivre);
				getRootPane().setDefaultButton(btnPoursuivre);
			}
		}
	}
	
	public void revalidate() {
		loadPanier();
		repaint();
		super.revalidate();
	}
	
	/**
	 * Charge le panier ou affiche vide
	 */
	private void loadPanier() {
		annonceContainer.removeAll();
		ObjetsManagement panier = Application.getInstance().getUsers().getConnectedUser().getPanier();
		if(panier.size() == 0) annonceContainer.add(new JLabel(Langues.getString("Panier.lblPanierVide.text")));
		for(int i = 0; i < panier.size(); i++) {
			annonceContainer.add(new AnnoncePanierPanel(panier.get(i), i, this));
		}
	}
}
