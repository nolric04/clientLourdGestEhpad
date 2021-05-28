package utils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class MyListModelRederer extends DefaultListCellRenderer{

	public MyListModelRederer() {
		super();
		
	}

	@Override
	public Component getListCellRendererComponent(
	        JList<?> list,
	        Object value,
	        int index,
	        boolean isSelected,
	        boolean cellHasFocus)
	    {
	        setComponentOrientation(list.getComponentOrientation());

	        Color bg = new Color(130,130,130,255);
	        Color fg = Color.red;

	        if (isSelected) {
	            setBackground(bg == null ? list.getSelectionBackground() : bg);
	            setForeground(fg == null ? list.getSelectionForeground() : fg);
	        }
	        else {
	        	if(index%2 == 0) {
	        		setBackground(new Color(60,60,60,255));
	        		setForeground(Color.white);	        		
	        	}else {
	        		setBackground(new Color(30,30,30,255));
	        		setForeground(Color.white);
	        	}
	        }

	        
	            setIcon(null);
	            setText((value == null) ? "" : value.toString());
	        

	        setEnabled(list.isEnabled());
	        setFont(list.getFont());

	        

	        return this;
	    }

	
}
