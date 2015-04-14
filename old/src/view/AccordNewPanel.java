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

/**
 * Nouvelle demande d'accord
 * @author 
 */

@SuppressWarnings("serial")
public class AccordNewPanel extends JPanel {
	private JLabel lblNouvelAccord;
	private JTextArea textArea;
	private JButton btnAccepter;
	private JButton btnRefuser;
	private JButton btnDiscuter;
	private JLabel lblAnnonce;

	public AccordNewPanel(final Accord a, final AccordList parent) {
		
		lblNouvelAccord = new JLabel(Langues.getString("AccordNewPanel.lblNouvelAccord.text") + a.getFrom());
		
		textArea = new JTextArea(a.getMessageFrom());
		textArea.setEditable(false);
		
		btnAccepter = new JButton(Langues.getString("AccordNewPanel.btnAccepter.text"));
		btnAccepter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AccordAcceptView(a, parent).setVisible(true);
			}
		});
		

		btnRefuser = new JButton(Langues.getString("AccordNewPanel.btnRefuser.text"));
		btnRefuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.DeclineAccord validator = new controller.DeclineAccord(a);
				if(validator.validate()) {
					if(!validator.process()) {
						//erreur
					}
					else {
						parent.revalidate();
					}
				}
			}
		});
		
		btnDiscuter = new JButton(Langues.getString("AccordNewPanel.btnDiscuter.text"));
		btnDiscuter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.NewConvers validator = new controller.NewConvers(a.getFrom());
				if(validator.validate()) {
					validator.process();
					Application.getInstance().openConvers(a.getFrom());
				}
				
			}
		});
		
		JSeparator separator = new JSeparator();
		
		lblAnnonce = new JLabel(Langues.getString("AccordNewPanel.lblAnnonce.text") + a.getAnnonce());
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNouvelAccord))
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
					.addComponent(lblNouvelAccord)
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
