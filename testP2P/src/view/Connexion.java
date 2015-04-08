package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import model.UsersManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;

/**
 * Fenetre de connexion
 */


@SuppressWarnings("serial")
public class Connexion extends JPanel {
	private JTextField login;
	private JPasswordField password;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	
	private JLabel errorLogin;
	private JLabel lblNomCompte;
	private JLabel lblMotDePasse;
	private JLabel lblListeDesComptes;
	
	private JButton btnValider;
	private JButton btnCrerUnNouveau;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Connexion() {
		lblListeDesComptes = new JLabel(Langues.getString("Connexion.lblListeComptes.text"));
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(getUsersNamesList()));
		comboBox.setMaximumRowCount(20);
		
		btnCrerUnNouveau = new JButton(Langues.getString("Connexion.btnNouveauCompte.text"));
		btnCrerUnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel parent = (JPanel) getParent();
				CardLayout cl = (CardLayout) parent.getLayout();
				cl.show(parent, "inscription");
				JDialog dialog = (JDialog) parent.getRootPane().getParent();
				dialog.pack();
			}
		});
		
		lblNomCompte = new JLabel(Langues.getString("Connexion.lblNomCompte.text"));
		
		lblMotDePasse = new JLabel(Langues.getString("Connexion.lblMotDePasse.text"));
		
		login = new JTextField();
		login.setColumns(10);
		
		password = new JPasswordField();
		
		btnValider = new JButton(Langues.getString("Connexion.btnValider.text"));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.Connexion connexion = new controller.Connexion(login.getText(), new String(password.getPassword()));
				if(connexion.validate()) {
					connexion.process();
					Application.getInstance().updateUI();
					JPanel parent = (JPanel) getParent();
					JDialog dialog = (JDialog) parent.getRootPane().getParent();
					dialog.dispose();
				}
				else errorLogin.setVisible(true);
			}
		});
		
		errorLogin = new JLabel(Langues.getString("Connexion.errorLogin.text"));
		errorLogin.setVisible(false);
		errorLogin.setForeground(Color.RED);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblMotDePasse)
						.addComponent(lblNomCompte)
						.addComponent(lblListeDesComptes))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnValider)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCrerUnNouveau))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(password, Alignment.LEADING)
								.addComponent(login, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(errorLogin)))
					.addContainerGap(5, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblListeDesComptes)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCrerUnNouveau))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNomCompte)
						.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorLogin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMotDePasse)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnValider)
					.addContainerGap(155, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	private String[] getUsersNamesList() {
		UsersManagement users = Application.getInstance().getUsers();
		String[] res = new String[users.getUsersNames().length + 1];
		res[0] = "";
		for(int i = 1; i <= users.getUsersNames().length; i++) {
			res[i] = users.getUsersNames()[i-1];
		}
		return res;
	}
}
