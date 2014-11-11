package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import model.UsersManagement;

/**
 * Liste d'amis de la fenetre de conversation
 */

@SuppressWarnings("serial")
public class FriendList extends JPopupMenu {

	private static int WIDTH = 120;
	private static int HEIGHT = 150;
	private UsersManagement users;
	
	private JScrollPane scrollPane;
	private JPanel panel;
	
	public FriendList(UsersManagement users) {
		this.users = users;
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(WIDTH + 20, 5));
		
		
		add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		fillFriends();

	}
	
	/**
	 * Ajoute les amis a la liste pour l'afficher
	 */
	public void fillFriends() {
		panel.removeAll();
		scrollPane.setPreferredSize(new Dimension(WIDTH + 20, 5));
		
		if(users.getConnectedUser() == null) return;
		for(String s: users.getConnectedUser().getFriends()) {
			addFriend(s);
		}
	}
	
	private void addFriend(String friend) {
		JButton btn = new JButton(friend);
		btn.setPreferredSize(new Dimension(WIDTH-20, btn.getPreferredSize().height));
		panel.add(btn);
		Dimension d = scrollPane.getPreferredSize();
		if(d.height < HEIGHT) {
			d.height += btn.getPreferredSize().height;
			scrollPane.setPreferredSize(d);
		}
	}
}
