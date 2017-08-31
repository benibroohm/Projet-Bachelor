package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import Controller.ControleurSaisie;

public class OpenL implements ActionListener {

	private Start st;
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFileChooser c = null;
		if (control.isPathSet()) {
			File f = new File(control.getFolderPath()+"/Anonymes");
			c = new JFileChooser(new File(f.getAbsolutePath()));
		}
		else
			c = new JFileChooser();
		
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(st);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			control.getFilename().setText(c.getSelectedFile().getName());
			control.getDir().setText(c.getCurrentDirectory().toString());
			
			try {
				control.open();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			control.getFilename().setText("You pressed cancel");
			control.getDir().setText("");
		}
	}
	
	public OpenL(Start st, ControleurSaisie control) {
		this.st = st;
	    this.control = control;
	}

}
