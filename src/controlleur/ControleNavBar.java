package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.App;
import vue.ViewPersonnel;
import vue.ViewRegistre;
import vue.ViewResident;

public class ControleNavBar implements ActionListener {

	private int i;
	
	public ControleNavBar(int i) {
		this.i=i;
	}
	
	/**
	 * est appelé au moment du clic sur navBar
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()!=null) {
	
			
			switch (i) {
	
				case 1:
					App.getContent().removeAll();
					for (int j=0;j<App.getEtablissement().getListRegistre().size();j++) {
						if(App.getEtablissement().getListRegistre().get(j).getService().equals("Global")) {
							App.getContent().add(new ViewRegistre(App.getEtablissement().getListRegistre().get(j))); 
					
						}
					}
					App.getContent().repaint();
					App.getContent().revalidate();
					
				break;
		
				case 2:
					App.getContent().removeAll();
					for (int j=0;j<App.getEtablissement().getListRegistre().size();j++) {
						if(App.getEtablissement().getListRegistre().get(j).getService().equals(App.getConnected().getService())) {
							App.getContent().add(new ViewRegistre(App.getEtablissement().getListRegistre().get(j))); 
						}
					}
					App.getContent().repaint();
					App.getContent().revalidate();

				break;
		
				case 4:
					App.getContent().removeAll();
					App.getContent().add(new ViewResident()) ;
					App.getContent().repaint();
					App.getContent().revalidate();

				break;
		
				case 5:
					App.getContent().removeAll();
					App.getContent().add(new ViewPersonnel());
					App.getContent().repaint();
					App.getContent().revalidate();
					
				break;
		
//				case 6: 
//					App.getContent().removeAll();
//					App.getContent().add(new ViewAgenda(App.getConnected()));
//					App.getContent().repaint();
//					App.getContent().revalidate();
//					
//				break;
			}
		}
	}
}
