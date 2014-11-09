package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

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
import controller.GeneratePdfObjet;
import model.ImageBase64;
import model.Objet;

public class AnnoncePanierPanel extends JPanel {

	private Objet obj = null;
	private static final Color COLOR_ACTIVE=new Color(110, 153, 204);
	private static final Color COLOR_INACTIVE=new Color(191, 205, 219);
	private Panier p;
	
	
	/**
	 * Create the panel.
	 */
	public AnnoncePanierPanel(Objet obj, final int i, Panier p_) {
		this.p = p_;
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
		isTroc.setBackground(obj.isTroc()?COLOR_ACTIVE:COLOR_INACTIVE);
		isTroc.setOpaque(true);
		isTroc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(isTroc);
		
		JLabel isVente = new JLabel(Messages.getString("AnnoncePanel.lblVente.text")); //$NON-NLS-1$
		isVente.setOpaque(true);
		isVente.setBackground(obj.isVente()?COLOR_ACTIVE:COLOR_INACTIVE);
		isVente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.add(isVente);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		
		String s = obj.isProposition() ? Messages.getString("AnnoncePanel.lblProposition.text"):"Souhait";
		JLabel propOuSouhait = new JLabel(s);
		propOuSouhait.setBackground(COLOR_ACTIVE);
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
		if(obj.getImg() != null) lblNewLabel.setIcon(new ImageIcon(ImageBase64.decode(obj.getImg()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
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
		
		JButton editButton = new JButton("Descendre");
		if(i == Application.getInstance().getUsers().getConnectedUser().getPanier().size() - 1) editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.Panier validator = new controller.Panier(i, controller.Panier.Action.DOWN);
				if(validator.validate()) {
					validator.process();
					p.revalidate();
				}
			}
			
		});
		
		JButton btnNewButton = new JButton("Monter"); //$NON-NLS-1$
		if(i == 0) btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.Panier validator = new controller.Panier(i, controller.Panier.Action.UP);
				if(validator.validate()) {
					validator.process();
					p.revalidate();
				}
			}
		});
		panel.add(btnNewButton);
		panel.add(editButton);
		
		JButton delButton = new JButton(Messages.getString("AnnoncePanel.lblSupprimer.text"));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.Panier validator = new controller.Panier(i, controller.Panier.Action.DELETE);
				if(validator.validate()) {
					validator.process();
					p.revalidate();
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
