package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.User;
import model.communications.MessageData;
import model.communications.MessageServiceListener;
import javax.swing.Box;

/**
 * Fenetre principale du programme
 * @author 
 *
 */

@SuppressWarnings("serial")
public class Window extends JFrame implements MessageServiceListener{

	private JPanel contentPane;
	private MainPanel mainPanel_;
	private JButton btnMonCompte;
	private JButton ajouterAnnonce;
	private JButton btnMsg;
	private JButton panier;
	private JButton requetecontact;
	private JLabel compteLabel;
	private JLabel connectezVous;
	private JMenuBar menuBar;
	private JMenu menuOption;
	private JMenu menuAide ;
	private JMenuItem mntmLangue;
	private JMenuItem mntmRecherche;
	private JToolBar chatBar;
	private JPanel infoBar;
	
	private MessagesPanel messagePanel;
	private JButton btnAccords;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	
	public Window() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 481);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuOption = new JMenu(Langues.getString("Window.menuOptions.text")); //$NON-NLS-1$
		menuBar.add(menuOption);
		
		mntmLangue = new JMenuItem(Langues.getString("Window.mntmLangue.text")); //$NON-NLS-1$
		mntmLangue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LangueSelector().setVisible(true);
			}
		});
		menuOption.add(mntmLangue);
		
		menuAide = new JMenu(Langues.getString("Window.menuAide.text")); //$NON-NLS-1$
		menuBar.add(menuAide);
		
		mntmRecherche = new JMenuItem(Langues.getString("Window.mntmRecherche.text")); //$NON-NLS-1$
		mntmRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RechercheInfo().setVisible(true);
			}
		});
		menuAide.add(mntmRecherche);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		mainPanel_ = new MainPanel();
		contentPane.add(mainPanel_, BorderLayout.CENTER);
		
		infoBar = new JPanel();
		contentPane.add(infoBar, BorderLayout.NORTH);
		

		ajouterAnnonce = new JButton(Langues.getString("Window.btnAjouterAnnonce")); //$NON-NLS-1$
		ajouterAnnonce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AnnonceEditor().setVisible(true);
			}
		});
		
		btnMonCompte = new JButton(Langues.getString("Window.btnMonCompte.text")); //$NON-NLS-1$
		btnMonCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User connectedUser = Application.getInstance().getUsers().getConnectedUser();
				if(connectedUser == null) {
					UserEditor userEditor = new UserEditor();
					userEditor.setVisible(true);
				}
				else {
					MonCompte monCompte = new MonCompte();
					monCompte.setVisible(true);
				}
			}
		});

		compteLabel = new JLabel(Langues.getString("Window.lblPasConnecte.text")); //$NON-NLS-1$
		
		connectezVous = new JLabel(Langues.getString("Window.lblConnectezVousPour.text")); //$NON-NLS-1$
		
		panier = new JButton(Langues.getString("Window.btnPanier.text")); //$NON-NLS-1$
		panier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Panier().setVisible(true);
			}
		});
		GroupLayout gl_toolBar = new GroupLayout(infoBar);
		gl_toolBar.setHorizontalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addComponent(ajouterAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(connectezVous)
					.addPreferredGap(ComponentPlacement.RELATED, 318, Short.MAX_VALUE)
					.addComponent(compteLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panier)
					.addGap(10)
					.addComponent(btnMonCompte))
		);
		gl_toolBar.setVerticalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addGroup(gl_toolBar.createParallelGroup(Alignment.BASELINE)
						.addComponent(ajouterAnnonce)
						.addComponent(btnMonCompte)
						.addComponent(compteLabel)
						.addComponent(connectezVous)
						.addComponent(panier))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		infoBar.setLayout(gl_toolBar);
		
		chatBar = new JToolBar();
		chatBar.setFloatable(false);
		contentPane.add(chatBar, BorderLayout.SOUTH);
		
		btnMsg = new JButton(Langues.getString("Window.btnFriends.text")); //$NON-NLS-1$
		messagePanel = new MessagesPanel(this);
		
		Application.getInstance().getChatService().addListener(messagePanel);
		btnMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messagePanel.setVisible(true);
				btnMsg.setBackground(null);
				btnMsg.setText(Langues.getString("Window.btnMsg.text"));
			}
		});
		chatBar.add(btnMsg);
		
		requetecontact = new JButton(Langues.getString("Window.btnRequtesContacts.text")); //$NON-NLS-1$
		Application.getInstance().getFriendRequestService().addListener(new MessageServiceListener()  {
			
			public void messageEvent(MessageData msg) {
				User u = Application.getInstance().getUsers().getConnectedUser();
				if(msg.getTo().equals(u.getLogin())) {
					requetecontact.setText(Langues.getString("Window.btnRequtesContacts.text") + " (" + u.getRequests().size() + ")");
				}
			}
		});
		requetecontact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FriendRequests fr = new FriendRequests();
				fr.show((Component) arg0.getSource(), 0, -fr.getPreferredSize().height);
			}
		});
		
		horizontalStrut = Box.createHorizontalStrut(20);
		chatBar.add(horizontalStrut);
		chatBar.add(requetecontact);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		chatBar.add(horizontalStrut_1);
		
		btnAccords = new JButton(Langues.getString("Window.btnAccords.text")); //$NON-NLS-1$
		btnAccords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AccordList().setVisible(true);
			}
		});
		chatBar.add(btnAccords);
		
		Application.getInstance().getChatService().addListener(this);
		
	}
	
	public void revalidate() {
		show_account_buttons();
		show_annonce_button();
		mainPanel_.revalidate();
		chatBar.revalidate();
		
		super.revalidate();
	}
	
	private void show_annonce_button() {
		User user = Application.getInstance().getUsers().getConnectedUser();
		if(user != null) {
			ajouterAnnonce.setEnabled(true);
			connectezVous.setVisible(false);
			panier.setVisible(true);
			btnMsg.setEnabled(true);
			requetecontact.setEnabled(true);
			requetecontact.setText(Langues.getString("Window.btnRequtesContacts.text") + " (" + user.getRequests().size() + ")");
			
		}
		else {
			ajouterAnnonce.setEnabled(false);
			connectezVous.setVisible(true);
			panier.setVisible(false);
			btnMsg.setEnabled(false);
			requetecontact.setEnabled(false);
			requetecontact.setText(Langues.getString("Window.btnRequtesContacts.text"));
		}
	}
	
	private void show_account_buttons() {
		User user = Application.getInstance().getUsers().getConnectedUser();
		if(user != null) {
			compteLabel.setText(Langues.getString("Window.txtConnecteComme.text") + user.getLogin());
			btnMonCompte.setText(Langues.getString("Window.btnMonCompte.text"));
		}
		else {
			compteLabel.setText(Langues.getString("Window.txtPasConnecte.text"));
			btnMonCompte.setText(Langues.getString("Window.btnSeConnecter.text"));
		}
	}

	public void messageEvent(MessageData msg) {
		if(Application.getInstance().getUsers().getConnectedUser() == null) return;
		if(messagePanel.isVisible()) return;
		if(msg.getTo().equals(Application.getInstance().getUsers().getConnectedUser().getLogin())) {
			btnMsg.setText(Langues.getString("Window.btnMsg.text")+ " ("+Application.getInstance().getUsers().getConnectedUser().getMessages().getNumberNewMsgs()+")");
			btnMsg.setBackground(Color.green);
		}
	}
	
	public MessagesPanel getMessagePanel() {
		return this.messagePanel;
	}
}
