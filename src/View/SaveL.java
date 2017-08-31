package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import Controller.ControleurSaisie;

public class SaveL implements ActionListener {

	private Start st;
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Demonstrate "Save" dialog:
		JFileChooser c;
		if (control.isPathSet()) {
			c = new JFileChooser(new File(control.getFolderPath()+"/Anonymes"));
		}
		else
			c = new JFileChooser();
		int rVal = c.showSaveDialog(this.st);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			control.getFilename().setText(c.getSelectedFile().getName());
			control.getDir().setText(c.getCurrentDirectory().toString());
			try {
				control.save();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			control.getFilename().setText("You pressed cancel");
			control.getDir().setText("");
		}
	}
	
	public SaveL(Start st, ControleurSaisie control) {
		this.st = st;
	    this.control = control;
	}

}
