package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class APanel extends JPanel{
	
	public Component add(Component comp) {
		
		super.add(comp);
		Dimension d = this.getPreferredSize();
		d.height += comp.getMinimumSize().height;
		this.setPreferredSize(d);
		
		return comp;
		
	}
	
}
