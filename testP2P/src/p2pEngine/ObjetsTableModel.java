package p2pEngine;

import gui.Application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public abstract class ObjetsTableModel extends AbstractTableModel{

	private Objets objets;
	protected String[] columnNames;
	private Application app;
	
	
	
	public ObjetsTableModel(Objets objets, String[] columnNames, Application app) {
		this.objets = objets;
		this.columnNames = columnNames;
		this.app = app;
	}

	public String getColumnName(int column) {
		if(column >= columnNames.length) return "Action";
		return columnNames[column];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length + 1;
	}
	
	@Override
	public int getRowCount() {
		return objets.size();
	}

	public abstract String getObjetColumnValue(int column, Object o);
	
	@Override
	public Object getValueAt(int row, int column) {
		final Objet o = (Objet) objets.get(row);
		
		if(column < columnNames.length)
		return getObjetColumnValue(column, o);
		
		JButton button = new JButton("Voir");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("cliqué dans la table :))");
				System.out.println(o.getNom());
				app.voirObjet(o);
				
				
			}
		});
		return button;
	}
	
	 @Override public Class<?> getColumnClass(int columnIndex) {
         return columnIndex >= columnNames.length ? String.class:JButton.class;
     }
	

}
