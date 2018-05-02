package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

/**
 * 
 * @author Arun Bommannavar
 * 
 */
public class PositionerPnPA implements PropertyChangeListener{
	EpicsDataObject pvObject = null;
	String pvName;
	double[] val;

	public PositionerPnPA(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "PA";
	}

	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);		
		pvObject.addPropertyChangeListener("val", this);
	}
	   public void disconnectChannel() {
	        if (pvObject != null) {
	        	pvObject.setDropPv(true);
	        }
	    }

	public void putValue(String[] str) {
		pvObject.putVal(str);
	}

	public double[] getArrayValues() {
		String[] strVal;

		strVal = pvObject.getArrayVal();
		val = new double[strVal.length];

		for (int i = 0; i < strVal.length; i++) {
			val[i] = Double.parseDouble(strVal[i]);
		}
		return val;
	}
	
	public double[] getArrayValues(int n){

		double[] values = new double[n];
		
		for (int i = 0; i < n; i++) {
			values[i] = val[i];
		}
		
		return values;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String[] strVal;
		pvObject.getArrayVal();		
		strVal = pvObject.getArrayVal();
		val= new double[strVal.length];

		for (int i = 0; i < strVal.length; i++) {
			val[i]= Double.parseDouble(strVal[i]);
		}		
	}
}
