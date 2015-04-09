package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.Accord;
import model.Accords;
import model.User;

/**
 * Liste de tous les accords deja passes
 * @author
 *
 */

@SuppressWarnings("serial")
public class AccordList extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JLabel lblAccords;

	public AccordList() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 727, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new APanel();
		scrollPane.setViewportView(panel);
		
		loadAccords();
		
		lblAccords = new JLabel(Langues.getString("AccordList.lblAccords.text"));
		lblAccords.setForeground(SystemColor.textHighlight);
		lblAccords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblAccords, BorderLayout.NORTH);
	}
	
	private AccordList getThis() {
		return this;
	}
	
	public void revalidate() {
		loadAccords();
		repaint();
		super.revalidate();
	}
	
	public void loadAccords() {
		User user = Application.getInstance().getUsers().getConnectedUser();
		Accords accords = Application.getInstance().getUsers().getConnectedUser().getAccords();
		panel.removeAll();
		for(Accord a: accords.getAccords()) {
			if(!a.getFrom().equals(user.getLogin()) && !a.isAccepted()) {
				panel.add(new AccordNewPanel(a, getThis()));
			}
			else if(a.isAccepted()) {
				panel.add(new AccordAcceptPanel(a, getThis()));
			}
			
		}
		
	}

}
