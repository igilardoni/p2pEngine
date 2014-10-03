package gui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import p2pEngine.Demande;

public class AjouterDemandeWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650459589240951135L;
	private Application app;
	
	private JTextField nom;
	private JTextField description;
	private JTextField enEchange;
	
	private void add() {
		Demande demande = new Demande(nom.getText(), description.getText(), enEchange.getText(), app.getUser());
		app.publishDemande(demande);
		this.dispose();
	}
	
	public AjouterDemandeWindow(Application app) {
		this.app = app;
		this.setSize(400, 400);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(app.getWindow());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.add(panel);
		
		JLabel label =  new JLabel("Nom de l'objet voulu");
		panel.add(label);
		
		nom = new JTextField();
		nom.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(nom);
		
		label = new JLabel("Description de l'objet");
		panel.add(label);
		
		description = new JTextField();
		description.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(description);
		
		label = new JLabel("Nom de l'objet à donner");
		panel.add(label);
		
		enEchange = new JTextField();
		enEchange.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(enEchange);
		
		JButton button = new JButton("Valider");
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		panel.add(button);
		
		
		this.pack();
		
	}
	
}