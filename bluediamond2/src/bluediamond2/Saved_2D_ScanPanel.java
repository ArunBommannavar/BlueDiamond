package bluediamond2;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.BorderLayout;

import com.klg.jclass.chart3d.Chart3dDataView;
import com.klg.jclass.chart3d.JCChart3dArea;
import com.klg.jclass.chart3d.JCData3dGridIndex;
import com.klg.jclass.chart3d.JCData3dIndex;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;
import com.klg.jclass.chart3d.j2d.beans.Chart3dJava2d;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.vecmath.Point3d;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class Saved_2D_ScanPanel extends JPanel {

	Chart3dJava2d chart3dJava2d;
	Chart3dDataView dataView3D = null;
    boolean pickedPoint3D = false;
    double pickedX;
    double pickedY;
    private final double HOLE_VALUE = Double.MAX_VALUE;

    ButtonGroup xPos_2DGroup = new ButtonGroup();
    ButtonGroup yPos_2DGroup = new ButtonGroup();
	ButtonGroup surfaceContourSelectionGroup = new ButtonGroup();

	private Map<Integer, JCheckBox> posX2DMap = new HashMap<>();
	private Map<Integer, JCheckBox> posYMap = new HashMap<>();
	
	JComboBox detComboBox_2D;

    JLabel x_Pos_2D_value;
    JLabel y_Pos_2D_value;
    JLabel int_2D_value;
	protected OldData2D oldData2D;

    int posPrecision = 4;
    
    int numXPoints;
    int numCurrentXPoint;
    int numXPos;

    int numYPoints;
    int numCurrentYPoint;
    int numYPos;

    int numDets;
    
    String[] posXName;
    String[] posXDesc;

    double[] posXMin;
    double[] posXMax;

    String[] detName;
    String[] detDesc;
    double[] detMin;
    double[] detMax;

    String[] posYName;
    String[] posYDesc;

    double[] posYMin;
    double[] posYMax;
	
    int[] dims = new int[2];

	/**
	 * Create the panel.
	 */
	public Saved_2D_ScanPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JPanel leftPanel_2D = new JPanel();
		leftPanel_2D.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
		springLayout.putConstraint(SpringLayout.NORTH, leftPanel_2D, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, leftPanel_2D, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, leftPanel_2D, 189, SpringLayout.WEST, this);
		add(leftPanel_2D);

		JPanel panel_2D_plot = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, panel_2D_plot, 1, SpringLayout.EAST, leftPanel_2D);
		springLayout.putConstraint(SpringLayout.EAST, panel_2D_plot, -8, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, leftPanel_2D, 0, SpringLayout.SOUTH, panel_2D_plot);
		springLayout.putConstraint(SpringLayout.NORTH, panel_2D_plot, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2D_plot, 514, SpringLayout.NORTH, this);
		SpringLayout sl_leftPanel_2D = new SpringLayout();
		leftPanel_2D.setLayout(sl_leftPanel_2D);

		JPanel surfaceCountourPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, surfaceCountourPanel, 10, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, surfaceCountourPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, surfaceCountourPanel, 62, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, surfaceCountourPanel, 169, SpringLayout.WEST, leftPanel_2D);
		leftPanel_2D.add(surfaceCountourPanel);

		JPanel xyzPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, xyzPanel, 6, SpringLayout.SOUTH, surfaceCountourPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, xyzPanel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, xyzPanel, 169, SpringLayout.WEST, leftPanel_2D);
		surfaceCountourPanel.setLayout(new GridLayout(2, 0, 0, 0));

		JRadioButton rdbtnSurfaceRadioButton = new JRadioButton("Surface Plot");
		rdbtnSurfaceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSurfaceDisplay();
			}
		});
		surfaceCountourPanel.add(rdbtnSurfaceRadioButton);

		JRadioButton rdbtnContourRadioButton = new JRadioButton("Contour Plot");
		rdbtnContourRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContourDisplay();
			}
		});
		rdbtnContourRadioButton.setSelected(true);
		surfaceCountourPanel.add(rdbtnContourRadioButton);
		
		surfaceContourSelectionGroup.add(rdbtnContourRadioButton);
		surfaceContourSelectionGroup.add(rdbtnSurfaceRadioButton);

		leftPanel_2D.add(xyzPanel);

		JPanel positioner_2D_Panel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, positioner_2D_Panel, 154, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, xyzPanel, -6, SpringLayout.NORTH, positioner_2D_Panel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, positioner_2D_Panel, 10, SpringLayout.WEST, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, positioner_2D_Panel, 0, SpringLayout.EAST, surfaceCountourPanel);
		xyzPanel.setLayout(new MigLayout("", "[][]", "[][][]"));

		JLabel x_AxisPosLabel2D = new JLabel("X = ");
		xyzPanel.add(x_AxisPosLabel2D, "cell 0 0");

		x_Pos_2D_value = new JLabel("New label");
		xyzPanel.add(x_Pos_2D_value, "cell 1 0");

		JLabel y_AxisPosLabel2D = new JLabel("Y =");
		xyzPanel.add(y_AxisPosLabel2D, "cell 0 1");

		y_Pos_2D_value = new JLabel("New label");
		xyzPanel.add(y_Pos_2D_value, "cell 1 1");

		JLabel intensityLabel_2D = new JLabel("Int=");
		xyzPanel.add(intensityLabel_2D, "cell 0 2");

		int_2D_value = new JLabel("New label");
		xyzPanel.add(int_2D_value, "cell 1 2");
		leftPanel_2D.add(positioner_2D_Panel);

		JPanel detectorLabelPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorLabelPanel, 300, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, positioner_2D_Panel, -6, SpringLayout.NORTH, detectorLabelPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorLabelPanel, 0, SpringLayout.WEST, surfaceCountourPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorLabelPanel, 169, SpringLayout.WEST, leftPanel_2D);
		positioner_2D_Panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		positioner_2D_Panel.add(tabbedPane, BorderLayout.CENTER);

		JPanel xAxisPositionersPanel_2D = new JPanel();
		tabbedPane.addTab("X-Axis", null, xAxisPositionersPanel_2D, null);
		xAxisPositionersPanel_2D.setLayout(new GridLayout(4, 0, 0, 0));
		
		JCheckBox chckbx_2D_Xpositioner1 = new JCheckBox("X-Positioner 1");
		chckbx_2D_Xpositioner1.setSelected(true);
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner1);
		
		JCheckBox chckbx_2D_Xpositioner2 = new JCheckBox("X-Positioner 2");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner2);
		
		JCheckBox chckbx_2D_Xpositioner3 = new JCheckBox("X-Positioner 3");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner3);
		
		JCheckBox chckbx_2D_Xpositioner4 = new JCheckBox("X-Positioner 4");
		xAxisPositionersPanel_2D.add(chckbx_2D_Xpositioner4);

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
		
		JCheckBox chckbx_2D_Ypositioner1 = new JCheckBox("Y-Positioner 1");
		chckbx_2D_Ypositioner1.setSelected(true);
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner1);
		
		JCheckBox chckbx_2D_Ypositioner2 = new JCheckBox("Y-Positioner 2");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner2);
		
		JCheckBox chckbx_2D_Ypositioner3 = new JCheckBox("Y-Positioner 3");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner3);
		
		JCheckBox chckbx_2D_Ypositioner4 = new JCheckBox("Y-Positioner 4");
		yAxisPositionersPanel_2D.add(chckbx_2D_Ypositioner4);
		leftPanel_2D.add(detectorLabelPanel);
		
		yPos_2DGroup.add(chckbx_2D_Ypositioner1);
		yPos_2DGroup.add(chckbx_2D_Ypositioner2);
		yPos_2DGroup.add(chckbx_2D_Ypositioner3);
		yPos_2DGroup.add(chckbx_2D_Ypositioner4);		
		
		posYMap.put(0, chckbx_2D_Ypositioner1);
		posYMap.put(1, chckbx_2D_Ypositioner2);
		posYMap.put(2, chckbx_2D_Ypositioner3);
		posYMap.put(3, chckbx_2D_Ypositioner4);

		
		JPanel detectorSelectionPanel = new JPanel();
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorLabelPanel, -6, SpringLayout.NORTH, detectorSelectionPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.SOUTH, detectorSelectionPanel, -158, SpringLayout.SOUTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.NORTH, detectorSelectionPanel, 326, SpringLayout.NORTH, leftPanel_2D);
		sl_leftPanel_2D.putConstraint(SpringLayout.WEST, detectorSelectionPanel, 0, SpringLayout.WEST, surfaceCountourPanel);
		sl_leftPanel_2D.putConstraint(SpringLayout.EAST, detectorSelectionPanel, 0, SpringLayout.EAST,
				surfaceCountourPanel);
		detectorLabelPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Detectors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detectorLabelPanel.add(lblNewLabel, BorderLayout.CENTER);
		leftPanel_2D.add(detectorSelectionPanel);
		detectorSelectionPanel.setLayout(new BorderLayout(0, 0));

		detComboBox_2D = new JComboBox();
		detComboBox_2D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (detComboBox_2D.getItemCount()>0)
					oldData2D.setSelectedDetector(detComboBox_2D.getSelectedIndex());
			}
		});
		detectorSelectionPanel.add(detComboBox_2D, BorderLayout.CENTER);
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

	public JCChart3dJava2d get3DChart() {
		return chart3dJava2d;
	}

	public void setSelectedXPositionerFontSize(int n) {
		chart3dJava2d.getChart3dArea().getXAxis().setAnnoFontCubeSize(n);
		chart3dJava2d.getChart3dArea().getXAxis().setTitleFontCubeSize(n);
	}

	public void setSelectedYPositionerFontSize(int n) {
		chart3dJava2d.getChart3dArea().getYAxis().setAnnoFontCubeSize(n);
		chart3dJava2d.getChart3dArea().getYAxis().setTitleFontCubeSize(n);
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
				pickedX = oldData2D.getXValue(nX);
				pickedY = oldData2D.getYValue(nY);
				zz = oldData2D.getZValue(nX, nY);
				x_Pos_2D_value.setText(String.valueOf(pickedX));
				y_Pos_2D_value.setText(String.valueOf(pickedY));
				int_2D_value.setText(String.valueOf(zz));
				// System.out.println(" Mouse Clicked in Chart area ");
			}

		}
	}
	
	
	public void setChartHeader(String str) {
		chart3dJava2d.setHeaderText(str);
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
		System.out.println(" Adding Detector "+str+"  total = "+detComboBox_2D.getItemCount());

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


	public void setFile(File inFile) {
        String tempString;
        int dataRank;

		oldData2D = new OldData2D(chart3dJava2d);
		dataView3D.setElevationDataSource(oldData2D);
		String inFileName = inFile.getName();
		setChartHeader(inFileName);
		
		ReadSavedMdaData rmd = new ReadSavedMdaData();

        rmd.setFile(inFile);
        rmd.readMdaData();
        dataRank = rmd.getRank();

        numCurrentYPoint = rmd.getCurrentPoint(0);
        clear2dDetector();
        /**
         * dims[0] = outer loop numPoints
         * dims[1] = inner loop numPoints
         */

        if (rmd.getCurrentPoint(0) > 1) {
        	
            dims = rmd.getDims();
            numYPoints = dims[0];
            numXPoints = dims[1];
            double[][] xVal;
            double[][] yVal;
            double[][][] zVal;

            numXPos = rmd.getNumPos(1);
            numYPos = rmd.getNumPos(0);
            numDets = rmd.getNumDets(1);
            oldData2D.initDataArray(numXPoints, numYPoints, numXPos, numYPos, numDets);

            posXName = new String[numXPos];
            posXDesc = new String[numXPos];

            posYName = new String[numYPos];
            posYDesc = new String[numYPos];

            detName = new String[numDets];
            detDesc = new String[numDets];

            posXName = rmd.getPosName(1);
            posXDesc = rmd.getPosDesc(1);

            posYName = rmd.getPosName(0);
            posYDesc = rmd.getPosDesc(0);

            detName = rmd.getDetName(1);
            detDesc = rmd.getDetDesc(1);
            
            for (int i = 0; i < 4; i++) {
                if (i < numXPos) {
                    tempString = posXDesc[i].trim();
                    if (tempString.equals("")) {
                        tempString = posXName[i].trim();
                    }
                    setPosXValid(i,tempString);
                } else {
                    setPosXInvalid(i);
                }
            }
            
            for (int i = 0; i < 4; i++) {
                if (i < numYPos) {
                    tempString = posYDesc[i].trim();
                    if (tempString.equals("")) {
                        tempString = posYName[i].trim();
                    }
                    setPosYValid(i,tempString);
                } else {
                    setPosYInvalid(i);
                }
            }

            for (int i = 0; i < 60; i++) {
            	tempString = " ";
                if (i < numDets) {
 
                    tempString = detDesc[i].trim();
                    if (tempString.equals("")) {
                        tempString = detName[i].trim();
                    }
                    add2DDetector(tempString);
                } 
            }

            xVal = new double[numXPos][numXPoints];
            yVal = new double[numYPos][numCurrentYPoint];
            zVal = new double[numDets][numXPoints][numCurrentYPoint];
            
            xVal = rmd.getPosData(1);
            yVal = rmd.getPosData(0);

            for (int i = 0; i < numDets; i++) {
                for (int j = 0; j < numXPoints; j++) {
                    for (int k = 0; k < numCurrentYPoint; k++) {
                        zVal[i][j][k] = HOLE_VALUE;
                    }
                }
            }

            int tempXpts;
            double[][] temp;

            for (int k = 0; k < numCurrentYPoint; k++) {

                tempXpts = rmd.getCurrentPoint(k + 1);
                temp = new double[numDets][tempXpts];
                temp = rmd.getDetsData(k + 1);

                for (int i = 0; i < numDets; i++) {
                    for (int j = 0; j < tempXpts; j++) {
                        zVal[i][j][k] = temp[i][j];
                    }
                }
            }

            oldData2D.setXPosdata(xVal);
            oldData2D.setYPosdata(yVal);
            oldData2D.setZData(zVal, numDets, numXPoints, numCurrentYPoint);
            oldData2D.plotData();
        }		
	}
	
	   public void setPosXValid(int i,String str) {
		      Object obj = posX2DMap.get(i);
		      JCheckBox jc = (JCheckBox) obj;
		      jc.setText(str);
		      jc.setEnabled(true);
		      jc.setVisible(true);
		   }
	   
	   public void setPosXInvalid(int i) {
		      Object obj = posX2DMap.get(i);
		      JCheckBox jc = (JCheckBox) obj;
		      jc.setEnabled(false);
		      jc.setVisible(false);
		   }
	   
	   public void setPosYValid(int i,String str) {
		      Object obj = posYMap.get(i);
		      JCheckBox jc = (JCheckBox) obj;
		      jc.setText(str);
		      jc.setEnabled(true);
		      jc.setVisible(true);
		   }
	   
	   public void setPosYInvalid(int i) {
		      Object obj = posYMap.get(i);
		      JCheckBox jc = (JCheckBox) obj;
		      jc.setEnabled(false);
		      jc.setVisible(false);
		   }


}
