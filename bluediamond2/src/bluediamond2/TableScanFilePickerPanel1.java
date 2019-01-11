package bluediamond2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class TableScanFilePickerPanel1 extends JPanel {
	private JTextField tableScanFileTextField;
	JLabel positionerLabel;
	JButton pickFileButton;
	JButton editFileButton;
	File programPath;

	/**
	 * Create the panel.
	 */
	public TableScanFilePickerPanel1() {
		setLayout(new MigLayout("", "[][][][][grow]", "[]"));
		
		positionerLabel = new JLabel("Positioner");
		add(positionerLabel, "cell 0 0");
		
		pickFileButton = new JButton("...");
		add(pickFileButton, "cell 2 0");
		pickFileButton.addActionListener(new TableScanFilePickerPanel1_pickFileButton_ActionAdapter(this));

		
		editFileButton = new JButton("Edit");
		add(editFileButton, "cell 3 0");
		
		tableScanFileTextField = new JTextField();
		add(tableScanFileTextField, "cell 4 0,growx");
		tableScanFileTextField.setColumns(10);
		Path currentRelativePath = Paths.get("");
		programPath = new File(currentRelativePath.toAbsolutePath().toString());

	}

	public void setPositionerName(String str) {
		positionerLabel.setText(str);
	}
	
	public void setTableFileName(String str) {
		tableScanFileTextField.setText(str);
	}
	
	public void setPickedFileName(String str) {
		tableScanFileTextField.setText(str);
	}
	
	public String getPickedFileName() {		
		return tableScanFileTextField.getText().trim();
	}
	
	public void pickFileButton_ActionPerformed() {
		
		File inFile;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(programPath);
		fc.setFileFilter(new TXTfilter());
		int retVal = fc.showOpenDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			inFile = fc.getSelectedFile();
			tableScanFileTextField.setText(inFile.getAbsolutePath());
		}
	}
}


class TableScanFilePickerPanel1_pickFileButton_ActionAdapter implements ActionListener{
	TableScanFilePickerPanel1 adaptee;
	
	
	TableScanFilePickerPanel1_pickFileButton_ActionAdapter(TableScanFilePickerPanel1 adaptee){
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.pickFileButton_ActionPerformed();
		
	}
	
}