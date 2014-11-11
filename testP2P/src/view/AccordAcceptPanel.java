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

public class AccordAcceptPanel extends JPanel {

	public AccordAcceptPanel(final Accord a, final AccordList parent) {
		
		JLabel lblVousAvezReu = new JLabel("L'accord au sujet de l'annonce " + a.getAnnonce() + " a été accepté");
		
		JSeparator separator = new JSeparator();
		
		JButton btnNoter = new JButton("Noter");
		btnNoter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RateView(a, parent).setVisible(true);
			}
		});
		if(a.isRated()) {
			btnNoter.setEnabled(false);
		}
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblVousAvezReu)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNoter))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 659, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVousAvezReu)
						.addComponent(btnNoter))
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		setLayout(groupLayout);

	}
}
