package view;

import java.awt.BorderLayout;


import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import java.awt.Component;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import model.Objet;
import model.ObjetsManagement;
import model.User;
import model.UsersManagement;

import javax.swing.JEditorPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;

import java.awt.Font;

import javax.swing.ImageIcon;



public class Panier extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel panierVide;
	private JLabel tri;
	private JComboBox comboBox;
	private ObjetsManagement panier = Application.getInstance().getUsers().getConnectedUser().getPanier();
	private JButton vente;
	private JButton troc;
	private JLabel imageLabel;
	private JLabel titre;
	private JLabel resume;
	
	
	private Objet obj = null;
	

	/**
	 * Create the dialog.
	 */
	public Panier() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 912, 481);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		panierVide = new JLabel(Messages.getString("Panier.lblPanierVide.text"));
		if(panier == null)
			panierVide.setVisible(false);
		else
			panierVide.setVisible(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addComponent(panierVide)
							.addGap(402))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(panierVide)
					.addGap(18)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab(Messages.getString("Panier.tabbedpaneMesAchats.txt"), null, scrollPane_1, null);
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tri.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setMaximumRowCount(4);
		comboBox.setModel(new DefaultComboBoxModel(getTrisNamesList()));
		
		
		tri = new JLabel(Messages.getString("Panier.lblTrierPar.text")); //$NON-NLS-1$
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 851, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(731, Short.MAX_VALUE)
					.addComponent(tri)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(65))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tri, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(166, Short.MAX_VALUE))
		);
		
		JButton btnSupprimer = new JButton(Messages.getString("Panier.btnSupprimer.text")); //$NON-NLS-1$
		btnSupprimer.setBounds(762, 11, 89, 23);
		desktopPane.add(btnSupprimer);
		
		JButton btnPdf = new JButton(Messages.getString("Panier.btnPdf.text")); //$NON-NLS-1$
		btnPdf.setBounds(668, 11, 89, 23);
		desktopPane.add(btnPdf);
		
		troc = new JButton(Messages.getString("Panier.btnTroc.text"));
		troc.setEnabled(false);
		troc.setBackground(UIManager.getColor("textHighlightText"));
		troc.setFont(new Font("Tahoma", Font.PLAIN, 9));
		troc.setBounds(10, 34, 51, 23);
		desktopPane.add(troc);
		
		vente = new JButton(Messages.getString("Panier.btnVente.text")); //$NON-NLS-1$
		vente.setEnabled(false);
		vente.setBackground(UIManager.getColor("textHighlightText"));
		vente.setFont(new Font("Tahoma", Font.PLAIN, 9));
		vente.setBounds(70, 34, 61, 23);
		desktopPane.add(vente);
		
		imageLabel = new JLabel(Messages.getString("Panier.lblImagelabel.text_1")); //$NON-NLS-1$
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(147, 11, 98, 100);
		desktopPane.add(imageLabel);
		
		titre = new JLabel(Messages.getString("Panier.lblTitre.text")); //$NON-NLS-1$
		titre.setBounds(255, 15, 383, 42);
		desktopPane.add(titre);
		
		resume = new JLabel(Messages.getString("Panier.lblResume.text"));
		resume.setBounds(255, 54, 383, 57);
		desktopPane.add(resume);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(300);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnEndOrderButton = new JButton(Messages.getString("Panier.btnTerminerCommande.text"));
				btnEndOrderButton.setIcon(new ImageIcon(Panier.class.getResource("/resources/images/x16/add.png")));
				btnEndOrderButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				{
					
					
					JButton btnPoursuivreMesAchats = new JButton(Messages.getString("Panier.btnPoursuivreMesAchats.text"));
					/*
					btnPoursuivreMesAchats.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.Panier validator = new controller.Panier(titre.getText(), resume.getText(), imageLabel.getToolTipT, troc.isSelected(), vente.isSelected());
							if(validator.validate()) {
								if(obj != null) validator.setEditObjet(obj);
								validator.process();
								
								dispose();
							}
							else {
								
							}
								
						}
					}); 
					*/
					buttonPane.add(btnPoursuivreMesAchats);
				}
				btnEndOrderButton.setActionCommand("Panier.btnTerminerCommande.text");
				buttonPane.add(btnEndOrderButton);
			}
		}
	}
	
	public Panier(int i) {
		this();
		this.obj = Application.getInstance().getUsers().getConnectedUser().getObjets().get(i);
		troc.setSelected(obj.isTroc());
		titre.setText(obj.getTitre());
		resume.setText(obj.getResume());
		setImage(obj.getImg());
	}
	
	private void setImage(String image) {
		if(image == null) return;
		imageLabel.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		imageLabel.setToolTipText(image);
	}
	
	
	private String[] getTrisNamesList() {
		String[] res = new String[comboBox.getMaximumRowCount()];
		res[0] = "";
		res[1] = Messages.getString("Panier.lblTitre.text");
		res[2] = Messages.getString("Panier.lblDate.text");
		res[3] = Messages.getString("Panier.lblLieu.text");
		return res;
	}
}