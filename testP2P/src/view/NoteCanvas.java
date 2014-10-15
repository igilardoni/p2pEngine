package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class NoteCanvas extends Canvas implements MouseMotionListener, MouseListener{
	static final long serialVersionUID = 5976281278513438960L;
	private int note;
	
	
	/**
	 * Crée une instance qui affiche les étoiles en fonction de la note, non éditable
	 * @param note
	 */
	public NoteCanvas(int note) {
		this.note = note;
	}
	
	/**
	 * Créer une instance éditable (on peut "noter")
	 */
	public NoteCanvas() {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		int oldNote = note;
		note = (int) ((float)e.getX() / (float)this.getWidth() * 5);
		//System.out.println(note);
		
		if(oldNote != note) this.repaint();
	}
	
	private void drawSquare(Graphics g, int place) {
		if(place <= note) {
			g.setColor(Color.YELLOW);
		}
		else g.setColor(Color.GRAY);
		g.fillRect(place * this.getWidth()/5, 0, this.getWidth()/5, this.getHeight());
		
		g.setColor(Color.BLACK);
		g.drawLine((place+1)*this.getWidth()/5 -1, 0, (place+1)*this.getWidth()/5 -1, this.getHeight());
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(int i = 0; i < 5; i++) {
			drawSquare(g, i);
		}
	}

}
