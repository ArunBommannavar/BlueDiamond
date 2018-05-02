package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class SaveFileName implements PropertyChangeListener {

	String pvName="";
	EpicsDataObject pvObject = null;
	String val;
	EpicsDataObject ret;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
	String changePropertyName = " " ;


	public SaveFileName(String str){
		pvName =  str.replaceFirst("scan1", "saveData_fileName");
	}
	
	public void createPV(){
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

	public String getVal() {	
		return val;
	}

	public void propertyChange(PropertyChangeEvent evt) {
	      Object evtObj = evt.getNewValue();
	      ret = (EpicsDataObject)evtObj;
	      val = ret.getVal();
	   	  changes.firePropertyChange(changePropertyName, "-99", val);  
	}
}
