package utils;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import modele.Resident;

public class TableModelResident extends DefaultTableModel {

	public TableModelResident(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public TableModelResident(ArrayList<Resident> data, Object[] columnNames) {
		super(convertResident(data), columnNames);
		
	
	}
	
	
	private static Object[][] convertResident(ArrayList<Resident> al){
		Object[][] Object = new Object[al.size()][5];
		for(int i =0 ; i < al.size() ; i++) {
			Object[i][0] = al.get(i).getNom();
			Object[i][1] = al.get(i).getPrenom();
			Object[i][2] = al.get(i).getDateNaissance();
			Object[i][3] = al.get(i).getNumSecu();
			Object[i][4] = al.get(i).getNumChambre();
		}
		return Object;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}