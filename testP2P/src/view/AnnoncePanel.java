package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import model.ImageBase64;
import model.Objet;
import controller.AnnonceDelete;
import controller.GeneratePdfObjet;

/**
 * Panel d'une annonce
 * @author 
 * 
 */

@SuppressWarnings("serial")
public class AnnoncePanel extends JPanel {

	private static final Color COLOR_ACTIVE = new Color(110, 153, 204);
	private static final Color COLOR_INACTIVE = new Color(191, 205, 219);

	private JPanel pnlInfoAnnonce;
	private JPanel pnlDateHeure;
	private JPanel pnlChoix;
	private JPanel pnlTrocVente;
	private JPanel pnlPropSouh;
	private JPanel pnlContentAnnonce;
	private JPanel pnlImageAnnonce;
	private JPanel pnlTextAnnonce;
	private JPanel pnlTitreAnnonce;
	private JPanel pnlBoutons;
	private JPanel pnlResumeAnnonce;
	
	private JLabel lblDate;
	private JLabel lblHeure;
	private JLabel lblIsTroc;
	private JLabel lblIsVente;
	private JLabel lblPropOuSouhait;
	private JLabel lblImage;
	private JLabel lblTitre;
	
	private JButton btnEditer;
	private JButton btnPdf;
	private JButton btnSupprimer;
	
	private JTextPane pnlResume;
	
	
	public AnnoncePanel(Objet obj, final int i) {
		
		setMinimumSize(new Dimension(10, 100));
		setMaximumSize(new Dimension(32767, 100));
		setLayout(new BorderLayout(0, 0));
		
		pnlInfoAnnonce = new JPanel();
		pnlInfoAnnonce.setOpaque(false);
		add(pnlInfoAnnonce, BorderLayout.WEST);
		pnlInfoAnnonce.setLayout(new BorderLayout(0, 0));
		
		pnlDateHeure = new JPanel();
		pnlInfoAnnonce.add(pnlDateHeure, BorderLayout.NORTH);
		pnlDateHeure.setLayout(new BoxLayout(pnlDateHeure, BoxLayout.Y_AXIS));
		
		lblDate = new JLabel(obj.getSimpleDate());
		lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlDateHeure.add(lblDate);
		
		lblHeure = new JLabel(obj.getSimpleTime()); //$NON-NLS-1$
		lblHeure.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlDateHeure.add(lblHeure);
		
		pnlChoix = new JPanel();
		pnlInfoAnnonce.add(pnlChoix, BorderLayout.CENTER);
		pnlChoix.setLayout(new BorderLayout(0, 0));
		
		pnlTrocVente = new JPanel();
		pnlChoix.add(pnlTrocVente, BorderLayout.CENTER);
		
		lblIsTroc = new JLabel(Langues.getString("AnnoncePanel.lblTroc.text")); //$NON-NLS-1$
		lblIsTroc.setBackground(obj.isTroc()?COLOR_ACTIVE:COLOR_INACTIVE);
		lblIsTroc.setOpaque(true);
		lblIsTroc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTrocVente.add(lblIsTroc);
		
		lblIsVente = new JLabel(Langues.getString("AnnoncePanel.lblVente.text")); //$NON-NLS-1$
		lblIsVente.setOpaque(true);
		lblIsVente.setBackground(obj.isVente()?COLOR_ACTIVE:COLOR_INACTIVE);
		lblIsVente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTrocVente.add(lblIsVente);
		
		pnlPropSouh = new JPanel();
		pnlChoix.add(pnlPropSouh, BorderLayout.SOUTH);
		
		String s = obj.isProposition() ? Langues.getString("AnnoncePanel.lblProposition.text"):Langues.getString("AnnoncePanel.lblSouhait.text");
		lblPropOuSouhait = new JLabel(s);
		lblPropOuSouhait.setBackground(COLOR_ACTIVE);
		lblPropOuSouhait.setOpaque(true);
		pnlPropSouh.add(lblPropOuSouhait);
		lblPropOuSouhait.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlContentAnnonce = new JPanel();
		pnlContentAnnonce.setOpaque(false);
		pnlContentAnnonce.setSize(new Dimension(0, 100));
		pnlContentAnnonce.setMaximumSize(new Dimension(32767, 100));
		add(pnlContentAnnonce, BorderLayout.CENTER);
		pnlContentAnnonce.setLayout(new BorderLayout(0, 0));
		
		pnlImageAnnonce = new JPanel();
		pnlImageAnnonce.setOpaque(false);
		pnlContentAnnonce.add(pnlImageAnnonce, BorderLayout.WEST);
		
		lblImage = new JLabel(Langues.getString("AnnoncePanel.lblImage.text"));
		if(obj.getImg() != null) lblImage.setIcon(new ImageIcon(ImageBase64.decode(obj.getImg()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		pnlImageAnnonce.add(lblImage);
		
		pnlTextAnnonce = new JPanel();
		pnlTextAnnonce.setOpaque(false);
		pnlContentAnnonce.add(pnlTextAnnonce, BorderLayout.CENTER);
		pnlTextAnnonce.setLayout(new BorderLayout(0, 0));
		
		pnlTitreAnnonce = new JPanel();
		pnlTitreAnnonce.setOpaque(false);
		pnlTextAnnonce.add(pnlTitreAnnonce, BorderLayout.NORTH);
		pnlTitreAnnonce.setLayout(new BorderLayout(0, 0));
		
		lblTitre = new JLabel(obj.getTitre());
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlTitreAnnonce.add(lblTitre, BorderLayout.CENTER);
		
		pnlBoutons = new JPanel();
		pnlBoutons.setOpaque(false);
		pnlTitreAnnonce.add(pnlBoutons, BorderLayout.EAST);
		
		btnEditer = new JButton(Langues.getString("AnnoncePanel.btnEditer.text"));
		btnEditer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new AnnonceEditor(i).setVisible(true);
			}
			
		});
		
		btnPdf = new JButton(Langues.getString("AnnoncePanel.btnPdf.text")); //$NON-NLS-1$
		btnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneratePdfObjet validator = new GeneratePdfObjet(i);
				if(validator.validate()) {
					validator.process();
					Application.getInstance().updateUI();
				}
			}
		});
		pnlBoutons.add(btnPdf);
		pnlBoutons.add(btnEditer);
		
		btnSupprimer = new JButton(Langues.getString("AnnoncePanel.btnSupprimer.text"));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnnonceDelete validator = new AnnonceDelete(i);
				if(validator.validate()) {
					validator.process();
					Application.getInstance().updateUI();
				}
				
			}
		});
		pnlBoutons.add(btnSupprimer);
		
		pnlResumeAnnonce = new JPanel();
		pnlResumeAnnonce.setOpaque(false);
		pnlTextAnnonce.add(pnlResumeAnnonce, BorderLayout.CENTER);
		pnlResumeAnnonce.setLayout(new BorderLayout(0, 0));
		
		pnlResume = new JTextPane();
		pnlResume.setEditable(false);
		pnlResume.setFocusable(false);
		pnlResume.setVerifyInputWhenFocusTarget(false);
		pnlResume.setRequestFocusEnabled(false);
		pnlResume.setOpaque(false);
		pnlResume.setText(obj.getResume());
		pnlResumeAnnonce.add(pnlResume, BorderLayout.CENTER);
		
		JSeparator separator = new JSeparator();
		add(separator, BorderLayout.SOUTH);
	}

}
