package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

/**
 * Permet de rechercher un utilisateur afin d'ouvrir une nouvelle discussion
 * @author
 *
 */

@SuppressWarnings("serial")
public class NewConvers extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JTextField identifiant;
	private MessagesPanel mp;
	private JLabel errorUtilisateurInconnu;
	private JLabel lblCreationDiscu;
	private JLabel lblEntrerIdentifiant;
	private JButton btnValider;
	private JButton btnAnnuler;
	
	public NewConvers(MessagesPanel mp_) {
		this.mp = mp_;
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblCreationDiscu = new JLabel(Langues.getString("NewConvers.lblNouvelleDiscu.text"));
		lblCreationDiscu.setForeground(SystemColor.textHighlight);
		lblCreationDiscu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		lblEntrerIdentifiant = new JLabel(Langues.getString("NewConvers.lblIdentifiant.text"));
		
		identifiant = new JTextField();
		identifiant.setColumns(10);
		
		btnValider = new JButton(Langues.getString("NewConvers.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.NewConvers validator = new controller.NewConvers(identifiant.getText());
				if(validator.validate()) {
					validator.process();
					mp.setNewConvers(identifiant.getText());
					dispose();
					return;
				}
				else {
					errorUtilisateurInconnu.setVisible(true);
				}
			}
		});
		
		errorUtilisateurInconnu = new JLabel(Langues.getString("NewConvers.errorUtilInconnu.text"));
		errorUtilisateurInconnu.setVisible(false);
		errorUtilisateurInconnu.setForeground(Color.RED);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCreationDiscu)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblEntrerIdentifiant))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(identifiant, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnValider))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(errorUtilisateurInconnu)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblCreationDiscu)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEntrerIdentifiant)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnValider))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorUtilisateurInconnu)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAnnuler = new JButton(Langues.getString("NewConvers.btnAnnuler.text"));
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnAnnuler.setActionCommand("OK");
				buttonPane.add(btnAnnuler);
				getRootPane().setDefaultButton(btnAnnuler);
			}
		}
	}
}
