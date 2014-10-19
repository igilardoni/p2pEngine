package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JFormattedTextField;

import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class AnnoncePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AnnoncePanel() {
		setMinimumSize(new Dimension(10, 100));
		setMaximumSize(new Dimension(32767, 100));
		setLayout(new BorderLayout(0, 0));
		
		JPanel infoAnnonce = new JPanel();
		infoAnnonce.setOpaque(false);
		add(infoAnnonce, BorderLayout.WEST);
		infoAnnonce.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		infoAnnonce.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("AnnoncePanel.lblNewLabel_1.text"));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("AnnoncePanel.lblNewLabel_2.text")); //$NON-NLS-1$
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		infoAnnonce.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		
		JLabel lblNewLabel_3 = new JLabel(Messages.getString("troc")); //$NON-NLS-1$
		lblNewLabel_3.setBackground(SystemColor.inactiveCaption);
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(lblNewLabel_3);
		
		JLabel label = new JLabel(Messages.getString("vente")); //$NON-NLS-1$
		label.setOpaque(true);
		label.setBackground(SystemColor.inactiveCaption);
		label.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(label);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_4 = new JLabel(Messages.getString("proposition"));
		lblNewLabel_4.setBackground(SystemColor.inactiveCaption);
		lblNewLabel_4.setOpaque(true);
		panel_4.add(lblNewLabel_4);
		lblNewLabel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel contentAnnonce = new JPanel();
		contentAnnonce.setOpaque(false);
		contentAnnonce.setSize(new Dimension(0, 100));
		contentAnnonce.setMaximumSize(new Dimension(32767, 100));
		add(contentAnnonce, BorderLayout.CENTER);
		contentAnnonce.setLayout(new BorderLayout(0, 0));
		
		JPanel imageAnnonce = new JPanel();
		imageAnnonce.setOpaque(false);
		contentAnnonce.add(imageAnnonce, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\crashxxl\\workspaceJXTA\\gui\\patatelol.jpg"));
		imageAnnonce.add(lblNewLabel);
		
		JPanel textAnonce = new JPanel();
		textAnonce.setOpaque(false);
		contentAnnonce.add(textAnonce, BorderLayout.CENTER);
		textAnonce.setLayout(new BorderLayout(0, 0));
		
		JPanel titreAnnonce = new JPanel();
		titreAnnonce.setOpaque(false);
		textAnonce.add(titreAnnonce, BorderLayout.NORTH);
		titreAnnonce.setLayout(new BorderLayout(0, 0));
		
		JLabel titreLabel = new JLabel(Messages.getString("AnnoncePanel.lblTitreAnnonce.text"));
		titreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titreAnnonce.add(titreLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		titreAnnonce.add(panel, BorderLayout.EAST);
		
		JButton editButton = new JButton(Messages.getString("editer"));
		panel.add(editButton);
		
		JButton delButton = new JButton(Messages.getString("supprimer"));
		panel.add(delButton);
		
		JPanel resumeAnnonce = new JPanel();
		resumeAnnonce.setOpaque(false);
		textAnonce.add(resumeAnnonce, BorderLayout.CENTER);
		resumeAnnonce.setLayout(new BorderLayout(0, 0));
		
		JTextPane txtpnLoremIpsumDolor = new JTextPane();
		txtpnLoremIpsumDolor.setEditable(false);
		txtpnLoremIpsumDolor.setFocusable(false);
		txtpnLoremIpsumDolor.setVerifyInputWhenFocusTarget(false);
		txtpnLoremIpsumDolor.setRequestFocusEnabled(false);
		txtpnLoremIpsumDolor.setOpaque(false);
		txtpnLoremIpsumDolor.setText(Messages.getString("AnnoncePanel.txtpnLoremIpsumDolor.text"));
		resumeAnnonce.add(txtpnLoremIpsumDolor, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		add(separator, BorderLayout.SOUTH);
	}

}
