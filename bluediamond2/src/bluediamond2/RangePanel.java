package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTabbedPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class RangePanel extends JPanel {
	private JTextField xRangeMinTextBox;
	private JTextField xRangeMaxTextBox;
	private JTextField yRangeMinTextBox;
	private JTextField yRangeMaxTextBox;
	private Map<Integer, JCheckBox> posXMap = new HashMap<>();
	private Map<Integer, JCheckBox> posYMap = new HashMap<>();
	JTabbedPane tabbedPane;
	JTabbedPane rangeTabbedPane;
	
	JPanel xPanel;
	JPanel posPanel;
	JPanel yPanel;
	CountDownConnection countDownConnection = CountDownConnection.getInstance();


	/**
	 * Create the panel.
	 */
	public RangePanel() {
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 204, 153));
		add(tabbedPane, BorderLayout.CENTER);
		
		xPanel = new JPanel();
		xPanel.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 153, 0), new Color(153, 102, 0), null, null), null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("x-axis", null, xPanel, null);
		GridBagLayout gbl_xPanel = new GridBagLayout();
		gbl_xPanel.columnWidths = new int[]{0, 0};
		gbl_xPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_xPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_xPanel.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		xPanel.setLayout(gbl_xPanel);
		
		posPanel = new JPanel();
		posPanel.setBackground(new Color(255, 222, 173));
		posPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 153, 102), new Color(204, 102, 0), null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_posPanel = new GridBagConstraints();
		gbc_posPanel.anchor = GridBagConstraints.NORTH;
		gbc_posPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_posPanel.gridx = 0;
		gbc_posPanel.gridy = 0;
		xPanel.add(posPanel, gbc_posPanel);
		posPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JCheckBox checkBoxPos1_1D = new JCheckBox("Positioner 1");
		checkBoxPos1_1D.setEnabled(false);
		checkBoxPos1_1D.setVisible(false);
		posPanel.add(checkBoxPos1_1D);
		posXMap.put(0, checkBoxPos1_1D);

		JCheckBox checkBoxPos2_1D = new JCheckBox("Positioner 2");
		checkBoxPos2_1D.setEnabled(false);
		checkBoxPos2_1D.setVisible(false);
		posPanel.add(checkBoxPos2_1D);
		posXMap.put(1, checkBoxPos2_1D);

		JCheckBox checkBoxPos3_1D = new JCheckBox("Positioner 3");
		checkBoxPos3_1D.setEnabled(false);
		checkBoxPos3_1D.setVisible(false);
		posPanel.add(checkBoxPos3_1D);
		posXMap.put(2, checkBoxPos3_1D);
		
		JCheckBox checkBoxPos4_1D = new JCheckBox("Positioner 4");
		checkBoxPos4_1D.setEnabled(false);
		checkBoxPos4_1D.setVisible(false);
		posPanel.add(checkBoxPos4_1D);
		posXMap.put(3, checkBoxPos4_1D);
		setXPositionerVisible(0,false);
		
		JPanel range1DPanel = new JPanel();
		range1DPanel.setBackground(new Color(255, 222, 173));
		range1DPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 102, 102), new Color(153, 51, 0), null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_range1DPanel = new GridBagConstraints();
		gbc_range1DPanel.anchor = GridBagConstraints.NORTH;
		gbc_range1DPanel.gridheight = 3;
		gbc_range1DPanel.fill = GridBagConstraints.BOTH;
		gbc_range1DPanel.gridx = 0;
		gbc_range1DPanel.gridy = 1;
		xPanel.add(range1DPanel, gbc_range1DPanel);
		range1DPanel.setLayout(new BorderLayout(0, 0));
		
		rangeTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		range1DPanel.add(rangeTabbedPane, BorderLayout.CENTER);
		
		JPanel xRange = new JPanel();
		xRange.setBackground(new Color(255, 228, 196));
		rangeTabbedPane.addTab("x-range", null, xRange, null);
		xRange.setLayout(new MigLayout("", "[49px][79px]", "[20px][20px][23px][23px]"));
		
		JLabel lblMin = new JLabel("Min");
		lblMin.setHorizontalAlignment(SwingConstants.LEFT);
		xRange.add(lblMin, "cell 0 0,alignx left,aligny center");
		
		xRangeMinTextBox = new JTextField();
		xRange.add(xRangeMinTextBox, "cell 1 0,growx,aligny top");
		xRangeMinTextBox.setColumns(10);
		
		JLabel lblMax = new JLabel("Max");
		lblMax.setHorizontalAlignment(SwingConstants.LEFT);
		xRange.add(lblMax, "cell 0 1,alignx left,aligny center");
		
		xRangeMaxTextBox = new JTextField();
		xRange.add(xRangeMaxTextBox, "cell 1 1,growx,aligny top");
		xRangeMaxTextBox.setColumns(10);
		
		JCheckBox chckbxUserXRange = new JCheckBox("User");
		xRange.add(chckbxUserXRange, "cell 0 2,alignx right,aligny top");
		
		JCheckBox chckbxAutoXRange = new JCheckBox("Auto");
		xRange.add(chckbxAutoXRange, "cell 1 2,alignx left,aligny top");
		
		JPanel yRange = new JPanel();
		yRange.setBackground(new Color(255, 222, 173));
		rangeTabbedPane.addTab("y-range", null, yRange, null);
		yRange.setLayout(new MigLayout("", "[49px][79px]", "[20px][20px][23px][23px]"));
		
		JLabel lblNewLabel = new JLabel("Min");
		yRange.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		
		yRangeMinTextBox = new JTextField();
		yRange.add(yRangeMinTextBox, "cell 1 0,growx,aligny top");
		yRangeMinTextBox.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Max");
		yRange.add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");
		
		yRangeMaxTextBox = new JTextField();
		yRange.add(yRangeMaxTextBox, "cell 1 1,growx,aligny top");
		yRangeMaxTextBox.setColumns(10);
		
		JCheckBox chckbxUserYRange = new JCheckBox("User");
		yRange.add(chckbxUserYRange, "cell 0 3,growx,aligny top");
		
		JCheckBox chckbxAutoYRange = new JCheckBox("Auto");
		yRange.add(chckbxAutoYRange, "cell 1 3,alignx left,aligny top");
		
		yPanel = new JPanel();
		yPanel.setBackground(new Color(255, 222, 173));
		yPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(222, 184, 135), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("y-axis", null, yPanel, null);
		yPanel.setLayout(new MigLayout("", "[97px]", "[23px][23px][23px][23px]"));
		
		JCheckBox checkBoxPos1_2D = new JCheckBox("New check box");
		yPanel.add(checkBoxPos1_2D, "cell 0 0,alignx left,aligny top");
		
		JCheckBox checkBoxPos2_2D = new JCheckBox("New check box");
		yPanel.add(checkBoxPos2_2D, "cell 0 1,alignx left,aligny top");
		
		JCheckBox checkBoxPos3_2D = new JCheckBox("New check box");
		yPanel.add(checkBoxPos3_2D, "cell 0 2,alignx left,aligny top");
		
		JCheckBox checkBoxPos4_2D = new JCheckBox("New check box");
		yPanel.add(checkBoxPos4_2D, "cell 0 3,alignx left,aligny top");
		setScan2DPosTab(false);
		
	}
	public void setScan2DPosTab(boolean b){
		tabbedPane.setEnabledAt(1, b);
		}
	
	public void setXPositionerVisible(int pos, boolean b){
		JCheckBox jb = posXMap.get(pos);
		jb.setEnabled(b);
		jb.setVisible(b);
	}	
	
	public void setXPositionerName(int pos, String str){
		JCheckBox jb = posXMap.get(pos);
		String firstPart;
		String secondPart;
		String rtyp;		
		int lastIndexOfDot;
		String pvName;
		EpicsDataObject pvObject;

		lastIndexOfDot = str.lastIndexOf(".");
		firstPart = str.substring(0, lastIndexOfDot);
		secondPart = str.substring(lastIndexOfDot + 1);
		pvName = firstPart+".RTYP";
		pvObject = new EpicsDataObject(pvName, true);
		countDownConnection.pendIO();

		rtyp = pvObject.getVal();
		
		PVDescription pvDescription = new PVDescription(firstPart,secondPart,rtyp,jb);
		pvDescription.makeEpicsDataObject();
		jb.setText(pvDescription.getDescription());
		
	}

}
