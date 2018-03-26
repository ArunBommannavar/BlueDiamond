package bluediamond2;

import java.util.ArrayList;
import java.util.List;

import edu.ciw.hpcat.epics.data.CountDownConnection;

public class Scan1DetectorParms {
	CountDownConnection countDownConnection = CountDownConnection.getInstance();
	private int numberOfDetectors = 60;
	DetectorPV[] detPV = new DetectorPV[numberOfDetectors];
	DetectorNV[] detNV = new DetectorNV[numberOfDetectors];
	DetectorCV[] detCV = new DetectorCV[numberOfDetectors];
	DetectorDnnRA[] detDnnRA = new DetectorDnnRA[numberOfDetectors];

	private String scanPv;
 
	List<Integer> validDet = new ArrayList<Integer>();

	boolean initDet = false;

	public Scan1DetectorParms(String str) {
		scanPv = str;
	}

	public void createDetPVs() {
		int j;
		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detNV[i] = new DetectorNV(scanPv, j);
			detNV[i].createPV();
			detPV[i] = new DetectorPV(scanPv, j);
			detPV[i].createPV();
		}
		countDownConnection.pendIO();
		for (int i = 0; i < numberOfDetectors; i++) {
			j = i + 1;
			detCV[i] = new DetectorCV(scanPv, j);
			detCV[i].createPV();
			detDnnRA[i] = new DetectorDnnRA(scanPv, j);
			detDnnRA[i].createPV();
		}
		countDownConnection.pendIO();

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
		double d[] = detDnnRA[det].getDoubleArrayVal(nPoints);
		return d;	
	}	
}
