package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import model.Objet;
import model.ObjetsManagement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Panier extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private APanel annonceContainer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Panier dialog = new Panier();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Panier() {
		setBounds(100, 100, 754, 481);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);
		contentPanel.setLayout(new BorderLayout(0, 0));
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPanel.add(scrollPane_1);
		annonceContainer = new APanel();
		annonceContainer.setPreferredSize(new Dimension(0, 0));
		scrollPane_1.setViewportView(annonceContainer);
		annonceContainer.setLayout(new BoxLayout(annonceContainer, BoxLayout.Y_AXIS));
		
		loadPanier();
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnViderLePanier = new JButton("Vider le panier");
				btnViderLePanier.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Application.getInstance().getUsers().getConnectedUser().getPanier().removeAll();
						revalidate();
					}
				});
				buttonPane.add(btnViderLePanier);
			}
			{
				JButton okButton = new JButton("Quitter");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	@Override
	public void revalidate() {
		loadPanier();
		repaint();
		super.revalidate();
	}
	
	private void loadPanier() {
		annonceContainer.removeAll();
		ObjetsManagement panier = Application.getInstance().getUsers().getConnectedUser().getPanier();
		if(panier.size() == 0) annonceContainer.add(new JLabel("Le panier est vide"));
		for(int i = 0; i < panier.size(); i++) {
			annonceContainer.add(new AnnoncePanierPanel(panier.get(i), i, this));
		}
	}

}
