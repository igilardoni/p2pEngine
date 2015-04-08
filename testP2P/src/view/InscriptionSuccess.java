package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Apparait en cas de succes lors d'une nouvelle inscription
 * @author
 *
 */
@SuppressWarnings("serial")
public class InscriptionSuccess extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JLabel lblTitre;
	private JLabel lblSucces;
	private JLabel lblConnecte;
	
	private JButton btnContinuer;
	
	public InscriptionSuccess() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblTitre = new JLabel(Langues.getString("InscriptionSuccess.lblReussi.text"));
		lblTitre.setForeground(SystemColor.textHighlight);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		lblSucces = new JLabel(Langues.getString("InscriptionSuccess.lblSucces.text"));
		
		lblConnecte = new JLabel(Langues.getString("InscriptionSuccess.lblSeConnecter.text"));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblTitre)
					.addContainerGap(306, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblSucces)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblConnecte)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblTitre)
					.addGap(9)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSucces)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblConnecte)
					.addContainerGap(128, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnContinuer = new JButton(Langues.getString("InscriptionSuccess.btnContinuer.text"));
				btnContinuer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnContinuer.setActionCommand("OK");
				buttonPane.add(btnContinuer);
				getRootPane().setDefaultButton(btnContinuer);
			}
		}
	}
}
