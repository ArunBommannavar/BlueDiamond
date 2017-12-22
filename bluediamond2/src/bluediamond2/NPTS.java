package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class NPTS implements PropertyChangeListener{

	EpicsDataObject pvObject = null;
	String pvName;
	int val;
	String temp;
	String changePropertyName ;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public NPTS(String str) {
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
		
		changes.removePropertyChangeListener(str,l);
	}
	public int getVal() {
		return val;
	}

	public void putVal(String str) {
		pvObject.putVal(str);
	}

	public void putVal(int n){
		String strN = String.valueOf(n);
		pvObject.putVal(strN);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	       EpicsDataObject evtObj = (EpicsDataObject)evt.getNewValue();	      
	       val = Integer.parseInt(evtObj.getVal());
//	       temp = evtObj.getVal();
	   	  changes.firePropertyChange(changePropertyName, new Object(), evtObj);   

	}

}
