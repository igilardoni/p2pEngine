package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


/**
 * Permet de choisir la langue du programme
 * @author 
 *
 */
@SuppressWarnings({"serial", "rawtypes"})
public class LangueSelector extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private HashMap map;
	
	private JLabel lblSelectionLangue;
	private JLabel lblLangueLogiciel;
	
	private final JComboBox comboBox;
	
	private JButton btnValider;
	private JButton btnAnnuler;
	

	@SuppressWarnings({ "unchecked" })
	public LangueSelector() {
		setBounds(100, 100, 450, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblSelectionLangue = new JLabel(Langues.getString("LangueSelector.lblSelectionLangue.text"));
		lblSelectionLangue.setForeground(SystemColor.textHighlight);
		lblSelectionLangue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		lblLangueLogiciel = new JLabel(Langues.getString("LangueSelector.lblLangueLogiciel.text"));
		
		map = new HashMap<String, Locale>();
		for(Locale l: Locale.getAvailableLocales()) {
			String search = "messages_" + l.getLanguage() + ".properties";
			if(getClass().getResource(search) != null)  {
				map.put(l.getDisplayLanguage(l), l);
			}
		}
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(map.keySet().toArray()));
		
		btnValider = new JButton(Langues.getString("LangueSelector.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault((Locale) map.get(comboBox.getSelectedItem()));
				Application.saveLocale((Locale) map.get(comboBox.getSelectedItem()));
				Application.restartUI();
				dispose();
			}
		});
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSelectionLangue)
					.addContainerGap(283, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblLangueLogiciel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnValider)
					.addGap(157))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSelectionLangue)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLangueLogiciel)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnValider)))
					.addContainerGap(153, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel pnlBoutons = new JPanel();
			pnlBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBoutons, BorderLayout.SOUTH);
			{
				btnAnnuler = new JButton(Langues.getString("LangueSelector.btnAnnuler.text"));
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnAnnuler.setActionCommand("Cancel");
				pnlBoutons.add(btnAnnuler);
			}
		}
	}
}
