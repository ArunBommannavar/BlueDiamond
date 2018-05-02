package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerPnRA implements PropertyChangeListener{
	EpicsDataObject pvObject = null;
	String pvName;
	double[] val;
	EpicsDataObject evtObj;

	int cpt;

	public PositionerPnRA(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "RA";
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

	public void setCPT(int n) {
		cpt = n;
	}

	public double[] getArrayValues() {
		return val;	
	}

	public double[] getArrayValues(int n) {

		double[] values = new double[n];
		
		for (int i=0; i<n;i++){
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
