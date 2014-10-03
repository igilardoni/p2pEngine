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

public class EditerReseau extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650459589240951135L;
	private Application app;
	private JTextField tcp;
	
	private void sauvegarder() {
	
		this.dispose();
	}
	
	public EditerReseau(Application app) {
		this.app = app;
		this.setSize(400, 400);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(app.getWindow());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.add(panel);
		
		JLabel label =  new JLabel("Entrez l'ip d'un correspondant");
		panel.add(label);
		
		tcp = new JTextField();
		tcp.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(tcp);
	
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

