package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ciw.hpcat.epics.data.CountDownConnection;

public class ScanMonitor implements PropertyChangeListener, Runnable {

	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	String scan1Prefix = "";
	String scan2Prefix = "";

	boolean scan1InProgress = false;
	boolean scan2InProgress = false;
	
	ScanVAL scan1VALObj;
	ScanEXSC scan1EXSCObj;
	ScanDSTATE scan1DSTATEObj;
	ScanEXSC scan2EXSCObj;
	ScanDATA scan1DATAObj;
//	ScanBUSY scan1BUSYObj;


	volatile boolean scan1VALStatus = false;
	volatile boolean scan1EXSCStatus = false;
	volatile boolean scan2EXSCStatus = false;
	volatile boolean scan1DSTATEStatus = false;
	volatile boolean scan1DATAStatus = false;
//	volatile boolean scan1BUSYStatus = false;
	
	 boolean scan1AfterScanDataReady = false;

	volatile String scan1DSTATEValue = "";
	volatile String scan1DATAValue = "";
//	volatile String scan1BUSYValue = "";

	NPTS scan1NPTSObj;
	NPTS scan2NPTSObj;

	CPT scan1CPTObj;
	CPT scan2CPTObj;

	SaveFileName saveFileNameObj;
	
	int scan1NumberOfPoints = 0;
	int scan2NumberOfPoints = 0;

	 int scan1CPT = 0;
	 int scan2CPT = 0;

	Scan1PositionerParms scan1PositionerParms;
	Scan1DetectorParms scan1DetectorParms;
	Scan2PositionerParms scan2PositionerParms;
	Data1D data1D;
	Data2D data2D;
	List<Integer> validPos1 = new ArrayList<Integer>();
	List<Integer> validPos2 = new ArrayList<Integer>();
	List<Integer> validDet = new ArrayList<Integer>();
	MainPanel_1 mainPanel;

	public ScanMonitor(String s1, String s2) {
		this.scan1Prefix = s1;
		this.scan2Prefix = s2;

	}

	public void createScanPVS() {
		scan1CPTObj = new CPT(scan1Prefix + ".CPT");
		scan2CPTObj = new CPT(scan2Prefix + ".CPT");
		
		scan1NPTSObj = new NPTS(scan1Prefix + ".NPTS");
		scan2NPTSObj = new NPTS(scan2Prefix + ".NPTS");
		saveFileNameObj = new SaveFileName(scan1Prefix);
		
		scan1VALObj = new ScanVAL(scan1Prefix + ".VAL");
		scan1EXSCObj = new ScanEXSC(scan1Prefix + ".EXSC");
		scan1DSTATEObj = new ScanDSTATE(scan1Prefix + ".DSTATE");
		scan2EXSCObj = new ScanEXSC(scan2Prefix + ".EXSC");
		scan1DATAObj = new ScanDATA(scan1Prefix+".DATA");
//		scan1BUSYObj = new ScanBUSY(scan1Prefix+".BUSY");

		scan1CPTObj.createPV();
		scan2CPTObj.createPV();
		scan1NPTSObj.createPV();
		scan2NPTSObj.createPV();
		saveFileNameObj.createPV();
		saveFileNameObj.addPropertyChangeListener("FileName", this);
		
		scan1VALObj.createPV();
		scan1VALObj.addPropertyChangeListener("VAL", this);
		
		scan1EXSCObj.createPV();		
		scan1EXSCObj.addPropertyChangeListener("SCAN1EXSC", this);
		
		scan2EXSCObj.createPV();
		scan2EXSCObj.addPropertyChangeListener("SCAN2EXSC", this);
//		scan1BUSYObj.createPV();
//		scan1BUSYObj.addPropertyChangeListener("BUSY", this);

	}

	public void createDSTATE() {
		scan1DSTATEObj.createPV();
		scan1DSTATEObj.addPropertyChangeListener("DSTATE", this);
	}

	public void createDATA(){
		scan1DATAObj.createPV();
		scan1DATAObj.addPropertyChangeListener("DATA", this);
	}
	public void disconnectChannel() {
		scan1NPTSObj.disconnectChannel();
		scan2NPTSObj.disconnectChannel();
		scan1CPTObj.disconnectChannel();
		scan1EXSCObj.disconnectChannel();
		scan1VALObj.disconnectChannel();
		scan1DSTATEObj.disconnectChannel();
		scan2EXSCObj.disconnectChannel();
		saveFileNameObj.disconnectChannel();
	}

	public void setMainPanel(MainPanel_1 m) {
		this.mainPanel = m;
	}

	public void setScan1PositionerParms(Scan1PositionerParms m) {
		scan1PositionerParms = m;
	}

	public void setScan1DetectorParms(Scan1DetectorParms m) {
		scan1DetectorParms = m;
	}

	public void setScan2PositionerParms(Scan2PositionerParms m) {
		scan2PositionerParms = m;
	}

	public void setData2D(Data2D d2d) {
		data2D = d2d;
	}

	public void setData1D(Data1D d1d) {
		data1D = d1d;
	}

	private void setScan1InProgress(boolean b) {
		scan1InProgress = b;
	}

	private void setScan2InProgress(boolean b) {
		scan2InProgress = b;
	}

	private void setScan1VALStatus(boolean b) {
		scan1VALStatus = b;
//		System.out.println(" scan1VALStatus = "+scan1VALStatus);
	}

	private void setScan1EXSCStatus(boolean b) {
		scan1EXSCStatus = b;
	}

	private void setScan2EXSCStatus(boolean b) {
		scan2EXSCStatus = b;
	}

	private void setScan1DSTATEStatus(boolean b)	{
		scan1DSTATEStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus;// & scan1BUSYStatus;
	}
	private void setScan1DATAStatus(boolean b)	{
		scan1DATAStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus;// & scan1BUSYStatus;

	}
	/*
	private void setScan1BUSYStatus(boolean b)	{
		scan1BUSYStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus;
	}
*/
	private void setScan1DSTATEValue(String str) {
		scan1DSTATEValue = str;
	}
	
	private void setDATAValue(String str){
		scan1DATAValue = str;
	}
	
	/*
	private void setBUSYValue(String str){
		scan1BUSYValue = str;
	}
	*/
	public void getScan1ValidDet() {
		validDet = scan1DetectorParms.getValidDet();

	}

	public void getScan1ValidPos() {
		validPos1 = scan1PositionerParms.getValidPos();

	}

	public void getScan2ValidPos() {
		validPos2 = scan2PositionerParms.getValidPos();

	}

	private void doScan1EXSC() {
		setScan1EXSCStatus(false);
		initPosDet1D();
	}
	private void doScan2EXSC() {
		setScan2EXSCStatus(false);
		mainPanel.clear2dDetector();
		/*
		 * Note down which positioners are valid and fill up the arraylist.
		 */		
//		scan2PositionerParms.validatePositioners();
		initPosDet2D();

	}

	public void validate1DPositioners(){
		scan1PositionerParms.validatePositioners();
	}
	
	public void validate2DPositioners(){
		scan2PositionerParms.validatePositioners();
	}
	
	public void validateDets() {
		scan1DetectorParms.validateDetectors();
	}

	private void doScan1Val(){		
	
		setScan1VALStatus(false);
		scan1CPT = scan1CPTObj.getValue();
//		System.out.println(" VAL Arrived "+scan1CPT);

		validDet.forEach((n) -> {
			data1D.setDetectorValue(n, scan1CPT, scan1DetectorParms.getDetCV(n));
		});		

		data1D.updateChartDisplay(scan1CPT, scan1NumberOfPoints);

	}
	
	private void updateData1DAfterScan(){	
//		System.out.println("In updateData1DAfterScan  ");
		/*
		 * Get current number of points
		 */
		scan1AfterScanDataReady = false;

		scan1CPT = scan1CPTObj.getValue();
//		System.out.println("scan1CPT = "+scan1CPT);
		if (scan1CPT > 0) {
			data1D.setNumberOfPoints(scan1CPT);
			data1D.setCurrentPoint(scan1CPT);
			data1D.initDataArray();
			data1D.initChartData();

			validPos1.forEach((n) -> {
				data1D.setPositionerDataArray(n, scan1PositionerParms.getPosReadBackArray(n, scan1CPT));
			});

			validDet.forEach((n) -> {
				data1D.setDetectorDataArray(n, scan1DetectorParms.getDnnArray(n, scan1CPT));
			});
			
			/*
			 * The data are loaded in xVals and yVals
			 * Now to determine the xaxis min and max
			 */
			data1D.setXAxisScale();
			data1D.setYAxisScale();

			/*
			 * Now lets plot the data.
			 */
			data1D.updateChartDisplay(scan1CPT, scan1CPT);
			
			/**
			 * Lets handle the 2D scan case
			 */
			if(scan2InProgress){
//				System.out.println("   Scan2 CPT = "+scan2CPT);

				scan2CPT = scan2CPTObj.getValue();
				if(scan2CPT==0){
					validPos1.forEach((n) -> {
						data2D.setXDataArray(n, scan1PositionerParms.getPosReadBackArray(n, scan1NumberOfPoints));
					});

				}
				validDet.forEach((n) -> {
//					System.out.println(" 2D det ="+n+"   Scan2 CPT = "+scan2CPT);
					data2D.setDetectorDataArray(scan2CPT,n, scan1DetectorParms.getDnnArray(n, scan1NumberOfPoints));
				});
				data2D.plotData();
//				data1D.initDataArray();
//				data1D.initChartData();
//				data1D.setXAxisScale();
//				data1D.setYAxisScale();

				
			}
			
			double d= data1D.getScanCenter();
			mainPanel.setScanCenterLabel(d);
			mainPanel.setMarkers("dstate");
			mainPanel.updateScanCenterDiff();
			mainPanel.updateUserAuto();
			String posName = mainPanel.getPositionerName(data1D.getSelectedPositioner());
			mainPanel.setXAxisTitle(posName);


		} else {
			System.out.println(" CPT = 0");
			
			mainPanel.showAlert(" Number of points is zero ");
			/*
			 * Post a alert message that CPT is zero.
			 */
		}
		scan1AfterScanDataReady = false;
	}
	
	
	public void setMainPanel_1D_PositionerNames(){
//		System.out.println("In setMainPanel_1D_PositionerNames  ");

		validPos1.forEach((n) -> {
			mainPanel.setXPositionerVisible_1D(n, true);
			final String str = scan1PositionerParms.getPosPnPV(n);
			mainPanel.setXPositionerName_1D(n, str);
		});
	}
	
	public void setMainPanel_2D_X_PositionerNames(){
//		System.out.println("In setMainPanel_2D_PositionerNames  ");

		validPos1.forEach((n) -> {
			mainPanel.setXPositionerVisible_2D(n, true);
			mainPanel.setXPositionerName_2D(n);
		});
	}
	
	public void setMainPanel_2D_Y_PositionerNames(){
		validPos2.forEach((n) -> {
			mainPanel.setYPositionerVisible_2D(n, true);
			final String str = scan2PositionerParms.getPosPnPV(n);
			mainPanel.setYPositionerName_2D(n, str);			
		});

	}
	
	public void setMainPanel_1D_DetectorNames(){
		validDet.forEach((n) -> {
			mainPanel.setDetVisible(n, true);
			mainPanel.setDetEnable(n, true);
			final String str = scan1DetectorParms.getDetPV(n);
			mainPanel.setDetectorName(n, str);
		});
	}	
	
		public void initPosDet1D() {		
//		System.out.println("In initPosDet1D  ");

		scan1NumberOfPoints = data1D.getScan1NumberOfPoints();
		scan1NumberOfPoints = scan1NPTSObj.getVal();
		data1D.setNumberOfPoints(scan1NumberOfPoints);
		mainPanel.resetDetectors();
		mainPanel.resetPositioners_1D();
		
		data1D.initDataArray();
		data1D.initChartData();

		validate1DPositioners();
		validateDets();
		
		getScan1ValidPos();
		getScan1ValidDet();		
		
		setMainPanel_1D_PositionerNames();		
		setMainPanel_1D_DetectorNames();

		// Linear/Table/Fly mode
		validPos1.forEach((n) -> {
			final String str = scan1PositionerParms.getPosScanMode(n);
			data1D.setScanMode(n, str);
			if (str.equals("TABLE")) {
				final double[] d = scan1PositionerParms.getPosPA(n, scan1NumberOfPoints);
				data1D.setPosPA(n, d);
			}
		});

		// Relative/Absolute scan

		validPos1.forEach((n) -> {
			final String str = scan1PositionerParms.getPosRelAbs(n);
			data1D.setPosRelAbs(n, str);
		});

		// Positioner Min Value

		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosMin(n);
			data1D.setPosMin(n, d);
		});

		// Positioner Scan Width
		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosWidth(n);
			data1D.setPosWidth(n, d);
		});

		// Positioner current position (Before Scan)

		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosPP(n);
			data1D.setPosPP(n, d);
		});

		validPos1.forEach((n)->{
			data1D.setXAxisRange(n);		
		});
		
		data1D.setXAxisScale();
		data1D.setYAxisScale();
		mainPanel.setMarkers("initPosDet1D");
		mainPanel.updateVMarkers();
		mainPanel.updateHMarkers();
		mainPanel.updateUserAuto();		
		setScan1InProgress(true);

	}
	
	public void initPosDet2D() {
//		System.out.println("In initPosDet2D  ");
		mainPanel.resetPositioners_2D();
		initPosDet1D();
		
		scan2NumberOfPoints = scan2NPTSObj.getVal();
		scan2PositionerParms.validatePositioners();
		getScan2ValidPos();	
		
		setMainPanel_2D_X_PositionerNames();
		setMainPanel_2D_Y_PositionerNames();
		
		data2D.setXNumPoints(scan1NumberOfPoints);
		data2D.setYNumPoints(scan2NumberOfPoints);
		data2D.initDataArray(scan1NumberOfPoints, scan2NumberOfPoints, 4, 4, 60);		
		
		validDet.forEach((n) -> {
			final String str = mainPanel.getDetectorName(n);
			mainPanel.add2DDetector(str);
		});

		// Linear/Table/Fly mode
		validPos1.forEach((n) -> {
			final String str = scan1PositionerParms.getPosScanMode(n);
			data2D.setScanMode_2DX(n, str);
			if (str.equals("TABLE")) {
				final double[] d = scan1PositionerParms.getPosPA(n, scan1NumberOfPoints);
				data2D.setPosPA_2DX(n, d);
			}
		});

		validPos2.forEach((n) -> {
			final String str = scan2PositionerParms.getPosScanMode(n);
			data2D.setScanMode_2DY(n, str);
			if (str.equals("TABLE")) {
				final double[] d = scan2PositionerParms.getPosPA(n, scan2NumberOfPoints);
				data2D.setPosPA_2DY(n, d);
			}
		});
		
		// Relative/Absolute scan

		validPos1.forEach((n) -> {
			final String str = scan1PositionerParms.getPosRelAbs(n);
			data2D.setPosRelAbs_2DX(n, str);
		});

		validPos2.forEach((n) -> {
			final String str = scan2PositionerParms.getPosRelAbs(n);
			data2D.setPosRelAbs_2DY(n, str);
		});

		// Positioner Min Value

		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosMin(n);
			data2D.setPosMin_2DX(n, d);
		});
		
		validPos2.forEach((n) -> {
			final double d = scan2PositionerParms.getPosMin(n);
			data2D.setPosMin_2DY(n, d);
		});

		// Positioner Scan Width
		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosWidth(n);
			data2D.setPosWidth_2DX(n, d);
		});

		validPos2.forEach((n) -> {
			final double d = scan2PositionerParms.getPosWidth(n);
			data2D.setPosWidth_2DY(n, d);
		});

		// Positioner current position (Before Scan)

		validPos1.forEach((n) -> {
			final double d = scan1PositionerParms.getPosPP(n);
			data2D.setPosPP_2DX(n, d);
		});
		
		validPos2.forEach((n) -> {
			final double d = scan2PositionerParms.getPosPP(n);
			data2D.setPosPP_2DY(n, d);
		});
		
		validPos1.forEach((n)->{
			data2D.setXAxisRange(n);		
		});
		
		validPos2.forEach((n)->{
			data2D.setYAxisRange(n);		
		});		
		
		data2D.setScanGridRange();
	}
	
	synchronized public void propertyChange(PropertyChangeEvent evt) {

		Object source = evt.getSource();
		String propertyName = evt.getPropertyName();
		Object evtObj = evt.getNewValue();
		String srcString = String.valueOf(evtObj);

//		System.out.println(" PropertyName = "+propertyName+"  srcString = "+srcString);
		if (propertyName.equals("VAL")) {
//			System.out.println("scan1InProgress = "+scan1InProgress);
			setScan1VALStatus(true &scan1InProgress);

		} else if (propertyName.equals("SCAN1EXSC")) {
			if (srcString.equals("1")) {
//				setScan1EXSCStatus(true & !scan2InProgress);
				setScan1EXSCStatus(true);
				mainPanel.setScanStatus("in progress");
			}
			else if(srcString.equals("0")) {
				mainPanel.setScanStatus("Done");
				setScan1InProgress(false);
				setScan1EXSCStatus(false);
			}
		} else if (propertyName.equals("DSTATE")) {
			if(srcString.equals("POSTED")){
				setScan1VALStatus(false);
				setScan1DSTATEStatus(true);
			}
			
		}else if(propertyName.equals("DATA")){
			if(srcString.equals("1")){
				setScan1VALStatus(false);
				setScan1DATAStatus(true);
			}
			
		} 
		else if (propertyName.equals("SCAN2EXSC")) {
//			System.out.println(" Before SCAN2EXSC ="+scan2EXSCStatus+"  Value = "+srcString);
			if (srcString.equals("1")) {
				setScan2InProgress(true);
				setScan2EXSCStatus(true);
//				mainPanel.setScanStatus("in progress");
			}else if(srcString.equals("0")){
				setScan2InProgress(false);
				setScan2EXSCStatus(false);
				}
//			System.out.println(" After SCAN2EXSC ="+scan2EXSCStatus+"  Value = "+srcString);

		}else if(propertyName.equals("FileName")){
			mainPanel.setFileName(srcString);
		}
	}

	public void run() {
		while (true) {
			if (scan2EXSCStatus) {
				doScan2EXSC();

			} else if (scan1EXSCStatus) {
//				if (!scan2InProgress) {
					/*
					 * There is no 2-D scan going on. Get Scan1 NPTS, Scan1
					 * Positioners and valid Pos. Display and enable those
					 * positioners in X-Positioners Get those positioner's start
					 * and width value Tell the plot about the X-scale
					 * positioners range.
					 */
					doScan1EXSC();
//				}else{
//					setScan1EXSCStatus(false);
//				}

			} else if (scan1VALStatus) {
				/*
				 * A new data point is available. Check for scan1 in progress.
				 * If the scan1 is in progress then read new value for each
				 * detectors and tell the plot
				 */
				doScan1Val();
			} 
			
			else if (scan1AfterScanDataReady) {
//				scan1AfterScanDataReady = false;

				updateData1DAfterScan();
				scan1AfterScanDataReady = false;
			}
			
		}

	}

}
