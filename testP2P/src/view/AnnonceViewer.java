package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import model.ImageBase64;
import model.Objet;

/**
 * Affiche une annonce pour un utilisateur qui souhaite voir son contenu integral
 * @author 
 *
 */

@SuppressWarnings("serial")
public class AnnonceViewer extends JFrame {

	private JPanel contentPane;
	
	private JLabel lblImage;
	private JLabel lblTitre;
	private JLabel lblTypeAnnonce;
	private JLabel lblPropSouh;
	private JLabel lblPosteLe;
	private JLabel lblDateHeure;
	private JLabel lblUtilisateur;
	private JLabel lblNomUtilisateur;
	private JLabel lblTrocVente;
	private JLabel lblEmail;
	private JLabel lblAdresseMail;
	private JLabel lblTel;
	private JLabel lblNumero;
	
	private JTextPane pnlResume;
	private JTextPane pnlDescription;
	
	private JCheckBox cbTroc;
	private JCheckBox cbVente;
	
	private JButton btnContacter;
	private JButton btnEnvoyerMessage;
	
	public AnnonceViewer(final Objet o) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblImage = new JLabel();
		lblImage.setMaximumSize(new Dimension(100, 100));
		lblImage.setMinimumSize(new Dimension(100, 100));
		lblImage.setPreferredSize(new Dimension(100, 100));
		lblImage.setSize(new Dimension(100, 100));
		if(o.getImg() != null) {
			lblImage.setIcon(new ImageIcon(ImageBase64.decode(o.getImg()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		}
		
		lblTitre = new JLabel(o.getTitre());
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitre.setForeground(SystemColor.textHighlight);
		
		pnlResume = new JTextPane();
		pnlResume.setText(o.getResume());
		pnlResume.setEditable(false);
		pnlResume.setOpaque(false);
		
		lblTrocVente = new JLabel(Langues.getString("AnnonceViewer.lblTrocVente.text"));
		
		cbTroc = new JCheckBox(Langues.getString("AnnonceViewer.cbTroc.text"));
		cbTroc.setSelected(o.isTroc());
		cbTroc.setEnabled(false);
		cbTroc.setFocusable(false);
		
		cbVente = new JCheckBox(Langues.getString("AnnonceViewer.cbVente.text"));
		cbVente.setSelected(o.isVente());
		cbVente.setEnabled(false);
		
		lblTypeAnnonce = new JLabel(Langues.getString("AnnonceViewer.lblTypeAnnonce.text"));
		
		lblPropSouh = new JLabel(o.isProposition() ?  Langues.getString("AnnonceViewer.lblProposition.text"):Langues.getString("AnnonceViewer.lblSouhait.text"));
		
		lblPosteLe = new JLabel(Langues.getString("AnnonceViewer.lblPosteLe.text"));
		
		lblDateHeure = new JLabel(o.getSimpleDate() + Langues.getString("AnnonceViewer.lblDateHeure.text") + o.getSimpleTime());
		
		lblUtilisateur = new JLabel(Langues.getString("AnnonceViewer.lblUtilisateur.text"));
		
		lblNomUtilisateur = new JLabel(o.getUser().getLogin());
		
		pnlDescription = new JTextPane();
		pnlDescription.setOpaque(false);
		pnlDescription.setEditable(false);
		pnlDescription.setContentType("text/html");
		pnlDescription.setText("<html>" + o.getDesc());
		

		btnContacter = new JButton(Langues.getString("AnnonceViewer.btnContacter.text"));
		btnContacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AccordView(o).setVisible(true);
			}
		});
		
		lblEmail = new JLabel(Langues.getString("AnnonceViewer.lblEmail.text"));
		
		lblAdresseMail = new JLabel(o.getUser().getMail());
		
		lblTel = new JLabel(Langues.getString("AnnonceViewer.lblTel.text"));
		
		lblNumero = new JLabel(o.getUser().getTel());
		
		btnEnvoyerMessage = new JButton(Langues.getString("AnnonceViewer.btnEnvoyerMessage.text"));
		btnEnvoyerMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.NewConvers validator = new controller.NewConvers(o.getUser().getLogin());
				if(validator.validate()) {
					validator.process();
					Application.getInstance().openConvers(o.getUser().getLogin());
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitre)
								.addComponent(pnlResume, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTrocVente)
							.addGap(18)
							.addComponent(cbTroc)
							.addGap(18)
							.addComponent(cbVente)
							.addGap(38)
							.addComponent(lblUtilisateur)
							.addGap(6)
							.addComponent(lblNomUtilisateur))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTypeAnnonce)
							.addGap(36)
							.addComponent(lblPropSouh)
							.addGap(156)
							.addComponent(lblEmail)
							.addGap(6)
							.addComponent(lblAdresseMail))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPosteLe)
							.addGap(18)
							.addComponent(lblDateHeure)
							.addGap(85)
							.addComponent(lblTel)
							.addGap(6)
							.addComponent(lblNumero))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(211)
							.addComponent(btnContacter))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(217)
							.addComponent(btnEnvoyerMessage))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(pnlDescription, GroupLayout.PREFERRED_SIZE, 643, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTitre)
							.addGap(6)
							.addComponent(pnlResume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(2)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblTrocVente))
						.addComponent(cbTroc)
						.addComponent(cbVente)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblUtilisateur))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNomUtilisateur)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTypeAnnonce)
						.addComponent(lblPropSouh)
						.addComponent(lblEmail)
						.addComponent(lblAdresseMail))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPosteLe)
						.addComponent(lblDateHeure)
						.addComponent(lblTel)
						.addComponent(lblNumero))
					.addGap(11)
					.addComponent(btnEnvoyerMessage)
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addComponent(pnlDescription, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(btnContacter))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
