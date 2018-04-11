package bluediamond2;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.BorderLayout;
import com.klg.jclass.chart.JCChart;
import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Saved_1D_ScanPanel extends JPanel {

	JCChart chart;
	Map<Integer,JTabbedPane> oldPanelSelect = new HashMap<Integer, JTabbedPane>();
	JTabbedPane tabbedPane_1;
	JTabbedPane tabbedPane_2;
	
	java.util.List<String> oldList = new ArrayList<String>();
	private int scanDataViewNum = 0;
	private Old_1D_Panel old_1D_Panel;
	private OldData1D oldData1D;
	
	private JTextField xRangeMinTextBox;
	private JTextField xRangeMaxTextBox;
	ButtonGroup autoUserGroup = new ButtonGroup();
	private JTextField yRangeMinTextBox;
	private JTextField yRangeMaxTextBox;

	String[] posName;
	String[] posDesc;
	String[] detName;
	String[] detDesc;
	double[] posMin;
	double[] posMax;
	double[] detMin;
	double[] detMax;
	
	/**
	 * Create the panel.
	 */
	public Saved_1D_ScanPanel() {
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(0, 255, 0), new Color(0, 255, 255)), new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0))));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel markerChartPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, markerChartPanel, 14, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, markerChartPanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, markerChartPanel, 845, SpringLayout.WEST, this);
		add(markerChartPanel);
		
		JPanel detectorPositionerTopPanel = new JPanel();
		detectorPositionerTopPanel.setBorder(new CompoundBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0)), new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(0, 0, 255), new Color(128, 128, 128))));
		springLayout.putConstraint(SpringLayout.NORTH, detectorPositionerTopPanel, 379, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, detectorPositionerTopPanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, detectorPositionerTopPanel, -9, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, detectorPositionerTopPanel, -3, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, markerChartPanel, -6, SpringLayout.NORTH, detectorPositionerTopPanel);
		SpringLayout sl_markerChartPanel = new SpringLayout();
		markerChartPanel.setLayout(sl_markerChartPanel);
		
		JPanel markerPanel = new JPanel();
		markerPanel.setBorder(new CompoundBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(255, 200, 0), new Color(128, 128, 128)), new BevelBorder(BevelBorder.RAISED, new Color(255, 200, 0), new Color(0, 255, 255), new Color(0, 0, 255), new Color(0, 255, 255))), new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 200, 0))));
		sl_markerChartPanel.putConstraint(SpringLayout.NORTH, markerPanel, 10, SpringLayout.NORTH, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.WEST, markerPanel, 10, SpringLayout.WEST, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.SOUTH, markerPanel, 349, SpringLayout.NORTH, markerChartPanel);
		sl_markerChartPanel.putConstraint(SpringLayout.EAST, markerPanel, 180, SpringLayout.WEST, markerChartPanel);
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
		sl_markerPanel.putConstraint(SpringLayout.EAST, leftPanel, 160, SpringLayout.WEST, markerPanel);
		markerPanel.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, rightPanel, 6, SpringLayout.SOUTH, leftPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, rightPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, rightPanel, 46, SpringLayout.SOUTH, leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		leftPanel.add(panel_7, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		leftPanel.add(panel_8, BorderLayout.WEST);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("LEFT");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setUI(new VerticalLabelUI(false));

		panel_8.add(lblNewLabel_5, BorderLayout.CENTER);
		sl_markerPanel.putConstraint(SpringLayout.EAST, rightPanel, 160, SpringLayout.WEST, markerPanel);
		markerPanel.add(rightPanel);
		
		JPanel centerPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, centerPanel, 6, SpringLayout.SOUTH, rightPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, centerPanel, 46, SpringLayout.SOUTH, rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		rightPanel.add(panel_9, BorderLayout.CENTER);
		
		JPanel panel_10 = new JPanel();
		rightPanel.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("RIGHT");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setUI(new VerticalLabelUI(false));

		panel_10.add(lblNewLabel_4, BorderLayout.CENTER);
		sl_markerPanel.putConstraint(SpringLayout.WEST, centerPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, centerPanel, 160, SpringLayout.WEST, markerPanel);
		markerPanel.add(centerPanel);
		
		JPanel widthPanel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, widthPanel, 6, SpringLayout.SOUTH, centerPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, widthPanel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, widthPanel, 46, SpringLayout.SOUTH, centerPanel);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_11 = new JPanel();
		centerPanel.add(panel_11, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		centerPanel.add(panel_12, BorderLayout.WEST);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("CENTER");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setUI(new VerticalLabelUI(false));

		panel_12.add(lblNewLabel_3, BorderLayout.CENTER);
		sl_markerPanel.putConstraint(SpringLayout.EAST, widthPanel, -10, SpringLayout.EAST, markerPanel);
		markerPanel.add(widthPanel);
		
		JPanel panel = new JPanel();
		sl_markerPanel.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, widthPanel);
		sl_markerPanel.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, markerPanel);
		sl_markerPanel.putConstraint(SpringLayout.SOUTH, panel, 135, SpringLayout.SOUTH, widthPanel);
		sl_markerPanel.putConstraint(SpringLayout.EAST, panel, 155, SpringLayout.WEST, markerPanel);
		widthPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		widthPanel.add(panel_13, BorderLayout.CENTER);
		
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
		tabbedPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(255, 200, 0), new Color(255, 255, 0)), new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 255), new Color(255, 200, 0), new Color(255, 255, 0))));
		panel_1.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("X-Range", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[][grow]", "[][]"));
		
		JLabel lblNewLabel = new JLabel("Min");
		panel_3.add(lblNewLabel, "cell 0 0,alignx trailing");
		
		xRangeMinTextBox = new JTextField();
		panel_3.add(xRangeMinTextBox, "cell 1 0,growx");
		xRangeMinTextBox.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Max");
		panel_3.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		xRangeMaxTextBox = new JTextField();
		panel_3.add(xRangeMaxTextBox, "cell 1 1,growx");
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
		panel.add(panel_2, BorderLayout.SOUTH);
		
		JCheckBox userCheckBox = new JCheckBox("User");
		panel_2.add(userCheckBox);
		
		JCheckBox autoCheckBox = new JCheckBox("Auto");
		autoCheckBox.setSelected(true);
		panel_2.add(autoCheckBox);
		
		autoUserGroup.add(autoCheckBox);
		autoUserGroup.add(userCheckBox);

		sl_markerChartPanel.putConstraint(SpringLayout.EAST, chartPanel, -10, SpringLayout.EAST, markerChartPanel);
		markerChartPanel.add(chartPanel);
		chartPanel.setLayout(new BorderLayout(0, 0));
		
		chart = new JCChart();
		chartPanel.add(chart, BorderLayout.CENTER);
		add(detectorPositionerTopPanel);
		detectorPositionerTopPanel.setLayout(new GridLayout(0, 2, 0, 0));
		oldData1D = new OldData1D(chart);
		JPanel panel_4 = new JPanel();
		detectorPositionerTopPanel.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		detectorPositionerTopPanel.add(panel_5);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel_4.add(tabbedPane_1, BorderLayout.NORTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		panel_5.add(tabbedPane_2, BorderLayout.NORTH);
		oldPanelSelect.put(0, tabbedPane_1);
		oldPanelSelect.put(1, tabbedPane_2);

	}
	
	public JCChart getSaved_1D_Chart() {
		return chart;
	}
	private JTabbedPane getSelectTabPane(int n) {
		int m = (n+1)%2;
		return oldPanelSelect.get(m);
	}
	
	public boolean isListed(String str) {
		return oldList.contains(str);
	}
	   public void setPosName(String[] s){
		      int n = s.length;
		      posName = new String[n];
		      for (int i =0; i < n ; i++){
		         posName[i] = s[i];
		      }
		   }

		   public void setPosDesc(String[] s){
		      int n = s.length;
		      posDesc = new String[n];
		      for (int i =0; i < n ; i++){
		         posDesc[i] = s[i];
		      }
		   }

		   public void setDetName(String[] s){
		      int n = s.length;
		      detName = new String[n];
		      for (int i =0; i < n ; i++){
		         detName[i] = s[i];
		      }
		   }

		   public void setDetDesc(String[] s){
		      int n = s.length;
		      detDesc = new String[n];
		      for (int i =0; i < n ; i++){
		         detDesc[i] = s[i];
		      }
		   }

		   public void setPosMin(double[] d){
		      int n= d.length;
		      posMin = new double[n];

		      for (int i=0; i < n; i++){
		         posMin[i] = d[i];
		      }
		   }

		   public void setPosMax(double[] d){
		      int n= d.length;
		      posMax = new double[n];
		      for (int i=0; i < n; i++){
		         posMax[i] = d[i];
		      }
		   }

		   public void setDetMin(double[] d){
		      int n= d.length;
		      detMin = new double[n];
		      for (int i=0; i < n; i++){
		         detMin[i] = d[i];
		      }
		   }

		   public void setDetMax(double[] d){
		      int n= d.length;
		      detMax = new double[n];
		      for (int i=0; i < n; i++){
		         detMax[i] = d[i];
		      }
		   }



	   public void addNewFile(File fl, int n) {
		     
		      scanDataViewNum = n;
		      String str = fl.getName();
		      old_1D_Panel = new Old_1D_Panel();
		      old_1D_Panel.setChart(chart);
		      old_1D_Panel.setDataViewNumber(n);
		      JTabbedPane oldTab = getSelectTabPane(n);
		      oldTab.addTab(str, old_1D_Panel);
		      oldList.add(str);
		     
		      java.util.List list = oldData1D.getSelectedChartDetectors();
		      old_1D_Panel.setSelectedDetectorsForDisplay(list);

		    }

}
