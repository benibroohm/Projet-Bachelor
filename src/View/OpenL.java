package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import Controller.ControleurSaisie;

public class OpenL implements ActionListener {

	private Start st;
	private JTextField filename = new JTextField(), dir = new JTextField();
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFileChooser c = new JFileChooser();
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(st);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(c.getSelectedFile().getName());
			dir.setText(c.getCurrentDirectory().toString());
			try {
				control.open(c.getSelectedFile());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			filename.setText("You pressed cancel");
			dir.setText("");
		}
	}
	
	public OpenL(Start st, ControleurSaisie control) {
		this.st = st;
		dir.setEditable(false);
	    filename.setEditable(false);
	    this.control = control;
	}

}
