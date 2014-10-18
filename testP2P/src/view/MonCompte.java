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

import model.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Color;

public class MonCompte extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * @wbp.nonvisual location=-430,117
	 */
	private final NoteCanvas noteCanvas = new NoteCanvas();
	private User user = Application.getInstance().getUsers().getConnectedUser();
	private JTextField email;
	private JTextField tel;
	private JTextField adresse;
	private JLabel errorEmail;
	private JLabel errorTel;
	private JLabel errorAdresse;
	private JLabel succesForm;
	

	/**
	 * Create the dialog.
	 */
	public MonCompte() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 595, 459);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblCrashxxl = new JLabel("Connecté en tant que " + user.getLogin());
		lblCrashxxl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCrashxxl.setForeground(SystemColor.textHighlight);
		
		JSeparator separator = new JSeparator();
		JLabel lblActions = new JLabel("Actions");
		lblActions.setForeground(SystemColor.textHighlight);
		lblActions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JSeparator separator_1 = new JSeparator();
		
		JButton btnSupprimer = new JButton("Supprimer le compte");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SuppCompteConfirm(getThis()).setVisible(true);
			}
		});
		
		JLabel lanote = new JLabel("thenote");
		
		JPanel panel = new JPanel();
		
		JLabel lblNote = new JLabel("Note:");
		
		JButton btnDconnexion = new JButton("Déconnexion");
		btnDconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getUsers().disconnectUser();
				Application.getInstance().updateUI();
				dispose();
			}
		});
		
		JLabel lblEmail = new JLabel("Email:");
		
		email = new JTextField(user.getMail());
		email.setColumns(10);
		
		JLabel lblTlphone = new JLabel("Téléphone:");
		
		tel = new JTextField(user.getTel());
		tel.setColumns(10);
		
		JLabel lblAdresse = new JLabel("Adresse:");
		
		adresse = new JTextField(user.getAdresse());
		adresse.setColumns(10);
		
		JButton btnValiderChangements = new JButton("Valider changements");
		btnValiderChangements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initError();
				controller.MonCompte compte = new controller.MonCompte(email.getText(), tel.getText(), adresse.getText());
				if(compte.validate()) {
					compte.process();
					succesForm.setVisible(true);
				}
				else showErrors(compte);
			}
		});
		
		JLabel lblNom = new JLabel("Nom:");
		
		JLabel lblPrnom = new JLabel("Prénom:");
		
		JLabel lblNewLabel = new JLabel(user.getNom());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_1 = new JLabel(user.getPrenom());
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel label = new JLabel(user.getMoyenneNotes() + "/5");
		
		errorEmail = new JLabel("Mauvais format");
		errorEmail.setVisible(false);
		errorEmail.setForeground(Color.RED);
		
		errorTel = new JLabel("Mauvais format");
		errorTel.setVisible(false);
		errorTel.setForeground(Color.RED);
		
		errorAdresse = new JLabel("Doit être rempli");
		errorAdresse.setVisible(false);
		errorAdresse.setForeground(Color.RED);
		
		succesForm = new JLabel("Changements effectués !");
		succesForm.setVisible(false);
		succesForm.setForeground(new Color(0, 204, 0));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCrashxxl)
					.addPreferredGap(ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
					.addComponent(lblNote)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addGap(7))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActions)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnSupprimer)
							.addGap(18)
							.addComponent(btnDconnexion)))
					.addContainerGap(279, Short.MAX_VALUE))
				.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrnom)
						.addComponent(lblNom)
						.addComponent(lblAdresse)
						.addComponent(lblTlphone)
						.addComponent(lblEmail))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addContainerGap())
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblNewLabel)
								.addContainerGap())
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(btnValiderChangements)
									.addContainerGap())
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
											.addComponent(tel)
											.addComponent(email, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
										.addGap(18)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(errorEmail)
											.addComponent(errorTel))
										.addContainerGap(200, Short.MAX_VALUE))
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(adresse, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPanel.createSequentialGroup()
												.addGap(207)
												.addComponent(noteCanvas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_contentPanel.createSequentialGroup()
												.addGap(18)
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
													.addComponent(succesForm)
													.addComponent(errorAdresse))))
										.addGap(99)))))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(label))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCrashxxl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
							.addComponent(lblNote, Alignment.TRAILING)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTlphone)
						.addComponent(tel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorTel))
					.addGap(10)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(noteCanvas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblAdresse)
							.addComponent(adresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(errorAdresse)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnValiderChangements)
						.addComponent(succesForm))
					.addGap(13)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrnom)
						.addComponent(lblNewLabel_1))
					.addGap(52)
					.addComponent(lblActions)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSupprimer)
						.addComponent(btnDconnexion))
					.addGap(47))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		NoteCanvas noteCanvas_1 = new NoteCanvas(user.getMoyenneNotes());
		panel.add(noteCanvas_1, BorderLayout.CENTER);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Quitter");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private void initError() {
		errorEmail.setVisible(false);
		errorAdresse.setVisible(false);
		errorTel.setVisible(false);
		succesForm.setVisible(false);
	}
	
	private void showErrors(controller.MonCompte compte) {
		if(compte.errorAdresse) errorAdresse.setVisible(true);
		if(compte.errorEmail) errorEmail.setVisible(true);
		if(compte.errorTel) errorTel.setVisible(true);
	}
	
	private JDialog getThis() {
		return this;
	}
}
