package view;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Inscription extends JPanel {
	private JTextField login;
	private JPasswordField password;
	private JPasswordField password2;
	private JTextField adresse;
	private JTextField tel;
	private JFormattedTextField email;
	
	private JLabel errorLogin;
	private JLabel errorPass1;
	private JLabel errorPass2;
	private JTextField nom;
	private JTextField prenom;
	
	private JLabel errorAdresse;
	private JLabel errorEmail;
	private JLabel errorTel; 
	private JLabel errorNom;
	private JLabel errorPrenom;

	/**
	 * Create the panel.
	 */
	public Inscription() {
		
		JLabel lblNouveauCompte = new JLabel("Nouveau compte");
		lblNouveauCompte.setForeground(SystemColor.textHighlight);
		lblNouveauCompte.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblLogin = new JLabel("Login");
		
		JLabel lblMotDePasse = new JLabel("Mot de passe");
		
		JLabel lblConfirmationDuMot = new JLabel("Confirmation");
		
		login = new JTextField();
		login.setColumns(10);
		
		password = new JPasswordField();
		
		password2 = new JPasswordField();
		
		JLabel lblInformationPersonnelles = new JLabel("Information personnelles");
		lblInformationPersonnelles.setForeground(SystemColor.textHighlight);
		lblInformationPersonnelles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblAdresse = new JLabel("Adresse");
		
		adresse = new JTextField();
		adresse.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail");
		
		 email = new JFormattedTextField();
		
		JLabel lblNewLabel = new JLabel("Téléphone");
		
		tel = new JTextField();
		tel.setColumns(10);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initErrorMsg();
				controller.Inscription inscription = new controller.Inscription(login.getText(), new String(password.getPassword()), 
						new String(password2.getPassword()), adresse.getText(), email.getText(), 
						tel.getText(), nom.getText(), prenom.getText());
				if(inscription.validate()) {
					inscription.process();
					JPanel parent = (JPanel) getParent();
					JDialog dialog = (JDialog) parent.getRootPane().getParent();
					new InscriptionSuccess().setVisible(true);
					dialog.dispose();
				}
				else {
					showErrorMsg(inscription);
					invalidate();
				}
			}
		});
		
		errorLogin = new JLabel("Doit faire plus de 3 caractères");
		errorLogin.setVisible(false);
		errorLogin.setForeground(Color.RED);
		
		errorPass1 = new JLabel("Doit faire plus de 6 caractères");
		errorPass1.setVisible(false);
		errorPass1.setForeground(Color.RED);
		
		errorPass2 = new JLabel("Doit être identique");
		errorPass2.setVisible(false);
		errorPass2.setForeground(Color.RED);
		
		JLabel lblNom = new JLabel("Nom");
		
		JLabel lblPrnom = new JLabel("Prénom");
		
		nom = new JTextField();
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setColumns(10);
		
		errorAdresse = new JLabel("Doit être rempli");
		errorAdresse.setVisible(false);
		errorAdresse.setForeground(Color.RED);
		
		errorEmail = new JLabel("Mauvais format");
		errorEmail.setVisible(false);
		errorEmail.setForeground(Color.RED);
		
		errorTel = new JLabel("Mauvais format");
		errorTel.setVisible(false);
		errorTel.setForeground(Color.RED);
		
		errorNom = new JLabel("Doit être rempli");
		errorNom.setVisible(false);
		errorNom.setForeground(Color.RED);
		
		errorPrenom = new JLabel("Doit être rempli");
		errorPrenom.setVisible(false);
		errorPrenom.setForeground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNouveauCompte)
					.addContainerGap(346, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblConfirmationDuMot)
						.addComponent(lblMotDePasse)
						.addComponent(lblLogin))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(password2)
						.addComponent(password)
						.addComponent(login, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(errorLogin)
						.addComponent(errorPass1)
						.addComponent(errorPass2))
					.addContainerGap(32, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInformationPersonnelles)
					.addContainerGap(298, Short.MAX_VALUE))
				.addComponent(separator_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrnom)
						.addComponent(lblNom)
						.addComponent(lblEmail)
						.addComponent(lblAdresse)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnValider)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(adresse, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
								.addComponent(nom)
								.addComponent(email)
								.addComponent(tel, 156, 156, Short.MAX_VALUE)
								.addComponent(prenom))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(errorAdresse)
								.addComponent(errorEmail)
								.addComponent(errorTel)
								.addComponent(errorNom)
								.addComponent(errorPrenom))))
					.addContainerGap(116, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNouveauCompte)
					.addGap(9)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin)
						.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorLogin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMotDePasse)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPass1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfirmationDuMot)
						.addComponent(password2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPass2))
					.addGap(18)
					.addComponent(lblInformationPersonnelles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdresse)
						.addComponent(adresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorAdresse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(tel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorTel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorNom))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrnom)
						.addComponent(prenom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPrenom))
					.addGap(18)
					.addComponent(btnValider)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	private void initErrorMsg() {
		errorLogin.setVisible(false);
		errorPass1.setVisible(false);
		errorPass2.setVisible(false);
		errorEmail.setVisible(false);
		errorTel.setVisible(false);
		errorAdresse.setVisible(false);
		errorNom.setVisible(false);
		errorPrenom.setVisible(false);
	}
	
	private void showErrorMsg(controller.Inscription inscription) {
		if(inscription.errorLogin) {
			errorLogin.setText("Doit faire plus de 3 caractères");
			errorLogin.setVisible(true);
		}
		
		if(inscription.errorDuplicateLogin) {
			errorLogin.setText("Ce login est déjà pris");
			errorLogin.setVisible(true);
		}
		
		if(inscription.errorPassword) {
			errorPass1.setVisible(true);
		}
		
		if(inscription.errorPassword2) {
			errorPass2.setVisible(true);
		}
		
		if(inscription.errorEmail) {
			errorEmail.setVisible(true);
		}
		
		if(inscription.errorTel) {
			errorTel.setVisible(true);
		}
		if(inscription.errorAdresse) {
			errorAdresse.setVisible(true);
		}
		if(inscription.errorNom) {
			errorNom.setVisible(true);
		}
		if(inscription.errorPrenom) {
			errorPrenom.setVisible(true);
		}
	}
}
