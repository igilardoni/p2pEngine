package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import model.User;
import model.communications.MessageData;

public class FriendRequests extends JPopupMenu {

	/**
	 * Create the panel.
	 */
	public FriendRequests() {
		
		
		final User user = Application.getInstance().getUsers().getConnectedUser();
		
		for(MessageData m: user.getRequests()) {
			final String sender = m.getSender();
			JMenuItem mntmRequest = new JMenuItem("Demande d'ami de " + m.getSender());
			add(mntmRequest);
			mntmRequest.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					user.acceptRequest(sender);
					Application.getInstance().updateUI();
				}
				
			});
		}

	}

}
