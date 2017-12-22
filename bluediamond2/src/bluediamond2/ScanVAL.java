package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.*;

public class ScanVAL implements PropertyChangeListener {
	EpicsDataObject pvObject = null;
	double val = -99.0;
	String pvName;
	String changePropertyName = "";

	EpicsDataObject ret;
	String temp;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public ScanVAL(String str) {
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
	public void addPropertyChangeListener(String str, PropertyChangeListener l) {
		changePropertyName = str;
		changes.addPropertyChangeListener(str, l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String str, PropertyChangeListener l) {
		changes.removePropertyChangeListener(str, l);
	}

	synchronized public void propertyChange(PropertyChangeEvent evt) {
		Object evtObj = evt.getNewValue();
		ret = (EpicsDataObject) evtObj;
		temp = ret.getVal();
		val = Double.parseDouble(temp);
//		System.out.println(" VAL value = "+val);
		changes.firePropertyChange(changePropertyName, -1, temp);

	}
}
