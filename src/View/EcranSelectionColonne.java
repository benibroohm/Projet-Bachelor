package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

import Controller.ControleurSaisie;

public class EcranSelectionColonne extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> selectedString;
	@SuppressWarnings("unchecked")
	public EcranSelectionColonne(CheckListItem[] columns, ControleurSaisie control) {
		if (columns.length == 0)
			dispose();
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize(new Dimension(450, 371));
		setLocationRelativeTo(null);
		setTitle("S\u00E9lect. colonnes");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblColonnesSensibles = new JLabel("S\u00E9lection colonnes");
		springLayout.putConstraint(SpringLayout.NORTH, lblColonnesSensibles, 33, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblColonnesSensibles, 163, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblColonnesSensibles, 55, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblColonnesSensibles, 269, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblColonnesSensibles);
	
		
		@SuppressWarnings({ "rawtypes" })
		JList list = new JList(columns);
		    list.setCellRenderer(new CheckListRenderer());
		    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		    list.addMouseListener(new MouseAdapter() {
		      @Override
		      public void mouseClicked(MouseEvent event) {
		        @SuppressWarnings("rawtypes")
				JList list = (JList) event.getSource();
		        
		        int index = list.locationToIndex(event.getPoint());// Get index of item
		                                                           // clicked
		        CheckListItem item = (CheckListItem) list.getModel()
		            .getElementAt(index);
		        item.setSelected(!item.isSelected()); // Toggle selected state
		        list.repaint(list.getCellBounds(index, index));// Repaint cell
		        
		      }
		    });
			JScrollPane scrollPane = new JScrollPane(list);
			springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblColonnesSensibles);
			springLayout.putConstraint(SpringLayout.WEST, scrollPane, 112, SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 196, SpringLayout.SOUTH, lblColonnesSensibles);
			springLayout.putConstraint(SpringLayout.EAST, scrollPane, 307, SpringLayout.WEST, getContentPane());
			getContentPane().add(scrollPane);
			
			JButton btnOk = new JButton("OK");
			btnOk.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					ArrayList<Integer> list = getSelected(columns);
					control.setSelected(list);
					dispose();
					control.launchAnonymousMethod(selectedString);
				}
			});
			springLayout.putConstraint(SpringLayout.NORTH, btnOk, 35, SpringLayout.SOUTH, scrollPane);
			springLayout.putConstraint(SpringLayout.WEST, btnOk, 0, SpringLayout.WEST, lblColonnesSensibles);
			springLayout.putConstraint(SpringLayout.EAST, btnOk, 94, SpringLayout.WEST, lblColonnesSensibles);
			getContentPane().add(btnOk);
	}
	
	public ArrayList<Integer> getSelected(CheckListItem[] columns) {
		ArrayList<Integer> retour = new ArrayList<Integer>();
		for(int i = 1; i < columns.length; i++) {
			if (columns[i].isSelected())
				retour.add(i);
		}
		this.selectedString = new DefaultListModel<String>();
		
		for(int i = 1; i < columns.length; i++) {
			if (columns[i].isSelected())
				this.selectedString.addElement(columns[i].toString());
		}
		return retour;
	}
}
