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

/**
 * Fenetre d'inscription d'une nouvel utilisateur
 * @author 
 */

@SuppressWarnings("serial")
public class Inscription extends JPanel {
	private JTextField nom;
	private JTextField prenom;
	private JTextField adresse;
	private JTextField tel;
	private JTextField login;
	
	private JPasswordField password1;
	private JPasswordField password2;
	private JFormattedTextField email;
	
	private JLabel lblNouveauCompte;
	private JLabel lblLogin;
	private JLabel lblPass1;
	private JLabel lblPass2;
	private JLabel lblInfoPersonnelles;
	private JLabel lblAdresse;
	private JLabel lblEmail;
	private JLabel lblTelephone;
	private JLabel lblNom;
	private JLabel lblPrenom;
	
	private JLabel errorLogin;
	private JLabel errorPass1;
	private JLabel errorPass2;
	private JLabel errorAdresse;
	private JLabel errorEmail;
	private JLabel errorTel; 
	private JLabel errorNom;
	private JLabel errorPrenom;
	
	private JButton btnValider;

	public Inscription() {
		
		lblNouveauCompte = new JLabel(Langues.getString("Inscription.lblNouveauCompte.text"));
		lblNouveauCompte.setForeground(SystemColor.textHighlight);
		lblNouveauCompte.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		lblLogin = new JLabel(Langues.getString("Inscription.lblLogin.text"));
		
		lblPass1 = new JLabel(Langues.getString("Inscription.lblPass1.text"));
		
		lblPass2 = new JLabel(Langues.getString("Inscription.lblPass2.text"));
		
		login = new JTextField();
		login.setColumns(10);
		
		password1 = new JPasswordField();
		password2 = new JPasswordField();
		
		lblInfoPersonnelles = new JLabel(Langues.getString("Inscription.lblInfoPersonnelles.text"));
		lblInfoPersonnelles.setForeground(SystemColor.textHighlight);
		lblInfoPersonnelles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator_1 = new JSeparator();
		
		lblAdresse = new JLabel(Langues.getString("Inscription.lblAdresse.text"));
		
		adresse = new JTextField();
		adresse.setColumns(10);
		
		lblEmail = new JLabel(Langues.getString("Inscription.lblEMail.text"));
		
		email = new JFormattedTextField();
		
		lblTelephone = new JLabel(Langues.getString("Inscription.lblTelephone.text"));
		
		tel = new JTextField();
		tel.setColumns(10);
		
		btnValider = new JButton(Langues.getString("Inscription.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initErrorMsg();
				controller.Inscription inscription = new controller.Inscription(login.getText(), new String(password1.getPassword()), 
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
		
		errorLogin = new JLabel(Langues.getString("Inscription.errorLogin.text"));
		errorLogin.setVisible(false);
		errorLogin.setForeground(Color.RED);
		
		errorPass1 = new JLabel(Langues.getString("Inscription.errorPass1.text"));
		errorPass1.setVisible(false);
		errorPass1.setForeground(Color.RED);
		
		errorPass2 = new JLabel(Langues.getString("Inscription.errorPass2.text"));
		errorPass2.setVisible(false);
		errorPass2.setForeground(Color.RED);
		
		lblNom = new JLabel(Langues.getString("Inscription.lblNom.text"));
		
		lblPrenom = new JLabel(Langues.getString("Inscription.lblPrenom.text"));
		
		nom = new JTextField();
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setColumns(10);
		
		errorAdresse = new JLabel(Langues.getString("Inscription.errorAdresse.text"));
		errorAdresse.setVisible(false);
		errorAdresse.setForeground(Color.RED);
		
		errorEmail = new JLabel(Langues.getString("Inscription.errorEMail.text"));
		errorEmail.setVisible(false);
		errorEmail.setForeground(Color.RED);
		
		errorTel = new JLabel(Langues.getString("Inscription.errorTelephone.text"));
		errorTel.setVisible(false);
		errorTel.setForeground(Color.RED);
		
		errorNom = new JLabel(Langues.getString("Inscription.errorNom.text"));
		errorNom.setVisible(false);
		errorNom.setForeground(Color.RED);
		
		errorPrenom = new JLabel(Langues.getString("Inscription.lblErrorPrenom.text"));
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
						.addComponent(lblPass2)
						.addComponent(lblPass1)
						.addComponent(lblLogin))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(password2)
						.addComponent(password1)
						.addComponent(login, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(errorLogin)
						.addComponent(errorPass1)
						.addComponent(errorPass2))
					.addContainerGap(32, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInfoPersonnelles)
					.addContainerGap(298, Short.MAX_VALUE))
				.addComponent(separator_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrenom)
						.addComponent(lblNom)
						.addComponent(lblEmail)
						.addComponent(lblAdresse)
						.addComponent(lblTelephone))
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
						.addComponent(lblPass1)
						.addComponent(password1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPass1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPass2)
						.addComponent(password2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPass2))
					.addGap(18)
					.addComponent(lblInfoPersonnelles)
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
						.addComponent(lblTelephone)
						.addComponent(tel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorTel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorNom))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrenom)
						.addComponent(prenom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorPrenom))
					.addGap(18)
					.addComponent(btnValider)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	/**
	 * Cache tous les messages d'erreur
	 */
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
	
	/**
	 * Affiche certains messages d'erreur si besoin
	 */
	private void showErrorMsg(controller.Inscription inscription) {
		if(inscription.errorLogin) {
			errorLogin.setVisible(true);
		}
		if(inscription.errorDuplicateLogin) {
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
