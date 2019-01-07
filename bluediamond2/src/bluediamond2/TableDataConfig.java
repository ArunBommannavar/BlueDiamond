package bluediamond2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TableDataConfig extends JDialog {

	private final JPanel contentPanel = new JPanel();

	JButton okButton;
	JButton cancelButton;

	Scan1PositionerParms scan1PositionerParms;
	Scan2PositionerParms scan2PositionerParms;
	
	List<Integer> valid1DPos;
	List<Integer> valid2DPos;
	
	JPanel tableXPanel;
	JPanel tableYPanel;

	/**
	 * Return value if cancel is chosen.
	 */
	public static final int CANCEL_OPTION = 1;

	/**
	 * Return value if approve (yes, ok) is chosen.
	 */
	public static final int APPROVE_OPTION = 0;

	/**
	 * Return value if an error occured.
	 */
	public static final int ERROR_OPTION = -1;

	private int returnValue = ERROR_OPTION;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { try { TableDataConfig dialog = new
	 * TableDataConfig(); dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 */
	/**
	 * Create the dialog.
	 */
	public TableDataConfig(JFrame jFrame, boolean modal) {
		super(jFrame, modal);
		setBounds(100, 100, 457, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, BorderLayout.CENTER);
			{
				tableXPanel = new JPanel();
				tabbedPane.addTab("x-axis positioners", null, tableXPanel, null);
				tableXPanel.setLayout(new GridLayout(1, 0, 0, 0));
			}
			{
				tableYPanel = new JPanel();
				tabbedPane.addTab("y-axis positioners", null, tableYPanel, null);
				tableYPanel.setLayout(new GridLayout(4, 0, 0, 0));
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			okButton.addActionListener(new TableDataConfig_okbutton_ActionAdapter(this));

			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			cancelButton.addActionListener(new TableDataConfig_cancelbutton_ActionAdapter(this));

			getRootPane().setDefaultButton(cancelButton);
			
			setLocationRelativeTo(jFrame);
			setVisible(true);
		}
	}

	public void okButton_actionPerformed(ActionEvent e) {
		returnValue = APPROVE_OPTION;
		setVisible(false);
	}

	public void cancelButton_actionPerformed(ActionEvent e) {
		returnValue = CANCEL_OPTION;
		setVisible(false);
	}

	public int getReturnVal() {
		return returnValue;
	}
	
	public void setScan1PositionerParms(Scan1PositionerParms s) {
		scan1PositionerParms = s;
	}
	
	public void setScan2PositionerParms(Scan2PositionerParms s) {
		scan2PositionerParms = s;
	}

	public void get1DValidPosList() {
		valid1DPos = scan1PositionerParms.getValidPos();
	}
	
	public void get2DValidPosList() {
		valid2DPos = scan2PositionerParms.getValidPos();
	}
	
	public void populatePositioners() {
		String motorName;
		
		for (int i = 0; i < 4; i++) {
			if(valid1DPos.contains(i) ) {
				TableScanFilePickerPanel tableScanFilePickerPanel = new TableScanFilePickerPanel();
				tableXPanel.add(tableScanFilePickerPanel);
			//	motorName = 
				
			}else {
				JPanel jPanelEmpty = new JPanel();
				tableXPanel.add(jPanelEmpty);
			}
			
		}
	}
}

class TableDataConfig_okbutton_ActionAdapter implements ActionListener {

	TableDataConfig adaptee;

	TableDataConfig_okbutton_ActionAdapter(TableDataConfig adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
	       adaptee.okButton_actionPerformed(e);
	}
}

class TableDataConfig_cancelbutton_ActionAdapter implements ActionListener {

	TableDataConfig adaptee;

	TableDataConfig_cancelbutton_ActionAdapter(TableDataConfig adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);

	}
}
