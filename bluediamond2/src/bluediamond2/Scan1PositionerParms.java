package bluediamond2;

import java.util.ArrayList;
import java.util.List;

import edu.ciw.hpcat.epics.data.CountDownConnection;

public class Scan1PositionerParms {
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	private String scanPv;
	private int numberOfPositioners = 4;

	private PositionerPnPV[] posPnPV = new PositionerPnPV[numberOfPositioners];
	private PositionerPnPA[] posPnPA = new PositionerPnPA[numberOfPositioners];
	private PositionerPnPP[] posPnPP = new PositionerPnPP[numberOfPositioners];
	private PositionerNV[] posNV = new PositionerNV[numberOfPositioners];
	private PositionerCV[] posCV = new PositionerCV[numberOfPositioners];
	private PositionerMin[] posMin = new PositionerMin[numberOfPositioners];
	private PositionerWidth[] posWidth = new PositionerWidth[numberOfPositioners];
	private PositionerRelAbs[] posRelAbs = new PositionerRelAbs[numberOfPositioners];
	private PositionerScanMode[] posScanMode = new PositionerScanMode[numberOfPositioners];	
	private PositionerPnRA[] posPnRA = new PositionerPnRA[numberOfPositioners];	
	private PositionerDesc[] posDesc = new PositionerDesc[numberOfPositioners];
	
	private String[] pnpv_values = new String[numberOfPositioners];
	
	List<Integer> validPos = new ArrayList<Integer>();
	boolean initPos = false;
	Data1D data1D;

	public Scan1PositionerParms(String str) {
		this.scanPv = str;
	}
	public void createPosPVs() {

		int j;		
		for (int i = 0; i < numberOfPositioners; i++) {
			j= i + 1;
			posNV[i] = new PositionerNV(scanPv, j);					
			posNV[i].createPV();
			
			posMin[i] = new PositionerMin(scanPv, j);	
			posMin[i].createPV();
			
			posWidth[i] = new PositionerWidth(scanPv,j);
			posWidth[i].createPV();
			
			posRelAbs[i] = new PositionerRelAbs(scanPv, j);	
			posRelAbs[i].createPV();
			
			posPnPV[i] = new PositionerPnPV(scanPv, j);	
			posPnPV[i].createPV();

			posPnPP[i] = new PositionerPnPP(scanPv, j);
			posPnPP[i].createPV();
			
			posPnPA[i] = new PositionerPnPA(scanPv,j);
			posPnPA[i].createPV();	
			
			posCV[i] = new PositionerCV(scanPv, j);
			posCV[i].createPV();
			
			posScanMode[i] = new PositionerScanMode(scanPv, j);	
			posScanMode[i].createPV();
			
			posPnRA[i] = new PositionerPnRA(scanPv, j);
			posPnRA[i].createPV();
		}
	}
	
	   public void disconnectChannel() {
		   /*
			scanNPTSObj.disconnectChannel();
			scanCPTObj.disconnectChannel();
*/
	    	if (initPos){		
			for (int i = 0; i < numberOfPositioners; i++) {
						
				posNV[i].disconnectChannel();	
				posMin[i].disconnectChannel();
				posWidth[i].disconnectChannel();;
				posRelAbs[i].disconnectChannel();
				posPnPV[i].disconnectChannel();
				posPnPP[i].disconnectChannel();
				posPnPA[i].disconnectChannel();
				posCV[i].disconnectChannel();
				posScanMode[i].disconnectChannel();
				posPnRA[i].disconnectChannel();
				}
	    	}
	   }
	   
	   public void initPnPV_values(){
		   for (int i=0; i< numberOfPositioners;i++){
			   pnpv_values[i]="NA";
			   posDesc[i] = null;
			   
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
	
	public void setData1D(Data1D d1d) {
		data1D = d1d;
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
	
	public String getPosPnPVDesc(int n){
		return posDesc[n].getValue();
	}
	public String getPosRelAbs(int n){
		return posRelAbs[n].getVal();
	}
	public double getPosPP(int n){
		return posPnPP[n].getDoubleVal();
	}	
	public double getPosMin(int n){
		return posMin[n].getDoubleVal();
	}
	public double getPosWidth(int n){
		return posWidth[n].getVal();
	}	
	public double[] getPosPA(int pos, int n){
		return posPnPA[pos].getArrayValues(n);
	}
	public double getPosCV(int pos){
		return posCV[pos].getDoubleVal();
	}
	public double[] getPosReadBackArray(int pos){
		return posPnRA[pos].getArrayValues();
	}
	public double[] getPosReadBackArray(int pos, int m){
		return posPnRA[pos].getArrayValues(m);
	}

	public PositionerPnPV[] getPosPnPV(){
		return posPnPV;
	}
}
