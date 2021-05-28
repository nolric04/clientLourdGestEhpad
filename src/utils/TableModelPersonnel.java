package utils;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import modele.Evenement;
import modele.Personnel;


public class TableModelPersonnel extends DefaultTableModel {

	public TableModelPersonnel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public TableModelPersonnel(ArrayList<Personnel> data, Object[] columnNames) {
		super(convertdata(data), columnNames);
	}
	
	
	private static Object[][] convertdata(ArrayList<Personnel> al){
		
		Object[][] Object = new Object[al.size()][7];
		for(int i =0 ; i < al.size() ; i++) {
			Object[i][0] = al.get(i).getNom();
			Object[i][1] = al.get(i).getPrenom();
			Object[i][2] = al.get(i).getNumTelPoste();
			Object[i][3] = al.get(i).getService();
			Object[i][4] = al.get(i).getIntitulePoste();
			Object[i][5] = al.get(i).getIdentifiant();
			Object[i][6] = al.get(i).getMdp();
		}
		return Object;
		
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
