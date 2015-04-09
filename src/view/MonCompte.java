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

import model.User;
import controller.GeneratePdfUser;

/**
 * Affiche la fenetre du compte de l'utilisateur courrant
 * @author 
 *
 */

@SuppressWarnings("serial")
public class MonCompte extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private User user = Application.getInstance().getUsers().getConnectedUser();
	private NoteCanvas noteCanvas;
	private JPanel panel;
	private JPanel buttonPane;
	
	private JTextField email;
	private JTextField tel;
	private JTextField adresse;
	
	private JLabel errorEmail;
	private JLabel errorTel;
	private JLabel errorAdresse;
	private JLabel succesForm;
	private JLabel lblConnecteComme;
	private JLabel lblActions;
	private JLabel lblNoteDe;
	private JLabel lblEmail;
	private JLabel lblTelephone;
	private JLabel lblAdresse;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblAffNom;
	private JLabel lblAffPrenom;
	private JLabel lblNote;
	
	private JButton btnEnregistrer;
	private JButton btnSupprimer;
	private JButton btnDeconnexion;
	private JButton btnPdf;
	private JButton btnQuitter;
	
	public MonCompte() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 595, 459);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblConnecteComme = new JLabel(Langues.getString("MonCompte.lblQuiSuisJe.text") + user.getLogin());
		lblConnecteComme.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConnecteComme.setForeground(SystemColor.textHighlight);
		
		JSeparator separator = new JSeparator();
		
		lblActions = new JLabel(Langues.getString("MonCompte.lblActions.text"));
		lblActions.setForeground(SystemColor.textHighlight);
		lblActions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JSeparator separator_1 = new JSeparator();
		
		btnSupprimer = new JButton(Langues.getString("MonCompte.btnSupprimerCompte.text"));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SuppCompteConfirm(getThis()).setVisible(true);
			}
		});
		
		panel = new JPanel();
		
		lblNoteDe = new JLabel(Langues.getString("MonCompte.lblNote.text"));
		
		btnDeconnexion = new JButton(Langues.getString("MonCompte.btnDeconnexion.text"));
		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getUsers().disconnectUser();
				Application.getInstance().updateUI();
				dispose();
			}
		});
		
		lblEmail = new JLabel(Langues.getString("MonCompte.lblEMail.text"));
		
		email = new JTextField(user.getMail());
		email.setColumns(10);
		
		lblTelephone = new JLabel(Langues.getString("MonCompte.lblTelephone.text"));
		
		tel = new JTextField(user.getTel());
		tel.setColumns(10);
		
		lblAdresse = new JLabel(Langues.getString("MonCompte.lblAdresse.text"));
		
		adresse = new JTextField(user.getAdresse());
		adresse.setColumns(10);
		
		btnEnregistrer = new JButton(Langues.getString("MonCompte.btnEnregChange.text"));
		btnEnregistrer.addActionListener(new ActionListener() {
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
		
		lblNom = new JLabel(Langues.getString("MonCompte.lblNom.text"));
		
		lblPrenom = new JLabel(Langues.getString("MonCompte.lblPrenom.text"));
		
		lblAffNom = new JLabel(user.getNom());
		lblAffNom.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblAffPrenom = new JLabel(user.getPrenom());
		lblAffPrenom.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblNote = new JLabel(user.getMoyenneNotes() + Langues.getString("MonCompte.lblTotalNote.text"));
		
		errorEmail = new JLabel(Langues.getString("MonCompte.lblErrorEMail.text"));
		errorEmail.setVisible(false);
		errorEmail.setForeground(Color.RED);
		
		errorTel = new JLabel(Langues.getString("MonCompte.lblErrorTelephone.text"));
		errorTel.setVisible(false);
		errorTel.setForeground(Color.RED);
		
		errorAdresse = new JLabel(Langues.getString("MonCompte.lblErrorAdresse.text"));
		errorAdresse.setVisible(false);
		errorAdresse.setForeground(Color.RED);
		
		succesForm = new JLabel(Langues.getString("MonCompte.lblModifEnreg.text"));
		succesForm.setVisible(false);
		succesForm.setForeground(new Color(0, 204, 0));
		
		btnPdf = new JButton(Langues.getString("MonCompte.btnPdf.text")); //$NON-NLS-1$
		btnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneratePdfUser validator = new GeneratePdfUser();
				if(validator.validate()) {
					validator.process();
					Application.getInstance().updateUI();
				}
			}
		});
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConnecteComme)
					.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
					.addComponent(lblNoteDe)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNote)
					.addGap(7))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActions)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnSupprimer)
							.addGap(18)
							.addComponent(btnDeconnexion)
							.addGap(18)
							.addComponent(btnPdf)))
					.addContainerGap(246, Short.MAX_VALUE))
				.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrenom)
						.addComponent(lblNom)
						.addComponent(lblAdresse)
						.addComponent(lblTelephone)
						.addComponent(lblEmail))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblAffPrenom)
							.addContainerGap())
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblAffNom)
								.addContainerGap())
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(btnEnregistrer)
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
										.addContainerGap(223, Short.MAX_VALUE))
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(adresse, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
										.addGap(18)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(succesForm)
											.addComponent(errorAdresse))
										.addGap(99)))))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNote))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblConnecteComme, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
							.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNoteDe, Alignment.TRAILING)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelephone)
						.addComponent(tel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorTel))
					.addGap(10)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdresse)
						.addComponent(adresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorAdresse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEnregistrer)
						.addComponent(succesForm))
					.addGap(13)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNom)
						.addComponent(lblAffNom))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrenom)
						.addComponent(lblAffPrenom))
					.addGap(52)
					.addComponent(lblActions)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSupprimer)
						.addComponent(btnDeconnexion)
						.addComponent(btnPdf))
					.addGap(47))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		noteCanvas = new NoteCanvas(user.getMoyenneNotes());
		panel.add(noteCanvas, BorderLayout.CENTER);
		contentPanel.setLayout(gl_contentPanel);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnQuitter = new JButton(Langues.getString("MonCompte.btnQuitter.text"));
				btnQuitter.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnQuitter.setActionCommand("OK");
				buttonPane.add(btnQuitter);
				getRootPane().setDefaultButton(btnQuitter);
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
