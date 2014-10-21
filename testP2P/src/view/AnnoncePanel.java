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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.SystemColor;

import javax.swing.JSeparator;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import controller.AnnonceDelete;
import model.Objet;

public class AnnoncePanel extends JPanel {

	private Objet obj;
	
	
	/**
	 * Create the panel.
	 */
	public AnnoncePanel(Objet obj, final int i) {
		this.obj = obj;
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
		JLabel lblNewLabel_1 = new JLabel(obj.getSimpleDate());
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(obj.getSimpleTime()); //$NON-NLS-1$
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		infoAnnonce.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		
		JLabel isTroc = new JLabel(Messages.getString("AnnoncePanel.lblTroc.text")); //$NON-NLS-1$
		isTroc.setBackground(SystemColor.inactiveCaption);
		isTroc.setOpaque(true);
		isTroc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(isTroc);
		
		JLabel isVente = new JLabel(Messages.getString("AnnoncePanel.lblVente.text")); //$NON-NLS-1$
		isVente.setOpaque(true);
		isVente.setBackground(SystemColor.inactiveCaption);
		isVente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(isVente);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		
		JLabel propOuSouhait = new JLabel(Messages.getString("AnnoncePanel.lblProposition.text"));
		propOuSouhait.setBackground(SystemColor.inactiveCaption);
		propOuSouhait.setOpaque(true);
		panel_4.add(propOuSouhait);
		propOuSouhait.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel contentAnnonce = new JPanel();
		contentAnnonce.setOpaque(false);
		contentAnnonce.setSize(new Dimension(0, 100));
		contentAnnonce.setMaximumSize(new Dimension(32767, 100));
		add(contentAnnonce, BorderLayout.CENTER);
		contentAnnonce.setLayout(new BorderLayout(0, 0));
		
		JPanel imageAnnonce = new JPanel();
		imageAnnonce.setOpaque(false);
		contentAnnonce.add(imageAnnonce, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("AnnoncePanel.lblImage.text"));
		if(obj.getImg() != null) lblNewLabel.setIcon(new ImageIcon(obj.getImg()));
		imageAnnonce.add(lblNewLabel);
		
		JPanel textAnonce = new JPanel();
		textAnonce.setOpaque(false);
		contentAnnonce.add(textAnonce, BorderLayout.CENTER);
		textAnonce.setLayout(new BorderLayout(0, 0));
		
		JPanel titreAnnonce = new JPanel();
		titreAnnonce.setOpaque(false);
		textAnonce.add(titreAnnonce, BorderLayout.NORTH);
		titreAnnonce.setLayout(new BorderLayout(0, 0));
		
		JLabel titreLabel = new JLabel(obj.getTitre());
		titreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titreAnnonce.add(titreLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		titreAnnonce.add(panel, BorderLayout.EAST);
		
		JButton editButton = new JButton(Messages.getString("AnnoncePanel.lblEditer.text"));
		panel.add(editButton);
		
		JButton delButton = new JButton(Messages.getString("AnnoncePanel.lblSupprimer.text"));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnnonceDelete validator = new AnnonceDelete(i);
				if(validator.validate()) {
					validator.process();
					Application.getInstance().updateUI();
				}
				
			}
		});
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
		txtpnLoremIpsumDolor.setText(obj.getResume());
		resumeAnnonce.add(txtpnLoremIpsumDolor, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		add(separator, BorderLayout.SOUTH);
	}

}
