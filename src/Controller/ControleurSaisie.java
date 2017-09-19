/**
 * Controleur principal de tout notre programme, le Big Boss qui g�re tout
 * - Il se charge de tout ce qui concerne la saisie (ajout, suppression de lignes/colonnes)
 * - Il se charge �galement de tout ce qui concerne la s�curit� (chiffrement des donn�es)
 * - Il se charge finalement de la partie anonymisation
 * 
 * @author Beni Broohm
 * @version 1.0 - Init
 * @depuis 05/09/2017
 * 
 * N.B : Tous les cas d'erreur sont trait�s de la m�me mani�re : on affiche une fen�tre d'erreur et on arr�te la fonction qui cause l'erreur
 */

package Controller;

/* Imports divers */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.crypto.NoSuchPaddingException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.CryptoUtils;
import Model.CustomModel;
import View.CheckListItem;
import View.EcranAnonymisation;
import View.EcranChoixTableau;
import View.EcranCorrespondances;
import View.EcranErreur;
import View.EcranNombre;
import View.EcranSelectionColonne;
import View.FenetreCrypto;
import View.SaveR;
import View.Start;

public class ControleurSaisie {
	// Ensemble des variables n�cessaires pour l'ex�cution
	private JTable table;
	private CustomModel model; // Notre mod�le pour le tableau, qui contient les donn�es
	private ArrayList<Integer> selected;
	private Start fenetre;
	private JTextField filename = new JTextField(), dir = new JTextField(); // Contient le chemin d'acc�s pour acc�der � un fichier
	private boolean pathSet;
	private String folderPath; // Chemin d'acc�s en cas de s�lection/cr�ation d'un projet
	private Vector<Vector<String>> link; // N�cessaire pour la sauvegarde des correspondances
	private boolean isPseudo;
	
	/**
	 * Retourne un JTable
	 * @return
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Setter pour le tableau
	 * @param table
	 */
	public void setTable(JTable table) {
		this.table = table;
		table.setAutoCreateColumnsFromModel(false);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	/**
	 * Cr�e un nouveau tableau avec les param�tres nc et nl
	 * @param nc
	 * @param nl
	 */
	public void createTableau(int nc, int nl) {
		String[] title = new String[nc];
		for (int i = 0; i < nc; i++) {
			title[i] = "Colonne";
		}
		this.model = new CustomModel(nl+1, title.length);
		this.model.setColumnIdentifiers(title);

		this.model.setValueAt("Ligne", 0, 0);
		for (int i = 1; i < nl+1; i++) {
			model.setValueAt(new Integer(i).intValue(), i, 0);
		}
		this.setTable(new JTable(model));
	}

	/**
	 * Affiche une interface pour permettre au chercheur de cr�er une nouvelle table
	 */
	public void launchChoice() {
		new EcranChoixTableau(this).setVisible(true);
	}

	/**
	 * Permet d'afficher une interface pour l'ajout de lignes/colonnes
	 * @param choix
	 */
	public void launchAjout(String choix) {
		new EcranNombre(this, choix).setVisible(true);
	}

	/**
	 * Instance d'un nouveau contr�leur
	 */
	public ControleurSaisie() {
		this.createTableau(1, 1); // On cr�e un nouveau tableau
		Start fenetre = new Start(this); // On affiche la fen�tre principale
		this.fenetre = fenetre;
		this.fenetre.setVisible(true);
		dir.setEditable(false);
		filename.setEditable(false);
		this.pathSet = false;
	}

	/**
	 * Fonction pour ajouter n colonnes � notre mod�le de tableau
	 * @param n
	 */
	public void addC(int n) {
		for (int i = 0; i < n; i++) {
			this.model.addColumn("Colonne");
		}
		this.setTable(new JTable(this.model));
		this.fenetre.refreshUI();
	}

	/**
	 * Fonction pour ajouter l lignes � notre mod�le de tableau
	 * @param l
	 */
	public void addL(int l) {
		for (int i = 0; i < l; i++) {
			Object[] row = new Object[this.table.getColumnCount()]; // On cr�e un array d'objets de la taille #nombre_colonnes
			row[0] = this.table.getRowCount(); // On met le num�ro de la ligne � la colonne 0
			for (int j = 1; j < this.table.getColumnCount(); j++)
				row[j] = null; // On ajoute des null aux colonnes suivantes
			this.model.addRow(row);
		}
		this.fenetre.refreshUI(); // Mise � jour
	}

	/**
	 * Fonction pour r�ordonner les index des lignes
	 */
	public void reorderIndex() {
		this.getTable().setValueAt("Ligne", 0, 0);
		for (int i = 1; i < this.getTable().getRowCount(); i++) {
			this.getTable().setValueAt(i, i, 0);
		}
		this.model.setColumnIdentifiers((Vector<?>) this.model.getDataVector().get(0));
	}

	/**
	 * Fonction pour la suppression des lignes s�lectionn�es
	 */
	public void removeRows() {
		CustomModel model = this.model;
		// On it�re sur l'ensemble des lignes s�lectionn�es et on supprime la ligne
		int row = this.table.getSelectedRow();
		while (row != -1 && row != 0)
		{
			model.removeRow(row);
			row = this.table.getSelectedRow();
		}
		this.fenetre.refreshUI();
	}

	/**
	 * Fonction pour la suppression des colonnes s�lectionn�es
	 */
	public void removeColumns() {
		int[] col = this.table.getSelectedColumns(); // On obtient l'ensemble des colonnes s�lectionn�es
		// On met l'ensemble de ces colonnes dans une liste
		ArrayList<Integer> listCol = new ArrayList<Integer>();
		for(int i : col) {
			listCol.add(new Integer(i));
		}
		
		// Si la colonne 0 est s�lectionn�e, on l'enl�ve
		if (listCol.contains(new Integer(0)))
			listCol.remove(new Integer(0));
		
		// On choisit ensuite l'ensemble des colonnes qu'on ajoute � une liste
		ArrayList<Integer> listInt = new ArrayList<Integer>();
		for (int i = 0; i < this.model.getColumnCount(); i++)
			listInt.add(new Integer(i));
		// On retire de la liste de toutes les colonnes les colonnes � supprimer
		listInt.removeAll(listCol);
		
		// On initialise un vecteur de donn�es v et on copie les donn�es � ne pas supprimer dans v
		@SuppressWarnings("unchecked")
		Vector<Vector<?>> data = this.model.getDataVector();
		Vector<Vector<?>> v = new Vector<Vector<?>>(this.model.getRowCount());
		
		for (int i = 0; i < this.model.getRowCount(); i++)
			v.addElement(new Vector<String>(listInt.size()));
		
		for(int i = 0; i < this.model.getRowCount(); i++) {
			Vector<String> vv = new Vector<String>();
			for(Integer ind : listInt) {
				Object toSet = null;
				if (data.elementAt(i).elementAt(ind.intValue()) != null)
					//System.out.println(data.elementAt(i).elementAt(ind.intValue()));
					toSet = new String(data.elementAt(i).elementAt(ind.intValue()).toString());
				vv.add((String) toSet);
			}
			v.set(i, vv);
		}
		this.model.setDataVector(v, v.get(0));
		this.model.fireTableDataChanged();
		this.setTable(new JTable(this.model)); // On met � jour la table et on fait du rangement
		this.fenetre.refreshUI();
		this.reorderIndex();
	}

	/**
	 * Fonction qui affiche une interface de s�lection des colonnes pour l'anonymisation
	 */
	public void launchColumnSelection() {
		Vector<?> row = (Vector<?>) this.model.getDataVector().elementAt(0); // On obtient toutes les colonnes
		boolean isOk = true;
		for (int i = 1; i < row.size(); i++) {
			Object o = row.get(i);
			if (o == null)
				isOk = false;
		}
		if (row.size() > 1 && isOk == true) {
			// On pr�sente les colonnes
			CheckListItem[] objets = new CheckListItem[this.getTable().getColumnCount()];
			for (int i = 0; i < row.size(); i++) {
				if (row.get(i) != null) {
					objets[i] = new CheckListItem((String) row.get(i));
				}
			}
			// On pr�sente apr�s les colonnes choisies
			new EcranSelectionColonne(objets, this).setVisible(true);
		}
	}

	/**
	 * Fonction principale  pour l'anonymisation
	 * @param list : liste des colonnes choisies
	 * @param option : option pour l'anonymisation ; 1 - pseudonymisation, 2 - k-anonymisation
	 */
	@SuppressWarnings("unchecked")
	public void anonymize(ArrayList<Integer> list, int option) {
		// Si on a choisi des colonnes
		if (list.size() > 0) {
			CustomModel model = (CustomModel) table.getModel();
			Vector<String> titre = (Vector<String>) model.getDataVector().get(0); // On prend la premi�re ligne qu'on enl�ve
			model.removeRow(0);
			Collections.shuffle(model.getDataVector()); // On shuffle les lignes
			if (option == 1) {
				Vector<Vector<String>> link = new Vector<Vector<String>>(model.getRowCount()); // On initialise le vecteur de correspondances
				for (int j = 0; j < model.getRowCount(); j++)
					link.add(j, new Vector<String>(list.size()));
				int k = 0;
				// Pour chaque ligne et pour les colonnes s�lectionn�es
				for (Integer i : list) {
					for (int j = 0; j < model.getRowCount(); j++) {
						Vector<String> v = (Vector<String>) model.getDataVector().elementAt(j);
						String t1 = (String) titre.elementAt(i.intValue()) + "#" + new Integer(j + 1).toString(); // On cr�e la donn�e � mettre
						Vector<String> vl = (Vector<String>) link.elementAt(j);
						String toSet = v.get(i) + ":" + t1;
						vl.add(k, toSet); // On ajoute la correspondance
						v.set(i.intValue(), t1); // On change les donn�es
					}
					k += 1;
				}
				Vector<String> v = new Vector<String>(list.size());
				for (Integer i : list)
					v.add(titre.get(i));
				link.add(0, v); // On ajoute le titre au vecteur de correspondance
				this.link = link;
				this.setPseudo(true); // On dit que la derni�re anonymisation est une pseudonymisation et qu'on a des correspondances
			}
			// Dans le cas de la k-anonymisation
			else if (option == 2) {
				for (Integer i : list) {
					for (int j = 0; j < model.getRowCount(); j++) {
						Vector<String> v = (Vector<String>) model.getDataVector().elementAt(j);
						v.set(i.intValue(), new String("*")); // On supprime simplement les donn�es
					}
				}
				this.setPseudo(false); // On met � jour le type de la derni�re anonymisation
			}
			model.insertRow(0, titre); // On remet le titre des colonnes
			model.fireTableDataChanged();
			this.reorderIndex();
			model.setColumnIdentifiers(titre);

			this.selected.clear(); // On enl�ve les colonnes pr�c�demment s�lectionn�es
			model.fireTableDataChanged();
		}

	}

	/**
	 * Fonction pour ouvrir un fichier *anonymis�* uniquement
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void open() throws IOException {
		this.setPathIfNot(); // On met � jour le chemin d'acc�s
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(
					new FileInputStream(dir.getText()+"/"+filename.getText()));
		} catch (FileNotFoundException e1) {
			new EcranErreur(e1.getMessage()).setVisible(true);
			return;
			
		} catch (IOException e1) {
			new EcranErreur(e1.getMessage()).setVisible(true);
			return;
		}
		
		// On essaye d'ouvrir le fichier et de lire l'objet repr�sentant nos donn�es
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<String>> data = (Vector<Vector<String>>) in.readObject();
			in.close();
			model.setDataVector(data, data.elementAt(0));
			this.setTable(new JTable(this.model)); // On affiche la table contenant les nouvelles donn�es
			this.fenetre.refreshUI();
		} catch (ClassNotFoundException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			in.close();
			return;
		}
	}

	/**
	 * Fonction pour sauvegarder un fichier anonymis�
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public void save() throws FileNotFoundException, IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		this.setPathIfNot(); // On met en place le chemin d'acc�s
		String fname = new String(dir.getText()+"/"+filename.getText()); // On ouvre le fichier avec le bon nom
		if (!fname.endsWith(".objet"))
			fname = fname.concat(".objet");
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(fname));

		try {
			out.writeObject(model.getDataVector()); // On �crit notre objet
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			out.close();
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
		}
		new SaveR(this.fenetre, this).actionPerformed(null);
	}

	/**
	 * Fonction pour la sauvegarde d'un fichier encrypt�
	 * 
	 * @param key
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public void saveEncrypted(String key) throws FileNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		this.setPathIfNot();
		String fname = new String(dir.getText()+"/"+filename.getText()); // On essaye d'ouvrir avec le bon nom
		if (!fname.endsWith(".encrypted"))
			fname = fname.concat(".encrypted");
		OutputStream baos = new FileOutputStream(fname); // On ouvre un flux
		try {
			CryptoUtils.encryptO(key, model.getDataVector(), baos); // On encrypte
		} catch (InvalidAlgorithmParameterException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			baos.close();
			return;
		}
	}

	/**
	 * Fonction pour d�chiffrement d'un fichier crypt�
	 * 
	 * @param key
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void openEncrypted(String key) throws FileNotFoundException, IOException {
		this.setPathIfNot();
		InputStream inputStream = new FileInputStream(dir.getText()+"/"+filename.getText());
		try {
			Vector<Vector<String>> data = null;
			try {
				data = (Vector<Vector<String>>) CryptoUtils.decryptO(key, inputStream); // On d�crypte le fichier
			} catch (InvalidAlgorithmParameterException e) {
				new EcranErreur(e.getMessage()).setVisible(true);
				return;
			}
			if (data == null)
				return;
			model.setDataVector(data, data.elementAt(0));
			this.setTable(new JTable(this.model));
			this.fenetre.refreshUI();
		} catch (InvalidKeyException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (NoSuchAlgorithmException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (NoSuchPaddingException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		inputStream.close();
	}

	/**
	 * Fonction pour la cr�ation d'un projet. La fonction cr�e 3 sous-dossiers et un fichier .info qui permet d'avoir le chemin
	 * d'acc�s.
	 * 
	 * @throws IOException
	 */
	public void createProject() throws IOException {
		try {
			Files.createDirectories(Paths.get(dir.getText()+"/"+filename.getText()+"/Anonymes"));
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		try {
			Files.createDirectories(Paths.get(dir.getText()+"/"+filename.getText()+"/Originaux"));
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		try {
			Files.createDirectories(Paths.get(dir.getText()+"/"+filename.getText()+"/Correspondances"));
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		File file = new File(dir.getText()+"/"+filename.getText()+"/"+filename.getText()+".info");

		FileWriter writer;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		try {
			writer.write(dir.getText()+"/"+filename.getText());
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			writer.close();
			return;
		}
		writer.close();
		this.setFolderPath(dir.getText()+"/"+filename.getText());
		this.pathSet = true;
	}

	/**
	 * Fonction pour ouvrir un projet. Cette fonction met juste en place le bon chemin d'acc�s.
	 * 
	 * @throws IOException
	 */
	public void openProject() throws IOException {
		this.setFolderPath(dir.getText());
		this.pathSet = true;

		// On choisit le fichier .info ici.
		JFileChooser c = new JFileChooser(new File(this.getFolderPath()+"/Originaux"));
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(this.fenetre);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			this.getFilename().setText(c.getSelectedFile().getName());
			this.getDir().setText(c.getCurrentDirectory().toString());
			this.launchKeyGet(2); // On ouvre ensuite une fen�tre pour que le chercheur choisisse un fichier de donn�es originales.
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			this.getFilename().setText("You pressed cancel");
			this.getDir().setText("");
		}
	}

	/**
	 * Cette fonction affiche seulement le r�capitulatif des colonnes s�lectionn�es
	 * @param noms
	 */
	public void launchAnonymousMethod(DefaultListModel<String> noms) {
		new EcranAnonymisation(noms, this).setVisible(true);
	}

	/*
	 * Section des setters/getters
	 */
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

	public JTextField getFilename() {
		return filename;
	}

	public void setFilename(JTextField filename) {
		this.filename = filename;
	}

	public JTextField getDir() {
		return dir;
	}

	public void setDir(JTextField dir) {
		this.dir = dir;
	}

	/**
	 * Fonction pour ouvrir une interface pour entrer un code secret.
	 * Option d�signe le mode pour lequel on ex�cute la fonction.
	 * 
	 * @param option
	 */
	public void launchKeyGet(int option) {
		new FenetreCrypto(this, option).setVisible(true);
	}

	public boolean isPathSet() {
		return pathSet;
	}

	public void setPathSet(boolean pathSet) {
		this.pathSet = pathSet;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * Fonction qui met en place un path s�lectionn� plus haut par une fonction open ou save.
	 */
	public void setPathIfNot() {
		if (!this.pathSet) {
			this.setFolderPath(dir.getText()+"/");
			this.pathSet = true;
		}
	}

	public boolean isPseudo() {
		return isPseudo;
	}

	public void setPseudo(boolean isPseudo) {
		this.isPseudo = isPseudo;
	}

	/**
	 * Fonction qui permet 
	 * @param string
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws FileNotFoundException
	 */
	public void saveLink(String string) throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException {

		if (this.isPseudo()) {
			String fname = new String(dir.getText()+"/"+filename.getText()); // On ouvre le fichier choisi
			if (!fname.endsWith(".link"))
				fname = fname.concat(".link");

			OutputStream baos = new FileOutputStream(fname);
			try {
				CryptoUtils.encryptO(string, link, baos); // On d�chiffre
			} catch (InvalidAlgorithmParameterException e) {
				new EcranErreur(e.getMessage()).setVisible(true); // Cas d'erreur
				return;
			} catch (InvalidKeyException e) {
				new EcranErreur(e.getMessage()).setVisible(true);
				return;
			}
		}
	}

	/**
	 * Fonction pour ouvrir une petite fen�tre qui affiche le contenu des fichiers de correspondance.
	 * 
	 * @param key
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void openLink(String key) throws IOException {
		this.setPathIfNot();
		InputStream inputStream = new FileInputStream(dir.getText()+"/"+filename.getText());
		Vector<Vector<String>> data = null;
		try {
			data = (Vector<Vector<String>>) CryptoUtils.decryptO(key, inputStream); // On d�crypte et on g�re les erreurs
		} catch (InvalidKeyException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (NoSuchAlgorithmException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (NoSuchPaddingException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (InvalidAlgorithmParameterException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
		inputStream.close();
		new EcranCorrespondances(data).setVisible(true);
	}
}
