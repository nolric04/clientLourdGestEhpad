package vue;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JFrame;

import modele.DaoAccess;
import modele.Etablissement;
import modele.Personnel;

public class App extends JFrame {
	
	public static Color defaultBack = Color.lightGray;
	public static Color defaultCont = Color.darkGray;
	public static Font commonFont = new Font("Arial", Font.BOLD, 15);
	
	private static Content content = new Content();
	private static DaoAccess accesBdd = new DaoAccess("localhost", "gestehpad", "gestEhpad", "1234", null);
	private static Etablissement etabe;
	private static Personnel connected;
	
	private static App app;
	
	public App(String str) {
		super(str);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200,800);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) throws SQLException {
        
	
		app = new App("Gest'Ehpad");
			
		app.getContentPane().add(new Connection());
		app.getContentPane().repaint();
		app.getContentPane().revalidate();
		
	}
	
	
	/**
	 * @return objet établissement
	 */
	public static Etablissement getEtablissement(){
	        return etabe;
	 } 

	/**
	 * 
	 * @param e modifie l'objet Etablissement
	 */
	public static void setEtablissement(Etablissement e){
	        etabe = e;
	 }  
	
	/**
	 * 
	 * @return l'objet BDD
	 */
    public static DaoAccess getBDD(){
        return accesBdd;
    }
    
    /**
     * 
     * @return l'objet de la personne connectée
     */
	public static Personnel getConnected() {
		return connected;
	}
	
	/**
	 * 
	 * @param p modifie la personne connectée
	 */
	public static void setConnected(Personnel p)
	{
		connected = p;
	}

	/**
	 * 
	 * @return pour obtenir l'instance de la page en cours
	 */
	public static Content getContent() {
		return content;
	}

	/**
	 * 
	 * @return pour obtenir l'instance de l'application
	 */
	public static App getApp()
	{
		return app;
	}

	/**
	 * remet à null établissement et la personne connectée
	 */
	public static void removeData()
	{
		etabe = null;
		connected = null;
	}

	/**
	 * reset le contenu
	 */
	public static void resetContent() {
		content = new Content();	
	}


	

}
