package vue;

import java.awt.Color;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controlleur.ControleFinEvenement;
import modele.Evenement;
import modele.Personnel;
import utils.TableModelResident;

public class ViewEvenement extends JPanel {

	/**
	 * 
	 * Constructeur d'un Panel d'evenement individuel pour afficher un evenement passer en parametre
	 * 
	 * @param Evenement e
	 * @see Evenement 
	 * 
	 */
	public ViewEvenement(Evenement e)
	{
		setBounds(0,0,1000,750);
		setBackground(Color.gray);
		this.setLayout(null);
		
		Personnel p = e.getDeclarant();
		
		
		JLabel importance = new JLabel("Niveau d'importance : " + Integer.toString(e.getGravite()), SwingConstants.CENTER);
		JLabel date = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(e.getDateEmission()), SwingConstants.CENTER);
		JLabel titre = new JLabel(e.getTitre(), SwingConstants.CENTER);
		JTextArea declarant = new JTextArea("\tDéclarant :\n Nom/Prenom :\t" + p.getNom() + " " + p.getPrenom() + "\n N° de Poste :\t" + p.getNumTelPoste() + "\n Service :\t" + p.getService());
		JTextArea description = new JTextArea(e.getDescription());
		JLabel listeResidentTexte = new JLabel("Residents concernés :", SwingConstants.CENTER);
		JTable listeResident = new JTable();
		String column[]={"NOM","PRENOM","DATE de Naissance","Numero SECU","chambre"};
		JScrollPane scrollPaneResident = new JScrollPane(listeResident);
		JButton boutonRetour = new JButton("retour");
		
		importance.setBounds(115, 10, 200, 25);
		importance.setBackground(Color.LIGHT_GRAY);
		importance.setOpaque(true);
		
		date.setBounds(115, 40, 200, 25);
		date.setBackground(Color.LIGHT_GRAY);
		date.setOpaque(true);
		
		titre.setBounds(10, 70, 650, 25);
		titre.setBackground(Color.LIGHT_GRAY);
		titre.setOpaque(true);
		
		declarant.setBounds(665, 10, 295, 85);
		declarant.setBackground(Color.LIGHT_GRAY);
		declarant.setOpaque(true);
		declarant.setEditable(false);
		
		description.setBounds(10,100,950,300);
		description.setBackground(Color.LIGHT_GRAY);
		description.setOpaque(true);
		description.setEditable(false);
		
		listeResidentTexte.setBounds(10, 405, 950, 25);
		listeResidentTexte.setBackground(Color.LIGHT_GRAY);
		listeResidentTexte.setOpaque(true);
		
		listeResident.setModel(new TableModelResident(e.getListeResident(), column));
		listeResident.setBounds(0, 0, 950, 500);

        scrollPaneResident.setBounds(10,435,950,200);
		
        boutonRetour.setBounds(10, 10, 100, 30);
        boutonRetour.addActionListener(new ControleFinEvenement(this));
        
		this.add(importance);
		this.add(date);
		this.add(titre);
		this.add(declarant);
		this.add(description);
		this.add(listeResidentTexte);
		this.add(scrollPaneResident);
		this.add(boutonRetour);
	}
}
