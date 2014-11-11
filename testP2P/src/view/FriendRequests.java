package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.User;
import model.communications.MessageData;

/**
 * Affiche les demandes d'amis
 */

@SuppressWarnings("serial")
public class FriendRequests extends JPopupMenu {

	public FriendRequests() {
		
		
		final User user = Application.getInstance().getUsers().getConnectedUser();
		
		for(MessageData m: user.getRequests()) {
			final String sender = m.getSender();
			JMenuItem mntmRequest = new JMenuItem(Langues.getString("FriendRequests.mntmRequest.text") + m.getSender());
			add(mntmRequest);
			mntmRequest.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					user.acceptRequest(sender);
					Application.getInstance().updateUI();
				}
			});
		}
	}
}
