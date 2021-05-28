package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class TableModelVisite extends DefaultTableModel {

	public TableModelVisite(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public TableModelVisite(ArrayList<Date> arrayList) {
		super(convertResident(arrayList), columnNames());
		
	
	}
	
	
	private static Object[] columnNames() {
		Object[] str = {"Date"};
		return str;
	}

	private static Object[][] convertResident(ArrayList<Date> al){
		Object[][] Object = new Object[al.size()][5];
		for(int i =0 ; i < al.size() ; i++) {
			Object[i][0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(al.get(i));
		}
		return Object;
	}
	
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	

}