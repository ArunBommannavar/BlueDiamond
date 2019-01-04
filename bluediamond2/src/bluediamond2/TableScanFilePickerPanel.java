package bluediamond2;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class TableScanFilePickerPanel extends JPanel {
	private JTextField tableScanFileTextField;
	private JLabel positionerLabel;
	private JButton pickFileButton;
	private JButton editFileButton;
	
	File programPath;
	
	/**
	 * Create the panel.
	 */
	public TableScanFilePickerPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		positionerLabel = new JLabel("New label");
		add(positionerLabel, "2, 2");
		
		pickFileButton = new JButton("...");
		pickFileButton.addActionListener(new TableScanFilePickerPanel_pickFileButton_ActionAdapter(this));
		add(pickFileButton, "4, 2");
		
		editFileButton = new JButton("Edit");
		add(editFileButton, "6, 2");
		
		tableScanFileTextField = new JTextField();
		add(tableScanFileTextField, "8, 2, fill, default");
		tableScanFileTextField.setColumns(10);
		tableScanFileTextField.setText("");
		
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


class TableScanFilePickerPanel_pickFileButton_ActionAdapter implements ActionListener{
	TableScanFilePickerPanel adaptee;
	
	
	TableScanFilePickerPanel_pickFileButton_ActionAdapter(TableScanFilePickerPanel adaptee){
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.pickFileButton_ActionPerformed();
		
	}
	
}