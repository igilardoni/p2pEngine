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
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccordView extends JFrame {

	private JPanel contentPane;
	private JLabel erreurMsg;

	public AccordView(final Objet o) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
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
		
		final JEditorPane texte = new JEditorPane();
		texte.setText("Bonjour " + o.getUserName() + " , je suis interessé(e) par votre annonce \"" + o.getTitre() + "\" ...");
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.NewAccord validator = new controller.NewAccord(o, texte.getText());
				if(validator.validate()) {
					if(validator.process()) {
						dispose();
					}
					else {
						//TODO erreur
					}
				}
				else {
					erreurMsg.setVisible(true);
				}
				
			}
		});
		
		erreurMsg = new JLabel("Le message doit faire plus de 10 caractère");
		erreurMsg.setVisible(false);
		erreurMsg.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAccordsSurUne)
					.addContainerGap(426, Short.MAX_VALUE))
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUtilisateur)
						.addComponent(lblAnnonce))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitreDeLannonce)
						.addComponent(lblUser))
					.addContainerGap(409, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessage)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(erreurMsg)
					.addContainerGap(474, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(texte, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(515, Short.MAX_VALUE)
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMessage)
						.addComponent(erreurMsg))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(texte, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEnvoyer)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
