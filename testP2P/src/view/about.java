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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class about extends JFrame {

	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public about() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblLogicielDvelopperPar = new JLabel("Logiciel développer par l'équipe Pire du peer");
		
		JLabel lblChefDeProjet = new JLabel("Chef de projet: Prudhomme Julien");
		lblChefDeProjet.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblMembresDeLquipe = new JLabel("Membres de l'équipe:\r\n");
		lblMembresDeLquipe.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblSarahBoukrisMariana = new JLabel("Sarah BOUKRIS, Mariana ANDUJAR,  Ismael Cussac");
		
		JLabel lblJulienRemySbastien = new JLabel("Julien REMY, Sébastien GOUVERNEUR");
		
		JLabel lblThisProductIncludes = new JLabel("This product includes software developed by Sun Microsystems, Inc. for JXTA(TM) technology.");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAbout)
						.addComponent(lblLogicielDvelopperPar)
						.addComponent(lblChefDeProjet)
						.addComponent(lblMembresDeLquipe, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblJulienRemySbastien)
						.addComponent(lblSarahBoukrisMariana)
						.addComponent(lblThisProductIncludes))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAbout)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogicielDvelopperPar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblChefDeProjet)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMembresDeLquipe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblJulienRemySbastien)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSarahBoukrisMariana)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblThisProductIncludes)
					.addContainerGap(81, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
