package view;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class RechercheInfo extends JFrame{
	private final JPanel contentPanel = new JPanel();
	
	public RechercheInfo(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		
		editorPane.setText(Langues.getString("RechercheInfo.contenu.text")); //$NON-NLS-1$
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addGap(20)
					.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
					.addGap(9))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
