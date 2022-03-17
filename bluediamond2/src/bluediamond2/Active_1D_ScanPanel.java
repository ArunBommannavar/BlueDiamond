package bluediamond2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;

import javax.swing.SpringLayout;
import java.awt.BorderLayout;

import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.EventTrigger;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCAxisTitle;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.JCLineStyle;
import com.klg.jclass.chart.JCMarker;

import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicLabelUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import gov.aps.jca.dbr.STRING;

public class Active_1D_ScanPanel extends JPanel {

	Context context;
	private JTextField xRangeMinTextField;
	private JTextField xRangeMaxTextField;
	private JTextField yRangeMinTextField;
	private JTextField yRangeMaxTextField;

	private Map<Integer, JCheckBox> posXMap = new HashMap<>();
	private Map<Integer, DetectorColorPanel> detMap_1D = new HashMap<>();

	JCheckBox xPositionerCheckBox_1;
	JCheckBox xPositionerCheckBox_2;
	JCheckBox xPositionerCheckBox_3;
	JCheckBox xPositionerCheckBox_4;
	int selectedPositioner = 0;

	boolean autoScale = true;
	JCheckBox userCheckBox;
	JCheckBox autoCheckBox;

	ButtonGroup posXButtonGroup = new ButtonGroup();
	ButtonGroup autoUserGroup = new ButtonGroup();

	protected Data1D data1D;
	private DetectorColorPanel detPanel;
	JPanel detectorPanel_1D;

	JCChart chart;
	JCAxis xaxis;
	JCAxis yaxis;
	ChartDataView dataView;

	JCMarker vMarker1;
	JCMarker vMarker2;
	JCMarker vMarkerCenter;

	double vMarker1Pos;
	double vMarker2Pos;
	double vMarkerCenterPos;
	double scanCenter;

	JCMarker hMarker1;
	JCMarker hMarker2;
	JCMarker hMarkerCenter;
	JCLineStyle centerVMarkerStyle;
	JCLineStyle centerHMarkerStyle;

	double hMarker1Pos;
	double hMarker2Pos;
	double hMarkerCenterPos;

	boolean vMarkersShowing = true;
	boolean hMarkersShowing = true;
	boolean hMarkerSelected = false;
	boolean vMarkerSelected = false;

	JCMarker pickedMarker;
	double pickedPoint;
	double minMarker;
	double maxMarker;
	int precision = 7;

	JLabel lblLeftMarkerValue;
	JLabel lblRightMarkerValue;
	JLabel lblCenterMarkerValue;
	JLabel lblWidthMarkerValue;
	JLabel scanStatusLabel;
	JLabel scanDiffLabel;
	JLabel scanCenterLabel;

	JLabel horzTopValue;
	JLabel horzBotValue;
	JLabel horzCenterValue;
	JLabel horzWidthValue;

	JCLineStyle vMarkerCenterStyle;
	JCLineStyle hMarkerCenterStyle;

	String checkText;
	boolean checkTextValid;

	String dx1Text;
	String dx2Text;
	String dy1Text;
	String dy2Text;

	boolean dx1Boolean;
	boolean dx2Boolean;
	boolean dy1Boolean;
	boolean dy2Boolean;

	double dx1;
	double dx2;
	double dy1;
	double dy2;

	String fileHeader = "";

	JButton btnLeftMarkerButton;
	JButton btnRightMarkerButton;
	JButton btnCenterMarkerButton;

	private PositionerPnPV[] scan1PosPnPV = new PositionerPnPV[4];

	JPanel detectorPanel_1D_1_30;
	JPanel detectorPanel_1D_31_60;

	/**
	 * Create the panel.
	 */
	public Active_1D_ScanPanel() {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		springLayout.putConstraint(SpringLayout.NORTH, leftPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, leftPanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, leftPanel, -5, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, leftPanel, 180, SpringLayout.WEST, this);
		add(leftPanel);

		JPanel plotPanel = new JPanel();
		plotPanel.setBorder(new CompoundBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(165, 42, 42), new Color(165, 42, 42),
						new Color(165, 42, 42), new Color(165, 42, 42)),
				new BevelBorder(BevelBorder.RAISED, new Color(255, 69, 0), new Color(255, 69, 0), new Color(255, 69, 0),
						new Color(255, 69, 0))));
		springLayout.putConstraint(SpringLayout.NORTH, plotPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, plotPanel, 5, SpringLayout.EAST, leftPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, plotPanel, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, plotPanel, -5, SpringLayout.EAST, this);
		add(plotPanel);

		detectorPanel_1D = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, detectorPanel_1D, 5, SpringLayout.SOUTH, plotPanel);
		springLayout.putConstraint(SpringLayout.WEST, detectorPanel_1D, 5, SpringLayout.EAST, leftPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, detectorPanel_1D, -5, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, detectorPanel_1D, -5, SpringLayout.EAST, this);
		SpringLayout sl_leftPanel = new SpringLayout();
		leftPanel.setLayout(sl_leftPanel);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, tabbedPane, -310, SpringLayout.SOUTH, leftPanel);
		tabbedPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		sl_leftPanel.putConstraint(SpringLayout.NORTH, tabbedPane, 5, SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, tabbedPane, 5, SpringLayout.WEST, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.EAST, tabbedPane, -5, SpringLayout.EAST, leftPanel);
		leftPanel.add(tabbedPane);

		JPanel scanStatusResetPanel = new JPanel();
		sl_leftPanel.putConstraint(SpringLayout.WEST, scanStatusResetPanel, 0, SpringLayout.WEST, tabbedPane);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, scanStatusResetPanel, 60, SpringLayout.SOUTH, tabbedPane);
		scanStatusResetPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		sl_leftPanel.putConstraint(SpringLayout.NORTH, scanStatusResetPanel, 5, SpringLayout.SOUTH, tabbedPane);
		sl_leftPanel.putConstraint(SpringLayout.EAST, scanStatusResetPanel, 0, SpringLayout.EAST, tabbedPane);
		leftPanel.add(scanStatusResetPanel);

		JPanel posPanel_1D = new JPanel();
		sl_leftPanel.putConstraint(SpringLayout.NORTH, posPanel_1D, 5, SpringLayout.SOUTH, scanStatusResetPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, posPanel_1D, 0, SpringLayout.WEST, tabbedPane);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, posPanel_1D, 100, SpringLayout.SOUTH, scanStatusResetPanel);
		posPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		scanStatusResetPanel.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_1 = new JPanel();
		scanStatusResetPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scanStatusLabel = new JLabel("Scan Status");
		scanStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(scanStatusLabel, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		scanStatusResetPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		scanStatusResetPanel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		sl_leftPanel.putConstraint(SpringLayout.EAST, posPanel_1D, 0, SpringLayout.EAST, tabbedPane);
		leftPanel.add(posPanel_1D);

		JPanel rangePanel_1D = new JPanel();
		sl_leftPanel.putConstraint(SpringLayout.NORTH, rangePanel_1D, 5, SpringLayout.SOUTH, posPanel_1D);

		sl_leftPanel.putConstraint(SpringLayout.WEST, rangePanel_1D, 0, SpringLayout.WEST, tabbedPane);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, rangePanel_1D, 144, SpringLayout.SOUTH, posPanel_1D);
		rangePanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		posPanel_1D.setLayout(new GridLayout(5, 0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0),
				new Color(0, 255, 255), new Color(0, 255, 0), new Color(0, 255, 255)), null));
		posPanel_1D.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Positioners");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);

		JPanel panel_4 = new JPanel();
		posPanel_1D.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_1 = new JCheckBox("x_positioner_1");
		xPositionerCheckBox_1.setSelected(true);
		panel_4.add(xPositionerCheckBox_1, BorderLayout.CENTER);
		posXMap.put(0, xPositionerCheckBox_1);

		JPanel panel_5 = new JPanel();
		posPanel_1D.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_2 = new JCheckBox("x_positioner_2");
		panel_5.add(xPositionerCheckBox_2, BorderLayout.CENTER);
		posXMap.put(1, xPositionerCheckBox_2);

		JPanel panel_6 = new JPanel();
		posPanel_1D.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_3 = new JCheckBox("x_positioner_3");
		panel_6.add(xPositionerCheckBox_3, BorderLayout.CENTER);
		posXMap.put(2, xPositionerCheckBox_3);

		JPanel panel_7 = new JPanel();
		posPanel_1D.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_4 = new JCheckBox("x_positioner_4");
		panel_7.add(xPositionerCheckBox_4, BorderLayout.CENTER);
		posXMap.put(3, xPositionerCheckBox_4);
		sl_leftPanel.putConstraint(SpringLayout.EAST, rangePanel_1D, 0, SpringLayout.EAST, tabbedPane);

		posXButtonGroup.add(xPositionerCheckBox_1);
		posXButtonGroup.add(xPositionerCheckBox_2);
		posXButtonGroup.add(xPositionerCheckBox_3);
		posXButtonGroup.add(xPositionerCheckBox_4);

		xPositionerCheckBox_1.addActionListener(new Active_1D_ScanPanel_xPositionerCheckBox_1_actionAdapter(this));
		xPositionerCheckBox_2.addActionListener(new Active_1D_ScanPanel_xPositionerCheckBox_2_actionAdapter(this));
		xPositionerCheckBox_3.addActionListener(new Active_1D_ScanPanel_xPositionerCheckBox_3_actionAdapter(this));
		xPositionerCheckBox_4.addActionListener(new Active_1D_ScanPanel_xPositionerCheckBox_4_actionAdapter(this));

		JPanel vertPanel_1D = new JPanel();
		vertPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		tabbedPane.addTab("Vertical", null, vertPanel_1D, null);
		vertPanel_1D.setLayout(new GridLayout(5, 0, 0, 0));

		JPanel leftMarkerPanel_1D = new JPanel();
		leftMarkerPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		vertPanel_1D.add(leftMarkerPanel_1D);
		leftMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel leftMarkerPanel_1D_valueButton = new JPanel();
		leftMarkerPanel_1D.add(leftMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		leftMarkerPanel_1D_valueButton.setLayout(new BorderLayout(0, 0));

		JPanel panel_11 = new JPanel();
		leftMarkerPanel_1D_valueButton.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new BorderLayout(0, 0));

		lblLeftMarkerValue = new JLabel("Left Marker Value");
		lblLeftMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(lblLeftMarkerValue, BorderLayout.SOUTH);

		JPanel panel_12 = new JPanel();
		leftMarkerPanel_1D_valueButton.add(panel_12, BorderLayout.SOUTH);
		panel_12.setLayout(new BorderLayout(0, 0));

		btnLeftMarkerButton = new JButton("Move");
		btnLeftMarkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scan1PosPnPV[selectedPositioner].movePositioner(lblLeftMarkerValue.getText());
			}
		});
		panel_12.add(btnLeftMarkerButton);

		JPanel leftMarkerPanel_1D_LeftLabelPanel = new JPanel();
		leftMarkerPanel_1D_LeftLabelPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		leftMarkerPanel_1D.add(leftMarkerPanel_1D_LeftLabelPanel, BorderLayout.WEST);
		leftMarkerPanel_1D_LeftLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblLeftV = new JLabel("LEFT");
		lblLeftV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLeftV.setHorizontalAlignment(SwingConstants.CENTER);
		leftMarkerPanel_1D_LeftLabelPanel.add(lblLeftV);
		lblLeftV.setUI(new VerticalLabelUI(false));

		JPanel rightMarkerPanel_1D = new JPanel();
		rightMarkerPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		vertPanel_1D.add(rightMarkerPanel_1D);
		rightMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel rightMarkerPanel_1D_valueButton = new JPanel();
		rightMarkerPanel_1D.add(rightMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		rightMarkerPanel_1D_valueButton.setLayout(new BorderLayout(0, 0));

		JPanel panel_13 = new JPanel();
		rightMarkerPanel_1D_valueButton.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));

		lblRightMarkerValue = new JLabel("Right Marker Value");
		lblRightMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblRightMarkerValue, BorderLayout.CENTER);

		JPanel panel_14 = new JPanel();
		rightMarkerPanel_1D_valueButton.add(panel_14, BorderLayout.SOUTH);
		panel_14.setLayout(new BorderLayout(0, 0));

		btnRightMarkerButton = new JButton("Move");
		btnRightMarkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scan1PosPnPV[selectedPositioner].movePositioner(lblRightMarkerValue.getText());

			}
		});
		panel_14.add(btnRightMarkerButton);

		JPanel rightMarkerPanel_1D_RightLabelPanel = new JPanel();
		rightMarkerPanel_1D_RightLabelPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		rightMarkerPanel_1D.add(rightMarkerPanel_1D_RightLabelPanel, BorderLayout.WEST);
		rightMarkerPanel_1D_RightLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblRightV = new JLabel("RIGHT");
		lblRightV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRightV.setHorizontalAlignment(SwingConstants.CENTER);
		rightMarkerPanel_1D_RightLabelPanel.add(lblRightV);
		lblRightV.setUI(new VerticalLabelUI(false));

		JPanel centerMarkerPanel_1D = new JPanel();
		centerMarkerPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		vertPanel_1D.add(centerMarkerPanel_1D);
		centerMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel centerMarkerPanel_1D_valueButton = new JPanel();
		centerMarkerPanel_1D.add(centerMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		centerMarkerPanel_1D_valueButton.setLayout(new BorderLayout(0, 0));

		JPanel panel_15 = new JPanel();
		centerMarkerPanel_1D_valueButton.add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));

		lblCenterMarkerValue = new JLabel("Center Marker Value");
		lblCenterMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_15.add(lblCenterMarkerValue, BorderLayout.CENTER);

		JPanel panel_16 = new JPanel();
		centerMarkerPanel_1D_valueButton.add(panel_16, BorderLayout.SOUTH);
		panel_16.setLayout(new BorderLayout(0, 0));

		btnCenterMarkerButton = new JButton("Move");
		btnCenterMarkerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double dMove = Double.valueOf(lblCenterMarkerValue.getText());
				scan1PosPnPV[selectedPositioner].movePositioner(lblCenterMarkerValue.getText());

			}
		});
		panel_16.add(btnCenterMarkerButton);

		JPanel centerMarkerPanel_1D_CenterLabelPanel = new JPanel();
		centerMarkerPanel_1D_CenterLabelPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		centerMarkerPanel_1D.add(centerMarkerPanel_1D_CenterLabelPanel, BorderLayout.WEST);
		centerMarkerPanel_1D_CenterLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblCenterV = new JLabel("CENTER");
		lblCenterV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCenterV.setHorizontalAlignment(SwingConstants.CENTER);
		centerMarkerPanel_1D_CenterLabelPanel.add(lblCenterV);
		lblCenterV.setUI(new VerticalLabelUI(false));

		JPanel widthMarkerPanel_1D = new JPanel();
		widthMarkerPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		vertPanel_1D.add(widthMarkerPanel_1D);
		widthMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel widthMarkerScanPanel_1D = new JPanel();
		widthMarkerPanel_1D.add(widthMarkerScanPanel_1D, BorderLayout.CENTER);
		widthMarkerScanPanel_1D.setLayout(new BorderLayout(0, 0));

		lblWidthMarkerValue = new JLabel("Width Marker Value");
		lblWidthMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		widthMarkerScanPanel_1D.add(lblWidthMarkerValue, BorderLayout.CENTER);

		JPanel widthMarkerPanel_1D_WidthLabelPanel = new JPanel();
		widthMarkerPanel_1D_WidthLabelPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		widthMarkerPanel_1D.add(widthMarkerPanel_1D_WidthLabelPanel, BorderLayout.WEST);
		widthMarkerPanel_1D_WidthLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblWidthV = new JLabel("WIDTH");
		lblWidthV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWidthV.setHorizontalAlignment(SwingConstants.CENTER);
		widthMarkerPanel_1D_WidthLabelPanel.add(lblWidthV);
		lblWidthV.setUI(new VerticalLabelUI(false));

		JPanel centerDiffPanel_1D = new JPanel();
		centerDiffPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		vertPanel_1D.add(centerDiffPanel_1D);
		centerDiffPanel_1D.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel scanCenterPanel_1D = new JPanel();
		scanCenterPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		centerDiffPanel_1D.add(scanCenterPanel_1D);
		scanCenterPanel_1D.setLayout(new BorderLayout(0, 0));

		scanCenterLabel = new JLabel("scan center");
		scanCenterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scanCenterPanel_1D.add(scanCenterLabel, BorderLayout.CENTER);

		JPanel diffPanel_1D = new JPanel();
		diffPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		centerDiffPanel_1D.add(diffPanel_1D);
		diffPanel_1D.setLayout(new BorderLayout(0, 0));

		scanDiffLabel = new JLabel("Scan Diff");
		scanDiffLabel.setHorizontalAlignment(SwingConstants.CENTER);
		diffPanel_1D.add(scanDiffLabel, BorderLayout.CENTER);

		JPanel horzPanel_1D = new JPanel();
		horzPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.ORANGE));
		tabbedPane.addTab("Horizontal", null, horzPanel_1D, null);
		horzPanel_1D.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel horzTopPanel_1D = new JPanel();
		horzTopPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		horzPanel_1D.add(horzTopPanel_1D);
		horzTopPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzTopValuePanel = new JPanel();
		horzTopPanel_1D.add(horzTopValuePanel, BorderLayout.CENTER);
		horzTopValuePanel.setLayout(new BorderLayout(0, 0));

		horzTopValue = new JLabel("New label");
		horzTopValue.setHorizontalAlignment(SwingConstants.CENTER);
		horzTopValuePanel.add(horzTopValue, BorderLayout.CENTER);

		JPanel horzTopLabelPanel = new JPanel();
		horzTopPanel_1D.add(horzTopLabelPanel, BorderLayout.WEST);
		horzTopLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblTop = new JLabel("Top");
		lblTop.setHorizontalAlignment(SwingConstants.CENTER);
		horzTopLabelPanel.add(lblTop);
		lblTop.setUI(new VerticalLabelUI(false));

		JPanel horzBottomPanel_1D = new JPanel();
		horzBottomPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		horzPanel_1D.add(horzBottomPanel_1D);
		horzBottomPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzBottomValuePanel = new JPanel();
		horzBottomPanel_1D.add(horzBottomValuePanel, BorderLayout.CENTER);
		horzBottomValuePanel.setLayout(new BorderLayout(0, 0));

		horzBotValue = new JLabel("New label");
		horzBotValue.setHorizontalAlignment(SwingConstants.CENTER);
		horzBottomValuePanel.add(horzBotValue, BorderLayout.CENTER);

		JPanel horzBottomLabelPanel = new JPanel();
		horzBottomPanel_1D.add(horzBottomLabelPanel, BorderLayout.WEST);
		horzBottomLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblBottom = new JLabel("Bottom");
		lblBottom.setHorizontalAlignment(SwingConstants.CENTER);
		horzBottomLabelPanel.add(lblBottom);
		lblBottom.setUI(new VerticalLabelUI(false));

		JPanel horzCenterPanel_1D = new JPanel();
		horzCenterPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		horzPanel_1D.add(horzCenterPanel_1D);
		horzCenterPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzCenterValuePanel = new JPanel();
		horzCenterPanel_1D.add(horzCenterValuePanel, BorderLayout.CENTER);
		horzCenterValuePanel.setLayout(new BorderLayout(0, 0));

		horzCenterValue = new JLabel("New label");
		horzCenterValue.setHorizontalAlignment(SwingConstants.CENTER);
		horzCenterValuePanel.add(horzCenterValue, BorderLayout.CENTER);

		JPanel horzCenterLabelPanel = new JPanel();
		horzCenterPanel_1D.add(horzCenterLabelPanel, BorderLayout.WEST);
		horzCenterLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblCenter = new JLabel("Center");
		lblCenter.setHorizontalAlignment(SwingConstants.CENTER);
		horzCenterLabelPanel.add(lblCenter);
		lblCenter.setUI(new VerticalLabelUI(false));

		JPanel horzWidthPanel_1D = new JPanel();
		horzWidthPanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		horzPanel_1D.add(horzWidthPanel_1D);
		horzWidthPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzWidthValuePanel = new JPanel();
		horzWidthPanel_1D.add(horzWidthValuePanel, BorderLayout.CENTER);
		horzWidthValuePanel.setLayout(new BorderLayout(0, 0));

		horzWidthValue = new JLabel("New label");
		horzWidthValue.setHorizontalAlignment(SwingConstants.CENTER);
		horzWidthValuePanel.add(horzWidthValue, BorderLayout.CENTER);

		JPanel horzWidthLabelPanel = new JPanel();
		horzWidthPanel_1D.add(horzWidthLabelPanel, BorderLayout.WEST);
		horzWidthLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblWidth = new JLabel("Width");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		horzWidthLabelPanel.add(lblWidth);
		lblWidth.setUI(new VerticalLabelUI(false));

		leftPanel.add(rangePanel_1D);
		rangePanel_1D.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		rangePanel_1D.add(tabbedPane_1, BorderLayout.CENTER);

		JPanel x_rangePanel_1D = new JPanel();
		x_rangePanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		tabbedPane_1.addTab("X-Range", null, x_rangePanel_1D, null);
		x_rangePanel_1D.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel xRangeMinLabel = new JLabel("Min");
		x_rangePanel_1D.add(xRangeMinLabel, "cell 0 0,alignx trailing");

		xRangeMinTextField = new JTextField();
		xRangeMinTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dx1Text = xRangeMinTextField.getText();
				dx1Boolean = validateTextFieldEntry(dx1Text);
				if (!dx1Boolean) {
					showAlert(" Invalid entry");
					xRangeMinTextField.setText(String.valueOf(getMinX()));
				} else {
					// if user then update the plot
					if (!getAutoScale()) {
						data1D.setxAxisUserMin(Double.parseDouble(dx1Text));
						data1D.setXAxisScale();
						setMarkers();
					}
				}

			}
		});
		x_rangePanel_1D.add(xRangeMinTextField, "cell 1 0,growx");
		xRangeMinTextField.setColumns(10);

		JLabel xRangeMaxLabel = new JLabel("Max");
		x_rangePanel_1D.add(xRangeMaxLabel, "cell 0 1,alignx trailing");

		xRangeMaxTextField = new JTextField();
		xRangeMaxTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dx2Text = xRangeMaxTextField.getText();
				dx2Boolean = validateTextFieldEntry(dx2Text);
				if (!dx2Boolean) {
					showAlert(" Invalid entry");
					xRangeMaxTextField.setText(String.valueOf(getMaxX()));
				} else {
					// if user then update the plot
					if (!getAutoScale()) {
						data1D.setxAxisUserMax(Double.parseDouble(dx2Text));
						data1D.setXAxisScale();
						setMarkers();
					}
				}

			}
		});
		x_rangePanel_1D.add(xRangeMaxTextField, "cell 1 1,growx");
		xRangeMaxTextField.setColumns(10);

		JPanel y_rangePanel_1D = new JPanel();
		y_rangePanel_1D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		tabbedPane_1.addTab("Y-Range", null, y_rangePanel_1D, null);
		y_rangePanel_1D.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel yRangeMinLabel = new JLabel("Min");
		y_rangePanel_1D.add(yRangeMinLabel, "cell 0 0,alignx trailing");

		yRangeMinTextField = new JTextField();
		yRangeMinTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dy1Text = yRangeMinTextField.getText();
				dy1Boolean = validateTextFieldEntry(dy1Text);

				if (!dy1Boolean) {
					showAlert(" Invalid Entry ");
					yRangeMinTextField.setText(String.valueOf(getMinY()));
				} else {
					if (!getAutoScale()) {
						data1D.setyAxisUserMin(Double.parseDouble(dy1Text));
						data1D.setYAxisScale();
						setMarkers();
					}
				}
			}
		});
		y_rangePanel_1D.add(yRangeMinTextField, "cell 1 0,growx");
		yRangeMinTextField.setColumns(10);

		JLabel yRangeMaxLabel = new JLabel("Max");
		y_rangePanel_1D.add(yRangeMaxLabel, "cell 0 1,alignx trailing");

		yRangeMaxTextField = new JTextField();
		yRangeMaxTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dy2Text = yRangeMaxTextField.getText();
				dy2Boolean = validateTextFieldEntry(dy2Text);
				if (!dy2Boolean) {
					showAlert(" Invalid Entry ");
					yRangeMaxTextField.setText(String.valueOf(getMaxY()));
				} else {
					if (!getAutoScale()) {
						data1D.setyAxisUserMax(Double.parseDouble(dy2Text));
						data1D.setYAxisScale();
						setMarkers();
					}
				}
			}
		});
		y_rangePanel_1D.add(yRangeMaxTextField, "cell 1 1,growx");
		yRangeMaxTextField.setColumns(10);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		rangePanel_1D.add(panel_8, BorderLayout.SOUTH);

		userCheckBox = new JCheckBox("User");
		panel_8.add(userCheckBox);
		userCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAutoScale(false);
				dx1Text = xRangeMinTextField.getText();
				dx2Text = xRangeMaxTextField.getText();
				dy1Text = yRangeMinTextField.getText();
				dy2Text = yRangeMaxTextField.getText();

				dx1 = Double.parseDouble(dx1Text);
				dx2 = Double.parseDouble(dx2Text);
				dy1 = Double.parseDouble(dy1Text);
				dy2 = Double.parseDouble(dy2Text);

				if ((dx2 <= dx1) || (dy2 <= dy1)) {
					showAlert(" xMin should be less than xMax AND yMin must be less than yMax");
					setAutoScale(true);
				} else {
					data1D.setxAxisUserMin(dx1);
					data1D.setxAxisUserMax(dx2);

					data1D.setyAxisUserMin(dy1);
					data1D.setyAxisUserMax(dy2);

					data1D.setXAxisScale();
					data1D.setYAxisScale();

					setMarkers();
				}
			}
		});

		autoCheckBox = new JCheckBox("Auto");
		autoCheckBox.setSelected(true);
		panel_8.add(autoCheckBox);
		plotPanel.setLayout(new BorderLayout(0, 0));
		autoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAutoScale(true);
				updateUserAuto();
			}
		});

		autoUserGroup.add(autoCheckBox);
		autoUserGroup.add(userCheckBox);

		chart = new JCChart(JCChart.PLOT);
		plotPanel.add(chart, BorderLayout.CENTER);
		add(detectorPanel_1D);
		chart.getHeader().setVisible(true);
		Font trb18 = new Font("TimesRoman", Font.BOLD, 18);
		chart.getHeader().setFont(trb18);
		chart.getHeader().setForeground(Color.BLUE);

		JCAxis xAxis = chart.getDataView(0).getXAxis();
		Font trb14 = new Font("TimesRoman", Font.BOLD, 14);
		xAxis.setFont(trb14);

		detectorPanel_1D.setLayout(new BorderLayout(0, 0));

		JTabbedPane detectorTabbedPane_1D = new JTabbedPane(JTabbedPane.TOP);
		detectorTabbedPane_1D.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		detectorPanel_1D.add(detectorTabbedPane_1D, BorderLayout.CENTER);

		detectorPanel_1D_1_30 = new JPanel();
		detectorPanel_1D_1_30.setBorder(new CompoundBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(255, 222, 173), new Color(255, 222, 173),
						new Color(255, 222, 173), new Color(255, 222, 173)),
				new BevelBorder(BevelBorder.RAISED, new Color(165, 42, 42), new Color(165, 42, 42),
						new Color(165, 42, 42), new Color(165, 42, 42))));
		detectorTabbedPane_1D.addTab("Detectors 1-30", null, detectorPanel_1D_1_30, null);
		detectorPanel_1D_1_30.setLayout(new GridLayout(10, 3, 2, 0));

		detectorPanel_1D_31_60 = new JPanel();
		detectorTabbedPane_1D.addTab("Detectors 31-60", null, detectorPanel_1D_31_60, null);
		detectorPanel_1D_31_60.setLayout(new GridLayout(10, 3, 0, 0));

		chart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				chart_mousePressed(e);
			}

			public void mouseReleased(MouseEvent e) {
				chart_mouseReleased(e);
			}
		});

		chart.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				chart_mouseDragged(e);
			}
		});

		chart.setAllowUserChanges(true);
		chart.setTrigger(0, new EventTrigger(InputEvent.META_MASK, EventTrigger.CUSTOMIZE));
		dataView = chart.getDataView(0);

		addDetectorPanels_1D();
		vMarker1 = new JCMarker();
		vMarker1.setAssociatedWithYAxis(false);

		vMarker2 = new JCMarker();
		vMarker2.setAssociatedWithYAxis(false);

		vMarkerCenter = new JCMarker();
		vMarkerCenter.setAssociatedWithYAxis(false);
		centerVMarkerStyle = vMarkerCenter.getLineStyle();
		centerVMarkerStyle.setPattern(JCLineStyle.SHORT_DASH);
		centerVMarkerStyle.setColor(java.awt.Color.BLUE);

		hMarker1 = new JCMarker();
		hMarker1.setAssociatedWithYAxis(true);

		hMarker2 = new JCMarker();
		hMarker2.setAssociatedWithYAxis(true);

		hMarkerCenter = new JCMarker();
		hMarkerCenter.setAssociatedWithYAxis(true);
		centerHMarkerStyle = hMarkerCenter.getLineStyle();
		centerHMarkerStyle.setPattern(JCLineStyle.SHORT_DASH);
		centerHMarkerStyle.setColor(java.awt.Color.BLUE);

		this.updateUI();

	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static void runSafe(Runnable task) {
		if (SwingUtilities.isEventDispatchThread()) {
			task.run();
		} else {
			SwingUtilities.invokeLater(task);
		}
	}

	public boolean between(double i, double minValueInclusive, double maxValueInclusive) {
		if (i >= minValueInclusive && i <= maxValueInclusive)
			return true;
		else
			return false;
	}

	public boolean isVerticalMarkerInRange() {

		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();
		boolean checkRange = between(vMarker1.getValue(), xaxis.getMin(), xaxis.getMax());
		checkRange = checkRange & between(vMarker2.getValue(), xaxis.getMin(), xaxis.getMax());
		return checkRange;
	}

	public boolean isHorizontalMarkerInRange() {

		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();
		boolean checkRange = between(hMarker1.getValue(), yaxis.getMin(), yaxis.getMax());
		checkRange = checkRange & between(hMarker2.getValue(), yaxis.getMin(), yaxis.getMax());

		return checkRange;
	}

	public void setHMarkers() {
		yaxis = chart.getDataView(0).getYAxis();
		runSafe(new Runnable() {
			public void run() {

				double d1 = yaxis.getMin();
				double d2 = yaxis.getMax();
				double d3 = d1 + (0.25 * (d2 - d1));
				double d4 = d1 + (0.75 * (d2 - d1));
				double d5 = (d3 + d4) / 2.0;

				hMarker1.setValue(d3);
				hMarker2.setValue(d4);
				hMarkerCenter.setValue(d5);

				hMarker1Pos = hMarker1.getValue();
				hMarker2Pos = hMarker2.getValue();
				hMarkerCenterPos = hMarkerCenter.getValue();

				setHorzTopValue(hMarker2Pos);
				setHorzBotValue(hMarker1Pos);
				setHorzCenterValue(hMarkerCenterPos);
				setHorzWidthValue(getWidth(d3, d4));

				// data1D.updateChartDisplay();
			}
		});
	}

	public void setMarkers() {

		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();
		runSafe(new Runnable() {
			public void run() {

				double d1 = xaxis.getMin();
				double d2 = xaxis.getMax();
				double d3 = d1 + (0.25 * (d2 - d1));
				double d4 = d1 + (0.75 * (d2 - d1));
				double d5 = (d3 + d4) / 2.0;

				vMarker1.setValue(d3);
				vMarker2.setValue(d4);
				vMarkerCenter.setValue(d5);

				vMarker1Pos = vMarker1.getValue();
				vMarker2Pos = vMarker2.getValue();
				vMarkerCenterPos = vMarkerCenter.getValue();

				setLeftMarkerValue(vMarker1Pos);
				setRightMarkerValue(vMarker2Pos);
				setCenterMarkerValue(vMarkerCenterPos);
				setWidthMarkerValue(getWidth(d3, d4));
				updateScanCenterDiff();

				d1 = yaxis.getMin();
				d2 = yaxis.getMax();
				d3 = d1 + (0.25 * (d2 - d1));
				d4 = d1 + (0.75 * (d2 - d1));
				d5 = (d3 + d4) / 2.0;

				hMarker1.setValue(d3);
				hMarker2.setValue(d4);
				hMarkerCenter.setValue(d5);

				hMarker1Pos = hMarker1.getValue();
				hMarker2Pos = hMarker2.getValue();
				hMarkerCenterPos = hMarkerCenter.getValue();

				setHorzTopValue(hMarker2Pos);
				setHorzBotValue(hMarker1Pos);
				setHorzCenterValue(hMarkerCenterPos);
				setHorzWidthValue(getWidth(d3, d4));

				data1D.updateChartDisplay();
			}
		});
	}

	public void setXAxisTitle(String str) {
		JCAxis xAxis = chart.getDataView(0).getXAxis();
		xAxis.setTitle(new JCAxisTitle(str));
	}

	public void xCheckBox1_actionPerformed(ActionEvent e) {
		data1D.setSelectedPositioner(0);
		selectedPositioner = 0;
		scanCenter = data1D.getScanCenter();
		setMarkers();
		setScanCenterLabel(scanCenter);
		updateScanCenterDiff();
		updateUserAuto();
		JCheckBox jb = ((JCheckBox) (e.getSource()));
		String str = jb.getText();
		setXAxisTitle(str);
	}

	public void xCheckBox2_actionPerformed(ActionEvent e) {
		data1D.setSelectedPositioner(1);
		selectedPositioner = 1;
		scanCenter = data1D.getScanCenter();
		setMarkers();
		setScanCenterLabel(scanCenter);
		updateScanCenterDiff();
		updateUserAuto();
		JCheckBox jb = ((JCheckBox) (e.getSource()));
		String str = jb.getText();
		setXAxisTitle(str);
	}

	public void xCheckBox3_actionPerformed(ActionEvent e) {
		data1D.setSelectedPositioner(2);
		selectedPositioner = 2;
		scanCenter = data1D.getScanCenter();
		setMarkers();
		setScanCenterLabel(scanCenter);
		updateScanCenterDiff();
		updateUserAuto();
		JCheckBox jb = ((JCheckBox) (e.getSource()));
		String str = jb.getText();
		setXAxisTitle(str);
	}

	public void xCheckBox4_actionPerformed(ActionEvent e) {
		data1D.setSelectedPositioner(3);
		selectedPositioner = 3;
		scanCenter = data1D.getScanCenter();
		setMarkers();
		setScanCenterLabel(scanCenter);
		updateScanCenterDiff();
		updateUserAuto();
		JCheckBox jb = ((JCheckBox) (e.getSource()));
		String str = jb.getText();
		setXAxisTitle(str);
	}

	public JCChart getChart() {
		return chart;
	}

	public void setChartHeader(String str) {
		((JLabel) chart.getHeader()).setText(str);
	}

	public void set1DDataSource(Data1D d1) {
		data1D = d1;
	}

	public void chart_mousePressed(MouseEvent e) {
		JCMarker mrk1;
		JCMarker mrk2;
		double dm1;
		double dm2;
		double dd;

		Point point = e.getPoint();
		// System.out.println(" Blue X Point = "+point.getX());
		com.klg.jclass.chart.JCLineStyle ll;

		if (vMarkersShowing) {
			chart.setBatched(true);

			mrk1 = getLeftMarker();
			mrk2 = getRightMarker();

			minMarker = getMinX();
			maxMarker = getMaxX();

			dm1 = mrk1.getValue();
			dm2 = mrk2.getValue();

			dd = (maxMarker - minMarker) * 0.01;

			pickedPoint = chart.getDataView(0).coordToDataCoord((int) point.getX(), (int) point.getY()).getX();
			if (pickedPoint < (dm1 + dd) && pickedPoint > (dm1 - dd)) {
				pickedMarker = mrk1;
				ll = pickedMarker.getLineStyle();
				ll.setColor(Color.RED);
				vMarkerSelected = true;

			} else if (pickedPoint < (dm2 + dd) && pickedPoint > (dm2 - dd)) {
				pickedMarker = mrk2;
				ll = pickedMarker.getLineStyle();
				ll.setColor(Color.RED);
				vMarkerSelected = true;

			} else {
				pickedMarker = null;
				vMarkerSelected = false;
			}

			chart.setBatched(false);

		}
		if (pickedMarker == null && hMarkersShowing) {

			chart.setBatched(true);

			mrk1 = getBottomtMarker();
			mrk2 = getTopMarker();

			minMarker = getMinY();
			maxMarker = getMaxY();
			dm1 = mrk1.getValue();
			dm2 = mrk2.getValue();
			dd = (maxMarker - minMarker) * 0.01;
			pickedPoint = chart.getDataView(0).coordToDataCoord((int) point.getX(), (int) point.getY()).getY();
			if (pickedPoint < (dm1 + dd) && pickedPoint > (dm1 - dd)) {
				pickedMarker = mrk1;
				ll = pickedMarker.getLineStyle();
				ll.setColor(Color.RED);
				hMarkerSelected = true;

			} else if (pickedPoint < (dm2 + dd) && pickedPoint > (dm2 - dd)) {
				pickedMarker = mrk2;
				ll = pickedMarker.getLineStyle();
				ll.setColor(Color.RED);
				hMarkerSelected = true;
			} else {
				pickedMarker = null;
				hMarkerSelected = false;
			}
			chart.setBatched(false);
		}
		data1D.updateChartDisplay();

	}

	public void chart_mouseReleased(MouseEvent e) {
		if (vMarkerSelected || hMarkerSelected) {
			chart.setBatched(true);
			com.klg.jclass.chart.JCLineStyle ll = pickedMarker.getLineStyle();
			ll.setColor(Color.BLACK);
			pickedMarker = null;
			chart.setBatched(false);
			data1D.updateChartDisplay();
		}
	}

	public void addVMarkers() {
		dataView.addMarker(vMarker1);
		dataView.addMarker(vMarker2);
		dataView.addMarker(vMarkerCenter);
	}

	public void showVMarkers() {
		addVMarkers();
		updateVMarkers();
	}

	public void addHMarkers() {
		dataView.addMarker(hMarker1);
		dataView.addMarker(hMarker2);
		dataView.addMarker(hMarkerCenter);
	}

	public void showHMarkers() {
		addHMarkers();
		updateHMarkers();
	}

	public void hideVMarkers() {

		vMarker1Pos = vMarker1.getValue();
		dataView.removeMarker(vMarker1);

		vMarker2Pos = vMarker2.getValue();
		dataView.removeMarker(vMarker2);

		vMarkerCenterPos = vMarkerCenter.getValue();
		dataView.removeMarker(vMarkerCenter);
	}

	public void hideHMarkers() {

		hMarker1Pos = hMarker1.getValue();
		dataView.removeMarker(hMarker1);

		hMarker2Pos = hMarker2.getValue();
		dataView.removeMarker(hMarker2);

		hMarkerCenterPos = hMarkerCenter.getValue();
		dataView.removeMarker(hMarkerCenter);
	}

	public void moveVmarker(JCMarker mrkr, double d) {
		double d1;
		double d2;
		double dCenter;

		mrkr.setValue(d);
		d1 = vMarker1.getValue();
		d2 = vMarker2.getValue();
		dCenter = getCenter(d1, d2);
		vMarkerCenter.setValue(dCenter);

		updateVMarkers();
		updateScanCenterDiff();

	}

	public void updateVMarkers() {
		runSafe(new Runnable() {
			public void run() {
				vMarker1Pos = vMarker1.getValue();
				vMarker2Pos = vMarker2.getValue();
				vMarkerCenterPos = vMarkerCenter.getValue();
				setLeftMarkerValue(vMarker1Pos);
				setRightMarkerValue(vMarker2Pos);
				setCenterMarkerValue(vMarkerCenterPos);
				setWidthMarkerValue(vMarker2Pos - vMarker1Pos);
			}
		});
	}

	public void updateScanCenterDiff() {
		runSafe(new Runnable() {
			public void run() {
				vMarkerCenterPos = vMarkerCenter.getValue();
				setScanDiffValue(scanCenter - vMarkerCenterPos);
			}
		});
	}

	public void updateHMarkers() {
		runSafe(new Runnable() {
			public void run() {
				hMarker1Pos = hMarker1.getValue();
				hMarker2Pos = hMarker2.getValue();
				hMarkerCenterPos = hMarkerCenter.getValue();
				setHorzBotValue(hMarker1Pos);
				setHorzTopValue(hMarker2Pos);
				setHorzCenterValue(hMarkerCenterPos);
				setHorzWidthValue(hMarker2Pos - hMarker1Pos);
			}
		});
	}

	public void moveHmarker(JCMarker mrkr, double d) {
		double d1;
		double d2;
		double dCenter;
		// double dWidth;

		mrkr.setValue(d);
		d1 = hMarker1.getValue();
		d2 = hMarker2.getValue();
		dCenter = getCenter(d1, d2);
		hMarkerCenter.setValue(dCenter);
		updateHMarkers();

	}

	public void setHorzTopValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		horzTopValue.setText(str);
	}

	public void setHorzBotValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		horzBotValue.setText(str);
	}

	public void setHorzCenterValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		horzCenterValue.setText(str);
	}

	public void setHorzWidthValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		horzWidthValue.setText(str);
	}

	public void chart_mouseDragged(MouseEvent e) {
		Point point;

		double pickedPoint;
		if (vMarkerSelected) {
			chart.setBatched(true);
			point = e.getPoint();

			pickedPoint = chart.getDataView(0).coordToDataCoord((int) point.getX(), (int) point.getY()).getX();
			if (pickedPoint < minMarker) {
				pickedPoint = minMarker;
			}
			if (pickedPoint > maxMarker) {
				pickedPoint = maxMarker;
			}

			pickedPoint = getPrecisionedData(pickedPoint);
			moveVmarker(pickedMarker, pickedPoint);
			chart.setBatched(false);

		} else if (hMarkerSelected) {
			chart.setBatched(true);
			point = e.getPoint();
			pickedPoint = chart.getDataView(0).coordToDataCoord((int) point.getX(), (int) point.getY()).getY();
			if (pickedPoint < minMarker) {
				pickedPoint = minMarker;
			}
			if (pickedPoint > maxMarker) {
				pickedPoint = maxMarker;
			}
			pickedPoint = getPrecisionedData(pickedPoint);
			moveHmarker(pickedMarker, pickedPoint);

			chart.setBatched(false);

		}
		data1D.updateChartDisplay();
	}

	public JCMarker getLeftMarker() {
		return vMarker1;
	}

	public JCMarker getRightMarker() {
		return vMarker2;
	}

	public JCMarker getCenterMarker() {
		return vMarkerCenter;
	}

	public JCMarker getTopMarker() {
		return hMarker1;
	}

	public JCMarker getBottomtMarker() {
		return hMarker2;
	}

	public double getMinX() {

		double d = getPrecisionedData(xaxis.getMin());
		return d;
	}

	public double getMaxX() {
		double d = getPrecisionedData(xaxis.getMax());
		return d;
	}

	public double getMinY() {
		return yaxis.getMin();
	}

	public double getMaxY() {
		return yaxis.getMax();
	}

	public double getPrecisionedData(double d1) {
		double d = Math.floor(d1 * Math.pow(10, precision) + 0.5);
		d = d / Math.pow(10, precision);
		return d;
	}

	public double getCenter(double d1, double d2) {

		double d = (d1 + d2) / 2.0;
		return d;
	}

	public double getWidth(double d1, double d2) {

		double d = d2 - d1;
		return d;
	}

	public void setLeftMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		lblLeftMarkerValue.setText(str);
	}

	public void setRightMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		lblRightMarkerValue.setText(str);
	}

	public void setCenterMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		lblCenterMarkerValue.setText(str);
	}

	public void setWidthMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		lblWidthMarkerValue.setText(str);
	}

	public void setScanCenterLabel(double d) {
		d = getPrecisionedData(d);
		scanCenter = d;
		String str = Double.toString(d);
		scanCenterLabel.setText("Scan Center = " + str);
	}

	public void setScanDiffValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		scanDiffLabel.setText("Diff = " + str);

	}

	public void setScanStatus(String str) {
		scanStatusLabel.setText(str);
	}

	public void setAutoScale(boolean b) {
		autoScale = b;

		data1D.setAutoScale(b);
		if (b) {
			updateUserAuto();
		}
	}

	public boolean getAutoScale() {
		return autoScale;
	}

	public void updateUserAuto() {
		data1D.updateChartDisplay();

		setUserXminText(this.getMinX());
		setUserXMaxText(this.getMaxX());
		setUserYminText(this.getMinY());
		setUserYMaxText(this.getMaxY());
	}

	public void setUserXminText(double d) {
		xRangeMinTextField.setText(String.valueOf(d));
	}

	public void setUserXMaxText(double d) {
		xRangeMaxTextField.setText(String.valueOf(d));
	}

	public void setUserYminText(double d) {
		yRangeMinTextField.setText(String.valueOf(d));
	}

	public void setUserYMaxText(double d) {
		yRangeMaxTextField.setText(String.valueOf(d));
	}

	public void showAlert(String str) {
		JOptionPane.showMessageDialog(this, str);

	}

	public boolean validTwoDots(String str) {
		boolean ret = true;
		if (str.indexOf('.', str.indexOf('.') + 1) != -1) {
			ret = false;
		}
		return ret;
	}

	public boolean isNumeric(String string) {
		return string.matches("^[-+]?\\d+(\\.\\d+)?$");
	}

	public boolean validateTextFieldEntry(String str) {

		boolean ret = isNumeric(str) & validTwoDots(str);
		return ret;
	}

	public void setXPositionerVisible_1D(int pos, boolean b) {

		JCheckBox jb = posXMap.get(pos);
		jb.setEnabled(b);
		jb.setVisible(b);
	}

	public void resetPositioners_1D() {
		for (int i = 0; i < 4; i++) {
			setXPositionerVisible_1D(i, false);
		}
	}

	public void setXPositionerName_1D(int pos, String str) {
		JCheckBox jb = posXMap.get(pos);
		jb.setText(str);
	}

	public String getPositionerName(int pos) {
		String ret = "";
		JCheckBox jb = posXMap.get(pos);
		ret = jb.getText();
		return ret;
	}

	public void setDetVisible(int det, boolean b) {
		DetectorColorPanel detPanel = detMap_1D.get(det);
		detPanel.setVisible(b);
	}

	public void setDetEnable(int det, boolean b) {

		DetectorColorPanel detPanel = detMap_1D.get(det);
		detPanel.setEnabled(b);
	}

	public void addDetectorPanels_1D() {

		for (int i = 0; i < 30; i++) {
			detPanel = new DetectorColorPanel();

			detPanel.addJDetCheckBoxName(Integer.toString(i));
			detPanel.addDetColorButtonName(Integer.toString(i));

			detPanel.getDetPanelButton().addActionListener(new Active_1D_ScanPanel_detPanelButton_actionAdapter(this));
			detPanel.getDetPanelCheckBox()
					.addItemListener(new Active_1D_ScanPanel_detPanelCheckBox_actionAdapter(this));

			detPanel.setEnabled(false);
			detPanel.setVisible(false);
			detPanel.setValid(false);

			detMap_1D.put(i, detPanel);
			detectorPanel_1D_1_30.add(detPanel);

		}

		for (int i = 30; i < 60; i++) {
			detPanel = new DetectorColorPanel();
			detPanel.setValid(false);

			detPanel.addJDetCheckBoxName(Integer.toString(i));
			detPanel.addDetColorButtonName(Integer.toString(i));
			detPanel.getDetPanelButton().addActionListener(new Active_1D_ScanPanel_detPanelButton_actionAdapter(this));
			detPanel.getDetPanelCheckBox()
					.addItemListener(new Active_1D_ScanPanel_detPanelCheckBox_actionAdapter(this));
			detMap_1D.put(i, detPanel);
			detectorPanel_1D_31_60.add(detPanel);
		}
	}

	public void setDetectorName(int det, String str) {
		DetectorColorPanel detPanel = detMap_1D.get(det);
		JCheckBox jb = detPanel.getDetPanelCheckBox();

		jb.setText(str);
	}

	public String getDetectorName(int det) {
		String ret = "";
		DetectorColorPanel detPanel = detMap_1D.get(det);
		JCheckBox jb = detPanel.getDetPanelCheckBox();
		ret = jb.getText();
		return ret;
	}

	public void resetDetectors() {

		for (int i = 0; i < 60; i++) {
			setDetVisible(i, false);
		}
	}

	public void detectorStatus1D(String str, boolean b) {
		int n = Integer.parseInt(str);
		data1D.setDetectorForDisplay(n, b);
	}

	public void detectorColorStatus1D(String str) {
		int nm = Integer.parseInt(str);
//		System.out.println(" Detector color string = " + str);
//		System.out.println(" Total number of series = " + data1D.getNumSeries());
		StylePicker2 sp = new StylePicker2();
		int thickness = data1D.getSeriesThickness(nm);
		int shape = data1D.getSeriesSymbol(nm);
		int symbolSize = data1D.getSeriesSymbolSize(nm);
		sp.setThickness(thickness);
		sp.setShape(shape);
		sp.setSymbolSize(symbolSize);
		int ret = sp.showDialog(this);
		sp.setVisible(true);
		if (ret == 0) {
			if (sp.isColorPicked()) {
				data1D.setSeriesColor(sp.getSelectedColor(), nm);
			}

			if (sp.isThicknessPicked()) {
				data1D.setSeriesThickness(sp.getThickness(), nm);
			}

			if (sp.isShapePicked()) {
				data1D.setSeriesSymbol(sp.getShape(), nm);
			}

			if (sp.isSymbolSizePicked()) {
				data1D.setSeriesSymbolSize(sp.getSymbolSize(), nm);
			}
		}
	}

	public void setMoveButtonsEnable(boolean b) {
		btnLeftMarkerButton.setEnabled(b);
		btnRightMarkerButton.setEnabled(b);
		btnCenterMarkerButton.setEnabled(b);
	}

	public void setScan1PosPv(PositionerPnPV[] pp) {
		for (int i = 0; i < 4; i++) {
			scan1PosPnPV[i] = pp[i];
		}
	}
}

class VerticalLabelUI extends BasicLabelUI {

	static {
		labelUI = new VerticalLabelUI(false);
	}

	protected boolean clockwise;

	VerticalLabelUI(boolean clockwise) {
		super();
		this.clockwise = clockwise;
	}

	public Dimension getPreferredSize(JComponent c) {
		Dimension dim = super.getPreferredSize(c);
		return new Dimension(dim.height, dim.width);
	}

	private static Rectangle paintIconR = new Rectangle();
	private static Rectangle paintTextR = new Rectangle();
	private static Rectangle paintViewR = new Rectangle();
	private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

	public void paint(Graphics g, JComponent c) {

		JLabel label = (JLabel) c;
		String text = label.getText();
		Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

		if ((icon == null) && (text == null)) {
			return;
		}

		FontMetrics fm = g.getFontMetrics();
		paintViewInsets = c.getInsets(paintViewInsets);

		paintViewR.x = paintViewInsets.left;
		paintViewR.y = paintViewInsets.top;

		// Use inverted height & width
		paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
		paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

		paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
		paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

		String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

		Graphics2D g2 = (Graphics2D) g;
		AffineTransform tr = g2.getTransform();
		if (clockwise) {
			g2.rotate(Math.PI / 2);
			g2.translate(0, -c.getWidth());
		} else {
			g2.rotate(-Math.PI / 2);
			g2.translate(-c.getHeight(), 0);
		}

		if (icon != null) {
			icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
		}

		if (text != null) {
			int textX = paintTextR.x;
			int textY = paintTextR.y + fm.getAscent();

			if (label.isEnabled()) {
				paintEnabledText(label, g, clippedText, textX, textY);
			} else {
				paintDisabledText(label, g, clippedText, textX, textY);
			}
		}

		g2.setTransform(tr);
	}
}

class Active_1D_ScanPanel_xPositionerCheckBox_1_actionAdapter implements ActionListener {
	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_xPositionerCheckBox_1_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox1_actionPerformed(e);
	}
}

class Active_1D_ScanPanel_xPositionerCheckBox_2_actionAdapter implements ActionListener {
	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_xPositionerCheckBox_2_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox2_actionPerformed(e);
	}
}

class Active_1D_ScanPanel_xPositionerCheckBox_3_actionAdapter implements ActionListener {
	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_xPositionerCheckBox_3_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox3_actionPerformed(e);
	}
}

class Active_1D_ScanPanel_xPositionerCheckBox_4_actionAdapter implements ActionListener {
	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_xPositionerCheckBox_4_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox4_actionPerformed(e);
	}
}

class Active_1D_ScanPanel_detPanelCheckBox_actionAdapter implements ItemListener {

	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_detPanelCheckBox_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object eObject = e.getSource();
		JCheckBox dd = (JCheckBox) eObject;
		adaptee.detectorStatus1D(dd.getName(), dd.isSelected());
	}
}

class Active_1D_ScanPanel_detPanelButton_actionAdapter implements ActionListener {

	private Active_1D_ScanPanel adaptee;

	Active_1D_ScanPanel_detPanelButton_actionAdapter(Active_1D_ScanPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eObject = e.getSource();
		JButton jButton = (JButton) eObject;
//		System.out.println(" Debug get Button Name= " + jButton.getName());
		adaptee.detectorColorStatus1D(jButton.getName());
	}
}
