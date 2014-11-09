package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import model.User;
import model.communications.MessageServiceListener;
import model.communications.MessageData;

import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class MessagesPanel extends JDialog implements MessageServiceListener{
	private JTextField textField;
	private JTextPane texte;
	private HTMLEditorKit kit;
    private HTMLDocument doc;
    private String currentSender = null;
    private JLabel sender;
    private JPanel convers;

	/**
	 * Create the panel.
	 */
	public MessagesPanel(JFrame w) {
		super(w);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 789, 394);
		

		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCreerUneDiscussion = new JButton(Messages.getString("MessagesPanel.btnAjouterDiscussion.text"));
		final MessagesPanel this_ = this;
		btnCreerUneDiscussion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewConvers(this_).setVisible(true);
			}
		});
		panel_2.add(btnCreerUneDiscussion);
		
		sender = new JLabel(Messages.getString("MessagesPanel.lblChoisirDiscussion.text"));
		panel_2.add(sender);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel_3.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton(Messages.getString("MessagesPanel.btnEnvoyer.text"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.Messages controller = new controller.Messages(textField.getText(), currentSender);
				if(controller.validate()) {
					addText(System.currentTimeMillis(), Application.getInstance().getUsers().getConnectedUser().getLogin(), textField.getText());
					textField.setText("");
					controller.process();
					Application.getInstance().getUsers().getConnectedUser().getMessages().viewUser(currentSender);
				}
			}
		});
		panel_3.add(btnNewButton, BorderLayout.EAST);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		texte = new JTextPane();
		kit = new HTMLEditorKit();
	    doc = new HTMLDocument();
	    texte.setEditorKit(kit);
	    texte.setDocument(doc);
		
		texte.setEditable(false);
		scrollPane.setViewportView(texte);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(200, 10));
		getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		
		JPanel panel_4 = new JPanel();
		scrollPane_1.setViewportView(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		convers = new JPanel();
		panel_4.add(convers, BorderLayout.CENTER);
		convers.setLayout(new BoxLayout(convers, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.SOUTH);
		
		JLabel lblContacts = new JLabel(Messages.getString("MessagesPanel.lblContacts.text"));
		panel_6.add(lblContacts);
		
		JButton btnAjouter = new JButton(Messages.getString("MessagesPanel.btnAjouter.text"));
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddFriend().setVisible(true);
			}
		});
		panel_6.add(btnAjouter);

	}
	
	public void setConvers(String userName) {
		this.currentSender = userName;
		sender.setText("Vous discutez avec "+ userName);
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
	
	public void loadConvers() {
		convers.removeAll();
		
		for(String s : Application.getInstance().getUsers().getConnectedUser().getMessages().getConvers()) {
			addConversButton(s);
		}
		
		revalidate();
		repaint();
	}
	
	public void addConversButton(final String s) {
		JPanel wrapperButton = new JPanel();
		wrapperButton.setLayout(new BoxLayout(wrapperButton, BoxLayout.X_AXIS));
		JButton button = new JButton(s + "(" 
				+ Application.getInstance().getUsers().getConnectedUser().getMessages().getNumberUserMessage(s) + ")");
		button.addActionListener(new ActionListener() {
			
			@Override
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

			@Override
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
	
	@Override
	public void setVisible(boolean visible) {
		loadConvers();
		super.setVisible(visible);
	}
	
	public void addText(long date, String userName, String content) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date d = new Date(date);
		String dateString = format.format(d);
		
		try {
			kit.insertHTML(doc, doc.getLength(), "<font color=\"#bbbbbb\">" + dateString + "</font>    <b>" + userName + "</b>: " + content, 0, 0, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		texte.setCaretPosition(doc.getLength());
		
		//revalidate();
		
		
	}

	@Override
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
