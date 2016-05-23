package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import model.ImageBase64;
import model.Objet;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

/**
 * Fenetre de creation/modification d'une annonce
 * @author 
 * 
 */

@SuppressWarnings("serial")
public class AnnonceEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnlBoutons;
	private JTextField titre;
	private JTextField resume;
	private JRadioButton rbProposition;
	private JRadioButton rbSouhait;
	private JCheckBox cbTroc;
	private JCheckBox cbArgent;
	private HTMLEditorPane description;
	
	private JButton btnParcourir;
	private JButton btnValider;
	private JButton btnAnnuler;
	
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
	private JLabel lblImage;
	
	private JSeparator separatorConfig;
	private JSeparator separatorDiffusion;
	private JSeparator separatorAnnonce;
	
	private Objet obj = null;
	
	public AnnonceEditor() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 700, 714);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		lblConfig = new JLabel(Langues.getString("AnnonceEditor.lblConfiguration.text")); //$NON-NLS-1$
		lblConfig.setForeground(SystemColor.textHighlight);
		lblConfig.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		separatorConfig = new JSeparator();
		
		lblTypeDeLannonce = new JLabel(Langues.getString("AnnonceEditor.lblTypeDeLannonce.text")); //$NON-NLS-1$
		
		rbProposition = new JRadioButton(Langues.getString("AnnonceEditor.rbtnProposition.text"));
		rbProposition.setSelected(true);
		rbSouhait = new JRadioButton(Langues.getString("AnnonceEditor.rbtnSouhait.text")); //$NON-NLS-1$
		
		ButtonGroup bgPropSouhait = new ButtonGroup();
		bgPropSouhait.add(rbProposition);
		bgPropSouhait.add(rbSouhait);
		
		lblTermesDeLchange = new JLabel(Langues.getString("AnnonceEditor.lblTermesDeLchange.text")); //$NON-NLS-1$
		
		cbTroc = new JCheckBox(Langues.getString("AnnonceEditor.cbTroc.text")); //$NON-NLS-1$
		cbArgent = new JCheckBox(Langues.getString("AnnonceEditor.cbArgent.text"));
		
		
       
		
		lblDiffusion = new JLabel(Langues.getString("AnnonceEditor.lblDiffusion.text")); //$NON-NLS-1$
		lblDiffusion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiffusion.setForeground(SystemColor.textHighlight);
		
		separatorDiffusion = new JSeparator();
		
		lblTitre = new JLabel(Langues.getString("AnnonceEditor.lblTitre.text")); //$NON-NLS-1$
		
		titre = new JTextField();
		titre.setColumns(10);
		
		lblRsumDeDescription = new JLabel(Langues.getString("AnnonceEditor.lblRsumDeDescription.text")); //$NON-NLS-1$
		
		resume = new JTextField();
		resume.setColumns(10);


		lblAnnonce = new JLabel(Langues.getString("AnnonceEditor.lblAnnonce.text")); //$NON-NLS-1$
		lblAnnonce.setForeground(SystemColor.textHighlight);
		lblAnnonce.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		separatorAnnonce = new JSeparator();
		
		lblDescription = new JLabel(Langues.getString("AnnonceEditor.lblCompleteDesc.text")); //$NON-NLS-1$
		
		description = new HTMLEditorPane();
		
		lblImageAperu = new JLabel(Langues.getString("AnnonceEditor.lblImageApercu.text"));
		
		btnParcourir = new JButton(Langues.getString("AnnonceEditor.btnParcourir.text")); //$NON-NLS-1$
		
		lblImage = new JLabel(Langues.getString("AnnonceEditor.lblImage.text")); //$NON-NLS-1$
		lblImage.setMinimumSize(new Dimension(100, 100));
		lblImage.setMaximumSize(new Dimension(100, 100));
		lblImage.setIcon(null);
		
		btnParcourir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageDialog dialog = new ImageDialog();
				dialog.showOpenDialog(null);
				
				setImage(dialog.getSelectedFile().toString());
				
			}
		});	


		errorTermes = new JLabel(Langues.getString("AnnonceEditor.lblErrorTermes.text")); //$NON-NLS-1$
		errorTermes.setVisible(false);
		errorTermes.setForeground(Color.RED);
		
		errorTitre = new JLabel(Langues.getString("AnnonceEditor.lblErrorTitre.text")); //$NON-NLS-1$
		errorTitre.setForeground(Color.RED);
		errorTitre.setVisible(false);
		
		errorResume = new JLabel(Langues.getString("AnnonceEditor.lblErrorResume.text")); //$NON-NLS-1$
		errorResume.setForeground(Color.RED);
		errorResume.setVisible(false);
		
		errorDesc = new JLabel(Langues.getString("AnnonceEditor.lblErrorDescription.text")); //$NON-NLS-1$
		errorDesc.setVisible(false);
		errorDesc.setForeground(Color.RED);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(separatorConfig, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDescription)
							.addGap(57)
							.addComponent(errorDesc))
						.addComponent(separatorAnnonce, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
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
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(rbProposition)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rbSouhait))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(cbTroc)
									.addComponent(cbArgent)
									.addGap(18)
									.addComponent(errorTermes)))
							.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE))
						.addComponent(separatorDiffusion, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
						.addComponent(lblConfig)
						.addComponent(lblAnnonce)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(description, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblImageAperu)
									.addGap(136)
									.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
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
						.addComponent(rbProposition)
						.addComponent(rbSouhait))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTermesDeLchange)
						.addComponent(cbTroc)
						.addComponent(cbArgent)
						.addComponent(errorTermes))
					.addPreferredGap(ComponentPlacement.RELATED)
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
								.addComponent(lblImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(41)
							.addComponent(btnParcourir))))
		);
		
		contentPanel.setLayout(gl_contentPanel);
		{
			pnlBoutons = new JPanel();
			pnlBoutons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBoutons, BorderLayout.SOUTH);
			{
				btnValider = new JButton(Langues.getString("AnnonceEditor.btnValider.text")); //$NON-NLS-1$
				btnValider.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						initError();
						controller.AnnonceEditor validator = new controller.AnnonceEditor(rbProposition.isSelected(), rbSouhait.isSelected(), cbTroc.isSelected(), cbArgent.isSelected(), titre.getText(), 
								resume.getText(), description.getText(), lblImage.getToolTipText());
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
				btnValider.setActionCommand("OK");
				pnlBoutons.add(btnValider);
				getRootPane().setDefaultButton(btnValider);
			}
			{
				btnAnnuler = new JButton(Langues.getString("AnnonceEditor.btnAnnuler.text")); //$NON-NLS-1$
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnAnnuler.setActionCommand("Cancel");
				pnlBoutons.add(btnAnnuler);
			}
		}
	}
	


	public AnnonceEditor(int i) {
		this();
		this.obj = Application.getInstance().getUsers().getConnectedUser().getObjets().get(i);
		rbProposition.setSelected(obj.isProposition());
		rbSouhait.setSelected(obj.isSouhait());
		cbTroc.setSelected(obj.isTroc());
		cbArgent.setSelected(obj.isVente());
		titre.setText(obj.getTitre());
		resume.setText(obj.getResume());
		description.setText(obj.getDesc());
		setImageFrom64(obj.getImg());
	}
	
	private void setImage(String image) {
		if(image == null) return;
		lblImage.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		lblImage.setToolTipText(image);
	}
	
	private void setImageFrom64(String image) {
		if(image == null) return;
		lblImage.setIcon(new ImageIcon(ImageBase64.decode(image).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		lblImage.setToolTipText(image);
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