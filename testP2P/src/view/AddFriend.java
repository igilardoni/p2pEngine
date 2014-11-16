package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
/**
 * Fenetre qui permet d'ajouter un ami aux contacts
 * @author Prudhomme Julien
 *
 */
@SuppressWarnings("serial")
public class AddFriend extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnlBoutons;
	private JTextField nomContact;
	private JLabel errorContact;
	private JLabel lblAjouterUnAmi;
	private JLabel lblNomDuContact;
	private JButton btnValider;
	private JButton btnAnnuler;
	

	public AddFriend() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		lblAjouterUnAmi = new JLabel(Langues.getString("AddFriend.lblAjouterAmi.text"));
		lblAjouterUnAmi.setForeground(SystemColor.textHighlight);
		lblAjouterUnAmi.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAjouterUnAmi.setBackground(SystemColor.textHighlight);
		
		JSeparator separator = new JSeparator();
		
		lblNomDuContact = new JLabel(Langues.getString("AddFriend.lblNomContact.text"));
		
		nomContact = new JTextField();
		nomContact.setColumns(10);
		
		btnValider = new JButton(Langues.getString("AddFriend.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AddFriend validator = new controller.AddFriend(nomContact.getText());
				if(validator.validate()) {
					validator.process();
					dispose();
				}
				else errorContact.setVisible(true);
				
			}
		});
		
		errorContact = new JLabel(Langues.getString("AddFriend.lblErrorContact.text"));
		errorContact.setVisible(false);
		errorContact.setForeground(Color.RED);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNomDuContact)
							.addGap(18)
							.addComponent(nomContact, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnValider))
						.addComponent(lblAjouterUnAmi)
						.addComponent(errorContact))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblAjouterUnAmi)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNomDuContact)
						.addComponent(nomContact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnValider))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorContact)
					.addContainerGap(142, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			pnlBoutons = new JPanel();
			pnlBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBoutons, BorderLayout.SOUTH);
			{
				btnAnnuler = new JButton(Langues.getString("AddFriend.btnAnnuler.text"));
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnAnnuler.setActionCommand("OK");
				pnlBoutons.add(btnAnnuler);
				getRootPane().setDefaultButton(btnAnnuler);
			}
		}
	}
}
