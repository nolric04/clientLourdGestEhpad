package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controlleur.ControleEvenement;
import controlleur.ControleRechercheRegistre;
import modele.Evenement;
import modele.Registre;
import utils.TableModelEvenement;

public class ViewRegistre extends JPanel {
		
	private JPanel filtrage;
	private JPanel tableauRegistre;
	
	private JTable table;
	private Evenement event;
	private Registre registre;

	private JComboBox<String> nvImportance;
	private JComboBox<String> selectDeclarant;
	private JTextField motClef;
	
	public ViewRegistre(Registre reg) {
		setLayout(null);
		setSize(1000,750);
		
		this.registre=reg;
			
		filtrage=new JPanel();
		filtrage.setLayout(null);
		filtrage.setBounds(0,0, 1000, 150);
		filtrage.setBackground(Color.darkGray);
		
		String[] importance= {"Niveau Importance","1","2","3"};
		
		nvImportance=new JComboBox<String>(importance);
		JButton rechercher = new JButton("rechercher");
		motClef = new JTextField();
		
		rechercher.addActionListener(new ControleRechercheRegistre(this));
		
		rechercher.setBounds(600,50,150,30);
		nvImportance.setBounds(10,25,180,25);
		motClef.setBounds(10, 60, 350, 25);
		
		
		int e=App.getEtablissement().getListEmployes().size();
		int cs=App.getEtablissement().getListChefServices().size();
		int da=App.getEtablissement().getListDirecteur().size();
		String[] id = new String [e+cs+da+1];
		id[0]="Déclarant";
		for (int i = 0; i<e;i++){
			id[i+1]=App.getEtablissement().getListEmployes().get(i).getIdentifiant();
		}
		for (int i=0;i<cs;i++) {
			id[i+e+1]=App.getEtablissement().getListChefServices().get(i).getIdentifiant();
		}
		for(int i=0;i<da;i++) {
			id[i+e+cs+1]=App.getEtablissement().getListDirecteur().get(i).getIdentifiant();
		}
		
		       
		selectDeclarant=new JComboBox<String>(id);
		selectDeclarant.setBounds(210,25,180,25);
		
		filtrage.add(nvImportance);
		filtrage.add(selectDeclarant);
		filtrage.add(rechercher);
		filtrage.add(motClef);
		
		tableauRegistre=new JPanel();
		tableauRegistre.setLayout(null);
		tableauRegistre.setBounds(0, 150,1000,500);
		
		tableauRegistre.setBorder(new EmptyBorder(0, 0, 0, 0));
		tableauRegistre.setLayout(new BorderLayout(0, 0));
		
		JPanel panelInter =new JPanel();
		panelInter.setLayout(null);
		panelInter.setBounds(0, 0,1000,500);
		panelInter.setBackground(Color.yellow);
				
		this.table = new JTable();
		
		setListe(reg.getListEvenements());
		
		this.table.setBounds(0,0,1000,600);
        this.table.setAutoCreateRowSorter(true);
		
		if(this.table.getModel().getRowCount()>0)
            this.table.setRowSelectionInterval(0, 0);
		changeEventValue();
		
		JScrollPane scrollPane1 = new JScrollPane(table);
		scrollPane1.setBounds(0, 0,1000,600);
		
		panelInter.add(scrollPane1);
		
		JPanel boutonEven=new JPanel();
		boutonEven.setLayout(null);
		boutonEven.setBounds(0,650,1000,100);
		boutonEven.setBackground(Color.darkGray);
		
		JButton ajoutEvenement=new JButton("+nouvel évènement");
		ajoutEvenement.setBounds(400,20,200,25);
		
		ajoutEvenement.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleEvenement(null, false, registre.getService());
			}                	
		});
		
		boutonEven.add(ajoutEvenement);
		panelInter.add(scrollPane1);
		
	
		JButton readEvent =new JButton("Lire l'événement");
	    readEvent.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleEvenement(event, true, null);
			}                	
	    });
	   
	    JButton modifyEvent =new JButton("Modifier l'événement");
        modifyEvent.setBounds(100,20,150,25);
        readEvent.setBounds(750,20,150,25);
        boutonEven.add(readEvent);
        boutonEven.add(modifyEvent);
        
        modifyEvent.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleEvenement(event, false, registre.getService());
			}                	
        });


        tableauRegistre.add(panelInter);
  
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent lse) {
        		if (!lse.getValueIsAdjusting()) {
        			changeEventValue();
			    }
			}
		});		      
		  
        table.addMouseListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent mouseEvent) {
		          JTable table =(JTable) mouseEvent.getSource();
		          Point point = mouseEvent.getPoint();
		          int row = table.rowAtPoint(point);
		          
		          changeEventValue();
		          
		          if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	  new ControleEvenement(event, true, null);
		          }
		      }
		  });
      
		        
		add(filtrage);
		add(tableauRegistre);
		add(boutonEven);
	}

	
	public Registre getRegistre() {
		return registre;
	}


	public JComboBox<String> getSelectDeclarant() {
		return selectDeclarant;
	}

	public JComboBox<String> getNvImportance() {
		return nvImportance;
	}

	public JTextField getMotClef() {
		return motClef;
	}
	
	public void setListe(ArrayList<Evenement> events)
	{
		String column[]={"Importance","Date","Déclarant","Titre","Voir+",};

		table.setModel(new TableModelEvenement(events,column));
		
		if(table.getModel().getRowCount()>0)
            table.setRowSelectionInterval(0, 0);
	}
	
	private void changeEventValue()
	{
		if(table.getSelectedRow() == -1 && table.getModel().getRowCount()>0)
			table.setRowSelectionInterval(0, 0);
		
		if(table.getModel().getRowCount()>0)
		{
			int selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
			for(int i=0; i<registre.getListEvenements().size();i++) {
				if( (registre.getListEvenements().get(i).getDeclarant().getIdentifiant().equals( table.getModel().getValueAt(selectedIndex, 2)) )
		                  && (registre.getListEvenements().get(i).getTitre().equals( table.getModel().getValueAt(selectedIndex, 3)) )
		                  && (registre.getListEvenements().get(i).getDescription().equals( table.getModel().getValueAt(selectedIndex, 4)) )
		                  ) {
							event=registre.getListEvenements().get(i);
			              }
			}
		}
		
	}
}

