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
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Panel de suppression d'un compte courrant
 * @author
 *
 */

@SuppressWarnings("serial")
public class SuppCompteConfirm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField password;
	private JLabel errorPass;
	private JDialog parent;
	private JLabel lblSupprimerCompte;
	private JLabel lblQuestion;
	private JLabel lblIrreversible;
	private JLabel lblConfirmer;
	private JLabel lblMotDePasse;
	private JPanel buttonPane;
	private JButton btnAnnuler;
	private JButton btnSupprimer;

	public SuppCompteConfirm(JDialog parent_) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.parent = parent_;
		setBounds(100, 100, 450, 249);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblSupprimerCompte = new JLabel(Langues.getString("SuppCompteConfirm.lblSupprimerCompte.text"));
		lblSupprimerCompte.setForeground(SystemColor.textHighlight);
		lblSupprimerCompte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		
		lblQuestion = new JLabel(Langues.getString("SuppCompteConfirm.lblQuestion.text"));
		
		lblIrreversible = new JLabel(Langues.getString("SuppCompteConfirm.lblIrreversible.text"));
		
		lblConfirmer = new JLabel(Langues.getString("SuppCompteConfirm.lblConfirmer.text"));
		
		lblMotDePasse = new JLabel(Langues.getString("SuppCompteConfirm.lblMotDePasse.text"));
		
		password = new JPasswordField();
		
		btnSupprimer = new JButton(Langues.getString("SuppCompteConfirm.btnSupprimer.text"));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initError();
				controller.SuppCompteConfirm confirm = new controller.SuppCompteConfirm(new String(password.getPassword()));
				if(confirm.validate()) {
					confirm.process();
					parent.dispose();
					dispose();
					Application.getInstance().updateUI();
				}
				else showError(confirm);
			}
		});
		
		errorPass = new JLabel(Langues.getString("SuppCompteConfirm.lblErrorMdP.text"));
		errorPass.setForeground(Color.RED);
		errorPass.setVisible(false);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSupprimerCompte)
					.addContainerGap(259, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion)
					.addContainerGap(354, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIrreversible)
					.addContainerGap(354, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConfirmer)
					.addContainerGap(85, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMotDePasse)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(errorPass)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(password, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSupprimer)))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSupprimerCompte)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblQuestion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblIrreversible)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblConfirmer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMotDePasse)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSupprimer))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorPass)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAnnuler = new JButton(Langues.getString("SuppCompteConfirm.btnAnnuler.text"));
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnAnnuler.setActionCommand("Cancel");
				buttonPane.add(btnAnnuler);
			}
		}
	}
	
	private void initError() {
		errorPass.setVisible(false);
	}
	
	private void showError(controller.SuppCompteConfirm confirm) {
		if(confirm.errorPassword) errorPass.setVisible(true);
	}
}
