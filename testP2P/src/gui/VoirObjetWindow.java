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

import p2pEngine.Objet;

public abstract class VoirObjetWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650459589240951135L;
	private Application app;
	protected Objet obj;
	
	public VoirObjetWindow(Application app, Objet o) {
		this.app = app;
		this.obj = o;
		this.setSize(400, 400);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(app.getWindow());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.add(panel);
		
		JLabel label =  new JLabel(getNameString());
		panel.add(label);
		
		label = new JLabel("Description:" + obj.getDescription());
		panel.add(label);
		
		label = new JLabel(getOtherNameString());
		panel.add(label);
		
		label = new JLabel("Information de contact :");
		panel.add(label);
		
		label = new JLabel("Nom de l'annonceur: " + obj.getUser().getNom());
		panel.add(label);
		
		label = new JLabel("Téléphone : " + obj.getUser().getTel());
		panel.add(label);
		
		label = new JLabel("Mail : " + obj.getUser().getMail());
		panel.add(label);
		this.pack();
		
	}
	
	protected abstract String getNameString();
	protected abstract String getOtherNameString();
	
}
