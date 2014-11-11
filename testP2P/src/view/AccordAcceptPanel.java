package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import controller.GeneratePdfContrat;
import model.Accord;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Affiche le succes de l'accord
 * @author 
 *
 */
@SuppressWarnings("serial")
public class AccordAcceptPanel extends JPanel {

	
	private JLabel lblAccordPasse;
	private JButton btnNoter;
	private JButton btnContrat;
	
	public AccordAcceptPanel(final Accord a, final AccordList parent) {
		
		lblAccordPasse = new JLabel(Langues.getString("AccordAcceptPanel.lblAccordPasse1.text") + a.getAnnonce() + Langues.getString("AccordAcceptPanel.lblAccordPasse2.text"));
		
		JSeparator separator = new JSeparator();
		

		btnNoter = new JButton(Langues.getString("AccordAcceptPanel.btnNoter.text"));
		btnNoter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RateView(a, parent).setVisible(true);
			}
		});
		if(a.isRated()) {
			btnNoter.setEnabled(false);
		}
		btnContrat = new JButton(Langues.getString("AccordAcceptPanel.btnContrat.text"));
		btnContrat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				GeneratePdfContrat validator = new GeneratePdfContrat(0);
				if(validator.validate()) {
					validator.process();
					Application.getInstance().updateUI();
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAccordPasse)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNoter)
							.addComponent(btnContrat))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 659, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAccordPasse)
						.addComponent(btnNoter)
						.addComponent(btnContrat))
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		setLayout(groupLayout);

	}
}
