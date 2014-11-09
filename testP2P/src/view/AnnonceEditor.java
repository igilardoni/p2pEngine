package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.JRadioButton;

import java.awt.SystemColor;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import model.ImageBase64;
import model.Objet;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Dimension;

import javax.swing.ImageIcon;

import java.awt.Color;

public class AnnonceEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField titre;
	private JTextField resume;
	private JRadioButton proposition;
	private JRadioButton souhait;
	private JCheckBox troc;
	private JCheckBox argent;
	private HTMLEditorPane description;
	private JButton btnParcourir;
	
	private JLabel errorTermes ;
	private JLabel errorTitre;
	private JLabel errorResume ;
	private JLabel errorDesc; 
	private JLabel lblImageAperu;
	private JLabel lblDescription;
	private JLabel lblAnnonce;
	private JLabel lblRsumDeDescription;
	private JLabel lblTitre;
	private JLabel lblDiffusion;
	private JLabel lblTermesDeLchange;
	private JLabel lblConfig;
	private JLabel lblTypeDeLannonce;
	private JLabel imageLabel;
	
	private JSeparator separatorConfig;
	private JSeparator separatorDiffusion;
	private JSeparator separatorAnnonce;
	
	private Objet obj = null;
	

	/**
	 * Fen�tre de cr�ation/modification d'une annonce
	 */
	public AnnonceEditor() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 700, 714);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		lblConfig = new JLabel(Messages.getString("AnnonceEditor.lblConfiguration.text")); //$NON-NLS-1$
		lblConfig.setForeground(SystemColor.textHighlight);
		lblConfig.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		separatorConfig = new JSeparator();
		
		lblTypeDeLannonce = new JLabel(Messages.getString("AnnonceEditor.lblTypeDeLannonce.text")); //$NON-NLS-1$
		
		proposition = new JRadioButton(Messages.getString("AnnonceEditor.rbtnProposition.text"));
		proposition.setSelected(true);
		souhait = new JRadioButton(Messages.getString("AnnonceEditor.rbtnSouhait.text")); //$NON-NLS-1$
		
		ButtonGroup bgPropSouhait = new ButtonGroup();
		bgPropSouhait.add(proposition);
		bgPropSouhait.add(souhait);
		
		lblTermesDeLchange = new JLabel(Messages.getString("AnnonceEditor.lblTermesDeLchange.text")); //$NON-NLS-1$
		
		troc = new JCheckBox(Messages.getString("AnnonceEditor.cbTroc.text")); //$NON-NLS-1$
		argent = new JCheckBox(Messages.getString("AnnonceEditor.cbArgent.text"));
		
		
       
		
		lblDiffusion = new JLabel(Messages.getString("AnnonceEditor.lblDiffusion.text")); //$NON-NLS-1$
		lblDiffusion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiffusion.setForeground(SystemColor.textHighlight);
		
		separatorDiffusion = new JSeparator();
		
		lblTitre = new JLabel(Messages.getString("AnnonceEditor.lblTitre.text")); //$NON-NLS-1$
		
		titre = new JTextField();
		titre.setColumns(10);
		
		lblRsumDeDescription = new JLabel(Messages.getString("AnnonceEditor.lblRsumDeDescription.text")); //$NON-NLS-1$
		
		resume = new JTextField();
		resume.setColumns(10);


		lblAnnonce = new JLabel(Messages.getString("AnnonceEditor.lblAnnonce.text")); //$NON-NLS-1$
		lblAnnonce.setForeground(SystemColor.textHighlight);
		lblAnnonce.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		separatorAnnonce = new JSeparator();
		
		lblDescription = new JLabel(Messages.getString("AnnonceEditor.lblCompleteDesc.text")); //$NON-NLS-1$
		
		description = new HTMLEditorPane();
		
		lblImageAperu = new JLabel(Messages.getString("AnnonceEditor.lblImageApercu.text"));
		
		btnParcourir = new JButton(Messages.getString("AnnonceEditor.btnParcourir.text")); //$NON-NLS-1$
		
		imageLabel = new JLabel(Messages.getString("AnnonceEditor.lblImage.text")); //$NON-NLS-1$
		imageLabel.setMinimumSize(new Dimension(100, 100));
		imageLabel.setMaximumSize(new Dimension(100, 100));
		imageLabel.setIcon(null);
		
		btnParcourir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageDialog dialog = new ImageDialog();
				dialog.showOpenDialog(null);
				
				setImage(dialog.getSelectedFile().toString());
				
			}
		});	


		errorTermes = new JLabel(Messages.getString("AnnonceEditor.lblErrorTermes.text")); //$NON-NLS-1$
		errorTermes.setVisible(false);
		errorTermes.setForeground(Color.RED);
		
		errorTitre = new JLabel(Messages.getString("AnnonceEditor.lblErrorTitre.text")); //$NON-NLS-1$
		errorTitre.setForeground(Color.RED);
		errorTitre.setVisible(false);
		
		errorResume = new JLabel(Messages.getString("AnnonceEditor.lblErrorResume.text")); //$NON-NLS-1$
		errorResume.setForeground(Color.RED);
		errorResume.setVisible(false);
		
		errorDesc = new JLabel(Messages.getString("AnnonceEditor.lblErrorDescription.text")); //$NON-NLS-1$
		errorDesc.setVisible(false);
		errorDesc.setForeground(Color.RED);
		
		JLabel lblContre = new JLabel(Messages.getString("AnnonceEditor.lblContre.text")); //$NON-NLS-1$
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(separatorConfig, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDescription)
							.addGap(57)
							.addComponent(errorDesc))
						.addComponent(separatorAnnonce, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTypeDeLannonce)
										.addComponent(lblTermesDeLchange)))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblRsumDeDescription))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblTitre))
								.addComponent(lblDiffusion))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(resume)
										.addComponent(titre, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
									.addGap(18)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(errorResume)
										.addComponent(errorTitre)))
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(troc)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPanel.createSequentialGroup()
												.addGap(12)
												.addComponent(lblContre))
											.addGroup(gl_contentPanel.createSequentialGroup()
												.addComponent(argent)
												.addGap(18)
												.addComponent(errorTermes))))
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(proposition)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(souhait))))
							.addPreferredGap(ComponentPlacement.RELATED, 549, Short.MAX_VALUE))
						.addComponent(separatorDiffusion, GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)
						.addComponent(lblConfig)
						.addComponent(lblAnnonce)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(description, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblImageAperu)
									.addGap(136)
									.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
									.addComponent(btnParcourir)
									.addGap(69)))))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConfig)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separatorConfig, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypeDeLannonce)
						.addComponent(proposition)
						.addComponent(souhait))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTermesDeLchange)
						.addComponent(troc)
						.addComponent(lblContre)
						.addComponent(argent)
						.addComponent(errorTermes))
					.addGap(4)
					.addComponent(lblDiffusion)
					.addGap(10)
					.addComponent(separatorDiffusion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTitre)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(titre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(errorTitre)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRsumDeDescription)
						.addComponent(resume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorResume))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separatorAnnonce, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(errorDesc))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(description, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(29)
									.addComponent(lblImageAperu))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(41)
							.addComponent(btnParcourir))))
		);
		
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(Messages.getString("AnnonceEditor.btnValider.text")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						initError();
						controller.AnnonceEditor validator = new controller.AnnonceEditor(proposition.isSelected(), souhait.isSelected(), troc.isSelected(), argent.isSelected(), titre.getText(), 
								resume.getText(), description.getText(), imageLabel.getToolTipText());
						if(validator.validate()) {
							if(obj != null) validator.setEditObjet(obj);
							validator.process();
							
							dispose();
						}
						else {
							showError(validator);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(Messages.getString("AnnonceEditor.btnAnnuler.text")); //$NON-NLS-1$
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	


	public AnnonceEditor(int i) {
		this();
		this.obj = Application.getInstance().getUsers().getConnectedUser().getObjets().get(i);
		proposition.setSelected(obj.isProposition());
		souhait.setSelected(obj.isSouhait());
		troc.setSelected(obj.isTroc());
		argent.setSelected(obj.isVente());
		titre.setText(obj.getTitre());
		resume.setText(obj.getResume());
		description.setText(obj.getDesc());
		setImageFrom64(obj.getImg());
	}
	
	private void setImage(String image) {
		if(image == null) return;
		imageLabel.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		imageLabel.setToolTipText(image);
	}
	
	private void setImageFrom64(String image) {
		if(image == null) return;
		imageLabel.setIcon(new ImageIcon(ImageBase64.decode(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		imageLabel.setToolTipText(image);
	}
	
	private void initError() {
		errorTermes.setVisible(false);
		errorTitre.setVisible(false);
		errorResume.setVisible(false);
		errorDesc.setVisible(false);
	}
	
	private void showError(controller.AnnonceEditor editor) {
		if(editor.errorVente || editor.errorTroc) errorTermes.setVisible(true);
		if(editor.errorTitle) errorTitre.setVisible(true);
		if(editor.errorResume) errorResume.setVisible(true);
		if(editor.errorDesc) errorDesc.setVisible(true);
	}
}