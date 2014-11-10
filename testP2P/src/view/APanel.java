package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Contient une liste d'AnnoncePanel / AnnoncePanierPanel / AnnonceRecherchePanel
 * @author 
 *
 */

@SuppressWarnings("serial")
public class APanel extends JPanel{
	
	public APanel() {
		super();
		Dimension d = this.getPreferredSize();
		d.height = 0;
		this.setPreferredSize(d);
	}
	
	public Component add(Component comp) {
		
		super.add(comp);
		Dimension d = this.getPreferredSize();
		d.height += comp.getMinimumSize().height;
		this.setPreferredSize(d);
		return comp;
		
	}
	
	public void removeAll() {
		super.removeAll();
		Dimension d = this.getPreferredSize();
		d.height = 0;
		this.setPreferredSize(d);
	}
	
}
