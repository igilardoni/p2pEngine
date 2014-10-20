package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JSeparator;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.JRadioButton;

import java.awt.SystemColor;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JEditorPane;

import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.Color;

public class AnnonceEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField titre;
	private JTextField resume;
	private JLabel imageLabel;
	private JRadioButton proposition;
	private JRadioButton souhait;
	private JCheckBox troc;
	private JCheckBox argent;
	private HTMLEditorPane description;
	
	private JLabel errorTermes ;
	private JLabel errorTitre;
	private JLabel errorResume ;
	private JLabel errorDesc; 

	/**
	 * Create the dialog.
	 */
	public AnnonceEditor() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 659, 714);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("AnnonceEditor.lblConfiguration.text")); //$NON-NLS-1$
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblTypeDeLannonce = new JLabel(Messages.getString("AnnonceEditor.lblTypeDeLannonce.text")); //$NON-NLS-1$
		
		proposition = new JRadioButton(Messages.getString("AnnonceEditor.rbtnProposition.text")); //$NON-NLS-1$
		proposition.setSelected(true);
		
		souhait = new JRadioButton(Messages.getString("AnnonceEditor.rbtnSouhait.text")); //$NON-NLS-1$
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(proposition);
		bg.add(souhait);
		
		JLabel lblTermesDeLchange = new JLabel(Messages.getString("AnnonceEditor.lblTermesDeLchange.text")); //$NON-NLS-1$
		
		troc = new JCheckBox(Messages.getString("AnnonceEditor.cbTroc.text")); //$NON-NLS-1$
		troc.setSelected(true);
		
		argent = new JCheckBox(Messages.getString("AnnonceEditor.cbArgent.text")); //$NON-NLS-1$
		
		JLabel lblDiffusion = new JLabel(Messages.getString("AnnonceEditor.lblDiffusion.text")); //$NON-NLS-1$
		lblDiffusion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiffusion.setForeground(SystemColor.textHighlight);
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblTitre = new JLabel(Messages.getString("AnnonceEditor.lblTitre.text")); //$NON-NLS-1$
		
		titre = new JTextField();
		titre.setText(Messages.getString("AnnonceEditor.tfTitre.text")); //$NON-NLS-1$
		titre.setColumns(10);
		
		JLabel lblRsumDeDescription = new JLabel(Messages.getString("AnnonceEditor.lblRsumDeDescription.text")); //$NON-NLS-1$
		
		resume = new JTextField();
		resume.setText(Messages.getString("AnnonceEditor.tfResume.text")); //$NON-NLS-1$
		resume.setColumns(10);
		
		JLabel lblAnnonce = new JLabel(Messages.getString("AnnonceEditor.lblAnnonce.text")); //$NON-NLS-1$
		lblAnnonce.setForeground(SystemColor.textHighlight);
		lblAnnonce.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSeparator separator_2 = new JSeparator();
		
		JLabel lblDescription = new JLabel(Messages.getString("AnnonceEditor.lblCompleteDesc.text")); //$NON-NLS-1$
		
		description = new HTMLEditorPane();
		
		JLabel lblImageAperu = new JLabel(Messages.getString("AnnonceEditor.lblImageApercu.text"));
		
		JButton btnNewButton = new JButton(Messages.getString("AnnonceEditor.btnParcourir.text")); //$NON-NLS-1$
		imageLabel = new JLabel(Messages.getString("AnnonceEditor.lblImage.text")); //$NON-NLS-1$
		imageLabel.setMinimumSize(new Dimension(100, 100));
		imageLabel.setMaximumSize(new Dimension(100, 100));
		imageLabel.setIcon(null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageDialog dialog = new ImageDialog();
				dialog.showOpenDialog(null);
				
				imageLabel.setIcon(new ImageIcon(new ImageIcon(dialog.getSelectedFile().toString()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
				imageLabel.setToolTipText(dialog.getSelectedFile().toString());
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
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(description, GroupLayout.PREFERRED_SIZE, 631, Short.MAX_VALUE)
						.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
						.addComponent(lblNewLabel)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTypeDeLannonce)
								.addComponent(lblTermesDeLchange))
							.addGap(48)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(proposition)
									.addGap(18)
									.addComponent(souhait))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(troc)
									.addGap(18)
									.addComponent(argent)
									.addGap(18)
									.addComponent(errorTermes)))
							.addGap(33))
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
						.addComponent(lblDiffusion)
						.addComponent(lblAnnonce)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblImageAperu)
							.addGap(162)
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
							.addComponent(btnNewButton))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblRsumDeDescription)
									.addGap(18)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(resume)
										.addComponent(titre, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))
								.addComponent(lblTitre))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(errorResume)
								.addComponent(errorTitre)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDescription)
							.addGap(18)
							.addComponent(errorDesc)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypeDeLannonce)
						.addComponent(proposition)
						.addComponent(souhait))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTermesDeLchange)
						.addComponent(troc)
						.addComponent(argent)
						.addComponent(errorTermes))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDiffusion)
					.addGap(9)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitre)
						.addComponent(titre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorTitre))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRsumDeDescription)
						.addComponent(resume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorResume))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnnonce)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(errorDesc))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(description, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImageAperu)
						.addComponent(btnNewButton)
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
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
