package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.ciw.hpcat.epics.data.*;

public class CPT implements PropertyChangeListener {

	EpicsDataObject pvObject = null;
	String pvName;
	int val;
	EpicsDataObject ret;
	String temp;

	public CPT(String str) {
		pvName = str;
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

	synchronized public int getValue() {
		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		val = Integer.parseInt(evtObj.getVal());
//		System.out.println(" pvName "+pvName+"  = "+val);
	}
}
