package Model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CustomModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomModel(int j, int k) {
		super(j, k);
	}
		
	public void removeColumn(int n) {
		Vector v = this.getDataVector();
		for (int i = 0; i < v.size(); i++) {
			v.remove(n);
		}
	}
	
	public void removeColumn(ArrayList<Integer> ind) {
		Vector data = this.getDataVector();
		Vector<Vector<Object>> newData = new Vector<Vector<Object>>();

		int lon = data.size();
		int lar = ((Vector) data.elementAt(0)).size();
		for (int i = 0; i < lon; i++) {
			Vector<Object> present = new Vector<Object>();
			Vector ligne = (Vector) data.elementAt(i);
			for (int j = 0; j < lar; j++) {
				if (!(ind.contains(new Integer(j))))
					present.add(ligne.get(j));
			}
			newData.add(present);
		}
				
		this.setDataVector(newData, (Vector) newData.elementAt(0));
		this.fireTableDataChanged();
	}

}
