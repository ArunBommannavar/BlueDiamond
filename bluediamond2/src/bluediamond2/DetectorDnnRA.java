package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class DetectorDnnRA implements PropertyChangeListener{

	EpicsDataObject pvObject = null;
	String pvName;
	double[] val;
	int cpt;

	public DetectorDnnRA(String str, int n) {
		pvName = str + ".D" + String.format("%02d", n) + "DA";
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

	public void setCPT(int n){
		cpt = n;
	}
	public double[] getDoubleArrayVal() {
		double[] d;

		String[] strVal = pvObject.getArrayVal();
		d = new double[strVal.length];
		for (int i = 0; i < strVal.length; i++) {
			d[i] = Double.parseDouble(strVal[i]);
		}
		return d;
	}
	public double[] getDoubleArrayVal(int n) {
		double[] d = new double[n];
		for (int i = 0; i < n; i++) {
			d[i] = val[i];
		}
		return d;
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
