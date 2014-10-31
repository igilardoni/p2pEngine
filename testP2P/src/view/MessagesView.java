package view;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MessagesView extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private JPanel panel;
	
	public void addConversation(String u) {
		final JToggleButton tglbtnAmi = new JToggleButton(u);
		final MessageView msg = new MessageView();
		tglbtnAmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				msg.show(tglbtnAmi, 0, -200);
			}
		});
		panel.add(tglbtnAmi);
	}

	public MessagesView() {

		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(null);
		add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		addConversation("ami1");
		addConversation("ami2");

	}

}
