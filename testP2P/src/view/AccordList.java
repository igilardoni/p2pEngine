package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import model.Accord;
import model.Accords;
import model.User;

import java.awt.Font;
import java.awt.SystemColor;

public class AccordList extends JFrame {

	private JPanel contentPane;
	private JPanel panel_1;
	

	public AccordList() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 727, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel_1 = new APanel();
		scrollPane.setViewportView(panel_1);
		
		loadAccords();
		
		JLabel lblAccords = new JLabel("Accords");
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
		panel_1.removeAll();
		for(Accord a: accords.getAccords()) {
			if(!a.getFrom().equals(user.getLogin()) && !a.isAccepted()) {
				panel_1.add(new AccordNewPanel(a, getThis()));
			}
			else if(a.isAccepted()) {
				panel_1.add(new AccordAcceptPanel(a, getThis()));
			}
			
		}
		
	}

}
