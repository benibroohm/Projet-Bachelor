package Model;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Tableau {
    /**
	 * 
	 */
	private DefaultTableModel model;
	private JTable table;
    public Tableau(int nc, int nl) {
    	String[] title = new String[nc];
    	for (int i = 0; i < nc; i++) {
    		int j = i+1;
    		title[i] = "C" + j;
    	}
    	this.model = new DefaultTableModel(nl+1, title.length) ;
    	model.setColumnIdentifiers(title);
    	this.table = new JTable(model);
    	table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
    }

    public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	//Retourne le nombre de colonnes
    public int getColumnCount() {
      return this.table.getColumnCount();
    }

    //Retourne le nombre de lignes
    public int getRowCount() {
      return this.table.getRowCount();
    }

    //Retourne la valeur à l'emplacement spécifié
    public Object getValueAt(int row, int col) {
      return this.table.getValueAt(row, col);
    }
    
    public void addColonnes(int c) {
    	int nc = this.table.getColumnCount();
    	for (int i = 0; i < c; i++) {
    		nc += i + 1;
    		String colName = "C" + nc;
        	this.model.addColumn(colName);
    	}
    }
    
    public void addLignes(int l) {
    	for (int i = 0; i < l; i++) {
    		Object[] row = new Object[this.table.getColumnCount()];
        	for (int j = 0; j < this.table.getColumnCount(); j++)
        		row[j] = null;
        	this.model.addRow(row);
    	}
    }
  }

