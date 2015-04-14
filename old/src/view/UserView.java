package view;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import model.User;

/**
 * Panel de transition pour contacter le vendeur
 * @author 
 *
 */

@SuppressWarnings("serial")
public class UserView extends JFrame {

	private JPanel contentPane;
	
	private JLabel lblUtilisateur;
	private JLabel lblEmail;
	private JLabel lblAdMail;
	private JLabel lblTelephone;
	private JLabel lblNumero;
	
	private JButton btnEnvoyer;
	
	public UserView(User u) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblUtilisateur = new JLabel(Langues.getString("UserView.lblUtilisateur.text") + u.getLogin());
		lblUtilisateur.setForeground(SystemColor.textHighlight);
		lblUtilisateur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblEmail = new JLabel(Langues.getString("UserView.lblEmail.text"));
		
		lblAdMail = new JLabel(u.getMail());
		
		lblTelephone = new JLabel(Langues.getString("UserView.lblTelephone.text"));
		
		lblNumero = new JLabel(u.getTel());
		
		btnEnvoyer = new JButton(Langues.getString("UserView.btnEnvoyer.text"));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUtilisateur)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEmail)
								.addComponent(lblTelephone))
							.addGap(32)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNumero)
								.addComponent(lblAdMail)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnEnvoyer)))
					.addContainerGap(204, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblUtilisateur)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(lblAdMail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelephone)
						.addComponent(lblNumero))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEnvoyer)
					.addContainerGap(136, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
