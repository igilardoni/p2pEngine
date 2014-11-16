package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import model.communications.Accord;

/**
 * Juste avant l'accord
 * @author Prudhomme Julien
 *
 */

@SuppressWarnings("serial")
public class AccordAcceptView extends JFrame {

	private JPanel contentPane;
	private JLabel errorMessage;
	private JLabel lblAccepter;
	private JLabel lblAnnonce;
	private JLabel lblMessage;
	private JButton btnEnvoyer;
	private final JEditorPane texte;

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
		
		lblAccepter = new JLabel(Langues.getString("AccordAcceptView.lblAccepter.text") + a.getFrom());
		
		lblAnnonce = new JLabel(Langues.getString("AccordAcceptView.lblAnnonce.text") + a.getAnnonce());
		
		lblMessage = new JLabel(Langues.getString("AccordAcceptView.lblMessage.text"));
		
		texte = new JEditorPane();
		texte.setText(Langues.getString("AccordAcceptView.editTexte1.text") + a.getFrom() + Langues.getString("AccordAcceptView.editTexte2.text") + a.getAnnonce());
		
		btnEnvoyer = new JButton(Langues.getString("AccordAcceptView.btnEnvoyer.text"));
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
		
		errorMessage = new JLabel(Langues.getString("AccordAcceptView.errorMessage.text"));
		errorMessage.setVisible(false);
		errorMessage.setForeground(Color.RED);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(texte, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
						.addComponent(lblAccepter)
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
					.addComponent(lblAccepter)
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
