package utils;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Evenement;

public class BoxTable extends JPanel 
{

	private int gravite;
	private String titre;
	private Date date;
	public BoxTable(Evenement e, int pos, boolean isAdmin)
	{
		setLayout(null);
		setBackground(Color.cyan);
		this.titre=e.getTitre();
		this.gravite=e.getGravite();
		this.date=e.getDateEmission();
		
		setBounds(5, 	pos*50+5+5*pos, 	isAdmin ? 315 : 490, 	50);


		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dateSt=df.format(this.date);		
		String importance=String.valueOf(this.gravite);
		
		JLabel dateAffichage = new JLabel(dateSt);
		JLabel titreAffichage = new JLabel(this.titre);
		JLabel graviteAffichage = new JLabel(importance);
		
		dateAffichage.setBounds(220,30,100,10);
		titreAffichage.setBounds(0, 0, 100, 10);
		graviteAffichage.setBounds(270,0,100,10);
		
		add(dateAffichage);
		add(graviteAffichage);
		add(titreAffichage);


	}
}
