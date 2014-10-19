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

public class SuppCompteConfirm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JLabel errorPass;
	private JDialog parent;

	/**
	 * Create the dialog.
	 */
	public SuppCompteConfirm(JDialog parent_) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.parent = parent_;
		setBounds(100, 100, 450, 249);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblSupprimerVotreCompte = new JLabel(Messages.getString("SuppCompteConfirm.lblSupprimerCompte.text"));
		lblSupprimerVotreCompte.setForeground(SystemColor.textHighlight);
		lblSupprimerVotreCompte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblEtesvousSurDe = new JLabel(Messages.getString("SuppCompteConfirm.lblQuestion.text"));
		
		JLabel lblCetteOprationEst = new JLabel(Messages.getString("SuppCompteConfirm.lblIrreversible.text"));
		
		JLabel lblEntrezVotreMot = new JLabel(Messages.getString("SuppCompteConfirm.lblConfirmer.text"));
		
		JLabel lblMotDePasse = new JLabel(Messages.getString("SuppCompteConfirm.lblMotDePasse.text"));
		
		passwordField = new JPasswordField();
		
		JButton btnSupprimer = new JButton(Messages.getString("SuppCompteConfirm.btnSupprimer.text"));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initError();
				controller.SuppCompteConfirm confirm = new controller.SuppCompteConfirm(new String(passwordField.getPassword()));
				if(confirm.validate()) {
					confirm.process();
					parent.dispose();
					dispose();
					Application.getInstance().updateUI();
				}
				else showError(confirm);
			}
		});
		
		errorPass = new JLabel(Messages.getString("SuppCompteConfirm.lblErrorMdP.text"));
		errorPass.setForeground(Color.RED);
		errorPass.setVisible(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSupprimerVotreCompte)
					.addContainerGap(259, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEtesvousSurDe)
					.addContainerGap(354, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCetteOprationEst)
					.addContainerGap(354, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEntrezVotreMot)
					.addContainerGap(85, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMotDePasse)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(errorPass)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSupprimer)))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSupprimerVotreCompte)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEtesvousSurDe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCetteOprationEst)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEntrezVotreMot)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMotDePasse)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSupprimer))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorPass)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton(Messages.getString("SuppCompteConfirm.btnAnnuler.text"));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
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
