package bluediamond2;

import java.util.ArrayList;
import java.util.List;

import gov.aps.jca.CAException;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class Scan2PositionerParms {
	
	Context context;
	
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
	private String[] positionerDescription = new String[4];

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
			posNV[i] = new PositionerNV(scanPv, j, context);					
			posNV[i].createChannel();

			posMin[i] = new PositionerMin(scanPv, j, context);	
			posMin[i].createChannel();

			posWidth[i] = new PositionerWidth(scanPv,j, context);
			posWidth[i].createChannel();

			posRelAbs[i] = new PositionerRelAbs(scanPv, j, context);	
			posRelAbs[i].createChannel();

			posPnPV[i] = new PositionerPnPV(scanPv, j, context);	
			posPnPV[i].createChannel();

			posPnPP[i] = new PositionerPnPP(scanPv, j, context);
			posPnPP[i].createChannel();

			posPnPA[i] = new PositionerPnPA(scanPv,j, context);
			posPnPA[i].createChannel();	

			posScanMode[i] = new PositionerScanMode(scanPv, j, context);	
			posScanMode[i].createChannel();

			posPnRA[i] = new PositionerPnRA(scanPv, j, context);
			posPnRA[i].createChannel();			
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
		
		for (int i = 0; i < numberOfPositioners; i++) {
			j= i + 1;
			posNV[i].channelLabels();
			posRelAbs[i].channelLabels();
			posScanMode[i].channelLabels();		
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
		
		for (int i = 0; i < numberOfPositioners; i++) {
			j = i + 1;
			posNV[i].setLabels();
			posRelAbs[i].setLabels();
			posScanMode[i].setLabels();
		}		

		for (int i = 0; i < numberOfPositioners; i++) {
			j= i + 1;
			posNV[i].setMonitor();			
			posMin[i].setMonitor();			
			posWidth[i].setMonitor();			
			posRelAbs[i].setMonitor();			
			posPnPV[i].setMonitor();			
			posPnPP[i].setMonitor();			
			posPnPA[i].setMonitor();			
			posScanMode[i].setMonitor();					
			posPnRA[i].setMonitor();		
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
	
	public void findPositionerDescription() {		
		validPos.forEach((n) -> {
			posPnPV[n].findMotorChannelPVdescription();
			positionerDescription[n] = posPnPV[n].getMotorDescription();
		});
	}
	
	public String getPositionerDescription(int n) {
		return positionerDescription[n];
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
	public PositionerPnPV[] getPosPnPVList(){
		return posPnPV;
	}
}
