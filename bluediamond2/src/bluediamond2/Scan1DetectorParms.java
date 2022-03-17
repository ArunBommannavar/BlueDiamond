package bluediamond2;

import java.util.ArrayList;
import java.util.List;

import gov.aps.jca.CAException;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class Scan1DetectorParms {
	
	Context context;
	private int numberOfDetectors = 60;
	DetectorPV[] detPV = new DetectorPV[numberOfDetectors];
	DetectorNV[] detNV = new DetectorNV[numberOfDetectors];
	DetectorCV[] detCV = new DetectorCV[numberOfDetectors];
	DetectorDnnRA[] detDnnRA = new DetectorDnnRA[numberOfDetectors];

	private String[] detectorDescription = new String[numberOfDetectors];

	private String scanPv;
 
	List<Integer> validDet = new ArrayList<Integer>();

	boolean initDet = false;

	public Scan1DetectorParms(String str,Context context) {
		this.context = context;
		scanPv = str;
	}

	
	public void createDetPVs() {
//		System.out.println("Before create Time = "+System.currentTimeMillis());

		int j;
		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detNV[i] = new DetectorNV(scanPv, j,context);
			detNV[i].createChannel();
			
			detPV[i] = new DetectorPV(scanPv, j,context);
			detPV[i].createChannel();
			
			detCV[i] = new DetectorCV(scanPv, j,context);
			detCV[i].createChannel();
			
			detDnnRA[i] = new DetectorDnnRA(scanPv, j,context);
			detDnnRA[i].createChannel();
		}	
		
		try {
			context.pendIO(5.0);
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
		
//		System.out.println("After create Time = "+System.currentTimeMillis());
		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detNV[i].channelLabels();			
		}		

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
		
		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detNV[i].setLabels();			
		}		

		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detNV[i].setMonitor();
			detPV[i].setMonitor();
			detCV[i].setMonitor();
			detDnnRA[i].setMonitor();			
		}
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
	
	
	public void disconnectChannel() {
		if (initDet) {

			for (int i = 0; i < numberOfDetectors; i++) {
				detNV[i].disconnectChannel();
				detPV[i].disconnectChannel();
				detCV[i].disconnectChannel();
				detDnnRA[i].disconnectChannel();
			}
		}
	}

	public void validateDetectors() {
		boolean b = false;
		validDet.clear();
		for (int i = 0; i < numberOfDetectors; i++) {
			b = false;
			b = detNV[i].isValid();
			if (b) {
				validDet.add(i);
			}
		}
		
		initDet = true;
	}

	public void findDetectorDescription() {
		validDet.forEach((n) -> {
			detPV[n].findDetectorPVdescription();
			detectorDescription[n] = detPV[n].getDetectorDescription();
		});
	}

	public String getDetectorDescription(int n) {
		return detectorDescription[n];
	}
	
	public List<Integer> getValidDet(){
		return validDet;
	}
	
	public void setInitDet(boolean b){
		initDet = b;
	}
	
	public boolean getInitDet(){
		return initDet;
	}
	public String getDetPV(int n){ 
		return detPV[n].getVal();
	}

	public double getDetCV(int det){
		double d = detCV[det].getDoubleVal();
		return d;
	}
	public double[] getDnnArray(int det, int nPoints){
		double d[] = detDnnRA[det].getArrayValues(nPoints);
		return d;	
	}	
}
