package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import modele.Reunion;

public class TableModelReunion extends DefaultTableModel {

	public TableModelReunion(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public TableModelReunion(ArrayList<Reunion> data) {
		super(convertResident(data), columnNames());
		
	
	}
	
	
	private static Object[] columnNames() {
		Object[] str = {"Titre","Date"};
		return str;
	}

	private static Object[][] convertResident(ArrayList<Reunion> al){
		Object[][] Object = new Object[al.size()][5];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i =0 ; i < al.size() ; i++) {
			Object[i][0] = al.get(i).getTitre();
			Object[i][1] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(al.get(i).getDate());
			
		}
		return Object;
	}
	
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}