	package vue;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.Resident;

public class FicheResident extends JPanel
{
	JPanel nomPanel;
	JPanel infoPanel;
	
	public FicheResident(Resident r)
	{
		setLayout(null);
		setBounds(0, 0, 1000, 750);
		setBackground(Color.yellow);
		
		nomPanel = new JPanel();
		infoPanel = new JPanel();
		
		//rentree dans nomPanel
		nomPanel.setLayout(null);
		nomPanel.setBounds(0, 0, 1000,150);
		nomPanel.setBackground(Color.orange);
		
		//A Changer avec les controleurs!!
		JLabel nomLabel =new JLabel(r.getNom());
		nomLabel.setBounds(500, 10, 100, 25);
		JLabel prenomLabel =new JLabel(r.getPrenom());
		prenomLabel.setBounds(10, 10, 100, 25);
		
		nomPanel.add(nomLabel);
		nomPanel.add(prenomLabel);
		
		//changement vers infoPanel
		infoPanel.setLayout(null);
		infoPanel.setBounds(0, 150, 1000,600);
		infoPanel.setBackground(Color.red);
		
		JLabel naissanceLabel =new JLabel("Date de Naissance");
		naissanceLabel.setBounds(10, 10, 110, 25);
		JLabel secuLabel =new JLabel("N° de securite sociale");
		secuLabel.setBounds(10, 50, 210, 25);
		JLabel contactLabel =new JLabel("Contact d'Urgence");
		contactLabel.setBounds(10, 190, 110, 25);
		JLabel nomContactLabel =new JLabel("Nom du Contact");
		nomContactLabel.setBounds(10, 230, 100, 25);
		JLabel numContactLabel =new JLabel("Numero du Contact");
		numContactLabel.setBounds(10, 270, 110, 25);
		JLabel lienLabel =new JLabel("Lien de Parente");
		lienLabel.setBounds(10, 310, 110, 25);
		JLabel chambreLabel =new JLabel("Chambre");
		chambreLabel.setBounds(750, 10, 100, 25);
		JLabel pathLabel =new JLabel("Pathologie(s)");
		pathLabel.setBounds(750, 110, 100, 25);
		JLabel allLabel =new JLabel("Allergie(s)");
		allLabel.setBounds(750, 210, 100, 25);
		JLabel textLabel =new JLabel("Texture");
		textLabel.setBounds(750, 310, 100, 25);

		
		infoPanel.add(naissanceLabel);
		infoPanel.add(secuLabel);
		infoPanel.add(contactLabel);
		infoPanel.add(nomContactLabel);
		infoPanel.add(numContactLabel);
		infoPanel.add(lienLabel);
		infoPanel.add(chambreLabel);
		infoPanel.add(pathLabel);
		infoPanel.add(allLabel);
		infoPanel.add(textLabel);
		
		JTextField dateNaissance =new JTextField(r.getDateNaissance());
		dateNaissance.setBounds(10, 30, 100, 25);
		JTextField numSecu =new JTextField(r.getNumSecu());
		numSecu.setBounds(10, 70, 100, 25);
		JTextField nomContact =new JTextField(r.getNomUrgence());
		nomContact.setBounds(10, 250, 100, 25);
		JTextField numContact =new JTextField(r.getNumUrgence());
		numContact.setBounds(10, 290, 100, 25);
		JTextField parentContact =new JTextField(r.getRelationUrgence());
		parentContact.setBounds(10, 330, 100, 25);
		JTextField chambre =new JTextField(r.getNumChambre());
		chambre.setBounds(750, 30, 100, 25);
		JTextField text =new JTextField(r.getNomRegime());
		text.setBounds(750, 330, 100, 25);
		
		infoPanel.add(dateNaissance);
		infoPanel.add(numSecu);
		infoPanel.add(nomContact);
		infoPanel.add(numContact);
		infoPanel.add(parentContact);
		infoPanel.add(chambre);
		infoPanel.add(text);
		
		JList pathList = new JList();
		pathList.setBounds(750, 130, 100, 25);
		JList allergieList = new JList();
		allergieList.setBounds(750, 230, 100, 25);
		
		infoPanel.add(pathList);
		infoPanel.add(allergieList);
		
		
		this.add(nomPanel);
		this.add(infoPanel);
	}
}
