package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class DetectorCV implements PropertyChangeListener {

	EpicsDataObject pvObject = null;
	String pvName;
	double val;
	EpicsDataObject ret;
	String temp;
	boolean ready = true;

	public DetectorCV(String str, int i) {
		pvName = str + ".D" + String.format("%02d", i) + "CV";
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

	public void setReady(boolean b) {
		ready = b;

	}

	synchronized public double getDoubleVal() {

		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		temp = evtObj.getVal();
		val = Double.parseDouble(temp);
	}

}
