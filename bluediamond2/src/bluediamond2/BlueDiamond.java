package bluediamond2;

import java.awt.EventQueue;

import javax.swing.UIManager.*;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;

import gov.aps.jca.CAException;
import gov.aps.jca.Context;
import gov.aps.jca.JCALibrary;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;

public class BlueDiamond {

	private static final String LOOKANDFEEL = null;
	final static String THEME = "Test";

	LogoPanel logoPanel = new LogoPanel();

	private JFrame frmBluediamond;
	private Context context = null;

	Config config = null;
	String scan1Str = "mnbmnb ";
	String scan2Str = "gfdfgdfgd ";
	File programPath;
	String mdaFilesPath = ".";

	boolean derivative = false;
	boolean yAxisLog = false;
	boolean showVMarkers = false;
	boolean showHMarkers = false;

	ScanMonitor scanMonitor = null;
	Scan1PositionerParms scan1PositionerParms = null;
	Scan2PositionerParms scan2PositionerParms = null;
	Scan1DetectorParms scan1DetectorParms = null;

	Data1D data1D;
	Data2D data2D;
	OldData2D oldData2D;

	MainPanel mainPanel;
	Active_1D_ScanPanel active_1D_ScanPanel;
	Active_2D_ScanPanel active_2D_ScanPanel;
	Saved_1D_ScanPanel saved_1D_ScanPanel;
	Saved_2D_ScanPanel saved_2D_ScanPanel;

	protected JCChart chart;
	protected JCChart oldChart;
	protected JCChart3dJava2d chart3d = null;
	protected JCChart3dJava2d oldChart3d = null;

	JRadioButton rdbtnAutoConvertRadioButton;
	boolean autoConvert = false;

	private Socket clientSocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					try {
						for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

							if ("Windows".equals(info.getName())) {
								UIManager.setLookAndFeel(info.getClassName());
								break;
							}
						}
					} catch (Exception e) {
						// If Nimbus is not available, you can set the GUI to another look and feel.
					}

					BlueDiamond window = new BlueDiamond();
					window.frmBluediamond.setVisible(true);
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
		frmBluediamond = new JFrame();
		frmBluediamond.setTitle("BlueDiamond");
		frmBluediamond.setBounds(20, 20, 1100, 770);
		frmBluediamond.setPreferredSize(new Dimension(1100, 770));
		frmBluediamond.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeApplication();
			}
		});
		// frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frmBluediamond.getContentPane().add(logoPanel, java.awt.BorderLayout.CENTER);
		// frame.getContentPane().getComponent(0).setName("BlueDiamond");

		initializeJCA();

		JMenuBar menuBar = new JMenuBar();
		frmBluediamond.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnFile);

		JMenuItem mntmOpenMda = new JMenuItem("Open");
		mnFile.add(mntmOpenMda);
		mntmOpenMda.addActionListener(new BlueDiamond_Mdafileopen_ActionAdapter(this));

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new BlueDiamond_fileexit_ActionAdapter(this));

		JMenu mnConfiguration = new JMenu("configuration");
		mnConfiguration.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnConfiguration);

		JMenuItem mntmNewConfig = new JMenuItem("New");
		mnConfiguration.add(mntmNewConfig);
		mntmNewConfig.addActionListener(new BlueDiamond_confignew_ActionAdapter(this));

		JMenuItem mntmOpenConfig = new JMenuItem("Open");
		mnConfiguration.add(mntmOpenConfig);
		mntmOpenConfig.addActionListener(new Bluediamond_configopen_ActionAdapter(this));

		JMenu mnUtil = new JMenu("Util");
		mnUtil.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuBar.add(mnUtil);

		JMenuItem mntmDerivative = new JMenuItem("Derivative");
		mnUtil.add(mntmDerivative);
		mntmDerivative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				derivative = !derivative;
				data1D.setDerivative(derivative);
				mntmDerivative.setText(((derivative) ? "Raw" : "Derivative"));
			}
		});

		JMenuItem mntmYaxislog = new JMenuItem("y-axis->log");
		mnUtil.add(mntmYaxislog);

		JMenu mnMarkerMenu = new JMenu("Markers");
		mnUtil.add(mnMarkerMenu);

		JMenuItem mntmShowVMarkers = new JMenuItem("Hide V Markers");
		mntmShowVMarkers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (showVMarkers) {
					active_1D_ScanPanel.showVMarkers();
				} else {
					active_1D_ScanPanel.hideVMarkers();
				}
				showVMarkers = !showVMarkers;
				mntmShowVMarkers.setText(((showVMarkers) ? "Show V Marker" : "Hide V Marker"));
			}
		});

		mnMarkerMenu.add(mntmShowVMarkers);

		JMenuItem mntmShowHMarkers = new JMenuItem("Hide H Markers");
		mntmShowHMarkers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showHMarkers) {
					active_1D_ScanPanel.showHMarkers();
				} else {
					active_1D_ScanPanel.hideHMarkers();
				}
				showHMarkers = !showHMarkers;
				mntmShowHMarkers.setText(((showHMarkers) ? "Show H Marker" : "Hide H Marker"));
			}
		});
		mnMarkerMenu.add(mntmShowHMarkers);

		JMenuItem mntmNewMenuItem = new JMenuItem("Reset");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.getSelectedPanelIndex();
			}
		});
		mnMarkerMenu.add(mntmNewMenuItem);

		mntmYaxislog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yAxisLog = !yAxisLog;
				data1D.setLogLinear(yAxisLog);
				mntmYaxislog.setText(((yAxisLog) ? "Linear" : "Logarithmic"));
			}
		});

		JMenu mnMdafiles = new JMenu("mdaFiles");
		mnMdafiles.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnMdafiles);

		JMenuItem mntmShow = new JMenuItem("Show");
		mnMdafiles.add(mntmShow);

		JMenu mnTable = new JMenu("TableScan");
		mnTable.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnTable);

		JMenuItem mntmReadTableScanValues = new JMenuItem("Read Values");
		mnTable.add(mntmReadTableScanValues);
		mntmReadTableScanValues.addActionListener(new BlueDiamond_mntmReadTableScanValues_ActionAdapter(this));

		JMenu mnHelp = new JMenu("About");
		mnHelp.setFont(new Font("Segoe UI", Font.BOLD, 14));

		JMenu mnAscii = new JMenu("ASCII");
		menuBar.add(mnAscii);

		rdbtnAutoConvertRadioButton = new JRadioButton("Auto Convert");
		mnAscii.add(rdbtnAutoConvertRadioButton);
		rdbtnAutoConvertRadioButton.addActionListener(new BlueDiamond_AutoConvert_ActionAdapter(this));

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new BlueDiamond_About_ActionAdapter(this));

	}

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String sendMessage(String msg) {
		out.println(msg);
		System.out.println(" Sending Message from Client " + msg);
		String resp = null;
		try {
			resp = in.readLine();
			System.out.println(" Response from Server = " + resp.trim());
			System.out.println(" next line");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	public void stopConnection() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (clientSocket != null)
				clientSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initializeJCA() {
		JCALibrary jca = JCALibrary.getInstance();
		try {
			context = jca.createContext(JCALibrary.CHANNEL_ACCESS_JAVA);
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeApplication() {
		if (scanMonitor != null)
			scanMonitor.disconnectChannel();
		if (scan1PositionerParms != null)
			scan1PositionerParms.disconnectChannel();
		if (scan1DetectorParms != null)
			scan1DetectorParms.disconnectChannel();
		if (scan2PositionerParms != null)
			scan2PositionerParms.disconnectChannel();
		System.exit(0);
	}

	private void closeJCAChannels() {
		try {

			// Destroy the context, check if never initialized.
			if (context != null)
				context.destroy();

		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	public void mdaFileOpen_actionPerformed(ActionEvent e) {
		/**
		 * Open an old mda file for display
		 */
		File inFile;
		JFileChooser jFileChooser1 = new JFileChooser(mdaFilesPath);
		FileInputStream mdaFileInputStream = null;
		jFileChooser1.setMultiSelectionEnabled(false);
		jFileChooser1.setFileFilter(new MDAfilter());

		int retVal = jFileChooser1.showOpenDialog(null);
		/**
		 * After getting the filename tell the ReadSaveddata instance to read and the
		 * data from that file and depending on the Rank show either 1-D scans or 2-D
		 * scans.
		 *
		 */
		if (retVal == JFileChooser.APPROVE_OPTION) {

			inFile = jFileChooser1.getSelectedFile();
			mdaFilesPath = inFile.getAbsolutePath();
			Path paths = Paths.get(mdaFilesPath);
			try {
				byte[] mdaDataBytes = Files.readAllBytes(paths);
				ByteArrayInputStream inBytes = new ByteArrayInputStream(mdaDataBytes);
				DataInputStream inData = new DataInputStream(inBytes);

				float version = inData.readFloat();
				int scanNumber = inData.readInt();
				int dataRank = inData.readInt();

				inData.close();
				if (dataRank == 1) {

					String inFileName = inFile.getName();
					if (!saved_1D_ScanPanel.isListed(inFileName)) {
						inData.close();
						saved_1D_ScanPanel.setFile(inFile);
					}
				} else if (dataRank == 2) {
					inData.close();
					saved_2D_ScanPanel.setFile(inFile);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void about_actionPerformed(ActionEvent e) {

		AboutFrame frameAbout = new AboutFrame();
		frameAbout.setVisible(true);
	}

	public void showNewConfigPanel() {
		int returnValue = 0;
		config = new Config(frmBluediamond, true);

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
			int returnVal = fc.showSaveDialog(frmBluediamond);

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
		int retVal = fc.showOpenDialog(frmBluediamond);

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

			scan1PositionerParms = new Scan1PositionerParms(scan1Str, context);
			scan1DetectorParms = new Scan1DetectorParms(scan1Str, context);
			scan2PositionerParms = new Scan2PositionerParms(scan2Str, context);

			mainPanel = new MainPanel();

			chart = mainPanel.getChart();
			oldChart = mainPanel.getOldChart();
			chart3d = mainPanel.get3DChart();
			oldChart3d = mainPanel.getOld3DChart();

			data1D = new Data1D(chart);
			data2D = new Data2D(chart3d);
			oldData2D = new OldData2D(oldChart3d);

			chart3d.getDataView(0).setElevationDataSource(data2D);
			oldChart3d.getDataView(0).setElevationDataSource(oldData2D);

			mainPanel.set1DDataSource(data1D);
			mainPanel.set2DDataSource(data2D);
			chart.getDataView(0).setDataSource(data1D);

			scanMonitor = new ScanMonitor(scan1Str, scan2Str, context);
			scanMonitor.setData1D(data1D);
			scanMonitor.setData2D(data2D);

			scanMonitor.setScan1PositionerParms(scan1PositionerParms);
			scanMonitor.setScan1DetectorParms(scan1DetectorParms);
			scanMonitor.setScan2PositionerParms(scan2PositionerParms);

			active_1D_ScanPanel = mainPanel.getActive_1D_ScanPanel();
			active_1D_ScanPanel.setContext(context);
			scanMonitor.setActive_1D_ScanPanel(active_1D_ScanPanel);
			active_1D_ScanPanel.resetPositioners_1D();

			active_2D_ScanPanel = mainPanel.getActive_2D_ScanPanel();
			active_2D_ScanPanel.setContext(context);
			scanMonitor.setActive_2D_ScanPanel(active_2D_ScanPanel);
			active_2D_ScanPanel.resetPositioners_2D();

			saved_1D_ScanPanel = mainPanel.getSaved_1D_ScanPanel();
			saved_2D_ScanPanel = mainPanel.getSaved_2D_ScanPanel();

			scan1PositionerParms.createPosPVs();
			scan1DetectorParms.createDetPVs();

			scan2PositionerParms.createPosPVs();

			scanMonitor.createScanPVS();

			scanMonitor.validate1DPositioners();
			scanMonitor.validate2DPositioners();
			scanMonitor.validateDets();

			scanMonitor.getScan1ValidPos();
			scanMonitor.getScan1ValidDet();
			scanMonitor.getScan2ValidPos();

			active_1D_ScanPanel.setScan1PosPv(scan1PositionerParms.getPosPnPVList());
			active_2D_ScanPanel.setScan1PosPv(scan1PositionerParms.getPosPnPVList());
			active_2D_ScanPanel.setScan2PosPv(scan2PositionerParms.getPosPnPVList());

			scan1PositionerParms.findPositionerDescription();
			scan2PositionerParms.findPositionerDescription();
			scan1DetectorParms.findDetectorDescription();

			scanMonitor.setMainPanel_1D_PositionerNames();
			scanMonitor.setMainPanel_2D_X_PositionerNames();
			scanMonitor.setMainPanel_2D_Y_PositionerNames();
			scanMonitor.setMainPanel_1D_DetectorNames();
			/*
			 * startConnection("164.54.105.197",27000); sendMessage("Get Message    ");
			 */
			scanMonitor.createDSTATE();
			scanMonitor.createDATA();

			Thread scanMonitorThread = new Thread(scanMonitor);
			scanMonitorThread.start();
			initDisplay();

			active_1D_ScanPanel.showVMarkers();
			active_1D_ScanPanel.showHMarkers();
			active_1D_ScanPanel.setMarkers();
			active_1D_ScanPanel.updateUserAuto();
		}
	}

	private void initDisplay() {
		frmBluediamond.getContentPane().remove(0);
		frmBluediamond.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
		frmBluediamond.getContentPane().validate();
		frmBluediamond.revalidate();

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

	public void autoConvert_actionPerformed(ActionEvent e) {
		autoConvert = rdbtnAutoConvertRadioButton.isSelected();
	}

	public void readTableScanValuesDialog() {
		int returnValue = 0;
		TableDataConfig tableDataConfig = new TableDataConfig(frmBluediamond, true);
		tableDataConfig.setScan1PositionerParms(scan1PositionerParms);
		tableDataConfig.setScan2PositionerParms(scan2PositionerParms);
		tableDataConfig.get1DValidPosList();
		tableDataConfig.get2DValidPosList();

		tableDataConfig.populatePositioners();
		tableDataConfig.revalidate();
		tableDataConfig.setVisible(true);
		tableDataConfig.setModal(true);

		returnValue = tableDataConfig.getReturnVal();
//		tableDataConfig.setVisible(false);
//		System.out.println(" Return Value = "+returnValue);

	}
}

class BlueDiamond_AutoConvert_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_AutoConvert_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {

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

class BlueDiamond_mntmReadTableScanValues_ActionAdapter implements ActionListener {
	BlueDiamond adaptee;

	BlueDiamond_mntmReadTableScanValues_ActionAdapter(BlueDiamond adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.readTableScanValuesDialog();

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
