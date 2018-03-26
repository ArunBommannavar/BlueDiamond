package bluediamond2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;



public class StylePicker2 extends JComponent {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame jFrame;
//	   java.util.List parmList = new ArrayList();
	   String[] detectors = new String[60];
	   String[] thicknessList = new String[20];
	   String[] shapeList = {"None", "Dot", "Box", "Triangle", "Diamond", "Star", "Vert-Line", "Horiz-Line", "Cross",
	                        "Square"};
	   String[] symbolSizeList = {"1", "2", "3", "4", "5", "6", "7", "8", "9","10","11","12","13","14","15","16","17","18","19","20"};
	   int selectedDetector = 1;
	   int thickness = 1;
	   int symbolSize = 1;
	   int shape = 1;
	   Color color;
	   boolean colorPicked = false;
	   boolean thicknessPicked = false;
	   boolean shapePicked = false;
	   boolean symbolSizePicked = false;

	   /**
	    * Return value if Apply is chosen.
	    */
	   public static final int APPLY_OPTION = 2;

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
	   private JDialog dialog = null;


	   JPanel jPanel1 = new JPanel();
	   BorderLayout borderLayout1 = new BorderLayout();
	   JPanel jPanel2 = new JPanel();
	   BorderLayout borderLayout2 = new BorderLayout();
	   JPanel jPanel3 = new JPanel();
	   JPanel jPanel4 = new JPanel();
	   JPanel jPanel5 = new JPanel();
	   GridLayout gridLayout1 = new GridLayout();
	   JPanel jPanel6 = new JPanel();
	   JPanel jPanel7 = new JPanel();
	   BorderLayout borderLayout3 = new BorderLayout();
	   JPanel jPanel8 = new JPanel();
	   BorderLayout borderLayout4 = new BorderLayout();
	   JLabel jLabel1 = new JLabel();
	   BorderLayout borderLayout5 = new BorderLayout();
	   BorderLayout borderLayout6 = new BorderLayout();
	   JPanel jPanel10 = new JPanel();
	   JPanel jPanel11 = new JPanel();
	   JPanel jPanel12 = new JPanel();
	   JPanel jPanel13 = new JPanel();
	   JButton jButton1 = new JButton();
	   JButton jButton2 = new JButton();
	   JLabel jLabel2 = new JLabel();
	   JLabel jLabel3 = new JLabel();
	   GridLayout gridLayout2 = new GridLayout();
	   GridLayout gridLayout3 = new GridLayout();
	   JPanel jPanel14 = new JPanel();
	   JPanel jPanel15 = new JPanel();
	   JLabel jLabel4 = new JLabel();
	   JLabel jLabel5 = new JLabel();
	   JButton jButton3 = new JButton();
	   JComboBox<String> jComboBox3; // = new JComboBox();
	   GridBagLayout gridBagLayout1 = new GridBagLayout();
	   GridBagLayout gridBagLayout2 = new GridBagLayout();
	   JPanel jPanel17 = new JPanel();
	   JPanel jPanel18 = new JPanel();
	   JPanel jPanel19 = new JPanel();
	   JLabel jLabel7 = new JLabel();
	   JLabel jLabel8 = new JLabel();
	   JComboBox<String> jComboBox4 = new JComboBox<String>(symbolSizeList);
	   JComboBox<String> jComboBox5 = new JComboBox<String>(shapeList);
	   GridBagLayout gridBagLayout4 = new GridBagLayout();
	   GridBagLayout gridBagLayout5 = new GridBagLayout();
	   GridBagLayout gridBagLayout3 = new GridBagLayout();
	   public StylePicker2() {
	      try {
	         jbInit();
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
	   }

	   public int showDialog(Component parent) throws HeadlessException {
	      dialog = createDialog(parent);
	      dialog.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {
	            returnValue = CANCEL_OPTION;
	         }
	      });
	      returnValue = ERROR_OPTION;
	      dialog.pack();
	      dialog.setVisible(true);
	      dialog.dispose();
	      dialog = null;
	      return returnValue;
	   }

	   protected JDialog createDialog(Component parent) throws HeadlessException {

	      Frame frame = parent instanceof Frame ? (Frame) parent
	                    : (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

	      String title = "";
	      JDialog dialog = new JDialog(frame, title, true);

	      Container contentPane = dialog.getContentPane();
	      contentPane.setLayout(new BorderLayout());
	      contentPane.add(this, BorderLayout.CENTER);

	      dialog.pack();
	      dialog.setLocationRelativeTo(parent);

	      return dialog;
	   }

	   private void jbInit() throws Exception {

	      makeArrays();
	      jComboBox3 = new JComboBox<String>(thicknessList);

	      jPanel1.setBounds(new Rectangle(0, 0, 10, 10));
	      this.setLayout(borderLayout1);
	      jPanel2.setLayout(borderLayout2);
	      jPanel3.setBorder(BorderFactory.createEtchedBorder());
	      jPanel3.setLayout(borderLayout3);
	      jPanel4.setBorder(BorderFactory.createEtchedBorder());
	      jPanel4.setLayout(gridLayout1);
	      jPanel5.setBorder(BorderFactory.createEtchedBorder());
	      gridLayout1.setRows(2);
	      jPanel7.setBorder(BorderFactory.createEtchedBorder());
	      jPanel7.setLayout(borderLayout5);
	      jPanel6.setBorder(BorderFactory.createEtchedBorder());
	      jPanel6.setLayout(borderLayout6);
	      jPanel8.setLayout(borderLayout4);
	      jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
	      jLabel1.setForeground(Color.blue);
	      jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
	      jLabel1.setText("                Detector       ");
	      jButton1.setText("Cancel");
	      jButton1.addActionListener(new StylePicker2_jButton1_actionAdapter(this));
	      jButton2.setText(" OK  ");
	      jButton2.addActionListener(new StylePicker2_jButton2_actionAdapter(this));
	      jLabel2.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
	      jLabel2.setForeground(Color.blue);
	      jLabel2.setText("Color    ");
	      jLabel3.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
	      jLabel3.setForeground(Color.blue);
	      jLabel3.setBorder(null);
	      jLabel3.setText("Symbol");
	      jPanel10.setBorder(BorderFactory.createEtchedBorder());
	      jPanel12.setBorder(BorderFactory.createEtchedBorder());
	      jPanel11.setLayout(gridLayout2);
	      jPanel13.setLayout(gridLayout3);
	      gridLayout2.setRows(3);
	      gridLayout3.setRows(3);
	      jPanel15.setLayout(gridBagLayout2);
	      jPanel14.setLayout(gridBagLayout1);
	      jLabel4.setBorder(BorderFactory.createEtchedBorder());
	      jLabel4.setText("Pick Color");
	      jLabel5.setBorder(BorderFactory.createEtchedBorder());
	      jLabel5.setText("Line Thickness");
	      jButton3.setText("Color");
	      jButton3.addActionListener(new StylePicker2_jButton3_actionAdapter(this));
	      jPanel17.setLayout(gridBagLayout4);
	      jPanel17.setBorder(BorderFactory.createEtchedBorder());
	      jPanel14.setBorder(null);
	      jPanel15.setBorder(null);
	      jPanel18.setBorder(BorderFactory.createEtchedBorder());
	      jPanel18.setLayout(gridBagLayout5);
	      jPanel19.setBorder(BorderFactory.createEtchedBorder());
	      jPanel19.setLayout(gridBagLayout3);
	      jLabel7.setBorder(BorderFactory.createEtchedBorder());
	      jLabel7.setText("Shape");
	      jLabel8.setBorder(BorderFactory.createEtchedBorder());
	      jLabel8.setText("Size    ");
	      jComboBox3.addActionListener(new StylePicker2_jComboBox3_actionAdapter(this));
	      jComboBox5.addActionListener(new StylePicker2_jComboBox5_actionAdapter(this));
	      jComboBox4.addActionListener(new StylePicker2_jComboBox4_actionAdapter(this));
	      this.add(jPanel2, java.awt.BorderLayout.CENTER);
	      jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);
	      jPanel4.add(jPanel7);
	      jPanel4.add(jPanel6);
	      jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

	      jPanel2.add(jPanel5, java.awt.BorderLayout.SOUTH);
	      jPanel5.add(jButton2);
	      jPanel5.add(jButton1);
	      jPanel7.add(jPanel10, java.awt.BorderLayout.WEST);
	      jPanel10.add(jLabel2);
	      jPanel7.add(jPanel11, java.awt.BorderLayout.CENTER);
	      jPanel11.add(jPanel14);
	      jPanel11.add(jPanel15);
	      jPanel6.add(jPanel12, java.awt.BorderLayout.WEST);
	      jPanel12.add(jLabel3);
	      jPanel6.add(jPanel13, java.awt.BorderLayout.CENTER);
	      jPanel13.add(jPanel17);
	      jPanel13.add(jPanel18);

	      jPanel13.add(jPanel19);
	      jPanel17.add(jLabel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                                                   , GridBagConstraints.WEST,
	                                                   GridBagConstraints.NONE,
	                                                   new Insets(1, 1, 3, 0), 61, 2));
	      jPanel17.add(jComboBox5, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	                                                      , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
	                                                      new Insets(1, 0, 3, 3), 36, -2));
	      jPanel18.add(jLabel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                                                   , GridBagConstraints.WEST,
	                                                   GridBagConstraints.NONE,
	                                                   new Insets(1, 2, 3, 0), 57, 2));
	      jPanel18.add(jComboBox4, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	                                                      , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
	                                                      new Insets(1, 0, 3, 3), 36, -2));
	      jPanel8.add(jLabel1, java.awt.BorderLayout.CENTER);
	      jPanel14.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                                                   , GridBagConstraints.WEST,
	                                                   GridBagConstraints.NONE,
	                                                   new Insets(1, 2, 3, 0), 45, 2));
	      jPanel14.add(jButton3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	                                                    , GridBagConstraints.CENTER,
	                                                    GridBagConstraints.NONE,
	                                                    new Insets(1, 0, 3, 1), 8, -3));
	      jPanel15.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                                                   , GridBagConstraints.WEST,
	                                                   GridBagConstraints.NONE,
	                                                   new Insets(1, 2, 3, 0), 23, 2));
	      jPanel15.add(jComboBox3, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	                                                      , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
	                                                      new Insets(1, 0, 3, 1), 38, -2));
	      jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

	   }

	   public boolean isColorPicked() {
	      return colorPicked;
	   }

	   public Color getSelectedColor() {
	      return color;
	   }

	   public boolean isThicknessPicked() {
	      return thicknessPicked;
	   }

	   public int getThickness() {
	      return thickness;
	   }
	   public void setSelectedColor(Color c){
		   color = c;
		   System.out.println(" Set Color = "+color);
	   }
	   public void setThickness(int n) {
	      jComboBox3.setSelectedIndex(n-1);

	   }
	   public boolean isShapePicked() {
	      return shapePicked;
	   }

	   public int getShape() {
	      return shape;
	   }

	   public void setShape(int n){
	       jComboBox5.setSelectedIndex(n-1);

	   }
	   public boolean isSymbolSizePicked() {
	      return symbolSizePicked;
	   }

	   public int getSymbolSize() {
	      return symbolSize;
	   }
	   public void setSymbolSize(int n){
	      jComboBox4.setSelectedIndex(n-1);

	   }

	   private void makeArrays() {
	      for (int i = 0; i < 60; i++) {
	         detectors[i] = Integer.toString(i + 1);

	      }

	      for (int i = 0; i < 20; i++) {
	         thicknessList[i] = Integer.toString(i + 1);
	      }
	   }

	   public void jButton3_actionPerformed(ActionEvent e) {

	      Color testColor = JColorChooser.showDialog(this, " Choose Color", color);     
	     	    
	      if (testColor != null) {
	         color = testColor;
	         colorPicked = true;
	      }
	   }

	   public void jComboBox3_actionPerformed(ActionEvent e) {
	      thickness = jComboBox3.getSelectedIndex() + 1;
	      thicknessPicked = true;
	   }

	   public void jComboBox5_actionPerformed(ActionEvent e) {
	      shape = jComboBox5.getSelectedIndex();
	      shapePicked = true;
	   }

	   public void jComboBox4_actionPerformed(ActionEvent e) {
	      symbolSize = jComboBox4.getSelectedIndex()+1;
	      symbolSizePicked = true;
	   }

	   public void jButton4_actionPerformed(ActionEvent e) {
	      returnValue = APPLY_OPTION;
	   }

	   public void jButton2_actionPerformed(ActionEvent e) {
	      returnValue = APPROVE_OPTION;
	      dialog.setVisible(false);

	   }

	   public void jButton1_actionPerformed(ActionEvent e) {
	      returnValue = CANCEL_OPTION;
	      dialog.setVisible(false);

	   }
	}


	class StylePicker2_jButton1_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jButton1_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jButton1_actionPerformed(e);
	   }
	}


	class StylePicker2_jButton2_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jButton2_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jButton2_actionPerformed(e);
	   }
	}


	class StylePicker2_jComboBox4_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jComboBox4_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jComboBox4_actionPerformed(e);
	   }
	}


	class StylePicker2_jComboBox5_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jComboBox5_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jComboBox5_actionPerformed(e);
	   }
	}


	class StylePicker2_jComboBox3_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jComboBox3_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jComboBox3_actionPerformed(e);
	   }
	}


	class StylePicker2_jButton3_actionAdapter implements ActionListener {
	   private StylePicker2 adaptee;
	   StylePicker2_jButton3_actionAdapter(StylePicker2 adaptee) {
	      this.adaptee = adaptee;
	   }

	   public void actionPerformed(ActionEvent e) {
	      adaptee.jButton3_actionPerformed(e);
	   }
}
