package bluediamond2;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.vecmath.Point3d;

import java.awt.Color;
import java.awt.BorderLayout;

import com.klg.jclass.chart3d.Chart3dDataView;
import com.klg.jclass.chart3d.JCChart3dArea;
import com.klg.jclass.chart3d.JCData3dGridIndex;
import com.klg.jclass.chart3d.JCData3dIndex;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;
import com.klg.jclass.chart3d.j2d.beans.Chart3dJava2d;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class Active_2D_ScanPanel extends JPanel {

	CountDownConnection countDownConnection = CountDownConnection.getInstance();

    Chart3dJava2d chart3dJava2d;
    Chart3dDataView dataView3D = null;

	protected Data2D data2D;
    JComboBox<String> detComboBox_2D;
    boolean pickedPoint3D = false;
    double pickedX;
    double pickedY;
    private final double HOLE_VALUE = Double.MAX_VALUE;

    ButtonGroup xPos_2DGroup = new ButtonGroup();
    ButtonGroup yPos_2DGroup = new ButtonGroup();
	private Map<Integer, JCheckBox> posX2DMap = new HashMap<>();
	private Map<Integer, JCheckBox> posYMap = new HashMap<>();
	
	JCheckBox chckbx_2D_Xpositioner1;
	JCheckBox chckbx_2D_Xpositioner2;
	JCheckBox chckbx_2D_Xpositioner3;
	JCheckBox chckbx_2D_Xpositioner4;
	
	JCheckBox chckbx_2D_Ypositioner1;
	JCheckBox chckbx_2D_Ypositioner2;
	JCheckBox chckbx_2D_Ypositioner3;
	JCheckBox chckbx_2D_Ypositioner4;
	int selectedPositioner_X = 0;
	int selectedPositioner_Y = 0;

	JLabel x_Pos_2D_value;
	JLabel y_Pos_2D_value;
	JLabel int_2D_value;
	
	private PositionerPnPV[] scan2PosPnPV = new PositionerPnPV[4];


	/**
	 * Create the panel.
	 */
	public Active_2D_ScanPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 255, 0), new Color(255, 200, 0)));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel leftPanel_2D = new JPanel();
		leftPanel_2D.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		springLayout.putConstraint(SpringLayout.NORTH, leftPanel_2D, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, leftPanel_2D, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, leftPanel_2D, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, leftPanel_2D, 180, SpringLayout.WEST, this);
		add(leftPanel_2D);
		
		JPanel panel_2D_plot = new JPanel();
		panel_2D_plot.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GREEN));
		springLayout.putConstraint(SpringLayout.WEST, panel_2D_plot, 5, SpringLayout.EAST, leftPanel_2D);
		SpringLayout sl_leftPanel_2D = new SpringLayout();
		leftPanel_2D.setLayout(sl_leftPanel_2D);
		
		JPanel surfaceCountourPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, surfaceCountourPanel, 10, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, surfaceCountourPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, surfaceCountourPanel, 59, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, surfaceCountourPanel, 160, SpringLayout.WEST, leftPanel_2D);
		leftPanel_2D.add(surfaceCountourPanel);
		
		JPanel xyzPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, xyzPanel, 6, SpringLayout.SOUTH, surfaceCountourPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, xyzPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, xyzPanel, 129, SpringLayout.SOUTH, surfaceCountourPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, xyzPanel, 160, SpringLayout.WEST, leftPanel_2D);
		leftPanel_2D.add(xyzPanel);
		
		JPanel positioner_2D_Panel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, positioner_2D_Panel, 6, SpringLayout.SOUTH, xyzPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, positioner_2D_Panel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, positioner_2D_Panel, 143, SpringLayout.SOUTH, xyzPanel);
		xyzPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		xyzPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[][]", "[][][][]"));
		
		JLabel x_AxisPosLabel2D = new JLabel("X=");
		panel.add(x_AxisPosLabel2D, "cell 0 0");
		
		x_Pos_2D_value = new JLabel("New label");
		panel.add(x_Pos_2D_value, "cell 1 0");
		
		JLabel y_AxisPosLabel2D = new JLabel("Y=");
		panel.add(y_AxisPosLabel2D, "cell 0 1");
		
		y_Pos_2D_value = new JLabel("New label");
		panel.add(y_Pos_2D_value, "cell 1 1");
		
		JLabel intensityLabel_2D = new JLabel("Int=");
		panel.add(intensityLabel_2D, "cell 0 2");
		
		int_2D_value = new JLabel("New label");
		panel.add(int_2D_value, "cell 1 2");
		
		JPanel panel_1 = new JPanel();
		xyzPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton2D = new JButton("Move");
		panel_1.add(btnNewButton2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, positioner_2D_Panel, 0, SpringLayout.EAST, surfaceCountourPanel);
		leftPanel_2D.add(positioner_2D_Panel);
		
		JPanel detectorLabelPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorLabelPanel, 6, SpringLayout.SOUTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorLabelPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorLabelPanel, 27, SpringLayout.SOUTH, positioner_2D_Panel);
		positioner_2D_Panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		positioner_2D_Panel.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel xAxisPositionersPanel_2D = new JPanel();
		tabbedPane.addTab("X-Axis", null, xAxisPositionersPanel_2D, null);
		xAxisPositionersPanel_2D.setLayout(new GridLayout(4, 0, 0, 0));
		
		chckbx_2D_Xpositioner1 = new JCheckBox("New check box");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner1);
		chckbx_2D_Xpositioner1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 0;
				data2D.setSelectedPositioner_X(0);
			}
		});


		chckbx_2D_Xpositioner2 = new JCheckBox("New check box");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner2);
		chckbx_2D_Xpositioner2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 1;
				data2D.setSelectedPositioner_X(1);
			}
		});

		chckbx_2D_Xpositioner3 = new JCheckBox("New check box");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner3);		chckbx_2D_Xpositioner3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_X = 2;
				data2D.setSelectedPositioner_X(2);
			}
		});

		
		chckbx_2D_Xpositioner4 = new JCheckBox("New check box");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner4);
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

		
		JPanel yAxisPositionersPanel_2D = new JPanel();
		tabbedPane.addTab("Y-Axis", null, yAxisPositionersPanel_2D, null);
		yAxisPositionersPanel_2D.setLayout(new GridLayout(4, 0, 0, 0));
		
		chckbx_2D_Ypositioner1 = new JCheckBox("New check box");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner1);
		chckbx_2D_Ypositioner1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 0;
				data2D.setSelectedPositioner_Y(0);
			}
		});

		chckbx_2D_Ypositioner2 = new JCheckBox("New check box");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner2);
		chckbx_2D_Ypositioner2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 1;
				data2D.setSelectedPositioner_Y(1);
			}
		});

		chckbx_2D_Ypositioner3 = new JCheckBox("New check box");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner3);
		chckbx_2D_Ypositioner3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPositioner_Y = 2;
				data2D.setSelectedPositioner_Y(2);
			}
		});
		
		JCheckBox chckbx_2D_Ypositioner4 = new JCheckBox("New check box");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner4);
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
		
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorLabelPanel, 160, SpringLayout.WEST, leftPanel_2D);
		leftPanel_2D.add(detectorLabelPanel);
		
		JPanel detectorSelectionPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorSelectionPanel, 0, SpringLayout.SOUTH, detectorLabelPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorSelectionPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorSelectionPanel, 21, SpringLayout.SOUTH, detectorLabelPanel);
		detectorLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblDetectors = new JLabel("Detectors");
		lblDetectors.setHorizontalAlignment(SwingConstants.CENTER);
		detectorLabelPanel.add(lblDetectors, BorderLayout.CENTER);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorSelectionPanel, 0, SpringLayout.EAST, surfaceCountourPanel);
		surfaceCountourPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JRadioButton rdbtnSurfaceRadioButton = new JRadioButton("Surface Plot");
		surfaceCountourPanel.add(rdbtnSurfaceRadioButton);
		
		JRadioButton rdbtnContourRadioButton = new JRadioButton("Contour Plot");
		surfaceCountourPanel.add(rdbtnContourRadioButton);
		leftPanel_2D.add(detectorSelectionPanel);
		detectorSelectionPanel.setLayout(new BorderLayout(0, 0));
		
		detComboBox_2D = new JComboBox();
		detectorSelectionPanel.add(detComboBox_2D, BorderLayout.CENTER);
		
		detComboBox_2D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(" Selected Combo Item # = "+detComboBox_2D.getItemCount());
				if (detComboBox_2D.getItemCount()>0)
				data2D.setSelectedDetector(detComboBox_2D.getSelectedIndex());
			}
		});

		springLayout.putConstraint(SpringLayout.SOUTH, panel_2D_plot, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panel_2D_plot, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, panel_2D_plot, 10, SpringLayout.NORTH, this);
		add(panel_2D_plot);
		panel_2D_plot.setLayout(new BorderLayout(0, 0));
		
		chart3dJava2d = new Chart3dJava2d();
		panel_2D_plot.add(chart3dJava2d, BorderLayout.CENTER);
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

	}
	
	public JCChart3dJava2d get3DChart(){
		return chart3dJava2d;
	}
	public void set2DDataSource(Data2D d2) {
		data2D = d2;
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
	public void add2DDetector(String str){
		detComboBox_2D.addItem(str);
	}
	
	public void clear2dDetector(){
		detComboBox_2D.removeAllItems();
		detComboBox_2D.getItemCount();
	}
	
	public void resetPositioners_2D(){
		for (int i=0;i<4;i++){
			setXPositionerVisible_2D(i,false);
			setYPositionerVisible_2D(i,false);
		}
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

	public void setXPositionerName_2D(int pos,String str) {
		JCheckBox jb2D = posX2DMap.get(pos);
		jb2D.setText(str);
		
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
	public void setScan2PosPv(PositionerPnPV[] pp) {
		for (int i = 0; i < 4; i++) {
			scan2PosPnPV[i] = pp[i];
		}
	}

}
