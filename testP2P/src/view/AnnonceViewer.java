package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.SystemColor;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import model.ImageBase64;
import model.Objet;

public class AnnonceViewer extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public AnnonceViewer(Objet o) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setMaximumSize(new Dimension(100, 100));
		lblNewLabel.setMinimumSize(new Dimension(100, 100));
		lblNewLabel.setPreferredSize(new Dimension(100, 100));
		lblNewLabel.setSize(new Dimension(100, 100));
		if(o.getImg() != null) {
			lblNewLabel.setIcon(new ImageIcon(ImageBase64.decode(o.getImg()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		}
		
		JLabel lblLeTitreDe = new JLabel(o.getTitre());
		lblLeTitreDe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLeTitreDe.setForeground(SystemColor.textHighlight);
		
		JTextPane textArea = new JTextPane();
		textArea.setText(o.getResume());
		textArea.setEditable(false);
		textArea.setOpaque(false);
		
		JLabel lblTermesDeLchange = new JLabel("Termes de l'échange");
		
		JCheckBox chckbxTroc = new JCheckBox("Troc");
		chckbxTroc.setSelected(o.isTroc());
		chckbxTroc.setEnabled(false);
		chckbxTroc.setFocusable(false);
		
		JCheckBox chckbxVente = new JCheckBox("Vente");
		chckbxVente.setSelected(o.isVente());
		chckbxVente.setEnabled(false);
		
		JLabel lblTypeDeLannonce = new JLabel("Type de l'annonce :");
		
		JLabel lblProposition = new JLabel(o.isProposition() ?  "Proposition":"Souhait");
		
		JLabel lblPostLe = new JLabel("Posté le :");
		
		JLabel lblh = new JLabel(o.getSimpleDate() + " à " + o.getSimpleTime());
		
		JLabel lblUtilisateur = new JLabel("Utilisateur :");
		
		JLabel lblCrashxxl = new JLabel(o.getUserName());
		
		JButton btnVoirUtilisateur = new JButton("Voir utilisateur");
		
		JTextPane txtpnLoremIpsumDolor = new JTextPane();
		txtpnLoremIpsumDolor.setOpaque(false);
		txtpnLoremIpsumDolor.setEditable(false);
		txtpnLoremIpsumDolor.setContentType("text/html");
		txtpnLoremIpsumDolor.setText("<html>" + o.getDesc());
		
		JButton btnContacterLutilisateurPour = new JButton("Contacter l'utilisateur pour un accords");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(194, Short.MAX_VALUE)
					.addComponent(btnContacterLutilisateurPour)
					.addGap(189))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnLoremIpsumDolor, GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLeTitreDe)
								.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTermesDeLchange)
								.addComponent(lblTypeDeLannonce))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblProposition)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(chckbxTroc)
									.addGap(18)
									.addComponent(chckbxVente))))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblPostLe)
							.addGap(18)
							.addComponent(lblh)
							.addGap(73)
							.addComponent(lblUtilisateur)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCrashxxl)
							.addGap(66)
							.addComponent(btnVoirUtilisateur)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblLeTitreDe)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTermesDeLchange)
						.addComponent(chckbxTroc)
						.addComponent(chckbxVente))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypeDeLannonce)
						.addComponent(lblProposition))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPostLe)
						.addComponent(lblh)
						.addComponent(lblUtilisateur)
						.addComponent(lblCrashxxl)
						.addComponent(btnVoirUtilisateur))
					.addGap(18)
					.addComponent(txtpnLoremIpsumDolor, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnContacterLutilisateurPour)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
