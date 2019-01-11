package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import gov.aps.jca.CAException;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class ScanMonitor implements PropertyChangeListener, Runnable {

	Context context;

	String scan1Prefix = "";
	String scan2Prefix = "";

	boolean scan1InProgress = false;
	boolean scan2InProgress = false;
	boolean hasScan1Parms = false;
//	boolean hasScan2Parms = false;

	ScanVAL scan1VALObj;
	ScanEXSC scan1EXSCObj;
	ScanDSTATE scan1DSTATEObj;
	ScanDATA scan1DATAObj;
	ScanBUSY scan1BUSYObj;

	ScanEXSC scan2EXSCObj;
	ScanBUSY scan2BUSYObj;

	volatile boolean scan1VALStatus = false;
	volatile boolean scan1EXSCStatus = false;
	volatile boolean scan2EXSCStatus = false;
	volatile boolean scan1DSTATEStatus = false;
	volatile boolean scan1DATAStatus = false;
	volatile boolean scan1BUSYStatus = false;

	boolean scan1AfterScanDataReady = false;

	volatile String scan1DSTATEValue = "";
	volatile String scan1DATAValue = "";

	NPTS scan1NPTSObj;
	NPTS scan2NPTSObj;

	CPT scan1CPTObj;
	CPT scan2CPTObj;

	SaveFileName saveFileNameObj;
	ScanStatusMessage scanStatusMessageObj;

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
	
	Active_1D_ScanPanel active_1D_ScanPanel;
	Active_2D_ScanPanel active_2D_ScanPanel;
	
	public ScanMonitor(String s1, String s2,Context context) {
		this.context = context;
		this.scan1Prefix = s1;
		this.scan2Prefix = s2;
	}

	public void createScanPVS() {
		scan1CPTObj = new CPT(scan1Prefix + ".CPT",context);
		scan2CPTObj = new CPT(scan2Prefix + ".CPT",context);

		scan1NPTSObj = new NPTS(scan1Prefix + ".NPTS",context);
		scan2NPTSObj = new NPTS(scan2Prefix + ".NPTS",context);
		saveFileNameObj = new SaveFileName(scan1Prefix,context);
		scanStatusMessageObj = new ScanStatusMessage(scan1Prefix+".SMSG",context);

		scan1VALObj = new ScanVAL(scan1Prefix + ".VAL",context);
		scan1EXSCObj = new ScanEXSC(scan1Prefix + ".EXSC",context);
		scan1DSTATEObj = new ScanDSTATE(scan1Prefix + ".DSTATE",context);
		scan1DATAObj = new ScanDATA(scan1Prefix + ".DATA",context);
		scan1BUSYObj = new ScanBUSY(scan1Prefix + ".BUSY",context);

		scan2EXSCObj = new ScanEXSC(scan2Prefix + ".EXSC",context);
		scan2BUSYObj = new ScanBUSY(scan2Prefix + ".BUSY",context);

		scan1CPTObj.createChannel();
		scan2CPTObj.createChannel();
		scan1NPTSObj.createChannel();
		scan2NPTSObj.createChannel();
		saveFileNameObj.createChannel();
		scanStatusMessageObj.createChannel();
		scan1BUSYObj.createChannel();
		scan1VALObj.createChannel();
		scan1EXSCObj.createChannel();;
		scan2EXSCObj.createChannel();
		scan2BUSYObj.createChannel();

		try {
			context.pendIO(3.0);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		saveFileNameObj.addPropertyChangeListener("FileName", this);
		scanStatusMessageObj.addPropertyChangeListener("ScanMessage", this);
		scan1BUSYObj.addPropertyChangeListener("SCAN1BUSY", this);
		scan1VALObj.addPropertyChangeListener("VAL", this);
		scan1EXSCObj.addPropertyChangeListener("SCAN1EXSC", this);
		scan2EXSCObj.addPropertyChangeListener("SCAN2EXSC", this);
		scan2BUSYObj.addPropertyChangeListener("SCAN2BUSY", this);
		
		scan1CPTObj.setMonitor();		
		scan2CPTObj.setMonitor();		
		scan1NPTSObj.setMonitor();		
		scan2NPTSObj.setMonitor();		
		saveFileNameObj.setMonitor();		
		scanStatusMessageObj.setMonitor();
		scan1BUSYObj.setMonitor();
		scan1VALObj.setMonitor();
		scan1EXSCObj.setMonitor();
		scan2EXSCObj.setMonitor();
		scan2BUSYObj.setMonitor();
		
		try {
			context.flushIO();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void createDSTATE() {
		scan1DSTATEObj.createChannel();
		try {
			context.pendIO(1.0);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan1DSTATEObj.channelLabels();
		try {
			context.pendIO(1.0);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan1DSTATEObj.setLabels();
		scan1DSTATEObj.addPropertyChangeListener("DSTATE", this);
		
		scan1DSTATEObj.setMonitor();
		try {
			context.flushIO();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createDATA() {
		scan1DATAObj.createChannel();
		try {
			context.pendIO(1.0);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan1DATAObj.addPropertyChangeListener("DATA", this);
		
		scan1DATAObj.setMonitor();
		try {
			context.flushIO();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectPVs() {
		
		
	}
	
	public void disconnectChannel() {
		scan1NPTSObj.disconnectChannel();
		scan2NPTSObj.disconnectChannel();
		scan1CPTObj.disconnectChannel();
		
		scan1EXSCObj.removePropertyChangeListener(this);
		scan1EXSCObj.disconnectChannel();
		
		scan1VALObj.removePropertyChangeListener(this);
		scan1VALObj.disconnectChannel();
		
		scan1DSTATEObj.removePropertyChangeListener(this);
		scan1DSTATEObj.disconnectChannel();
		
		scan1BUSYObj.removePropertyChangeListener(this);
		scan1BUSYObj.disconnectChannel();
		
		scan2BUSYObj.removePropertyChangeListener(this);
		scan2BUSYObj.disconnectChannel();
		
		scan2EXSCObj.removePropertyChangeListener(this);
		scan2EXSCObj.disconnectChannel();
		
		saveFileNameObj.removePropertyChangeListener(this);
		saveFileNameObj.disconnectChannel();
		
		scanStatusMessageObj.removePropertyChangeListener(this);
		scanStatusMessageObj.disconnectChannel();
		
		
		
	}

	public void setActive_1D_ScanPanel(Active_1D_ScanPanel active_1D_ScanPanel) {
		this.active_1D_ScanPanel = active_1D_ScanPanel;
	}
	public void setActive_2D_ScanPanel(Active_2D_ScanPanel active_2D_ScanPanel) {
		this.active_2D_ScanPanel = active_2D_ScanPanel;
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
	}

	private void setScan1EXSCStatus(boolean b) {
		scan1EXSCStatus = b;
	}

	private void setScan2EXSCStatus(boolean b) {
		scan2EXSCStatus = b;
	}

	private void setScan1DSTATEStatus(boolean b) {
		scan1DSTATEStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus & scan1BUSYStatus;
	}

	private void setScan1DATAStatus(boolean b) {
		scan1DATAStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus & scan1BUSYStatus;
	}
	
	private void setScan1BusyStatus(boolean b) {
		scan1BUSYStatus = b;
		scan1AfterScanDataReady = scan1DSTATEStatus & scan1DATAStatus & scan1BUSYStatus;
	}

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
//		System.out.println(" In doScan1EXSC hasScan1Parms = "+hasScan1Parms);
		setScan1EXSCStatus(false);
//		initPosDet1D();
		
		if(!hasScan1Parms) {
			initPosDet1D();
		}else {
			data1D.clearYValues();
			data1D.updateChartDisplay();
		}		
		
	}

	private void doScan2EXSC() {
		setScan2EXSCStatus(false);
		active_2D_ScanPanel.clear2dDetector();
		
		initPosDet2D();
		/*
		if(!hasScan2Parms) {
			initPosDet2D();
		}	
		*/
	}

	public void validate1DPositioners() {
		scan1PositionerParms.validatePositioners();
		scan1PositionerParms.findPositionerDescription();
	}

	public void validate2DPositioners() {
		scan2PositionerParms.validatePositioners();
		scan2PositionerParms.findPositionerDescription();
	}

	public void validateDets() {
		scan1DetectorParms.validateDetectors();
	}
	
	private void setHasScan1Parms(boolean b) {
		hasScan1Parms = b;
	}
	/*
	private void setHasScan2Parms(boolean b) {
		hasScan2Parms = b;
	}
	*/
	private void doScan1Val() {

		setScan1VALStatus(false);
		scan1CPT = scan1CPTObj.getValue();
//		System.out.println(" In doScan1Val scan1CPT = "+scan1CPT+"   scan1NumberOfPoints = "+scan1NumberOfPoints );

		validDet.forEach((n) -> {
			data1D.setDetectorValue(n, scan1CPT, scan1DetectorParms.getDetCV(n));
		});

		data1D.updateChartDisplay(scan1CPT, scan1NumberOfPoints);
		active_1D_ScanPanel.setHMarkers();

	}

	private void updateData1DAfterScan() {
		scan1AfterScanDataReady = false;
		scan1CPT = scan1CPTObj.getValue();
//		System.out.println(" updateData1DAfterScan CPT = "+scan1CPT);
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
			 * The data are loaded in xVals and yVals Now to determine the xaxis min and max
			 */
			data1D.setChartRawData();
			data1D.setXAxisScale();
			data1D.setYAxisScale();
			active_1D_ScanPanel.setMarkers();

			/*
			 * Now lets plot the data.
			 */
			data1D.updateChartDisplay(scan1CPT, scan1CPT);
			

			/**
			 * Lets handle the 2D scan case
			 */
			if (scan2InProgress) {

				scan2CPT = scan2CPTObj.getValue();
//				System.out.println(" CPT 2 = "+scan2CPT);

				if (scan2CPT == 1) {
					validPos1.forEach((n) -> {
						data2D.setXDataArray(n, scan1PositionerParms.getPosReadBackArray(n, scan1NumberOfPoints));
					});

				}
				validDet.forEach((n) -> {
					data2D.setDetectorDataArray(scan2CPT, n, scan1DetectorParms.getDnnArray(n, scan1NumberOfPoints));
				});
				data2D.plotData();
			}

			double d = data1D.getScanCenter();
			active_1D_ScanPanel.setScanCenterLabel(d);
			active_1D_ScanPanel.setMarkers();
			active_1D_ScanPanel.updateScanCenterDiff();
			active_1D_ScanPanel.updateUserAuto();
			
			String posName = active_1D_ScanPanel.getPositionerName(data1D.getSelectedPositioner());
		
			active_1D_ScanPanel.setXAxisTitle(posName);

		} else {

			active_1D_ScanPanel.showAlert(" Number of points is zero ");
			/*
			 * Post a alert message that CPT is zero.
			 */
		}
		scan1AfterScanDataReady = false;
	}

	public void setMainPanel_1D_PositionerNames() {

		validPos1.forEach((n) -> {
			active_1D_ScanPanel.setXPositionerVisible_1D(n, true);
//			final String str = scan1PositionerParms.getPosPnPV(n);
			final String str = scan1PositionerParms.getPositionerDescription(n);
//			System.out.println(" In ScanMonitor "+str);

			active_1D_ScanPanel.setXPositionerName_1D(n, str);
		});
	}

	public void setMainPanel_2D_X_PositionerNames() {

		validPos1.forEach((n) -> {
			active_2D_ScanPanel.setXPositionerVisible_2D(n, true);
			String posName_X = active_1D_ScanPanel.getPositionerName(n);
			active_2D_ScanPanel.setXPositionerName_2D(n,posName_X);
		});
	}

	public void setMainPanel_2D_Y_PositionerNames() {
		validPos2.forEach((n) -> {
			active_2D_ScanPanel.setYPositionerVisible_2D(n, true);
			final String str = scan2PositionerParms.getPosPnPV(n);
			active_2D_ScanPanel.setYPositionerName_2D(n, str);
		});
	}

	public void setMainPanel_1D_DetectorNames() {
		validDet.forEach((n) -> {
			active_1D_ScanPanel.setDetVisible(n, true);
			active_1D_ScanPanel.setDetEnable(n, true);
			final String str = scan1DetectorParms.getDetPV(n);
//			System.out.println(" In ScanMonitor setMainPanel_1D_DetectorNames "+n+"  "+str);
			active_1D_ScanPanel.setDetectorName(n, str);
		});
	}

	public void init1DPlot() {
		data1D.initDataArray();
		data1D.initChartData();
	}
	
	public void initPosDet1D() {
		
		scan1NumberOfPoints = data1D.getScan1NumberOfPoints();
		scan1NumberOfPoints = scan1NPTSObj.getVal();
		data1D.setNumberOfPoints(scan1NumberOfPoints);
		active_1D_ScanPanel.resetDetectors();
		active_1D_ScanPanel.resetPositioners_1D();

		init1DPlot();

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

		validPos1.forEach((n) -> {
			data1D.setXAxisRange(n);
		});

		data1D.setXAxisScale();
		data1D.setYAxisScale();
		active_1D_ScanPanel.setMarkers();
		active_1D_ScanPanel.updateVMarkers();
		active_1D_ScanPanel.updateHMarkers();
		active_1D_ScanPanel.updateUserAuto();
		setScan1InProgress(true);
		setHasScan1Parms(true);

	}

	public void initPosDet2D() {
		active_2D_ScanPanel.resetPositioners_2D();
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
			final String str = active_1D_ScanPanel.getDetectorName(n);
			active_2D_ScanPanel.add2DDetector(str);
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

		validPos1.forEach((n) -> {
			data2D.setXAxisRange(n);
		});

		validPos2.forEach((n) -> {
			data2D.setYAxisRange(n);
		});

		data2D.setScanGridRange();
		active_2D_ScanPanel.setSelectedX_Positioner(0);
		active_2D_ScanPanel.setSelectedY_Positioner(0);
//		setHasScan2Parms(true);
	}

	synchronized public void propertyChange(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		Object evtObj = evt.getNewValue();
		String srcString = String.valueOf(evtObj);
		
//		System.out.println(" PropertyChange  "+propertyName+"  "+srcString+"  "+System.currentTimeMillis());
		if (propertyName.equals("VAL")) {
			setScan1VALStatus(true & scan1InProgress);

		} else if (propertyName.equals("SCAN1EXSC")) {
			if (srcString.equals("1")) {
//				 setScan1EXSCStatus(true & !scan2InProgress);
				active_1D_ScanPanel.setMoveButtonsEnable(false);
				setScan1EXSCStatus(true);
			} else if (srcString.equals("0")) {
				if(!scan2InProgress) {
					setScan1InProgress(false);
					setScan1EXSCStatus(false);
					setHasScan1Parms(false);
				}
				active_1D_ScanPanel.setMoveButtonsEnable(true);

			}
		} else if (propertyName.equals("DSTATE")) {
			if (srcString.equals("POSTED")) {
				setScan1InProgress(false);
				setScan1VALStatus(false);
				setScan1DSTATEStatus(true);
			}

		} else if (propertyName.equals("DATA")) {

			if (srcString.equals("1")) {
				setScan1VALStatus(false);
				setScan1DATAStatus(true);
			}

		} else if (propertyName.equals("SCAN2EXSC")) {
			if (srcString.equals("1")) {
				active_2D_ScanPanel.setBtnMoveButton2DEnable(false);
				setScan2EXSCStatus(true);
			} else if (srcString.equals("0")) {
				setScan2EXSCStatus(false);
//				setHasScan2Parms(false);
			}

		} else if(propertyName.equals("SCAN2BUSY")) {
			if (srcString.equals("1")) {
			setScan2InProgress(true);
			}
			if (srcString.equals("0")) {
			setScan2InProgress(false);
			setHasScan1Parms(false);
			
			}
		}
		else if(propertyName.equals("SCAN1BUSY")) {
			if (srcString.equals("1")) {
			setScan1InProgress(true);
			setScan1BusyStatus(false);
			}
			if (srcString.equals("0")) {
				if(!scan2InProgress) {
					setScan1InProgress(false);
				}
				setScan1BusyStatus(true);
			}
		}		
		
		else if (propertyName.equals("FileName")) {
			active_1D_ScanPanel.setChartHeader(srcString);
			active_2D_ScanPanel.setChartHeader(srcString);
		}
		
		else if(propertyName.equals("ScanMessage")) {
			active_1D_ScanPanel.setScanStatus(srcString);
			if(!scan2InProgress) {
				
			}
		}
	}

	public void run() {
		while (true) {
			if (scan2EXSCStatus) {
//				System.out.println(" In run doScan2EXSC ");
				doScan2EXSC();

			} else if (scan1EXSCStatus) {
				// if (!scan2InProgress) {
				/*
				 * There is no 2-D scan going on. Get Scan1 NPTS, Scan1 Positioners and valid
				 * Pos. Display and enable those positioners in X-Positioners Get those
				 * positioner's start and width value Tell the plot about the X-scale
				 * positioners range.
				 */
//				System.out.println(" In run doScan1EXSC ");

				doScan1EXSC();
				// }else{
				// setScan1EXSCStatus(false);
				// }

			} else if (scan1VALStatus) {
				/*
				 * A new data point is available. Check for scan1 in progress. If the scan1 is
				 * in progress then read new value for each detectors and tell the plot
				 */
				doScan1Val();
			}

			else if (scan1AfterScanDataReady) {

				updateData1DAfterScan();
			}

		}

	}

}
