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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlleur.ControleRechercheResident;
import controlleur.ControleResident;
import modele.Personnel;
import modele.Resident;
import utils.TableModelPersonnel;
import utils.TableModelResident;

@SuppressWarnings("serial")
public class ViewResident extends JPanel {
	
	private JPanel filtreResident;
	private JPanel tableauResident;
	private JTable table;
	private Resident resid;
	
	private JTextField nom;
	private JTextField prenom;
	private JTextField numSecu;
	private JTextField chambre;

	public ViewResident()
	{
		setLayout(null);
		setBounds(0, 0, 1000, 750);
		setBackground(Color.darkGray);

		filtreResident=new JPanel();
		tableauResident=new JPanel();
		 
		filtreResident.setLayout(null);
		filtreResident.setBounds(0, 0, 1000,150);
		filtreResident.setBackground(Color.gray);
		
	//jtextfield	
		JLabel nomLabel =new JLabel("Nom");
		JLabel prenomLabel =new JLabel("Prenom");
		JLabel numSecuLabel =new JLabel("Numero Secu");
		JLabel chambreLabel =new JLabel("Numero chambre");
		
		
		nomLabel.setBounds(10, 10, 100, 25);
		prenomLabel.setBounds(120, 10, 100, 25);
		numSecuLabel.setBounds(230, 10, 100, 25);
		chambreLabel.setBounds(340, 10, 100, 25);
		
		nom =new JTextField();
		prenom =new JTextField();
		numSecu =new JTextField();
		chambre =new JTextField();
		
		nom.setBounds(10, 40, 100, 25);
		prenom.setBounds(120, 40, 100, 25);
		numSecu.setBounds(230, 40, 100, 25);
		chambre.setBounds(340, 40, 100, 25);

		JButton filtrer = new JButton("filtrer");
		filtrer.setLocation(450,120);
		filtrer.setSize(100,25);
		
		filtrer.addActionListener(new ControleRechercheResident(this));
	
		//champs texts
		filtreResident.add(nomLabel);
		filtreResident.add(nom);
		filtreResident.add(prenomLabel);
		filtreResident.add(prenom);
		filtreResident.add(numSecuLabel);
		filtreResident.add(numSecu);
		filtreResident.add(chambreLabel);
		filtreResident.add(chambre);
		
		//button
		filtreResident.add(filtrer);

		//tableau resultat
		tableauResident.setLayout(null);
		tableauResident.setBounds(0, 150,1000,500);

		tableauResident.setBorder(new EmptyBorder(0, 0, 0, 0));
		tableauResident.setLayout(new BorderLayout(0, 0));
			
		JPanel panelInter =new JPanel();
		panelInter.setLayout(null);
		panelInter.setBounds(0, 0,1000,150);
		panelInter.setBackground(Color.darkGray);
		tableauResident.add(panelInter);
		
		
        this.table = new JTable();
        setListe(App.getEtablissement().getListResident());
        this.table.setBounds(0,0,1000,500);
        this.table.setAutoCreateRowSorter(true);
        
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);

        		int selectedIndex = table.convertRowIndexToModel(row);
        		
                Resident selection = null;
                for(Resident tmp : App.getEtablissement().getListResident())
                {
                	if(tmp.getNumSecu().equals(table.getModel().getValueAt(selectedIndex, 3)))
                		selection = tmp;
                }
                
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    new ControleResident(selection, false);
                }
                else
                {
                	resid = selection;
                }
            }
        });
        
      	JScrollPane scrollPane = new JScrollPane(table);
      	scrollPane.setBounds(0, 0,1000,550);
      	
        panelInter.add(scrollPane);
        
		this.add(filtreResident);
		this.add(tableauResident);
	
		JPanel boutonResident=new JPanel();
		JButton ajoutResident=new JButton("+nouveau résident");
		JButton ModifResident=new JButton("Modification");
		JButton AffichageResident=new JButton("Lecture fiche");
		
		boutonResident.setLayout(null);
		boutonResident.setBackground(Color.darkGray);
		
		boutonResident.setBounds(0,650,1000,100);
		ajoutResident.setBounds(400,20,200,25);
		ModifResident.setBounds(100,20,150,25);
		AffichageResident.setBounds(750,20,150,25);
		
		
		add(boutonResident);
		boutonResident.add(ajoutResident);
		boutonResident.add(ModifResident);
		boutonResident.add(AffichageResident);
		
		ajoutResident.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleResident(null, false);
			}                	
		});
		ModifResident.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleResident(resid, false);
			}                	
        });
		AffichageResident.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleResident(resid, true);
			}                	
	    });
	}
	
	
	public JTextField getNom() {
		return nom;
	}


	public JTextField getPrenom() {
		return prenom;
	}


	public JTextField getNumSecu() {
		return numSecu;
	}


	public JTextField getChambre() {
		return chambre;
	}


	public JTable getTable() {
		return table;
	}
	

	public void setListe(ArrayList<Resident> lr)
	{
		String column[]={"NOM","PRENOM","DATE de Naissance","Numero SECU","chambre",};
        this.table.setModel(new TableModelResident(lr, column));
	}
	
}
