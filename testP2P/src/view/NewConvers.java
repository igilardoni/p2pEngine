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
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NewConvers extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private MessagesPanel mp;
	private JLabel lblUtilisateurInconnue;


	/**
	 * Create the dialog.
	 */
	public NewConvers(MessagesPanel mp_) {
		this.mp = mp_;
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblCrationDuneDiscussion = new JLabel(Messages.getString("NewConvers.lblNouvelleDiscu.text"));
		lblCrationDuneDiscussion.setForeground(SystemColor.textHighlight);
		lblCrationDuneDiscussion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		JLabel lblVeuillezEntrerLidentifiant = new JLabel(Messages.getString("NewConvers.lblIdentifiant.text"));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnValider = new JButton(Messages.getString("NewConvers.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.NewConvers validator = new controller.NewConvers(textField.getText());
				if(validator.validate()) {
					validator.process();
					mp.setNewConvers(textField.getText());
					dispose();
					return;
					
				}
				else {
					lblUtilisateurInconnue.setVisible(true);
				}
			}
		});
		
		lblUtilisateurInconnue = new JLabel(Messages.getString("NewConvers.lblUtilInconnu.text"));
		lblUtilisateurInconnue.setVisible(false);
		lblUtilisateurInconnue.setForeground(Color.RED);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCrationDuneDiscussion)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblVeuillezEntrerLidentifiant))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnValider))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblUtilisateurInconnue)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblCrationDuneDiscussion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblVeuillezEntrerLidentifiant)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnValider))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUtilisateurInconnue)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(Messages.getString("NewConvers.btnAnnuler.text"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
