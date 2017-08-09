package Controller;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Model.Tableau;
import View.EcranChoixTableau;
import View.Start;

public class ControleurSaisie {
	public Tableau table;
	private Start fenetre;
	public void createTableau(int nc, int nl) {
		this.table = new Tableau(nc, nl);
	}
	
	public void launchChoice() {
		EcranChoixTableau choice = new EcranChoixTableau(this);
		choice.setVisible(true);
	}
	
	public void updateTable() {
		this.fenetre.updateTable(this.table);
		System.out.println(this.fenetre.table.getRowCount());
	}
	
	public ControleurSaisie() {
		this.table = new Tableau(1, 1);
		Start fenetre = new Start(this);
		this.fenetre = fenetre;
		this.fenetre.setVisible(true);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
		ControleurSaisie saisie = new ControleurSaisie();
	}
}
