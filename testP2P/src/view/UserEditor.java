package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Contient le panel de connexion ou d'inscription
 * @author Prudhomme Julien
 *
 */

@SuppressWarnings("serial")
public class UserEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnlBoutons;
	private JButton btnQuitter;
	
	public UserEditor() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 489, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			Connexion connexion = new Connexion();
			contentPanel.add(connexion, "connexion");
		}
		{
			Inscription inscription = new Inscription();
			contentPanel.add(inscription, "inscription");
		}
		{
			pnlBoutons = new JPanel();
			pnlBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBoutons, BorderLayout.SOUTH);
			{
				btnQuitter = new JButton(Langues.getString("UserEditor.btnQuitter.text"));
				btnQuitter.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnQuitter.setActionCommand("Cancel");
				pnlBoutons.add(btnQuitter);
			}
		}
	}
}
