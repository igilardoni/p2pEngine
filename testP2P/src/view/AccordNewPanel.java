package view;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JSeparator;

import model.Accord;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccordNewPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AccordNewPanel(final Accord a, final AccordList parent) {
		
		JLabel lblVousAvezReu = new JLabel("Vous avez reçu une demande d'accord de " + a.getFrom());
		
		JTextArea textArea = new JTextArea(a.getMessageFrom());
		textArea.setEditable(false);
		
		JButton btnAccepter = new JButton("Accepter");
		btnAccepter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AccordAcceptView(a, parent).setVisible(true);
			}
		});
		
		JButton btnRefuser = new JButton("Refuser");
		
		JButton btnDiscuter = new JButton("Discuter");
		
		JSeparator separator = new JSeparator();
		
		JLabel lblAnnonce = new JLabel("Annonce : " + a.getAnnonce());
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblVousAvezReu))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(202)
							.addComponent(btnAccepter)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRefuser)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiscuter))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 636, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAnnonce))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 659, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblVousAvezReu)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAccepter)
						.addComponent(btnRefuser)
						.addComponent(btnDiscuter))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		setLayout(groupLayout);

	}
}
