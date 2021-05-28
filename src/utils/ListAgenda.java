package utils;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import controlleur.ControleAjouterReunion;
import controlleur.ControleAjouterVisite;
import controlleur.Utils;
import modele.ChefService;
import modele.Directeur;
import modele.Personnel;

public class ListAgenda extends JPanel {
	
	
	public ListAgenda(Personnel p, String str, int x) {
		
		String here;
		JLabel titre;
		JTable jt = new JTable();
		JButton addItem;
		JPanel titrePan = new JPanel();
		
		Directeur d = null;
		ChefService cs = null;

		setLayout(null);
		setBounds(0,0,x,750);
		setBackground(Color.lightGray);
		titrePan.setLayout(null);
		
		if(p.getClass() == Directeur.class) {
			d = (Directeur)p;
		}else if(p.getClass() == ChefService.class){
			cs = (ChefService)p;
		}
				
		if ( str.equalsIgnoreCase("visite") ){
			titre = new JLabel("Visites");
			jt.setModel(new TableModelVisite(d.getListVisites()));
			addItem = new JButton("Ajouter");
			here ="visite";
		}else {
			titre = new JLabel("Réunions");
			addItem = new JButton("Ajouter");
			jt.setModel(new TableModelReunion(p.getClass()==Directeur.class ? d.getListReunions() : cs.getListeReunion()) );
			here ="reunion";
		}
		
		titrePan.setBounds(0,0,x,100);
		titrePan.setBackground(Color.lightGray);
		titrePan.add(titre);
		addItem.setBounds(x/2-50, 650, 100, 25);
		
		titre.setBounds(x/2-60, 50, 120 , 25);
		jt.setBounds(0, 100, x , 500);
		
		
		jt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                
                int id = 0;
                
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
	                if(here.equalsIgnoreCase("reunion")){
						try {
							System.out.println( Utils.returnIdinBdd("reunion", "dateReunion", table.getModel().getValueAt(row, 1).toString()) );
							id = Utils.returnIdinBdd("reunion", "dateReunion", table.getModel().getValueAt(row, 1).toString());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else if (here.equalsIgnoreCase("visite")){
						try {
							System.out.println(Utils.returnIdinBdd("visite", "dateVisite", table.getModel().getValueAt(row, 0).toString()));
							id = Utils.returnIdinBdd("visite", "dateVisite", table.getModel().getValueAt(row, 0).toString());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
                }
                
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                	if(here.equalsIgnoreCase("reunion")){
    					try {
							new ControleAjouterReunion(id);
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
    				}else if (here.equalsIgnoreCase("visite")){
    					try {
							new ControleAjouterVisite(id);
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
    				}
                }
            }
        });
		
		addItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(here.equalsIgnoreCase("reunion")){
					try {
						new ControleAjouterReunion(0);
					} catch (SQLException | ParseException e1) {
						e1.printStackTrace();
					}
				}else if (here.equalsIgnoreCase("visite")){
					try {
						new ControleAjouterVisite(0);
					} catch (SQLException | ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		add(titrePan);
		add(jt);
		add(addItem);
		
		
		
		
	}
	

}
