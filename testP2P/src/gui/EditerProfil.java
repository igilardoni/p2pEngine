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

import p2pEngine.Offre;
import p2pEngine.Utilisateur;

public class EditerProfil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650459589240951135L;
	private Application app;
	
	private Utilisateur user;
	private JTextField nom;
	private JTextField tel;
	private JTextField email;
	
	private void sauvegarder() {
		
		user.setNom(nom.getText());
		user.setMail(email.getText());
		user.setTel(tel.getText());
		
		user.serialiser("user.seralized");
		
		this.dispose();
	}
	
	public EditerProfil(Application app) {
		this.app = app;
		this.user = app.getUser();
		this.setSize(400, 400);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(app.getWindow());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.add(panel);
		
		JLabel label =  new JLabel("Nom ou pseudo :");
		panel.add(label);
		
		nom = new JTextField();
		nom.setAlignmentX(CENTER_ALIGNMENT);
		nom.setText(user.getNom());
		panel.add(nom);
		
		label = new JLabel("Numéro de téléphone :");
		panel.add(label);
		
		tel = new JTextField();
		tel.setAlignmentX(CENTER_ALIGNMENT);
		tel.setText(user.getTel());
		panel.add(tel);
		
		label = new JLabel("E-mail :");
		panel.add(label);
		
		email = new JTextField();
		email.setAlignmentX(CENTER_ALIGNMENT);
		email.setText(user.getMail());
		panel.add(email);
		
		JButton button = new JButton("Valider");
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sauvegarder();
			}
		});
		panel.add(button);
		
		
		this.pack();
		
	}
	
}
