package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import model.User;
import model.communications.MessageData;
import model.communications.MessageServiceListener;
/**
 * Fentere principale de l'outils de conversation 
 * @author
 *
 */
@SuppressWarnings("serial")
public class MessagesPanel extends JDialog implements MessageServiceListener{
	private JTextField message;
	private JTextPane texte;
	private HTMLEditorKit kit;
    private HTMLDocument doc;
    private String currentSender = null;
    
	private JLabel lblChoisir;
	private JLabel lblContacts;
	private JPanel convers;
	private JPanel pnlDiscussion;
	private JPanel pnlHaut;
	private JPanel pnlBas;
	private JPanel pnlDroite;
	private JPanel pnlAjouterContact;
	private JPanel pnlContacts;
	
	private JScrollPane scrpnlMessages;
	private JScrollPane scrpnlContacts;
	
	private JButton btnAjouter;
	private JButton btnCreerDiscussion;
	private JButton btnEnvoyer;
    
	public MessagesPanel(JFrame w) {
		super(w);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 789, 394);
		

		getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlDiscussion = new JPanel();
		getContentPane().add(pnlDiscussion, BorderLayout.CENTER);
		pnlDiscussion.setLayout(new BorderLayout(0, 0));
		
		pnlHaut = new JPanel();
		pnlDiscussion.add(pnlHaut, BorderLayout.NORTH);
		pnlHaut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnCreerDiscussion = new JButton(Langues.getString("MessagesPanel.btnCreerDiscussion.text"));
		final MessagesPanel this_ = this;
		btnCreerDiscussion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewConvers(this_).setVisible(true);
			}
		});
		pnlHaut.add(btnCreerDiscussion);
		
		lblChoisir = new JLabel(Langues.getString("MessagesPanel.lblChoisirDiscussion.text"));
		pnlHaut.add(lblChoisir);
		
		pnlBas = new JPanel();
		pnlDiscussion.add(pnlBas, BorderLayout.SOUTH);
		pnlBas.setLayout(new BorderLayout(0, 0));
		
		message = new JTextField();
		pnlBas.add(message, BorderLayout.CENTER);
		message.setColumns(10);
		
		btnEnvoyer = new JButton(Langues.getString("MessagesPanel.btnEnvoyer.text"));
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.Messages controller = new controller.Messages(message.getText(), currentSender);
				if(controller.validate()) {
					addText(System.currentTimeMillis(), Application.getInstance().getUsers().getConnectedUser().getLogin(), message.getText());
					message.setText("");
					controller.process();
					Application.getInstance().getUsers().getConnectedUser().getMessages().viewUser(currentSender);
				}
			}
		});
		pnlBas.add(btnEnvoyer, BorderLayout.EAST);
		
		scrpnlMessages = new JScrollPane();
		pnlDiscussion.add(scrpnlMessages, BorderLayout.CENTER);
		
		texte = new JTextPane();
		kit = new HTMLEditorKit();
	    doc = new HTMLDocument();
	    texte.setEditorKit(kit);
	    texte.setDocument(doc);
		
		texte.setEditable(false);
		scrpnlMessages.setViewportView(texte);
		
		pnlDroite = new JPanel();
		pnlDroite.setPreferredSize(new Dimension(200, 10));
		getContentPane().add(pnlDroite, BorderLayout.EAST);
		pnlDroite.setLayout(new BorderLayout(0, 0));
		
		scrpnlContacts = new JScrollPane();
		pnlDroite.add(scrpnlContacts);
		
		pnlContacts = new JPanel();
		scrpnlContacts.setViewportView(pnlContacts);
		pnlContacts.setLayout(new BorderLayout(0, 0));
		
		convers = new JPanel();
		pnlContacts.add(convers, BorderLayout.CENTER);
		convers.setLayout(new BoxLayout(convers, BoxLayout.Y_AXIS));
		
		pnlAjouterContact = new JPanel();
		pnlContacts.add(pnlAjouterContact, BorderLayout.SOUTH);
		
		lblContacts = new JLabel(Langues.getString("MessagesPanel.lblContacts.text"));
		pnlAjouterContact.add(lblContacts);
		
		btnAjouter = new JButton(Langues.getString("MessagesPanel.btnAjouter.text"));
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddFriend().setVisible(true);
			}
		});
		pnlAjouterContact.add(btnAjouter);
	}
	
	public void setConvers(String userName) {
		this.currentSender = userName;
		lblChoisir.setText(Langues.getString("MessagesPanel.lblDiscutezAvec.text")+ userName);
		texte.setText("");
		User user = Application.getInstance().getUsers().getConnectedUser();
		ArrayList<MessageData> msgs = user.getMessages().getMessages(userName);
		user.getMessages().viewUser(userName);
		
		if(msgs != null) {
			for(MessageData m: msgs) {
				addText(m.getDate(), m.getSender(), m.getContent());
			}
		}
		loadConvers();
		
	}
	
	public void setNewConvers(String userName) {
		setConvers(userName);
		loadConvers();
	}
	
	/**
	 * Charge les conversations en cours
	 */
	public void loadConvers() {
		convers.removeAll();
		
		for(String s : Application.getInstance().getUsers().getConnectedUser().getMessages().getConvers()) {
			addConversButton(s);
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Ajoute un bouton pour chaque conversation en cours pour switcher entre elles
	 */
	public void addConversButton(final String s) {
		JPanel wrapperButton = new JPanel();
		wrapperButton.setLayout(new BoxLayout(wrapperButton, BoxLayout.X_AXIS));
		JButton button = new JButton(s + "(" 
				+ Application.getInstance().getUsers().getConnectedUser().getMessages().getNumberUserMessage(s) + ")");
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setConvers(s);
			}
		});
		if(currentSender != null && currentSender.equals(s)) {
			button.setBackground(Color.green);
		}
		wrapperButton.setPreferredSize(button.getPreferredSize());
		wrapperButton.add(button, BorderLayout.CENTER);
		JButton closeButton = new JButton("Fermer");
		closeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				controller.DeleteConvers validator = new controller.DeleteConvers(s);
				if(validator.validate()) {
					validator.process();
					loadConvers();
				}
			}
			
		});
		wrapperButton.add(closeButton, BorderLayout.EAST);
		convers.add(wrapperButton);
	}
	
	public void setVisible(boolean visible) {
		loadConvers();
		super.setVisible(visible);
	}
	
	/**
	 * Ajoute un message
	 */
	public void addText(long date, String userName, String content) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date d = new Date(date);
		String dateString = format.format(d);
		
		try {
			kit.insertHTML(doc, doc.getLength(), "<font color=\"#bbbbbb\">" + dateString + "</font>    <b>" + userName + "</b>: " + content, 0, 0, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		texte.setCaretPosition(doc.getLength());
		
	}
	
	/**
	 * Notifie visuellement l'utilisateur s'il recoit un nouveau message
	 */
	public void messageEvent(MessageData msg) {
		if(!this.isVisible()) return;
		if(currentSender != null && currentSender.equals(msg.getSender()) && 
		   Application.getInstance().getUsers().getConnectedUser().getLogin().equals(msg.getTo())) {
			addText(msg.getDate(), msg.getSender(), msg.getContent());
			
			if(this.isVisible())
			Application.getInstance().getUsers().getConnectedUser().getMessages().viewUser(msg.getSender());
		}
		loadConvers();
	}
}
