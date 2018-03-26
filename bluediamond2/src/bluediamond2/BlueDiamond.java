package bluediamond2;

import java.awt.EventQueue;

import javax.swing.UIManager.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;
import edu.ciw.hpcat.epics.data.*;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

//import javax.swing.JFrame;

public class BlueDiamond {

	LogoPanel logoPanel = new LogoPanel();

	private JFrame frame;
	Config config = null;
	String scan1Str = "mnbmnb ";
	String scan2Str = "gfdfgdfgd ";
	File programPath;

	boolean derivative = false;
	boolean yAxisLog = false;
	Scan1PositionerParms scan1PositionerParms;
	Scan2PositionerParms scan2PositionerParms;
	Scan1DetectorParms scan1DetectorParms;
	// Scan1Monitor scan1Monitor;
	// Scan2Monitor scan2Monitor;

	Data1D data1D;
	Data2D data2D;

	ScanMonitor scanMonitor;
	MainPanel_1 mainPanel;
	protected JCChart chart;
	protected JCChart oldChart;
    JCChart3dJava2d chart3d = null;
    
    ReadSavedMdaFile readSavedMdaFile = ReadSavedMdaFile.getInstance();

    CountDownConnection countDownConnection = CountDownConnection.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (Exception e) {
					    // If Nimbus is not available, you can set the GUI to another look and feel.
					}
					BlueDiamond window = new BlueDiamond();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BlueDiamond() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Path currentRelativePath = Paths.get("");
		programPath = new File(currentRelativePath.toAbsolutePath().toString());
		frame = new JFrame();
		frame.setBounds(20, 20, 1100, 770);
		frame.setPreferredSize(new Dimension(1100,770));
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeApplication();
			}
		});
//		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(logoPanel, java.awt.BorderLayout.CENTER);
		// frame.getContentPane().getComponent(0).setName("BlueDiamond");

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenMda = new JMenuItem("Open");
		mnFile.add(mntmOpenMda);
		mntmOpenMda.addActionListener(new BlueDiamond_Mdafileopen_ActionAdapter(this));

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new BlueDiamond_fileexit_ActionAdapter(this));

		JMenu mnConfiguration = new JMenu("configuration");
		menuBar.add(mnConfiguration);

		JMenuItem mntmNewConfig = new JMenuItem("New");
		mnConfiguration.add(mntmNewConfig);
		mntmNewConfig.addActionListener(new BlueDiamond_confignew_ActionAdapter(this));

		JMenuItem mntmOpenConfig = new JMenuItem("Open");
		mnConfiguration.add(mntmOpenConfig);
		mntmOpenConfig.addActionListener(new Bluediamond_configopen_ActionAdapter(this));

		JMenu mnUtil = new JMenu("Util");
		menuBar.add(mnUtil);

		JMenuItem mntmDerivative = new JMenuItem("Derivative");
		mnUtil.add(mntmDerivative);
		mntmDerivative.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				derivative = !derivative;
				data1D.setDerivative(derivative);
				mntmDerivative.setText(((derivative)? "Raw":"Derivative"));
				
			}			
		});

		JMenuItem mntmYaxislog = new JMenuItem("y-axis->log");
		mnUtil.add(mntmYaxislog);
		mntmYaxislog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				yAxisLog = !yAxisLog;
				data1D.setLogLinear(yAxisLog);
				mntmYaxislog.setText(((yAxisLog)? "Linear":"Logarithmic"));
				
			}			
		});

		JMenu mnMdafiles = new JMenu("mdaFiles");
		menuBar.add(mnMdafiles);

		JMenuItem mntmShow = new JMenuItem("Show");
		mnMdafiles.add(mntmShow);

		JMenu mnTable = new JMenu("TableScan");
		menuBar.add(mnTable);

		JMenuItem mntmReadValues = new JMenuItem("Read Values");
		mnTable.add(mntmReadValues);

		JMenu mnHelp = new JMenu("About");
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new BlueDiamond_About_ActionAdapter(this));


	}

	public void closeApplication() {
		scanMonitor.disconnectChannel();
		scan1PositionerParms.disconnectChannel();
		scan1DetectorParms.disconnectChannel();
		scan2PositionerParms.disconnectChannel();
		System.exit(0);

	}

	public void mdaFileOpen_actionPerformed(ActionEvent e) {
        /**
         * Open an old mda file for display
         */
        File inFile;
//        byte[] fileContents;
//        int currentPoint;
        JFileChooser jFileChooser1 = new JFileChooser();
        jFileChooser1.setMultiSelectionEnabled(false);
        jFileChooser1.setFileFilter(new MDAfilter());
//        int retVal = jFileChooser1.showOpenDialog(this);
        
        int retVal = jFileChooser1.showOpenDialog(null);
        /**
         * After getting the filename tell the ReadSaveddata
         * instance to read and the data from that file and
         * depending on the Rank show either
         * 1-D scans or 2-D scans.
         *
         */
        if (retVal == JFileChooser.APPROVE_OPTION) {
            inFile = jFileChooser1.getSelectedFile();
            readSavedMdaFile.setFile(inFile,oldChart);
        }
	}

	public void about_actionPerformed(ActionEvent e) {

		AboutFrame frameAbout = new AboutFrame();
		// int width = 350;
		// int height = 300;
		// frame.setSize(width, height);
		frameAbout.setVisible(true);

	}

	public void showNewConfigPanel() {
		int returnValue = 0;
		config = new Config(frame, true);

		config.set_1DScanPV(scan1Str);
		config.set_2DScanPV(scan2Str);

		returnValue = config.getReturnVal();
		switch (returnValue) {
		case 0:
			scan1Str = config.get_1DScanPV();
			scan2Str = config.get_2DScanPV();

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(programPath);
			fc.setFileFilter(new TXTfilter());
			int returnVal = fc.showSaveDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String tmp = file.getAbsolutePath();
				if (!tmp.endsWith(".txt")) {
					tmp = tmp + ".txt";
					file = new File(tmp);
				}
				try {
					FileWriter fStream = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(fStream);
					out.write(scan1Str);
					out.write(System.getProperty("line.separator"));
					out.write(scan2Str);
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			break;

		case 1:
			System.out.println(" Cancelled ");
			break;

		case 2:
			System.out.println(" RUN button pressed");
			System.out.println(" scan1 = " + scan1Str);
			System.out.println(" scan2 = " + scan2Str);

			break;
		}

	}

	public void showOpenConfigPanel() {
		File inFile;
		String[] parms = new String[2];

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(programPath);
		fc.setFileFilter(new TXTfilter());
		int retVal = fc.showOpenDialog(frame);

		if (retVal == JFileChooser.APPROVE_OPTION) {
			inFile = fc.getSelectedFile();

			int i = 0;
			try {
				BufferedReader in = new BufferedReader(new FileReader(inFile));
				String inStr;
				while ((inStr = in.readLine()) != null) {
					parms[i] = inStr;
					i++;
				}
				in.close();
			} catch (IOException e1) {
			}

			scan1Str = parms[0].trim();
			scan2Str = parms[1].trim();

			scan1PositionerParms = new Scan1PositionerParms(scan1Str);
			scan1DetectorParms = new Scan1DetectorParms(scan1Str);
			scan2PositionerParms = new Scan2PositionerParms(scan2Str);

			scan1PositionerParms.initPnPV_values();
			mainPanel = new MainPanel_1();
			chart = mainPanel.getChart();
			oldChart = mainPanel.getOldChart();

			chart3d = mainPanel.get3DChart(); 

			data1D = new Data1D(chart);
			data2D = new Data2D(chart3d);
			chart3d.getDataView(0).setElevationDataSource(data2D);
			
			mainPanel.set1DDataSource(data1D);
			mainPanel.set2DDataSource(data2D);
			chart.getDataView(0).setDataSource(data1D);

			scanMonitor = new ScanMonitor(scan1Str, scan2Str);
			scanMonitor.setData1D(data1D);
			scanMonitor.setData2D(data2D);
			
			scanMonitor.setScan1PositionerParms(scan1PositionerParms);
			scanMonitor.setScan1DetectorParms(scan1DetectorParms);
			scanMonitor.setScan2PositionerParms(scan2PositionerParms);
			scanMonitor.setMainPanel(mainPanel);

			scan1PositionerParms.createPosPVs();
			countDownConnection.pendIO();

			scan1DetectorParms.createDetPVs();
			countDownConnection.pendIO();

			scan2PositionerParms.createPosPVs();
			countDownConnection.pendIO();
			scanMonitor.createScanPVS();

			countDownConnection.pendIO();

			scanMonitor.validate1DPositioners();
			scanMonitor.validate2DPositioners();
			scanMonitor.validateDets();
			
		
			scanMonitor.getScan1ValidPos();
			scanMonitor.getScan1ValidDet();
			scanMonitor.getScan2ValidPos();
			scanMonitor.setMainPanel_1D_PositionerNames();
			scanMonitor.setMainPanel_2D_X_PositionerNames();
			scanMonitor.setMainPanel_2D_Y_PositionerNames();
			scanMonitor.setMainPanel_1D_DetectorNames();
			

			mainPanel.setScan1PosPv(scan1PositionerParms.getPosPnPV());
			mainPanel.setScan2PosPv(scan2PositionerParms.getPosPnPV());		
			

			scanMonitor.createDSTATE();
			scanMonitor.createDATA();
			countDownConnection.pendIO();


			Thread scanMonitorThread = new Thread(scanMonitor);
			scanMonitorThread.start();
			initDisplay();			
		}
	}

	private void initDisplay() {
		frame.getContentPane().remove(0);
		frame.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
		frame.getContentPane().validate();
		frame.revalidate();

	}

	public void fileexit_actionPerformed(ActionEvent e) {
		closeApplication();

	}

	public void confignew_actionPerformed(ActionEvent e) {
		showNewConfigPanel();

	}

	public void configopen_actionPerformed(ActionEvent e) {
		showOpenConfigPanel();

	}
}

class BlueDiamond_Mdafileopen_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_Mdafileopen_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.mdaFileOpen_actionPerformed(actionEvent);
	}
}

class BlueDiamond_fileexit_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_fileexit_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.fileexit_actionPerformed(actionEvent);
	}
}

class BlueDiamond_confignew_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_confignew_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		// adaptee.confignew_actionPerformed(actionEvent);
		adaptee.showNewConfigPanel();
	}
}

class Bluediamond_configopen_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	Bluediamond_configopen_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.configopen_actionPerformed(actionEvent);
	}
}

class BlueDiamond_About_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_About_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.about_actionPerformed(actionEvent);
	}
}

class TXTfilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
	}

	public String getDescription() {
		return "txt files";
	}
}
class MDAfilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mda");
    }

    public String getDescription() {
        return "mda files";
    }
}


