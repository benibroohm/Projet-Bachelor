package Model;

import javax.swing.table.DefaultTableModel;

/**
 * Classe qui structure notre tableau
 * 
 * @author Beni Broohm
 *
 */
public class CustomModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomModel(int j, int k) {
		super(j, k);
	}
}
