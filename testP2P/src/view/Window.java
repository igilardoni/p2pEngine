package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import model.User;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.Component;
import javax.swing.Box;

public class Window extends JFrame {

	private JPanel contentPane;
	private JLabel compteLabel;
	private JButton monCompte;
	
	private JButton ajouterAnnonce;
	private JLabel connectezVous;
	private MainPanel mainPanel_;
	private JMenuItem mntmLangue;
	private JToolBar toolBar_1;
	private JButton btnMsg;

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
		
		mntmLangue = new JMenuItem(Messages.getString("Window.mntmLangue.text")); //$NON-NLS-1$
		mntmLangue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LangueSelector().setVisible(true);
			}
		});
		menuOption.add(mntmLangue);
		
		JMenu menuAide = new JMenu(Messages.getString("Window.menuAide.text")); //$NON-NLS-1$
		menuBar.add(menuAide);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		mainPanel_ = new MainPanel();
		contentPane.add(mainPanel_, BorderLayout.CENTER);
		
		JPanel toolBar = new JPanel();
		contentPane.add(toolBar, BorderLayout.NORTH);
		

		ajouterAnnonce = new JButton(Messages.getString("Window.btnAjouterAnnonce")); //$NON-NLS-1$
		ajouterAnnonce.addActionListener(new ActionListener() {
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
		
		connectezVous = new JLabel(Messages.getString("Window.lblConnectezVousPour.text")); //$NON-NLS-1$
		GroupLayout gl_toolBar = new GroupLayout(toolBar);
		gl_toolBar.setHorizontalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addComponent(ajouterAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(connectezVous)
					.addPreferredGap(ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
					.addComponent(compteLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(monCompte))
		);
		gl_toolBar.setVerticalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addGroup(gl_toolBar.createParallelGroup(Alignment.BASELINE)
						.addComponent(ajouterAnnonce)
						.addComponent(monCompte)
						.addComponent(compteLabel)
						.addComponent(connectezVous))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		toolBar.setLayout(gl_toolBar);
		
		toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		contentPane.add(toolBar_1, BorderLayout.SOUTH);
		
		btnMsg = new JButton(Messages.getString("Window.btnFriends.text")); //$NON-NLS-1$
		final MessagesPanel messagePanel = new MessagesPanel();
		Application.getInstance().getChatService().addListener(messagePanel);
		btnMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messagePanel.setVisible(true);
			}
		});
		toolBar_1.add(btnMsg);
	}
	
	public void revalidate() {
		show_account_buttons();
		show_annonce_button();
		mainPanel_.revalidate();
		
		super.revalidate();
	}
	
	private void show_annonce_button() {
		User user = Application.getInstance().getUsers().getConnectedUser();
		if(user != null) {
			ajouterAnnonce.setEnabled(true);
			connectezVous.setVisible(false);
			btnMsg.setEnabled(true);
		}
		else {
			ajouterAnnonce.setEnabled(false);
			connectezVous.setVisible(true);
			btnMsg.setEnabled(false);
		}
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
