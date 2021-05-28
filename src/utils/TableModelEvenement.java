package utils;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import modele.Evenement;


public class TableModelEvenement extends DefaultTableModel {

	public TableModelEvenement(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public TableModelEvenement(ArrayList<Evenement> data, Object[] columnNames) {
		super(convertEvenement(data), columnNames);
	}
	
	
	private static Object[][] convertEvenement(ArrayList<Evenement> al){
		
		Object[][] Object = new Object[al.size()][5];
		for(int i =0 ; i < al.size() ; i++) {
			Object[i][0] = al.get(i).getGravite();
			Object[i][1] = al.get(i).getDateEmission();
			Object[i][2] = al.get(i).getDeclarant().getIdentifiant();
			Object[i][3] = al.get(i).getTitre();
			Object[i][4] = al.get(i).getDescription();
			
		}
		return Object;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
