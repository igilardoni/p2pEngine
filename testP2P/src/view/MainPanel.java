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
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainPanel extends JPanel {
	private JTextField textField;

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
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 445, 26);
		panel.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		panel_5.add(textField);
		textField.setColumns(10);
		
		JButton btnRechercher = new JButton("Rechercher");
		panel_5.add(btnRechercher);
		
		JRadioButton rdbtnTroc = new JRadioButton("Troc");
		panel_5.add(rdbtnTroc);
		
		JRadioButton rdbtnVente = new JRadioButton("Vente");
		panel_5.add(rdbtnVente);
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 26, 445, 234);
		panel.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane);
		
		APanel panel_7 = new APanel();
		panel_7.setPreferredSize(new Dimension(0, 0));
		scrollPane.setViewportView(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		AnnoncePanel annoncePanel_3 = new AnnoncePanel();
		panel_7.add(annoncePanel_3);
		
		AnnoncePanel annoncePanel_4 = new AnnoncePanel();
		panel_7.add(annoncePanel_4);
		
		AnnoncePanel annoncePanel_5 = new AnnoncePanel();
		panel_7.add(annoncePanel_5);
		setLayout(new CardLayout(0, 0));
		add(tabbedPane, "name_111035141476854");

	}
}
