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
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MonCompte extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * @wbp.nonvisual location=-430,117
	 */
	private final NoteCanvas noteCanvas = new NoteCanvas();

	/**
	 * Create the dialog.
	 */
	public MonCompte() {
		setBounds(100, 100, 595, 298);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblCrashxxl = new JLabel("Informations générale");
		lblCrashxxl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCrashxxl.setForeground(SystemColor.textHighlight);
		
		JSeparator separator = new JSeparator();
		JLabel lblLogin = new JLabel("Login:");
		JLabel lblTlphone = new JLabel("Téléphone:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblAdresse = new JLabel("Adresse:");
		JLabel lblNom = new JLabel("Nom:");
		JLabel lblPrnom = new JLabel("Prénom:");
		JLabel lblActions = new JLabel("Actions");
		lblActions.setForeground(SystemColor.textHighlight);
		lblActions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JSeparator separator_1 = new JSeparator();
		
		JButton btnNewButton = new JButton("Modifier");
		
		JButton btnSupprimer = new JButton("Supprimer");
		
		JLabel lblCrashxxl_1 = new JLabel("crashxxl");
		
		JLabel lblTelephone = new JLabel("telephone");
		
		JLabel lblEmail_1 = new JLabel("email");
		
		JLabel lblAdresse_1 = new JLabel("adresse");
		
		JLabel lblNom_1 = new JLabel("nom");
		
		JLabel lblPrenom = new JLabel("prenom");
		
		JLabel lblNote = new JLabel("Note:");
		
		JLabel lanote = new JLabel("thenote");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCrashxxl)
					.addContainerGap(399, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAdresse)
						.addComponent(lblEmail)
						.addComponent(lblLogin)
						.addComponent(lblTlphone))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblAdresse_1)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCrashxxl_1)
										.addComponent(lblTelephone))
									.addPreferredGap(ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNom)
										.addComponent(lblPrnom)))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblEmail_1)
									.addPreferredGap(ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
									.addComponent(lblNote)))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNom_1)
								.addComponent(lblPrenom)
								.addComponent(noteCanvas))
							.addGap(55))))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblActions)
					.addContainerGap(503, Short.MAX_VALUE))
				.addComponent(separator_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(btnSupprimer)
					.addContainerGap(365, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblCrashxxl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin)
						.addComponent(lblNom)
						.addComponent(lblCrashxxl_1)
						.addComponent(lblNom_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTlphone)
						.addComponent(lblPrnom)
						.addComponent(lblTelephone)
						.addComponent(lblPrenom))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(lblEmail_1)
						.addComponent(lblNote)
						.addComponent(noteCanvas))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdresse)
						.addComponent(lblAdresse_1))
					.addGap(18)
					.addComponent(lblActions)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnSupprimer))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
