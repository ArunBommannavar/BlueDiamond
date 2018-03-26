package bluediamond2;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;


import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Config extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4210489905455657035L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_1D;
	private JTextField textField_2D;
	private String scan1Text = "";
	private String scan2Text = "";
	private JButton okButton;
	private JButton cancelButton;
	   /**
	    * Return value if run is chosen.
	    */
	   public static final int RUN_OPTION = 2;

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

	/**
	 * Create the dialog.
	 */
	public Config(JFrame jFrame,boolean modal) {
		super(jFrame,modal);
		setBounds(100, 100, 486, 305);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 2, 0, 0));
//		setModal(true);
		
			JPanel panelA = new JPanel();
			panelA.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), Color.GREEN, new Color(0, 255, 127), null));
			contentPanel.add(panelA);
			panelA.setLayout(new BorderLayout(0, 0));
			
				JPanel panel_1 = new JPanel();
				panelA.add(panel_1, BorderLayout.NORTH);
				
					JLabel lblddScan = new JLabel("1-D, 2-D Scan PVs");
					lblddScan.setFont(UIManager.getFont("Label.font"));
					panel_1.add(lblddScan);
				
			
			
				JPanel panel_2 = new JPanel();
				panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0)));
				panelA.add(panel_2, BorderLayout.CENTER);
				panel_2.setLayout(new MigLayout("", "[][][grow]", "[][]"));
				
				JLabel scanPv_1D_label = new JLabel("1-D scan PV");
				panel_2.add(scanPv_1D_label, "cell 0 0");
				
				
				textField_1D = new JTextField();
				textField_1D.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						scan1Text= textField_1D.getText().trim();
					}
				});
				textField_1D.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						scan1Text= textField_1D.getText().trim();
					}
				});
				panel_2.add(textField_1D, "cell 2 0,growx");
				textField_1D.setColumns(10);					
				
				JLabel scanPv_2D_label = new JLabel("2-D Scan PV");
				panel_2.add(scanPv_2D_label, "cell 0 1");					
				
				textField_2D = new JTextField();
				textField_2D.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						scan2Text= textField_2D.getText().trim();
					}
				});
				textField_2D.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						scan2Text= textField_2D.getText().trim();
					}
				});
				panel_2.add(textField_2D, "cell 2 1,growx");
				textField_2D.setColumns(10);
		
				JPanel panelB = new JPanel();
				panelB.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0)), null));
				contentPanel.add(panelB);
				panelB.setLayout(new MigLayout("", "[][][][]", "[][][][][]"));
			
				JLabel lblForExampled = new JLabel("for example: ");
				lblForExampled.setFont(new Font("Tahoma", Font.BOLD, 11));
				panelB.add(lblForExampled, "cell 0 0,alignx left,aligny top");
					
				JLabel lbldScanPv = new JLabel("1-D Scan PV");
				panelB.add(lbldScanPv, "cell 0 2");		
			
				JLabel lbltestscan = new JLabel("16TEST1:scan1");
				panelB.add(lbltestscan, "cell 2 2");
				
				JLabel lbldScanPv_1 = new JLabel("2-D Scan PV");
				panelB.add(lbldScanPv_1, "cell 0 3");
						
				JLabel lbltestscan_1 = new JLabel("16TEST1:scan2");
				panelB.add(lbltestscan_1, "cell 2 3");
		
				JPanel buttonPane = new JPanel();
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				buttonPane.setLayout(new GridLayout(0, 6, 0, 0));	
				
				okButton = new JButton("Save");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new Config_okbutton_ActionAdapte(this));
				
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new Config_cancelbutton_ActionAdapte(this));
				
				JPanel panel1 = new JPanel();
				buttonPane.add(panel1);

				JPanel panel2 = new JPanel();
				buttonPane.add(panel2);
			
				JPanel panel3 = new JPanel();
				buttonPane.add(panel3);
													
				getRootPane().setDefaultButton(cancelButton);
				setLocationRelativeTo(jFrame);
				setVisible(true);

	}
	
	public void set_1DScanPV(String str){
		textField_1D.setText(str);
	}

	public String get_1DScanPV(){
		return scan1Text;
	}
	
	public void set_2DScanPV(String str){
		textField_2D.setText(str);
	}

	public String get_2DScanPV(){
			return scan2Text;
	}
		
	public int getReturnVal(){
		return returnValue;
	}
	public void okButton_actionPerformed(ActionEvent e){
	      returnValue = APPROVE_OPTION;
	      setVisible(false);

	}	
	public void cancelButton_actionPerformed(ActionEvent e){
	      returnValue = CANCEL_OPTION;
	      setVisible(false);

	}	
	
}

class Config_okbutton_ActionAdapte implements ActionListener {
	Config adaptee;

	Config_okbutton_ActionAdapte(Config adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.okButton_actionPerformed(actionEvent);
    }
}

class Config_cancelbutton_ActionAdapte implements ActionListener {
	Config adaptee;

	Config_cancelbutton_ActionAdapte(Config adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cancelButton_actionPerformed(actionEvent);
    }
}
