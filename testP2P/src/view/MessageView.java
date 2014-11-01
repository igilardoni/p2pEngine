package view;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;

public class MessageView extends JPopupMenu {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public MessageView() {
		setPopupSize(new Dimension(200, 200));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Envoyer");
		panel.add(btnNewButton, BorderLayout.EAST);

	}

}
