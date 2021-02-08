package bluediamond2;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.BorderLayout;

import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.JCMarker;

import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Saved_1D_ScanPanel extends JPanel {

	JCChart chart;
	HpFileTableModel dm1 = new HpFileTableModel();
	TableCellRenderer renderer1 = new EvenOddRenderer();

	boolean hidden = false;

	private int scanDataViewNum = 1;
	private Old_1D_Panel old_1D_Panel;

	java.util.List<String> oldList = new ArrayList<String>();
	private Map<String, OldFileList> oldFileListMap = new HashMap<String, OldFileList>();

	private JTextField xRangeMinTextBox;
	private JTextField xRangeMaxTextBox;
	private JTextField yRangeMinTextBox;
	private JTextField yRangeMaxTextBox;

	ButtonGroup autoUserGroup = new ButtonGroup();

	JTabbedPane tabbedPane_left;
	private JTable table;

	JCAxis xaxis;
	JCAxis yaxis;

	boolean vMarkersShowing = true;
	boolean vMarkerSelected = false;
	JCMarker pickedMarker;
	double pickedPoint;
	double minMarker;
	double maxMarker;

	JCMarker vMarker1;
	JCMarker vMarker2;
	JCMarker vMarkerCenter;
	double vMarker1Pos;
	double vMarker2Pos;
	double vMarkerCenterPos;
	double scanCenter;
	int precision = 4;

	JLabel lblLeftMarkerValue;
	JLabel lblCenterMarkerValue;
	JLabel lblWidthMarkerValue;
	JLabel lblRightMarkerValue;

	boolean xScaleAuto = true;
	JCheckBox userCheckBox;
	JCheckBox autoCheckBox;

	double displayXMin;
	double displayXMax;

	/**
	 * Create the panel.
	 */
	public Saved_1D_ScanPanel() {
		setBorder(
				new CompoundBorder(
						new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255),
								new Color(0, 255, 0), new Color(0, 255, 255)),
						new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0))));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JPanel markerChartPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, markerChartPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, markerChartPanel, -10, SpringLayout.EAST, this);
		add(markerChartPanel);
		SpringLayout sl_markerChartPanel = new SpringLayout();
		markerChartPanel.setLayout(sl_markerChartPanel);

		JPanel markerPanel = new JPanel();
		markerPanel.setBackground(new Color(255, 255, 204));
		sl_markerChartPanel.putConstraint(SpringLayout.SOUTH, markerPanel, -5, SpringLayout.SOUTH, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.EAST, markerPanel, 200, SpringLayout.WEST, markerChartPanel);
		markerPanel.setBorder(new CompoundBorder(
				new CompoundBorder(
						new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255),
								new Color(255, 200, 0), new Color(128, 128, 128)),
						new BevelBorder(BevelBorder.RAISED, new Color(255, 200, 0), new Color(0, 255, 255),
								new Color(0, 0, 255), new Color(0, 255, 255))),
				new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0))));
		sl_markerChartPanel.putConstraint(SpringLayout.NORTH, markerPanel, 10, SpringLayout.NORTH, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.WEST, markerPanel, 10, SpringLayout.WEST, markerChartPanel);
		markerChartPanel.add(markerPanel);

		JPanel chartPanel = new JPanel();
		sl_markerChartPanel.putConstraint(SpringLayout.NORTH, chartPanel, 10, SpringLayout.NORTH, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.WEST, chartPanel, 6, SpringLayout.EAST, markerPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.SOUTH, chartPanel, 0, SpringLayout.SOUTH, markerPanel);
		SpringLayout sl_markerPanel = new SpringLayout();
		markerPanel.setLayout(sl_markerPanel);

		JPanel leftPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, leftPanel, 10, SpringLayout.NORTH, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, leftPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, leftPanel, 50, SpringLayout.NORTH, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, leftPanel, -10, SpringLayout.EAST, markerPanel);
		markerPanel.add(leftPanel);

		JPanel rightPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, rightPanel, 6, SpringLayout.SOUTH, leftPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, rightPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, rightPanel, 46, SpringLayout.SOUTH, leftPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, rightPanel, 0, SpringLayout.EAST, leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		leftPanel.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		lblLeftMarkerValue = new JLabel("New label");
		lblLeftMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblLeftMarkerValue, BorderLayout.CENTER);

		JPanel panel_8 = new JPanel();
		leftPanel.add(panel_8, BorderLayout.WEST);
		panel_8.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_5 = new JLabel("LEFT");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setUI(new VerticalLabelUI(false));

		panel_8.add(lblNewLabel_5, BorderLayout.CENTER);
		markerPanel.add(rightPanel);

		JPanel centerPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, centerPanel, 6, SpringLayout.SOUTH, rightPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, centerPanel, 46, SpringLayout.SOUTH, rightPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, centerPanel, 0, SpringLayout.EAST, rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		rightPanel.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));

		lblRightMarkerValue = new JLabel("New label");
		lblRightMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblRightMarkerValue, BorderLayout.CENTER);

		JPanel panel_10 = new JPanel();
		rightPanel.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_4 = new JLabel("RIGHT");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setUI(new VerticalLabelUI(false));

		panel_10.add(lblNewLabel_4, BorderLayout.CENTER);
		sl_markerPanel.putConstraint(SpringLayout.WEST, centerPanel, 10, SpringLayout.WEST, markerPanel);
		markerPanel.add(centerPanel);

		JPanel widthPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, widthPanel, 6, SpringLayout.SOUTH, centerPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, widthPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, widthPanel, 46, SpringLayout.SOUTH, centerPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, widthPanel, 0, SpringLayout.EAST, centerPanel);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel_11 = new JPanel();
		centerPanel.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new BorderLayout(0, 0));

		lblCenterMarkerValue = new JLabel("New label");
		lblCenterMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(lblCenterMarkerValue, BorderLayout.CENTER);

		JPanel panel_12 = new JPanel();
		centerPanel.add(panel_12, BorderLayout.WEST);
		panel_12.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_3 = new JLabel("CENTER");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setUI(new VerticalLabelUI(false));

		panel_12.add(lblNewLabel_3, BorderLayout.CENTER);
		markerPanel.add(widthPanel);

		JPanel panel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, widthPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, panel, 135, SpringLayout.SOUTH, widthPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, widthPanel);
		widthPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel_13 = new JPanel();
		widthPanel.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));

		lblWidthMarkerValue = new JLabel("New label");
		lblWidthMarkerValue.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblWidthMarkerValue, BorderLayout.CENTER);

		JPanel panel_14 = new JPanel();
		widthPanel.add(panel_14, BorderLayout.WEST);
		panel_14.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_2 = new JLabel("WIDTH");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setUI(new VerticalLabelUI(false));

		panel_14.add(lblNewLabel_2, BorderLayout.CENTER);
		markerPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(0, 255, 255));
		tabbedPane.setBorder(new CompoundBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255),
						new Color(255, 200, 0), new Color(255, 255, 0)),
				new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(0, 255, 0),
						new Color(0, 255, 255))));
		panel_1.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(175, 238, 238));
		tabbedPane.addTab("X-Range", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel = new JLabel("Min");
		panel_3.add(lblNewLabel, "cell 0 0,alignx trailing,growy");

		xRangeMinTextBox = new JTextField();
		xRangeMinTextBox.setBackground(UIManager.getColor("TextField.background"));
		panel_3.add(xRangeMinTextBox, "cell 1 0,grow");
		xRangeMinTextBox.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Max");
		panel_3.add(lblNewLabel_1, "cell 0 1,alignx trailing,growy");

		xRangeMaxTextBox = new JTextField();
		panel_3.add(xRangeMaxTextBox, "cell 1 1,grow");
		xRangeMaxTextBox.setColumns(10);

		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Y-Range", null, panel_6, null);
		panel_6.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel_6 = new JLabel("Min");
		panel_6.add(lblNewLabel_6, "cell 0 0,alignx trailing");

		yRangeMinTextBox = new JTextField();
		panel_6.add(yRangeMinTextBox, "cell 1 0,growx");
		yRangeMinTextBox.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Max");
		panel_6.add(lblNewLabel_7, "cell 0 1,alignx trailing");

		yRangeMaxTextBox = new JTextField();
		panel_6.add(yRangeMaxTextBox, "cell 1 1,growx");
		yRangeMaxTextBox.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 205), new Color(0, 0, 205),
				new Color(119, 136, 153), new Color(112, 128, 144)));
		panel_2.setBackground(new Color(95, 158, 160));
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		userCheckBox = new JCheckBox("User");
		userCheckBox.setEnabled(false);
		userCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setXScaleAuto(!userCheckBox.isSelected());
			}
		});
		userCheckBox.setBackground(new Color(95, 158, 160));
		panel_2.add(userCheckBox, BorderLayout.WEST);

		autoCheckBox = new JCheckBox("Auto");
		autoCheckBox.setEnabled(false);
		autoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setXScaleAuto(autoCheckBox.isSelected());
			}
		});
		autoCheckBox.setBackground(new Color(95, 158, 160));
		autoCheckBox.setSelected(true);
		panel_2.add(autoCheckBox, BorderLayout.EAST);

		autoUserGroup.add(autoCheckBox);
		autoUserGroup.add(userCheckBox);

		sl_markerChartPanel.putConstraint(SpringLayout.EAST, chartPanel, -10, SpringLayout.EAST, markerChartPanel);
		markerChartPanel.add(chartPanel);
		chartPanel.setLayout(new BorderLayout(0, 0));

		// chart = new JCChart();
		chart = new JCChart(JCChart.PLOT);
		// chart = new ZoomXChart();
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

		chartPanel.add(chart, BorderLayout.CENTER);

		vMarker1 = new JCMarker();
		vMarker1.setAssociatedWithYAxis(false);

		vMarker2 = new JCMarker();
		vMarker2.setAssociatedWithYAxis(false);

		vMarkerCenter = new JCMarker();
		vMarkerCenter.setAssociatedWithYAxis(false);

		JPanel detectorPositionerTopPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, detectorPositionerTopPanel, 365, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, detectorPositionerTopPanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, detectorPositionerTopPanel, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, detectorPositionerTopPanel, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.WEST, markerChartPanel, 0, SpringLayout.WEST, detectorPositionerTopPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, markerChartPanel, -5, SpringLayout.NORTH, detectorPositionerTopPanel);
		add(detectorPositionerTopPanel);
		SpringLayout sl_detectorPositionerTopPanel = new SpringLayout();
		detectorPositionerTopPanel.setLayout(sl_detectorPositionerTopPanel);

		JPanel savedFilepanel = new JPanel();
		savedFilepanel
				.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.YELLOW));
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.NORTH, savedFilepanel, 5, SpringLayout.NORTH,
				detectorPositionerTopPanel);
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.WEST, savedFilepanel, 5, SpringLayout.WEST,
				detectorPositionerTopPanel);
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.SOUTH, savedFilepanel, -5, SpringLayout.SOUTH,
				detectorPositionerTopPanel);
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.EAST, savedFilepanel, 200, SpringLayout.WEST,
				detectorPositionerTopPanel);
		detectorPositionerTopPanel.add(savedFilepanel);

		JPanel posDetPanel = new JPanel();
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.EAST, posDetPanel, 0, SpringLayout.EAST,
				detectorPositionerTopPanel);
		posDetPanel.setBorder(new CompoundBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 255),
						new Color(0, 255, 255)),
				new CompoundBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0)),
						new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 0), new Color(255, 200, 0),
								new Color(0, 255, 0), new Color(0, 255, 255)))));
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.NORTH, posDetPanel, 5, SpringLayout.NORTH,
				detectorPositionerTopPanel);
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.WEST, posDetPanel, 6, SpringLayout.EAST,
				savedFilepanel);
		sl_detectorPositionerTopPanel.putConstraint(SpringLayout.SOUTH, posDetPanel, 0, SpringLayout.SOUTH,
				savedFilepanel);
		savedFilepanel.setLayout(new BorderLayout(0, 0));

		JPanel savedPanelLabel = new JPanel();
		savedPanelLabel.setBorder(new LineBorder(Color.ORANGE, 2));
		savedPanelLabel.setBackground(new Color(153, 204, 153));
		savedFilepanel.add(savedPanelLabel, BorderLayout.NORTH);

		JLabel lblNewLabel_8 = new JLabel("Saved .mda Files");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		savedPanelLabel.add(lblNewLabel_8);

		JPanel savedFilesTablePanel = new JPanel();
		savedFilesTablePanel
				.setBorder(new BevelBorder(BevelBorder.RAISED, Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.ORANGE));
		savedFilepanel.add(savedFilesTablePanel, BorderLayout.CENTER);
		savedFilesTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(102, 51, 51), 2, true));
		savedFilesTablePanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable(dm1) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 102, 0), new Color(204, 204, 0),
				new Color(204, 255, 153), new Color(204, 255, 153)));
		table.setBackground(new Color(255, 255, 204));
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setName("Saved File");
		dm1.addTableModelListener(new HPFileTableModelListener(table));
		setPosHeaders(table);
		table.getColumn("Select").setCellRenderer(renderer1);
		table.setDefaultRenderer(Object.class, renderer1);
		scrollPane.setViewportView(table);
		detectorPositionerTopPanel.add(posDetPanel);
		posDetPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane_left = new JTabbedPane(JTabbedPane.TOP);
		posDetPanel.add(tabbedPane_left, BorderLayout.CENTER);
	}

	public JCChart getSaved_1D_Chart() {
		return chart;
	}

	public void setXScaleAuto(boolean b) {
		xScaleAuto = b;

		int selectedPositioner;
		double tempXMin;
		double tempXMax;
		ChartDataView tempDataView;
		OldData1D tempOldData1D;
		String tempFileNameString;
		OldFileList tempOldFileList;

		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;

		/*
		 * Loop through all dataViews. They are available through OldFileListMap
		 * 
		 */

		for (Map.Entry<String, OldFileList> entry : oldFileListMap.entrySet()) {
			tempFileNameString = entry.getKey();
			tempOldFileList = entry.getValue();
			tempDataView = tempOldFileList.getChartDataView();

			if (tempDataView.isVisible()) {
				tempOldData1D = tempOldFileList.getOldData1D();
				selectedPositioner = tempOldData1D.getSelectedPositioner();
				tempXMin = tempOldData1D.getSelectedPositionerXMin();
				if (tempXMin < xMin)
					xMin = tempXMin;

				tempXMax = tempOldData1D.getSelectedPositionerXMax();
				if (tempXMax > xMax)
					xMax = tempXMax;

				tempDataView.getXAxis().setMin(xMin);
				tempDataView.getXAxis().setMax(xMax);

			}
		}

		showVMarkers();
		chart.update();
	}

	public void setDisplayXMin(double d) {
		displayXMin = d;
	}

	public void setDisplayXMax(double d) {
		displayXMax = d;
	}

	public double getDisplayXMin() {

		return displayXMin;
	}

	public double getDisplayXMax() {
		return displayXMax;
	}

	public boolean getXScaleAuto() {
		return xScaleAuto;
	}

	public boolean isXScaleAuto() {
		return ((xScaleAuto) ? true : false);
	}

	public void setFile(File inFile) {
		String inFileName = inFile.getName();
		ReadSavedMdaData rmd = new ReadSavedMdaData();

		chart.addDataView(scanDataViewNum);

		OldFileList oldFileList = new OldFileList(inFileName);
		OldData1D oldData1D = new OldData1D(chart);
		oldData1D.setFileName(inFileName);
		oldFileList.addOldData1D(oldData1D);

		oldData1D.setDataViewNumber(scanDataViewNum);

		chart.getDataView(scanDataViewNum).setDataSource(oldData1D);
		oldFileList.addChartDataView(chart.getDataView(scanDataViewNum));

		old_1D_Panel = new Old_1D_Panel();
		old_1D_Panel.setSaved_1D_ScanPanel(this);
		old_1D_Panel.setChart(chart);
		old_1D_Panel.setDataViewNumber(scanDataViewNum);
		oldList.add(inFileName);
		oldFileList.addOld_1D_Panel(old_1D_Panel);
		oldFileListMap.put(inFileName, oldFileList);

		populateFileTable(inFileName);

		tabbedPane_left.addTab(inFileName, old_1D_Panel);

		rmd.setFile(inFile);
		rmd.readMdaData();

		int numCurrentXPoint = rmd.getCurrentPoint(0);
		int numXPoints = rmd.getNumPoints(0);

		oldData1D.setNumPoints(numXPoints);
		oldData1D.setNumberOfCurrentPoints(numCurrentXPoint);

		int numXPos = rmd.getNumPos(0);
		int numDets = rmd.getNumDets(0);

		oldData1D.setNumPositioners(numXPos);
		oldData1D.setNumberOfDetectors(numDets);

		String[] posXName = new String[numXPos];
		String[] posXDesc = new String[numXPos];
		String[] detName = new String[numDets];
		String[] detDesc = new String[numDets];

		posXName = rmd.getPosName(0);
		posXDesc = rmd.getPosDesc(0);
		detName = rmd.getDetName(0);
		detDesc = rmd.getDetDesc(0);

		oldData1D.setPosName(posXName);
		oldData1D.setPosDesc(posXDesc);
		oldData1D.setDetName(detName);
		oldData1D.setDetDesc(detDesc);

		double[][] xVal;
		double[][] yVal;

		xVal = new double[numXPos][numCurrentXPoint];
		yVal = new double[numDets][numCurrentXPoint];

		xVal = rmd.getPosData(0);
		yVal = rmd.getDetsData(0);
		oldData1D.initArrays();

		oldData1D.setXVals(xVal);
		oldData1D.setYVals(yVal);

		oldData1D.calculatePosDetMinMax();
		double[] posXMin = oldData1D.getPosXMinArray();
		double[] posXMax = oldData1D.getPosXMaxArray();
		double[] detMin = oldData1D.getDetMinArray();
		double[] detMax = oldData1D.getDetMaxArray();

		populatePosPanel(numXPos, posXName, posXDesc, posXMin, posXMax);
		populateDetPanel(numDets, detName, detDesc, detMin, detMax);

		oldData1D.updateDisplay();

		java.util.List list = oldData1D.getSelectedChartDetectors();
		old_1D_Panel.setSelectedDetectorsForDisplay(list);
		autoCheckBox.setEnabled(true);
		userCheckBox.setEnabled(true);
		setXScaleAuto(true);

		scanDataViewNum++;
	}

	public boolean isListed(String str) {
		return oldList.contains(str);
	}

	public void dropAllTabs() {

		tabbedPane_left.getTabCount();
		tabbedPane_left.removeAll();
	}

	public void addVisibleTabs() {

		String tempFileNameString;
		OldFileList tempOldFileList;
		boolean tabVisible;
		Old_1D_Panel temp_Old_1D_Panel;

		for (Map.Entry<String, OldFileList> entry : oldFileListMap.entrySet()) {
			tempFileNameString = entry.getKey();
			tempOldFileList = entry.getValue();
			tabVisible = tempOldFileList.getTabVisible();

			if (tabVisible) {
				tempFileNameString = tempOldFileList.getFileName();
				temp_Old_1D_Panel = tempOldFileList.getOld_1D_Panel();
				tabbedPane_left.add(tempFileNameString, temp_Old_1D_Panel);

			}
		}

	}

	public void populateFileTable(String str) {
		HpFileTableModel mp = (HpFileTableModel) table.getModel();
		JRadioButton rb1 = new JRadioButton();
		table.getColumn("Select").setCellRenderer(new RadioButtonRenderer());
		table.getColumn("Select").setCellEditor(new RadioButtonEditor(new JCheckBox()));

		mp.addRow(new Object[] { str, rb1 });

		rb1.setHorizontalAlignment(SwingConstants.CENTER);
		rb1.setSelected(true);


	}

	public void populateDetPanel(int numDets, String[] detName, String[] detDesc, double[] detMin, double[] detMax) {
		for (int i = 0; i < numDets; i++) {
			old_1D_Panel.addNewDetData(detName[i], detDesc[i], detMin[i], detMax[i]);
		}
	}

	public void populatePosPanel(int numPos, String[] posXName, String[] posXDesc, double[] posXMin, double[] posXMax) {
		for (int i = 0; i < numPos; i++) {
			old_1D_Panel.addNewPosData(posXName[i], posXDesc[i], posXMin[i], posXMax[i]);
		}

	}

	public void setPosHeaders(JTable tb) {

		HpFileTableModel mp = (HpFileTableModel) tb.getModel();

		mp.addColumn("File Name");
		mp.addColumn("Select");
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) tb.getColumnModel();
		int wo = colModel.getTotalColumnWidth();

		TableColumn col0 = colModel.getColumn(0);
		col0.setPreferredWidth((wo * 75) / 100);

		TableColumn col1 = colModel.getColumn(1);
		col1.setPreferredWidth((wo * 25) / 100);

	}

	class HpFileTableModel extends DefaultTableModel {

	}

	public class HPFileTableModelListener implements TableModelListener {
		JTable table;
		HpFileTableModel mp;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		HPFileTableModelListener(JTable table) {
			this.table = table;
			mp = (HpFileTableModel) table.getModel();
		}

		public void tableChanged(TableModelEvent e) {
			int firstRow = e.getFirstRow();
			int lastRow = e.getLastRow();
			int mColIndex = e.getColumn();

			switch (e.getType()) {
			case TableModelEvent.INSERT:

				// The inserted rows are in the range [firstRow, lastRow]
				for (int r = firstRow; r <= lastRow; r++) {
					// Row r was inserted
				}
				break;
			case TableModelEvent.UPDATE:
				if (firstRow == TableModelEvent.HEADER_ROW) {
					if (mColIndex == TableModelEvent.ALL_COLUMNS) {
						// A column was added
					} else {
						// Column mColIndex in header changed
					}
				} else {
					// The rows in the range [firstRow, lastRow] changed
					for (int r = firstRow; r <= lastRow; r++) {
						// Row r was changed

						if (mColIndex == TableModelEvent.ALL_COLUMNS) {
							// All columns in the range of rows have changed
						} else {
							// Column mColIndex changed
							if (mColIndex == 1) {
								Object obj = mp.getValueAt(firstRow, mColIndex);
								Object fileNameObject = mp.getValueAt(firstRow, 0);
								String fileName = fileNameObject.toString();
								JRadioButton rb = (JRadioButton) obj;
								OldFileList oldFileList1 = oldFileListMap.get(fileName);
								ChartDataView chartDataView1 = oldFileList1.getChartDataView();

								if (rb.isSelected()) {
									oldFileList1.setTabVisible(true);
									chartDataView1.setVisible(true);
									chart.update();

								} else {
									oldFileList1.setTabVisible(false);
									chartDataView1.setVisible(false);
									chart.update();
								}

								dropAllTabs();
								addVisibleTabs();
								setXScaleAuto(true);
								// Object dObj = chart.getDataView(dataViewNumber).getDataSource();
								if (table.getName() == "Saved File") {

								}
							}
						}
					}
				}
				break;
			case TableModelEvent.DELETE:

				// The rows in the range [firstRow, lastRow] changed
				for (int r = firstRow; r <= lastRow; r++) {
					// Row r was deleted
				}
				break;
			}
		}
	}

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);
			((JLabel) renderer).setHorizontalAlignment(SwingConstants.CENTER);

			// ((JLabel) renderer).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			Color foreground, background;
			if (hidden) {
				foreground = new Color(0xDC, 0xDC, 0xDC);
				background = new Color(0x69, 0x69, 0x69);
			} else {
				background = new Color(0xFF, 0xFA, 0xCD);
				foreground = new Color(0x00, 0x00, 0xCD);
			}
			renderer.setForeground(foreground);
			renderer.setBackground(background);

			setAlignmentX(DefaultTableCellRenderer.CENTER);
			return renderer;
		}
	}

	class RadioButtonRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			/*
			 * if (value == null) { return null; }
			 */

			return (Component) value;
		}
	}

	class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null) {
				return null;
			}

			button = (JRadioButton) value;

			button.addItemListener(this);
			return (Component) value;

		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
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

			}
		});
	}

	public void showVMarkers() {
		setMarkers();
		addVMarkers();
		updateVMarkers();

	}

	public void addVMarkers() {
		chart.getDataView(0).addMarker(vMarker1);
		chart.getDataView(0).addMarker(vMarker2);
		chart.getDataView(0).addMarker(vMarkerCenter);
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

	public double getWidth(double d1, double d2) {

		double d = d2 - d1;
		return d;
	}

	public double getPrecisionedData(double d1) {
		double d = Math.floor(d1 * Math.pow(10, precision) + 0.5);
		d = d / Math.pow(10, precision);
		return d;
	}

	public double getMinX() {
		return xaxis.getMin();
	}

	public double getMaxX() {
		return xaxis.getMax();
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

		}
		chart.update();
	}

	public void chart_mouseReleased(MouseEvent e) {
		if (vMarkerSelected) {
			chart.setBatched(true);
			com.klg.jclass.chart.JCLineStyle ll = pickedMarker.getLineStyle();
			ll.setColor(Color.BLACK);
			pickedMarker = null;
			chart.setBatched(false);
			chart.update();
		}
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

		}
		chart.update();
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
	}

	public double getCenter(double d1, double d2) {

		double d = (d1 + d2) / 2.0;
		// d = getPrecisionedData(d);

		return d;
	}
}

class TabManager {
	JTabbedPane tabbedPane;
	String[] titles;
	JComponent[] components;

	public TabManager(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void dropAllTabs() {

	}

	public void setTabs(String[] titles) {
		removeTabs(titles);
		addTabs(titles);
	}

	private void addTabs(String[] titles) {
		String[] tabTitles = getTabTitles();
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			if (!contains(tabTitles, title)) {
				insert(title);
			}
		}
	}

	private void insert(String title) {
		String[] tabTitles = getTabTitles();
		int index = getIndex(tabTitles, title);
		JComponent component = components[getIndex(title)];
		if (index == -1) {
			tabbedPane.addTab(title, component);
		} else {
			tabbedPane.insertTab(title, null, component, null, index);
		}
	}

	private int getIndex(String[] array, String insert) {
		int insertIndex = getIndex(insert);
		for (int i = 0; i < array.length; i++) {
			int index = getIndex(array[i]);
			if (insertIndex < index) {
				;
				return i;
			}
		}
		return -1;
	}

	private int getIndex(String str) {
		for (int i = 0; i < titles.length; i++) {
			if (titles[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}

	private void removeTabs(String[] titles) {
		String[] tabTitles = getTabTitles();
		for (int i = tabTitles.length - 1; i >= 0; i--) {
			String title = tabTitles[i];
			if (!contains(titles, title)) {
				tabbedPane.removeTabAt(i);
			}
		}
	}

	private boolean contains(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element)) {
				return true;
			}
		}
		return false;
	}

	private String[] getTabTitles() {
		int tabCount = tabbedPane.getTabCount();
		String[] titles = new String[tabCount];
		for (int i = 0; i < tabCount; i++) {
			titles[i] = tabbedPane.getTitleAt(i);
		}
		return titles;
	}
}
