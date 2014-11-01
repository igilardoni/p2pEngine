package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
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
import model.communications.ChatServiceListener;
import model.communications.MessageData;

import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MessagesPanel extends JFrame implements ChatServiceListener{
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
	public MessagesPanel() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(new Rectangle(0, 0, 600, 300));
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);
		
		sender = new JLabel("Vous pouvez choisir une discussion");
		panel_2.add(sender);
		
		JButton btnCreerUneDiscussion = new JButton("Ajouter une discussion");
		final MessagesPanel this_ = this;
		btnCreerUneDiscussion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewConvers(this_).setVisible(true);
			}
		});
		panel_2.add(btnCreerUneDiscussion);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel_3.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Envoyer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.Messages controller = new controller.Messages(textField.getText(), currentSender);
				if(controller.validate()) {
					addText(System.currentTimeMillis(), Application.getInstance().getUsers().getConnectedUser().getLogin(), textField.getText());
					textField.setText("");
					controller.process();
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
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.SOUTH);
		
		JLabel lblContacts = new JLabel("Contacts");
		panel_6.add(lblContacts);
		
		JButton btnAjouter = new JButton("Ajouter");
		panel_6.add(btnAjouter);

	}
	
	public void setConvers(String userName) {
		this.currentSender = userName;
		sender.setText(userName);
		texte.setText("");
		User user = Application.getInstance().getUsers().getConnectedUser();
		ArrayList<MessageData> msgs = user.getMessages().getMessages(userName);
		
		if(msgs != null) {
			for(MessageData m: msgs) {
				addText(m.getDate(), m.getSender(), m.getContent());
			}
		}
		
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
	}
	
	public void addConversButton(final String s) {
		JButton button = new JButton(s);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setConvers(s);
			}
		});
		convers.add(button);
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
		
	}

	@Override
	public void messageEvent(MessageData msg) {
		if(currentSender != null && currentSender.equals(msg.getSender()) && 
		   Application.getInstance().getUsers().getConnectedUser().getLogin().equals(msg.getTo())) {
			addText(msg.getDate(), msg.getSender(), msg.getContent());
		}
		loadConvers();
		
	}

}
