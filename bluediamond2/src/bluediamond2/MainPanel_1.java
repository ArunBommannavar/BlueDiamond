package bluediamond2;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.vecmath.Point3d;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCAxisTitle;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.JCLineStyle;
import com.klg.jclass.chart.JCMarker;

import com.klg.jclass.chart3d.*;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;


import com.klg.jclass.chart3d.Chart3dDataView;

import com.klg.jclass.chart3d.JCData3dIndex;
import com.klg.jclass.chart3d.JCData3dGridIndex;

import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;


public class MainPanel_1 extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CountDownConnection countDownConnection = CountDownConnection.getInstance();
	private JTextField xRangeMin_textField_1D;
	private JTextField xRangeMax_textField_1D;
	private JTextField yRangeMin_textField_1D;
	private JTextField yRangeMax_textField_1D;

	JTabbedPane tabbedPane;
	JPanel panel_1D;
	JPanel leftPanel_1D;
	JPanel panel_1D_Plot;
	JPanel detectorPanel_1D;
	JTabbedPane tabbedPane_1D;
	JPanel posPanel_1D;
	JPanel vertPanel_1D;
	JPanel leftMarkerPanel_1D;
	JPanel leftMarkerPanel_1D_valueButton;
	JPanel leftMarkerPanel_1D_LeftLabelPanel;
	JPanel rightMarkerPanel_1D;
	JPanel rightMarkerPanel_1D_valueButton;
	JPanel rightMarkerPanel_1D_RightLabelPanel;
	JPanel centerMarkerPanel_1D;
	JPanel centerMarkerPanel_1D_valueButton;
	JPanel centerMarkerPanel_1D_CenterLabelPanel;
	JPanel widthMarkerPanel_1D;
	JPanel widthMarkerScanPanel_1D;
	JPanel widthMarkerPanel_1D_WidthLabelPanel;

	JPanel scanCenterPanel_1D;

	JPanel detectorPanel_1D_1_30;
	JPanel detectorPanel_1D_31_60;

	SpringLayout sl_panel_1D;
	SpringLayout sl_leftPanel_1D;

	JCheckBox xPositionerCheckBox_1;
	JCheckBox xPositionerCheckBox_2;
	JCheckBox xPositionerCheckBox_3;
	JCheckBox xPositionerCheckBox_4;
	ButtonGroup posXButtonGroup = new ButtonGroup();

	protected Data1D data1D;
	protected Data2D data2D;
	private DetectorColorPanel detPanel;

	JTabbedPane detectorTabbedPane_1D;
	private Map<Integer, JCheckBox> posXMap = new HashMap<>();
	private Map<Integer, JCheckBox> posX2DMap = new HashMap<>();
	private Map<Integer, JCheckBox> posYMap = new HashMap<>();
	
	private Map<Integer, DetectorColorPanel> detMap_1D = new HashMap<>();
//	private Map<Integer, DetectorColorPanel> detMap_2D = new HashMap<>();
	List<Integer> selectedDetectors = new ArrayList<Integer>();

	protected JCChart chart;
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

	double hMarker1Pos;
	double hMarker2Pos;
	double hMarkerCenterPos;

	JCLineStyle vMarkerCenterStyle;
	JCLineStyle hMarkerCenterStyle;
	double xAxisMin;
	double xAxisMax;
	double yAxisMin;
	double yAxisMax;

	JLabel leftMarkerValueLabel;
	JLabel rightMarkerValueLabel;
	JLabel centerMarkerValueLabel;
	JLabel widthVerticalLabel;
	JLabel scanCenterLabel;
	JLabel scanDiffLabel;
	JLabel scanStatusLabel;
	JLabel fileNameLabel;

	JLabel horzTopValue;
	JLabel horzBotValue;
	JLabel horzCenterValue;
	JLabel horzWidthValue;

	JButton btnMoveLeft;
	JButton btnMoveRight;
	JButton btnMoveCenter;
	int precision = 4;
	boolean vMarkersShowing = true;
	boolean hMarkersShowing = true;
	boolean hMarkerSelected = false;
	boolean vMarkerSelected = false;
	JCMarker pickedMarker;
	double pickedPoint;
	double minMarker;
	double maxMarker;
	int selectedPositioner = 0;
	int selectedPositioner_X = 0;
	int selectedPositioner_Y = 0;
	
	private PositionerPnPV[] scan1PosPnPV = new PositionerPnPV[4];
	private PositionerPnPV[] scan2PosPnPV = new PositionerPnPV[4];

	ButtonGroup autoUserGroup = new ButtonGroup();
	JCheckBox autoCheckBox;
	JCheckBox userCheckBox;
	boolean autoScale = true;

	ButtonGroup detector2DGroup = new ButtonGroup();	
	protected JCChart3dJava2d chart3dJava2d;
    Chart3dDataView dataView3D = null;
    JRadioButton rdbtnSurfaceRadioButton;
    JRadioButton rdbtnContourRadioButton;
    ButtonGroup contourSurfaceGroup = new ButtonGroup();
    
    JCheckBox chckbx_2D_Xpositioner1;
    JCheckBox chckbx_2D_Xpositioner2;
    JCheckBox chckbx_2D_Xpositioner3;
    JCheckBox chckbx_2D_Xpositioner4;   
	
    JCheckBox chckbx_2D_Ypositioner1;
    JCheckBox chckbx_2D_Ypositioner2;
    JCheckBox chckbx_2D_Ypositioner3;
    JCheckBox chckbx_2D_Ypositioner4;
	
    ButtonGroup xPos_2DGroup = new ButtonGroup();
    ButtonGroup yPos_2DGroup = new ButtonGroup();
    
    JLabel x_Pos_2D_value;
    JLabel y_Pos_2D_value;
    JLabel int_2D_value;

    
    JComboBox<String> detComboBox_2D;
    boolean pickedPoint3D = false;
    double pickedX;
    double pickedY;
    private final double HOLE_VALUE = Double.MAX_VALUE;

    JCChart oldChart;
    
    
    
    /**
	 * Create the panel.
	 */
	public MainPanel_1() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(800, 550));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane, BorderLayout.CENTER);

		panel_1D = new JPanel();
		panel_1D.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabbedPane.addTab("1-D Scan", null, panel_1D, null);
		sl_panel_1D = new SpringLayout();
		panel_1D.setLayout(sl_panel_1D);

		leftPanel_1D = new JPanel();
		leftPanel_1D.setBackground(UIManager.getColor("Button.background"));
		leftPanel_1D.setForeground(UIManager.getColor("Button.background"));
		sl_panel_1D.putConstraint(SpringLayout.NORTH, leftPanel_1D, 5, SpringLayout.NORTH, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.WEST, leftPanel_1D, 5, SpringLayout.WEST, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.SOUTH, leftPanel_1D, -5, SpringLayout.SOUTH, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.EAST, leftPanel_1D, 180, SpringLayout.WEST, panel_1D);
		leftPanel_1D.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 255), Color.BLUE, null, null));
		panel_1D.add(leftPanel_1D);

		panel_1D_Plot = new JPanel();
		panel_1D_Plot.setBackground(new Color(240, 240, 240));
		sl_panel_1D.putConstraint(SpringLayout.SOUTH, panel_1D_Plot, -250, SpringLayout.SOUTH, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.EAST, panel_1D_Plot, -5, SpringLayout.EAST, panel_1D);
		panel_1D_Plot
				.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 255), new Color(0, 0, 255), null, null));
		sl_panel_1D.putConstraint(SpringLayout.NORTH, panel_1D_Plot, 5, SpringLayout.NORTH, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.WEST, panel_1D_Plot, 5, SpringLayout.EAST, leftPanel_1D);
		panel_1D.add(panel_1D_Plot);

		detectorPanel_1D = new JPanel();
		sl_panel_1D.putConstraint(SpringLayout.WEST, detectorPanel_1D, 5, SpringLayout.EAST, leftPanel_1D);
		sl_panel_1D.putConstraint(SpringLayout.SOUTH, detectorPanel_1D, -5, SpringLayout.SOUTH, panel_1D);
		sl_panel_1D.putConstraint(SpringLayout.EAST, detectorPanel_1D, -5, SpringLayout.EAST, panel_1D);
		detectorPanel_1D
				.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 255), new Color(0, 0, 255), null, null));
		sl_panel_1D.putConstraint(SpringLayout.NORTH, detectorPanel_1D, 5, SpringLayout.SOUTH, panel_1D_Plot);
		panel_1D_Plot.setLayout(new BorderLayout(0, 0));

		chart = new JCChart(JCChart.PLOT);
		chart.getChartArea().setFont(new Font("Lucida Calligraphy", Font.PLAIN, 11));

		panel_1D_Plot.add(chart, BorderLayout.CENTER);		
		chart.setBackground(Color.LIGHT_GRAY);
		

		sl_leftPanel_1D = new SpringLayout();
		leftPanel_1D.setLayout(sl_leftPanel_1D);
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
		dataView = chart.getDataView(0);

		vMarker1 = new JCMarker();
		vMarker2 = new JCMarker();
		vMarkerCenter = new JCMarker();
		vMarker1.setLabel("Left");
		vMarker2.setLabel("Right");
		vMarkerCenter.setLabel("Center");
		vMarker1.setAssociatedWithYAxis(false);
		vMarker2.setAssociatedWithYAxis(false);
		vMarkerCenter.setAssociatedWithYAxis(false);

		hMarker1 = new JCMarker();
		hMarker2 = new JCMarker();
		hMarkerCenter = new JCMarker();
		hMarker1.setLabel("Bottom");
		hMarker2.setLabel("Top");
		hMarker1.setAssociatedWithYAxis(true);
		hMarker2.setAssociatedWithYAxis(true);
		hMarkerCenter.setAssociatedWithYAxis(true);

		vMarkerCenterStyle = vMarkerCenter.getLineStyle();
		vMarkerCenterStyle.setPattern(JCLineStyle.SHORT_DASH);
		vMarkerCenterStyle.setColor(java.awt.Color.BLUE);

		hMarkerCenterStyle = hMarkerCenter.getLineStyle();
		hMarkerCenterStyle.setPattern(JCLineStyle.SHORT_DASH);
		hMarkerCenterStyle.setColor(java.awt.Color.BLUE);

		dataView.addMarker(vMarker1);
		dataView.addMarker(vMarker2);
		dataView.addMarker(vMarkerCenter);

		dataView.addMarker(hMarker1);
		dataView.addMarker(hMarker2);
		dataView.addMarker(hMarkerCenter);

		tabbedPane_1D = new JTabbedPane(JTabbedPane.TOP);
		sl_leftPanel_1D.putConstraint(SpringLayout.NORTH, tabbedPane_1D, 5, SpringLayout.NORTH, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.WEST, tabbedPane_1D, 5, SpringLayout.WEST, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.SOUTH, tabbedPane_1D, -300, SpringLayout.SOUTH, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.EAST, tabbedPane_1D, -5, SpringLayout.EAST, leftPanel_1D);
		leftPanel_1D.add(tabbedPane_1D);

		vertPanel_1D = new JPanel();
		tabbedPane_1D.addTab("Vertical", null, vertPanel_1D, null);
		vertPanel_1D.setLayout(new GridLayout(5, 0, 0, 0));

		leftMarkerPanel_1D = new JPanel();
		leftMarkerPanel_1D.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE, null, null));
		vertPanel_1D.add(leftMarkerPanel_1D);
		leftMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		leftMarkerPanel_1D_valueButton = new JPanel();
		leftMarkerPanel_1D.add(leftMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		leftMarkerPanel_1D_valueButton.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_11 = new JPanel();
		leftMarkerPanel_1D_valueButton.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));

		leftMarkerValueLabel = new JLabel("Left Value");
		leftMarkerValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(leftMarkerValueLabel, BorderLayout.CENTER);

		JPanel panel_17 = new JPanel();
		leftMarkerPanel_1D_valueButton.add(panel_17);

		btnMoveLeft = new JButton("Move");
		panel_17.add(btnMoveLeft);
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMotorLeftMarker();
			}
		});

		leftMarkerPanel_1D_LeftLabelPanel = new JPanel();
		leftMarkerPanel_1D_LeftLabelPanel
				.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), null, null));
		leftMarkerPanel_1D.add(leftMarkerPanel_1D_LeftLabelPanel, BorderLayout.WEST);
		leftMarkerPanel_1D_LeftLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblLeft = new JLabel("Left");
		lblLeft.setHorizontalAlignment(SwingConstants.CENTER);
		leftMarkerPanel_1D_LeftLabelPanel.add(lblLeft, BorderLayout.CENTER);
		lblLeft.setUI(new VerticalLabelUI(false));

		rightMarkerPanel_1D = new JPanel();
		rightMarkerPanel_1D.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE, null, null));
		vertPanel_1D.add(rightMarkerPanel_1D);
		rightMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		rightMarkerPanel_1D_valueButton = new JPanel();
		rightMarkerPanel_1D.add(rightMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		rightMarkerPanel_1D_valueButton.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_26 = new JPanel();
		rightMarkerPanel_1D_valueButton.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));

		rightMarkerValueLabel = new JLabel("Right Value");
		rightMarkerValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_26.add(rightMarkerValueLabel, BorderLayout.CENTER);

		JPanel panel_27 = new JPanel();
		rightMarkerPanel_1D_valueButton.add(panel_27);

		btnMoveRight = new JButton("Move");
		panel_27.add(btnMoveRight);
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMotorRightMarker();
			}
		});

		rightMarkerPanel_1D_RightLabelPanel = new JPanel();
		rightMarkerPanel_1D.add(rightMarkerPanel_1D_RightLabelPanel, BorderLayout.WEST);
		rightMarkerPanel_1D_RightLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_17 = new JLabel("Right");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		rightMarkerPanel_1D_RightLabelPanel.add(lblNewLabel_17, BorderLayout.CENTER);
		lblNewLabel_17.setUI(new VerticalLabelUI(false));

		centerMarkerPanel_1D = new JPanel();
		centerMarkerPanel_1D.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.BLUE, null, null));
		vertPanel_1D.add(centerMarkerPanel_1D);
		centerMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		centerMarkerPanel_1D_valueButton = new JPanel();
		centerMarkerPanel_1D.add(centerMarkerPanel_1D_valueButton, BorderLayout.CENTER);
		centerMarkerPanel_1D_valueButton.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_28 = new JPanel();
		centerMarkerPanel_1D_valueButton.add(panel_28);
		panel_28.setLayout(new BorderLayout(0, 0));

		centerMarkerValueLabel = new JLabel("Center Value");
		centerMarkerValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_28.add(centerMarkerValueLabel, BorderLayout.CENTER);

		JPanel panel_29 = new JPanel();
		centerMarkerPanel_1D_valueButton.add(panel_29);

		btnMoveCenter = new JButton("Move");
		panel_29.add(btnMoveCenter);
		btnMoveCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMotorCenterMarker();
			}
		});

		centerMarkerPanel_1D_CenterLabelPanel = new JPanel();
		centerMarkerPanel_1D.add(centerMarkerPanel_1D_CenterLabelPanel, BorderLayout.WEST);
		centerMarkerPanel_1D_CenterLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_18 = new JLabel("Center");
		lblNewLabel_18.setHorizontalAlignment(SwingConstants.CENTER);
		centerMarkerPanel_1D_CenterLabelPanel.add(lblNewLabel_18, BorderLayout.CENTER);
		lblNewLabel_18.setUI(new VerticalLabelUI(false));

		widthMarkerPanel_1D = new JPanel();
		widthMarkerPanel_1D.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.BLUE, null, null));
		vertPanel_1D.add(widthMarkerPanel_1D);
		widthMarkerPanel_1D.setLayout(new BorderLayout(0, 0));

		widthMarkerScanPanel_1D = new JPanel();
		widthMarkerPanel_1D.add(widthMarkerScanPanel_1D, BorderLayout.CENTER);
		widthMarkerScanPanel_1D.setLayout(new BorderLayout(0, 0));

		widthVerticalLabel = new JLabel("Width Value");
		widthVerticalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		widthMarkerScanPanel_1D.add(widthVerticalLabel, BorderLayout.CENTER);

		widthMarkerPanel_1D_WidthLabelPanel = new JPanel();
		widthMarkerPanel_1D.add(widthMarkerPanel_1D_WidthLabelPanel, BorderLayout.WEST);
		widthMarkerPanel_1D_WidthLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_19 = new JLabel("Width");
		lblNewLabel_19.setHorizontalAlignment(SwingConstants.CENTER);
		widthMarkerPanel_1D_WidthLabelPanel.add(lblNewLabel_19, BorderLayout.CENTER);
		lblNewLabel_19.setUI(new VerticalLabelUI(false));

		/*
		 * Start Scan Status
		 */
		JPanel scanStatusResetPanel = new JPanel();
		sl_leftPanel_1D.putConstraint(SpringLayout.NORTH, scanStatusResetPanel, 2, SpringLayout.SOUTH, tabbedPane_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.WEST, scanStatusResetPanel, 2, SpringLayout.WEST, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.SOUTH, scanStatusResetPanel, -240, SpringLayout.SOUTH, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.EAST, scanStatusResetPanel, -2, SpringLayout.EAST, leftPanel_1D);
		leftPanel_1D.add(scanStatusResetPanel);
		scanStatusResetPanel.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_1 = new JPanel();
		scanStatusResetPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scanStatusLabel = new JLabel(" Scan Status= ");
		panel_1.add(scanStatusLabel, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		scanStatusResetPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		fileNameLabel = new JLabel(" File Name= ");
		panel_2.add(fileNameLabel, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		scanStatusResetPanel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		JPanel centerDiffPanel_1D = new JPanel();
		centerDiffPanel_1D.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.BLUE, null, null));
		vertPanel_1D.add(centerDiffPanel_1D);
		centerDiffPanel_1D.setLayout(new GridLayout(2, 0, 0, 0));

		posPanel_1D = new JPanel();
		posPanel_1D.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_leftPanel_1D.putConstraint(SpringLayout.NORTH, posPanel_1D, 5, SpringLayout.SOUTH, scanStatusResetPanel);
		sl_leftPanel_1D.putConstraint(SpringLayout.WEST, posPanel_1D, 5, SpringLayout.WEST, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.SOUTH, posPanel_1D, -130, SpringLayout.SOUTH, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.EAST, posPanel_1D, -5, SpringLayout.EAST, leftPanel_1D);

		/*
		 * Start Scan Central Panel
		 */
		scanCenterPanel_1D = new JPanel();
		centerDiffPanel_1D.add(scanCenterPanel_1D);
		scanCenterPanel_1D.setLayout(new BorderLayout(0, 0));

		scanCenterLabel = new JLabel("scan center =");
		scanCenterPanel_1D.add(scanCenterLabel, BorderLayout.CENTER);

		JPanel diffPanel_1D = new JPanel();
		centerDiffPanel_1D.add(diffPanel_1D);
		diffPanel_1D.setLayout(new BorderLayout(0, 0));

		scanDiffLabel = new JLabel("Scan Diff =");
		diffPanel_1D.add(scanDiffLabel, BorderLayout.CENTER);
		/*
		 * End scan status
		 */
		JPanel horzPanel_1D = new JPanel();
		tabbedPane_1D.addTab("Horizontal", null, horzPanel_1D, null);
		horzPanel_1D.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel horzTopPanel_1D = new JPanel();
		horzPanel_1D.add(horzTopPanel_1D);
		horzTopPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzTopLabelPanel_1D = new JPanel();
		horzTopPanel_1D.add(horzTopLabelPanel_1D, BorderLayout.WEST);
		horzTopLabelPanel_1D.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_20 = new JLabel("Top");
		lblNewLabel_20.setUI(new VerticalLabelUI(false));
		lblNewLabel_20.setHorizontalAlignment(SwingConstants.CENTER);
		horzTopLabelPanel_1D.add(lblNewLabel_20, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		horzTopPanel_1D.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));

		horzTopValue = new JLabel("Horizontal Top");
		horzTopValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(horzTopValue, BorderLayout.CENTER);

		JPanel horzBottomPanel_1D = new JPanel();
		horzPanel_1D.add(horzBottomPanel_1D);
		horzBottomPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzBottomLabelPanel_1D = new JPanel();
		horzBottomPanel_1D.add(horzBottomLabelPanel_1D, BorderLayout.WEST);
		horzBottomLabelPanel_1D.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_21 = new JLabel("Bottom");
		lblNewLabel_21.setUI(new VerticalLabelUI(false));

		lblNewLabel_21.setHorizontalAlignment(SwingConstants.CENTER);
		horzBottomLabelPanel_1D.add(lblNewLabel_21, BorderLayout.CENTER);

		JPanel panel_5 = new JPanel();
		horzBottomPanel_1D.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		horzBotValue = new JLabel("Horizontal Bottom");
		horzBotValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(horzBotValue, BorderLayout.CENTER);

		JPanel horCenterPanel_1D = new JPanel();
		horzPanel_1D.add(horCenterPanel_1D);
		horCenterPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzCenterLabelPanel_1D = new JPanel();
		horCenterPanel_1D.add(horzCenterLabelPanel_1D, BorderLayout.WEST);
		horzCenterLabelPanel_1D.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_22 = new JLabel("Center");
		lblNewLabel_22.setUI(new VerticalLabelUI(false));
		lblNewLabel_22.setHorizontalAlignment(SwingConstants.CENTER);
		horzCenterLabelPanel_1D.add(lblNewLabel_22, BorderLayout.CENTER);

		JPanel panel_6 = new JPanel();
		horCenterPanel_1D.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));

		horzCenterValue = new JLabel("Horizontal Center ");
		horzCenterValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(horzCenterValue, BorderLayout.CENTER);

		JPanel horzWidthPanel_1D = new JPanel();
		horzPanel_1D.add(horzWidthPanel_1D);
		horzWidthPanel_1D.setLayout(new BorderLayout(0, 0));

		JPanel horzWidthLabelPanel_1D = new JPanel();
		horzWidthPanel_1D.add(horzWidthLabelPanel_1D, BorderLayout.WEST);
		horzWidthLabelPanel_1D.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_23 = new JLabel("Width");
		lblNewLabel_23.setUI(new VerticalLabelUI(false));
		lblNewLabel_23.setHorizontalAlignment(SwingConstants.CENTER);
		horzWidthLabelPanel_1D.add(lblNewLabel_23, BorderLayout.CENTER);

		JPanel panel_7 = new JPanel();
		horzWidthPanel_1D.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		horzWidthValue = new JLabel("Horizontal Width");
		horzWidthValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(horzWidthValue, BorderLayout.CENTER);
		leftPanel_1D.add(posPanel_1D);
		/*
		 * Start Range Panel
		 */
		JPanel ranegePanel_1D = new JPanel();
		sl_leftPanel_1D.putConstraint(SpringLayout.NORTH, ranegePanel_1D, 5, SpringLayout.SOUTH, posPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.SOUTH, ranegePanel_1D, -5, SpringLayout.SOUTH, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.WEST, ranegePanel_1D, 5, SpringLayout.WEST, leftPanel_1D);
		sl_leftPanel_1D.putConstraint(SpringLayout.EAST, ranegePanel_1D, -5, SpringLayout.EAST, leftPanel_1D);

		posPanel_1D.setLayout(new GridLayout(5, 0, 0, 0));

		JPanel pos_label_panel_1D = new JPanel();
		posPanel_1D.add(pos_label_panel_1D);
		pos_label_panel_1D.setLayout(new BorderLayout(0, 0));

		JLabel lblPositioners = new JLabel("Positioners");
		lblPositioners.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPositioners.setHorizontalAlignment(SwingConstants.CENTER);
		pos_label_panel_1D.add(lblPositioners, BorderLayout.CENTER);

		JPanel pos_1_panel = new JPanel();
		posPanel_1D.add(pos_1_panel);
		pos_1_panel.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_1 = new JCheckBox("x_positioner_1");
		xPositionerCheckBox_1.setSelected(true);
		xPositionerCheckBox_1.setVisible(false);
		pos_1_panel.add(xPositionerCheckBox_1, BorderLayout.CENTER);
		posXMap.put(0, xPositionerCheckBox_1);

		JPanel pos_2_panel = new JPanel();
		posPanel_1D.add(pos_2_panel);
		pos_2_panel.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_2 = new JCheckBox("x_positioner_2");
		xPositionerCheckBox_2.setVisible(false);

		pos_2_panel.add(xPositionerCheckBox_2, BorderLayout.CENTER);
		posXMap.put(1, xPositionerCheckBox_2);

		JPanel pos_3_panel = new JPanel();
		posPanel_1D.add(pos_3_panel);
		pos_3_panel.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_3 = new JCheckBox("x_positioner_3");
		xPositionerCheckBox_3.setVisible(false);
		pos_3_panel.add(xPositionerCheckBox_3, BorderLayout.CENTER);
		posXMap.put(2, xPositionerCheckBox_3);

		JPanel pos_4_panel = new JPanel();
		posPanel_1D.add(pos_4_panel);
		pos_4_panel.setLayout(new BorderLayout(0, 0));

		xPositionerCheckBox_4 = new JCheckBox("x_positioner_4");
		xPositionerCheckBox_4.setVisible(false);
		pos_4_panel.add(xPositionerCheckBox_4, BorderLayout.CENTER);
		posXMap.put(3, xPositionerCheckBox_4);

		posXButtonGroup.add(xPositionerCheckBox_1);
		posXButtonGroup.add(xPositionerCheckBox_2);
		posXButtonGroup.add(xPositionerCheckBox_3);
		posXButtonGroup.add(xPositionerCheckBox_4);
		xPositionerCheckBox_1.addActionListener(new MainPanel_1_xPositionerCheckBox_1_actionAdapter(this));
		xPositionerCheckBox_2.addActionListener(new MainPanel_1_xPositionerCheckBox_2_actionAdapter(this));
		xPositionerCheckBox_3.addActionListener(new MainPanel_1_xPositionerCheckBox_3_actionAdapter(this));
		xPositionerCheckBox_4.addActionListener(new MainPanel_1_xPositionerCheckBox_4_actionAdapter(this));

		sl_leftPanel_1D.putConstraint(SpringLayout.WEST, ranegePanel_1D, 0, SpringLayout.WEST, leftPanel_1D);
		leftPanel_1D.add(ranegePanel_1D);
		ranegePanel_1D.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		ranegePanel_1D.add(tabbedPane_1, BorderLayout.CENTER);

		JPanel x_rangePanel_1D = new JPanel();
		tabbedPane_1.addTab("x-range", null, x_rangePanel_1D, null);
		x_rangePanel_1D.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel_11 = new JLabel("Min");
		x_rangePanel_1D.add(lblNewLabel_11, "cell 0 0,alignx trailing");

		xRangeMin_textField_1D = new JTextField();
		x_rangePanel_1D.add(xRangeMin_textField_1D, "cell 1 0,growx");
		xRangeMin_textField_1D.setColumns(10);
		xRangeMin_textField_1D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = validateTextFieldEntry(xRangeMin_textField_1D.getText());
				if (b) {
					if (!autoScale) {
						data1D.setxAxisUserMin(Double.parseDouble(xRangeMin_textField_1D.getText()));
						data1D.setXAxisScale();
					}
				} else {
					showAlert(" Numeric values only allowed ");
				}
			}
		});

		JLabel lblNewLabel_12 = new JLabel("Max");
		x_rangePanel_1D.add(lblNewLabel_12, "cell 0 1,alignx trailing");

		xRangeMax_textField_1D = new JTextField();
		x_rangePanel_1D.add(xRangeMax_textField_1D, "cell 1 1,growx");
		xRangeMax_textField_1D.setColumns(10);
		xRangeMax_textField_1D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = validateTextFieldEntry(xRangeMax_textField_1D.getText());
				if (b) {
					if (!autoScale) {
						data1D.setxAxisUserMax(Double.parseDouble(xRangeMax_textField_1D.getText()));
						data1D.setXAxisScale();
					}
				} else {
					showAlert(" Numeric values only allowed ");
				}
			}
		});

		JPanel y_rangePanel_1D = new JPanel();
		tabbedPane_1.addTab("y-range", null, y_rangePanel_1D, null);
		y_rangePanel_1D.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel_10 = new JLabel("Min");
		y_rangePanel_1D.add(lblNewLabel_10, "cell 0 0,alignx trailing");

		yRangeMin_textField_1D = new JTextField();
		y_rangePanel_1D.add(yRangeMin_textField_1D, "cell 1 0,growx");
		yRangeMin_textField_1D.setColumns(10);

		

		
		JLabel lblNewLabel_13 = new JLabel("Max");
		y_rangePanel_1D.add(lblNewLabel_13, "cell 0 1,alignx trailing");

		yRangeMax_textField_1D = new JTextField();
		y_rangePanel_1D.add(yRangeMax_textField_1D, "cell 1 1,growx");
		yRangeMax_textField_1D.setColumns(10);

		
		yRangeMax_textField_1D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = validateTextFieldEntry(yRangeMax_textField_1D.getText());
				if (b) {
					if (!autoScale) {
						data1D.setyAxisUserMax(Double.parseDouble(yRangeMax_textField_1D.getText()));
						data1D.setYAxisScale();
					}
				} else {
					showAlert(" Numeric values only allowed ");
				}
			}
		});

		
		JPanel panel = new JPanel();
		ranegePanel_1D.add(panel, BorderLayout.SOUTH);

		userCheckBox = new JCheckBox("User");
		panel.add(userCheckBox);

		userCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAutoScale(false);
				double dx1 = Double.parseDouble(xRangeMin_textField_1D.getText());
				double dx2 = Double.parseDouble(xRangeMax_textField_1D.getText());
				double dy1 = Double.parseDouble(yRangeMin_textField_1D.getText());
				double dy2 = Double.parseDouble(yRangeMax_textField_1D.getText());

				if ((dx2 <= dx1) || (dy2 <= dy1)) {
					showAlert(" xMin should be less than xMax");

				} else {
					data1D.setxAxisUserMin(dx1);
					data1D.setxAxisUserMax(dx2);
					data1D.setXAxisScale();
				}
			}
		});

		autoCheckBox = new JCheckBox("Auto");
		autoCheckBox.setSelected(true);
		panel.add(autoCheckBox);
		autoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAutoScale(true);
			}
		});

		autoUserGroup.add(autoCheckBox);
		autoUserGroup.add(userCheckBox);
		JButton resetMarkerButton = new JButton("Reset Markers");
		panel_3.add(resetMarkerButton, BorderLayout.CENTER);
		panel_1D.add(detectorPanel_1D);
		
		resetMarkerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setMarkers("Reset");
				
			}
			
		});
		detectorPanel_1D.setLayout(new BorderLayout(0, 0));

		detectorTabbedPane_1D = new JTabbedPane(JTabbedPane.TOP);
		detectorTabbedPane_1D.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		detectorPanel_1D.add(detectorTabbedPane_1D, BorderLayout.CENTER);

		detectorPanel_1D_1_30 = new JPanel();
		detectorTabbedPane_1D.addTab("Detectors 1-30", null, detectorPanel_1D_1_30, null);
		detectorPanel_1D_1_30.setLayout(new GridLayout(10, 3, 0, 0));

		detectorPanel_1D_31_60 = new JPanel();
		detectorTabbedPane_1D.addTab("Detectors 31-60", null, detectorPanel_1D_31_60, null);
		detectorPanel_1D_31_60.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_2D = new JPanel();
		panel_2D.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("2-D Scan", null, panel_2D, null);
		SpringLayout sl_panel_2D = new SpringLayout();
		panel_2D.setLayout(sl_panel_2D);

		JPanel leftPanel_2D = new JPanel();
		sl_panel_2D.putConstraint(SpringLayout.SOUTH, leftPanel_2D, -5, SpringLayout.SOUTH, panel_2D);
		sl_panel_2D.putConstraint(SpringLayout.EAST, leftPanel_2D, 180, SpringLayout.WEST, panel_2D);
		leftPanel_2D.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, null, Color.BLUE, null));
		sl_panel_2D.putConstraint(SpringLayout.NORTH, leftPanel_2D, 5, SpringLayout.NORTH, panel_2D);
		sl_panel_2D.putConstraint(SpringLayout.WEST, leftPanel_2D, 5, SpringLayout.WEST, panel_2D);
		panel_2D.add(leftPanel_2D);

		JPanel panel_2D_Plot = new JPanel();
		sl_panel_2D.putConstraint(SpringLayout.NORTH, panel_2D_Plot, 5, SpringLayout.NORTH, panel_2D);
		sl_panel_2D.putConstraint(SpringLayout.WEST, panel_2D_Plot, 5, SpringLayout.EAST, leftPanel_2D);
		sl_panel_2D.putConstraint(SpringLayout.SOUTH, panel_2D_Plot, -5, SpringLayout.SOUTH, panel_2D);
		sl_panel_2D.putConstraint(SpringLayout.EAST, panel_2D_Plot, -5, SpringLayout.EAST, panel_2D);
		panel_2D_Plot.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, null, Color.BLUE, null));
		panel_2D.add(panel_2D_Plot);
		panel_2D_Plot.setLayout(new BorderLayout(0, 0));
		
		chart3dJava2d = new JCChart3dJava2d();
		panel_2D_Plot.add(chart3dJava2d, BorderLayout.CENTER);
		SpringLayout sl_leftPanel_2D = new SpringLayout();
		leftPanel_2D.setLayout(sl_leftPanel_2D);
		
		dataView3D = chart3dJava2d.getDataView(0);
		chart3dJava2d.getActionTable().addAllDefaultActions();
		
		chart3dJava2d.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	chart3dJava2d_mouseClicked(e);
            }
        });

        dataView3D.getContour().setZoned(true);
        dataView3D.getContour().setContoured(true);
        dataView3D.getElevation().setTransparent(false);
        dataView3D.getElevation().setMeshed(true);
        dataView3D.getElevation().setShaded(true);
        setAnnotationFontSize(50);
        setContourDisplay();
        setSelectedXPositionerFontSize(20);
        setSelectedYPositionerFontSize(20);
        chart3dJava2d.setAllowUserChanges(true);
        
		JPanel surfaceCountourPanel = new JPanel();
		surfaceCountourPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, surfaceCountourPanel, 5, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, surfaceCountourPanel, 5, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, surfaceCountourPanel, 45, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, surfaceCountourPanel, -5, SpringLayout.EAST, leftPanel_2D);
		leftPanel_2D.add(surfaceCountourPanel);
		surfaceCountourPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		rdbtnSurfaceRadioButton = new JRadioButton("Surface Plot");
		surfaceCountourPanel.add(rdbtnSurfaceRadioButton);
		
		rdbtnSurfaceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSurfaceDisplay();
			}
		});
		
		rdbtnContourRadioButton = new JRadioButton("Contour Plot");
		rdbtnContourRadioButton.setSelected(true);
		surfaceCountourPanel.add(rdbtnContourRadioButton);
		rdbtnContourRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContourDisplay();
			}
		});		
		
		contourSurfaceGroup.add(rdbtnSurfaceRadioButton);
		contourSurfaceGroup.add(rdbtnContourRadioButton);
       
		JPanel xyzPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, xyzPanel, 50, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, xyzPanel, 5, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, xyzPanel, 160, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, xyzPanel, -5, SpringLayout.EAST, leftPanel_2D);
		xyzPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		leftPanel_2D.add(xyzPanel);
		xyzPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		xyzPanel.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel x_AxisPosLabel2D = new JLabel("X=");
		panel_13.add(x_AxisPosLabel2D, "cell 0 0");
		
		x_Pos_2D_value = new JLabel("x-position");
		panel_13.add(x_Pos_2D_value, "cell 1 0");
		
		JLabel y_AxisPosLabel2D = new JLabel("Y=");
		panel_13.add(y_AxisPosLabel2D, "cell 0 1");
		
	    JLabel intensityLabel_2D = new JLabel("Int");
		panel_13.add(intensityLabel_2D, "cell 0 2");
		
		y_Pos_2D_value = new JLabel("y-position");
		panel_13.add(y_Pos_2D_value, "cell 1 1");
		
		int_2D_value = new JLabel("Intensity");
		panel_13.add(int_2D_value, "cell 1 2");

		JPanel panel_14 = new JPanel();
		xyzPanel.add(panel_14, BorderLayout.SOUTH);
		
		JButton btnNewButton2D = new JButton("Move");
		panel_14.add(btnNewButton2D);
		btnNewButton2D.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {				
				moveXYmotors(pickedX,pickedY);
			}			
		});
		
		JPanel positioner_2D_Panel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, positioner_2D_Panel, 5, SpringLayout.SOUTH, xyzPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, positioner_2D_Panel, 0, SpringLayout.WEST, xyzPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, positioner_2D_Panel, 151, SpringLayout.SOUTH, xyzPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, positioner_2D_Panel, 0, SpringLayout.EAST, xyzPanel);
		positioner_2D_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		leftPanel_2D.add(positioner_2D_Panel);
		positioner_2D_Panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		positioner_2D_Panel.add(tabbedPane_2, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		tabbedPane_2.addTab("X-Axis", null, panel_12, null);
		panel_12.setLayout(new GridLayout(4, 0, 0, 0));
		
		chckbx_2D_Xpositioner1 = new JCheckBox("New check box");
		chckbx_2D_Xpositioner1.setSelected(true);
		chckbx_2D_Xpositioner1.setVisible(false);
		panel_12.add(chckbx_2D_Xpositioner1);
		
		chckbx_2D_Xpositioner1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 0;
				data2D.setSelectedPositioner_X(0);
			}
		});

		chckbx_2D_Xpositioner2 = new JCheckBox("New check box");
		chckbx_2D_Xpositioner2.setVisible(false);
		panel_12.add(chckbx_2D_Xpositioner2);
		
		chckbx_2D_Xpositioner2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 1;
				data2D.setSelectedPositioner_X(1);
			}
		});

		chckbx_2D_Xpositioner3 = new JCheckBox("New check box");
		chckbx_2D_Xpositioner3.setVisible(false);

		panel_12.add(chckbx_2D_Xpositioner3);
		
		chckbx_2D_Xpositioner3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 2;
				data2D.setSelectedPositioner_X(2);
			}
		});

		
		chckbx_2D_Xpositioner4 = new JCheckBox("New check box");
		chckbx_2D_Xpositioner4.setVisible(false);

		panel_12.add(chckbx_2D_Xpositioner4);
				
		chckbx_2D_Xpositioner4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 3;
				data2D.setSelectedPositioner_X(3);
			}
		});

		
		xPos_2DGroup.add(chckbx_2D_Xpositioner1);
		xPos_2DGroup.add(chckbx_2D_Xpositioner2);
		xPos_2DGroup.add(chckbx_2D_Xpositioner3);
		xPos_2DGroup.add(chckbx_2D_Xpositioner4);		
		
		posX2DMap.put(0, chckbx_2D_Xpositioner1);
		posX2DMap.put(1, chckbx_2D_Xpositioner2);
		posX2DMap.put(2, chckbx_2D_Xpositioner3);
		posX2DMap.put(3, chckbx_2D_Xpositioner4);
		
		JPanel panel_15 = new JPanel();
		tabbedPane_2.addTab("Y-Axis", null, panel_15, null);
		panel_15.setLayout(new GridLayout(4, 0, 0, 0));
		
		chckbx_2D_Ypositioner1 = new JCheckBox("New check box");
		chckbx_2D_Ypositioner1.setSelected(true);
		chckbx_2D_Ypositioner1.setVisible(false);
		panel_15.add(chckbx_2D_Ypositioner1);
		
		chckbx_2D_Ypositioner1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 0;
				data2D.setSelectedPositioner_Y(0);
			}
		});

				
		chckbx_2D_Ypositioner2 = new JCheckBox("New check box");
		chckbx_2D_Ypositioner2.setVisible(false);

		panel_15.add(chckbx_2D_Ypositioner2);
		
		chckbx_2D_Ypositioner2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 1;
				data2D.setSelectedPositioner_Y(1);
			}
		});


		chckbx_2D_Ypositioner3 = new JCheckBox("New check box");
		chckbx_2D_Ypositioner3.setVisible(false);

		panel_15.add(chckbx_2D_Ypositioner3);
		
		chckbx_2D_Ypositioner3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 2;
				data2D.setSelectedPositioner_Y(2);
			}
		});
		
		chckbx_2D_Ypositioner4 = new JCheckBox("New check box");
		chckbx_2D_Ypositioner4.setVisible(false);

		panel_15.add(chckbx_2D_Ypositioner4);
		
		chckbx_2D_Ypositioner4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 3;
				data2D.setSelectedPositioner_Y(3);
			}
		});

		
		yPos_2DGroup.add(chckbx_2D_Ypositioner1);
		yPos_2DGroup.add(chckbx_2D_Ypositioner2);
		yPos_2DGroup.add(chckbx_2D_Ypositioner3);
		yPos_2DGroup.add(chckbx_2D_Ypositioner4);		
		
		posYMap.put(0, chckbx_2D_Ypositioner1);
		posYMap.put(1, chckbx_2D_Ypositioner2);
		posYMap.put(2, chckbx_2D_Ypositioner3);
		posYMap.put(3, chckbx_2D_Ypositioner4);

		JPanel detectorLabelPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorLabelPanel, 2, SpringLayout.SOUTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorLabelPanel, 5, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorLabelPanel, 20, SpringLayout.SOUTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorLabelPanel, 0, SpringLayout.EAST, positioner_2D_Panel);
		leftPanel_2D.add(detectorLabelPanel);
		detectorLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Detectors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detectorLabelPanel.add(lblNewLabel, BorderLayout.CENTER);


		JPanel detectorSelectionPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorSelectionPanel, 22, SpringLayout.SOUTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorSelectionPanel, 40, SpringLayout.SOUTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorSelectionPanel, 0, SpringLayout.EAST, positioner_2D_Panel);
		detectorSelectionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorSelectionPanel, 5, SpringLayout.WEST, leftPanel_2D);
		leftPanel_2D.add(detectorSelectionPanel);
		detectorSelectionPanel.setLayout(new BorderLayout(0, 0));
		
		detComboBox_2D = new JComboBox<String>();
		detectorSelectionPanel.add(detComboBox_2D, BorderLayout.CENTER);		
		
		JPanel panel_1D_old = new JPanel();
		tabbedPane.addTab("Old Files (1-D)", null, panel_1D_old, null);
		SpringLayout sl_panel_1D_old = new SpringLayout();
		panel_1D_old.setLayout(sl_panel_1D_old);
		
		JPanel panel_8 = new JPanel();
		sl_panel_1D_old.putConstraint(SpringLayout.NORTH, panel_8, 0, SpringLayout.NORTH, panel_1D_old);
		sl_panel_1D_old.putConstraint(SpringLayout.WEST, panel_8, 5, SpringLayout.WEST, panel_1D_old);
		sl_panel_1D_old.putConstraint(SpringLayout.SOUTH, panel_8, 345, SpringLayout.NORTH, panel_1D_old);
		sl_panel_1D_old.putConstraint(SpringLayout.EAST, panel_8, -5, SpringLayout.EAST, panel_1D_old);
		panel_1D_old.add(panel_8);
		
		JPanel panel_9 = new JPanel();
		sl_panel_1D_old.putConstraint(SpringLayout.NORTH, panel_9, 6, SpringLayout.SOUTH, panel_8);
		SpringLayout sl_panel_8 = new SpringLayout();
		panel_8.setLayout(sl_panel_8);
		
		JPanel panel_oldMarkers = new JPanel();
		sl_panel_8.putConstraint(SpringLayout.NORTH, panel_oldMarkers, 0, SpringLayout.NORTH, panel_8);
		sl_panel_8.putConstraint(SpringLayout.WEST, panel_oldMarkers, 0, SpringLayout.WEST, panel_8);
		sl_panel_8.putConstraint(SpringLayout.SOUTH, panel_oldMarkers, 345, SpringLayout.NORTH, panel_8);
		sl_panel_8.putConstraint(SpringLayout.EAST, panel_oldMarkers, 161, SpringLayout.WEST, panel_8);
		panel_8.add(panel_oldMarkers);
		
		JPanel panel_oldChart = new JPanel();
		sl_panel_8.putConstraint(SpringLayout.NORTH, panel_oldChart, 0, SpringLayout.NORTH, panel_8);
		sl_panel_8.putConstraint(SpringLayout.WEST, panel_oldChart, 0, SpringLayout.EAST, panel_oldMarkers);
		sl_panel_8.putConstraint(SpringLayout.SOUTH, panel_oldChart, 0, SpringLayout.SOUTH, panel_oldMarkers);
		sl_panel_8.putConstraint(SpringLayout.EAST, panel_oldChart, 622, SpringLayout.EAST, panel_oldMarkers);
		SpringLayout sl_panel_oldMarkers = new SpringLayout();
		panel_oldMarkers.setLayout(sl_panel_oldMarkers);
		
		JPanel panel_10 = new JPanel();
		sl_panel_oldMarkers.putConstraint(SpringLayout.NORTH, panel_10, 5, SpringLayout.NORTH, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.WEST, panel_10, 5, SpringLayout.WEST, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.SOUTH, panel_10, 30, SpringLayout.NORTH, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.EAST, panel_10, -5, SpringLayout.EAST, panel_oldMarkers);
		panel_oldMarkers.add(panel_10);
		
		JPanel panel_16 = new JPanel();
		sl_panel_oldMarkers.putConstraint(SpringLayout.NORTH, panel_16, 5, SpringLayout.SOUTH, panel_10);
		sl_panel_oldMarkers.putConstraint(SpringLayout.WEST, panel_16, 5, SpringLayout.WEST, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.SOUTH, panel_16, 30, SpringLayout.SOUTH, panel_10);
		sl_panel_oldMarkers.putConstraint(SpringLayout.EAST, panel_16, 0, SpringLayout.EAST, panel_10);
		panel_oldMarkers.add(panel_16);
		
		JPanel panel_18 = new JPanel();
		sl_panel_oldMarkers.putConstraint(SpringLayout.NORTH, panel_18, 5, SpringLayout.SOUTH, panel_16);
		sl_panel_oldMarkers.putConstraint(SpringLayout.WEST, panel_18, 5, SpringLayout.WEST, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.SOUTH, panel_18, 30, SpringLayout.SOUTH, panel_16);
		panel_16.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_22 = new JPanel();
		panel_16.add(panel_22);
		
		JLabel lblOld_1D_Right_Label = new JLabel("New label");
		panel_22.add(lblOld_1D_Right_Label);
		
		JPanel panel_23 = new JPanel();
		panel_16.add(panel_23);
		
		JLabel lblOld_1D_Right_Value = new JLabel("New label");
		panel_23.add(lblOld_1D_Right_Value);
		sl_panel_oldMarkers.putConstraint(SpringLayout.EAST, panel_18, 0, SpringLayout.EAST, panel_10);
		panel_oldMarkers.add(panel_18);
		
		JPanel panel_19 = new JPanel();
		sl_panel_oldMarkers.putConstraint(SpringLayout.NORTH, panel_19, 5, SpringLayout.SOUTH, panel_18);
		sl_panel_oldMarkers.putConstraint(SpringLayout.WEST, panel_19, 5, SpringLayout.WEST, panel_oldMarkers);
		sl_panel_oldMarkers.putConstraint(SpringLayout.SOUTH, panel_19, 30, SpringLayout.SOUTH, panel_18);
		panel_18.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_24 = new JPanel();
		panel_18.add(panel_24);
		
		JLabel lblOld_1D_Center_Label = new JLabel("New label");
		panel_24.add(lblOld_1D_Center_Label);
		
		JPanel panel_25 = new JPanel();
		panel_18.add(panel_25);
		
		JLabel lblOld_1D_Center_Value = new JLabel("New label");
		panel_25.add(lblOld_1D_Center_Value);
		sl_panel_oldMarkers.putConstraint(SpringLayout.EAST, panel_19, 0, SpringLayout.EAST, panel_10);
		panel_10.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_20 = new JPanel();
		panel_10.add(panel_20);
		
		JLabel lblOld_1D_Left_Label = new JLabel("New label");
		panel_20.add(lblOld_1D_Left_Label);
		
		JPanel panel_21 = new JPanel();
		panel_10.add(panel_21);
		
		JLabel lblOld_1D_Left_Value = new JLabel("New label");
		panel_21.add(lblOld_1D_Left_Value);
		panel_oldMarkers.add(panel_19);
		panel_19.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_30 = new JPanel();
		panel_19.add(panel_30);
		
		JLabel lblOld_1D_Width_Label = new JLabel("New label");
		panel_30.add(lblOld_1D_Width_Label);
		
		JPanel panel_31 = new JPanel();
		panel_19.add(panel_31);
		
		JLabel lblOld_1D_Width_Value = new JLabel("New label");
		panel_31.add(lblOld_1D_Width_Value);
		panel_8.add(panel_oldChart);
		panel_oldChart.setLayout(new BorderLayout(0, 0));
		
		oldChart = new JCChart();
		panel_oldChart.add(oldChart, BorderLayout.CENTER);
		sl_panel_1D_old.putConstraint(SpringLayout.WEST, panel_9, 5, SpringLayout.WEST, panel_1D_old);
		sl_panel_1D_old.putConstraint(SpringLayout.SOUTH, panel_9, -5, SpringLayout.SOUTH, panel_1D_old);
		sl_panel_1D_old.putConstraint(SpringLayout.EAST, panel_9, -5, SpringLayout.EAST, panel_1D_old);
		panel_1D_old.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		panel_9.add(tabbedPane_3, BorderLayout.CENTER);
		
		JPanel panel_2D_old = new JPanel();
		tabbedPane.addTab("Old Files (2-D)", null, panel_2D_old, null);
		SpringLayout sl_panel_2D_old = new SpringLayout();
		panel_2D_old.setLayout(sl_panel_2D_old);
		
		JPanel leftPanel_2D_old = new JPanel();
		sl_panel_2D_old.putConstraint(SpringLayout.NORTH, leftPanel_2D_old, 5, SpringLayout.NORTH, panel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.WEST, leftPanel_2D_old, 5, SpringLayout.WEST, panel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.SOUTH, leftPanel_2D_old, -5, SpringLayout.SOUTH, panel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.EAST, leftPanel_2D_old, 180, SpringLayout.WEST, panel_2D_old);
		panel_2D_old.add(leftPanel_2D_old);
		
		JPanel panel_2D_old_Plot = new JPanel();
		sl_panel_2D_old.putConstraint(SpringLayout.NORTH, panel_2D_old_Plot, 5, SpringLayout.NORTH, panel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.WEST, panel_2D_old_Plot, 5, SpringLayout.EAST, leftPanel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.SOUTH, panel_2D_old_Plot, -5, SpringLayout.SOUTH, panel_2D_old);
		sl_panel_2D_old.putConstraint(SpringLayout.EAST, panel_2D_old_Plot, -5, SpringLayout.EAST, panel_2D_old);
		panel_2D_old.add(panel_2D_old_Plot);
		panel_2D_old_Plot.setLayout(new BorderLayout(0, 0));

		detComboBox_2D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(" Selected Combo Item # = "+detComboBox_2D.getItemCount());
				if (detComboBox_2D.getItemCount()>0)
				data2D.setSelectedDetector(detComboBox_2D.getSelectedIndex());
			}
		});
		addDetectorPanels_1D();
		this.updateUI();
	}
	
	public JCChart getChart() {
		return chart;
	}

	public JCChart getOldChart(){
		return oldChart;
	}
	public JCChart3dJava2d get3DChart(){
		return chart3dJava2d;
	}
		
	public void set1DDataSource(Data1D d1) {
		data1D = d1;
	}

	public void set2DDataSource(Data2D d2) {
		data2D = d2;
	}

    public void setContourDisplay() {
        dataView3D.getContour().setZoned(true);
        dataView3D.getContour().setContoured(true);
        dataView3D.getElevation().setTransparent(false);
        dataView3D.getElevation().setMeshed(false);
        dataView3D.getElevation().setShaded(false);

    }

    public void setSurfaceDisplay() {
        dataView3D.getContour().setZoned(true);
        dataView3D.getContour().setContoured(true);
        dataView3D.getElevation().setTransparent(false);
        dataView3D.getElevation().setMeshed(true);
        dataView3D.getElevation().setShaded(true);
    }

    public void setAnnotationFontSize(int n) {

        JCChart3dArea jcArea = chart3dJava2d.getChart3dArea();
        
      
        com.klg.jclass.chart3d.JCAxis jx = jcArea.getXAxis();
        com.klg.jclass.chart3d.JCAxis jy = jcArea.getYAxis();
        com.klg.jclass.chart3d.JCAxis jz = jcArea.getZAxis();
        jx.setAnnoFontCubeSize(n);
        jy.setAnnoFontCubeSize(n);
        jz.setAnnoFontCubeSize(n);

    }
    
    public void chart3dJava2d_mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        int nX;
        int nY;
        double zz;
        if (isValidLocation(p)) {
            JCData3dIndex index = chart3dJava2d.pick(p, dataView3D);
            if (index instanceof JCData3dGridIndex) {
                JCData3dGridIndex gridIndex = (JCData3dGridIndex) index;
                nX = gridIndex.getX();
                nY = gridIndex.getY();
                pickedPoint3D = true;
                pickedX = data2D.getXValue(nX);
                pickedY = data2D.getYValue(nY);
                zz = data2D.getZValue(nX, nY);
                x_Pos_2D_value.setText(String.valueOf(pickedX));
                y_Pos_2D_value.setText(String.valueOf(pickedY));
                int_2D_value.setText(String.valueOf(zz));
 //               System.out.println(" Mouse Clicked in Chart area ");
           }
        }
    }
    private boolean isValidLocation(Point p) {
        Point3d p3 = dataView3D.map(p.x, p.y);
        if (p3 == null ||
            p3.x == HOLE_VALUE ||
            p3.y == HOLE_VALUE ||
            p3.z == HOLE_VALUE) {
            return (false);
        }

        return (true);
    }

    public void setSelectedXPositionerFontSize(int n) {
    	chart3dJava2d.getChart3dArea().getXAxis().setAnnoFontCubeSize(n);
    	chart3dJava2d.getChart3dArea().getXAxis().setTitleFontCubeSize(n);

    }

    public void setSelectedYPositionerFontSize(int n) {
    	chart3dJava2d.getChart3dArea().getYAxis().setAnnoFontCubeSize(n);
    	chart3dJava2d.getChart3dArea().getYAxis().setTitleFontCubeSize(n);

    }

    
	public void addDetectorPanels_1D() {

		for (int i = 0; i < 30; i++) {
			detPanel = new DetectorColorPanel();

			detPanel.addJDetCheckBoxName(Integer.toString(i));
			detPanel.addDetColorButtonName(Integer.toString(i));

			detPanel.getDetPanelButton().addActionListener(new MainPanel_1D_detPanelButton_actionAdapter(this));			
			detPanel.getDetPanelCheckBox().addItemListener(new MainPanel_1D_detPanelCheckBox_actionAdapter(this));

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
			detPanel.getDetPanelButton().addActionListener(new MainPanel_1D_detPanelButton_actionAdapter(this));			
			detPanel.getDetPanelCheckBox().addItemListener(new MainPanel_1D_detPanelCheckBox_actionAdapter(this));
			detMap_1D.put(i, detPanel);
			detectorPanel_1D_31_60.add(detPanel);
		}
	}
	
	public void add2DDetector(String str){
		detComboBox_2D.addItem(str);
	}
	
	public void clear2dDetector(){
		detComboBox_2D.removeAllItems();
		detComboBox_2D.getItemCount();
	}
	

	public void setXPositionerVisible_1D(int pos, boolean b) {

		JCheckBox jb = posXMap.get(pos);
		jb.setEnabled(b);
		jb.setVisible(b);
	}

	public void resetPositioners_1D(){
		for (int i=0;i<4;i++){
			setXPositionerVisible_1D(i,false);
		}
	}
	public void resetPositioners_2D(){
		for (int i=0;i<4;i++){
			setXPositionerVisible_2D(i,false);
			setYPositionerVisible_2D(i,false);
		}
	}
	
	public void resetDetectors(){
		
		for (int i=0;i<60;i++){
				setDetVisible(i,false);
			}
	}
	
	public void setXPositionerVisible_2D(int pos, boolean b) {
		JCheckBox jb = posX2DMap.get(pos);
		jb.setEnabled(b);
		jb.setVisible(b);
	}
	public void setYPositionerVisible_2D(int pos, boolean b) {
		JCheckBox jb = posYMap.get(pos);
		jb.setEnabled(b);
		jb.setVisible(b);
	}
	
	public void setXPositionerName_1D(int pos, String str) {
//		System.out.println(" In MainPanel setXPositionerName_1D  "+" Pos = "+pos+"  str = "+str);
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
		pvName = firstPart + ".RTYP";
		pvObject = new EpicsDataObject(pvName, true);
		countDownConnection.pendIO();

		rtyp = pvObject.getVal();
		pvObject.setDropPv(true);

		PVDescription pvDescription = new PVDescription(firstPart, secondPart, rtyp, jb);
		pvDescription.makeEpicsDataObject();

//		new Thread (pvDescription).start();
//		countDownConnection.pendIO();

		jb.setText(pvDescription.getDescription());
		pvDescription.disconnectChannel();
	}
	
	public void setYPositionerName_2D(int pos, String str) {
//		System.out.println(" In MainPanel setYPositionerName_2D  "+" Pos = "+pos+"  str = "+str);

		JCheckBox jb = posYMap.get(pos);
		String firstPart;
		String secondPart;
		String rtyp;
		int lastIndexOfDot;
		String pvName;
		EpicsDataObject pvObject;

		lastIndexOfDot = str.lastIndexOf(".");
		firstPart = str.substring(0, lastIndexOfDot);
		secondPart = str.substring(lastIndexOfDot + 1);
		pvName = firstPart + ".RTYP";
		pvObject = new EpicsDataObject(pvName, true);
		countDownConnection.pendIO();

		rtyp = pvObject.getVal();
		pvObject.setDropPv(true);

		PVDescription pvDescription = new PVDescription(firstPart, secondPart, rtyp, jb);
		pvDescription.makeEpicsDataObject();
//		countDownConnection.pendIO();
		jb.setText(pvDescription.getDescription());
		pvDescription.disconnectChannel();
	}

	public void setXPositionerName_2D(int pos) {
		JCheckBox jb1D = posXMap.get(pos);
		JCheckBox jb2D = posX2DMap.get(pos);
		String posName = jb1D.getText();
		jb2D.setText(posName);
		
	}
	
	public String getPositionerName(int pos){
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

	public void setDetectorName(int det, String str) {
//		System.out.println(" In MainPanel setDetectorName  "+" det = "+det+"  str = "+str);
		DetectorColorPanel detPanel = detMap_1D.get(det);
		JCheckBox jb = detPanel.getDetPanelCheckBox();
		String firstPart;
		String secondPart;
		String rtyp;
		int lastIndexOfDot;
		String pvName;
		EpicsDataObject pvObject = null;
		String detName;

		lastIndexOfDot = str.lastIndexOf(".");
		firstPart = str.substring(0, lastIndexOfDot);
		secondPart = str.substring(lastIndexOfDot + 1);
		pvName = firstPart + ".RTYP";
		pvObject = new EpicsDataObject(pvName, true);
		countDownConnection.pendIO();

		rtyp = pvObject.getVal();
		pvObject.setDropPv(true);

		PVDescription pvDescription = new PVDescription(firstPart, secondPart, rtyp, jb);
		pvDescription.makeEpicsDataObject();
//		new Thread (pvDescription).start();
//		countDownConnection.pendIO();
		detName = pvDescription.getDescription();
		jb.setText(detName);
		pvDescription.disconnectChannel();
	}

	public String getDetectorName(int det){
		String ret = "";
		DetectorColorPanel detPanel = detMap_1D.get(det);
		JCheckBox jb = detPanel.getDetPanelCheckBox();
//		ret = jb.getName();
		ret = jb.getText();
		return ret;
	}
	
	
	public void detectorStatus1D(String str,boolean b){
		int n = Integer.parseInt(str);
		data1D.setDetectorForDisplay(n, b);
	}
	
	public void detectorColorStatus1D(String str){
		int nm = Integer.parseInt(str);
		StylePicker2 sp = new StylePicker2();
		int thickness = data1D.getSeriesThickness(nm);
		int shape = data1D.getSeriesSymbol(nm);
		int symbolSize = data1D.getSeriesSymbolSize(nm);
//		Color lineColor = data1D.getSeriesLineColor(nm);
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
	
	
	public void detectorStatus2D(String str,boolean b){
//		int n = Integer.parseInt(str);
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src instanceof JCheckBox) {

			JCheckBox jc = (JCheckBox) src;
			String str = jc.getName();

			int n = Integer.parseInt(str);
			boolean b = jc.isSelected();
			data1D.setDetectorForDisplay(n, b);

		} else if (src instanceof JButton) {
			JButton jb = (JButton) src;
			String str = jb.getName();

			int nm = Integer.parseInt(str);
			StylePicker2 sp = new StylePicker2();
			int thickness = data1D.getSeriesThickness(nm);
			int shape = data1D.getSeriesSymbol(nm);
			int symbolSize = data1D.getSeriesSymbolSize(nm);
//			Color lineColor = data1D.getSeriesLineColor(nm);
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
	}

	public boolean between(double i, double minValueInclusive, double maxValueInclusive) {
		if (i >= minValueInclusive && i <= maxValueInclusive)
			return true;
		else
			return false;
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

	public void updateScanCenterDiff() {
		runSafe(new Runnable() {
			public void run() {
				vMarkerCenterPos = vMarkerCenter.getValue();
				setScanDiffValue(scanCenter - vMarkerCenterPos);
			}
		});
	}

	
	public void setMarkers(String whoCalled) {

		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();

		boolean checkRange = between(vMarker1.getValue(), xaxis.getMin(), xaxis.getMax());
		checkRange = checkRange & between(vMarker2.getValue(), xaxis.getMin(), xaxis.getMax());
		checkRange = checkRange & between(hMarker1.getValue(), yaxis.getMin(), yaxis.getMax());
		checkRange = checkRange & between(hMarker2.getValue(), yaxis.getMin(), yaxis.getMax());
		if (whoCalled.equals("Reset"))checkRange=!checkRange;
		if (!checkRange) {

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
	}

	public double getPrecisionedData(double d1) {
		double d = Math.floor(d1 * Math.pow(10, precision) + 0.5);
		d = d / Math.pow(10, precision);
		return d;
	}

	public double getCenter(double d1, double d2) {

		double d = (d1 + d2) / 2.0;
		// d = getPrecisionedData(d);

		return d;
	}

	public double getWidth(double d1, double d2) {

		double d = d2 - d1;
		// d = getPrecisionedData(d);
		return d;
	}

	public void setLeftMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		leftMarkerValueLabel.setText(str);
	}

	public void setRightMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		rightMarkerValueLabel.setText(str);
	}

	public void setCenterMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		centerMarkerValueLabel.setText(str);
	}

	public void setWidthMarkerValue(double d) {
		d = getPrecisionedData(d);
		String str = Double.toString(d);
		widthVerticalLabel.setText(str);
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
		scanStatusLabel.setText("Scan " + str);
	}

	public void setFileName(String str) {
		fileNameLabel.setText(str);
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

	public static void runSafe(Runnable task) {
		if (SwingUtilities.isEventDispatchThread()) {
			task.run();
		} else {
			SwingUtilities.invokeLater(task);
		}
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
		return xaxis.getMin();
	}

	public double getMaxX() {
		return xaxis.getMax();
	}

	public double getMinY() {
		return yaxis.getMin();
	}

	public double getMaxY() {
		return yaxis.getMax();
	}

	public void setAutoScale(boolean b) {
		autoScale = b;
		data1D.setAutoScale(b);
		if (b) {
			updateUserAuto();
		}
	}

	public void updateUserAuto() {
		setUserXmin(this.getMinX());
		setUserXMax(this.getMaxX());
		data1D.updateChartDisplay();

	}

	public void setUserXmin(double d) {

		xRangeMin_textField_1D.setText(String.valueOf(d));
	}

	public void setUserXMax(double d) {
		xRangeMax_textField_1D.setText(String.valueOf(d));
	}

	public void showAlert(String str) {
		JOptionPane.showMessageDialog(this,str);

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

	public void chart_mousePressed(MouseEvent e) {
		JCMarker mrk1;
		JCMarker mrk2;
		double dm1;
		double dm2;
		double dd;

		Point point = e.getPoint();
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
			data1D.updateChartDisplay();
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
			data1D.updateChartDisplay();
		}
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


	public void moveVmarker(JCMarker mrkr, double d) {
		double d1;
		double d2;
		double dCenter;
//		double dWidth;
//		double temp;

		mrkr.setValue(d);
		d1 = vMarker1.getValue();
		d2 = vMarker2.getValue();
		dCenter = getCenter(d1, d2);
		vMarkerCenter.setValue(dCenter);

		updateVMarkers();
		updateScanCenterDiff();

	}

	public void moveHmarker(JCMarker mrkr, double d) {
		double d1;
		double d2;
		double dCenter;
//		double dWidth;

		mrkr.setValue(d);
		d1 = hMarker1.getValue();
		d2 = hMarker2.getValue();
		dCenter = getCenter(d1, d2);
		hMarkerCenter.setValue(dCenter);
		updateHMarkers();

	}

	public void chart_mouseDragged(MouseEvent e) {
		Point point;
//		String str;

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
			data1D.updateChartDisplay();
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
			data1D.updateChartDisplay();
		}
	}

	public void moveMotorLeftMarker() {
		scan1PosPnPV[selectedPositioner].movePositioner(vMarker1.getValue());
	}

	public void moveMotorRightMarker() {
		scan1PosPnPV[selectedPositioner].movePositioner(vMarker2.getValue());
	}

	public void moveMotorCenterMarker() {
		scan1PosPnPV[selectedPositioner].movePositioner(vMarkerCenter.getValue());
	}
	
	public void moveXYmotors(double x, double y){
		scan1PosPnPV[selectedPositioner].movePositioner(x);
		scan2PosPnPV[selectedPositioner].movePositioner(y);
	}

	public void xCheckBox1_actionPerformed(ActionEvent e) {
		data1D.setSelectedPositioner(0);
		selectedPositioner = 0;
		scanCenter = data1D.getScanCenter();
		setMarkers("xCheckBox1_actionPerformed");
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
		setMarkers("xCheckBox2_actionPerformed");
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
		setMarkers("xCheckBox3_actionPerformed");
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
		setMarkers("xCheckBox4_actionPerformed");
		setScanCenterLabel(scanCenter);
		updateScanCenterDiff();
		updateUserAuto();
		JCheckBox jb = ((JCheckBox) (e.getSource()));
		String str = jb.getText();
		setXAxisTitle(str);
	}

	public void setXAxisTitle(String str){
		JCAxis xAxis = chart.getDataView(0).getXAxis();
		xAxis.setTitle(new JCAxisTitle(str));
	}
	public void setScan1PosPv(PositionerPnPV[] pp) {
		for (int i = 0; i < 4; i++) {
			scan1PosPnPV[i] = pp[i];
		}
	}

	public void setScan2PosPv(PositionerPnPV[] pp) {
		for (int i = 0; i < 4; i++) {
			scan2PosPnPV[i] = pp[i];
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

class MainPanel_1_xPositionerCheckBox_1_actionAdapter implements ActionListener {
	private MainPanel_1 adaptee;

	MainPanel_1_xPositionerCheckBox_1_actionAdapter(MainPanel_1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox1_actionPerformed(e);
	}
}

class MainPanel_1_xPositionerCheckBox_2_actionAdapter implements ActionListener {
	private MainPanel_1 adaptee;

	MainPanel_1_xPositionerCheckBox_2_actionAdapter(MainPanel_1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox2_actionPerformed(e);
	}
}

class MainPanel_1_xPositionerCheckBox_3_actionAdapter implements ActionListener {
	private MainPanel_1 adaptee;

	MainPanel_1_xPositionerCheckBox_3_actionAdapter(MainPanel_1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox3_actionPerformed(e);
	}
}

class MainPanel_1_xPositionerCheckBox_4_actionAdapter implements ActionListener {
	private MainPanel_1 adaptee;

	MainPanel_1_xPositionerCheckBox_4_actionAdapter(MainPanel_1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.xCheckBox4_actionPerformed(e);
	}
}

class MainPanel_1D_detPanelCheckBox_actionAdapter implements ItemListener {

	private MainPanel_1 adaptee;
	MainPanel_1D_detPanelCheckBox_actionAdapter(MainPanel_1 adaptee){
		this.adaptee = adaptee;
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object eObject = e.getSource();		
		JCheckBox dd = (JCheckBox)eObject;
		adaptee.detectorStatus1D(dd.getName(),dd.isSelected());
	}	
}
class MainPanel_1D_detPanelButton_actionAdapter implements ActionListener {

	private MainPanel_1 adaptee;
	MainPanel_1D_detPanelButton_actionAdapter(MainPanel_1 adaptee){
		this.adaptee = adaptee;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eObject = e.getSource();
		JButton jButton = (JButton)eObject;
		adaptee.detectorColorStatus1D(jButton.getName());
		
	}
}

class MainPanel_2D_detPanelCheckBox_actionAdapter implements ItemListener {

	private MainPanel_1 adaptee;
	MainPanel_2D_detPanelCheckBox_actionAdapter(MainPanel_1 adaptee){
		this.adaptee = adaptee;
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object eObject = e.getSource();		
		JCheckBox dd = (JCheckBox)eObject;
		adaptee.detectorStatus2D(dd.getName(),dd.isSelected());
	}	
}
