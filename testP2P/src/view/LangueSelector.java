package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

public class LangueSelector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private HashMap map;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LangueSelector dialog = new LangueSelector();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LangueSelector() {
		setBounds(100, 100, 450, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblSelectionDeLa = new JLabel(Messages.getString("LangueSelector.lblSelectionLangue.text"));
		lblSelectionDeLa.setForeground(SystemColor.textHighlight);
		lblSelectionDeLa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblLangueDuLogiciel = new JLabel(Messages.getString("LangueSelector.lblLangueLogiciel.text"));
		
		map = new HashMap<String, Locale>();
		for(Locale l: Locale.getAvailableLocales()) {
			String search = "messages_" + l.getLanguage() + ".properties";
			if(getClass().getResource(search) != null)  {
				map.put(l.getDisplayLanguage(l), l);
			}
		}
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(map.keySet().toArray()));
		
		JButton btnValider = new JButton(Messages.getString("LangueSelector.btnValider.text"));
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
					.addComponent(lblSelectionDeLa)
					.addContainerGap(283, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblLangueDuLogiciel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnValider)
					.addGap(157))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSelectionDeLa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLangueDuLogiciel)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnValider)))
					.addContainerGap(153, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton annuler = new JButton(Messages.getString("LangueSelector.btnAnnuler.text"));
				annuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				annuler.setActionCommand("Cancel");
				buttonPane.add(annuler);
			}
		}
	}
}
