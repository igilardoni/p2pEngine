package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import model.Objet;

public class AccordView extends JFrame {

	private JPanel contentPane;

	public AccordView(Objet o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAccordsSurUne = new JLabel("Accords sur une annonce");
		lblAccordsSurUne.setForeground(SystemColor.textHighlight);
		lblAccordsSurUne.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblAnnonce = new JLabel("Annonce :");
		
		JLabel lblTitreDeLannonce = new JLabel(o.getTitre());
		
		JLabel lblUtilisateur = new JLabel("Utilisateur :");
		
		JLabel lblUser = new JLabel(o.getUserName());
		
		JLabel lblMessage = new JLabel("Message:");
		
		JEditorPane dtrpnBonjourJeSuis = new JEditorPane();
		dtrpnBonjourJeSuis.setText("Bonjour " + o.getUserName() + " , je suis interessé(e) par votre annonce \"" + o.getTitre() + "\" ...");
		
		JButton btnEnvoyer = new JButton("Envoyer");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAccordsSurUne)
					.addContainerGap(426, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUtilisateur)
						.addComponent(lblAnnonce))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitreDeLannonce)
						.addComponent(lblUser))
					.addContainerGap(371, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessage)
					.addContainerGap(536, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(dtrpnBonjourJeSuis, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(495, Short.MAX_VALUE)
					.addComponent(btnEnvoyer)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAccordsSurUne)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAnnonce)
						.addComponent(lblTitreDeLannonce))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUtilisateur)
						.addComponent(lblUser))
					.addGap(30)
					.addComponent(lblMessage)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(dtrpnBonjourJeSuis, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEnvoyer)
					.addContainerGap(80, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
