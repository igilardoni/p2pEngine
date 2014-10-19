package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JToolBar;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import model.User;

public class Window extends JFrame {

	private JPanel contentPane;
	private JLabel compteLabel;
	private JButton monCompte;

	/**
	 * Create the frame.
	 */
	public Window() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 481);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuOption = new JMenu(Messages.getString("Window.menuOptions.text")); //$NON-NLS-1$
		menuBar.add(menuOption);
		
		JMenu menuAide = new JMenu(Messages.getString("Window.menuAide.text")); //$NON-NLS-1$
		menuBar.add(menuAide);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		MainPanel mainPanel_ = new MainPanel();
		contentPane.add(mainPanel_, BorderLayout.CENTER);
		
		JPanel toolBar = new JPanel();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton(Messages.getString("Window.btnAjouterAnnonce")); //$NON-NLS-1$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AnnonceEditor().setVisible(true);
			}
		});
		
		monCompte = new JButton(Messages.getString("Window.btnMonCompte.text")); //$NON-NLS-1$
		monCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User connectedUser = Application.getInstance().getUsers().getConnectedUser();
				if(connectedUser == null) {
					UserEditor userEditor = new UserEditor();
					userEditor.setVisible(true);
				}
				else {
					MonCompte moncompte = new MonCompte();
					moncompte.setVisible(true);
				}
				
			}
		});
		
		compteLabel = new JLabel(Messages.getString("Window.lblPasConnecte.text")); //$NON-NLS-1$
		GroupLayout gl_toolBar = new GroupLayout(toolBar);
		gl_toolBar.setHorizontalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED, 562, Short.MAX_VALUE)
					.addComponent(compteLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(monCompte))
		);
		gl_toolBar.setVerticalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addGroup(gl_toolBar.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(monCompte)
						.addComponent(compteLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		toolBar.setLayout(gl_toolBar);
	}
	
	public void revalidate() {
		show_account_buttons();
		super.revalidate();
	}
	
	private void show_account_buttons() {
		User user = Application.getInstance().getUsers().getConnectedUser();
		if(user != null) {
			compteLabel.setText(Messages.getString("Window.txtConnecteComme.text") + user.getLogin());
			monCompte.setText(Messages.getString("Window.txtMonCompte.text"));
		}
		else {
			compteLabel.setText(Messages.getString("Window.txtPasConnecte.text"));
			monCompte.setText(Messages.getString("Window.txtSeConnecter.text"));
		}
	}
	
}
