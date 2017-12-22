package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

/*
 *  If there is a valid positioner, then value will be "PV OK" 
 */
public class PositionerNV implements PropertyChangeListener {
	EpicsDataObject pvObject = null;
	String pvName;
	int val = -1;
	boolean valid = false;
	String temp;

	boolean init = false;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public PositionerNV(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "NV";
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

	public void removePropertyChangeListener(String str, PropertyChangeListener l) {
		changes.removePropertyChangeListener(str, l);
	}

	public void addPropertyChangeListener(String str, PropertyChangeListener l) {
		changes.addPropertyChangeListener(str, l);

	}


	public boolean isValid() {

		if (temp.equals("PV OK")) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}

	public void setInit(boolean b) {
		init = b;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		temp = evtObj.getVal();
	}
}
