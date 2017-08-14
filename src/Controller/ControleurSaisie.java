package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import Model.CustomModel;
import View.CheckListItem;
import View.EcranAnonymisation;
import View.EcranChoixTableau;
import View.EcranNombre;
import View.EcranSelectionColonne;
import View.Start;

public class ControleurSaisie {
	private JTable table;
	private CustomModel model;
	private ArrayList<Integer> selected;
	private Start fenetre;
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
		table.setAutoCreateColumnsFromModel(false);
    	table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public void createTableau(int nc, int nl) {
		String[] title = new String[nc];
    	for (int i = 0; i < nc; i++) {
    		int j = i+1;
    		title[i] = "C" + j;
    	}
    	this.model = new CustomModel(nl+1, title.length);
    	this.model.setColumnIdentifiers(title);
    	
    	this.model.setValueAt("Ligne", 0, 0);
    	for (int i = 1; i < nl+1; i++) {
    		model.setValueAt(new Integer(i).intValue(), i, 0);
    	}
    	this.setTable(new JTable(model));
	}
	
	public void launchChoice() {
		EcranChoixTableau choice = new EcranChoixTableau(this);
		choice.setVisible(true);
	}
	
	public void launchAjout(String choix) {
		EcranNombre nombre = new EcranNombre(this, choix);
		nombre.setVisible(true);
	}
	
	public ControleurSaisie() {
		this.createTableau(1, 1);
		Start fenetre = new Start(this);
		this.fenetre = fenetre;
		this.fenetre.setVisible(true);
	}
	
	public void addC(int n) {
		
		int nc = this.model.getColumnCount();
		for (int i = 0; i < n; i++) {
			int num = nc+i+1;
			this.model.addColumn("C" + num);
		}
		this.setTable(new JTable(this.model));
		this.fenetre.refreshUI();
	}
	
	public void addL(int l) {
		for (int i = 0; i < l; i++) {
    		Object[] row = new Object[this.table.getColumnCount()];
    		row[0] = this.table.getRowCount();
        	for (int j = 1; j < this.table.getColumnCount(); j++)
        		row[j] = null;
        	this.model.addRow(row);
    	}
		this.fenetre.refreshUI();
	}
	
	public void reorderIndex() {
    	this.getTable().setValueAt("Ligne", 0, 0);
    	for (int i = 1; i < this.getTable().getRowCount(); i++) {
    		this.getTable().setValueAt(i, i, 0);
    	}
    }

	public void removeRows() {
		// TODO Auto-generated method stub
		CustomModel model = this.model;
		int row = this.table.getSelectedRow();
        while (row != -1)
        {
            model.removeRow(row);
            row = this.table.getSelectedRow();
        }
		this.fenetre.refreshUI();
	}
	
	public void removeColumns() {
		int col = this.table.getSelectedColumn();
		
		while (col != -1)
		{
			this.table.removeColumn(this.table.getColumnModel().getColumn(col));
			for (int i = 0; i < table.getRowCount(); i++) {
				Vector v = (Vector) model.getDataVector().elementAt(i);
				v.removeElementAt(col);
			}
			col = this.table.getSelectedColumn();
		}
		this.model.fireTableDataChanged();
		this.fenetre.refreshUI();
	}
	
	public void launchColumnSelection() {
		Vector row = (Vector) this.model.getDataVector().elementAt(0);
		CheckListItem[] objets = new CheckListItem[this.getTable().getColumnCount()];
		for (int i = 0; i < row.size(); i++) {
			if (row.get(i) != null) {
				objets[i] = new CheckListItem((String) row.get(i));
			}
		}
		EcranSelectionColonne choice = new EcranSelectionColonne(objets, this);
		choice.setVisible(true);
	}
	
	public void anonymize(ArrayList<Integer> list, int option) {
		if (list.size() > 0) {
			CustomModel model = (CustomModel) table.getModel();
			Vector titre = (Vector) model.getDataVector().get(0);
			model.removeRow(0);
			Collections.shuffle(model.getDataVector());
			if (option == 1) {
				for (Integer i : list) {
					for (int j = 0; j < model.getRowCount(); j++) {
						Vector v = (Vector) model.getDataVector().elementAt(j);
						String t1 = (String) titre.elementAt(i.intValue()) + j + 1;
						v.set(i.intValue(), t1);
					}
				}
			}
			else if (option == 2) {
				for (Integer i : list) {
					for (int j = 0; j < model.getRowCount(); j++) {
						Vector v = (Vector) model.getDataVector().elementAt(j);
						v.set(i.intValue(), new String("*"));
					}
				}
			}
			model.insertRow(0, titre);
			model.fireTableDataChanged();
			this.reorderIndex();
			model.setColumnIdentifiers(titre);
			
			this.selected.clear();
			model.fireTableDataChanged();
		}
		
	}
	
	public void open(File file) throws FileNotFoundException, IOException {
		final ObjectInputStream in = new ObjectInputStream(
		        new BufferedInputStream(new FileInputStream(file)));

		    try {
				final Vector dataFromFile = (Vector) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void save() {
		final ObjectOutputStream out = new ObjectOutputStream(
		        new BufferedOutputStream(new FileOutputStream(FILE_NAME)));

		    try {
		      out.writeObject(products);
		    } finally {
		      out.close();
		    }
	}
	
	public void launchAnonymousMethod(DefaultListModel<String> noms) {
		EcranAnonymisation anonyme = new EcranAnonymisation(noms, this);
		anonyme.setVisible(true);
	}
	
	public ArrayList<Integer> getSelected() {
		return selected;
	}
	
	public void setSelected(ArrayList<Integer> selected) {
		this.selected = selected;
	}

	public CustomModel getModel() {
		return model;
	}

	public void setModel(CustomModel model) {
		this.model = model;
	}

	public Start getFenetre() {
		return fenetre;
	}

	public void setFenetre(Start fenetre) {
		this.fenetre = fenetre;
	}
}
