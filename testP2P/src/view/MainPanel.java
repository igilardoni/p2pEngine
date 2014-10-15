package view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab(Messages.getString("mesAnnonces"), null, panel_2, null);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel_1 = new APanel();
		panel_1.setPreferredSize(new Dimension(0, 0));
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		AnnoncePanel annoncePanel = new AnnoncePanel();
		panel_1.add(annoncePanel);
		
		AnnoncePanel annoncePanel_1 = new AnnoncePanel();
		panel_1.add(annoncePanel_1);
		
		AnnoncePanel annoncePanel_2 = new AnnoncePanel();
		panel_1.add(annoncePanel_2);
		panel_2.add(scrollPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab(Messages.getString("search_button"), null, panel, null);
		setLayout(new CardLayout(0, 0));
		add(tabbedPane, "name_111035141476854");

	}
}
