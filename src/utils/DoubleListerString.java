package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DoubleListerString extends JPanel implements ListSelectionListener{

	private JList right;
	
	
	public DoubleListerString(int posX, int posY, int boundX, int boundY, ArrayList<String> al, ArrayList<String> resList) {
		
		setLayout(null);
		setBounds(posX, posY, boundX, boundY);
		
		MyListModelRederer dcr = new MyListModelRederer();
		DefaultListModel<String> modelR = new DefaultListModel<String>();
		modelR.addAll(al);
				
		JList left  = new JList(modelR);
		left.setBounds(0, 0, boundX/5*2, boundY);
		left.setCellRenderer(dcr);

		JScrollPane panLeft = new JScrollPane(left);
		panLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panLeft.setBounds(0, 0, boundX/5*2, boundY);
		
		if (resList == null) {
			right = new JList(new DefaultListModel<String>());
			
		}else {
			DefaultListModel<String> dlm = new DefaultListModel<String>();
			dlm.addAll(resList);
			right = new JList(dlm);
			
		}
		right.setCellRenderer(dcr);
		

		JScrollPane panRight = new JScrollPane(right);
		panRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panRight.setBounds(0, 0, boundX/5*2, boundY);
		
		JButton add = new JButton("Ajouter");
		JButton remove = new JButton("Retirer");
		
		right.setBounds(0, 0, boundX/5*2, boundY);
		
		panRight.setBounds(boundX/5*3, 0, boundX/5*2, boundY);
		add.setBounds(boundX/5*2+5, boundY/3, boundX/5-10, 25);
		remove.setBounds(boundX/5*2+5, boundY/3+50, boundX/5-10, 25);
		
		
		left.setSelectedIndex(0);
		if(right.getModel().getSize() == 0)
			remove.setEnabled(false);
		else
			right.setSelectedIndex(0);
		
		add(panLeft);
		add(panRight);
		add(add);
		add(remove);
		
		repaint();
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = right.getModel().getSize();
				String[] ob = new String[size + 1];
				
				for (int i=0 ; i < size ; i++) {
					if(right.getModel().getElementAt(i).equals( left.getSelectedValue())) {
						return;
					}
					ob[i] = (String) right.getModel().getElementAt(i);
				}
				ob[size] = (String) left.getSelectedValue();
				right.setListData(ob);
				if(!remove.isEnabled())
					remove.setEnabled(true);
				
				right.setSelectedIndex(0);
				
			}
		});
		
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = right.getModel().getSize();
				String[] ob = new String[size -1];
				for (int i=0, j=0 ; i < size ; i++) {
					if(i != right.getSelectedIndex()) {
						ob[j] = (String) right.getModel().getElementAt(i);
						j++;
					}
				}
				right.setListData(ob);
				right.setSelectedIndex(0);
				if(right.getModel().getSize() == 0)
					remove.setEnabled(false);
			}
		});
		
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		
	}
	
	public ArrayList<String> getList(){
		ArrayList<String> al = new ArrayList<String>();
		for(int i=0;i<right.getModel().getSize();i++)
			al.add((String) right.getModel().getElementAt(i));
		
		return al;
		
	}
}
