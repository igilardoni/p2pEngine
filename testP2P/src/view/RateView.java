package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import model.communications.Accord;

/**
 * Fenetre pour noter une transaction
 * @author Prudhomme Julien
 *
 */
public class RateView extends JFrame {

	private JPanel contentPane;

	public RateView(final Accord a, final AccordList parent) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 312, 207);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		NoteCanvas noteCanvas = new NoteCanvas();
		noteCanvas.setNoteListener(new NoteListener() {
			
			@Override
			public void noteSet(int note) {
				controller.Rate validator = new controller.Rate(a, note);
				if(validator.validate()) {
					if(!validator.process()) {
						//erreur
					}
					else {
						parent.revalidate();
						dispose();
					}
				}
				
			}
		});
		contentPane.add(noteCanvas, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel lblSelectionnezLaNote = new JLabel("Selectionnez la note sur le graphique");
		panel.add(lblSelectionnezLaNote);
	}

}
