package bluediamond2;

import java.util.ArrayList;
import java.util.List;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import gov.aps.jca.Context;

public class Scan2PositionerParms {
	
	Context context;
	
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	private int numberOfPositioners = 4;
	private PositionerPnPV[] posPnPV = new PositionerPnPV[numberOfPositioners];
	private PositionerPnPA[] posPnPA = new PositionerPnPA[numberOfPositioners];
	private PositionerPnPP[] posPnPP = new PositionerPnPP[numberOfPositioners];
	private PositionerNV[] posNV = new PositionerNV[numberOfPositioners];
	private PositionerMin[] posMin = new PositionerMin[numberOfPositioners];
	private PositionerWidth[] posWidth = new PositionerWidth[numberOfPositioners];
	private PositionerRelAbs[] posRelAbs = new PositionerRelAbs[numberOfPositioners];
	private PositionerScanMode[] posScanMode = new PositionerScanMode[numberOfPositioners];	
	private PositionerPnRA[] posPnRA = new PositionerPnRA[numberOfPositioners];

	private String scanPv;

	List<Integer> validPos = new ArrayList<Integer>();
	boolean initPos = false;
	
	public Scan2PositionerParms(String str,Context context) {
		this.context = context;
		this.scanPv = str;
	}
	public void createPosPVs() {
				
		int j = 0;		

		for (int i = 0; i < numberOfPositioners; i++) {
			j= i + 1;
			posNV[i] = new PositionerNV(scanPv, j,context);					
			posNV[i].createPV();
			
			posMin[i] = new PositionerMin(scanPv, j,context);	
			posMin[i].createPV();
			
			posWidth[i] = new PositionerWidth(scanPv,j,context);
			posWidth[i].createPV();
			
			posRelAbs[i] = new PositionerRelAbs(scanPv, j,context);	
			posRelAbs[i].createPV();
			
			posPnPV[i] = new PositionerPnPV(scanPv, j,context);	
			posPnPV[i].createPV();
			
			posPnPA[i] = new PositionerPnPA(scanPv,j,context);
			posPnPA[i].createPV();				

			posPnPP[i] = new PositionerPnPP(scanPv, j,context);
			posPnPP[i].createPV();
			
			posScanMode[i] = new PositionerScanMode(scanPv, j,context);	
			posScanMode[i].createPV();
			
			posPnRA[i] = new PositionerPnRA(scanPv, j,context);
			posPnRA[i].createPV();
		}
	}
	
	   public void disconnectChannel() {
		   
	    	if (initPos){		
			for (int i = 0; i < numberOfPositioners; i++) {
						
				posNV[i].disconnectChannel();	
				posMin[i].disconnectChannel();
				posWidth[i].disconnectChannel();;
				posRelAbs[i].disconnectChannel();
				posPnPV[i].disconnectChannel();
				posPnPP[i].disconnectChannel();
				posPnPA[i].disconnectChannel();
				posScanMode[i].disconnectChannel();
				posPnRA[i].disconnectChannel();
				}
	    	}
	   }
	   
	public void validatePositioners() {
		boolean b = false;
		validPos.clear();
		for (int i = 0; i < numberOfPositioners; i++) {
			b = false;
			b = posNV[i].isValid();
			if (b) {
				validPos.add(i);
				}
		}
		initPos = true;
	}
	
	public List<Integer> getValidPos(){
		return validPos;
	}

	public void setInitPos(boolean b){
		initPos = b;
	}
	public boolean getInitPos(){
		return initPos;
	}
	
	public String getPosScanMode(int n){
		String scanMode =posScanMode[n].getVal();		
		return scanMode;
	}
	public String getPosPnPV(int n){ 
		return posPnPV[n].getVal();
	}
	public String getPosRelAbs(int n){
		return posRelAbs[n].getVal();
	}
	public double getPosMin(int n){
		return posMin[n].getDoubleVal();
	}
	public double getPosPP(int n){
		return posPnPP[n].getDoubleVal();
	}
	
	public double getPosWidth(int n){
		return posWidth[n].getVal();
	}
	public double[] getPosPA(int pos, int n){
		return posPnPA[pos].getArrayValues(n);
	}

	public double[] getPosReadBackArray(int n){
		return posPnRA[n].getArrayValues();
	}
	public double[] getPosReadBackArray(int n, int m){
		return posPnRA[n].getArrayValues(m);
	}
	public PositionerPnPV[] getPosPnPV(){
		return posPnPV;
	}
}
