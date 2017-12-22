package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class DetectorPV implements PropertyChangeListener{
	EpicsDataObject pvObject = null;
	String pvName;
	String val = null;
	EpicsDataObject ret;

	public DetectorPV(String str, int i) {
		pvName = str + ".D" + String.format("%02d", i) + "PV";
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

	public String getVal() {

		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		val = evtObj.getVal();		
		
	}
}
