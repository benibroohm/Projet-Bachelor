/**
 * Program.java : programme principal qui démarre l'ensemble de l'application
 * 
 * @author Beni Broohm
 * @version 1.0 - Init
 * @depuis 5/09/2017
 * 
 * Cette classe crée un Thread spécial pour l'exécution de l'UI
 */

package Controller;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new ControleurSaisie();
			}
		});
	}
}
