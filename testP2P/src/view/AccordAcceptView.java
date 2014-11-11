package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import model.Accord;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccordAcceptView extends JFrame {

	private JPanel contentPane;
	private JLabel errorMessage;

	/**
	 * Create the frame.
	 */
	public AccordAcceptView(final Accord a, final  AccordList parent) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblVousAllezAccepter = new JLabel("Vous allez accepter l'accord de " + a.getFrom());
		
		JLabel lblAnnonce = new JLabel("Annonce : " + a.getAnnonce());
		
		JLabel lblMessage = new JLabel("Message :");
		
		final JEditorPane texte = new JEditorPane();
		texte.setText("Bonjour " + a.getFrom() + ", j'accepte l'accord au sujet de l'annonce : " + a.getAnnonce());
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.AcceptAccord validator = new controller.AcceptAccord(a, texte.getText());
				if(validator.validate()) {
					if(!validator.process()) {
						//erreur
					}
					else {
						parent.revalidate();
						dispose();
					}
				}
				else {
					errorMessage.setVisible(true);
				}
			}
		});
		
		errorMessage = new JLabel("Le message doit faire plus de 10 caractères");
		errorMessage.setVisible(false);
		errorMessage.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(texte, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
						.addComponent(lblVousAllezAccepter)
						.addComponent(lblAnnonce)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMessage)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(errorMessage))
						.addComponent(btnEnvoyer, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblVousAllezAccepter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMessage)
						.addComponent(errorMessage))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(texte, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEnvoyer)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
