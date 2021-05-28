package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.App;
import vue.Connection;

public class ControleDeconnexion implements ActionListener{
	
	public ControleDeconnexion() {}

	@Override

	public void actionPerformed(ActionEvent e) 
	{
		/*
		 * removeData 	remet �tablisssement � null 
		 * 				d�connecte l'utilisateur
		 */
		App.removeData();
		App.getContent().removeAll();
		App.getApp().getContentPane().removeAll();
		
		//garbage collector supprime tous les �l�ments � null
		System.gc();
		
		App.getApp().getContentPane().add(new Connection());
		App.getApp().getContentPane().repaint();
		App.getApp().getContentPane().revalidate();		
	}

}
